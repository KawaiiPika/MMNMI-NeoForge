package xyz.pixelatedw.mineminenomi.gametest;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("mineminenomi")
<<<<<<< HEAD
=======
@PrefixGameTestTemplate(false)
>>>>>>> origin/main
public class ExampleGameTest {

    @GameTest(template="empty")
    public void empty(GameTestHelper helper) {
        helper.succeed();
    }
}
