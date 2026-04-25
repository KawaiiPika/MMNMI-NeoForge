package xyz.pixelatedw.mineminenomi.api.entities.ai;
public abstract class TickedGoal<T extends net.minecraft.world.entity.Mob> extends net.minecraft.world.entity.ai.goal.Goal {
    public T mob;
    public TickedGoal(T mob) { this.mob = mob; }
}
