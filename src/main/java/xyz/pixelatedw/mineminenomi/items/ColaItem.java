package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModFoods;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class ColaItem extends Item {

    public ColaItem() {
        super(new Item.Properties().stacksTo(16).food(ModFoods.COLA));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return super.use(level, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null) {
                // Restore stamina (Cyborgs use cola for energy, others just stamina)
                // For now, let's just restore stamina
                double restoreAmount = 25.0;
                stats.setStamina(Math.min(stats.getMaxStamina(), stats.getStamina() + restoreAmount));
                stats.sync(player);
            }
            
            if (!player.getAbilities().instabuild) {
                player.getInventory().add(new ItemStack(ModItems.EMPTY_COLA.get()));
            }
        }
        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }
}
