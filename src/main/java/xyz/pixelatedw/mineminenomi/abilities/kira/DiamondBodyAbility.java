package xyz.pixelatedw.mineminenomi.abilities.kira;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
public class DiamondBodyAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kira_kira_no_mi");
    private static final ResourceLocation MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "diamond_body");
    private static final AttributeModifier ARMOR_MODIFIER = new AttributeModifier(MODIFIER_ID, 20.0, AttributeModifier.Operation.ADD_VALUE);
    private static final AttributeModifier ARMOR_TOUGHNESS_MODIFIER = new AttributeModifier(MODIFIER_ID, 12.0, AttributeModifier.Operation.ADD_VALUE);
    private static final AttributeModifier ATTACK_MODIFIER = new AttributeModifier(MODIFIER_ID, 6.0, AttributeModifier.Operation.ADD_VALUE);
    private static final AttributeModifier TOUGHNESS_MODIFIER = new AttributeModifier(MODIFIER_ID, 8.0, AttributeModifier.Operation.ADD_VALUE);
    public DiamondBodyAbility() { super(FRUIT); }
    @Override protected void startUsing(LivingEntity entity) {
        addModifier(entity, Attributes.ARMOR, ARMOR_MODIFIER); addModifier(entity, Attributes.ARMOR_TOUGHNESS, ARMOR_TOUGHNESS_MODIFIER);
        addModifier(entity, ModAttributes.PUNCH_DAMAGE, ATTACK_MODIFIER); addModifier(entity, ModAttributes.TOUGHNESS, TOUGHNESS_MODIFIER);
    }
    @Override protected void onTick(LivingEntity entity, long duration) { if (getDuration(entity) >= 2400) { this.stop(entity); } }
    @Override protected void stopUsing(LivingEntity entity) {
        removeModifier(entity, Attributes.ARMOR, MODIFIER_ID); removeModifier(entity, Attributes.ARMOR_TOUGHNESS, MODIFIER_ID);
        removeModifier(entity, ModAttributes.PUNCH_DAMAGE, MODIFIER_ID); removeModifier(entity, ModAttributes.TOUGHNESS, MODIFIER_ID);
        this.startCooldown(entity, (long) (40.0F + this.getDuration(entity) / 3.0F));
    }
    private void addModifier(LivingEntity entity, net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute, AttributeModifier modifier) {
        var inst = entity.getAttribute(attribute); if (inst != null && !inst.hasModifier(modifier.id())) { inst.addTransientModifier(modifier); }
    }
    private void removeModifier(LivingEntity entity, net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute, ResourceLocation modifierId) {
        var inst = entity.getAttribute(attribute); if (inst != null) { inst.removeModifier(modifierId); }
    }
    @Override public Component getDisplayName() { return Component.translatable("ability.mineminenomi.diamond_body"); }
}
