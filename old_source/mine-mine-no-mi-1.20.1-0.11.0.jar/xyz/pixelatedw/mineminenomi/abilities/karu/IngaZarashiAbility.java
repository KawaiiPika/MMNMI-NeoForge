package xyz.pixelatedw.mineminenomi.abilities.karu;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.MorphAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class IngaZarashiAbility extends MorphAbility {
   private static final int MIN_COOLDOWN = 20;
   private static final int MAX_COOLDOWN = 240;
   public static final RegistryObject<AbilityCore<IngaZarashiAbility>> INSTANCE = ModRegistry.registerAbility("inga_zarashi", "Inga Zarashi", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Increases your physical prowess depending on how much damage you have in your %s counter", new Object[]{MentionHelper.mentionText(ModI18n.GUI_KARMA)}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, IngaZarashiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(20.0F, 240.0F), ContinuousComponent.getTooltip()).build("mineminenomi");
   });
   private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("06141405-6e5c-4b98-a8f7-230e0ffb96bc");
   private static final UUID ATTACK_MODIFIER_UUID = UUID.fromString("7ddb710f-a497-4f64-b272-8fcc9955b401");
   private static final UUID REACH_MODIFIER_UUID = UUID.fromString("dc0d06d6-ffd6-49d8-b484-da232b78fd41");
   private Optional<KarmaAbility> karmaAbility = Optional.empty();

   public IngaZarashiAbility(AbilityCore<IngaZarashiAbility> core) {
      super(core);
      this.addCanUseCheck(this::canUse);
      this.addContinueUseCheck(this::canUse);
      this.continuousComponent.addTickEvent(this::duringContinuousEvent);
      this.continuousComponent.addEndEvent(this::endContinuousEvent);
   }

   private void duringContinuousEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.getContinueTime() % 20.0F == 0.0F && this.karmaAbility.isPresent()) {
         this.updateKarma(entity);
      }

   }

   private void endContinuousEvent(LivingEntity entity, IAbility ability) {
      float cooldown = Mth.m_14036_(this.continuousComponent.getContinueTime(), 20.0F, 240.0F);
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   private Result canUse(LivingEntity entity, IAbility ability) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props == null) {
         return Result.fail((Component)null);
      } else {
         KarmaAbility karma = (KarmaAbility)props.getPassiveAbility((AbilityCore)KarmaAbility.INSTANCE.get());
         if (karma == null) {
            return Result.fail((Component)null);
         } else {
            this.karmaAbility = Optional.ofNullable(karma);
            return Result.success();
         }
      }
   }

   private void updateKarma(LivingEntity entity) {
      if (((KarmaAbility)this.karmaAbility.get()).getPrevKarma() != ((KarmaAbility)this.karmaAbility.get()).getKarma()) {
         this.statsComponent.removeModifiers(entity);

         for(Map.Entry<Attribute, AttributeModifier> entry : this.getAttributes().entries()) {
            this.statsComponent.removeAttributeModifier((Attribute)entry.getKey());
            this.statsComponent.addAttributeModifier((Attribute)entry.getKey(), (AttributeModifier)entry.getValue());
         }

         this.statsComponent.applyModifiers(entity);
         this.morphComponent.updateMorphSize(entity);
         ((KarmaAbility)this.karmaAbility.get()).setPrevKarma(((KarmaAbility)this.karmaAbility.get()).getKarma());
      }

      ((KarmaAbility)this.karmaAbility.get()).addKarma(entity, -(((KarmaAbility)this.karmaAbility.get()).getKarma() / 100.0F));
   }

   private Multimap<Attribute, AttributeModifier> getAttributes() {
      Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
      float karma = ((KarmaAbility)this.karmaAbility.get()).getKarma();
      double armorMod = (double)Math.min(karma / 100.0F * 8.0F, 8.0F);
      double attackMod = (double)Math.min(karma / 100.0F * 10.0F, 10.0F);
      double reachMod = Math.min((double)(karma / 100.0F) * (double)2.5F, (double)2.5F);
      map.put(Attributes.f_22284_, new AbilityAttributeModifier(ARMOR_MODIFIER_UUID, INSTANCE, "Karma Armor Modifier", armorMod, Operation.ADDITION));
      map.put((Attribute)ModAttributes.PUNCH_DAMAGE.get(), new AbilityAttributeModifier(ATTACK_MODIFIER_UUID, INSTANCE, "Karma Attack Modifier", attackMod, Operation.ADDITION));
      AbilityAttributeModifier reachAttribute = new AbilityAttributeModifier(REACH_MODIFIER_UUID, INSTANCE, "Karma Reach Modifier", reachMod, Operation.ADDITION);
      map.put((Attribute)ForgeMod.ENTITY_REACH.get(), reachAttribute);
      map.put((Attribute)ForgeMod.BLOCK_REACH.get(), reachAttribute);
      return map;
   }

   public MorphInfo getTransformation() {
      return (MorphInfo)ModMorphs.INGA_ZARASHI.get();
   }
}
