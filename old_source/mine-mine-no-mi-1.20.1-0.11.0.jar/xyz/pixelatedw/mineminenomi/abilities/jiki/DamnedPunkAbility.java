package xyz.pixelatedw.mineminenomi.abilities.jiki;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.jiki.DamnedPunkProjectile;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class DamnedPunkAbility extends Ability {
   private static final int VALUE_PER_PROJECTILE = 15;
   private static final int HOLD_TIME = 500;
   private static final int COOLDOWN = 400;
   public static final RegistryObject<AbilityCore<DamnedPunkAbility>> INSTANCE = ModRegistry.registerAbility("damned_punk", "Damned Punk", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Transforms the users arm into a railgun that shoots a projectile with every swing using %s magnetic objects from the users inventory per shoot, dealing massive damage on impact.", new Object[]{MentionHelper.mentionText(15)}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, DamnedPunkAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.PUNK_GIBSON)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(400.0F), ContinuousComponent.getTooltip(500.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::startContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final SwingTriggerComponent swingTriggerComponent = (new SwingTriggerComponent(this)).addSwingEvent(this::swingEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final MorphComponent morphComponent = new MorphComponent(this);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this, 1508);

   public DamnedPunkAbility(AbilityCore<DamnedPunkAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.swingTriggerComponent, this.projectileComponent, this.morphComponent, this.explosionComponent});
      this.addCanUseCheck(JikiHelper::requiresPunkGibson);
      this.addContinueUseCheck(JikiHelper::requiresPunkGibson);
      this.addCanUseCheck(this::canUse);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 500.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.morphComponent.startMorph(entity, (MorphInfo)ModMorphs.DAMNED_PUNK.get());
      PunkGibsonAbility punkGibson = (PunkGibsonAbility)AbilityCapability.get(entity).map((props) -> (PunkGibsonAbility)props.getEquippedAbility((AbilityCore)PunkGibsonAbility.INSTANCE.get())).orElse((Object)null);
      if (punkGibson != null && punkGibson.isContinuous()) {
         punkGibson.activateDamnedPunk(entity);
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.morphComponent.stopMorph(entity);
      PunkGibsonAbility punkGibson = (PunkGibsonAbility)AbilityCapability.get(entity).map((props) -> (PunkGibsonAbility)props.getEquippedAbility((AbilityCore)PunkGibsonAbility.INSTANCE.get())).orElse((Object)null);
      if (punkGibson != null && punkGibson.isContinuous()) {
         punkGibson.activatePunkGibson(entity);
      }

      this.cooldownComponent.startCooldown(entity, 400.0F);
   }

   private void swingEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         List<ItemStack> inventory = ItemsHelper.getAllInventoryItems(entity);
         if (!JikiHelper.hasEnoughIron(inventory, 15.0F)) {
            WyHelper.sendMessage(entity, ModI18nAbilities.MESSAGE_NOT_ENOUGH_MATERIALS);
            this.continuousComponent.stopContinuity(entity);
         } else {
            this.projectileComponent.shoot(entity);
            JikiHelper.spawnAttractEffect(entity);
         }
      }
   }

   private DamnedPunkProjectile createProjectile(LivingEntity entity) {
      List<ItemStack> inventory = ItemsHelper.getAllInventoryItems(entity);
      List<ItemStack> components = JikiHelper.getMagneticItemsNeeded(inventory, 15.0F);
      DamnedPunkProjectile proj = new DamnedPunkProjectile(entity.m_9236_(), entity, components, this);
      return proj;
   }

   private Result canUse(LivingEntity entity, IAbility ability) {
      PunkGibsonAbility abl = (PunkGibsonAbility)AbilityCapability.get(entity).map((props) -> (PunkGibsonAbility)props.getEquippedAbility((AbilityCore)PunkGibsonAbility.INSTANCE.get())).orElse((Object)null);
      if (abl != null && abl.isContinuous()) {
         return Result.success();
      } else {
         List<ItemStack> inventory = ItemsHelper.getAllInventoryItems(entity);
         return !JikiHelper.hasEnoughIron(inventory, 15.0F) ? Result.fail(ModI18nAbilities.MESSAGE_NOT_ENOUGH_MATERIALS) : Result.fail((Component)null);
      }
   }
}
