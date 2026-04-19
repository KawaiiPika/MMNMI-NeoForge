package xyz.pixelatedw.mineminenomi.abilities.brawler;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.entities.projectiles.CannonBallProjectile;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GenkotsuMeteorAbility extends Ability {
   private static final Component RYUSEIGUN_MODE_NAME = Component.m_237115_(ModRegistry.registerAbilityName("genkotsu_meteor_ryuseigun", "Ryuseigun"));
   private static final ResourceLocation GENKOTSU_METEOR_NORMAL_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/genkotsu_meteor.png");
   private static final ResourceLocation GENKOTSU_METEOR_RYUSEIGUN_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/genkotsu_meteor_ryuseigun.png");
   private static final float NORMAL_COOLDOWN = 60.0F;
   private static final float RYUSEIGUN_COOLDOWN = 200.0F;
   private static final float DAMAGE = 15.0F;
   public static final RegistryObject<AbilityCore<GenkotsuMeteorAbility>> INSTANCE = ModRegistry.registerAbility("genkotsu_meteor", "Genkotsu Meteor", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Throws a cannon ball from the user's hand or multiple cannon balls in §aRyuseigun§r mode", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, GenkotsuMeteorAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, (e, a) -> Component.m_237113_(name).m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GREEN)), CooldownComponent.getTooltip(60.0F), DealDamageComponent.getTooltip(15.0F)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, (e, a) -> RYUSEIGUN_MODE_NAME.m_6881_().m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GREEN)), CooldownComponent.getTooltip(200.0F), DealDamageComponent.getTooltip(15.0F)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.PHYSICAL, SourceType.INDIRECT).setNodeFactories(GenkotsuMeteorAbility::createNode).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::onContinuityStart);
   private final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addTriggerEvent(this::onRepeaterTrigger).addStopEvent(this::onRepeaterStop);
   private final AltModeComponent<Mode> altModeComponent;
   private final ProjectileComponent projectileComponent;
   private final ExplosionComponent explosionComponent;
   private ItemStack cannonBalls;

   public GenkotsuMeteorAbility(AbilityCore<GenkotsuMeteorAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<Mode>(this, Mode.class, GenkotsuMeteorAbility.Mode.NORMAL)).addChangeModeEvent(this::onAltModeChange);
      this.projectileComponent = new ProjectileComponent(this, this::createProjectile);
      this.explosionComponent = new ExplosionComponent(this);
      this.cannonBalls = null;
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.repeaterComponent, this.altModeComponent, this.projectileComponent, this.explosionComponent});
      this.addCanUseCheck(this::canUseAbility);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         this.repeaterComponent.stop(entity);
      } else {
         for(ItemStack itemStack : ItemsHelper.getAllInventoryItems(entity)) {
            if (itemStack.m_41720_().equals(ModItems.CANNON_BALL.get())) {
               this.cannonBalls = itemStack;
               break;
            }
         }

         this.continuousComponent.triggerContinuity(entity);
      }
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.altModeComponent.getCurrentMode() == GenkotsuMeteorAbility.Mode.NORMAL) {
            this.repeaterComponent.start(entity, 1, 1);
         } else if (this.altModeComponent.getCurrentMode() == GenkotsuMeteorAbility.Mode.RYUSEIGUN) {
            this.repeaterComponent.start(entity, 10, 4);
         }

      }
   }

   private void onRepeaterTrigger(LivingEntity entity, IAbility ability) {
      if (this.cannonBalls != null && this.cannonBalls.m_41613_() != 0 && !this.canUseAbility(entity, ability).isFail()) {
         this.cannonBalls.m_41774_(1);
         float inaccuracy = this.altModeComponent.getCurrentMode() == GenkotsuMeteorAbility.Mode.NORMAL ? 0.0F : 2.0F;
         this.projectileComponent.shoot(entity, 3.0F, inaccuracy);
      } else {
         WyHelper.sendMessage(entity, ModI18nAbilities.MESSAGE_NEED_FIST_OR_CANNONBALLS);
         this.repeaterComponent.stop(entity);
      }
   }

   private void onRepeaterStop(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
      if (this.altModeComponent.getCurrentMode() == GenkotsuMeteorAbility.Mode.NORMAL) {
         super.cooldownComponent.startCooldown(entity, 60.0F);
      } else if (this.altModeComponent.getCurrentMode() == GenkotsuMeteorAbility.Mode.RYUSEIGUN) {
         super.cooldownComponent.startCooldown(entity, 200.0F);
      }

   }

   private boolean onAltModeChange(LivingEntity entity, IAbility ability, Mode mode) {
      if (mode == GenkotsuMeteorAbility.Mode.NORMAL) {
         super.setDisplayIcon(GENKOTSU_METEOR_NORMAL_ICON);
      } else if (mode == GenkotsuMeteorAbility.Mode.RYUSEIGUN) {
         super.setDisplayIcon(GENKOTSU_METEOR_RYUSEIGUN_ICON);
      }

      return true;
   }

   private CannonBallProjectile createProjectile(LivingEntity entity) {
      CannonBallProjectile proj = new CannonBallProjectile(entity.m_9236_(), entity, this);
      proj.setDamage(15.0F);
      proj.addBlockHitEvent(100, (hit) -> {
         if (proj.f_19797_ >= 2) {
            AbilityExplosion explosion = this.explosionComponent.createExplosion(proj, proj.getOwner(), (double)hit.m_82425_().m_123341_(), (double)hit.m_82425_().m_123342_(), (double)hit.m_82425_().m_123343_(), 1.0F);
            explosion.setStaticDamage(5.0F);
            explosion.setDestroyBlocks(false);
            explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION2);
            explosion.m_46061_();
         }
      });
      return proj;
   }

   private Result canUseAbility(LivingEntity entity, IAbility ability) {
      return (!entity.m_21205_().m_41619_() || ItemsHelper.countItemInInventory(entity, (Item)ModItems.CANNON_BALL.get()) <= 0) && !entity.m_21205_().m_41720_().equals(ModItems.CANNON_BALL.get()) ? Result.fail(ModI18nAbilities.MESSAGE_NEED_FIST_OR_CANNONBALLS) : Result.success();
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-3.0F, 8.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.BRAWLER.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }

   public static enum Mode {
      NORMAL,
      RYUSEIGUN;

      // $FF: synthetic method
      private static Mode[] $values() {
         return new Mode[]{NORMAL, RYUSEIGUN};
      }
   }
}
