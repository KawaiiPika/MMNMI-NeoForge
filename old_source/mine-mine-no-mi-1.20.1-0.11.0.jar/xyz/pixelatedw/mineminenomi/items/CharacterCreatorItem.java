package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenCharacterCreatorScreenPacket;

public class CharacterCreatorItem extends Item {
   public CharacterCreatorItem() {
      super((new Item.Properties()).m_41487_(1));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      if (!world.f_46443_) {
         boolean hasRandomizedRace = ServerConfig.getRaceRandomizer();
         boolean allowMinkRaceSelect = ServerConfig.getAllowSubRaceSelect();
         ModNetwork.sendTo(new SOpenCharacterCreatorScreenPacket(hasRandomizedRace, allowMinkRaceSelect), (ServerPlayer)player);
      }

      return new InteractionResultHolder(InteractionResult.SUCCESS, player.m_21120_(hand));
   }
}
