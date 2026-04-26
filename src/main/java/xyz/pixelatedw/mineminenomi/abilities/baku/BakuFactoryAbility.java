package xyz.pixelatedw.mineminenomi.abilities.baku;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
public class BakuFactoryAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "baku_baku_no_mi");
    private BlockState previousBlock;
    private BlockPos usedPos;
    public BakuFactoryAbility() { super(FRUIT); }
    @Override protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide && entity instanceof Player player) {
            ServerLevel serverLevel = (ServerLevel) entity.level();
            serverLevel.getServer().execute(() -> {
                usedPos = player.blockPosition(); previousBlock = serverLevel.getBlockState(usedPos);
                serverLevel.setBlock(usedPos, Blocks.CRAFTING_TABLE.defaultBlockState(), 3);
                player.openMenu(serverLevel.getBlockState(usedPos).getMenuProvider(serverLevel, usedPos));
                serverLevel.setBlock(usedPos, previousBlock, 3);
            });
        }
    }
    @Override protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide && entity instanceof Player player) { if (!(player.containerMenu instanceof CraftingMenu)) { this.stop(entity); } }
    }
    @Override public Component getDisplayName() { return Component.translatable("ability.mineminenomi.baku_factory"); }
}
