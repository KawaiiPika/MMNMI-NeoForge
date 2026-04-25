package xyz.pixelatedw.mineminenomi.entities.mobs;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;

public class SniperEntity extends OPEntity implements RangedAttackMob {

    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(SniperEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_FISHMAN = SynchedEntityData.defineId(SniperEntity.class, EntityDataSerializers.BOOLEAN);

    public SniperEntity(EntityType<? extends SniperEntity> type, Level level) {
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
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.0D, 60, 40.0F));
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
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    public static boolean checkSpawnRules(EntityType<? extends OPEntity> type, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return OPEntity.checkMobSpawnRules(type, world, reason, pos, random);
    }

    public static SniperEntity createMarineSniper(EntityType<? extends SniperEntity> type, Level level) {
        SniperEntity entity = new SniperEntity(type, level);
        entity.entityData.set(DATA_VARIANT_ID, level.random.nextInt(5) + 1);
        if (!level.isClientSide()) {
            entity.getStats().setFaction(ModFactions.MARINE.getId());
            entity.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModWeapons.FLINTLOCK.get()));
        }
        return entity;
    }

    public static SniperEntity createPirateSniper(EntityType<? extends SniperEntity> type, Level level) {
        SniperEntity entity = new SniperEntity(type, level);
        if (!level.isClientSide()) {
            entity.getStats().setFaction(ModFactions.PIRATE.getId());
            entity.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModWeapons.FLINTLOCK.get()));
            if (level.random.nextInt(10) < 3) {
                entity.entityData.set(IS_FISHMAN, true);
                entity.entityData.set(DATA_VARIANT_ID, level.random.nextInt(3) + 1);
            } else {
                entity.entityData.set(DATA_VARIANT_ID, level.random.nextInt(3) + 6);
            }
        } else {
            entity.entityData.set(DATA_VARIANT_ID, 6);
        }
        return entity;
    }

    public static SniperEntity createBanditSniper(EntityType<? extends SniperEntity> type, Level level) {
        SniperEntity entity = new SniperEntity(type, level);
        entity.entityData.set(DATA_VARIANT_ID, level.random.nextInt(3) + 1);
        if (!level.isClientSide()) {
            entity.getStats().setFaction(ModFactions.BANDIT.getId());
            entity.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModWeapons.FLINTLOCK.get()));
        }
        return entity;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        // Fallback for Sniper Entity since Custom Bullets aren't ported yet.
        net.minecraft.world.entity.projectile.Arrow arrow = new net.minecraft.world.entity.projectile.Arrow(this.level(), this, new ItemStack(net.minecraft.world.item.Items.ARROW), null);
        arrow.setOwner(this);
        double d0 = target.getX() - this.getX();
        double d1 = target.getY(0.3333333333333333D) - arrow.getY();
        double d2 = target.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        arrow.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
        this.playSound(net.minecraft.sounds.SoundEvents.ARROW_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(arrow);
    }
}
