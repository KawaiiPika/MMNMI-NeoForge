package xyz.pixelatedw.mineminenomi.items.weapons;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public class ModGunItem extends ProjectileWeaponItem {

    public static final TagKey<Item> GUN_AMMO_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("mineminenomi", "gun_ammo"));
    public static final TagKey<Item> BAZOOKA_AMMO_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("mineminenomi", "bazooka_ammo"));

    public static final Predicate<ItemStack> GUN_AMMO = (stack) -> stack.is(GUN_AMMO_TAG);
    public static final Predicate<ItemStack> BAZOOKA_AMMO = (stack) -> stack.is(BAZOOKA_AMMO_TAG);

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
        super(new Item.Properties().stacksTo(1).durability(maxDamage));
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

    // Builder-like setters
    public ModGunItem setGunpowderLimit(int limit) { this.maxGunpowder = limit; return this; }
    public ModGunItem setDamageMultiplier(float damage) { this.damageMultiplier = damage; return this; }
    public ModGunItem setShotCooldown(int cd) { this.shotCooldown = cd; return this; }
    public ModGunItem setReloadCooldown(int cd) { this.reloadCooldown = cd; return this; }
    public ModGunItem setInaccuracy(float acc) { this.inaccuracy = acc; return this; }
    public ModGunItem setBulletSpeed(float speed) { this.bulletSpeed = speed; return this; }

    @Override
    protected void shootProjectile(net.minecraft.world.entity.LivingEntity shooter, net.minecraft.world.entity.projectile.Projectile projectile, int index, float velocity, float inaccuracy, float angle, @javax.annotation.Nullable net.minecraft.world.entity.LivingEntity target) {
        projectile.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), angle, velocity, inaccuracy);
    }

    // TODO: Implement shooting and reloading logic
}
