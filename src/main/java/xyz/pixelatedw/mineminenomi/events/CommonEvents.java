package xyz.pixelatedw.mineminenomi.events;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.HakiHelper;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;

import java.util.ArrayList;

@EventBusSubscriber(modid = ModMain.PROJECT_ID)
public class CommonEvents {

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (event.getLevel() instanceof ServerLevel level) {
            FactionsWorldData data = FactionsWorldData.get();
            if (data != null) {
                data.tick(level);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            PlayerStats stats = PlayerStats.get(entity);
            if (stats != null) {
                for (String abilityId : stats.getActiveAbilities()) {
                    Ability ability = ModAbilities.REGISTRY.get(ResourceLocation.parse(abilityId));
                    if (ability != null) {
                        ability.tick(entity);
                    }
                }
                HakiHelper.tickHaki(entity);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        PlayerStats targetStats = PlayerStats.get(target);
        var source = event.getSource();
        var attacker = source.getEntity();

        // Attacker Logic
        if (attacker instanceof LivingEntity livingAttacker) {
            PlayerStats attackerStats = PlayerStats.get(livingAttacker);
            if (attackerStats != null) {
                // Ability damage hooks
                for (String abilityId : attackerStats.getActiveAbilities()) {
                    Ability ability = ModAbilities.REGISTRY.get(ResourceLocation.parse(abilityId));
                    if (ability != null) {
                        float modifiedAmount = ability.onAttack(livingAttacker, target, source, event.getAmount());
                        event.setAmount(modifiedAmount);
                        if (modifiedAmount <= 0) {
                            event.setCanceled(true);
                            return;
                        }
                    }
                }

                // Magma Coating boost
                if (attackerStats.isAbilityActive("mineminenomi:magma_coating")) {
                    event.setAmount(event.getAmount() + 10.0F);
                    target.setRemainingFireTicks(100);
                }

                // Swordsman Damage Perk
                if (attackerStats.isAbilityActive("mineminenomi:swordsman_damage") && xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper.isSword(livingAttacker.getMainHandItem())) {
                    event.setAmount(event.getAmount() * 1.2F);
                }

                // Black Leg Damage Perk
                if (attackerStats.isAbilityActive("mineminenomi:black_leg_damage") && livingAttacker.getMainHandItem().isEmpty()) {
                    event.setAmount(event.getAmount() * 1.2F);
                }

                // Sniper Accuracy Perk
                if (attackerStats.isAbilityActive("mineminenomi:sniper_accuracy") && source.is(net.minecraft.tags.DamageTypeTags.IS_PROJECTILE)) {
                    event.setAmount(event.getAmount() * 1.2F);
                }

                // Brawler Damage Perk
                if (attackerStats.isAbilityActive("mineminenomi:brawler_damage") && livingAttacker.getMainHandItem().isEmpty()) {
                    event.setAmount(event.getAmount() * 1.2F);
                }

                // Haki damage boost
                float newAmount = HakiHelper.calculateHakiDamage(livingAttacker, target, event.getAmount());
                event.setAmount(newAmount);
            }
        }

        // Target Logic
        if (targetStats != null) {
            // Active abilities onHurt hook
            for (String abilityId : targetStats.getActiveAbilities()) {
                Ability ability = ModAbilities.REGISTRY.get(ResourceLocation.parse(abilityId));
                if (ability != null) {
                    float newAmount = ability.onHurt(target, source, event.getAmount());
                    event.setAmount(newAmount);
                    if (newAmount <= 0) {
                        event.setCanceled(true);
                        return;
                    }
                }
            }

            // Kenbunshoku Haki dodge
            if (targetStats.isKenbunshokuActive()) {
                float dodgeChance = targetStats.isAbilityActive("mineminenomi:kenbunshoku_haki_future_sight") ? 0.8f : 0.5f;
                if (target.getRandom().nextFloat() < dodgeChance) {
                    event.setCanceled(true);
                    targetStats.setKenbunshokuHakiExp(targetStats.getKenbunshokuHakiExp() + 0.1f);
                    targetStats.sync(target);
                    
                    if (target.level() instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.CRIT, target.getX(), target.getY() + 1, target.getZ(), 10, 0.5, 0.5, 0.5, 0.1);
                        serverLevel.playSound(null, target.getX(), target.getY(), target.getZ(), 
                            xyz.pixelatedw.mineminenomi.init.ModSounds.DODGE_1.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                    }
                    
                    if (target instanceof Player player) {
                        player.displayClientMessage(Component.literal("DODGED!"), true);
                    }
                    return;
                }
            }

            // Logia Intangibility
            if (targetStats.isLogia() && !target.hasEffect(xyz.pixelatedw.mineminenomi.init.ModEffects.HANDCUFFED_KAIROSEKI)) {
                boolean bypass = false;
                if (attacker instanceof LivingEntity livingAttacker) {
                    PlayerStats attackerStats = PlayerStats.get(livingAttacker);
                    if (attackerStats != null && attackerStats.isBusoshokuActive()) {
                        bypass = true;
                    }
                }

                if (!bypass) {
                    event.setCanceled(true);
                    if (target.level() instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, target.getX(), target.getY() + 1, target.getZ(), 10, 0.2, 0.5, 0.2, 0.05);
                        serverLevel.playSound(null, target.getX(), target.getY(), target.getZ(), 
                            SoundEvents.FIRE_EXTINGUISH, SoundSource.NEUTRAL, 0.5F, 1.2F);
                    }
                    return;
                }
            }
        }
    }
}
