package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.builder.TestEntityBuilder;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

@GameTestHolder(ModMain.PROJECT_ID)
@PrefixGameTestTemplate(false)
public class Batch1GameTests {

    @GameTest(template="abilitygametest.empty")
    public void testYukiYukiNoMiInitialization(GameTestHelper helper) {
        ResourceLocation yukiFruit = ResourceLocation.fromNamespaceAndPath("mineminenomi", "yuki_yuki_no_mi");

        Player testPlayer = TestEntityBuilder.instance()
            .withDoriki(500)
            .withDevilFruit(yukiFruit)
            .create(helper, GameType.SURVIVAL);

        testPlayer.setPos(helper.absolutePos(new BlockPos(1, 2, 1)).getCenter());

        PlayerStats stats = testPlayer.getData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.PLAYER_STATS);

        helper.assertTrue(stats.getBasic().doriki() == 500, "Doriki should be initialized to 500");
        helper.assertTrue(stats.getBasic().identity().devilFruit().isPresent(), "Player should have a Devil Fruit");
        helper.assertTrue(stats.getBasic().identity().devilFruit().get().equals(yukiFruit), "Player should have Yuki Yuki no Mi");

        helper.succeed();
    }


    @GameTest(template="abilitygametest.empty")
    public void testYomiYomiNoMiInitialization(GameTestHelper helper) {
        ResourceLocation yomiFruit = ResourceLocation.fromNamespaceAndPath("mineminenomi", "yomi_yomi_no_mi");

        Player testPlayer = TestEntityBuilder.instance()
            .withDevilFruit(yomiFruit)
            .create(helper, GameType.SURVIVAL);

        testPlayer.setPos(helper.absolutePos(new BlockPos(1, 2, 1)).getCenter());

        PlayerStats stats = testPlayer.getData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.PLAYER_STATS);

        helper.assertTrue(stats.getBasic().identity().devilFruit().isPresent(), "Player should have a Devil Fruit");
        helper.assertTrue(stats.getBasic().identity().devilFruit().get().equals(yomiFruit), "Player should have Yomi Yomi no Mi");

        helper.succeed();
    }

    @GameTest(template="abilitygametest.empty")
    public void testGasuGasuNoMiInitialization(GameTestHelper helper) {
        ResourceLocation gasuFruit = ResourceLocation.fromNamespaceAndPath("mineminenomi", "gasu_gasu_no_mi");

        Player testPlayer = TestEntityBuilder.instance()
            .withDevilFruit(gasuFruit)
            .create(helper, GameType.SURVIVAL);

        testPlayer.setPos(helper.absolutePos(new BlockPos(1, 2, 1)).getCenter());

        PlayerStats stats = testPlayer.getData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.PLAYER_STATS);

        helper.assertTrue(stats.getBasic().identity().devilFruit().isPresent(), "Player should have a Devil Fruit");
        helper.assertTrue(stats.getBasic().identity().devilFruit().get().equals(gasuFruit), "Player should have Gasu Gasu no Mi");

        helper.succeed();
    }

    @org.junit.jupiter.api.Test
    public void dummyTest() {
        org.junit.jupiter.api.Assertions.assertTrue(true);
    }
}
