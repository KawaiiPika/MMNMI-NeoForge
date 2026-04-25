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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class CaptainEntity extends OPEntity {

    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(CaptainEntity.class, EntityDataSerializers.INT);

    public CaptainEntity(EntityType<? extends CaptainEntity> type, Level level) {
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
        String path = this.getType().toShortString();

        if (path.contains("marine")) {
            return ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/marine_captain" + variant + ".png");
        } else if (path.contains("bandit")) {
            return ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/bandit" + variant + ".png");
        } else {
            return ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/pirate_captain" + variant + ".png");
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return OPEntity.createAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.ARMOR, 8.0D);
    }

    public static boolean checkSpawnRules(EntityType<? extends OPEntity> type, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return OPEntity.checkMobSpawnRules(type, world, reason, pos, random);
    }

    public static CaptainEntity createMarineCaptain(EntityType<? extends CaptainEntity> type, Level level) {
        CaptainEntity entity = new CaptainEntity(type, level);
        entity.entityData.set(DATA_VARIANT_ID, level.random.nextInt(5) + 1);
        if (!level.isClientSide()) {
            entity.getStats().setFaction(ModFactions.MARINE.getId());
            ItemStack marineCapeStack = new ItemStack(ModArmors.MARINE_CAPTAIN_CAPE.get());
            marineCapeStack.set(net.minecraft.core.component.DataComponents.DYED_COLOR, new net.minecraft.world.item.component.DyedItemColor(33980, true));
            entity.setItemSlot(EquipmentSlot.CHEST, marineCapeStack);
        }
        return entity;
    }

    public static CaptainEntity createPirateCaptain(EntityType<? extends CaptainEntity> type, Level level) {
        CaptainEntity entity = new CaptainEntity(type, level);
        entity.entityData.set(DATA_VARIANT_ID, level.random.nextInt(8) + 1);
        if (!level.isClientSide()) {
            entity.getStats().setFaction(ModFactions.PIRATE.getId());
            if (level.random.nextFloat() < 0.7F) {
                ItemStack pirateCapeStack = new ItemStack(ModArmors.PIRATE_CAPTAIN_CAPE.get());
                entity.setItemSlot(EquipmentSlot.CHEST, pirateCapeStack);
            }
        }
        return entity;
    }

    public static CaptainEntity createBanditCaptain(EntityType<? extends CaptainEntity> type, Level level) {
        CaptainEntity entity = new CaptainEntity(type, level);
        entity.entityData.set(DATA_VARIANT_ID, level.random.nextInt(3) + 1);
        if (!level.isClientSide()) {
            entity.getStats().setFaction(ModFactions.BANDIT.getId());
        }
        return entity;
    }
}
