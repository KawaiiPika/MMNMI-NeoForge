package xyz.pixelatedw.mineminenomi.abilities.hie;

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
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class IceTimeAbility extends PunchAbility {
   private static final int COOLDOWN = 900;
   public static final RegistryObject<AbilityCore<IceTimeAbility>> INSTANCE = ModRegistry.registerAbility("ice_time", "Ice Time", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Hit the target to encase them in ice", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, IceTimeAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(900.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setSourceElement(SourceElement.ICE).build("mineminenomi");
   });

   public IceTimeAbility(AbilityCore<IceTimeAbility> core) {
      super(core);
      this.clearUseChecks();
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.FROZEN.get(), 100, 0));
      return true;
   }

   public int getUseLimit() {
      return 1;
   }

   public float getPunchCooldown() {
      return 900.0F;
   }
}
