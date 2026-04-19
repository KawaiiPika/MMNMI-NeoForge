package xyz.pixelatedw.mineminenomi.abilities.jiki;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PunkGibsonAbility extends Ability {
   private static final int REQUIRED_IRON = 100;
   private static final int HOLD_TIME = 2400;
   private static final int MIN_COOLDOWN = 60;
   private static final int MAX_COOLDOWN = 600;
   public static final RegistryObject<AbilityCore<PunkGibsonAbility>> INSTANCE = ModRegistry.registerAbility("punk_gibson", "Punk Gibson", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Uses %s magnetic items from the user's inventory to create a large arm increasing their punch power and reach.", new Object[]{"§a100§r"}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, PunkGibsonAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(60.0F, 600.0F), ContinuousComponent.getTooltip(2400.0F), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.METAL).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private static final AbilityAttributeModifier PUNCH_DAMAGE_MODIFIER;
   private static final AbilityAttributeModifier REACH_MODIFIER;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final HitTriggerComponent hitTriggerComponent = (new HitTriggerComponent(this)).addTryHitEvent(this::hitEvent);
   private final ChangeStatsComponent statsComponent = new ChangeStatsComponent(this);
   private final MorphComponent morphComponent = new MorphComponent(this);
   private List<ItemStack> magneticItems = new ArrayList();
   private boolean dropItems = true;

   public PunkGibsonAbility(AbilityCore<PunkGibsonAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.hitTriggerComponent, this.continuousComponent, this.statsComponent, this.morphComponent});
      this.statsComponent.addAttributeModifier(ModAttributes.PUNCH_DAMAGE, PUNCH_DAMAGE_MODIFIER);
      this.statsComponent.addAttributeModifier(ForgeMod.ENTITY_REACH, REACH_MODIFIER);
      this.statsComponent.addAttributeModifier(ForgeMod.BLOCK_REACH, REACH_MODIFIER);
      this.addCanUseCheck(JikiHelper.getMetalicItemsCheck(100));
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 2400.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.morphComponent.startMorph(entity, (MorphInfo)ModMorphs.PUNK_GIBSON.get());
      this.statsComponent.applyModifiers(entity);
      if (!this.magneticItems.isEmpty()) {
         this.magneticItems.clear();
      }

      List<ItemStack> inventory = ItemsHelper.getAllInventoryItems(entity);
      this.magneticItems = JikiHelper.getMagneticItemsNeeded(inventory, 100.0F);
      this.dropItems = true;
      JikiHelper.spawnAttractEffect(entity);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.morphComponent.stopMorph(entity);
      this.statsComponent.removeModifiers(entity);
      if (this.dropItems && this.magneticItems.size() > 0) {
         ItemStack stack = (ItemStack)this.magneticItems.get(0);
         ItemsHelper.itemBreakParticles(entity, 100, stack);
         entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), SoundEvents.f_12018_, entity.m_5720_(), 0.5F, 1.0F);
         JikiHelper.dropComponentItems(entity.m_9236_(), entity.m_20182_(), this.magneticItems);
      }

      float cooldown = Mth.m_14036_(this.continuousComponent.getContinueTime(), 60.0F, 600.0F);
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   private HitTriggerComponent.HitResult hitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      return HitTriggerComponent.HitResult.PASS;
   }

   public void activateDamnedPunk(LivingEntity entity) {
      this.statsComponent.removeModifiers(entity);
   }

   public void activatePunkGibson(LivingEntity entity) {
      this.statsComponent.applyModifiers(entity);
   }

   public void stopItemDrops() {
      this.dropItems = false;
   }

   public List<ItemStack> getMagneticItems() {
      return this.magneticItems;
   }

   static {
      PUNCH_DAMAGE_MODIFIER = new AbilityAttributeModifier(UUID.randomUUID(), INSTANCE, "Punk Gibson Strength Modifier", (double)6.0F, Operation.ADDITION);
      REACH_MODIFIER = new AbilityAttributeModifier(UUID.randomUUID(), INSTANCE, "Punk Gibson Reach Modifier", (double)2.0F, Operation.ADDITION);
   }
}
