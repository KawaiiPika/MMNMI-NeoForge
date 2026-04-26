package xyz.pixelatedw.mineminenomi.abilities.doa;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
public class DoorDoorAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "doa_doa_no_mi");
    public DoorDoorAbility() { super(FRUIT); }
    @Override protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            Vec3 startVec = entity.position().add(0, entity.getEyeHeight(), 0);
            Vec3 look = entity.getLookAngle();
            Vec3 endVec = startVec.add(look.scale(16.0));
            BlockHitResult hitBlock = entity.level().clip(new ClipContext(startVec, endVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
            if (hitBlock.getType() == HitResult.Type.BLOCK && Math.sqrt(entity.distanceToSqr(hitBlock.getLocation())) <= 2.5) {
                Vec3 vec = entity.position();
                BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
                pos.set(vec.x, vec.y, vec.z);
                boolean firstSolid = false;
                int airBlocks = 0;
                Direction dir = entity.getDirection().getOpposite();
                for (int i = 0; i < 40; ++i) {
                    BlockState state = entity.level().getBlockState(pos);
                    if (state.is(Blocks.AIR) && (firstSolid || airBlocks > 1)) {
                        entity.teleportTo(vec.x, vec.y + 1.0, vec.z);
                        break;
                    }
                    vec = vec.add(look);
                    pos.set(Mth.floor(vec.x), Mth.floor(vec.y), Mth.floor(vec.z));
                    state = entity.level().getBlockState(pos);
                    if (state.isFaceSturdy(entity.level(), pos, dir, SupportType.FULL)) { firstSolid = true; }
                    if (state.is(Blocks.AIR)) { ++airBlocks; }
                }
                entity.level().playSound(null, entity.blockPosition(), ModSounds.DOA_IN_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
                this.startCooldown(entity, 100);
            }
        }
        this.stop(entity);
    }
    @Override public Component getDisplayName() { return Component.translatable("ability.mineminenomi.door_door"); }
}
