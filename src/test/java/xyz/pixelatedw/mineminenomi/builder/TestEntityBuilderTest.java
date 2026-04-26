package xyz.pixelatedw.mineminenomi.builder;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

import static org.junit.jupiter.api.Assertions.*;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import net.minecraft.world.level.GameType;
import xyz.pixelatedw.mineminenomi.ModMain;

@GameTestHolder(ModMain.PROJECT_ID)
@PrefixGameTestTemplate(false)
public class TestEntityBuilderTest {

    @GameTest(template="abilitygametest.empty")
    public void testBuilderWithGameTestHelper(GameTestHelper helper) {
        ResourceLocation testFruit = ResourceLocation.fromNamespaceAndPath("mineminenomi", "yuki_yuki_no_mi");

        Player testPlayer = TestEntityBuilder.instance()
            .withDoriki(100)
            .withDevilFruit(testFruit)
            .create(helper, GameType.SURVIVAL);

        PlayerStats stats = testPlayer.getData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.PLAYER_STATS);

        helper.assertTrue(stats.getBasic().doriki() == 100, "Doriki should be 100");
        helper.assertTrue(stats.getBasic().identity().devilFruit().orElse(null).equals(testFruit), "Fruit should be Yuki Yuki no Mi");
        helper.succeed();
    }

    @org.junit.jupiter.api.Test
    public void dummyTest() {
        assertTrue(true);
    }
}
