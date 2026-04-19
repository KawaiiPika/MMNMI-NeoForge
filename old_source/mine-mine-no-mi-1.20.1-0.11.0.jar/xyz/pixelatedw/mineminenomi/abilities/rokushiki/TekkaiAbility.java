package xyz.pixelatedw.mineminenomi.abilities.rokushiki;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.GuardAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class TekkaiAbility extends GuardAbility {
   private static final ResourceLocation TEKKAI_WALK_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/tekkai_walk.png");
   private static final int CONTINUITY_THRESHOLD = 200;
   private static final int MIN_COOLDOWN = 60;
   private static final int MAX_COOLDOWN = 260;
   private static final GuardAbility.GuardValue HEAVY_GUARD;
   private static final GuardAbility.GuardValue WALK_GUARD;
   private static final int DORIKI = 1000;
   public static final RegistryObject<AbilityCore<TekkaiAbility>> INSTANCE;
   private final AltModeComponent<Mode> altModeComponent;
   private final AnimationComponent animationComponent;

   public TekkaiAbility(AbilityCore<TekkaiAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<Mode>(this, Mode.class, TekkaiAbility.Mode.HEAVY)).addChangeModeEvent(this::onAltModeChange);
      this.animationComponent = new AnimationComponent(this);
      super.addComponents(new AbilityComponent[]{this.altModeComponent, this.animationComponent});
      this.continuousComponent.addStartEvent(this::onContinuityStart).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   }

   public GuardAbility.GuardValue getGuard(LivingEntity entity) {
      return this.altModeComponent.isMode(TekkaiAbility.Mode.HEAVY) ? HEAVY_GUARD : WALK_GUARD;
   }

   public boolean canGuardBreak(LivingEntity entity) {
      return !this.altModeComponent.isMode(TekkaiAbility.Mode.HEAVY);
   }

   public void onGuard(LivingEntity entity, DamageSource source, float originalDamage, float finalDamage) {
   }

   public float getHoldTime() {
      return 200.0F;
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.CROSSED_ARMS);
      if (this.altModeComponent.isMode(TekkaiAbility.Mode.HEAVY)) {
         this.blockMovement(entity);
      }

   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      if (this.altModeComponent.isMode(TekkaiAbility.Mode.HEAVY)) {
         AbilityHelper.setDeltaMovement(entity, (double)0.0F, (double)-5.0F, (double)0.0F);
      }

   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      this.removeMovementBlock(entity);
      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, this.continuousComponent.getContinueTime() + 60.0F);
   }

   private boolean onAltModeChange(LivingEntity entity, IAbility ability, Mode mode) {
      if (mode == TekkaiAbility.Mode.HEAVY) {
         this.setDisplayIcon((AbilityCore)INSTANCE.get());
      } else if (mode == TekkaiAbility.Mode.WALK) {
         this.setDisplayIcon(TEKKAI_WALK_ICON);
      }

      return true;
   }

   public void switchToHeavyMode(LivingEntity entity) {
      this.altModeComponent.setMode(entity, TekkaiAbility.Mode.HEAVY);
   }

   public void switchToWalkMode(LivingEntity entity) {
      this.altModeComponent.setMode(entity, TekkaiAbility.Mode.WALK);
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode tekkai = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(6.0F, -2.0F));
      NodeUnlockCondition unlockCondition = RequiredRaceUnlockCondition.requires((Race)ModRaces.HUMAN.get()).and(DorikiUnlockCondition.atLeast((double)1000.0F));
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      tekkai.setUnlockRule(unlockCondition, unlockAction);
      return tekkai;
   }

   static {
      HEAVY_GUARD = GuardAbility.GuardValue.percentage(0.2F, GuardAbility.GuardBreakKind.PER_HIT, 50.0F);
      WALK_GUARD = GuardAbility.GuardValue.percentage(0.1F, GuardAbility.GuardBreakKind.PER_HIT, 30.0F);
      INSTANCE = ModRegistry.registerAbility("tekkai", "Tekkai", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Hardens the user's body to protect themselves, but they're unable to move", (Object)null), ImmutablePair.of("  §aHEAVY§r immobile, more protection", (Object)null), ImmutablePair.of("  §aWALK§r can move, less protection", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, TekkaiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(60.0F, 260.0F), ContinuousComponent.getTooltip(200.0F)).setNodeFactories(TekkaiAbility::createNode).build("mineminenomi");
      });
   }

   public static enum Mode {
      HEAVY,
      WALK;

      // $FF: synthetic method
      private static Mode[] $values() {
         return new Mode[]{HEAVY, WALK};
      }
   }
}
