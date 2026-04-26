package xyz.pixelatedw.mineminenomi.abilities.ittoryu;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ittoryu.YakkodoriProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class YakkodoriAbility extends Ability {
   private static final float COOLDOWN = 200.0F;
   private static final float DAMAGE = 15.0F;
   private static final int ANIMATION_TICKS = 7;
   private static final int DORIKI = 5000;
   private static final int MARKSMANSHIP_POINTS = 10;
   private static final int WEAPON_MASTERY_POINTS = 20;
   public static final RegistryObject<AbilityCore<YakkodoriAbility>> INSTANCE = ModRegistry.registerAbility("yakkodori", "Yakkodori", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Launches a crescent moon-shaped slash, which destroys everything in its path", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, YakkodoriAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), DealDamageComponent.getTooltip(15.0F)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.SLASH).setSourceElement(SourceElement.AIR).setNodeFactories(YakkodoriAbility::createNode).build("mineminenomi");
   });
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);

   public YakkodoriAbility(AbilityCore<YakkodoriAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.animationComponent, this.projectileComponent, this.explosionComponent});
      this.explosionComponent.setBlocksAffectedLimit(512);
      this.addCanUseCheck(AbilityUseConditions::requiresSword);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      ItemStack stack = entity.m_21205_();
      stack.m_41622_(1, entity, (user) -> user.m_21166_(EquipmentSlot.MAINHAND));
      this.animationComponent.start(entity, ModAnimations.DOWNWARD_SLASH, 7);
      this.projectileComponent.shoot(entity, 2.0F, 1.0F);
      if (!entity.m_9236_().f_46443_) {
         ((ServerLevel)entity.m_9236_()).m_7726_().m_8394_(entity, new ClientboundAnimatePacket(entity, 0));
      }

      this.cooldownComponent.startCooldown(entity, 200.0F);
   }

   private YakkodoriProjectile createProjectile(LivingEntity entity) {
      YakkodoriProjectile proj = new YakkodoriProjectile(entity.m_9236_(), entity, this);
      proj.setDamage(15.0F);
      return proj;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode yakkodori = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-6.0F, -1.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.SWORDSMAN.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      yakkodori.addPrerequisites(((AbilityCore)ShiShishiSonsonAbility.INSTANCE.get()).getNode(entity));
      yakkodori.setUnlockRule(unlockCondition, unlockAction);
      return yakkodori;
   }
}
