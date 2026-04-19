package xyz.pixelatedw.mineminenomi.abilities.doa;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KaitenDoorAbility extends PunchAbility {
   private static final int COOLDOWN = 200;
   public static final RegistryObject<AbilityCore<KaitenDoorAbility>> INSTANCE = ModRegistry.registerAbility("kaiten_door", "Kaiten Door", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Turn the head of your opponent into a rotating door to confuse and disorient them.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, KaitenDoorAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });

   public KaitenDoorAbility(AbilityCore<KaitenDoorAbility> core) {
      super(core);
      this.clearUseChecks();
   }

   public float getPunchDamage() {
      return 4.0F;
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      target.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 100, 1, false, false));
      target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DOOR_HEAD.get(), 100, 0, false, false));
      this.continuousComponent.stopContinuity(entity);
      return true;
   }

   public int getUseLimit() {
      return 1;
   }

   public float getPunchCooldown() {
      return 200.0F;
   }
}
