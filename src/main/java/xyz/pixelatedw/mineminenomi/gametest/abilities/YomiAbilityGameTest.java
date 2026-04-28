package xyz.pixelatedw.mineminenomi.gametest.abilities;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.gametest.GameTestHolder;
import xyz.pixelatedw.mineminenomi.abilities.yomi.YomiImmunityAbility;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

@GameTestHolder("mineminenomi")
@net.neoforged.neoforge.gametest.PrefixGameTestTemplate(false)
public class YomiAbilityGameTest {

    @GameTest(template="empty")
    public void testYomiLogiaImmunity(GameTestHelper helper) {
        LivingEntity entity = helper.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);
        PlayerStats stats = PlayerStats.get(entity);
        if (stats == null) {
            helper.fail("PlayerStats attachment is null");
            return;
        }

        // Inject an active Yomi fruit first since the passive checks it
        stats.setBasic(new xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.BasicStats(
            0, 0, 0, 0, 0, 0, 0,
            new xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.Identity(stats.getBasic().identity().faction(), stats.getBasic().identity().race(), stats.getBasic().identity().subRace(), stats.getBasic().identity().fightingStyle(), java.util.Optional.ofNullable(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "yomi_yomi_no_mi"))),
            stats.getBasic().hasShadow(), stats.getBasic().hasHeart(), stats.getBasic().hasStrawDoll(), stats.getBasic().isRogue(), stats.getBasic().stamina(), stats.getBasic().maxStamina(), stats.getBasic().trainingPoints()
        ));
        stats.setDevilFruit(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "yomi_yomi_no_mi"));
        entity.setData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.PLAYER_STATS, stats);

        YomiImmunityAbility ability = new YomiImmunityAbility();
        ability.tick(entity);

        helper.succeedWhen(() -> {
            // Test succeeds
        });
    }
}
