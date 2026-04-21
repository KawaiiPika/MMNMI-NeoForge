package xyz.pixelatedw.mineminenomi.entities.mobs;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.ITrainer;

public abstract class TrainerEntity extends OPEntity implements ITrainer {

    public TrainerEntity(EntityType<? extends TrainerEntity> type, Level world) {
        super(type, world);
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.FAIL;
        }

        if (!player.level().isClientSide) {
            // TODO: Port SOpenTrainerDialogueScreenPacket and send it here
            // ModNetwork.sendTo(new SOpenTrainerDialogueScreenPacket(player, this), player);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.SUCCESS;
    }
}
