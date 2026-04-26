package xyz.pixelatedw.mineminenomi.abilities.baku;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import java.util.ArrayList;
import java.util.List;
public class BeroCannonAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "baku_baku_no_mi");
    public BeroCannonAbility() { super(FRUIT); }
    @Override public xyz.pixelatedw.mineminenomi.api.util.Result canUse(LivingEntity entity) {
        xyz.pixelatedw.mineminenomi.api.util.Result superResult = super.canUse(entity);
        if (superResult.isFail()) return superResult;
        if (getBlocksInInventory(entity).isEmpty()) { return xyz.pixelatedw.mineminenomi.api.util.Result.fail(Component.translatable("ability.mineminenomi.error.not_enough_blocks")); }
        return xyz.pixelatedw.mineminenomi.api.util.Result.success();
    }
    @Override protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            List<ItemStack> projectiles = getBlocksInInventory(entity);
            if (!projectiles.isEmpty()) {
                ItemStack stack = projectiles.get(0);
                if (stack.getCount() > 1) { stack.shrink(1); } else if (entity instanceof net.minecraft.world.entity.player.Player player) { player.getInventory().removeItem(stack); }
            }
            this.startCooldown(entity, 40);
        }
        this.stop(entity);
    }
    private List<ItemStack> getBlocksInInventory(LivingEntity entity) {
        List<ItemStack> projectiles = new ArrayList<>();
        if (entity instanceof net.minecraft.world.entity.player.Player player) {
            for (ItemStack item : player.getInventory().items) { if (!item.isEmpty() && item.getItem() instanceof BlockItem) { projectiles.add(item); } }
        }
        return projectiles;
    }
    @Override public Component getDisplayName() { return Component.translatable("ability.mineminenomi.bero_cannon"); }
}
