package xyz.pixelatedw.mineminenomi.gametest;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.api.helpers.HakiHelper;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.services.ProgressionService;

@GameTestHolder("mineminenomi")
@PrefixGameTestTemplate(false)
@net.neoforged.neoforge.gametest.PrefixGameTestTemplate(false)
public class ProgressionGameTest {

    @GameTest(template="empty")
    public void testDorikiProgressionCaps(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);

        // Use TestEntityBuilder equivalent or setData
        PlayerStats newStats = new PlayerStats();
        newStats.setDoriki(15000); // Set beyond limit (should cap to 10000 internally)
        player.setData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.PLAYER_STATS, newStats);

        PlayerStats stats = PlayerStats.get(player);

        ProgressionService.checkProgression(player);

        // Should cap at 10 tiers (10000 doriki)
        if (stats.getTrainingPoints(TrainingPointType.MARTIAL_ARTS) != 10) {
            helper.fail("Expected 10 training points for Martial Arts, but got: " + stats.getTrainingPoints(TrainingPointType.MARTIAL_ARTS));
            return;
        }

        if (stats.getBasic().trainingPoints().getOrDefault("LAST_PROCESSED_DORIKI", 0) != 10000) {
            helper.fail("Expected LAST_PROCESSED_DORIKI to be 10000, but got: " + stats.getBasic().trainingPoints().getOrDefault("LAST_PROCESSED_DORIKI", 0));
            return;
        }

        helper.succeed();
    }

    @GameTest(template="empty")
    public void testHakiExpCap(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);

        PlayerStats newStats = new PlayerStats();
        // Attempt to set beyond cap
        newStats.setBusoshokuHakiExp(200.0f);
        player.setData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.PLAYER_STATS, newStats);

        PlayerStats stats = PlayerStats.get(player);

        if (stats.getBusoshokuHakiExp() != 100.0f) {
            helper.fail("Expected Busoshoku Haki Exp to be capped at 100.0, but got: " + stats.getBusoshokuHakiExp());
            return;
        }

        stats.setBusoshokuActive(true);
        // Haki Helper damage calculation check
        float originalAmount = 10.0f;
        float damage = HakiHelper.calculateHakiDamage(player, player, originalAmount);

        // 1.0 + (100.0 / 100.0) = 2.0 -> 10 * 2.0 = 20.0
        if (damage != 20.0f) {
            helper.fail("Expected calculated Haki Damage to be 20.0, but got: " + damage);
            return;
        }

        helper.succeed();
    }

}
