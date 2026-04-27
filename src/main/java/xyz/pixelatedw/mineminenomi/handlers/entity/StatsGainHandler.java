package xyz.pixelatedw.mineminenomi.handlers.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

@EventBusSubscriber(modid = "mineminenomi")
public class StatsGainHandler {

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity target = event.getEntity();
        DamageSource damageSource = event.getSource();

        if (target.level().isClientSide) return;

        if (ServerConfig.isMobRewardsEnabled()) {
            Player player = null;
            if (damageSource.getEntity() instanceof Player p) {
                player = p;
            } else if (damageSource.getDirectEntity() instanceof Player p2) {
                player = p2;
            }

            if (player != null) {
                PlayerStats props = PlayerStats.get(player);
                if (props != null) {
                    long plusBelly = 0L;
                    long plusBounty = 0L;
                    double plusDoriki = 0.0;

                    PlayerStats targetProps = target instanceof Player ? PlayerStats.get(target) : null;

                    boolean isTargetMarine = targetProps != null && targetProps.isMarine();
                    boolean isTargetBountyHunter = targetProps != null && targetProps.isBountyHunter();
                    boolean isTargetRevo = targetProps != null && targetProps.isRevolutionary();

                    double targetDoriki = targetProps != null ? targetProps.getDoriki() : (target.getMaxHealth() * 2);
                    long targetBounty = targetProps != null ? targetProps.getBounty() : 0L;
                    long targetBelly = targetProps != null ? targetProps.getBelly() : 0L;

                    if (props.isMarine()) {
                        if (!isTargetMarine && !isTargetBountyHunter) {
                            props.alterLoyalty(0.5);
                        } else {
                            props.alterLoyalty(-0.5);
                        }
                    }

                    if (target instanceof Player) {
                        double dorikiLost = targetDoriki - (targetDoriki * (ServerConfig.getDorikiKeepPercentage() / 100.0));
                        plusDoriki = dorikiLost / 4.0;
                        double bountyLost = targetBounty - (targetBounty * (ServerConfig.getBountyKeepPercentage() / 100.0));
                        plusBounty = (long) (bountyLost / 2.0);
                        double bellyLost = targetBelly - (targetBelly * (ServerConfig.getBellyKeepPercentage() / 100.0));
                        plusBelly = (long) bellyLost;
                    } else {
                        if (props.isMarine() && isTargetMarine) return;
                        if (props.isRevolutionary() && isTargetRevo) return;

                        if (targetDoriki > 0.0) {
                            plusDoriki = targetDoriki / 100.0;
                            if (props.getDoriki() > targetDoriki) {
                                plusDoriki = targetDoriki / 10000.0;
                            }

                            if (plusDoriki < 1.0 && ServerConfig.isMinimumDorikiPerKillEnabled()) {
                                plusDoriki = 1.0;
                            }

                            plusBounty = (long) targetDoriki / 20L;
                            plusBelly = targetBelly;
                        }

                        if (target instanceof Villager) {
                            plusBounty = 250L;
                        }

                        plusDoriki *= ServerConfig.getDorikiRewardMultiplier();
                        plusBounty = (long) (plusBounty * ServerConfig.getBountyRewardMultiplier());
                        plusBelly = (long) (plusBelly * ServerConfig.getBellyRewardMultiplier());
                    }

                    if (plusDoriki > 0.0) {
                        props.alterDoriki(plusDoriki);
                    }

                    if (plusBounty > 0L) {
                        props.alterBounty(plusBounty);
                    }

                    if (plusBelly > 0L) {
                        props.alterBelly(plusBelly);
                    }

                    props.sync(player);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;

        PlayerStats props = PlayerStats.get(player);
        if (props != null) {
            // Loyalty
            if (props.isMarine() || props.isPirate() || props.isBountyHunter() || props.isRevolutionary()) {
                if (props.getLoyalty() < 100.0) {
                    if (props.getDoriki() >= 4000.0 && props.getLoyalty() <= 15.0) {
                        // Do nothing
                    } else {
                        if (player.level().getGameTime() % 24000L == 0L) {
                            double loyaltyGain = 1.0 * ServerConfig.getLoyaltyMultiplier();
                            props.alterLoyalty(loyaltyGain);
                            props.sync(player);
                        }
                    }
                }
            }

            // Passive Kenbunshoku Exp
            if (props.isAbilityActive("mineminenomi:kenbunshoku_haki_future_sight") && props.getKenbunshokuHakiExp() >= 60.0F && player.tickCount % 600 == 0) {
                float finalHakiExp = (float) (0.025F * ServerConfig.getHakiExpMultiplier());
                props.alterKenbunshokuHakiExp(finalHakiExp);
                props.sync(player);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        LivingEntity target = event.getEntity();
        DamageSource damageSource = event.getSource();
        float amount = event.getNewDamage();

        if (target.level().isClientSide) return;

        Entity attackerEntity = damageSource.getEntity();
        if (attackerEntity instanceof Player player) {
            PlayerStats props = PlayerStats.get(player);
            if (props != null) {
                // Busoshoku Exp Gain logic
                boolean isHardeningDamage = false;
                boolean isImbuingDamage = false;

                if (props.isBusoshokuActive()) {
                    isHardeningDamage = true;
                }

                if (props.isAbilityActive("mineminenomi:busoshoku_haki_imbuing")) {
                    isImbuingDamage = true;
                }

                if (isHardeningDamage || isImbuingDamage) {
                    double atk = 0.0;
                    double hp = target.getMaxHealth();
                    if (target.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE) != null) {
                        atk = target.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE);
                    }
                    float expValue = (float) (atk + hp);
                    int mult = 2;
                    boolean flag = false;

                    if (expValue < props.getBusoshokuHakiExp()) {
                        mult = 10;
                    }

                    if (props.isBusoshokuActive() || props.isAbilityActive("mineminenomi:busoshoku_haki_imbuing") || props.getBusoshokuHakiExp() <= 30.0F) {
                        flag = true;
                    }

                    if (flag) {
                        float currentHaki = props.getBusoshokuHakiExp();
                        float finalExp = expValue * (ServerConfig.getHakiExpLimit() - currentHaki) / mult / 10000.0F;
                        float exp = Math.max(0.001F, finalExp);
                        float finalHakiExp = (float) (exp * ServerConfig.getHakiExpMultiplier());
                        props.alterBusoshokuHakiExp(finalHakiExp);
                        props.sync(player);
                    }
                }
            }
        }

        if (target instanceof Player targetPlayer) {
            PlayerStats targetProps = PlayerStats.get(targetPlayer);
            if (targetProps != null) {
                // Kenbunshoku On-Hit Exp
                if (targetProps.isKenbunshokuActive() || targetProps.getKenbunshokuHakiExp() <= 30.0F) {
                    float exp = amount / (20.0F + 100.0F * (targetProps.getKenbunshokuHakiExp() / 100.0F));
                    if (exp <= 0.0F) {
                        exp = 1.0E-5F;
                    }

                    float finalHakiExp = (float) (exp * ServerConfig.getHakiExpMultiplier());
                    targetProps.alterKenbunshokuHakiExp(finalHakiExp);
                    targetProps.sync(targetPlayer);
                }
            }
        }
    }
}
