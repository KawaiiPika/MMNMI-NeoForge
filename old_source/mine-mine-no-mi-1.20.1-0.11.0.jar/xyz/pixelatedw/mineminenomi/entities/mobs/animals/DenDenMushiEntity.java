package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;

public class DenDenMushiEntity extends Animal implements IEntityAdditionalSpawnData {
   private int type = 0;

   public DenDenMushiEntity(EntityType<DenDenMushiEntity> type, Level world) {
      super(type, world);
      this.f_21364_ = 0;
      if (world != null && !world.f_46443_) {
         this.type = this.f_19796_.m_188503_(MobsHelper.DEN_DEN_MUSHI_TEXTURES.length);
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22279_, (double)0.12F).m_22268_(Attributes.f_22276_, (double)5.0F);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new PanicGoal(this, (double)0.75F));
      this.f_21345_.m_25352_(2, new WaterAvoidingRandomStrollGoal(this, (double)1.0F));
      this.f_21345_.m_25352_(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21345_.m_25352_(4, new RandomLookAroundGoal(this));
   }

   public InteractionResult m_6071_(Player player, InteractionHand hand) {
      ItemStack stack = player.m_21120_(hand);
      if (!player.m_9236_().f_46443_ && hand == InteractionHand.MAIN_HAND && stack.m_41720_() == Items.f_42416_) {
         ItemStack denStack = new ItemStack((ItemLike)ModBlocks.DEN_DEN_MUSHI.get());
         denStack.m_41784_().m_128405_("type", this.type);
         player.m_36356_(denStack);
         stack.m_41774_(1);
         this.m_142687_(RemovalReason.DISCARDED);
         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.FAIL;
      }
   }

   public void m_7380_(CompoundTag nbt) {
      super.m_7380_(nbt);
      nbt.m_128405_("type", this.type);
   }

   public void m_7378_(CompoundTag nbt) {
      super.m_7378_(nbt);
      this.type = nbt.m_128451_("type");
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      buffer.writeInt(this.type);
   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      this.type = buffer.readInt();
   }

   public AgeableMob m_142606_(ServerLevel p_146743_, AgeableMob p_146744_) {
      return null;
   }

   public ResourceLocation getCurrentTexture() {
      return MobsHelper.DEN_DEN_MUSHI_TEXTURES[this.type];
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
