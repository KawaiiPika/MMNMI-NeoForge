package xyz.pixelatedw.mineminenomi.abilities.hiso;

import java.util.List;
import java.util.Objects;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.level.GameRules;
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
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.mixins.IServerLevelMixin;

public class ForewarnAbility extends Ability {
   public static final TargetPredicate TARGETS_PREDICATE;
   private static final Component MESSAGE_UNCHANGING_WEATHER;
   private static final String MESSAGE_NEXT_RAIN;
   private static final String MESSAGE_NEXT_CLEAR;
   private static final int COOLDOWN = 200;
   private static final int RANGE = 10;
   public static final RegistryObject<AbilityCore<ForewarnAbility>> INSTANCE;
   private final RangeComponent rangeComponent = new RangeComponent(this);

   public ForewarnAbility(AbilityCore<ForewarnAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.rangeComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      List<LivingEntity> nearby = this.rangeComponent.getTargetsInArea(entity, 10.0F, TARGETS_PREDICATE);
      if (nearby.size() <= 0) {
         this.cooldownComponent.startCooldown(entity, 200.0F);
      } else {
         if (nearby.size() > 0 && !entity.m_9236_().f_46443_) {
            LivingEntity animal = (LivingEntity)nearby.stream().findFirst().orElse((Object)null);
            if (animal == null) {
               this.cooldownComponent.startCooldown(entity, 200.0F);
               return;
            }

            Component moo = Component.m_237119_();
            if (animal instanceof Cow) {
               if (entity instanceof ServerPlayer) {
                  ServerPlayer serverPlayer = (ServerPlayer)entity;
                  ModAdvancements.MOOTEOROLOGIST.trigger(serverPlayer);
               }

               moo = ModI18n.MESSAGE_MOO;
            }

            int rainWeather = ((IServerLevelMixin)entity.m_9236_()).getServerLevelData().m_6531_();
            int clearWeather = ((IServerLevelMixin)entity.m_9236_()).getServerLevelData().m_6537_();
            boolean weatherStoppedFlag = !entity.m_20194_().m_129900_().m_46207_(GameRules.f_46150_);
            if (weatherStoppedFlag) {
               String var13 = MESSAGE_UNCHANGING_WEATHER.getString();
               WyHelper.sendMessage(entity, Component.m_237113_(var13 + " " + moo.getString()), true);
               return;
            }

            if (clearWeather == 0) {
               Component message = Component.m_237110_(MESSAGE_NEXT_RAIN, new Object[]{rainWeather / 1200});
               String var10001 = message.getString();
               WyHelper.sendMessage(entity, Component.m_237113_(var10001 + " " + moo.getString()), true);
            } else if (rainWeather == 0) {
               Component message = Component.m_237110_(MESSAGE_NEXT_CLEAR, new Object[]{clearWeather / 1200});
               String var12 = message.getString();
               WyHelper.sendMessage(entity, Component.m_237113_(var12 + " " + moo.getString()), true);
            }
         }

         this.cooldownComponent.startCooldown(entity, 200.0F);
      }
   }

   static {
      TargetPredicate var10000 = new TargetPredicate();
      Objects.requireNonNull(Animal.class);
      TARGETS_PREDICATE = var10000.selector(Animal.class::isInstance);
      MESSAGE_UNCHANGING_WEATHER = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "unchanging_weather", "The weather won't ever change it seems."));
      MESSAGE_NEXT_RAIN = ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "next_rain", "Next rain will happen in %s minutes.");
      MESSAGE_NEXT_CLEAR = ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "next_clear", "The rain should stop in %s minutes.");
      INSTANCE = ModRegistry.registerAbility("animal_forewarning", "Animal Forewarning", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user to communicate with nearby animals and learn when the next rain or clear weathers will come.", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, ForewarnAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), RangeComponent.getTooltip(10.0F, RangeComponent.RangeType.AOE)).build("mineminenomi");
      });
   }
}
