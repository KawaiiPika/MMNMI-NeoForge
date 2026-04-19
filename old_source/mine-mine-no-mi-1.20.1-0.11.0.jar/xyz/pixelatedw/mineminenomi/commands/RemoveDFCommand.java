package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.pixelatedw.mineminenomi.abilities.FlyAbility;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.config.GeneralConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.world.OneFruitWorldData;
import xyz.pixelatedw.mineminenomi.handlers.ability.ProgressionHandler;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncDevilFruitPacket;

public class RemoveDFCommand {
   private static final Predicate<CommandSourceStack> REQUIRE = (source) -> {
      int reqPermsLevel = (Boolean)GeneralConfig.PUBLIC_REMOVEDF.get() ? 0 : 2;
      boolean hasPerms = Requires.hasEitherPermission(ModPermissions.REMOVE_DF_COMMAND, ModPermissions.REMOVE_DF_COMMAND_SELF).test(source);
      return source.m_6761_(reqPermsLevel) || hasPerms;
   };

   public static LiteralArgumentBuilder<CommandSourceStack> create() {
      LiteralArgumentBuilder<CommandSourceStack> builder = (LiteralArgumentBuilder)Commands.m_82127_("removedf").requires(REQUIRE);
      ((LiteralArgumentBuilder)builder.executes((context) -> removesDF(context, ((CommandSourceStack)context.getSource()).m_81375_()))).then(((RequiredArgumentBuilder)Commands.m_82129_("target", EntityArgument.m_91466_()).requires(Requires.hasPermission(ModPermissions.REMOVE_DF_COMMAND))).executes((context) -> removesDF(context, EntityArgument.m_91474_(context, "target"))));
      return builder;
   }

   private static int removesDF(CommandContext<CommandSourceStack> context, ServerPlayer player) throws CommandSyntaxException {
      try {
         IDevilFruit devilFruitProps = (IDevilFruit)DevilFruitCapability.get(player).orElse((Object)null);
         IAbilityData abilityDataProps = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
         if (devilFruitProps == null || abilityDataProps == null) {
            return -1;
         }

         OneFruitWorldData worldData = OneFruitWorldData.get();
         if (devilFruitProps.hasAnyDevilFruit()) {
            worldData.lostOneFruit((ResourceLocation)devilFruitProps.getDevilFruit().get(), player, "Removed via Command");
            if (devilFruitProps.hasYamiPower()) {
               worldData.lostOneFruit(((AkumaNoMiItem)ModFruits.YAMI_YAMI_NO_MI.get()).getRegistryKey(), player, "Removed via Command");
            }

            devilFruitProps.removeDevilFruit();
            devilFruitProps.clearMorphs();
            devilFruitProps.setAwakenedFruit(false);
         }

         boolean hasUnlockedFlying = abilityDataProps.getPassiveAbilities((abl) -> abl instanceof FlyAbility && !((FlyAbility)abl).isPaused()).size() > 0;
         if (hasUnlockedFlying && !player.m_7500_() && !player.m_5833_()) {
            player.m_150110_().f_35936_ = false;
            player.m_150110_().f_35935_ = false;
            player.f_8906_.m_9829_(new ClientboundPlayerAbilitiesPacket(player.m_150110_()));
         }

         abilityDataProps.clearUnlockedAbilities(AbilityCategory.DEVIL_FRUITS.isCorePartofCategory());
         abilityDataProps.clearPassiveAbilities(AbilityCategory.DEVIL_FRUITS.isAbilityPartofCategory());
         abilityDataProps.clearEquippedAbilities(AbilityCategory.DEVIL_FRUITS.isAbilityPartofCategory());
         ProgressionHandler.checkForRacialUnlocks(player);
         if (WyDebug.isDebug()) {
            player.m_21219_();
            HakiCapability.get(player).ifPresent((props) -> props.setHakiOveruse(0));
         }

         ModNetwork.sendToAllTrackingAndSelf(new SSyncDevilFruitPacket(player), player);
         ModNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket(player), player);
         ForgeRegistries.ATTRIBUTES.forEach((attr) -> {
            if (player.m_21051_(attr) != null) {
               Stream var10000 = player.m_21051_(attr).m_22122_().stream();
               Objects.requireNonNull(AbilityAttributeModifier.class);
               var10000 = var10000.filter(AbilityAttributeModifier.class::isInstance);
               Objects.requireNonNull(AbilityAttributeModifier.class);
               var10000.map(AbilityAttributeModifier.class::cast).forEach((mod) -> player.m_21051_(attr).m_22130_(mod));
            }

         });
         AbilityHelper.enableAbilities(player, (ability) -> true);
         ((CommandSourceStack)context.getSource()).m_288197_(() -> {
            String var10000 = String.valueOf(ChatFormatting.GREEN);
            return Component.m_237113_(var10000 + "Removed Devil Fruit for " + player.m_5446_().getString());
         }, true);
      } catch (Exception e) {
         e.printStackTrace();
      }

      return 1;
   }
}
