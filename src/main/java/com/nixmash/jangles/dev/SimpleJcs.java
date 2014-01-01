package com.nixmash.jangles.dev;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.jcs.access.exception.CacheException;
import org.apache.jcs.engine.ElementAttributes;
import org.apache.jcs.engine.control.CompositeCacheManager;
import org.apache.jcs.JCS;

/**
 * Provides a wrapper around Apache JCS (Java Caching System) to facilitate a
 * "common case" for caching. This wrapper provides the following:
 * <ul>
 * <li>Automatic configuration of an indexed disk cache backing store.
 * <li>Write-through caching: All cached instances are written out to disk.
 * <li>Provides a default maximum life for entries of 365 days.
 * <li>Provides a default maximum cache size of 100,000 instances.
 * <li>Provides a default directory location (~/.simplejcs) for cache files.
 * <li>Helps ensure that all SimpleJcs instances have a unique name.
 * <li>All caches use the JCS "group" facility to allow access to the set of
 * keys.
 * <li>Constructor uses "days" rather than seconds as time unit for maxLife.
 * <li>put() uses "hours" rather than seconds as time unit for maxLife.
 * <li>A more convenient API for setting/getting items from the cache and
 * controlling logging.
 * <li>Logging of exceptions raised by JCS.
 * <li>Disables JCS logging messages unless the System property
 * SimpleJcs.enableJCSLogging is set.
 * <li>Shutdown hook ensures that backing index file is closed correctly on JVM
 * exit.
 * <li>Convenient packaging mechanism for required jar files to simplify library
 * use.
 * </ul>
 * 
 * @author Philip Johnson
 */
public class SimpleJcs {

	/** The number of seconds in a day. * */
	private static final long secondsInADay = 60L * 60L * 24L;
	/** Default number of days for max life. */
	private static final Long defaultMaxLifeDays = 365L;
	/**
	 * Maximum number of in-memory instances before sending items to disk.
	 * Default is 50,000.
	 */
	private static final Long defaultCapacity = 100000L;
	/** The name of this cache, which defines a "region" in JCS terms. */
	private String cacheName = "Jangles";
	/** The logger used for cache exception logging. */
	private Log logger = null;
	/** Holds a list of already defined caches to help ensure uniqueness. */
	private static List<String> cacheNames = new ArrayList<String>();
	/**
	 * Default group name. No client should ever using the following string for
	 * a group.
	 */
	private static final String DEFAULT_GROUP = "__Default_SimpleJcs_Group__";

	private static final String failureMsg = "Failure to clear cache ";

	/** Default directory where cache files go: ~/.simplejcs. **/
	private static final String defaultDir = System.getProperty("user.home")
			+ "/.simplejcs";

	/**
	 * A thread that will ensure that all of these caches will be disposed of
	 * during shutdown.
	 */
	private static Thread shutdownThread = new Thread() {
		/** Run the shutdown hook for disposing of all caches. */
		@Override
		public void run() {
			// for (String cacheName : cacheNames) {
			try {
				// System.out.println("Shutting down " + cacheName + " cache.");
				JCS.getInstance("Jangles").dispose();
				dispose("Jangles");
			} catch (Exception e) {
				String msg = failureMsg + ":" + e.getMessage();
				System.out.println(msg);
			}
		}
		// }
	};

	/**
	 * A boolean that enables us to figure out whether to install the shutdown
	 * thread.
	 */
	private static boolean hasShutdownHook = false; // NOPMD

	private static SimpleJcs instance;

	public static SimpleJcs GetInstance() {
		synchronized (SimpleJcs.class) {
			if (instance == null) {
				instance = new SimpleJcs("Jangles");
			}
		}

		return instance;
	}

	/**
	 * Creates a new SimpleJcs instance with the specified name. Defaults used
	 * for all other configuration properties.
	 * 
	 * @param cacheName
	 *            The name of this cache.
	 */
	private SimpleJcs(String cacheName) {
		this(cacheName, defaultDir, defaultMaxLifeDays * secondsInADay,
				defaultCapacity);
	}

	/**
	 * Creates a new SimpleJcs instance with the specified name in the specified
	 * directory. Defaults used for all other configuration properties.
	 * 
	 * @param cacheName
	 *            The name of this cache.
	 * @param dir
	 *            The directory where the cache files are located.
	 */
	public SimpleJcs(String cacheName, String dir) {
		this(cacheName, dir, defaultMaxLifeDays * secondsInADay,
				defaultCapacity);
	}

