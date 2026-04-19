package xyz.pixelatedw.mineminenomi.handlers.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.abilities.haki.HaoshokuHakiInfusionAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.projectileextra.IProjectileExtras;
import xyz.pixelatedw.mineminenomi.data.entity.projectileextra.ProjectileExtrasCapability;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncProjectileExtrasPacket;

public class ProjectilesHandler {
   public static void setProjectileHakiData(Projectile proj, Entity owner) {
      if (owner != null && owner instanceof LivingEntity thrower) {
         IProjectileExtras projectileExtrasProps = (IProjectileExtras)ProjectileExtrasCapability.get(proj).orElse((Object)null);
         IAbilityData abilityDataProps = (IAbilityData)AbilityCapability.get(thrower).orElse((Object)null);
         if (projectileExtrasProps != null && abilityDataProps != null) {
            HaoshokuHakiInfusionAbility haoshokuHakiInfusion = (HaoshokuHakiInfusionAbility)abilityDataProps.getEquippedAbility((AbilityCore)HaoshokuHakiInfusionAbility.INSTANCE.get());
            boolean hasBusoshokuImbuingActive = HakiHelper.hasImbuingActive(thrower, false, false);
            boolean hasBusoshokuShroudingActive = HakiHelper.hasAdvancedBusoActive(thrower);
            boolean hasHaoshokuInfusionActive = haoshokuHakiInfusion != null && haoshokuHakiInfusion.isContinuous();
            projectileExtrasProps.setProjectileBusoshokuImbued(hasBusoshokuImbuingActive);
            projectileExtrasProps.setProjectileBusoshokuShrouded(hasBusoshokuShroudingActive);
            projectileExtrasProps.setProjectileHaoshokuInfused(hasHaoshokuInfusionActive);
            if (!proj.m_9236_().f_46443_ && owner instanceof Player) {
               Player player = (Player)owner;
               ModNetwork.sendToAllTrackingAndSelf(new SSyncProjectileExtrasPacket(proj), player);
            }

         }
      }
   }
}
