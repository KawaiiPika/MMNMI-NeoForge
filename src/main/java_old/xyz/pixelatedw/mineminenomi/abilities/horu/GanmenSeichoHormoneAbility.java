package xyz.pixelatedw.mineminenomi.abilities.horu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GanmenSeichoHormoneAbility extends PunchAbility {
   private static final int COOLDOWN = 400;
   public static final RegistryObject<AbilityCore<GanmenSeichoHormoneAbility>> INSTANCE = ModRegistry.registerAbility("ganmen_seicho_hormone", "Ganmen Seicho Hormone", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("By injecting special growth hormones into a target, their head expands to enormous proportions.", (Object)null), ImmutablePair.of("§aSHIFT-USE§r: User injects themselves boosting the §aDeath Wink§r ability as well", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GanmenSeichoHormoneAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(400.0F), ContinuousComponent.getTooltip()).setSourceType(SourceType.FIST, SourceType.FRIENDLY).build("mineminenomi");
   });
   private final MorphComponent morphComponent = new MorphComponent(this);

   public GanmenSeichoHormoneAbility(AbilityCore<GanmenSeichoHormoneAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.morphComponent});
      this.addTickEvent(this::tickEvent);
      this.continuousComponent.addStartEvent(100, this::startContinuityEvent);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity.m_6047_()) {
         entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.GANMEN_SEICHO_HORMONE.get(), 300, 0));
         this.morphComponent.startMorph(entity, (MorphInfo)ModMorphs.GANMEN_SEICHO.get());
         this.continuousComponent.stopContinuity(entity);
      }

   }

   private void tickEvent(LivingEntity entity, IAbility ability) {
      if (this.morphComponent.isMorphed()) {
         IDevilFruit fruitProps = (IDevilFruit)DevilFruitCapability.get(entity).orElse((Object)null);
         if (fruitProps == null) {
            return;
         }

         if (!entity.m_21023_((MobEffect)ModEffects.GANMEN_SEICHO_HORMONE.get())) {
            this.morphComponent.stopMorph(entity);
         }
      }

   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.GANMEN_SEICHO_HORMONE.get(), 300, 0));
      this.morphComponent.startMorph(target, (MorphInfo)ModMorphs.GANMEN_SEICHO.get());
      return true;
   }

   public int getUseLimit() {
      return 1;
   }

   public float getPunchCooldown() {
      return 400.0F;
   }
}
