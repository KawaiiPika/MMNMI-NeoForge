package xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.WeatherBallKind;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.data.entity.quest.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quest.QuestCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class HeatEggTempo extends ChargedTempoAbility {
   private static final int HOLD_TIME = 300;
   public static final RegistryObject<AbilityCore<HeatEggTempo>> INSTANCE = ModRegistry.registerAbility("heat_egg_tempo", "Heat Egg Tempo", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Used to imbue the Clima Tact with Heat Balls, while active it doubles the physical damage dealt by the clima tact", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, AbilityType.PASSIVE, HeatEggTempo::new)).setIcon(ModResources.TEMPO_ICON).addDescriptionLine(CHARGED_TEMPO_DESCRIPTION).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, ContinuousComponent.getTooltip(300.0F)).setUnlockCheck(HeatEggTempo::canUnlock).build("mineminenomi");
   });
   private static final AttributeModifier DAMAGE_MODIFIER;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(100, this::startContinuousEvent).addTickEvent(100, this::tickContinuousEvent).addEndEvent(100, this::endContinuousEvent);
   private final ChangeStatsComponent changeStatsComponent = new ChangeStatsComponent(this);
   private ItemStack climaTactRef;

   public HeatEggTempo(AbilityCore<HeatEggTempo> core) {
      super(core);
      this.climaTactRef = ItemStack.f_41583_;
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.changeStatsComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresClimaTact);
   }

   public WeatherBallKind[] getTempoOrder() {
      return new WeatherBallKind[]{WeatherBallKind.HEAT, WeatherBallKind.HEAT, WeatherBallKind.HEAT};
   }

   public void useTempo(LivingEntity entity) {
      this.continuousComponent.startContinuity(entity);
   }

   private void startContinuousEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.climaTactRef = entity.m_21205_();
         this.changeStatsComponent.addAttributeModifier(Attributes.f_22281_, DAMAGE_MODIFIER);
         this.changeStatsComponent.applyModifiers(entity);
         entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.HEAT_EGG_TEMPO.get(), 300, 0));
      }
   }

   private void tickContinuousEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.getContinueTime() >= 300.0F) {
         ItemStack mainHand = entity.m_21205_();
         if (!this.climaTactRef.m_41619_() && mainHand.equals(this.climaTactRef)) {
            this.continuousComponent.stopContinuity(entity);
         } else if (!mainHand.m_41619_()) {
            this.climaTactRef = entity.m_21205_();
         }
      }

   }

   private void endContinuousEvent(LivingEntity entity, IAbility ability) {
      this.changeStatsComponent.removeModifiers(entity);
      this.climaTactRef = ItemStack.f_41583_;
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

   static {
      DAMAGE_MODIFIER = new AttributeModifier(UUID.fromString("87c23069-f8b5-43df-96fc-e9fcffd6efb8"), "Heat Egg Bonus Modifier", (double)2.0F, Operation.MULTIPLY_BASE);
   }
}
