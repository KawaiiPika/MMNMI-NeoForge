package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.entities.clouds.MH5CloudEntity;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.items.armors.WootzArmorItem;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class MH5Ability extends Ability {
   private static final int COOLDOWN = 1200;
   private static final int CHARGE_TIME = 200;
   public static final int RANGE = 100;
   public static final RegistryObject<AbilityCore<MH5Ability>> INSTANCE = ModRegistry.registerAbility("mh5", "MH5", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Charges a deathly poison bomb causing all within its radius to die when inhaling the resulting gas.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.EQUIPMENT, MH5Ability::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(1200.0F), ChargeComponent.getTooltip(200.0F), RangeComponent.getTooltip(100.0F, RangeComponent.RangeType.AOE)).setSourceElement(SourceElement.POISON).setUnlockCheck(MH5Ability::canUnlock).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::startChargeEvent).addTickEvent(this::duringChargeEvent).addEndEvent(this::endChargeEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private BlockPos targetPosition;
   private float initialHealth;
   private double hpThreshold;

   public MH5Ability(AbilityCore<MH5Ability> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.animationComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresWootzArmor);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 200.0F);
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.MH5_CHARGING);
      this.initialHealth = entity.m_21223_();
      this.hpThreshold = WyHelper.percentage((double)20.0F, (double)entity.m_21233_());
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 200, 0));
   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      if ((double)entity.m_21223_() <= (double)this.initialHealth - this.hpThreshold) {
         this.chargeComponent.forceStopCharging(entity);
         entity.m_21195_((MobEffect)ModEffects.MOVEMENT_BLOCKED.get());
         entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DIZZY.get(), 100, 0));
         this.cooldownComponent.startCooldown(entity, 1200.0F);
      } else {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.CHARGE_MH5_BOMB.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      }
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      if (this.targetPosition == null) {
         Vec3 startVec = entity.m_20182_().m_82520_((double)0.0F, (double)entity.m_20192_(), (double)0.0F);
         Vec3 endVec = startVec.m_82549_(entity.m_20154_().m_82490_((double)128.0F));
         BlockHitResult result = entity.m_9236_().m_45547_(new ClipContext(startVec, endVec, Block.COLLIDER, Fluid.NONE, entity));
         this.targetPosition = result.m_82425_();
      }

      MH5CloudEntity cloud = new MH5CloudEntity(entity.m_9236_(), entity);
      cloud.setLife(100);
      cloud.m_20035_(this.targetPosition, 0.0F, 0.0F);
      AbilityHelper.setDeltaMovement(cloud, (double)0.0F, (double)0.0F, (double)0.0F);
      entity.m_9236_().m_7967_(cloud);
      this.cooldownComponent.startCooldown(entity, 1200.0F);
      this.targetPosition = null;
   }

   public void setTargetPosition(BlockPos targetPos) {
      this.targetPosition = targetPos;
   }

   private static boolean canUnlock(LivingEntity entity) {
      return entity instanceof Player ? false : WootzArmorItem.hasArmorEquipped(entity);
   }
}
