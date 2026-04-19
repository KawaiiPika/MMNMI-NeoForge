package xyz.pixelatedw.mineminenomi.abilities.ryupteranodon;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class BeakGrabAbility extends Ability {
   private static final int COOLDOWN = 200;
   private static final int HOLD_TIME = 300;
   private static final float RANGE = 1.2F;
   public static final RegistryObject<AbilityCore<BeakGrabAbility>> INSTANCE = ModRegistry.registerAbility("beak_grab", "Beak Grab", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user to carry their target.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BeakGrabAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.PTERA_FLY)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ContinuousComponent.getTooltip(300.0F), RangeComponent.getTooltip(1.2F, RangeComponent.RangeType.LINE)).setSourceHakiNature(SourceHakiNature.HARDENING).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::onContinuityStart).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final GrabEntityComponent grabEntityComponent = new GrabEntityComponent(this, false, true, 2.0F);

   public BeakGrabAbility(AbilityCore<BeakGrabAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.animationComponent, this.rangeComponent, this.grabEntityComponent});
      this.addCanUseCheck(RyuPteraHelper::requiresFlyPoint);
      this.addContinueUseCheck(RyuPteraHelper::requiresFlyPoint);
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 300.0F);
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      Level level = entity.m_9236_();
      if (level != null && !level.f_46443_) {
         this.animationComponent.start(entity, ModAnimations.PTERA_OPEN_MOUTH);
         level.m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.DASH_ABILITY_SWOOSH_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
      }
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      Level level = entity.m_9236_();
      if (level != null && !level.f_46443_) {
         LivingEntity grabbedTarget = this.grabEntityComponent.getGrabbedEntity();
         if (!this.canUse(entity).isFail() && (grabbedTarget == null || this.grabEntityComponent.canContinueGrab(entity))) {
            if (grabbedTarget == null) {
               this.grabEntityComponent.grabNearest(entity, 1.2F, 1.4F, false);
            }

         } else {
            this.continuousComponent.stopContinuity(entity);
         }
      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      Level level = entity.m_9236_();
      if (level != null && !level.f_46443_) {
         this.grabEntityComponent.release(entity);
         this.animationComponent.stop(entity);
         this.cooldownComponent.startCooldown(entity, 200.0F);
      }
   }
}
