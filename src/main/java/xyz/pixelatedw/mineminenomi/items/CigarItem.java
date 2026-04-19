package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class CigarItem extends Item {
    private final int smokeFrequency;

    public CigarItem(int smokeFrequency) {
        super(new Item.Properties().durability(1000));
        this.smokeFrequency = smokeFrequency;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (player.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
            ItemStack stack = heldStack.copy();
            stack.setCount(1);
            player.setItemSlot(EquipmentSlot.HEAD, stack);
            heldStack.shrink(1);
            return InteractionResultHolder.consume(heldStack);
        }
        return InteractionResultHolder.pass(heldStack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (entity instanceof Player player) {
            boolean isEquipped = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == this;
            if (isEquipped && player.tickCount % this.smokeFrequency == 0) {
                if (level.isClientSide && !player.isInvisible()) {
                    Vec3 look = player.getLookAngle();
                    level.addParticle(ParticleTypes.SMOKE, 
                        player.getX() + look.x * 0.5, 
                        player.getEyeY() + look.y * 0.5 - 0.2, 
                        player.getZ() + look.z * 0.5, 
                        0, 0.04, 0);
                }

                if (!level.isClientSide) {
                    PlayerStats stats = PlayerStats.get(player);
                    // Moku Moku and Gasu Gasu users don't consume cigars
                    boolean infinite = false;
                    if (stats != null) {
                        String df = stats.getDevilFruit().map(rl -> rl.toString()).orElse("");
                        if (df.equals("mineminenomi:moku_moku_no_mi") || df.equals("mineminenomi:gasu_gasu_no_mi")) {
                            infinite = true;
                        }
                    }
                    
                    if (!infinite) {
                        stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
                    }
                }
            }
        }
    }
}
