package com.serli.chell.framework.message;

import com.serli.chell.framework.PortletHelper;
import com.serli.chell.framework.util.ConvertUtils;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public final class MessageBundle {

    private static final String FRAMEWORK_BASE_BUNDLE = "chell-messages";
    private static final String APPLICATION_BASE_BUNDLE = "application";

    private static Set<String> mergedBundleNames = ConvertUtils.toOrderedSet(FRAMEWORK_BASE_BUNDLE, APPLICATION_BASE_BUNDLE);
    private static ConcurrentHashMap<Locale, MergedResourceBundle> mergedBundles = new ConcurrentHashMap<Locale, MergedResourceBundle>();
    private static ConcurrentHashMap<String, ResourceBundle> cachedBundles = new ConcurrentHashMap<String, ResourceBundle>();

    private MessageBundle() {
    }

    public static synchronized void loadBundle(String bundleName) {
        mergedBundleNames.add(bundleName);
    }

    public static String getMessage(String key) {
        return message(getMergedBundle(PortletHelper.getLocale()), key);
    }

    public static String getMessage(Locale locale, String key) {
        return message(getMergedBundle(locale), key);
    }

    public static String getMessageFormatted(String key, Object... arguments) {
        return MessageFormat.format(getMessage(key), arguments);
    }

    public static String getMessageFormatted(Locale locale, String key, Object... arguments) {
        return MessageFormat.format(getMessage(locale, key), arguments);
    }

    public static String getBundleMessage(String bundleName, String key) {
        return message(getCachedBundle(bundleName, PortletHelper.getLocale()), key);
    }

    public static String getBundleMessage(String bundleName, Locale locale, String key) {
        return message(getCachedBundle(bundleName, locale), key);
    }

    public static String getBundleMessageFormatted(String bundleName, String key, Object... arguments) {
        return MessageFormat.format(getBundleMessage(bundleName, key), arguments);
    }

    public static String getBundleMessageFormatted(String bundleName, Locale locale, String key, Object... arguments) {
        return MessageFormat.format(getBundleMessage(bundleName, locale, key), arguments);
    }

    private static ResourceBundle getCachedBundle(String bundleName, Locale locale) {
        String bundleKey = locale.toString() + bundleName;
        ResourceBundle bundle = cachedBundles.get(bundleKey);
        if (bundle == null) {
            bundle =  ResourceBundle.getBundle(bundleName, locale);
            cachedBundles.putIfAbsent(bundleKey, bundle);
            ResourceBundle.clearCache();
        }
        return bundle;
    }

    private static ResourceBundle getMergedBundle(Locale locale) {
        MergedResourceBundle bundle = mergedBundles.get(locale);
        bundle = mergedBundles.get(locale);
        if (bundle == null) {
            bundle = new MergedResourceBundle(locale);
            mergedBundles.putIfAbsent(locale, bundle);
        }
        return bundle;
    }

    private static String message(ResourceBundle bundle, String key) {
        try {
            return bundle.getString(key);
        } catch (Exception e) {
            return error(key);
        }
    }

    private static String error(String key) {
        return new StringBuilder("??").append(key).append("??").toString();
    }

    private static class MergedResourceBundle extends ResourceBundle {

        private Map<String, Object> lookup = new HashMap<String, Object>();
        private Set<String> loadedBundleNames = new HashSet<String>();
        private Locale currentLocale;



        public MergedResourceBundle(Locale currentLocale) {
            this.currentLocale = currentLocale;
        }

        private Map<String, Object> getLookup() {
            if (mergedBundleNames.size() != loadedBundleNames.size()) {
                Enumeration<String> keys;
                String key;
                for (String bundleName : mergedBundleNames) {
                    if (!loadedBundleNames.contains(bundleName)) {
                        loadedBundleNames.add(bundleName);
                        try {
                            ResourceBundle rb = ResourceBundle.getBundle(bundleName, currentLocale);
                            keys = rb.getKeys();
                            while (keys.hasMoreElements()) {
                                key = keys.nextElement();
                                lookup.put(key, rb.getObject(key));
                            }
                        } catch (MissingResourceException ex) {
                        }
                    }
                }
                ResourceBundle.clearCache();
            }
            return lookup;
        }

        public Object handleGetObject(String key) {
            synchronized (MessageBundle.class) {
                return getLookup().get(key);
            }
        }

        public Enumeration<String> getKeys() {
            synchronized (MessageBundle.class) {
                return new BundleKeyEnumeration(getLookup());
            }
        }

        @Override
        protected Set<String> handleKeySet() {
            synchronized (MessageBundle.class) {
                return getLookup().keySet();
            }
        }
    }

    private static class BundleKeyEnumeration implements Enumeration<String> {

        private Iterator<String> it;

        public BundleKeyEnumeration(Map<String, Object> bundle) {
            it = bundle.keySet().iterator();
        }

        public boolean hasMoreElements() {
            return it.hasNext();
        }

        public String nextElement() {
            return it.next();
        }
    }
}
