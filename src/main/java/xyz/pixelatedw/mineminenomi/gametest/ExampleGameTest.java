package xyz.pixelatedw.mineminenomi.gametest;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.neoforge.gametest.GameTestHolder;

@GameTestHolder("mineminenomi")
@net.neoforged.neoforge.gametest.PrefixGameTestTemplate(false)
public class ExampleGameTest {

    @GameTest(template="empty")
    public void empty(GameTestHelper helper) {
        helper.succeed();
    }
}
