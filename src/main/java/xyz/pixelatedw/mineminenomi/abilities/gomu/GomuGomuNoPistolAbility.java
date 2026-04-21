package xyz.pixelatedw.mineminenomi.abilities.gomu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class GomuGomuNoPistolAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "gomu_gomu_no_mi");
    public GomuGomuNoPistolAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            xyz.pixelatedw.mineminenomi.entities.projectiles.GomuPistolEntity pistol = new xyz.pixelatedw.mineminenomi.entities.projectiles.GomuPistolEntity(entity.level(), entity);
            pistol.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 2.0F, 0.1F);
            entity.level().addFreshEntity(pistol);
            
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.GOMU_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 1.0F);
                
            this.startCooldown(entity, 30);
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.gomu_gomu_no_pistol"); }
}
