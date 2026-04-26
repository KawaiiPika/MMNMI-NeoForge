package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import javax.annotation.Nullable;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.IWithHome;

public class RandomWalkAroundHomeGoal<E extends PathfinderMob & IWithHome> extends RandomStrollGoal {
    protected E mob;
    protected final float probability;
    private int distance;
    private int offset;

    public RandomWalkAroundHomeGoal(E mob, double speed) {
        this(mob, speed, 0.001F);
        this.mob = mob;
    }

    public RandomWalkAroundHomeGoal(E mob, double speed, float chance) {
        super(mob, speed);
        this.distance = 10;
        this.offset = 7;
        this.mob = mob;
        this.probability = chance;
    }

    public RandomWalkAroundHomeGoal<E> setWalkDistance(int distance) {
        this.distance = distance;
        return this;
    }

    public RandomWalkAroundHomeGoal<E> setWalkOffset(int offset) {
        this.offset = offset;
        return this;
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        if (!((IWithHome) this.mob).getHomePosition().isPresent()) {
            return super.getPosition();
        } else {
            Vec3 homePosition = ((IWithHome) this.mob).getHomePosition().get();
            if (this.mob.isInWaterOrBubble()) {
                Vec3 vector3d = WyHelper.findValidGroundLocation(this.mob.level(), homePosition, this.distance * 2, this.offset);
                return vector3d == null ? super.getPosition() : vector3d;
            } else {
                return this.mob.getRandom().nextFloat() >= this.probability ? WyHelper.findValidGroundLocation(this.mob.level(), homePosition, this.distance, this.offset) : super.getPosition();
            }
        }
    }
}
