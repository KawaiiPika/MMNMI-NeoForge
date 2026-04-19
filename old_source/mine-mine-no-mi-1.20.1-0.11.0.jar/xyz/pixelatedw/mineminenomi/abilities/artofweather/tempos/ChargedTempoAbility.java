package xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos;

import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.TempoAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.WeatherBallKind;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public abstract class ChargedTempoAbility extends TempoAbility {
   public static final AbilityDescriptionLine.IDescriptionLine CHARGED_TEMPO_DESCRIPTION = (entity, ability) -> {
      String ball1 = "";
      String ball2 = "";
      String ball3 = "";
      if (ability instanceof ChargedTempoAbility tempo) {
         ball1 = tempo.getTempoOrder()[0].toString();
         ball2 = tempo.getTempoOrder()[1].toString();
         ball3 = tempo.getTempoOrder()[2].toString();
      }

      return Component.m_237110_(ModI18nAbilities.DESCRIPTION_CHARGED_TEMPO_COMBINATION, new Object[]{ball1, ball2, ball3});
   };

   public ChargedTempoAbility(AbilityCore<? extends ChargedTempoAbility> core) {
      super(core);
   }

   public abstract WeatherBallKind[] getTempoOrder();
}
