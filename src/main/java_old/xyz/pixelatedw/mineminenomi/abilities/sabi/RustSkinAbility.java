package xyz.pixelatedw.mineminenomi.abilities.sabi;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModEnchantments;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.items.weapons.AbilitySwordItem;
import xyz.pixelatedw.mineminenomi.items.weapons.ModSwordItem;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class RustSkinAbility extends PassiveAbility {
   public static final RegistryObject<AbilityCore<RustSkinAbility>> INSTANCE = ModRegistry.registerAbility("rust_skin", "Rust Skin", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Makes the user immune to attacks received from iron based items, damaging them in the processes", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, RustSkinAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE).build("mineminenomi");
   });
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::damageTakenEvent);

   public RustSkinAbility(AbilityCore<RustSkinAbility> ability) {
      super(ability);
      this.addComponents(new AbilityComponent[]{this.damageTakenComponent});
      super.addDuringPassiveEvent(this::onDuringPassive);
   }

   private void onDuringPassive(LivingEntity entity) {
      if (entity.m_21023_((MobEffect)ModEffects.PUNK_CROSS.get())) {
         entity.m_21195_((MobEffect)ModEffects.PUNK_CROSS.get());
      }

      if (entity.m_21023_((MobEffect)ModEffects.GENOCIDE_RAID.get())) {
         entity.m_21195_((MobEffect)ModEffects.GENOCIDE_RAID.get());
      }

   }

   public float damageTakenEvent(LivingEntity entity, IAbility ability, DamageSource source, float damage) {
      if (this.isPaused()) {
         return damage;
      } else {
         Entity directEntity = source.m_7640_();
         Entity sourceEntity = source.m_7639_();
         if (directEntity != null && directEntity instanceof Projectile && IDamageSourceHandler.getHandler(source).hasElement(SourceElement.METAL)) {
            directEntity.m_6074_();
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.RUST_SKIN.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
            return 0.0F;
         } else {
            if (sourceEntity != null && sourceEntity instanceof LivingEntity) {
               if (IDamageSourceHandler.getHandler(source).hasElement(SourceElement.METAL)) {
                  WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.RUST_SKIN.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
                  return 0.0F;
               }

               LivingEntity attacker = (LivingEntity)sourceEntity;
               boolean isImbued = (Boolean)AbilityCapability.get(entity).map((props) -> (BusoshokuHakiImbuingAbility)props.getEquippedAbility((AbilityCore)BusoshokuHakiImbuingAbility.INSTANCE.get())).map((abl) -> abl.isContinuous()).orElse(false);
               if (isImbued) {
                  return damage;
               }

               ItemStack mainhandGear = attacker.m_6844_(EquipmentSlot.MAINHAND);
               ItemStack offhandGear = attacker.m_6844_(EquipmentSlot.OFFHAND);
               ItemStack toDamage = null;
               boolean isMainhand = mainhandGear.m_204117_(ModTags.Items.RUSTY) && mainhandGear.getEnchantmentLevel((Enchantment)ModEnchantments.KAIROSEKI.get()) <= 0;
               boolean isOffhand = offhandGear.m_204117_(ModTags.Items.RUSTY) && offhandGear.getEnchantmentLevel((Enchantment)ModEnchantments.KAIROSEKI.get()) <= 0;
               if (isMainhand || isOffhand) {
                  toDamage = mainhandGear;
               }

               Item mainhandItem = mainhandGear.m_41720_();
               Item offhandItem = offhandGear.m_41720_();
               if (mainhandItem instanceof ModSwordItem && !(mainhandItem instanceof AbilitySwordItem)) {
                  boolean immunity = ItemsHelper.isImmuneToRust(attacker, mainhandGear);
                  if (!immunity) {
                     toDamage = mainhandGear;
                  }
               }

               if (offhandItem instanceof ModSwordItem && !(offhandItem instanceof AbilitySwordItem)) {
                  boolean immunity = ItemsHelper.isImmuneToRust(attacker, offhandGear);
                  if (!immunity) {
                     toDamage = offhandGear;
                  }
               }

               if (toDamage != null) {
                  if (toDamage.m_41763_()) {
                     toDamage.m_41622_(toDamage.m_41776_() / 4 + 1, entity, (e) -> e.m_21166_(EquipmentSlot.MAINHAND));
                  } else {
                     toDamage.m_41774_(1 + this.random.nextInt(8));
                  }

                  WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.RUST_SKIN.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
                  return 0.0F;
               }
            }

            return damage;
         }
      }
   }
}
