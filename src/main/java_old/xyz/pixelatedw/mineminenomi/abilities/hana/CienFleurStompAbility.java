package xyz.pixelatedw.mineminenomi.abilities.hana;

import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hana.HanaFeetEntity;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class CienFleurStompAbility extends Ability {
   private static final int COOLDOWN = 200;
   private static final int TRIGGERS = 7;
   private static final int INTERVAL = 4;
   private static final int RANGE = 10;
   public static final RegistryObject<AbilityCore<CienFleurStompAbility>> INSTANCE = ModRegistry.registerAbility("cien_fleur_stomp", "Cien Fleur: Stomp", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Stomps the ground in front of the user using giant feet.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, CienFleurStompAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addTriggerEvent(this::triggerRepeaterEvent).addStopEvent(this::stopRepeaterEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private Iterator<BlockPos> targetedBlocks;

   public CienFleurStompAbility(AbilityCore<CienFleurStompAbility> ability) {
      super(ability);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.repeaterComponent, this.projectileComponent, this.animationComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.CROSSED_ARMS);
      HanaHelper.spawnBlossomEffect(entity);
      Predicate<BlockPos> predicate = (pos) -> entity.m_9236_().m_8055_(pos.m_7494_()).m_60795_() && (double)pos.m_123342_() > entity.m_20186_() - (double)3.0F;
      Vec3 look = entity.m_20182_().m_82549_(entity.m_20154_().m_82542_((double)7.0F, (double)1.0F, (double)7.0F));
      BlockPos ogPos = BlockPos.m_274561_(look.m_7096_(), entity.m_20186_(), look.m_7094_());
      List<BlockPos> poses = WyHelper.getNearbyBlocks(ogPos, entity.m_9236_(), 10, predicate, ImmutableList.of(Blocks.f_50016_));
      this.targetedBlocks = WyHelper.shuffle(poses).stream().limit(7L).iterator();
      this.repeaterComponent.start(entity, 7, 4);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 200.0F);
   }

   private void triggerRepeaterEvent(LivingEntity entity, IAbility ability) {
      if (this.targetedBlocks != null && this.targetedBlocks.hasNext()) {
         BlockPos pos = (BlockPos)this.targetedBlocks.next();
         HanaFeetEntity stompFeet = (HanaFeetEntity)this.projectileComponent.getNewProjectile(entity);
         stompFeet.m_6027_((double)pos.m_123341_(), (double)(pos.m_123342_() + 15), (double)pos.m_123343_());
         AbilityHelper.setDeltaMovement(stompFeet, (double)0.0F, -0.9, (double)0.0F);
         entity.m_9236_().m_7967_(stompFeet);
      }
   }

   private void stopRepeaterEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
   }

   private HanaFeetEntity createProjectile(LivingEntity entity) {
      HanaFeetEntity stompFeet = new HanaFeetEntity(entity.m_9236_(), entity, this);
      return stompFeet;
   }
}
