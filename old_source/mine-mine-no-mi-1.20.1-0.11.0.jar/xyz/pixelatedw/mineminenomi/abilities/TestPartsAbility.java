package xyz.pixelatedw.mineminenomi.abilities;

import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.MorphAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.math.VectorHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.entities.AbilityPart;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SUpdateAbilityComponentPacket;

public class TestPartsAbility extends MorphAbility {
   public static final RegistryObject<AbilityCore<TestPartsAbility>> INSTANCE = ModRegistry.registerAbility("test_parts", "Test Parts", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Ability for testing part entities.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, TestPartsAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(10.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).build("mineminenomi");
   });
   private static final AbilityAttributeModifier SPEED_MODIFIER;
   private static final AbilityAttributeModifier HEALTH_MODIFIER;
   private static final AbilityAttributeModifier ARMOR_MODIFIER;
   private static final AbilityAttributeModifier STRENGTH_MODIFIER;
   private static final AbilityAttributeModifier ATTACK_SPEED_MODIFIER;
   private static final AbilityAttributeModifier JUMP_BOOST_MODIFIER;
   private static final AbilityAttributeModifier REACH_MODIFIER;
   private static final AbilityAttributeModifier TOUGHNESS_MODIFIER;

   public TestPartsAbility(AbilityCore<TestPartsAbility> core) {
      super(core);
      Predicate<LivingEntity> isMorphed = (entity) -> this.morphComponent.isMorphed();
      super.continuousComponent.addStartEvent(this::startMorph);
      super.statsComponent.addAttributeModifier(Attributes.f_22279_, SPEED_MODIFIER, isMorphed);
      super.statsComponent.addAttributeModifier(Attributes.f_22276_, HEALTH_MODIFIER, isMorphed);
      super.statsComponent.addAttributeModifier(Attributes.f_22284_, ARMOR_MODIFIER, isMorphed);
      super.statsComponent.addAttributeModifier((Supplier)ModAttributes.PUNCH_DAMAGE, STRENGTH_MODIFIER, isMorphed);
      super.statsComponent.addAttributeModifier(Attributes.f_22283_, ATTACK_SPEED_MODIFIER, isMorphed);
      super.statsComponent.addAttributeModifier((Supplier)ModAttributes.JUMP_HEIGHT, JUMP_BOOST_MODIFIER, isMorphed);
      super.statsComponent.addAttributeModifier((Supplier)ForgeMod.BLOCK_REACH, REACH_MODIFIER, isMorphed);
      super.statsComponent.addAttributeModifier((Supplier)ForgeMod.ENTITY_REACH, REACH_MODIFIER, isMorphed);
      super.statsComponent.addAttributeModifier((Supplier)ModAttributes.TOUGHNESS, TOUGHNESS_MODIFIER, isMorphed);
   }

   public MorphInfo getTransformation() {
      return (MorphInfo)ModMorphs.BISON_HEAVY.get();
   }

   private void startMorph(LivingEntity entity, IAbility ability) {
      Level level = entity.m_9236_();
      if (!level.f_46443_) {
         this.morphComponent.addPart("head", new AbilityPart(entity, 1.0F, 1.0F));
         this.morphComponent.addPart("body_1", new AbilityPart(entity, 1.0F, 1.0F));
         this.morphComponent.addPart("body_2", new AbilityPart(entity, 1.0F, 1.0F));
         this.morphComponent.addPart("body_3", new AbilityPart(entity, 1.0F, 1.0F));
         ModNetwork.sendToAllTrackingAndSelf(new SUpdateAbilityComponentPacket(entity, this, super.morphComponent), entity);
      }
   }

   public void handlePart(String name, PartEntity<?> part, IAbility ability) {
      Entity entityParent = part.getParent();
      if (entityParent instanceof LivingEntity parent) {
         Vec3 origin = parent.m_20182_().m_82520_((double)0.0F, (double)(parent.m_20206_() - part.m_20206_()), (double)0.0F);
         if (name.equals("head")) {
            part.m_146884_(VectorHelper.getRelativeOffset(origin, parent.m_6080_(), parent.m_146909_(), new Vec3((double)0.0F, (double)0.0F, (double)1.0F)));
         } else if (name.equals("body_1")) {
            part.m_146884_(VectorHelper.getRelativeOffset(origin, parent.f_20883_, parent.m_146909_(), new Vec3((double)0.0F, (double)0.0F, (double)-1.0F)));
         } else if (name.equals("body_2")) {
            part.m_146884_(VectorHelper.getRelativeOffset(origin, parent.f_20883_, parent.m_146909_(), new Vec3((double)0.0F, (double)0.0F, (double)-2.0F)));
         } else if (name.equals("body_3")) {
            part.m_146884_(VectorHelper.getRelativeOffset(origin, parent.f_20883_, parent.m_146909_(), new Vec3((double)0.0F, (double)0.0F, (double)-3.0F)));
         }

      }
   }

   static {
      SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_MOVEMENT_SPEED_UUID, INSTANCE, "Bison Heavy Point Modifier", 0.3, Operation.MULTIPLY_BASE);
      HEALTH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_HEALTH_UUID, INSTANCE, "Bison Heavy Point Modifier", (double)10.0F, Operation.ADDITION);
      ARMOR_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ARMOR_UUID, INSTANCE, "Bison Heavy Point Modifier", (double)6.0F, Operation.ADDITION);
      STRENGTH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_STRENGTH_UUID, INSTANCE, "Bison Heavy Point Modifier", (double)4.0F, Operation.ADDITION);
      ATTACK_SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ATTACK_SPEED_UUID, INSTANCE, "Bison Heavy Point Modifier", (double)-0.8F, Operation.ADDITION);
      JUMP_BOOST_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_JUMP_BOOST_UUID, INSTANCE, "Bison Heavy Point Modifier", (double)1.0F, Operation.ADDITION);
      REACH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ATTACK_REACH_UUID, INSTANCE, "Bison Heavy Point Reach Modifier", (double)0.5F, Operation.ADDITION);
      TOUGHNESS_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_TOUGHNESS_UUID, INSTANCE, "Bison Heavy Point Toughness Modifier", (double)2.0F, Operation.ADDITION);
   }
}
