package xyz.pixelatedw.mineminenomi.items;

import com.google.common.base.Strings;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.events.entity.CrewEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.FactionHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFoods;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class SakeCupItem extends Item {
   public SakeCupItem() {
      super((new Item.Properties()).m_41487_(1).m_41489_(ModFoods.ALCOHOL));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      if (this.getLeader(player.m_21205_(), world) != null) {
         player.m_6672_(hand);
      } else {
         int slot = WyHelper.getIndexOfItemStack((Item)ModItems.SAKE_BOTTLE.get(), player.m_150109_());
         if (slot >= 0) {
            ItemStack stack = player.m_150109_().m_8020_(slot);
            stack.m_41622_(1, player, (user) -> user.m_21166_(EquipmentSlot.MAINHAND));
            this.setLeader(player.m_21205_(), player);
            return InteractionResultHolder.m_19090_(itemstack);
         }
      }

      return InteractionResultHolder.m_19100_(itemstack);
   }

   public ItemStack m_5922_(ItemStack itemStack, Level world, LivingEntity entity) {
      if (!world.f_46443_ && entity instanceof Player player) {
         Player leader = this.getLeader(itemStack, player.m_9236_());
         if (leader != null) {
            FactionsWorldData worldProps = FactionsWorldData.get();
            Crew crew = worldProps.getCrewWithCaptain(leader.m_20148_());
            if (crew == null) {
               ModMain.LOGGER.debug("Cannot find a crew for captain " + leader.m_7755_().getString());
            } else {
               CrewEvent.Join event = new CrewEvent.Join(player, crew);
               if (!MinecraftForge.EVENT_BUS.post(event) && !crew.hasMember(player.m_20148_())) {
                  worldProps.addCrewMember(crew, player);
                  FactionHelper.sendMessageToCrew(world, crew, Component.m_237110_(ModI18n.CREW_MESSAGE_NEW_JOIN, new Object[]{player.m_7755_().getString()}));
                  Optional<IEntityStats> lazyStats = EntityStatsCapability.get(entity);
                  lazyStats.ifPresent((props) -> props.setFaction((Faction)ModFactions.PIRATE.get()));
                  ModNetwork.sendToAllTrackingAndSelf(new SSyncEntityStatsPacket(player, lazyStats), player);
                  itemStack.m_41784_().m_128405_("leader", 0);
               }
            }
         }

         player.m_150109_().m_36054_(new ItemStack((ItemLike)ModItems.SAKE_CUP.get()));
         itemStack.m_41774_(1);
      }

      return itemStack;
   }

   public void setLeader(ItemStack itemStack, Player leader) {
      itemStack.m_41751_(new CompoundTag());
      itemStack.m_41783_().m_128359_("leader", leader.m_20148_().toString());
   }

   public Player getLeader(ItemStack itemStack, Level world) {
      if (!itemStack.m_41782_()) {
         itemStack.m_41751_(new CompoundTag());
      }

      String leaderUUID = itemStack.m_41783_().m_128461_("leader");
      return !Strings.isNullOrEmpty(leaderUUID) ? world.m_46003_(UUID.fromString(leaderUUID)) : null;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.DRINK;
   }
}
