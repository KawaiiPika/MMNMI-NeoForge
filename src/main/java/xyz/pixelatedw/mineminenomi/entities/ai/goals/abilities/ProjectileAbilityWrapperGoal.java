package xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities;
public class ProjectileAbilityWrapperGoal<T> extends xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal<net.minecraft.world.entity.Mob> {
    public Object chargeComponent;
    public ProjectileAbilityWrapperGoal(net.minecraft.world.entity.Mob entity, Object ability) { super(entity); }
    public Object getAbility() { return null; }
    public void tickWrapper() {}
    public boolean canUse() { return false; }
}
