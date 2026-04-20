package xyz.pixelatedw.mineminenomi.api.helpers;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class HakiHelper {
    
    public static float calculateHakiDamage(LivingEntity attacker, LivingEntity target, float originalAmount) {
        PlayerStats stats = PlayerStats.get(attacker);
        if (stats == null || !stats.isBusoshokuActive()) return originalAmount;
        
        double boost = 1.0 + (stats.getBusoshokuHakiExp() / 5000.0);
        
        // Imbuing boost
        if (stats.isAbilityActive("mineminenomi:busoshoku_haki_imbuing")) {
            boost += 0.5;
        }
        
        // Conqueror's Infusion boost (Premium WOW effect)
        if (stats.isAbilityActive("mineminenomi:haoshoku_haki_infusion")) {
            boost += 1.5;
            if (target.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.REVERSE_PORTAL, target.getX(), target.getY() + 1, target.getZ(), 20, 0.3, 0.5, 0.3, 0.1);
                serverLevel.sendParticles(ParticleTypes.ENCHANTED_HIT, target.getX(), target.getY() + 1, target.getZ(), 15, 0.5, 0.5, 0.5, 0.2);
            }
        }

        // Gain exp
        stats.setBusoshokuHakiExp(stats.getBusoshokuHakiExp() + 0.1f);
        stats.sync(attacker);

        return (float) (originalAmount * boost);
    }

    public static boolean isWeakenedByHaoshoku(LivingEntity user, LivingEntity target) {
        PlayerStats userStats = PlayerStats.get(user);
        PlayerStats targetStats = PlayerStats.get(target);
        
        if (userStats == null) return false;
        
        double userPower = userStats.getDoriki(); // Using doriki as a base metric
        double targetPower = targetStats != null ? targetStats.getDoriki() : target.getMaxHealth() * 10;
        
        // Target is weakened if user's power is significantly higher
        return userPower > targetPower * 1.5;
    }

    public static boolean isHaoshokuBorn(net.minecraft.world.entity.player.Player player) {
        PlayerStats stats = PlayerStats.get(player);
        if (stats != null && stats.getBasic().isHaoshokuBornOverride()) {
            return true;
        }

        String[] bits = ("" + player.getUUID().getMostSignificantBits()).split("");
        int sum = 0;
        for (String bit : bits) {
            if (!bit.equals("-")) {
                try { sum += Integer.parseInt(bit); } catch (Exception ignored) {}
            }
        }
        // Deterministic sum-based check for approx 1/1000 rarity or similar
        // Legacy: sum & 10 <= 1 (approx 20% chance if uniform)
        // Let's make it actually rare like the anime: 1 in 1 million is too much, 1 in 100 is good.
        return (sum % 100) == 0; 
    }

    public static int getHaoshokuColour(LivingEntity entity) {
        // Deterministic color based on UUID if not set
        long seed = entity.getUUID().getMostSignificantBits();
        java.util.Random rand = new java.util.Random(seed);
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);
        return (r << 16) | (g << 8) | b;
    }
    
    public static boolean canEnableHaki(LivingEntity entity) {
        return true;
    }

    public static void tickHaki(LivingEntity entity) {
        if (entity.level().isClientSide) return;
        
        PlayerStats stats = PlayerStats.get(entity);
        if (stats == null) return;

        boolean changed = false;
        boolean hakiActive = stats.isBusoshokuActive() || stats.isKenbunshokuActive();
        
        if (hakiActive) {
            double drain = 0.5;
            if (stats.getStamina() > 0) {
                stats.setStamina(Math.max(0, stats.getStamina() - drain));
                changed = true;
            } else {
                stats.setBusoshokuActive(false);
                stats.setKenbunshokuActive(false);
                changed = true;
                if (entity instanceof net.minecraft.world.entity.player.Player player) {
                    player.displayClientMessage(net.minecraft.network.chat.Component.translatable("haki.overuse"), true);
                }
            }
        } else {
            if (stats.getStamina() < stats.getMaxStamina()) {
                stats.setStamina(Math.min(stats.getMaxStamina(), stats.getStamina() + 0.2));
                changed = true;
            }
        }

        if (changed) stats.sync(entity);
    }
}
