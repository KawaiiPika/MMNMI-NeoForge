package xyz.pixelatedw.mineminenomi.entities.mobs;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.HandleCannonGoal;

import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class GruntEntity extends OPEntity {

    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(GruntEntity.class, EntityDataSerializers.INT);

    public GruntEntity(EntityType<? extends GruntEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_VARIANT_ID, 1);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.entityData.get(DATA_VARIANT_ID));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(DATA_VARIANT_ID, compound.getInt("Variant"));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public ResourceLocation getCurrentTexture() {
        int variant = this.entityData.get(DATA_VARIANT_ID);
        if (this.getType().toShortString().contains("marine")) {
            return ModResources.getMarineTexture(variant);
        } else {
            return ModResources.getPirateTexture(variant);
        }
    }

    public static GruntEntity createMarineGrunt(EntityType<? extends GruntEntity> type, Level level) {
        GruntEntity entity = new GruntEntity(type, level);
        entity.entityData.set(DATA_VARIANT_ID, level.random.nextInt(5) + 1);
        return entity;
    }

    public static GruntEntity createPirateGrunt(EntityType<? extends GruntEntity> type, Level level) {
        GruntEntity entity = new GruntEntity(type, level);
        entity.entityData.set(DATA_VARIANT_ID, level.random.nextInt(8) + 1);
        return entity;
    }
}
