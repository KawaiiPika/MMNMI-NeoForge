package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.enums.FruitType;

import java.util.ArrayList;
import java.util.List;

// AkumaNoMiItem is the base class for all Devil Fruit items.
// Full eat/ability logic deferred to Phase 3 (requires Capabilities, ModFruits, etc.)
public class AkumaNoMiItem extends Item implements xyz.pixelatedw.mineminenomi.api.items.IFruitColor {
    public static final int GENERIC_FRUIT_VARIATIONS = 10;

    public final FruitType type;
    private final int tier;
    private final java.util.function.Supplier<xyz.pixelatedw.mineminenomi.api.abilities.Ability>[] abilities;

    // Static list of all devil fruits (used for randomization)
    public static final List<AkumaNoMiItem> DEVIL_FRUITS = new ArrayList<>();

    @SafeVarargs
    public AkumaNoMiItem(int tier, FruitType type, java.util.function.Supplier<xyz.pixelatedw.mineminenomi.api.abilities.Ability>... abilities) {
        super(new Item.Properties().stacksTo(1));
        this.tier = tier;
        this.type = type;
        this.abilities = abilities;
        DEVIL_FRUITS.add(this);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public net.minecraft.world.item.ItemStack finishUsingItem(net.minecraft.world.item.ItemStack itemStack, Level world, net.minecraft.world.entity.LivingEntity entity) {
        if (!world.isClientSide() && entity instanceof Player player) {
            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(player);
            
            if (stats.hasDevilFruit()) {
                player.hurt(world.damageSources().generic(), Float.MAX_VALUE);
                player.displayClientMessage(net.minecraft.network.chat.Component.translatable("item.message.devil_fruit_death"), false);
                itemStack.shrink(1);
                return itemStack;
            }

            net.minecraft.resources.ResourceLocation fruitId = net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(this);
            stats.setDevilFruit(fruitId);

            if (this.abilities != null) {
                for (int i = 0; i < this.abilities.length && i < 24; i++) {
                    xyz.pixelatedw.mineminenomi.api.abilities.Ability ability = this.abilities[i].get();
                    if (ability != null) {
                        for (java.util.Map.Entry<net.minecraft.resources.ResourceKey<xyz.pixelatedw.mineminenomi.api.abilities.Ability>, xyz.pixelatedw.mineminenomi.api.abilities.Ability> entry : xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY.entrySet()) {
                            if (entry.getValue() == ability) {
                                stats.setEquippedAbility(i, entry.getKey().location());
                                break;
                            }
                        }
                    }
                }
            } else {
                xyz.pixelatedw.mineminenomi.api.helpers.FruitAbilityHelper.giveFruitAbilities(player, fruitId);
            }
            
            if (this.type == FruitType.LOGIA) {
                stats.setLogia(true);
            }
            stats.sync(player);

            player.displayClientMessage(net.minecraft.network.chat.Component.translatable("item.message.devil_fruit_eat", this.getDevilFruitName()), false);
            itemStack.shrink(1);
        }
        return itemStack;
    }

    @Override
    public net.minecraft.world.item.UseAnim getUseAnimation(net.minecraft.world.item.ItemStack stack) {
        return net.minecraft.world.item.UseAnim.EAT;
    }

    public int getTier() { return tier; }
    public FruitType getType() { return type; }
    public java.util.function.Supplier<xyz.pixelatedw.mineminenomi.api.abilities.Ability>[] getAbilities() { return abilities; }

    public net.minecraft.network.chat.Component getDevilFruitName() {
        return this.getName(new net.minecraft.world.item.ItemStack(this));
    }
}
