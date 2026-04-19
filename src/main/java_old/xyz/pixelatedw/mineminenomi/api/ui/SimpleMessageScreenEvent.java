package xyz.pixelatedw.mineminenomi.api.ui;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.INBTSerializable;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ui.acks.SSimpleMessageScreenEventPacket;

public class SimpleMessageScreenEvent implements INBTSerializable<CompoundTag> {
   private int id;
   private Component message;
   private boolean isFlashing;
   private int timeVisible;

   public SimpleMessageScreenEvent() {
      this(0);
   }

   public SimpleMessageScreenEvent(int id) {
      this.isFlashing = false;
      this.timeVisible = 100;
      this.id = id;
   }

   public int getId() {
      return this.id;
   }

   public SimpleMessageScreenEvent setMessage(Component message) {
      this.message = message;
      return this;
   }

   public Component getMessage() {
      return this.message;
   }

   public SimpleMessageScreenEvent setFlash() {
      this.isFlashing = true;
      return this;
   }

   public boolean isFlashing() {
      return this.isFlashing;
   }

   public SimpleMessageScreenEvent setTimeVisible(int ticks) {
      this.timeVisible = ticks;
      return this;
   }

   public int getTimeVisible() {
      return this.timeVisible;
   }

   public void sendEvent(ServerPlayer player) {
      ModNetwork.sendTo(new SSimpleMessageScreenEventPacket(this), player);
   }

   public CompoundTag serializeNBT() {
      CompoundTag tag = new CompoundTag();
      String json = Serializer.m_130703_(this.message);
      tag.m_128405_("id", this.id);
      tag.m_128359_("message", json);
      tag.m_128379_("isFlashing", this.isFlashing);
      tag.m_128405_("timeVisible", this.timeVisible);
      return tag;
   }

   public void deserializeNBT(CompoundTag nbt) {
      String json = nbt.m_128461_("message");
      this.id = nbt.m_128451_("id");
      this.message = Serializer.m_130701_(json);
      this.isFlashing = nbt.m_128471_("isFlashing");
      this.timeVisible = nbt.m_128451_("timeVisible");
   }
}