	/**
	 * Creates a new SimpleJcs with the specified parameters. If a cache with
	 * this name already exists, then this instance will be an alias to that
	 * cache and its original configuration will remain unchanged.
	 * 
	 * @param cacheName
	 *            The name of this SimpleJcs, which will be used as the JCS
	 *            "region".
	 * @param dir
	 *            the directory in which the backing store will be created.
	 * @param maxLifeSeconds
	 *            The maximum number of SECONDS after which items expire from
	 *            the cache.
	 * @param capacity
	 *            The maximum number of instances to hold in the cache.
	 */
	public SimpleJcs(String cacheName, String dir, Long maxLifeSeconds,
			Long capacity) {
		// Set up the shutdown hook if we're the first one. Not thread safe, but
		// there's not too
		// much harm done if there are multiple shutdown hooks running.
		if (!SimpleJcs.hasShutdownHook) {
			Runtime.getRuntime().addShutdownHook(SimpleJcs.shutdownThread);
			SimpleJcs.hasShutdownHook = true;
		}
		this.cacheName = cacheName;
		this.logger = LogFactory.getLog(this.getClass()); // SimpleJcsLogger.getLogger(cacheName
															// + ".SimpleJcs",
															// dir + "/logs");
		mkdirs(dir);

		// Finish configuration if this is a new instance of the cache.
		if (!SimpleJcs.cacheNames.contains(cacheName)) {
			SimpleJcs.cacheNames.add(cacheName);
			if (!System.getProperties().containsKey(
					"SimpleJcs.enableJCSLogging")) {

				// Logger.getLogger("org.apache.jcs").setLevel(Level.OFF);
			}
			CompositeCacheManager ccm = CompositeCacheManager
					.getUnconfiguredInstance();
			ccm.configure(initJcsProps(cacheName, dir, maxLifeSeconds, capacity));
		}
	}

	/**
	 * Adds the key-value pair to this cache. Entry will expire from cache after
	 * the default maxLife (currently 24 hours). Logs a message if the cache
	 * throws an exception.
	 * 
	 * @param key
	 *            The key, typically a UriString.
	 * @param value
	 *            The value, typically the object returned from the Hackystat
	 *            service.
	 */
	public void put(Serializable key, Serializable value) {
		try {
			JCS.getInstance(this.cacheName).putInGroup(key, DEFAULT_GROUP,
					value);
		} catch (CacheException e) {
			String msg = "Failure to add " + key + " to cache "
					+ this.cacheName + ":" + e.getMessage();
			this.logger.warn(msg);
		}
	}

	/**
	 * Adds the key-value pair to this cache with an explicit expiration time.
	 * 
	 * @param key
	 *            The key, typically a UriString.
	 * @param value
	 *            The value, typically the object returned from the Hackystat
	 *            service.
	 * @param maxLifeHours
	 *            The number of hours before this item will expire from cache.
	 */
	public void put(Serializable key, Serializable value, double maxLifeHours) {
		try {
			ElementAttributes attributes = new ElementAttributes();
			long maxLifeSeconds = (long) (maxLifeHours * 3600D);
			attributes.setMaxLifeSeconds(maxLifeSeconds);
			attributes.setIsEternal(false);
			JCS.getInstance(this.cacheName).putInGroup(key, DEFAULT_GROUP,
					value, attributes);
		} catch (CacheException e) {
			String msg = "Failure to add " + key + " to cache "
					+ this.cacheName + ":" + e.getMessage();
			this.logger.warn(msg);
		}
	}

	/**
	 * Returns the object associated with key from the cache, or null if not
	 * found.
	 * 
	 * @param key
	 *            The key whose associated value is to be retrieved.
	 * @return The value, or null if not found.
	 */
	public Object get(Serializable key) {
		try {
			return JCS.getInstance(this.cacheName).getFromGroup(key,
					DEFAULT_GROUP);
		} catch (CacheException e) {
			String msg = "Failure of get: " + key + " in cache "
					+ this.cacheName + ":" + e.getMessage();
			this.logger.warn(msg);
			return null;
		}
	}

	/**
	 * Ensures that the key-value pair associated with key is no longer in this
	 * cache. Logs a message if the cache throws an exception.
	 * 
	 * @param key
	 *            The key to be removed.
	 */
	public void remove(Serializable key) {
		try {
			JCS.getInstance(this.cacheName).remove(key, DEFAULT_GROUP);
		} catch (CacheException e) {
			String msg = "Failure to remove: " + key + " cache "
					+ this.cacheName + ":" + e.getMessage();
			this.logger.warn(msg);
		}
	}

	/**
	 * Removes everything in the default cache, but not any of the group caches.
	 */
	public void clear() {
		clearGroup(DEFAULT_GROUP);
	}

