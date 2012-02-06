
package com.serli.chell.framework;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public abstract class Bootstrap implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        onBootstrap();
    }

    protected abstract void onBootstrap();

    public void contextDestroyed(ServletContextEvent sce) {
    }
}
