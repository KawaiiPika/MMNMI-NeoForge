package xyz.pixelatedw.mineminenomi.api;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import xyz.pixelatedw.mineminenomi.commands.FGCommand;

public class WyDebug {
   public static final Marker ABILITY_LOG = MarkerManager.getMarker("ABILITY_LOG");

   public static boolean isDebug() {
      return Boolean.getBoolean("piwdebug");
   }

   public static void debug(Object message) {
      debug((Marker)null, message);
   }

   public static void debug(@Nullable Marker marker, Object message) {
      if (FGCommand.SHOW_DEBUG_IN_CHAT) {
         Minecraft.m_91087_().f_91074_.m_213846_(Component.m_237113_(String.valueOf(message)));
      } else {
         if (marker == null) {
            LogManager.getLogger(getCallerClassName()).debug(message);
         } else {
            LogManager.getLogger(getCallerClassName()).debug(marker, message);
         }

      }
   }

   public static void info(Object message) {
      LogManager.getLogger(getCallerClassName()).info(message);
   }

   public static void warn(Object message) {
      LogManager.getLogger(getCallerClassName()).warn(message);
   }

   public static void error(Object message) {
      LogManager.getLogger(getCallerClassName()).error(message);
   }

   public static void error(Object message, Throwable t) {
      LogManager.getLogger(getCallerClassName()).error(message, t);
   }

   public static String getCallerClassName() {
      StackTraceElement[] stElements = Thread.currentThread().getStackTrace();

      for(int i = 1; i < stElements.length; ++i) {
         StackTraceElement ste = stElements[i];
         if (!ste.getClassName().equals(WyDebug.class.getName()) && ste.getClassName().indexOf("java.lang.Thread") != 0) {
            String var10000 = ste.getClassName();
            return var10000 + ":" + ste.getLineNumber();
         }
      }

      return null;
   }

   public static void printCurrentStackTrace() {
      try {
         throw new RuntimeException();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
