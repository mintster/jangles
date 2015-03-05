package com.nixmash.jangles.core;


import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.apache.jcs.engine.ElementAttributes;

import java.io.Serializable;


public final class JanglesCache {
    private static JanglesCache instance;
    private static JCS janglesCache;
    private JanglesCache() {
        try {
//            FileOutputStream f = new FileOutputStream("/home/daveburke/web/jangles/deverror.log");
//            System.setErr(new PrintStream(f));
        	janglesCache = JCS.getInstance("default");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static JanglesCache getInstance() {
        synchronized (JanglesCache.class) {
            if (instance == null) {
                instance = new JanglesCache();
            }
        }

        return instance;
    }

    public Object get(Serializable key) {
        return janglesCache.get(key);
    }

    public void put(Serializable key, Serializable object, double maxLifeMinutes)
    {
        ElementAttributes attributes = new ElementAttributes();
        attributes.setIsEternal(false);
        long maxLifeSeconds = (long)(maxLifeMinutes * 60D);
        attributes.setMaxLifeSeconds(maxLifeSeconds);
        try {
        	janglesCache.put(key, object, attributes);
        } catch (CacheException e) {
        }
    }

    public void put(Serializable key, Serializable object) {
        try {
        	janglesCache.put(key, object);
        } catch (CacheException e) {
        }
    }

    public void remove(Serializable key) {
        try {
        	janglesCache.remove(key);
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        try {
        	janglesCache.clear();
        }
        catch (CacheException e) {
            String msg = "Failure to clear cache:" + e.getMessage();
            JanglesLogs.instance().logError(msg);
        }
    }
}