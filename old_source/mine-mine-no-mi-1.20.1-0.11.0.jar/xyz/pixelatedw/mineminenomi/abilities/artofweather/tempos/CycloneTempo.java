package xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.WeatherBallKind;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.data.entity.quest.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quest.QuestCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class CycloneTempo extends ChargedTempoAbility {
   private static final float RANGE = 6.0F;
   private static final int HOLD_TIME = 200;
   public static final RegistryObject<AbilityCore<CycloneTempo>> INSTANCE = ModRegistry.registerAbility("cyclone_tempo", "Cyclone Tempo", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Used to create a small cyclone surrounding the user, pushes enemies away from the user while also providing a small protection against physical damage", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, AbilityType.PASSIVE, CycloneTempo::new)).setIcon(ModResources.TEMPO_ICON).addDescriptionLine(CHARGED_TEMPO_DESCRIPTION).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, ContinuousComponent.getTooltip(200.0F), RangeComponent.getTooltip(6.0F, RangeComponent.RangeType.AOE)).setUnlockCheck(CycloneTempo::canUnlock).build("mineminenomi");
   });
   private final RangeComponent rangeComponent = new RangeComponent(this);

   public CycloneTempo(AbilityCore<CycloneTempo> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.rangeComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresClimaTact);
   }

   public WeatherBallKind[] getTempoOrder() {
      return new WeatherBallKind[]{WeatherBallKind.COOL, WeatherBallKind.COOL, WeatherBallKind.COOL};
   }

   public void useTempo(LivingEntity entity) {
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.CYCLONE_TEMPO.get(), 200, 0));
   }

   private static boolean canUnlock(LivingEntity entity) {
      if (entity instanceof Player player) {
         IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
         IQuestData questProps = (IQuestData)QuestCapability.get(player).orElse((Object)null);
         return props != null && questProps != null ? props.isWeatherWizard() : false;
      } else {
         return false;
      }
   }
}
