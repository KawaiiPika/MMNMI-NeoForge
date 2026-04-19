package xyz.pixelatedw.mineminenomi.blocks.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.init.ModBlockEntities;

public class PoneglyphBlockEntity extends BlockEntity {
   private Type entryType;
   private ChallengeCore<?> challenge;

   public PoneglyphBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModBlockEntities.PONEGLYPH.get(), pos, state);
      this.entryType = PoneglyphBlockEntity.Type.CHALLENGE;
   }

   public Type getEntryType() {
      return this.entryType;
   }

   public void setEntryType(Type entryType) {
      this.entryType = entryType;
   }

   public ChallengeCore<?> getChallenge() {
      return this.challenge;
   }

   public void setChallenge(ChallengeCore<?> challenge) {
      this.challenge = challenge;
   }

   public void m_183515_(CompoundTag nbt) {
      super.m_183515_(nbt);
      nbt.m_128405_("entryType", this.entryType.ordinal());
      if (this.challenge != null) {
         nbt.m_128359_("entryId", this.challenge.getRegistryKey().toString());
      }

   }

   public void m_142466_(CompoundTag nbt) {
      super.m_142466_(nbt);
      this.entryType = PoneglyphBlockEntity.Type.values()[nbt.m_128451_("entryType")];
      if (nbt.m_128441_("entryId")) {
         ResourceLocation challengeId = ResourceLocation.parse(nbt.m_128461_("entryId"));
         this.challenge = (ChallengeCore)((IForgeRegistry)WyRegistry.CHALLENGES.get()).getValue(challengeId);
      }

   }

   public CompoundTag m_5995_() {
      CompoundTag tag = super.m_5995_();
      this.m_183515_(tag);
      return tag;
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.m_195640_(this);
   }

   public static enum Type {
      CHALLENGE;

      // $FF: synthetic method
      private static Type[] $values() {
         return new Type[]{CHALLENGE};
      }
   }
}
