package xyz.pixelatedw.mineminenomi.entities.mobs;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.entities.ITrainer;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SelfHealEatGoal;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues.SOpenTrainerDialogueScreenPacket;

public abstract class TrainerEntity extends OPEntity implements ITrainer {
   private static final float SPAWNER_DESPAWN_DISTANCE = 40000.0F;

   public TrainerEntity(EntityType<? extends TrainerEntity> type, Level world) {
      this(type, world, (ResourceLocation[])null);
   }

   public TrainerEntity(EntityType<? extends TrainerEntity> type, Level world, ResourceLocation[] textures) {
      super(type, world, textures);
   }

   public void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(0, new JumpOutOfHoleGoal(this));
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(3, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.f_21345_.m_25352_(4, new SelfHealEatGoal(this));
      this.f_21345_.m_25352_(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(5, new RandomLookAroundGoal(this));
   }

   public boolean m_6785_(double distance) {
      if (this.isSpawnedViaSpawner() && distance < (double)40000.0F) {
         return false;
      } else {
         return !this.m_8077_() || ServerConfig.getDespawnWithNametag();
      }
   }

   public void m_8119_() {
      if (!this.m_9236_().f_46443_ && this.m_9236_().m_46791_() == Difficulty.PEACEFUL && !NuWorld.isChallengeDimension(this.m_9236_())) {
         this.m_6710_((LivingEntity)null);
         this.m_6703_((LivingEntity)null);
         this.m_21335_((Entity)null);
      }

      super.m_8119_();
   }

   protected InteractionResult m_6071_(Player player, InteractionHand hand) {
      if (hand != InteractionHand.MAIN_HAND) {
         return InteractionResult.FAIL;
      } else {
         ItemStack stack = player.m_21120_(hand);
         if (!stack.m_41619_() && stack.m_41720_() == Items.f_42656_) {
            return InteractionResult.FAIL;
         } else if (!player.m_9236_().f_46443_) {
            ModNetwork.sendTo(new SOpenTrainerDialogueScreenPacket(player, this), player);
            return InteractionResult.PASS;
         } else {
            return InteractionResult.PASS;
         }
      }
   }
}
