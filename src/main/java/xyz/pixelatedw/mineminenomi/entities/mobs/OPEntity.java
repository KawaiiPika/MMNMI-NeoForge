package xyz.pixelatedw.mineminenomi.entities.mobs;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public abstract class OPEntity extends PathfinderMob implements IEntityWithComplexSpawn {

    protected ResourceLocation currentTexture;

    protected OPEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }
    
    public PlayerStats getStats() {
        return PlayerStats.get(this);
    }

    public ResourceLocation getCurrentTexture() {
        return currentTexture != null ? currentTexture : ModResources.NULL_ENTITY_TEXTURE;
    }

    @Override
    public void writeSpawnData(RegistryFriendlyByteBuf buffer) {
        buffer.writeBoolean(this.currentTexture != null);
        if (this.currentTexture != null) {
            buffer.writeResourceLocation(this.currentTexture);
        }
    }

    @Override
    public void readSpawnData(RegistryFriendlyByteBuf buffer) {
        boolean hasTexture = buffer.readBoolean();
        if (hasTexture) {
            this.currentTexture = buffer.readResourceLocation();
        }
    }
}
