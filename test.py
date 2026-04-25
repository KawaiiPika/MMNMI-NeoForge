# Create a proper gametest with no template! Oh wait, `No test functions were given!`
# The framework requires at least one test.
# I'll create a GameTest generator instead.
import os
os.makedirs('src/main/java/xyz/pixelatedw/mineminenomi/gametest/', exist_ok=True)
with open('src/main/java/xyz/pixelatedw/mineminenomi/gametest/ExampleGameTest.java', 'w') as f:
    f.write('''package xyz.pixelatedw.mineminenomi.gametest;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("mineminenomi")
public class ExampleGameTest {

    @GameTest(template="minecraft:empty")
    public void empty(GameTestHelper helper) {
        helper.succeed();
    }
}
''')
