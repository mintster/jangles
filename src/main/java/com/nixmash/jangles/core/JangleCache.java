package com.nixmash.jangles.core;


import com.nixmash.jangles.Jangles;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.apache.jcs.engine.ElementAttributes;

import java.io.Serializable;


public final class JangleCache {
    private static JangleCache instance;
    private static JCS jangleCache;
    private Log logger = null;

    private JangleCache() {
        try {

            jangleCache = JCS.getInstance("default");
            //this.logger = LogFactory.getLog(Jangles.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Singleton access point to the manager.
     */
    public static JangleCache GetInstance() {
        synchronized (JangleCache.class) {
            if (instance == null) {
                instance = new JangleCache();
            }
        }

        return instance;
    }

    public Object Get(Serializable key) {
        return jangleCache.get(key);
    }

    public void Put(Serializable key, Serializable _object, double maxLifeMinutes)
    {
        ElementAttributes attributes = new ElementAttributes();
        attributes.setIsEternal(false);
        long maxLifeSeconds = (long)(maxLifeMinutes * 60D);
        attributes.setMaxLifeSeconds(maxLifeSeconds);
        try {
            jangleCache.put(key, _object, attributes);
        } catch (CacheException e) {
            this.logger.error("Failure to put object " + key + "in cache: " + e.getMessage());
        }
    }

    public void Put(Serializable key, Serializable _object) {
        try {
            jangleCache.put(key, _object);
        } catch (CacheException e) {
            this.logger.error("Failure to put object " + key + "in cache: " + e.getMessage());
        }
    }

    public void Remove(Serializable key) {
        try {
            jangleCache.remove(key);
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }

    public void Clear() {
        try {
            jangleCache.clear();
        }
        catch (CacheException e) {
            String msg = "Failure to clear cache:" + e.getMessage();
            this.logger.error(msg);
        }
    }
}