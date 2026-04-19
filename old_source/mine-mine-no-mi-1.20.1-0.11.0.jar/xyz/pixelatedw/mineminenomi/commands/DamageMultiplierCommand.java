package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nCommands;

public class DamageMultiplierCommand {
   public static LiteralArgumentBuilder<CommandSourceStack> create() {
      LiteralArgumentBuilder<CommandSourceStack> builder = (LiteralArgumentBuilder)Commands.m_82127_("damagem").requires((source) -> source.m_6761_(2));
      builder.then(Commands.m_82129_("multiplier", DoubleArgumentType.doubleArg((double)0.0F, (double)10.0F)).then(Commands.m_82129_("target", EntityArgument.m_91466_()).executes((context) -> applyMultiplier(context, DoubleArgumentType.getDouble(context, "multiplier"), EntityArgument.m_91474_(context, "target")))));
      return builder;
   }

   private static int applyMultiplier(CommandContext<CommandSourceStack> context, double multiplier, ServerPlayer player) {
      EntityStatsCapability.get(player).ifPresent((props) -> {
         props.setDamageMultiplier(multiplier);
         Component messsage = Component.m_237110_(ModI18nCommands.DAMAGE_MULTIPLIER_SET, new Object[]{props.getDamageMultiplier(), player.m_5446_().getString()});
         ((CommandSourceStack)context.getSource()).m_288197_(() -> messsage, true);
      });
      return 1;
   }
}
