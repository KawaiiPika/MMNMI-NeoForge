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
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.HakiHelper;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;

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
                boolean hasKairoseki = entity.hasEffect(xyz.pixelatedw.mineminenomi.init.ModEffects.HANDCUFFED_KAIROSEKI);
                // Iterating directly over getActiveAbilities() is safe here because PlayerStats uses a
                // copy-on-write pattern for its lists. Any modification (like toggling an ability)
                // will replace the list reference in PlayerStats rather than modifying the list being iterated.
                for (String abilityId : stats.getActiveAbilities()) {
                    Ability ability = ModAbilities.REGISTRY.get(ResourceLocation.parse(abilityId));
                    if (ability != null) {
                        if (hasKairoseki && ability.getRequiredFruit() != null) {
                            continue;
                        }
                        ability.tick(entity);
                    }
                }
                HakiHelper.tickHaki(entity);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityInvulnerabilityCheck(net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent event) {
        if (!(event.getEntity() instanceof LivingEntity target)) return;
        PlayerStats targetStats = PlayerStats.get(target);
        if (targetStats == null) return;
        boolean targetHasKairoseki = target.hasEffect(xyz.pixelatedw.mineminenomi.init.ModEffects.HANDCUFFED_KAIROSEKI);

        var source = event.getSource();
        var attacker = source.getEntity();

        for (String abilityId : targetStats.getActiveAbilities()) {
            Ability ability = ModAbilities.REGISTRY.get(ResourceLocation.parse(abilityId));
            if (ability != null) {
                if (targetHasKairoseki && ability.getRequiredFruit() != null) {
                    continue;
                }
                if (ability.checkInvulnerability(target, source)) {
                    event.setInvulnerable(true);
                }
            }
        }

        if (targetStats.isLogia() && !targetHasKairoseki) {
            boolean bypass = false;
            if (attacker instanceof LivingEntity livingAttacker) {
                PlayerStats attackerStats = PlayerStats.get(livingAttacker);
                if (attackerStats != null && attackerStats.isBusoshokuActive()) {
                    bypass = true;
                }
            }

            if (!bypass) {
                event.setInvulnerable(true);
                if (target.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, target.getX(), target.getY() + 1, target.getZ(), 10, 0.2, 0.5, 0.2, 0.05);
                    serverLevel.playSound(null, target.getX(), target.getY(), target.getZ(),
                        SoundEvents.FIRE_EXTINGUISH, SoundSource.NEUTRAL, 0.5F, 1.2F);
                }

                if (attacker instanceof LivingEntity livingAttacker) {
                    for (String abilityId : targetStats.getActiveAbilities()) {
                        Ability ability = ModAbilities.REGISTRY.get(ResourceLocation.parse(abilityId));
                        if (ability != null) {
                            ability.onLogiaDodge(target, livingAttacker);
                        }
                    }
                }
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
                boolean attackerHasKairoseki = livingAttacker.hasEffect(xyz.pixelatedw.mineminenomi.init.ModEffects.HANDCUFFED_KAIROSEKI);
                // Ability damage hooks
                // Iterating directly over getActiveAbilities() is safe here because PlayerStats uses a
                // copy-on-write pattern for its lists.
                for (String abilityId : attackerStats.getActiveAbilities()) {
                    Ability ability = ModAbilities.REGISTRY.get(ResourceLocation.parse(abilityId));
                    if (ability != null) {
                        if (attackerHasKairoseki && ability.getRequiredFruit() != null) {
                            continue;
                        }
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
            boolean targetHasKairoseki = target.hasEffect(xyz.pixelatedw.mineminenomi.init.ModEffects.HANDCUFFED_KAIROSEKI);

            // Soap Defense
            if (!targetHasKairoseki && targetStats.isAbilityActive("mineminenomi:soap_defense")) {
                Ability ability = ModAbilities.REGISTRY.get(ResourceLocation.parse("mineminenomi:soap_defense"));
                if (ability != null && ability.isUsing(target)) {
                    event.setAmount(event.getAmount() * 0.2F);
                }
            }

            // Cyborg Body
            if (targetStats.isAbilityActive("mineminenomi:cyborg_body")) {
                event.setAmount(event.getAmount() * 0.9F);
            }

            // Mantra dodge chance
            if (targetStats.isAbilityActive("mineminenomi:mantra")) {
                Ability ability = ModAbilities.REGISTRY.get(ResourceLocation.parse("mineminenomi:mantra"));
                if (ability != null && ability.isUsing(target)) {
                    if (target.getRandom().nextFloat() < 0.2F) { // 20% dodge chance
                        if (!target.level().isClientSide) {
                            xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.sendAbilityMessage(target, Component.translatable("ability.mineminenomi.mantra.dodge"));
                        }
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

        }
    }

    @SubscribeEvent
    public static void onLivingDamagePost(LivingDamageEvent.Post event) {
        LivingEntity target = event.getEntity();
        PlayerStats targetStats = PlayerStats.get(target);
        var source = event.getSource();
        var attacker = source.getEntity();

        boolean targetHasKairoseki = target.hasEffect(xyz.pixelatedw.mineminenomi.init.ModEffects.HANDCUFFED_KAIROSEKI);

        if (target instanceof xyz.pixelatedw.mineminenomi.api.ILivingEntityExtension extension) {
            extension.onDamageTake(source, event.getNewDamage());
        }

        if (targetStats != null && !targetHasKairoseki) {
            for (String abilityId : targetStats.getActiveAbilities()) {
                Ability ability = xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY.get(net.minecraft.resources.ResourceLocation.parse(abilityId));
                if (ability != null) {
                    ability.onDamageTaken(target, source);
                }
            }
            for (String abilityId : targetStats.getCombat().equippedAbilities()) {
                Ability ability = xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY.get(net.minecraft.resources.ResourceLocation.parse(abilityId));
                if (ability != null) {
                    ability.onDamageTaken(target, source);
                }
            }
        }

        // Attacker Logic
        if (attacker instanceof LivingEntity livingAttacker) {
            PlayerStats attackerStats = PlayerStats.get(livingAttacker);
            if (attackerStats != null) {
                boolean attackerHasKairoseki = livingAttacker.hasEffect(xyz.pixelatedw.mineminenomi.init.ModEffects.HANDCUFFED_KAIROSEKI);

                // Iterate abilities
                for (String abilityId : attackerStats.getActiveAbilities()) {
                    Ability ability = ModAbilities.REGISTRY.get(ResourceLocation.parse(abilityId));
                    if (ability != null) {
                        if (attackerHasKairoseki && ability.getRequiredFruit() != null) {
                            continue;
                        }
                        ability.onDamageTakenByTarget(livingAttacker, target, source);
                    }
                }

                // Magma Coating
                if (attackerStats.isAbilityActive("mineminenomi:magma_coating")) {
                    boolean skip = attackerHasKairoseki;
                    if (!skip) {
                        target.setRemainingFireTicks(100);
                    }
                }

                // Haki EXP
                HakiHelper.onHakiDamageTaken(livingAttacker);
            }
        }

        // Target Logic
        if (targetStats != null) {
            for (String abilityId : targetStats.getActiveAbilities()) {
                Ability ability = ModAbilities.REGISTRY.get(ResourceLocation.parse(abilityId));
                if (ability != null) {
                    if (targetHasKairoseki && ability.getRequiredFruit() != null) {
                        continue;
                    }
                    ability.onDamageTake(target, source, event.getNewDamage());
                    ability.onDamageTaken(target, source);
                }
            }
        }
    }
}
