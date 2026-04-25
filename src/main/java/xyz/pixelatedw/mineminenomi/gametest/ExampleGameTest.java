package xyz.pixelatedw.mineminenomi.gametest;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("mineminenomi")
@PrefixGameTestTemplate(false)
public class ExampleGameTest {

    @GameTest(template="minecraft:empty")
    public void empty(GameTestHelper helper) {
        helper.succeed();
    }
}
