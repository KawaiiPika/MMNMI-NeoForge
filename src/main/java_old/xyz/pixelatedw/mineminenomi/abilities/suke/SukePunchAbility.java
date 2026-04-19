package xyz.pixelatedw.mineminenomi.abilities.suke;

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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SukePunchAbility extends PunchAbility {
   public static final RegistryObject<AbilityCore<SukePunchAbility>> INSTANCE = ModRegistry.registerAbility("suke_punch", "Suke Punch", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Turns an entity's entire body invisible after hitting them.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SukePunchAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, ContinuousComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });

   public SukePunchAbility(AbilityCore<SukePunchAbility> core) {
      super(core);
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      if (target.m_21023_((MobEffect)ModEffects.SUKE_INVISIBILITY.get())) {
         target.m_21195_((MobEffect)ModEffects.SUKE_INVISIBILITY.get());
      } else {
         target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.SUKE_INVISIBILITY.get(), 2400, 0, false, false, true));
      }

      return true;
   }

   public int getUseLimit() {
      return 1;
   }

   public float getPunchCooldown() {
      return 0.0F;
   }
}
