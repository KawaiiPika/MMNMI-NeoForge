package xyz.pixelatedw.mineminenomi.events;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.abilities.brawler.DamageAbsorptionAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.SamehadaShoteiAbility;

@EventBusSubscriber(modid = "mineminenomi")
public class CombatEvents {

    @SubscribeEvent
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        PlayerStats stats = PlayerStats.get(entity);

        if (stats != null) {
            // Check Damage Absorption
            if (stats.isAbilityActive("mineminenomi:damage_absorption")) {
                DamageAbsorptionAbility ability = (DamageAbsorptionAbility) ModAbilities.DAMAGE_ABSORPTION.get();
                if (ability != null && ability.isUsing(entity)) {
                    // Completely absorb the damage
                    event.setCanceled(true);
                    return;
                }
            }

            // Check Samehada Shotei
            if (stats.isAbilityActive("mineminenomi:samehada_shotei")) {
                SamehadaShoteiAbility ability = (SamehadaShoteiAbility) ModAbilities.SAMEHADA_SHOTEI.get();
                if (ability != null && ability.isUsing(entity)) {
                    // Half damage
                    float originalDamage = event.getAmount();
                    event.getContainer().setNewDamage(originalDamage * 0.5F);

                    if (event.getSource().getEntity() instanceof LivingEntity attacker) {
                        ability.counterAttack(entity, attacker, originalDamage);
                    }
                    return;
                }
            }

            // Check Tekkai (from previous batches, but let's be careful to not break anything, just a placeholder if needed)
            // if (stats.isAbilityActive("mineminenomi:tekkai")) { ... }
        }
    }
}
