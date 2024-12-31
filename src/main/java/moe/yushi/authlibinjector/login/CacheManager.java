package moe.yushi.authlibinjector.login;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CacheManager {
    public static CacheManager INSTANCE = new CacheManager();

    private Path cachedCachePath;

    private CacheManager() {}

    public Path getCachePath() {
        if (cachedCachePath != null)
            return cachedCachePath;
        String xdgCache = System.getenv("XDG_CACHE_HOME");
        cachedCachePath = xdgCache == null
            ? Paths.get(System.getProperty("user.home"), ".cache", "anvilauth-injector")
            : Paths.get(xdgCache, "anvilauth-injector");
        return cachedCachePath;
    }

    public void createCache() {
        this.getCachePath().toFile().mkdirs();
    }
}
