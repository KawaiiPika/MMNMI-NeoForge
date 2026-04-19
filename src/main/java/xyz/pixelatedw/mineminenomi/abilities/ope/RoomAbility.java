package xyz.pixelatedw.mineminenomi.abilities.ope;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.entities.SphereEntity;

public class RoomAbility extends Ability {

    private static final int ROOM_RADIUS = 15;
    private static final int MAX_TICKS = 600; // 30 seconds
    
    // Map player UUID to the center of their active Room
    private static final java.util.Map<java.util.UUID, BlockPos> activeRooms = new java.util.HashMap<>();
    private static final java.util.Map<java.util.UUID, SphereEntity> roomEntities = new java.util.HashMap<>();

    public RoomAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "ope_ope_no_mi"));
    }

    @Override
    public boolean isUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        return stats != null && stats.isAbilityActive(this.getId().toString());
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             createRoom(entity);
             entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.ROOM_CREATE_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 1.0F);
        }
    }

    private void createRoom(LivingEntity entity) {
        Level level = entity.level();
        if (level.isClientSide) return;

        BlockPos center = entity.blockPosition();

        SphereEntity sphere = new SphereEntity(level, entity);
        sphere.setRadius(ROOM_RADIUS);
        sphere.setMaxLife(MAX_TICKS);
        level.addFreshEntity(sphere);

        activeRooms.put(entity.getUUID(), center);
        roomEntities.put(entity.getUUID(), sphere);
    }

    private void destroyRoom(LivingEntity entity) {
        Level level = entity.level();
        if (level.isClientSide) return;

        activeRooms.remove(entity.getUUID());
        SphereEntity sphere = roomEntities.remove(entity.getUUID());
        if (sphere != null && !sphere.isRemoved()) {
            sphere.discard();
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide && duration >= MAX_TICKS) {
            this.startCooldown(entity, 300);
            this.stop(entity);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            destroyRoom(entity);
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.ROOM_CLOSE_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 1.0F);
        }
    }

    public static boolean isEntityInRoom(LivingEntity entity) {
        // If there's any active room containing the entity's position
        for (java.util.Map.Entry<java.util.UUID, BlockPos> entry : activeRooms.entrySet()) {
            BlockPos center = entry.getValue();
            if (center.distSqr(entity.blockPosition()) <= ROOM_RADIUS * ROOM_RADIUS) {
                return true;
            }
        }
        return false;
    }

    private ResourceLocation getId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "room");
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.room");
    }
}
