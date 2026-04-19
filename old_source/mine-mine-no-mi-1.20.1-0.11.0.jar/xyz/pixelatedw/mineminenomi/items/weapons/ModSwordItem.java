package xyz.pixelatedw.mineminenomi.items.weapons;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.items.IMultiChannelColorItem;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.mixins.IMobEffectInstanceMixin;

public class ModSwordItem extends SwordItem implements IMultiChannelColorItem {
   public static final UUID BASE_ATTACK_REACH_UUID = UUID.fromString("57e27c27-d6e4-4b42-9484-806ac7d3d30c");
   private Set<HitEffect> hitEffects;
   private boolean isDyeable;
   private int availableLayers;
   private Multimap<Attribute, AttributeModifier> modifiers;

   public ModSwordItem(Tier tier, int damage) {
      this(tier, damage, -2.4F, new Item.Properties());
   }

   public ModSwordItem(Tier tier, int damage, float speed) {
      this(tier, damage, speed, new Item.Properties());
   }

   public ModSwordItem(Tier tier, int damage, float speed, Item.Properties props) {
      super(tier, damage, speed, props);
      this.hitEffects = new HashSet();
      this.modifiers = super.m_7167_(EquipmentSlot.MAINHAND);
   }

   public ModSwordItem(ModsBuilder mods) {
      this(mods, new Item.Properties());
   }

   public ModSwordItem(ModsBuilder mods, Item.Properties props) {
      super(mods.tier, mods.rawDamage, mods.speed, props);
      this.hitEffects = new HashSet();
      this.modifiers = mods.buildModifiers();
   }

   public boolean m_7579_(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
      super.m_7579_(itemStack, target, attacker);

      for(HitEffect hitEffect : this.hitEffects) {
         if (hitEffect.addedStacks > 0 && target.m_21023_(hitEffect.effect)) {
            MobEffectInstance effect = target.m_21124_(hitEffect.effect);
            int oldDuration = target.m_21124_(hitEffect.effect).m_19557_();
            ((IMobEffectInstanceMixin)effect).setDuration(oldDuration + 20 * hitEffect.addedStacks);
            ((IMobEffectInstanceMixin)effect).setAmplifier(effect.m_19564_() + hitEffect.addedStacks);
         } else {
            target.m_7292_(new MobEffectInstance(hitEffect.effect, hitEffect.duration, 0));
         }
      }

      return true;
   }

   public <T extends ModSwordItem> T addEffect(MobEffect effect, int duration) {
      this.hitEffects.add(new HitEffect(effect, duration, 0));
      return (T)this;
   }

   public <T extends ModSwordItem> T addEffect(MobEffect effect, int duration, int addedStacks) {
      this.hitEffects.add(new HitEffect(effect, duration, addedStacks));
      return (T)this;
   }

   public <T extends ModSwordItem> T setDyeable() {
      return (T)this.setDyeable(1);
   }

   public <T extends ModSwordItem> T setDyeable(int maxLayers) {
      this.isDyeable = true;
      this.availableLayers = maxLayers;
      return (T)this;
   }

   public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot slot) {
      return slot == EquipmentSlot.MAINHAND ? this.modifiers : super.m_7167_(slot);
   }

   /** @deprecated */
   @Deprecated
   public ModSwordItem setRustImmunity() {
      return this;
   }

   /** @deprecated */
   @Deprecated
   public ModSwordItem setIsPoisonous(int time) {
      this.addEffect(MobEffects.f_19614_, time);
      return this;
   }

   /** @deprecated */
   @Deprecated
   public ModSwordItem setFrosbiteTimer(int time) {
      this.addEffect((MobEffect)ModEffects.FROSTBITE.get(), time);
      return this;
   }

   public int getDefaultLayerColor(int layer) {
      return layer == 0 ? ItemsHelper.GENERIC_HANDLE_COLOR.getRGB() : -1;
   }

   public int getMaxLayers() {
      return this.availableLayers;
   }

   public boolean canBeDyed() {
      return this.isDyeable;
   }

   private static record HitEffect(MobEffect effect, int duration, int addedStacks) {
   }

   public static class ModsBuilder {
      private final Tier tier;
      private final int rawDamage;
      private final float damage;
      private float speed = -2.4F;
      private float reach = 0.0F;

      public ModsBuilder(Tier tier, int damage) {
         this.tier = tier;
         this.rawDamage = damage;
         this.damage = (float)damage + tier.m_6631_();
      }

      public ModsBuilder speed(float speed) {
         this.speed = speed;
         return this;
      }

      public ModsBuilder reach(float reach) {
         this.reach = reach;
         return this;
      }

      public ImmutableMultimap<Attribute, AttributeModifier> buildModifiers() {
         ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
         builder.put(Attributes.f_22281_, new AttributeModifier(ModSwordItem.f_41374_, "Weapon modifier", (double)this.damage, Operation.ADDITION));
         builder.put(Attributes.f_22283_, new AttributeModifier(ModSwordItem.f_41375_, "Weapon modifier", (double)this.speed, Operation.ADDITION));
         builder.put((Attribute)ForgeMod.ENTITY_REACH.get(), new AttributeModifier(ModSwordItem.BASE_ATTACK_REACH_UUID, "Reach bonus", (double)this.reach, Operation.ADDITION));
         return builder.build();
      }
   }
}
