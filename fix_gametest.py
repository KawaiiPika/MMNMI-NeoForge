with open('src/main/java/xyz/pixelatedw/mineminenomi/gametest/ExampleGameTest.java', 'w') as f:
    f.write('''package xyz.pixelatedw.mineminenomi.gametest;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.neoforge.gametest.GameTestHolder;

@GameTestHolder("mineminenomi")
public class ExampleGameTest {

    @GameTest
    public void empty(GameTestHelper helper) {
        helper.succeed();
    }
}
''')
