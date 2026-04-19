package xyz.pixelatedw.mineminenomi.abilities.bara;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.OutOfBodyAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.entities.BottomHalfBodyEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BaraSplitAbility extends OutOfBodyAbility {
   private static final int HOLD_TIME = 1000;
   private static final int MIN_COOLDOWN = 100;
   private static final int MAX_COOLDOWN = 1000;
   public static final RegistryObject<AbilityCore<BaraSplitAbility>> INSTANCE = ModRegistry.registerAbility("bara_split", "Bara Split", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user to split its upper part of the body from the lower.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BaraSplitAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F, 1000.0F), ContinuousComponent.getTooltip(1000.0F)).build("mineminenomi");
   });
   private final MorphComponent morphComponent = new MorphComponent(this);
   private final PoolComponent poolComponent;
   private BottomHalfBodyEntity legs;

   public BaraSplitAbility(AbilityCore<BaraSplitAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.BARA_ABILITY, new AbilityPool[0]);
      this.addComponents(new AbilityComponent[]{this.morphComponent, this.poolComponent});
      this.continuousComponent.addStartEvent(this::startContinuityEvent);
      this.continuousComponent.addTickEvent(this::duringContinuityEvent);
      this.continuousComponent.addEndEvent(this::endContinuityEvent);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.morphComponent.startMorph(entity, (MorphInfo)ModMorphs.BARA_SPLIT.get());
      AbilityHelper.setDeltaMovement(entity, (double)0.0F, (double)2.0F, (double)0.0F);
      this.legs = new BottomHalfBodyEntity(entity.m_9236_(), entity);
      this.legs.m_7678_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity.m_146908_(), entity.m_146909_());
      entity.m_9236_().m_7967_(this.legs);
      this.legs.setParentAbility(this);
      this.setOriginalBody(this.legs);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         float time = this.continuousComponent.getContinueTime();
         if (this.legs != null) {
            this.setPivotPoint(this.legs.m_20183_());
         }

         if (time > 5.0F && entity.m_20096_()) {
            this.continuousComponent.stopContinuity(entity);
         }
      }
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.morphComponent.stopMorph(entity);
      float cooldown = Math.max(100.0F, this.continuousComponent.getContinueTime());
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   public int getHoldTime() {
      return 1000;
   }

   public float getMaxRange() {
      return 40.0F;
   }

   public boolean isPhysical() {
      return true;
   }
}
