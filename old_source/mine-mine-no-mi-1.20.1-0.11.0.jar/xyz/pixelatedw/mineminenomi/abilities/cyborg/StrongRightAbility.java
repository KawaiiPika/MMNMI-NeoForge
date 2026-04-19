package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.StrongRightProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class StrongRightAbility extends Ability {
   private static final float COOLDOWN = 100.0F;
   public static final RegistryObject<AbilityCore<StrongRightAbility>> INSTANCE = ModRegistry.registerAbility("strong_right", "Strong Right", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user punches the opponent with an extensible short range metal fist.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, StrongRightAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setUnlockCheck(StrongRightAbility::canUnlock).build("mineminenomi");
   });
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private boolean isShot;
   private NuProjectileEntity projectile;

   public StrongRightAbility(AbilityCore<StrongRightAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.projectileComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresHands);
      this.addUseEvent(this::useEvent);
      this.addTickEvent(this::tickEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity);
      this.projectile = this.projectileComponent.getShotProjectile();
      this.isShot = true;
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.NO_HANDS.get(), 200, 0));
      this.cooldownComponent.startCooldown(entity, 100.0F);
   }

   private void tickEvent(LivingEntity entity, IAbility ability) {
      if (this.isShot && this.projectile != null && !this.projectile.m_6084_()) {
         this.projectile.m_142687_(RemovalReason.DISCARDED);
         this.projectile = null;
         entity.m_21195_((MobEffect)ModEffects.NO_HANDS.get());
         this.isShot = false;
      }

   }

   private StrongRightProjectile createProjectile(LivingEntity entity) {
      StrongRightProjectile proj = new StrongRightProjectile(entity.m_9236_(), entity, this);
      return proj;
   }

   private static boolean canUnlock(LivingEntity user) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(user).orElse((Object)null);
      return props == null ? false : props.isCyborg();
   }
}
