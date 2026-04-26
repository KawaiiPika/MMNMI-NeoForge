package xyz.pixelatedw.mineminenomi.items.weapons;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.init.ModTags;

import java.util.function.Predicate;

import org.jspecify.annotations.Nullable;
public class ModGunItem extends ProjectileWeaponItem {

    public static final Predicate<ItemStack> GUN_AMMO = (stack) -> stack.is(ModTags.Items.GUN_AMMO);
    public static final Predicate<ItemStack> BAZOOKA_AMMO = (stack) -> stack.is(ModTags.Items.BAZOOKA_AMMO);

    private int maxGunpowder = 3;
    private float bulletSpeed = 2.0F;
    private float inaccuracy = 2.0F;
    private int shotCooldown = 10;
    private int reloadCooldown = 20;
    private float damageMultiplier = 1.0F;
    private Predicate<ItemStack> bulletCheck;

    public ModGunItem(int maxDamage) {
        this(maxDamage, GUN_AMMO);
    }

    public ModGunItem(int maxDamage, Predicate<ItemStack> bulletPredicate) {
        super(new Item.Properties().stacksTo(1).durability(maxDamage).component(xyz.pixelatedw.mineminenomi.init.ModDataComponents.GUNPOWDER.get(), 0));
        this.bulletCheck = bulletPredicate;
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return bulletCheck;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack, net.minecraft.world.entity.LivingEntity entity) {
        return 72000;
    }

    private void setLoadedGunPowder(ItemStack stack, int powder) {
        stack.set(xyz.pixelatedw.mineminenomi.init.ModDataComponents.GUNPOWDER.get(), powder);
    }

    private int getLoadedGunPowder(ItemStack stack) {
        return stack.getOrDefault(xyz.pixelatedw.mineminenomi.init.ModDataComponents.GUNPOWDER.get(), 0);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        boolean flag = !player.getProjectile(itemStack).isEmpty();
        boolean hasGunPowder = this.getLoadedGunPowder(itemStack) > 0;

        if (!hasGunPowder) {
            for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
                ItemStack stack = player.getInventory().getItem(i);
                if (stack.getItem() == net.minecraft.world.item.Items.GUNPOWDER) {
                    int count = this.maxGunpowder;
                    if (stack.getCount() < count) {
                        count = stack.getCount();
                    }

                    this.setLoadedGunPowder(itemStack, count * 5);
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(count);
                    }
                    hasGunPowder = true;
                    player.getCooldowns().addCooldown(this.asItem(), this.reloadCooldown);
                    break;
                }
            }
        }

        if (!hasGunPowder) {
            return InteractionResultHolder.fail(itemStack);
        } else if (!player.getAbilities().instabuild && !flag) {
            return InteractionResultHolder.fail(itemStack);
        } else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemStack);
        }
    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, net.minecraft.world.entity.LivingEntity living, int timeLeft) {
        if (living instanceof Player player) {
            ItemStack ammoStack = player.getProjectile(itemStack);

            Item powder = ammoStack.getItem();

            int loadedGunpowder = this.getLoadedGunPowder(itemStack);
            if (!level.isClientSide()) {
                boolean flag = player.getAbilities().instabuild;
                int i = this.getUseDuration(itemStack, player) - timeLeft;
                i = net.neoforged.neoforge.event.EventHooks.onArrowLoose(itemStack, level, player, i, !ammoStack.isEmpty() || flag);
                if (i < 0) {
                    return;
                }

                if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                    if (powder instanceof xyz.pixelatedw.mineminenomi.items.BulletItem bulletItem) {
                        Object projObj = bulletItem.createProjectile(serverLevel, player);
                        if (projObj instanceof net.minecraft.world.entity.projectile.Projectile proj) {
                            this.shootProjectile(player, proj, 0, this.bulletSpeed, this.inaccuracy, 0.0F, null);
                            serverLevel.addFreshEntity(proj);
                        }
                    } else if (itemStack.is(xyz.pixelatedw.mineminenomi.init.ModWeapons.BAZOOKA.get()) && ammoStack.is(xyz.pixelatedw.mineminenomi.init.ModTags.Items.BAZOOKA_AMMO)) {
                        Object projObj = ((xyz.pixelatedw.mineminenomi.items.BulletItem)ammoStack.getItem()).createProjectile(serverLevel, player);
                        if (projObj instanceof net.minecraft.world.entity.projectile.Projectile proj) {
                            this.shootProjectile(player, proj, 0, this.bulletSpeed, this.inaccuracy, 0.0F, null);
                            serverLevel.addFreshEntity(proj);
                        }
                    } else if (flag && ammoStack.isEmpty()) {
                        // Creative mode dummy shoot if no ammo
                        // TODO: Generic projectile creation
                    }
                }

                itemStack.hurtAndBreak(1, player, net.minecraft.world.entity.LivingEntity.getSlotForHand(player.getUsedItemHand()));
            }

            player.getCooldowns().addCooldown(this.asItem(), this.shotCooldown);
            boolean hasInfinite = player.getAbilities().instabuild;

            if (hasInfinite) {
                return;
            }

            --loadedGunpowder;
            this.setLoadedGunPowder(itemStack, Math.max(0, loadedGunpowder));
            ammoStack.shrink(1);
            if (ammoStack.isEmpty()) {
                player.getInventory().removeItem(ammoStack);
            }
        }
    }

    // Builder-like setters
    public ModGunItem setGunpowderLimit(int limit) { this.maxGunpowder = limit; return this; }
    public ModGunItem setDamageMultiplier(float damage) { this.damageMultiplier = damage; return this; }
    public ModGunItem setShotCooldown(int cd) { this.shotCooldown = cd; return this; }
    public ModGunItem setReloadCooldown(int cd) { this.reloadCooldown = cd; return this; }
    public ModGunItem setInaccuracy(float acc) { this.inaccuracy = acc; return this; }
    public ModGunItem setBulletSpeed(float speed) { this.bulletSpeed = speed; return this; }

    @Override
    protected void shootProjectile(net.minecraft.world.entity.LivingEntity shooter, net.minecraft.world.entity.projectile.Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {
        projectile.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), angle, velocity, inaccuracy);
    }
}
