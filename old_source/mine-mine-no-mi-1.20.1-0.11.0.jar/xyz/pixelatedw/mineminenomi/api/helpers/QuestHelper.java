package xyz.pixelatedw.mineminenomi.api.helpers;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class QuestHelper {
   public static boolean removeQuestItem(Player player, Item item, int amount) {
      int id = WyHelper.getIndexOfItemStack(item, player.m_150109_());
      if (id < 0) {
         player.m_213846_(Component.m_237110_(ModI18n.MESSAGE_MISSING_QUEST_ITEMS, new Object[]{item.m_41466_().toString()}));
         return false;
      } else {
         player.m_150109_().m_8020_(id).m_41774_(amount);
         return true;
      }
   }
}
