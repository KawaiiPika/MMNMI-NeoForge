package xyz.pixelatedw.mineminenomi.entities.ai.goals.pacifista;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

// Represents a simplified fallback without using ProjectileAbilityWrapperGoal which might not be ported
public class PacifistaRadicalBeamGoal extends TickedGoal<Mob> {

    public PacifistaRadicalBeamGoal(Mob entity) {
        super(entity);
    }

    @Override
    public boolean canUse() {
        return GoalHelper.hasAliveTarget(this.entity);
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void start() {
        super.start();
        // Fallback for missing animation comps
    }

    @Override
    public void tick() {
        super.tick();
        this.entity.addEffect(new MobEffectInstance(ModEffects.MOVEMENT_BLOCKED, 10, 0));
        LivingEntity target = this.entity.getTarget();
        if (target != null) {
            GoalHelper.lookAtEntity(this.entity, target);
        }
    }
}
