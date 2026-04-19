package xyz.pixelatedw.mineminenomi.abilities.moku;

import java.util.ArrayList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class WhitePullAbility extends Ability {
   private static final int COOLDOWN = 200;
   private static final int RANGE = 100;
   public static final RegistryObject<AbilityCore<WhitePullAbility>> INSTANCE = ModRegistry.registerAbility("white_pull", "White Pull", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Pulls all nearby entities surrounded by smoke towards the user.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, WhitePullAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), RangeComponent.getTooltip(100.0F, RangeComponent.RangeType.AOE)).setSourceHakiNature(SourceHakiNature.SPECIAL).build("mineminenomi");
   });
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   private ArrayList<LivingEntity> targetsToRemove = new ArrayList();

   public WhitePullAbility(AbilityCore<WhitePullAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.rangeComponent, this.continuousComponent});
      this.addUseEvent(this::useEvent);
   }

   public void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 100.0F);
   }

   public void onContinuityTick(LivingEntity entity, IAbility ability) {
      for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 100.0F)) {
         if (target.m_21023_((MobEffect)ModEffects.SMOKE.get())) {
            AbilityHelper.setDeltaMovement(target, entity.m_20182_().m_82546_(target.m_20182_()).m_82541_().m_82490_((double)0.5F));
            if (!this.targetsToRemove.contains(target)) {
               this.targetsToRemove.add(target);
            }
         }
      }

   }

   public void onContinuityEnd(LivingEntity entity, IAbility ability) {
      for(LivingEntity target : this.targetsToRemove) {
         target.m_21195_((MobEffect)ModEffects.SMOKE.get());
      }

      this.targetsToRemove.clear();
      this.cooldownComponent.startCooldown(entity, 200.0F);
   }
}
