package xyz.pixelatedw.mineminenomi.abilities.beta;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class BetaCoatingAbility extends Ability {
   private static final int HOLD_TIME = 800;
   private static final int MIN_COOLDOWN = 100;
   private static final int MAX_COOLDOWN = 400;
   public static final RegistryObject<AbilityCore<BetaCoatingAbility>> INSTANCE = ModRegistry.registerAbility("beta_coating", "Beta Coating", (id, name) -> {
      Component[] DESCRIPTION = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Covers the user in a thick mucus coat, which makes them immune to almost all attacks, but extremely vulnerable to fire.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BetaCoatingAbility::new)).addDescriptionLine(DESCRIPTION).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F, 400.0F), ContinuousComponent.getTooltip(800.0F)).build("mineminenomi");
   });
   private static final List<ResourceKey<DamageType>> EXPLOSIVE_SOURCES;
   private static final List<SourceElement> EXPLOSIVE_ELEMENTS;
   private static final AbilityAttributeModifier SPEED_MULTIPLIER;
   private static final AbilityOverlay OVERLAY;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final SkinOverlayComponent skinOverlayComponent;
   private final DamageTakenComponent damageTakenComponent;
   private final ChangeStatsComponent changeStatsComponent;
   private final ExplosionComponent explosionComponent;
   private boolean exploded;

   public BetaCoatingAbility(AbilityCore<BetaCoatingAbility> core) {
      super(core);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::damageTakenEvent);
      this.changeStatsComponent = new ChangeStatsComponent(this);
      this.explosionComponent = new ExplosionComponent(this);
      this.exploded = false;
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.skinOverlayComponent, this.damageTakenComponent, this.changeStatsComponent, this.explosionComponent});
      this.changeStatsComponent.addAttributeModifier(Attributes.f_22279_, SPEED_MULTIPLIER);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 800.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.exploded = false;
      this.skinOverlayComponent.showAll(entity);
      this.changeStatsComponent.applyModifiers(entity);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.getContinueTime() % 10.0F == 0.0F) {
         this.checkIfOnFire(entity);
      }

      this.climbingMovement(entity);
   }

   private void climbingMovement(LivingEntity entity) {
      boolean isNearBlock = false;
      AABB bb = entity.m_20191_().m_82377_((double)0.25F, (double)0.25F, (double)0.25F);
      int mX = Mth.m_14107_(bb.f_82288_);
      int mY = Mth.m_14107_(bb.f_82289_);
      int mZ = Mth.m_14107_(bb.f_82290_);
      BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

      for(int y2 = mY; (double)y2 < bb.f_82292_; ++y2) {
         for(int x2 = mX; (double)x2 < bb.f_82291_; ++x2) {
            for(int z2 = mZ; (double)z2 < bb.f_82293_; ++z2) {
               mutpos.m_122178_(x2, y2, z2);
               if (Block.m_49863_(entity.m_9236_(), mutpos, Direction.UP)) {
                  isNearBlock = true;
                  break;
               }
            }
         }
      }

      if (entity.f_19862_ && !entity.f_19863_ || entity.m_6047_() && isNearBlock) {
         double climbSpeed = Math.min(0.1, entity.m_20154_().f_82480_ * (double)0.5F);
         if (entity.m_6047_()) {
            AbilityHelper.setDeltaMovement(entity, entity.m_20184_().f_82479_, (double)0.0F, entity.m_20184_().f_82481_);
         } else {
            AbilityHelper.setDeltaMovement(entity, entity.m_20184_().f_82479_, climbSpeed, entity.m_20184_().f_82481_);
         }

         entity.f_19789_ = 0.0F;
      }

   }

   private void checkIfOnFire(LivingEntity entity) {
      boolean hasFireAbilityActive = false;
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props != null) {
         for(IAbility abl : props.getEquippedAbilities()) {
            if (abl.getCore().getSourceElement().equals(SourceElement.FIRE) || abl.getCore().getSourceElement().equals(SourceElement.MAGMA)) {
               boolean isActive = (Boolean)abl.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).map((comp) -> comp.isContinuous()).orElse(false);
               if (isActive) {
                  hasFireAbilityActive = true;
                  break;
               }

               isActive = (Boolean)abl.getComponent((AbilityComponentKey)ModAbilityComponents.CHARGE.get()).map((comp) -> comp.isCharging()).orElse(false);
               if (isActive) {
                  hasFireAbilityActive = true;
                  break;
               }
            }
         }
      }

      if (entity.m_6060_() || hasFireAbilityActive) {
         this.exploded = true;
         this.continuousComponent.stopContinuity(entity);
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.hideAll(entity);
      this.changeStatsComponent.removeModifiers(entity);
      float bonus = 0.0F;
      if (this.exploded) {
         bonus = 600.0F;
         entity.m_20095_();
         AbilityExplosion explosion = this.explosionComponent.createExplosion(entity, entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), 6.0F);
         explosion.setExplosionSound(true);
         explosion.setDamageOwner(true);
         explosion.setDestroyBlocks(true);
         explosion.setFireAfterExplosion(true);
         explosion.setStaticDamage(100.0F);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION6);
         explosion.setDamageEntities(true);
         explosion.m_46061_();
      }

      float cooldown = Math.min(100.0F, this.continuousComponent.getContinueTime() / 2.0F) + bonus;
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   private float damageTakenEvent(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      if (!this.isContinuous()) {
         return damage;
      } else {
         IDamageSourceHandler sourceHandler = IDamageSourceHandler.getHandler(damageSource);
         if (!sourceHandler.isUnavoidable() && !damageSource.m_276093_(DamageTypes.f_268515_) && !damageSource.m_276093_(DamageTypes.f_268530_)) {
            for(SourceElement element : EXPLOSIVE_ELEMENTS) {
               if (sourceHandler.hasElement(element)) {
                  this.exploded = true;
                  this.continuousComponent.stopContinuity(entity);
                  return damage;
               }
            }

            for(ResourceKey<DamageType> damageType : EXPLOSIVE_SOURCES) {
               if (damageSource.m_276093_(damageType)) {
                  this.exploded = true;
                  this.continuousComponent.stopContinuity(entity);
                  return damage;
               }
            }

            if (damageSource.m_7640_() instanceof LivingEntity && damageSource.m_7640_().m_6060_()) {
               return damage;
            } else {
               WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.BETA_COATING.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
               return 0.0F;
            }
         } else {
            return damage;
         }
      }
   }

   static {
      EXPLOSIVE_SOURCES = Arrays.asList(DamageTypes.f_268565_, DamageTypes.f_268631_, DamageTypes.f_268450_, DamageTypes.f_268468_, DamageTypes.f_268546_);
      EXPLOSIVE_ELEMENTS = Arrays.asList(SourceElement.FIRE, SourceElement.LIGHTNING, SourceElement.EXPLOSION);
      SPEED_MULTIPLIER = new AbilityAttributeModifier(UUID.fromString("efa08cbd-57e5-478f-b15c-6295eb1b375e"), INSTANCE, "Beta Speed Modifier", (double)-0.25F, Operation.MULTIPLY_TOTAL);
      OVERLAY = (new AbilityOverlay.Builder()).setTexture(ModResources.BETA_COATING).setColor(WyHelper.hexToRGB("#FFFFFFA6")).build();
   }
}
