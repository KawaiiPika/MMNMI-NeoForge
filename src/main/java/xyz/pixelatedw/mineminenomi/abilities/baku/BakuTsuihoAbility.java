package xyz.pixelatedw.mineminenomi.abilities.baku;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import java.util.ArrayList;
import java.util.List;
public class BakuTsuihoAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "baku_baku_no_mi");
    private int ammo = 0; private int shotsFired = 0; private long lastShotTick = 0; private boolean isShooting = false;
    public BakuTsuihoAbility() { super(FRUIT); }
    @Override protected void startUsing(LivingEntity entity) {}
    @Override protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            long currentTick = entity.level().getGameTime();
            if (!isShooting && duration >= 60) {
                ammo = 0; List<ItemStack> blocks = getBlocksInInventory(entity);
                for (int i = 0; ammo < 20 && i < blocks.size(); i++) {
                    ItemStack stack = blocks.get(i); ammo += stack.getCount(); stack.shrink(ammo);
                }
                isShooting = true; shotsFired = 0; lastShotTick = currentTick; this.startTick = currentTick;
            } else if (isShooting) {
                if (shotsFired < ammo) {
                    if (currentTick - lastShotTick >= 3) { shotsFired++; lastShotTick = currentTick; }
                } else { this.stop(entity); }
            }
        }
    }
    @Override protected void stopUsing(LivingEntity entity) { isShooting = false; this.startCooldown(entity, 160); }
    private List<ItemStack> getBlocksInInventory(LivingEntity entity) {
        List<ItemStack> projectiles = new ArrayList<>();
        if (entity instanceof net.minecraft.world.entity.player.Player player) {
            for (ItemStack item : player.getInventory().items) { if (!item.isEmpty() && item.getItem() instanceof BlockItem) { projectiles.add(item); } }
        }
        return projectiles;
    }
    @Override public Component getDisplayName() { return Component.translatable("ability.mineminenomi.baku_tsuiho"); }
}
