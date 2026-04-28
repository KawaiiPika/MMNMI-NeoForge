package xyz.pixelatedw.mineminenomi.gametest.abilities;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

public class FubukiAbilityGameTest {

    @GameTest(template = "mineminenomi:empty_3x3x3")
    public void testFubukiAoe(GameTestHelper helper) {
        helper.succeed();
    }
}
