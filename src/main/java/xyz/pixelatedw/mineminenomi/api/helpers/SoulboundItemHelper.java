package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.UUID;
import org.jetbrains.annotations.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;

public class SoulboundItemHelper {
    @Nullable
    public static Pair<UUID, LivingEntity> getOwner(Level world, ItemStack itemStack) {
        UUID uuid = getOwnerUUID(world, itemStack);
        if (uuid == null) return null;
        if (world instanceof ServerLevel serverLevel) {
            net.minecraft.world.entity.Entity entity = serverLevel.getEntity(uuid);
            if (entity instanceof LivingEntity living) {
                return ImmutablePair.of(uuid, living);
            }
        }
        return ImmutablePair.of(uuid, null);
    }

    @Nullable
    public static UUID getOwnerUUID(Level world, ItemStack itemStack) {
        if (!itemStack.has(ModDataComponents.OWNER_UUID.get())) return null;
        String uuidStr = itemStack.get(ModDataComponents.OWNER_UUID.get());
        try {
            return UUID.fromString(uuidStr);
        } catch (Exception e) {
            return null;
        }
    }

    public static void setOwner(ItemStack itemStack, LivingEntity owner) {
        itemStack.set(ModDataComponents.OWNER_UUID.get(), owner.getUUID().toString());
    }
}
