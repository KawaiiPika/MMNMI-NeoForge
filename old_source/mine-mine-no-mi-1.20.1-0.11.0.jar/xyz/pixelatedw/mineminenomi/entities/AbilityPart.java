package xyz.pixelatedw.mineminenomi.entities;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.entity.PartEntity;
import xyz.pixelatedw.mineminenomi.api.entities.IPartType;
import xyz.pixelatedw.mineminenomi.api.entities.PartEntityType;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class AbilityPart extends PartEntity<LivingEntity> implements IPartType {
   private EntityDimensions size;

   public AbilityPart(LivingEntity parent) {
      super(parent);
   }

   public AbilityPart(LivingEntity parent, float width, float height) {
      super(parent);
      this.size = EntityDimensions.m_20395_(width, height);
      super.m_6210_();
   }

   public PartEntityType<? extends PartEntity<?>, ? extends Entity> getPartType() {
      return (PartEntityType)ModEntities.ABILITY_PART.get();
   }

   protected void m_8097_() {
   }

   protected void m_7378_(CompoundTag tag) {
   }

   protected void m_7380_(CompoundTag tag) {
   }

   public boolean m_6087_() {
      return true;
   }

   @Nullable
   public ItemStack m_142340_() {
      return ((LivingEntity)super.getParent()).m_142340_();
   }

   public boolean m_6469_(DamageSource source, float amount) {
      return ((LivingEntity)super.getParent()).m_6469_(source, amount);
   }

   public boolean m_7306_(Entity entity) {
      return this == entity || super.getParent() == entity;
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      throw new UnsupportedOperationException();
   }

   public EntityDimensions m_6972_(Pose pose) {
      return this.size;
   }

   public CompoundTag serializeNBT() {
      CompoundTag tag = super.serializeNBT();
      tag.m_128350_("width", this.size.f_20377_);
      tag.m_128350_("height", this.size.f_20378_);
      return tag;
   }

   public void deserializeNBT(CompoundTag tag) {
      super.deserializeNBT(tag);
      float width = tag.m_128457_("width");
      float height = tag.m_128457_("height");
      this.size = EntityDimensions.m_20395_(width, height);
      super.m_6210_();
   }
}
