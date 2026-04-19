package xyz.pixelatedw.mineminenomi.abilities.ori;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BlockTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ori.AwaseBaoriProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class AwaseBaoriAbility extends Ability {
   private static final int COOLDOWN = 200;
   private static final int HOLD_TIME = 100;
   public static final RegistryObject<AbilityCore<AwaseBaoriAbility>> INSTANCE = ModRegistry.registerAbility("awase_baori", "Awase Baori", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Launches a short range projectile that creates a small cage around the hit target.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AwaseBaoriAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ContinuousComponent.getTooltip(100.0F)).setSourceHakiNature(SourceHakiNature.SPECIAL).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final BlockTrackerComponent blockTrackerComponent = new BlockTrackerComponent(this);
   private AwaseBaoriProjectile proj;

   public AwaseBaoriAbility(AbilityCore<AwaseBaoriAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.projectileComponent, this.blockTrackerComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 200.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.blockTrackerComponent.clearPositions();
      this.proj = (AwaseBaoriProjectile)this.projectileComponent.getNewProjectile(entity);
      this.projectileComponent.shoot(this.proj, entity, entity.m_146909_(), entity.m_146908_(), 2.0F, 1.0F);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (this.blockTrackerComponent.getPositions().isEmpty() && (this.proj == null || !this.proj.m_6084_())) {
         this.continuousComponent.stopContinuity(entity);
      }
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      if (this.proj != null) {
         this.proj.m_142687_(RemovalReason.DISCARDED);
      }

      for(BlockPos pos : this.blockTrackerComponent.getPositions()) {
         entity.m_9236_().m_46597_(pos, Blocks.f_50016_.m_49966_());
      }

      this.cooldownComponent.startCooldown(entity, 200.0F);
   }

   private AwaseBaoriProjectile createProjectile(LivingEntity entity) {
      AwaseBaoriProjectile proj = new AwaseBaoriProjectile(entity.m_9236_(), entity, this);
      return proj;
   }
}
