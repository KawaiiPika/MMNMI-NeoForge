package xyz.pixelatedw.mineminenomi.abilities.ryuallosaurus;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class AncientTailSpinAbility extends Ability {
   private static final int COOLDOWN = 200;
   private static final float RANGE = 5.5F;
   private static final float DAMAGE = 15.0F;
   public static final RegistryObject<AbilityCore<AncientTailSpinAbility>> INSTANCE = ModRegistry.registerAbility("ancient_tail_spin", "Ancient Tail Spin", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Spins around hitting all nearby enemies with the user's tail.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AncientTailSpinAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.ALLOSAURUS_HEAVY, ModMorphs.ALLOSAURUS_WALK)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), RangeComponent.getTooltip(5.5F, RangeComponent.RangeType.AOE), DealDamageComponent.getTooltip(15.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);

   public AncientTailSpinAbility(AbilityCore<AncientTailSpinAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.animationComponent, this.rangeComponent, this.dealDamageComponent});
      this.addCanUseCheck(RyuAllosaurusHelper::requiresEitherPoint);
      this.addContinueUseCheck(RyuAllosaurusHelper::requiresEitherPoint);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      List<LivingEntity> list = this.rangeComponent.getTargetsInArea(entity, entity.m_20183_(), 5.5F);
      this.animationComponent.start(entity, ModAnimations.YAW_SPIN, 10);
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.SPIN.get(), SoundSource.PLAYERS, 2.0F, 0.75F + entity.m_217043_().m_188501_() / 4.0F);

      for(LivingEntity target : list) {
         if (this.dealDamageComponent.hurtTarget(entity, target, 15.0F)) {
            Vec3 dist = target.m_20182_().m_82546_(entity.m_20182_()).m_82520_((double)0.0F, (double)-1.0F, (double)0.0F);
            double speedReduction = (double)2.0F;
            double xSpeed = -dist.f_82479_ / speedReduction;
            double zSpeed = -dist.f_82481_ / speedReduction;
            AbilityHelper.setDeltaMovement(target, -xSpeed, (double)0.1F, -zSpeed);
         }
      }

      this.cooldownComponent.startCooldown(entity, 200.0F);
   }
}
