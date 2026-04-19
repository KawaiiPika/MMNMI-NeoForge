package xyz.pixelatedw.mineminenomi.abilities.bari;

import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bari.BarrierbilityStairsProjectile;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BarrierbilityStairsAbility extends Ability {
   private static final int COOLDOWN = 100;
   public static final RegistryObject<AbilityCore<BarrierbilityStairsAbility>> INSTANCE = ModRegistry.registerAbility("barrierbility_stairs", "Barrierbility Stairs", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("By shaping the Barrier, the user can create stairs.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BarrierbilityStairsAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F), ContinuousComponent.getTooltip()).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final BlockTrackerComponent blockTrackerComponent = new BlockTrackerComponent(this);

   public BarrierbilityStairsAbility(AbilityCore<BarrierbilityStairsAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.projectileComponent, this.blockTrackerComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity, 2.0F, 1.0F);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      for(BlockPos pos : this.blockTrackerComponent.getPositions()) {
         if (entity.m_9236_().m_8055_(pos).m_60734_() == ModBlocks.BARRIER.get()) {
            entity.m_9236_().m_46597_(pos, Blocks.f_50016_.m_49966_());
         }
      }

      this.blockTrackerComponent.clearPositions();
      this.cooldownComponent.startCooldown(entity, 100.0F);
   }

   public void fillBlocksList(Set<BlockPos> blockPositions) {
      this.blockTrackerComponent.addPositions(blockPositions);
   }

   private BarrierbilityStairsProjectile createProjectile(LivingEntity entity) {
      BarrierbilityStairsProjectile proj = new BarrierbilityStairsProjectile(entity.m_9236_(), entity, this);
      proj.m_6034_(proj.m_20185_(), proj.m_20186_(), proj.m_20189_());
      return proj;
   }
}
