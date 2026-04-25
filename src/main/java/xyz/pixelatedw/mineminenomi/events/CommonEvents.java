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

        // Check active and equipped abilities for invulnerabilities
        java.util.Set<String> abilitiesToCheck = new java.util.HashSet<>();
        abilitiesToCheck.addAll(targetStats.getActiveAbilities());
        abilitiesToCheck.addAll(targetStats.getCombat().equippedAbilities());

        for (String abilityId : abilitiesToCheck) {
            if (abilityId == null || abilityId.isEmpty()) continue;
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
                    java.util.Set<String> dodgeAbilitiesToCheck = new java.util.HashSet<>();
                    dodgeAbilitiesToCheck.addAll(targetStats.getActiveAbilities());
                    dodgeAbilitiesToCheck.addAll(targetStats.getCombat().equippedAbilities());
                    for (String abilityId : dodgeAbilitiesToCheck) {
                        if (abilityId == null || abilityId.isEmpty()) continue;
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
                java.util.Set<String> attackerAbilitiesToCheck = new java.util.HashSet<>();
                attackerAbilitiesToCheck.addAll(attackerStats.getActiveAbilities());
                attackerAbilitiesToCheck.addAll(attackerStats.getCombat().equippedAbilities());

                for (String abilityId : attackerAbilitiesToCheck) {
                    if (abilityId == null || abilityId.isEmpty()) continue;
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



        // Attacker Logic
        if (attacker instanceof LivingEntity livingAttacker) {
            PlayerStats attackerStats = PlayerStats.get(livingAttacker);
            if (attackerStats != null) {
                boolean attackerHasKairoseki = livingAttacker.hasEffect(xyz.pixelatedw.mineminenomi.init.ModEffects.HANDCUFFED_KAIROSEKI);

                // Iterate abilities
                java.util.Set<String> attackerAbilitiesToCheck = new java.util.HashSet<>();
                attackerAbilitiesToCheck.addAll(attackerStats.getActiveAbilities());
                attackerAbilitiesToCheck.addAll(attackerStats.getCombat().equippedAbilities());

                for (String abilityId : attackerAbilitiesToCheck) {
                    if (abilityId == null || abilityId.isEmpty()) continue;
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
            boolean targetHasKairosekiCheck = target.hasEffect(xyz.pixelatedw.mineminenomi.init.ModEffects.HANDCUFFED_KAIROSEKI);
            java.util.Set<String> targetAbilitiesToCheck = new java.util.HashSet<>();
            targetAbilitiesToCheck.addAll(targetStats.getActiveAbilities());
            targetAbilitiesToCheck.addAll(targetStats.getCombat().equippedAbilities());

            for (String abilityId : targetAbilitiesToCheck) {
                if (abilityId == null || abilityId.isEmpty()) continue;
                Ability ability = ModAbilities.REGISTRY.get(ResourceLocation.parse(abilityId));
                if (ability != null) {
                    if (targetHasKairosekiCheck && ability.getRequiredFruit() != null) {
                        continue;
                    }
                    ability.onDamageTake(target, source, event.getNewDamage());
                    ability.onDamageTaken(target, source);
                }
            }
        }
    }
}
