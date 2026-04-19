package xyz.pixelatedw.mineminenomi.abilities.gasu;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.effects.BaseEffect;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.gasu.KoroParticleEffect;

public class KoroAbility extends Ability {
   private static final TargetPredicate TARGETS_PREDICATE = (new TargetPredicate()).testFriendlyFaction();
   private static final int COOLDOWN = 400;
   private static final float RANGE = 30.0F;
   public static final RegistryObject<AbilityCore<KoroAbility>> INSTANCE = ModRegistry.registerAbility("koro", "Koro", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Removes all poison from self and nearby allies.", (Object)null), ImmutablePair.of("If used while %s is active, it'll remove all harmful effects for self and nearby allies.", new Object[]{ShinokuniAbility.INSTANCE}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, KoroAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(400.0F), RangeComponent.getTooltip(30.0F, RangeComponent.RangeType.AOE)).build("mineminenomi");
   });
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private KoroParticleEffect.Details particleDetails = new KoroParticleEffect.Details();

   public KoroAbility(AbilityCore<KoroAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.rangeComponent});
      this.addUseEvent(this::useEvent);
   }

   public void useEvent(LivingEntity player, IAbility ability) {
      List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(player, 30.0F, TARGETS_PREDICATE);
      targets.add(player);
      boolean hasShinokuniActive = (Boolean)AbilityCapability.get(player).map((props) -> (ShinokuniAbility)props.getEquippedAbility((AbilityCore)ShinokuniAbility.INSTANCE.get())).map((abl) -> abl != null && abl.isContinuous()).orElse(false);

      for(LivingEntity target : targets) {
         if (hasShinokuniActive) {
            for(MobEffectInstance inst : target.m_21220_()) {
               MobEffect var10 = inst.m_19544_();
               if (var10 instanceof BaseEffect) {
                  BaseEffect baseEffect = (BaseEffect)var10;
                  if (!baseEffect.isRemoveable()) {
                     continue;
                  }
               }

               if (inst.m_19544_().m_19483_() == MobEffectCategory.HARMFUL && target.m_21195_(inst.m_19544_())) {
                  this.particleDetails.setEffect(inst);
                  WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.KORO.get(), player, target.m_20185_(), target.m_20186_(), target.m_20189_(), this.particleDetails);
               }
            }
         } else if (target.m_21023_(MobEffects.f_19614_) && target.m_21195_(MobEffects.f_19614_)) {
            this.particleDetails.setEffect(target.m_21124_(MobEffects.f_19614_));
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.KORO.get(), player, target.m_20185_(), target.m_20186_(), target.m_20189_(), this.particleDetails);
         }
      }

      this.cooldownComponent.startCooldown(player, 400.0F);
   }
}
