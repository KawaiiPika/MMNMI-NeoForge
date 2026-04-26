package xyz.pixelatedw.mineminenomi.entities.mobs;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.pacifista.PacifistaRadicalBeamGoal;
import xyz.pixelatedw.mineminenomi.init.ModFactions;

public class PacifistaEntity extends OPEntity {
    public static final byte START_CHARGE_LASER_EVENT = 100;
    public static final byte END_CHARGE_LASER_EVENT = 101;
    private boolean isChargingLaser = false;

    public PacifistaEntity(EntityType<? extends PacifistaEntity> type, Level world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new ImprovedMeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new PacifistaRadicalBeamGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return OPEntity.createAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .add(Attributes.ARMOR, 20.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 2.0D);
    }

    @Override
    public void die(DamageSource cause) {
        super.die(cause);
        if (!this.level().isClientSide) {
            // Explode on death
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 5.0F, Level.ExplosionInteraction.NONE);
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        switch (id) {
            case START_CHARGE_LASER_EVENT -> this.isChargingLaser = true;
            case END_CHARGE_LASER_EVENT -> this.isChargingLaser = false;
            default -> super.handleEntityEvent(id);
        }
    }

    public boolean isChargingLaser() {
        return this.isChargingLaser;
    }

    public static boolean checkSpawnRules(EntityType<? extends OPEntity> type, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return OPEntity.checkMobSpawnRules(type, world, reason, pos, random);
    }
}
