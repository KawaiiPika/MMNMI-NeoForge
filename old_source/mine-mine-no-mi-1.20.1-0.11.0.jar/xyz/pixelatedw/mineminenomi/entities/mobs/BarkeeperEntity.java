package xyz.pixelatedw.mineminenomi.entities.mobs;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.RandomWalkingAroundHomeGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SelfHealEatGoal;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues.SOpenBarkeeperDialogueScreenPacket;

public class BarkeeperEntity extends OPEntity {
   public static final int BASE_RUM_PRICE = 100;
   public static final int BASE_RUMOR_PRICE = 1000;
   public int previousHash = 0;

   public BarkeeperEntity(EntityType<BarkeeperEntity> type, Level world) {
      super(type, world, MobsHelper.BARKEEPER_TEXTURES);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new OpenDoorGoal(this, false));
      this.f_21345_.m_25352_(3, (new RandomWalkingAroundHomeGoal(this, (double)0.5F)).setWalkDistance(2).setWalkOffset(2));
      this.f_21345_.m_25352_(4, new SelfHealEatGoal(this));
      this.f_21345_.m_25352_(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(5, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(0, new HurtByTargetGoal(this, new Class[0]));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)10.0F).m_22268_(Attributes.f_22279_, 0.3).m_22268_(Attributes.f_22281_, (double)1.0F).m_22268_(Attributes.f_22276_, (double)20.0F).m_22268_(Attributes.f_22284_, (double)0.0F);
   }

   protected InteractionResult m_6071_(Player player, InteractionHand hand) {
      if (hand != InteractionHand.MAIN_HAND) {
         return InteractionResult.FAIL;
      } else if (!player.m_9236_().f_46443_) {
         ModNetwork.sendTo(new SOpenBarkeeperDialogueScreenPacket(player, this), player);
         return InteractionResult.PASS;
      } else {
         return InteractionResult.PASS;
      }
   }
}
