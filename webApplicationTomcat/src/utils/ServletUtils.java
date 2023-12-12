package utils;

import jakarta.servlet.ServletContext;
import prediction.manager.EngineManager;
import prediction.manager.IEngineManager;

public class ServletUtils {
    private static final String ENGINE_MANAGER_ATTRIBUTE_NAME = "engineManager";
    private static final Object systemEngineLock = new Object();

//    public static IWorldManager getWorldManager(ServletContext servletContext) {
//        synchronized (systemEngineLock) {
//            if (servletContext.getAttribute(ENGINE_MANAGER_ATTRIBUTE_NAME) == null) {
//                servletContext.setAttribute(ENGINE_MANAGER_ATTRIBUTE_NAME, new WorldManager());
//            }
//        }
//        return (IWorldManager) servletContext.getAttribute(ENGINE_MANAGER_ATTRIBUTE_NAME);
//    }
    public static IEngineManager getEngineManager(ServletContext servletContext) {
        synchronized (systemEngineLock) {
            if (servletContext.getAttribute(ENGINE_MANAGER_ATTRIBUTE_NAME) == null) {
                servletContext.setAttribute(ENGINE_MANAGER_ATTRIBUTE_NAME, new EngineManager());
            }
        }
        return (IEngineManager) servletContext.getAttribute(ENGINE_MANAGER_ATTRIBUTE_NAME);
    }

}
