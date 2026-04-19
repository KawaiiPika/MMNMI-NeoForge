package xyz.pixelatedw.mineminenomi.abilities.bara;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bara.DaiCircusProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KuchuKirimomiDaiCircusAbility extends Ability {
   private static final int COOLDOWN = 300;
   private static final int HOLD_TIME = 60;
   public static final RegistryObject<AbilityCore<KuchuKirimomiDaiCircusAbility>> INSTANCE = ModRegistry.registerAbility("kuchu_kirimomi_dai_circus", "Kuchu Kirimomi Dai Circus", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Fires both fists at an enemy and lifts them up, moving them around according to the user's movements.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, KuchuKirimomiDaiCircusAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F), ContinuousComponent.getTooltip(60.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final GrabEntityComponent grabEntityComponent = new GrabEntityComponent(this, true, true, 7.0F);
   private final MorphComponent morphComponent = new MorphComponent(this);
   private final PoolComponent poolComponent;

   public KuchuKirimomiDaiCircusAbility(AbilityCore<KuchuKirimomiDaiCircusAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.GRAB_ABILITY, new AbilityPool[0]);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.projectileComponent, this.grabEntityComponent, this.morphComponent, this.poolComponent});
      this.addCanUseCheck(BaraHelper::hasLimbs);
      this.addTickEvent(this::tickEvent);
      this.addUseEvent(this::useEvent);
   }

   private void tickEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_ && !this.continuousComponent.isContinuous() && this.morphComponent.isMorphed()) {
         NuProjectileEntity projectile = this.projectileComponent.getShotProjectile();
         if (projectile == null || !projectile.m_6084_()) {
            this.morphComponent.stopMorph(entity);
         }
      }

   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         this.continuousComponent.stopContinuity(entity);
      } else {
         this.projectileComponent.shoot(entity, 3.0F, 0.0F);
         int projectileLife = this.projectileComponent.getShotProjectile().getLife();
         this.morphComponent.startMorph(entity, (MorphInfo)ModMorphs.BARA_CIRCUS.get());
         entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.NO_HANDS.get(), projectileLife, 0));
      }
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.NO_HANDS.get(), 5, 0));
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.morphComponent.stopMorph(entity);
      this.grabEntityComponent.release(entity);
      this.cooldownComponent.startCooldown(entity, 300.0F);
   }

   private DaiCircusProjectile createProjectile(LivingEntity entity) {
      DaiCircusProjectile proj = new DaiCircusProjectile(entity.m_9236_(), entity, this);
      proj.addEntityHitEvent(100, (result) -> {
         Entity patt5086$temp = result.m_82443_();
         if (patt5086$temp instanceof LivingEntity target) {
            if (this.grabEntityComponent.grabManually(entity, target)) {
               this.continuousComponent.startContinuity(entity, 60.0F);
            }
         }

      });
      return proj;
   }
}
