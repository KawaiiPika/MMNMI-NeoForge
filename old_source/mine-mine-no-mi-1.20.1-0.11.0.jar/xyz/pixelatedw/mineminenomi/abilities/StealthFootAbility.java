package xyz.pixelatedw.mineminenomi.abilities;

import java.util.List;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.math.VectorHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class StealthFootAbility extends Ability {
   private static final int CHARGE_TIME = 20;
   private static final int COOLDOWN = 100;
   public static final int RANGE = 10;
   private static final int DAMAGE = 20;
   public static final RegistryObject<AbilityCore<StealthFootAbility>> INSTANCE = ModRegistry.registerAbility("stealth_foot", "Stealth Foot", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, StealthFootAbility::new)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F), ChargeComponent.getTooltip(20.0F), RangeComponent.getTooltip(10.0F, RangeComponent.RangeType.AOE), DealDamageComponent.getTooltip(20.0F)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.SLASH).build("mineminenomi"));
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addEndEvent(this::endChargeEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private LivingEntity target;

   public StealthFootAbility(AbilityCore<StealthFootAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.rangeComponent, this.chargeComponent, this.dealDamageComponent, this.animationComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      if (!this.chargeComponent.isCharging()) {
         List<LivingEntity> targets = this.rangeComponent.getTargetsInLine(entity, 10.0F, 2.0F);
         if (targets.size() > 0) {
            this.target = (LivingEntity)targets.get(0);
         }

         if (this.target != null) {
            Vec3 targetLook = VectorHelper.calculateViewVectorFromBodyRot(this.target.m_146909_(), this.target.f_20883_).m_82542_((double)-2.0F, (double)0.0F, (double)-2.0F);
            Vec3 newPos = this.target.m_20182_().m_82549_(targetLook);
            entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.VANISH.get(), 10, 0, false, false));
            entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 20, 0, false, false));
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GEPPO.get(), entity, entity.m_20185_(), entity.m_20186_() + (double)0.5F, entity.m_20189_());
            entity.m_20324_(newPos.f_82479_, newPos.f_82480_, newPos.f_82481_);
            entity.m_7618_(Anchor.EYES, this.target.m_20182_().m_82520_((double)0.0F, (double)this.target.m_20192_(), (double)0.0F));
            this.chargeComponent.startCharging(entity, 20.0F);
         } else {
            this.cooldownComponent.startCooldown(entity, 100.0F);
         }

      }
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_ && this.target != null && this.target.m_6084_() && Math.abs(this.target.m_20270_(entity)) < 5.0F) {
         this.animationComponent.start(entity, ModAnimations.CROSSED_ATTACK, 7);
         entity.m_21011_(InteractionHand.MAIN_HAND, true);

         for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, this.target.m_20183_(), 2.0F)) {
            this.dealDamageComponent.hurtTarget(entity, target, 20.0F);
            WyHelper.spawnParticles(ParticleTypes.f_123766_, (ServerLevel)entity.m_9236_(), target.m_20185_(), target.m_20186_() + (double)target.m_20192_(), target.m_20189_());
         }
      }

      this.target = null;
      this.cooldownComponent.startCooldown(entity, 100.0F);
   }
}
