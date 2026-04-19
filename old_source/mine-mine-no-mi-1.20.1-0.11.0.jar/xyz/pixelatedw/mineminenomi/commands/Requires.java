package xyz.pixelatedw.mineminenomi.commands;

import java.util.function.Predicate;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.permission.PermissionAPI;
import net.minecraftforge.server.permission.nodes.PermissionDynamicContext;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;

public class Requires {
   public static Predicate<CommandSourceStack> hasPermission(PermissionNode<Boolean> perm) {
      return (source) -> {
         if (perm == null) {
            return false;
         } else {
            CommandSource patt610$temp = source.f_81288_;
            if (patt610$temp instanceof ServerPlayer) {
               ServerPlayer player = (ServerPlayer)patt610$temp;
               return !ServerConfig.hasPermissionsEnabled() ? (Boolean)perm.getDefaultResolver().resolve(player, player.m_20148_(), new PermissionDynamicContext[0]) : (Boolean)PermissionAPI.getPermission(player, perm, new PermissionDynamicContext[0]);
            } else {
               return source.m_6761_(2);
            }
         }
      };
   }

   public static Predicate<CommandSourceStack> hasEitherPermission(PermissionNode<Boolean>... perms) {
      return (source) -> {
         if (perms == null) {
            return false;
         } else {
            CommandSource patt1135$temp = source.f_81288_;
            if (patt1135$temp instanceof ServerPlayer) {
               ServerPlayer player = (ServerPlayer)patt1135$temp;
               if (!ServerConfig.hasPermissionsEnabled()) {
                  for(PermissionNode<Boolean> node : perms) {
                     if ((Boolean)node.getDefaultResolver().resolve(player, player.m_20148_(), new PermissionDynamicContext[0])) {
                        return true;
                     }
                  }

                  return false;
               } else {
                  for(PermissionNode<Boolean> node : perms) {
                     if ((Boolean)node.getDefaultResolver().resolve(player, player.m_20148_(), new PermissionDynamicContext[0])) {
                        boolean result = (Boolean)PermissionAPI.getPermission(player, node, new PermissionDynamicContext[0]);
                        if (result) {
                           return true;
                        }
                     }
                  }

                  return false;
               }
            } else {
               return source.m_6761_(2);
            }
         }
      };
   }
}
