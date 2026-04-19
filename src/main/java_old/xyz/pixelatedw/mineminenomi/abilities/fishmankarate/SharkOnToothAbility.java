package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import java.util.Iterator;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class SharkOnToothAbility extends Ability {
   private static final int COOLDOWN = 300;
   private static final int HOLD_TIME = 15;
   private static final float RANGE = 1.5F;
   private static final float DAMAGE = 40.0F;
   public static final RegistryObject<AbilityCore<SharkOnToothAbility>> INSTANCE = ModRegistry.registerAbility("shark_on_tooth", "Shark on Tooth", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Launches the user forward while spinning, biting the first enemy it comes in contact with.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, SharkOnToothAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F), DealDamageComponent.getTooltip(40.0F), RangeComponent.getTooltip(1.5F, RangeComponent.RangeType.LINE)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.PHYSICAL).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(100, this::startContinuityEvent).addTickEvent(100, this::tickContinuityEvent).addEndEvent(100, this::endContinuityEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private boolean hitTarget = false;

   public SharkOnToothAbility(AbilityCore<SharkOnToothAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.rangeComponent, this.dealDamageComponent, this.animationComponent});
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.startContinuity(entity, 15.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.hitTarget = false;
      this.animationComponent.start(entity, ModAnimations.SHARK_ON_TOOTH);
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.DASH_ABILITY_SWOOSH_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity.m_6084_()) {
         Vec3 look = entity.m_20154_();
         Vec3 speed = look.m_82542_(2.3, (double)0.0F, 2.3);
         entity.m_6478_(MoverType.SELF, speed);
         if (!this.hitTarget) {
            List<LivingEntity> targets = this.rangeComponent.getTargetsInLine(entity, 1.0F, 1.5F);
            Iterator var6 = targets.iterator();
            if (var6.hasNext()) {
               LivingEntity target = (LivingEntity)var6.next();
               this.dealDamageComponent.hurtTarget(entity, target, 40.0F);
               this.hitTarget = true;
            }
         }
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 300.0F);
   }

   public boolean hasHitTarget() {
      return this.hitTarget;
   }
}
