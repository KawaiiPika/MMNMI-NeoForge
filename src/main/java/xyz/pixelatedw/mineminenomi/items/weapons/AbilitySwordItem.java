package xyz.pixelatedw.mineminenomi.items.weapons;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import java.util.function.Supplier;

public class AbilitySwordItem extends ModSwordItem {
    
    private final Supplier<xyz.pixelatedw.mineminenomi.api.abilities.Ability> ability;

    public AbilitySwordItem(Tier tier, Supplier<xyz.pixelatedw.mineminenomi.api.abilities.Ability> ability, int damage) {
        this(tier, ability, damage, -2.4F);
    }

    public AbilitySwordItem(Tier tier, Supplier<xyz.pixelatedw.mineminenomi.api.abilities.Ability> ability, int damage, float attackSpeed) {
        super(tier, damage, attackSpeed, new Item.Properties().durability(-1));
        this.ability = ability;
    }

    public Supplier<xyz.pixelatedw.mineminenomi.api.abilities.Ability> getAbility() {
        return ability;
    }
}
