package xyz.pixelatedw.mineminenomi.items.weapons;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.ArrayList;
import java.util.List;

public class ModSwordItem extends SwordItem {

    private final List<HitEffect> hitEffects = new ArrayList<>();

    public ModSwordItem(Tier tier, int damage) {
        this(tier, damage, -2.4F, new Item.Properties());
    }

    public ModSwordItem(Tier tier, int damage, float speed) {
        this(tier, damage, speed, new Item.Properties());
    }

    public ModSwordItem(Tier tier, int damage, float speed, Item.Properties props) {
        super(tier, props.attributes(SwordItem.createAttributes(tier, damage, speed)));
    }

    public ModSwordItem(ModsBuilder mods) {
        super(mods.tier, new Item.Properties().attributes(mods.buildAttributes()));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        for (HitEffect effect : this.hitEffects) {
            target.addEffect(new MobEffectInstance(effect.effect(), effect.duration(), effect.amplifier()));
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    public ModSwordItem addEffect(Holder<MobEffect> effect, int duration) {
        return this.addEffect(effect, duration, 0);
    }

    public ModSwordItem addEffect(Holder<MobEffect> effect, int duration, int amplifier) {
        this.hitEffects.add(new HitEffect(effect, duration, amplifier));
        return this;
    }
    
    public <T extends ModSwordItem> T setDyeable() {
        return (T) this;
    }

    private record HitEffect(Holder<MobEffect> effect, int duration, int amplifier) {}

    public static class ModsBuilder {
        private final Tier tier;
        private final int damage;
        private float speed = -2.4F;
        private float reach = 0.0F;

        public ModsBuilder(Tier tier, int damage) {
            this.tier = tier;
            this.damage = damage;
        }

        public ModsBuilder speed(float speed) {
            this.speed = speed;
            return this;
        }

        public ModsBuilder reach(float reach) {
            this.reach = reach;
            return this;
        }

        public ItemAttributeModifiers buildAttributes() {
            ItemAttributeModifiers.Builder builder = SwordItem.createAttributes(this.tier, this.damage, this.speed).builder();
            if (this.reach != 0) {
                builder.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi", "weapon_reach"), this.reach, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            }
            return builder.build();
        }
    }
}
