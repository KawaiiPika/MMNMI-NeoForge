package xyz.pixelatedw.mineminenomi.gametest.abilities;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

public class YomiAbilityGameTest {

    @GameTest(template = "mineminenomi:empty_3x3x3")
    public void testYomiLogiaImmunity(GameTestHelper helper) {
        helper.succeed();
    }
}
