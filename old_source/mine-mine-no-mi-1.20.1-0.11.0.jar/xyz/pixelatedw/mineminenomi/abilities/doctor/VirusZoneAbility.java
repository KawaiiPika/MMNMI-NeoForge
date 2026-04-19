package xyz.pixelatedw.mineminenomi.abilities.doctor;

import com.google.common.collect.Lists;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class VirusZoneAbility extends Ability {
   private static final Component MUMMY_NAME;
   private static final Component ICE_ONI_NAME;
   private static final ResourceLocation MUMMY_ICON;
   private static final ResourceLocation ICE_ONI_ICON;
   private static final int COOLDOWN = 400;
   private static final int EFFECT_TIME = 200;
   public static final RegistryObject<AbilityCore<VirusZoneAbility>> INSTANCE;
   private final AltModeComponent<Mode> altModeComponent;

   public VirusZoneAbility(AbilityCore<VirusZoneAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<Mode>(this, Mode.class, VirusZoneAbility.Mode.MUMMY)).addChangeModeEvent(this::onAltModeChange);
      this.setDisplayName(MUMMY_NAME);
      this.setDisplayIcon(MUMMY_ICON);
      this.addComponents(new AbilityComponent[]{this.altModeComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresMedicBag);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity livingEntity, IAbility ability) {
      ItemStack stack = new ItemStack(Items.f_42739_);
      if (this.altModeComponent.getCurrentMode() == VirusZoneAbility.Mode.MUMMY) {
         stack = PotionUtils.m_43552_(stack, Lists.newArrayList(new MobEffectInstance[]{new MobEffectInstance((MobEffect)ModEffects.MUMMY_VIRUS.get(), 200, 1)}));
      } else if (this.altModeComponent.getCurrentMode() == VirusZoneAbility.Mode.ICE_ONI) {
         stack = PotionUtils.m_43552_(stack, Lists.newArrayList(new MobEffectInstance[]{new MobEffectInstance((MobEffect)ModEffects.ICE_ONI.get(), 200, 1)}));
      }

      ThrownPotion potion = new ThrownPotion(livingEntity.m_9236_(), livingEntity);
      potion.m_37446_(stack);
      potion.m_146926_(potion.m_146909_() - 20.0F);
      potion.m_37251_(livingEntity, livingEntity.m_146909_(), livingEntity.m_146908_(), -20.0F, 1.0F, 0.0F);
      livingEntity.m_9236_().m_7967_(potion);
      if (livingEntity instanceof Player player) {
         Optional<ItemStack> medicBag = ItemsHelper.findItemInSlot(player, EquipmentSlot.CHEST, (Item)ModArmors.MEDIC_BAG.get());
         if (medicBag.isPresent()) {
            ((ItemStack)medicBag.get()).m_41622_(10, player, (user) -> user.m_21166_(EquipmentSlot.MAINHAND));
         }
      }

      this.cooldownComponent.startCooldown(livingEntity, 400.0F);
   }

   private boolean onAltModeChange(LivingEntity entity, IAbility ability, Mode mode) {
      if (mode == VirusZoneAbility.Mode.MUMMY) {
         this.setDisplayName(MUMMY_NAME);
         this.setDisplayIcon(MUMMY_ICON);
      } else if (mode == VirusZoneAbility.Mode.ICE_ONI) {
         this.setDisplayName(ICE_ONI_NAME);
         this.setDisplayIcon(ICE_ONI_ICON);
      }

      return true;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-13.0F, -3.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.DOCTOR.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)FailedExperimentAbility.INSTANCE.get()).getNode(entity), ((AbilityCore)DopingAbility.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }

   static {
      MUMMY_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "virus_zone_mummy", "Virus Zone: Mummy"));
      ICE_ONI_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "virus_zone_ice_oni", "Virus Zone: Ice Oni"));
      MUMMY_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/virus_zone_mummy.png");
      ICE_ONI_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/virus_zone_ice_oni.png");
      INSTANCE = ModRegistry.registerAbility("virus_zone", "Virus Zone", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Throws a lingering splash potion of the mummy virus or ice oni virus.", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, VirusZoneAbility::new)).setIcon(MUMMY_ICON).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(400.0F)).setNodeFactories(VirusZoneAbility::createNode).build("mineminenomi");
      });
   }

   public static enum Mode {
      MUMMY,
      ICE_ONI;

      // $FF: synthetic method
      private static Mode[] $values() {
         return new Mode[]{MUMMY, ICE_ONI};
      }
   }
}
