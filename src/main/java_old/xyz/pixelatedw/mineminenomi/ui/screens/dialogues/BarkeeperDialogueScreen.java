package xyz.pixelatedw.mineminenomi.ui.screens.dialogues;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.client.entity.interaction.CBarkeeperBuyRumPacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.interaction.CBarkeeperListNewsPacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.interaction.CBarkeeperListRumorsPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.DialogueScreen;

public class BarkeeperDialogueScreen extends DialogueScreen {
   public BarkeeperDialogueScreen(LivingEntity entity) {
      super(entity);
   }

   public void setupDialogue() {
      Component rumPrice = Component.m_237110_(ModI18n.GUI_BUY_RUM_COST, new Object[]{100});
      Component rumorPrice = Component.m_237110_(ModI18n.GUI_RUMORS_WITH_PRICE, new Object[]{1000});
      this.addInteraction(new DialogueScreen.InteractionAction(ModI18n.GUI_NEWS, this::listNews));
      this.addInteraction(new DialogueScreen.InteractionAction(rumorPrice, this::listRumors));
      this.addInteraction(new DialogueScreen.InteractionAction(rumPrice, this::buyRum));
      this.addInteraction(new DialogueScreen.InteractionAction(ModI18n.GUI_QUIT, (btn) -> this.m_7379_()));
   }

   private void listNews(Button btn) {
      ModNetwork.sendToServer(new CBarkeeperListNewsPacket());
   }

   private void listRumors(Button btn) {
      ModNetwork.sendToServer(new CBarkeeperListRumorsPacket(this.getTarget().m_19879_()));
   }

   private void buyRum(Button btn) {
      ModNetwork.sendToServer(new CBarkeeperBuyRumPacket());
   }
}
