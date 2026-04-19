package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IHitEntityObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class HitEntityObjective extends Objective implements IHitEntityObjective {
    protected static final ICheckHit DEFAULT_CHECK = (player, target, source, amount) -> true;
    private ICheckHit hitCheck;

    public HitEntityObjective(Quest parent, Component title, int count) {
        this(parent, title, count, DEFAULT_CHECK);
    }

    public HitEntityObjective(Quest parent, Component title, int count, EntityType<?> entityType) {
        this(parent, title, count, (player, target, source, amount) -> target.getType() == entityType);
    }

    public HitEntityObjective(Quest parent, Component title, int count, ICheckHit check) {
        super(parent, title);
        this.setMaxProgress((float) count);
        this.hitCheck = check;
    }

    @Override
    public boolean checkHit(Player player, LivingEntity target, DamageSource source, float amount) {
        return this.hitCheck.test(player, target, source, amount);
    }

    @FunctionalInterface
    public interface ICheckHit {
        boolean test(Player player, LivingEntity target, DamageSource source, float amount);

        default ICheckHit and(ICheckHit check) {
            return (player, target, source, amount) -> this.test(player, target, source, amount) && check.test(player, target, source, amount);
        }

        default ICheckHit or(ICheckHit check) {
            return (player, target, source, amount) -> this.test(player, target, source, amount) || check.test(player, target, source, amount);
        }
    }
}