	/**
	 * Clears the default as well as all group caches.
	 */
	public void clearAll() {
		try {
			JCS.getInstance(this.cacheName).clear();
		} catch (CacheException e) {
			String msg = failureMsg + this.cacheName + ":" + e.getMessage();
			this.logger.warn(msg);
		}
	}

	/**
	 * Returns the set of keys associated with this cache.
	 * 
	 * @return The set containing the keys for this cache.
	 */
	public Set<Serializable> getKeys() {
		return getGroupKeys(DEFAULT_GROUP);
	}

	/**
	 * Returns the current number of elements in this cache.
	 * 
	 * @return The current size of this cache.
	 */
	public int size() {
		return getGroupSize(DEFAULT_GROUP);
	}

	/**
	 * Shuts down the specified cache, and removes it from the list of active
	 * caches so it can be created again.
	 * 
	 * @param cacheName
	 *            The name of the cache to dispose of.
	 */
	public static void dispose(String cacheName) {
		try {
			cacheNames.remove(cacheName);
			JCS.getInstance(cacheName).dispose();
		} catch (CacheException e) {
			String msg = failureMsg + cacheName + ":" + e.getMessage();
			System.out.println(msg);
		}
	}

	/**
	 * Implements group-based addition of cache elements.
	 * 
	 * @param key
	 *            The key.
	 * @param group
	 *            The group.
	 * @param value
	 *            The value.
	 */
	public void putInGroup(Serializable key, String group, Serializable value) {
		try {
			JCS.getInstance(this.cacheName).putInGroup(key, group, value);
		} catch (CacheException e) {
			String msg = "Failure to add " + key + " to cache "
					+ this.cacheName + ":" + e.getMessage();
			this.logger.warn(msg);
		}
	}

	/**
	 * Implements group-based retrieval of cache elements.
	 * 
	 * @param key
	 *            The key.
	 * @param group
	 *            The group.
	 * @return The element associated with key in the group, or null.
	 */
	public Object getFromGroup(Serializable key, String group) {
		try {
			return JCS.getInstance(this.cacheName).getFromGroup(key, group);
		} catch (CacheException e) {
			String msg = "Failure of get: " + key + " in cache "
					+ this.cacheName + ":" + e.getMessage();
			this.logger.warn(msg);
			return null;
		}
	}

	/**
	 * Implements group-based removal of cache elements.
	 * 
	 * @param key
	 *            The key whose value is to be removed.
	 * @param group
	 *            The group.
	 */
	public void removeFromGroup(Serializable key, String group) {
		try {
			JCS.getInstance(this.cacheName).remove(key, group);
		} catch (CacheException e) {
			String msg = "Failure to remove: " + key + " cache "
					+ this.cacheName + ":" + e.getMessage();
			this.logger.warn(msg);
		}
	}

	/**
	 * Returns the set of cache keys associated with this group.
	 * 
	 * @param group
	 *            The group.
	 * @return The set of cache keys for this group.
	 */
	@SuppressWarnings("unchecked")
	public Set<Serializable> getGroupKeys(String group) {
		Set<Serializable> keySet;
		try {
			keySet = JCS.getInstance(this.cacheName).getGroupKeys(group);
		} catch (CacheException e) {
			String msg = "Failure to obtain keyset for cache: "
					+ this.cacheName;
			this.logger.warn(msg);
			keySet = new HashSet<Serializable>();
		}
		return keySet;
	}

	/**
	 * Returns the current number of elements in this cache group.
	 * 
	 * @param group
	 *            The name of the group.
	 * @return The current size of this cache.
	 */
	public int getGroupSize(String group) {
		return getGroupKeys(group).size();
	}

	/**
	 * Removes everything in the specified group.
	 * 
	 * @param group
	 *            The group name.
	 */
	public void clearGroup(String group) {
		try {
			JCS cache = JCS.getInstance(this.cacheName);
			for (Object key : cache.getGroupKeys(group)) {
				cache.remove(key, group);
			}
		} catch (CacheException e) {
			String msg = failureMsg + this.cacheName + ":" + e.getMessage();
			this.logger.warn(msg);
		}
	}

