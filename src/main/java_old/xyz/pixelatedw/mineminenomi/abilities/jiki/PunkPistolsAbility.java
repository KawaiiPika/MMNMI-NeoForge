package xyz.pixelatedw.mineminenomi.abilities.jiki;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.RepeaterAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.jiki.PunkPistolProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class PunkPistolsAbility extends RepeaterAbility {
   private static final int VALUE_PER_SPEAR = 5;
   private static final int SPEARS = 6;
   private static final int INTERVAL = 4;
   private static final int COOLDOWN = 240;
   public static final RegistryObject<AbilityCore<PunkPistolsAbility>> INSTANCE = ModRegistry.registerAbility("punk_pistols", "Punk Pistols", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Uses %s magnetic objects from the user's inventory to form %s spears and shoots them at enemies.", new Object[]{MentionHelper.mentionText(30), MentionHelper.mentionText(6)}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, PunkPistolsAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.METAL).build("mineminenomi");
   });
   private final AnimationComponent animationComponent = new AnimationComponent(this);

   public PunkPistolsAbility(AbilityCore<PunkPistolsAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.animationComponent});
      this.continuousComponent.addStartEvent(this::startContinuityEvent);
      this.continuousComponent.addEndEvent(this::endContinuityEvent);
      this.repeaterComponent.addTriggerEvent(this::triggerRepeaterEvent);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.AIM_SNIPER);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
   }

   private void triggerRepeaterEvent(LivingEntity entity, IAbility ability) {
      List<ItemStack> inventory = ItemsHelper.getAllInventoryItems(entity);
      if (!JikiHelper.hasEnoughIron(inventory, 5.0F)) {
         WyHelper.sendMessage(entity, ModI18nAbilities.MESSAGE_NOT_ENOUGH_MATERIALS);
         this.continuousComponent.stopContinuity(entity);
      } else {
         JikiHelper.spawnAttractEffect(entity);
      }
   }

   public int getMaxTriggers() {
      return 6;
   }

   public int getTriggerInterval() {
      return 4;
   }

   public float getRepeaterCooldown() {
      return 240.0F;
   }

   public PunkPistolProjectile getProjectileFactory(LivingEntity entity) {
      List<ItemStack> inventory = ItemsHelper.getAllInventoryItems(entity);
      List<ItemStack> components = JikiHelper.getMagneticItemsNeeded(inventory, 5.0F);
      PunkPistolProjectile proj = new PunkPistolProjectile(entity.m_9236_(), entity, components, this);
      return proj;
   }
}
