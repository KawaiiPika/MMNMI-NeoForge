package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import xyz.pixelatedw.mineminenomi.api.events.ability.AbilityUseEvent;
import xyz.pixelatedw.mineminenomi.entities.clouds.WeatherCloudEntity;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public abstract class TempoAbility extends PassiveAbility {
   public static AbilityDescriptionLine.IDescriptionLine getTooltip(boolean requiresCloud, int amount, WeatherBallKind kind) {
      return (e, a) -> {
         MutableComponent tooltip = Component.m_237110_(ModI18nAbilities.DESCRIPTION_TEMPO_SAME_TYPE_COMBINATION, new Object[]{amount, kind.toString()});
         if (requiresCloud) {
            tooltip.m_130946_("\n");
            tooltip.m_7220_(ModI18nAbilities.DESCRIPTION_TEMPO_REQUIRES_WEATHER_CLOUD);
         }

         return tooltip;
      };
   }

   public static AbilityDescriptionLine.IDescriptionLine getTooltip(boolean requiresCloud, WeatherBallKind... balls) {
      return (e, a) -> {
         WeatherBallKind previousKind = null;
         boolean sameType = true;

         for(WeatherBallKind kind : balls) {
            if (previousKind == null) {
               previousKind = kind;
            } else if (!kind.equals(previousKind)) {
               sameType = false;
               break;
            }
         }

         MutableComponent tooltip = null;
         if (sameType) {
            tooltip = Component.m_237110_(ModI18nAbilities.DESCRIPTION_TEMPO_SAME_TYPE_COMBINATION, new Object[]{balls.length, balls[0].toString()});
         }

         if (balls.length == 2) {
            tooltip = Component.m_237110_(ModI18nAbilities.DESCRIPTION_TEMPO_COMBINATION_2, new Object[]{balls[0].toString(), balls[1].toString()});
         } else if (balls.length == 3) {
            tooltip = Component.m_237110_(ModI18nAbilities.DESCRIPTION_TEMPO_COMBINATION_3, new Object[]{balls[0].toString(), balls[1].toString(), balls[2].toString()});
         }

         if (tooltip != null && requiresCloud) {
            tooltip.m_130946_("\n");
            tooltip.m_7220_(ModI18nAbilities.DESCRIPTION_TEMPO_REQUIRES_WEATHER_CLOUD);
         }

         return tooltip;
      };
   }

   public TempoAbility(AbilityCore<? extends TempoAbility> core) {
      super(core);
   }

   public void use(LivingEntity entity) {
      AbilityUseEvent pre = new AbilityUseEvent.Pre(entity, this);
      if (!MinecraftForge.EVENT_BUS.post(pre)) {
         this.useTempo(entity);
         AbilityUseEvent post = new AbilityUseEvent.Post(entity, this);
         MinecraftForge.EVENT_BUS.post(post);
      }
   }

   public void useTempo(LivingEntity entity) {
   }

   public boolean isUnderWeatherCloud(WeatherCloudEntity cloud, LivingEntity target) {
      Vec3 cloudPos = new Vec3(cloud.m_20185_(), target.m_20186_(), cloud.m_20189_());
      return Math.sqrt(target.m_20238_(cloudPos)) < (double)(cloud.m_6972_(Pose.STANDING).f_20377_ / 2.0F);
   }
}
