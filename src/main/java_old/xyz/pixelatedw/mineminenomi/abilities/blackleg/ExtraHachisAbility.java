package xyz.pixelatedw.mineminenomi.abilities.blackleg;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joml.Vector3d;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.blackleg.ExtraHachisProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.blackleg.PoeleAFrireProjectile;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class ExtraHachisAbility extends Ability {
   private static final Component POELE_A_FRIRE_NAME = Component.m_237115_(ModRegistry.registerAbilityName("poele_a_frire", "Poêle à Frire"));
   private static final ResourceLocation POELE_A_FRIRE_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/poele_a_frire.png");
   private static final float NORMAL_COOLDOWN = 240.0F;
   private static final float POELE_A_FRIRE_COOLDOWN = 300.0F;
   public static final RegistryObject<AbilityCore<ExtraHachisAbility>> INSTANCE = ModRegistry.registerAbility("extra_hachis", "Extra Hachis", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Launches a rapid barrage of kicks", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, ExtraHachisAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setNodeFactories(ExtraHachisAbility::createNode).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::onContinuityStart);
   private final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addTriggerEvent(this::onRepeaterTrigger).addStopEvent(this::onRepeaterStop);
   private final AltModeComponent<Mode> altModeComponent;

   public ExtraHachisAbility(AbilityCore<ExtraHachisAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<Mode>(this, Mode.class, ExtraHachisAbility.Mode.NORMAL, true)).addChangeModeEvent(this::onAltModeChange);
      super.addComponents(this.continuousComponent, this.repeaterComponent, this.altModeComponent);
      super.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         this.repeaterComponent.stop(entity);
      } else {
         this.continuousComponent.triggerContinuity(entity);
      }
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.repeaterComponent.start(entity, 20, 2);
      }
   }

   private void onRepeaterTrigger(LivingEntity entity, IAbility ability) {
      int projectileSpace = 2;
      float speed = 4.0F;
      Vector3d pos = new Vector3d(entity.m_20185_() + WyHelper.randomWithRange(-projectileSpace, projectileSpace) + WyHelper.randomDouble(), entity.m_20186_() + (double)1.5F + WyHelper.randomWithRange(0, projectileSpace) + WyHelper.randomDouble(), entity.m_20189_() + WyHelper.randomWithRange(-projectileSpace, projectileSpace) + WyHelper.randomDouble());
      ExtraHachisProjectile extraHachisProjectile = new ExtraHachisProjectile(entity.m_9236_(), entity, this);
      if (this.altModeComponent.getCurrentMode() == ExtraHachisAbility.Mode.POELE_A_FRIRE) {
         speed = 5.0F;
         PoeleAFrireProjectile poeleAFrireProjectile = new PoeleAFrireProjectile(entity.m_9236_(), entity, this);
         poeleAFrireProjectile.m_7678_(pos.x, pos.y, pos.z, 0.0F, 0.0F);
         entity.m_9236_().m_7967_(poeleAFrireProjectile);
         poeleAFrireProjectile.m_37251_(entity, entity.m_146909_(), entity.m_146908_(), 0.0F, speed, 3.0F);
      }

      extraHachisProjectile.m_7678_(pos.x, pos.y, pos.z, 0.0F, 0.0F);
      entity.m_9236_().m_7967_(extraHachisProjectile);
      extraHachisProjectile.m_37251_(entity, entity.m_146909_(), entity.m_146908_(), 0.0F, speed, 3.0F);
      ((ServerLevel)entity.m_9236_()).m_7726_().m_8394_(entity, new ClientboundAnimatePacket(entity, 0));
   }

   private void onRepeaterStop(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
      if (this.altModeComponent.getCurrentMode() == ExtraHachisAbility.Mode.NORMAL) {
         super.cooldownComponent.startCooldown(entity, 240.0F);
      } else if (this.altModeComponent.getCurrentMode() == ExtraHachisAbility.Mode.POELE_A_FRIRE) {
         super.cooldownComponent.startCooldown(entity, 300.0F);
      }

   }

   private boolean onAltModeChange(LivingEntity entity, IAbility ability, Mode mode) {
      if (mode == ExtraHachisAbility.Mode.NORMAL) {
         this.setDisplayIcon((AbilityCore)INSTANCE.get());
         this.setDisplayName(((AbilityCore)INSTANCE.get()).getLocalizedName());
      } else if (mode == ExtraHachisAbility.Mode.POELE_A_FRIRE) {
         this.setDisplayName(POELE_A_FRIRE_NAME);
         this.setDisplayIcon(POELE_A_FRIRE_ICON);
      }

      return true;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-9.0F, 11.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.BLACK_LEG.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)AntiMannerKickCourseAbility.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }

   public static enum Mode {
      NORMAL,
      POELE_A_FRIRE;

      // $FF: synthetic method
      private static Mode[] $values() {
         return new Mode[]{NORMAL, POELE_A_FRIRE};
      }
   }
}
