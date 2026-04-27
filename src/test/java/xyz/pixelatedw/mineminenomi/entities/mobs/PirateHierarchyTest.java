package xyz.pixelatedw.mineminenomi.entities.mobs;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.gametest.GameTestHolder;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.init.ModFactions;

@GameTestHolder(ModMain.PROJECT_ID)
public class PirateHierarchyTest {

    @GameTest(template = "examplegametest.empty")
    public void testPirateFactionChecks(GameTestHelper helper) {
        GruntEntity grunt = helper.spawn(EntityType.ZOMBIE, new BlockPos(1, 2, 1)).convertTo(xyz.pixelatedw.mineminenomi.init.ModMobs.PIRATE_GRUNT.get(), true);
        if (grunt != null) {
            grunt.getStats().setFaction(ModFactions.PIRATE.getId());
            helper.assertTrue(grunt.getStats().isPirate(), "Entity should be a Pirate");
            helper.assertFalse(grunt.getStats().isMarine(), "Entity should not be a Marine");
            helper.assertFalse(grunt.getStats().isBandit(), "Entity should not be a Bandit");
            helper.succeed();
        } else {
            helper.fail("Failed to convert ZOMBIE to PIRATE_GRUNT");
        }
    }

    @GameTest(template = "examplegametest.empty")
    public void testMarineFactionChecks(GameTestHelper helper) {
        CaptainEntity marine = helper.spawn(EntityType.ZOMBIE, new BlockPos(1, 2, 1)).convertTo(xyz.pixelatedw.mineminenomi.init.ModMobs.MARINE_CAPTAIN.get(), true);
        if (marine != null) {
            marine.getStats().setFaction(ModFactions.MARINE.getId());
            helper.assertFalse(marine.getStats().isPirate(), "Entity should not be a Pirate");
            helper.assertTrue(marine.getStats().isMarine(), "Entity should be a Marine");
            helper.assertFalse(marine.getStats().isBandit(), "Entity should not be a Bandit");
            helper.succeed();
        } else {
            helper.fail("Failed to convert ZOMBIE to MARINE_CAPTAIN");
        }
    }

    @GameTest(template = "examplegametest.empty")
    public void testBanditFactionChecks(GameTestHelper helper) {
        SniperEntity bandit = helper.spawn(EntityType.ZOMBIE, new BlockPos(1, 2, 1)).convertTo(xyz.pixelatedw.mineminenomi.init.ModMobs.BANDIT_SNIPER.get(), true);
        if (bandit != null) {
            bandit.getStats().setFaction(ModFactions.BANDIT.getId());
            helper.assertFalse(bandit.getStats().isPirate(), "Entity should not be a Pirate");
            helper.assertFalse(bandit.getStats().isMarine(), "Entity should not be a Marine");
            helper.assertTrue(bandit.getStats().isBandit(), "Entity should be a Bandit");
            helper.succeed();
        } else {
            helper.fail("Failed to convert ZOMBIE to BANDIT_SNIPER");
        }
    }
}
