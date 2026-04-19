package xyz.pixelatedw.mineminenomi.abilities.artofweather;

import java.awt.Color;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.WeatherBallKind;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;

public class ArtOfWeatherHelper {
   public static final Color AOW_LIGHTNING_COLOR = new Color(253, 208, 35, 205);
   public static final String WEATHER_BALL_CHARGE_TAG = "WeatherCharges";

   public static String getChargesString(ItemStack itemStack) {
      CompoundTag nbt = itemStack.m_41784_();
      byte[] charge = nbt.m_128441_("WeatherCharges") ? itemStack.m_41784_().m_128463_("WeatherCharges") : new byte[3];
      String[] tempoColors = new String[charge.length];

      for(int i = 0; i < charge.length; ++i) {
         byte b = charge[i];
         WeatherBallKind kind = WeatherBallKind.from(b);
         if (kind == null) {
            tempoColors[i] = "§r";
         } else {
            String var10002;
            switch (kind) {
               case COOL -> var10002 = "§b";
               case HEAT -> var10002 = "§4";
               case THUNDER -> var10002 = "§e";
               default -> var10002 = "§r";
            }

            tempoColors[i] = var10002;
         }
      }

      StringBuilder sb = new StringBuilder();
      int maxCharges = charge.length;

      for(int i = 0; i < maxCharges; ++i) {
         sb.append(String.format("%s█§r", tempoColors[i]));
         if (i < maxCharges - 1) {
            sb.append("━");
         }
      }

      return sb.toString();
   }

   public static WeatherBallKind[] getCharges(ItemStack itemStack) {
      byte[] charge = itemStack.m_41784_().m_128463_("WeatherCharges");
      WeatherBallKind[] tempo = new WeatherBallKind[charge.length];

      for(int i = 0; i < charge.length; ++i) {
         byte b = charge[i];
         if (b == 0) {
            return new WeatherBallKind[0];
         }

         tempo[i] = WeatherBallKind.from(b);
      }

      return tempo;
   }

   public static boolean chargeWeatherBall(ItemStack itemStack, WeatherBallKind ball) {
      if (!ItemsHelper.isClimaTact(itemStack)) {
         return false;
      } else {
         CompoundTag nbt = itemStack.m_41784_();
         if (!nbt.m_128441_("WeatherCharges")) {
            nbt.m_128382_("WeatherCharges", new byte[3]);
         }

         byte[] charge = nbt.m_128463_("WeatherCharges");

         for(int i = 0; i < 3; ++i) {
            if (charge[i] == 0) {
               charge[i] = ball.getKind();
               break;
            }
         }

         nbt.m_128382_("WeatherCharges", charge);
         return true;
      }
   }

   public static void emptyCharges(ItemStack itemStack) {
      CompoundTag nbt = itemStack.m_41784_();
      nbt.m_128382_("WeatherCharges", new byte[3]);
   }
}