	/**
	 * Sets up the Properties instance for configuring this JCS cache instance.
	 * Each SimpleJcs is defined as a JCS "region". Given a SimpleJcs named
	 * "PJ", we create a properties instance whose contents are similar to the
	 * following:
	 * 
	 * <pre>
	 * jcs.region.PJ=DC-PJ
	 * jcs.region.PJ.cacheattributes=org.apache.jcs.engine.CompositeCacheAttributes
	 * jcs.region.PJ.cacheattributes.MaxObjects=[maxCacheCapacity]
	 * jcs.region.PJ.cacheattributes.MemoryCacheName=org.apache.jcs.engine.memory.lru.LRUMemoryCache
	 * jcs.region.PJ.cacheattributes.UseMemoryShrinker=true
	 * jcs.region.PJ.cacheattributes.MaxMemoryIdleTimeSeconds=3600
	 * jcs.region.PJ.cacheattributes.ShrinkerIntervalSeconds=3600
	 * jcs.region.PJ.cacheattributes.MaxSpoolPerRun=500
	 * jcs.region.PJ.elementattributes=org.apache.jcs.engine.ElementAttributes
	 * jcs.region.PJ.elementattributes.IsEternal=false
	 * jcs.region.PJ.elementattributes.MaxLifeSeconds=[maxIdleTime]
	 * jcs.auxiliary.DC-PJ=org.apache.jcs.auxiliary.disk.indexed.IndexedDiskCacheFactory
	 * jcs.auxiliary.DC-PJ.attributes=org.apache.jcs.auxiliary.disk.indexed.IndexedDiskCacheAttributes
	 * jcs.auxiliary.DC-PJ.attributes.DiskPath=[cachePath]
	 * jcs.auxiliary.DC-PJ.attributes.maxKeySize=10000000
	 * </pre>
	 * 
	 * We define cachePath as
	 * HackystatHome.getHome()/.hackystat/[cacheSubDir]/cache. This enables a
	 * service a cache name of "dailyprojectdata" and have the cache data put
	 * inside its internal subdirectory.
	 * 
	 * See bottom of: http://jakarta.apache.org/jcs/BasicJCSConfiguration.html
	 * for more details.
	 * 
	 * @param cacheName
	 *            The name of this cache, used to define the region properties.
	 * @param dir
	 *            The directory name, used to generate the disk storage
	 *            directory.
	 * @param maxLifeSeconds
	 *            The maximum life of instances in the cache in seconds before
	 *            they expire.
	 * @param maxCapacity
	 *            The maximum size of this cache.
	 * @return The properties file.
	 */
	private Properties initJcsProps(String cacheName, String dir,
			Long maxLifeSeconds, Long maxCapacity) {
		String reg = "jcs.region." + cacheName;
		String regCacheAtt = reg + ".cacheattributes";
		String regEleAtt = reg + ".elementattributes";
		String aux = "jcs.auxiliary.DC-" + cacheName;
		String auxAtt = aux + ".attributes";
		String memName = "org.apache.jcs.engine.memory.lru.LRUMemoryCache";
		String diskAttName = "org.apache.jcs.auxiliary.disk.indexed.IndexedDiskCacheAttributes";
		Properties props = new Properties();
		props.setProperty(reg, "DC-" + cacheName);
		props.setProperty(regCacheAtt,
				"org.apache.jcs.engine.CompositeCacheAttributes");
		props.setProperty(regCacheAtt + ".MaxObjects", maxCapacity.toString());
		props.setProperty(regCacheAtt + ".MemoryCacheName", memName);
		props.setProperty(regCacheAtt + ".UseMemoryShrinker", "true");
		props.setProperty(regCacheAtt + ".MaxMemoryIdleTimeSeconds", "3600");
		props.setProperty(regCacheAtt + ".ShrinkerIntervalSeconds", "3600");
		props.setProperty(regCacheAtt + ".DiskUsagePatternName", "UPDATE");
		props.setProperty(regCacheAtt + ".MaxSpoolPerRun", "500");
		props.setProperty(regEleAtt, "org.apache.jcs.engine.ElementAttributes");
		props.setProperty(regEleAtt + ".IsEternal", "false");
		props.setProperty(regEleAtt + ".MaxLifeSeconds",
				maxLifeSeconds.toString());
		props.setProperty(aux,
				"org.apache.jcs.auxiliary.disk.indexed.IndexedDiskCacheFactory");
		props.setProperty(auxAtt, diskAttName);
		props.setProperty(auxAtt + ".DiskPath", dir);
		props.setProperty(auxAtt + ".maxKeySize", "1000000");
		return props;
	}

	/**
	 * Instantiates the directory indicated by dir, and throws an error if this
	 * is unsuccessful.
	 * 
	 * @param dir
	 *            The directory where the cache files will go.
	 */
	private void mkdirs(String dir) {
		File path = new File(dir);
		boolean dirsOk = path.mkdirs();
		if (!dirsOk && !path.exists()) {
			throw new RuntimeException("mkdirs() failed");
		}
	}
}