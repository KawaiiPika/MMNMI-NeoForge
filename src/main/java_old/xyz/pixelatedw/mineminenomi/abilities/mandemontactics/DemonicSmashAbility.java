package xyz.pixelatedw.mineminenomi.abilities.mandemontactics;

import java.util.List;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
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
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.math.VectorHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class DemonicSmashAbility extends Ability {
   private static final int COOLDOWN = 160;
   private static final float RANGE = 1.25F;
   private static final float DAMAGE = 20.0F;
   public static final TargetPredicate TARGET_TEST = (new TargetPredicate()).testEnemyFaction().testSimpleInView();
   public static final RegistryObject<AbilityCore<DemonicSmashAbility>> INSTANCE = ModRegistry.registerAbility("demonic_smash", "Demonic Smash", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, DemonicSmashAbility::new)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(160.0F), RangeComponent.getTooltip(1.25F, RangeComponent.RangeType.LINE), DealDamageComponent.getTooltip(20.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.BLUNT).build("mineminenomi"));
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private boolean isLookingRight = true;

   public DemonicSmashAbility(AbilityCore<DemonicSmashAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.rangeComponent, this.dealDamageComponent, this.animationComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresBluntWeapon);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      List<LivingEntity> targets = this.rangeComponent.getTargetsInLine(entity, 1.25F, 1.5F);
      this.isLookingRight = entity.m_217043_().m_188499_();
      this.animationComponent.start(entity, ModAnimations.DEMONIC_SMASH, 7);
      Vec3 dir = VectorHelper.getRelativeOffset(entity.m_20182_(), entity.m_146908_(), new Vec3(this.isLookingRight ? (double)-5.0F : (double)5.0F, (double)0.0F, (double)0.0F));
      Vec3 move = entity.m_20182_().m_82546_(dir);

      for(LivingEntity target : targets) {
         ItemsHelper.stopShieldAndStartCooldown(target, 100);
         if (this.dealDamageComponent.hurtTarget(entity, target, 20.0F)) {
            AbilityHelper.setDeltaMovement(target, move);
         }
      }

      this.cooldownComponent.startCooldown(entity, 160.0F);
   }
}
