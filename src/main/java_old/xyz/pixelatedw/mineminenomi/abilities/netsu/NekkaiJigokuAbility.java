package xyz.pixelatedw.mineminenomi.abilities.netsu;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class NekkaiJigokuAbility extends Ability {
   private static final int HOLD_TIME = 600;
   private static final int MIN_COOLDOWN = 20;
   private static final int MAX_COOLDOWN = 300;
   private static final int RANGE = 30;
   public static final RegistryObject<AbilityCore<NekkaiJigokuAbility>> INSTANCE = ModRegistry.registerAbility("nekkai_jigoku", "Nekkai Jigoku", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Boils water around the user, damaging entities inside it.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, NekkaiJigokuAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(20.0F, 300.0F), ContinuousComponent.getTooltip(600.0F), RangeComponent.getTooltip(30.0F, RangeComponent.RangeType.AOE)).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addTickEvent(this::duringContinuityEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);

   public NekkaiJigokuAbility(AbilityCore<NekkaiJigokuAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.rangeComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 600.0F);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, 30.0F);
      List<BlockPos> blocks = WyHelper.getNearbyBlocks(entity, 30);

      for(LivingEntity target : targets) {
         if (target.m_20069_()) {
            target.m_6469_(entity.m_269291_().m_269549_(), 2.0F);
         }
      }

      if (!entity.m_9236_().f_46443_) {
         BlockPos.MutableBlockPos blockUp = new BlockPos.MutableBlockPos();

         for(BlockPos blockPos : blocks) {
            blockUp.m_122178_(blockPos.m_123341_(), blockPos.m_123342_() + 1, blockPos.m_123343_());
            if (entity.m_9236_().m_8055_(blockPos).m_60734_() == Blocks.f_49990_ && entity.m_9236_().m_8055_(blockUp).m_60734_() == Blocks.f_50016_ && this.continuousComponent.getContinueTime() % 5.0F == 0.0F) {
               WyHelper.spawnParticles(ParticleTypes.f_123795_, (ServerLevel)entity.m_9236_(), (double)blockPos.m_123341_() + WyHelper.randomDouble() / (double)2.0F, (double)blockPos.m_123342_() + 0.8, (double)blockPos.m_123343_() + WyHelper.randomDouble() / (double)2.0F);
            }
         }
      }

   }
}
