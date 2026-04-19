package xyz.pixelatedw.mineminenomi.abilities.magu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class LavaFlowAbility extends Ability {
   private static final int COOLDOWN = 300;
   private static final int HOLD_TIME = 100;
   public static final RegistryObject<AbilityCore<LavaFlowAbility>> INSTANCE = ModRegistry.registerAbility("lava_flow", "Lava Flow", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user covers their legs into lava creating a path while walking trough it", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, LavaFlowAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F), ContinuousComponent.getTooltip(100.0F)).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::onContinuityStart).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   private int originY = -1;

   public LavaFlowAbility(AbilityCore<LavaFlowAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent});
      this.addUseEvent(this::onUse);
   }

   private void onUse(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 100.0F);
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      this.originY = entity.m_20183_().m_123342_() - 5;
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.originY >= 0) {
            BlockPos pos = entity.m_20183_().m_7495_();
            boolean isBlockBelowOrigin = pos.m_123342_() < this.originY;
            boolean areEyesInLava = entity.isEyeInFluidType((FluidType)ForgeMod.LAVA_TYPE.get());
            if (!areEyesInLava) {
               MaguHelper.generateLavaPool(entity.m_9236_(), entity.m_20183_().m_6625_(2), 4);
            } else if (areEyesInLava && !isBlockBelowOrigin) {
               MaguHelper.generateLavaPool(entity.m_9236_(), entity.m_20183_().m_6630_(1), 3);
            }

         }
      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      super.cooldownComponent.startCooldown(entity, 300.0F);
   }
}
