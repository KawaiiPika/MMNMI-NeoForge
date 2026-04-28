package xyz.pixelatedw.mineminenomi.gametest.abilities;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import xyz.pixelatedw.mineminenomi.api.helpers.HakiHelper;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

@GameTestHolder("mineminenomi")
@PrefixGameTestTemplate(false)
public class HakiGameTest {

    @GameTest(template="empty_chest")
    public void testBusoshokuDamageBoost(GameTestHelper helper) {
        LivingEntity attacker = helper.spawn(EntityType.PIG, 1, 1, 1);
        LivingEntity target = helper.spawn(EntityType.COW, 2, 1, 2);

        PlayerStats stats = PlayerStats.get(attacker);
        if (stats == null) {
            helper.fail("PlayerStats attachment is null");
            return;
        }

        stats.setBusoshokuActive(true);
        stats.setBusoshokuHakiExp(5000f); // Should result in +1.0 boost

        float originalDamage = 10.0f;
        float expectedDamage = originalDamage * 2.0f; // 1.0 base + 1.0 from exp

        float calculatedDamage = HakiHelper.calculateHakiDamage(attacker, target, originalDamage);

        if (calculatedDamage != expectedDamage) {
            helper.fail("Expected " + expectedDamage + " damage but got " + calculatedDamage);
            return;
        }

        helper.succeed();
    }

    @GameTest(template="empty_chest")
    public void testHakiDamageTakenExpGain(GameTestHelper helper) {
        LivingEntity attacker = helper.spawn(EntityType.PIG, 1, 1, 1);
        PlayerStats stats = PlayerStats.get(attacker);

        if (stats == null) {
            helper.fail("PlayerStats attachment is null");
            return;
        }

        stats.setBusoshokuActive(true);
        stats.setBusoshokuHakiExp(0f);

        HakiHelper.onHakiDamageTaken(attacker);

        helper.succeedWhen(() -> {
            if (stats.getBusoshokuHakiExp() <= 0f) {
                helper.fail("Haki exp did not increase upon taking damage");
            }
        });
    }
}
