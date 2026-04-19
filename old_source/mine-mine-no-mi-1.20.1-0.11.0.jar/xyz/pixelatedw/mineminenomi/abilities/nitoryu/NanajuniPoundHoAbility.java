package xyz.pixelatedw.mineminenomi.abilities.nitoryu;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.SanjurokuPoundHoAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.nitoryu.NanajuniPoundHoProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class NanajuniPoundHoAbility extends Ability {
   private static final float COOLDOWN = 320.0F;
   private static final float DAMAGE = 30.0F;
   private static final int ANIMATION_TICKS = 7;
   private static final int DORIKI = 5000;
   private static final int MARKSMANSHIP_POINTS = 10;
   private static final int WEAPON_MASTERY_POINTS = 20;
   public static final RegistryObject<AbilityCore<NanajuniPoundHoAbility>> INSTANCE = ModRegistry.registerAbility("nanajuni_pound_ho", "Nanajuni Pound Ho", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Same principle as the Sanjuroku Pound Ho. Holding their two swords horizontally above the shoulder, the user then performs a circular swing that launches two air compressed projectiles spiraling towards the target instead of one, making it twice as powerful.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, NanajuniPoundHoAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(320.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.SLASH).setSourceElement(SourceElement.AIR).setNodeFactories(NanajuniPoundHoAbility::createNode).build("mineminenomi");
   });
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);

   public NanajuniPoundHoAbility(AbilityCore<NanajuniPoundHoAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.animationComponent, this.projectileComponent, this.explosionComponent});
      this.explosionComponent.setBlocksAffectedLimit(218);
      this.addCanUseCheck(AbilityUseConditions::requiresSword);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      ItemStack stack = entity.m_21205_();
      stack.m_41622_(1, entity, (user) -> user.m_21166_(EquipmentSlot.MAINHAND));
      this.animationComponent.start(entity, ModAnimations.OVER_SHOULDER_SLASH, 7);
      this.projectileComponent.shoot(entity);
      if (!entity.m_9236_().f_46443_) {
         ((ServerLevel)entity.m_9236_()).m_7726_().m_8394_(entity, new ClientboundAnimatePacket(entity, 0));
      }

      this.cooldownComponent.startCooldown(entity, 320.0F);
   }

   private NanajuniPoundHoProjectile createProjectile(LivingEntity entity) {
      NanajuniPoundHoProjectile proj = new NanajuniPoundHoProjectile(entity.m_9236_(), entity, this);
      proj.setDamage(30.0F);
      return proj;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode nanajuniPoundHo = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(13.0F, -4.0F));
      NodeUnlockCondition unlockCondition = DorikiUnlockCondition.atLeast((double)5000.0F).and(TrainingPointsUnlockCondition.marksmanship((double)10.0F).and(TrainingPointsUnlockCondition.weaponMastery((double)20.0F)));
      NodeUnlockAction unlockAction = SubtractPointsAction.marksmanship(10).andThen(SubtractPointsAction.weaponMastery(20).andThen(UnlockAbilityAction.unlock(INSTANCE)));
      nanajuniPoundHo.addPrerequisites(((AbilityCore)SanjurokuPoundHoAbility.INSTANCE.get()).getNode(entity));
      nanajuniPoundHo.setUnlockRule(unlockCondition, unlockAction);
      return nanajuniPoundHo;
   }
}
