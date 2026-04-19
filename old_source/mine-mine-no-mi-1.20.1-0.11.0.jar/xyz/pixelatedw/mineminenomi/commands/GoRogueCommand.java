package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;

public class GoRogueCommand {
   public static LiteralArgumentBuilder<CommandSourceStack> create() {
      LiteralArgumentBuilder<CommandSourceStack> builder = Commands.m_82127_("gorogue");
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)builder.then(((RequiredArgumentBuilder)Commands.m_82129_("target", EntityArgument.m_91466_()).requires((source) -> source.m_6761_(2))).executes((ctx) -> goRogue(ctx, EntityArgument.m_91474_(ctx, "target"))))).requires((source) -> source.m_6761_(0))).executes((ctx) -> goRogue(ctx, ((CommandSourceStack)ctx.getSource()).m_81375_()));
      return builder;
   }

   private static int goRogue(CommandContext<CommandSourceStack> context, ServerPlayer player) {
      EntityStatsCapability.get(player).ifPresent((props) -> {
         boolean rogue = props.isRogue();
         props.setRogue(!rogue);
         Component message = Component.m_237113_("§2Rogue mode " + (rogue ? "disabled" : "enabled") + " for " + player.m_5446_().getString() + "§r");
         player.m_5661_(message, false);
         if (!((CommandSourceStack)context.getSource()).m_81373_().equals(player)) {
            ((CommandSourceStack)context.getSource()).m_288197_(() -> message, false);
         }

      });
      return 1;
   }
}
