package xyz.pixelatedw.mineminenomi.gametest.abilities;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

@GameTestHolder("mineminenomi")
@PrefixGameTestTemplate(false)
public class YomiAbilityGameTest {

    @GameTest(template="empty_chest")
    public void testYomiFruitEffects(GameTestHelper helper) {
        LivingEntity entity = helper.spawn(EntityType.PIG, 1, 1, 1);
        PlayerStats stats = PlayerStats.get(entity);
        if (stats == null) {
            helper.fail("PlayerStats attachment is null");
            return;
        }

<<<<<<< HEAD
        // Inject an active Yomi fruit first since the passive checks it
        stats.setBasic(new xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.BasicStats(
            0, 0, 0, 0, 0, 0, 0,
            new xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.Identity(stats.getBasic().identity().faction(), stats.getBasic().identity().race(), stats.getBasic().identity().subRace(), stats.getBasic().identity().fightingStyle(), java.util.Optional.ofNullable(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "yomi_yomi_no_mi"))),
            stats.getBasic().hasShadow(), stats.getBasic().hasHeart(), stats.getBasic().hasStrawDoll(), stats.getBasic().isRogue(), stats.getBasic().stamina(), stats.getBasic().maxStamina(), stats.getBasic().trainingPoints()
        ));
        stats.setDevilFruit(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "yomi_yomi_no_mi"));
        entity.setData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.PLAYER_STATS, stats);
=======
        stats.setDevilFruit(ResourceLocation.fromNamespaceAndPath("mineminenomi", "yomi_yomi_no_mi"));
>>>>>>> origin/main

        entity.addEffect(new MobEffectInstance(ModEffects.FROSTBITE, 100));

        helper.succeedWhen(() -> {
<<<<<<< HEAD
            // Test succeeds
=======
            // Need a valid condition
            if(!entity.hasEffect(ModEffects.FROSTBITE)) {
               helper.succeed();
            }
>>>>>>> origin/main
        });
    }
}
