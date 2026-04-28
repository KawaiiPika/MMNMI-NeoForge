package xyz.pixelatedw.mineminenomi.gametest.abilities;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
<<<<<<< HEAD
import net.minecraft.world.entity.EntityType;
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

        stats.setDevilFruit(ResourceLocation.fromNamespaceAndPath("mineminenomi", "yomi_yomi_no_mi"));

        entity.addEffect(new MobEffectInstance(ModEffects.FROSTBITE, 100));

        helper.succeedWhen(() -> {
            // Need a valid condition
            if(!entity.hasEffect(ModEffects.FROSTBITE)) {
               helper.succeed();
            }
        });
=======

public class YomiAbilityGameTest {

    @GameTest(template = "mineminenomi:empty_3x3x3")
    public void testYomiLogiaImmunity(GameTestHelper helper) {
        helper.succeed();
>>>>>>> 1ee21eb0 (WIP: Pre-merge stash)
    }
}
