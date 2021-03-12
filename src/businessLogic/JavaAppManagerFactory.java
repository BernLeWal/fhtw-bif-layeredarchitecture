package businessLogic;

public final class JavaAppManagerFactory {
    private static JavaAppManager manager;

    public static JavaAppManager GetFactoryManager() {
        if (manager == null) {
            manager = new JavaAppManagerImpl();
        }
        return manager;
    }
}
