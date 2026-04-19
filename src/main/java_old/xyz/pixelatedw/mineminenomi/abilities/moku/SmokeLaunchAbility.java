package xyz.pixelatedw.mineminenomi.abilities.moku;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SmokeLaunchAbility extends Ability {
   private static final int COOLDOWN = 300;
   private static final int RANGE = 100;
   public static final RegistryObject<AbilityCore<SmokeLaunchAbility>> INSTANCE = ModRegistry.registerAbility("smoke_launch", "Smoke Launch", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Launches all nearby entities surrounded by smoke in the sky.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SmokeLaunchAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F), RangeComponent.getTooltip(100.0F, RangeComponent.RangeType.AOE)).setSourceHakiNature(SourceHakiNature.SPECIAL).build("mineminenomi");
   });
   private final RangeComponent rangeComponent = new RangeComponent(this);

   public SmokeLaunchAbility(AbilityCore<SmokeLaunchAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.rangeComponent});
      this.addUseEvent(this::useEvent);
   }

   public void useEvent(LivingEntity entity, IAbility ability) {
      for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 100.0F)) {
         if (target.m_21023_((MobEffect)ModEffects.SMOKE.get())) {
            target.m_21195_((MobEffect)ModEffects.SMOKE.get());
            target.m_7292_(new MobEffectInstance(MobEffects.f_19620_, 200, 1));
         }
      }

      this.cooldownComponent.startCooldown(entity, 300.0F);
   }
}
