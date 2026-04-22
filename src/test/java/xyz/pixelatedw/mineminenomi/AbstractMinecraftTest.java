package xyz.pixelatedw.mineminenomi;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractMinecraftTest {
    @BeforeAll
    public static void setupMinecraft() {
        try {
            SharedConstants.tryDetectVersion();
            Bootstrap.bootStrap();
        } catch (Throwable t) {
            System.err.println("Failed to bootstrap Minecraft: " + t.getMessage());
        }
    }
}
