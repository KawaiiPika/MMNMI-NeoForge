package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractMinecraftTest {

    private static boolean isBootstrapped = false;

    @BeforeAll
    public static void setupMinecraftEnvironment() {
        if (!isBootstrapped) {
            SharedConstants.tryDetectVersion();
            try {
                Bootstrap.bootStrap();
            } catch (Throwable t) {
                // If it fails to fully bootstrap we ignore
            }
            isBootstrapped = true;
        }
    }
}
