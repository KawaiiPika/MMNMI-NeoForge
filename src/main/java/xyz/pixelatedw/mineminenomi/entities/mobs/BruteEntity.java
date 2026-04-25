package xyz.pixelatedw.mineminenomi.entities.mobs;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class BruteEntity extends OPEntity {

    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(BruteEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_FISHMAN = SynchedEntityData.defineId(BruteEntity.class, EntityDataSerializers.BOOLEAN);

    public BruteEntity(EntityType<? extends BruteEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_VARIANT_ID, 1);
        builder.define(IS_FISHMAN, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.entityData.get(DATA_VARIANT_ID));
        compound.putBoolean("IsFishman", this.entityData.get(IS_FISHMAN));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(DATA_VARIANT_ID, compound.getInt("Variant"));
        this.entityData.set(IS_FISHMAN, compound.getBoolean("IsFishman"));
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
        String path = this.getType().toShortString();

        if (this.entityData.get(IS_FISHMAN)) {
            return ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/fishman_pirate" + variant + ".png");
        } else if (path.contains("marine")) {
            return ModResources.getMarineTexture(variant);
        } else if (path.contains("bandit")) {
            return ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/bandit" + variant + ".png");
        } else {
            return ModResources.getPirateTexture(variant);
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return OPEntity.createAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.ARMOR, 4.0D);
    }

    public static boolean checkSpawnRules(EntityType<? extends OPEntity> type, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return OPEntity.checkMobSpawnRules(type, world, reason, pos, random);
    }

    public static BruteEntity createMarineBrute(EntityType<? extends BruteEntity> type, Level level) {
        BruteEntity entity = new BruteEntity(type, level);
        entity.entityData.set(DATA_VARIANT_ID, level.random.nextInt(5) + 1);
        if (!level.isClientSide()) {
            entity.getStats().setFaction(ModFactions.MARINE.getId());
        }
        return entity;
    }

    public static BruteEntity createPirateBrute(EntityType<? extends BruteEntity> type, Level level) {
        BruteEntity entity = new BruteEntity(type, level);
        if (!level.isClientSide()) {
            entity.getStats().setFaction(ModFactions.PIRATE.getId());
            if (level.random.nextInt(10) < 3) {
                entity.entityData.set(IS_FISHMAN, true);
                entity.entityData.set(DATA_VARIANT_ID, level.random.nextInt(3) + 1);
            } else {
                entity.entityData.set(DATA_VARIANT_ID, level.random.nextInt(3) + 6); // Pirate 6, 7, 8 in legacy
            }
        } else {
            entity.entityData.set(DATA_VARIANT_ID, 6);
        }
        return entity;
    }

    public static BruteEntity createBanditBrute(EntityType<? extends BruteEntity> type, Level level) {
        BruteEntity entity = new BruteEntity(type, level);
        entity.entityData.set(DATA_VARIANT_ID, level.random.nextInt(3) + 1);
        if (!level.isClientSide()) {
            entity.getStats().setFaction(ModFactions.BANDIT.getId());
        }
        return entity;
    }
}
