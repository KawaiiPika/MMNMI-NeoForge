package xyz.pixelatedw.mineminenomi.entities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.items.VivreCardItem;

import java.util.List;
import java.util.UUID;

public class VivreCardEntity extends Entity implements ItemSupplier {
    private @Nullable LivingEntity owner;
    private UUID ownerUUID;
    private final double speedLimit = 0.001;

    public VivreCardEntity(EntityType<? extends VivreCardEntity> type, Level world) {
        super(type, world);
    }

    public VivreCardEntity(Level worldIn) {
        this(ModEntities.VIVRE_CARD.get(), worldIn);
    }

    public void setOwner(UUID uuid) {
        this.ownerUUID = uuid;
        if (this.level() instanceof ServerLevel serverLevel) {
            Entity e = serverLevel.getEntity(this.ownerUUID);
            if (e instanceof LivingEntity living) {
                this.owner = living;
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (!this.onGround()) {
                this.move(MoverType.SELF, new Vec3(0.0D, -0.1D, 0.0D));
            }

            if (this.ownerUUID != null && !this.isRemoved()) {
                if (this.owner == null && this.level() instanceof ServerLevel serverLevel) {
                    Entity e = serverLevel.getEntity(this.ownerUUID);
                    if (e instanceof LivingEntity living) {
                        this.owner = living;
                    }
                }

                if (this.owner != null) {
                    if (this.owner.getHealth() <= 0.0F) {
                        WyHelper.spawnParticles(ParticleTypes.ASH, (ServerLevel) this.level(), this.getX(), this.getY() + 0.3, this.getZ());
                        this.discard();
                    } else {
                        double posX = this.getX() - this.owner.getX();
                        double posZ = this.getZ() - this.owner.getZ();

                        if (posX > 0.0D) {
                            if (posX > speedLimit) {
                                posX = speedLimit;
                            }
                        } else if (posX < 0.0D) {
                            if (posX < -speedLimit) {
                                posX = -speedLimit;
                            }
                        }

                        if (posZ > 0.0D) {
                            if (posZ > speedLimit) {
                                posZ = speedLimit;
                            }
                        } else if (posZ < 0.0D) {
                            if (posZ < -speedLimit) {
                                posZ = -speedLimit;
                            }
                        }

                        this.move(MoverType.SELF, new Vec3(-posX, 0.0D, -posZ));

                        // Look at owner
                        this.lookAt(net.minecraft.commands.arguments.EntityAnchorArgument.Anchor.EYES, this.owner.position());

                        if (this.tickCount > 40) {
                            double radius = 0.5D;
                            AABB aabb = this.getBoundingBox().inflate(radius, radius, radius);
                            List<Player> list = this.level().getEntitiesOfClass(Player.class, aabb, EntitySelector.NO_SPECTATORS.and(EntitySelector.ENTITY_STILL_ALIVE));
                            for (Player player : list) {
                                ItemStack stack = new ItemStack(ModItems.VIVRE_CARD.get());
                                if (stack.getItem() instanceof VivreCardItem vivreCardItem) {
                                    vivreCardItem.setOwner(stack, this.owner);
                                }
                                if (player.getInventory().add(stack)) {
                                    this.discard();
                                    break;
                                } else {
                                    player.drop(stack, false);
                                    this.discard();
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    this.discard();
                }
            } else {
                this.discard();
            }
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("OwnerUUID", 8)) {
            try {
                this.setOwner(UUID.fromString(compound.getString("OwnerUUID")));
            } catch (Exception e) {
                // ignore
            }
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        if (this.ownerUUID != null) {
            compound.putString("OwnerUUID", this.ownerUUID.toString());
        }
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(ModItems.VIVRE_CARD.get());
    }
}
