package xyz.pixelatedw.mineminenomi.abilities.kuku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.BreakingBlocksParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GourmetSnipeAbility extends Ability {
   private static final BlockProtectionRule GRIEF_RULE;
   private static final ArrayList<Item> FOODS;
   private static final int HOLD_TIME = 10;
   private static final int COOLDOWN = 400;
   private static final float RANGE = 2.0F;
   private static final float DAMAGE = 20.0F;
   public static final RegistryObject<AbilityCore<GourmetSnipeAbility>> INSTANCE;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private BreakingBlocksParticleEffect.Details details = new BreakingBlocksParticleEffect.Details(20);
   private int initialY;

   public GourmetSnipeAbility(AbilityCore<GourmetSnipeAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.rangeComponent, this.dealDamageComponent, this.animationComponent, this.hitTrackerComponent});
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addCanUseCheck(AbilityUseConditions::requiresMeleeWeapon);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.startContinuity(entity, 10.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.initialY = (int)entity.m_20186_();
      Vec3 speed = entity.m_20154_().m_82542_((double)6.0F, (double)6.0F, (double)6.0F);
      AbilityHelper.setDeltaMovement(entity, speed.f_82479_, speed.f_82480_, speed.f_82481_);
      this.continuousComponent.startContinuity(entity, 10.0F);
      this.animationComponent.start(entity, ModAnimations.SHOOT_SELF_FORWARD);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         Vec3 speed = entity.m_20154_().m_82542_(1.6, (double)1.0F, 1.6);
         AbilityHelper.setDeltaMovement(entity, speed.f_82479_, entity.m_20184_().m_7098_(), speed.f_82481_);
         if (entity.m_6084_()) {
            for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 2.0F)) {
               if (this.hitTrackerComponent.canHit(target)) {
                  this.dealDamageComponent.hurtTarget(entity, target, 20.0F);
               }
            }

            List<BlockPos> positions = new ArrayList();

            for(BlockPos location : WyHelper.getNearbyBlocks(entity, 2)) {
               if (location.m_123342_() >= this.initialY && NuWorld.setBlockState((Entity)entity, location, Blocks.f_50016_.m_49966_(), 3, GRIEF_RULE)) {
                  if (entity.m_9236_().f_46441_.m_188500_() > 0.4) {
                     ItemStack foodStack = new ItemStack((ItemLike)FOODS.get((int)WyHelper.randomWithRange(0, FOODS.size() - 1)));
                     entity.m_9236_().m_7967_(new ItemEntity(entity.m_9236_(), (double)location.m_123341_(), (double)location.m_123342_(), (double)location.m_123343_(), foodStack));
                  }

                  positions.add(location);
               }
            }

            if (positions.size() > 0) {
               this.details.setPositions(positions);
               WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.BREAKING_BLOCKS.get(), entity, (double)0.0F, (double)0.0F, (double)0.0F, this.details);
            }
         }

      }
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 400.0F);
   }

   static {
      GRIEF_RULE = (new BlockProtectionRule.Builder(new BlockProtectionRule[]{DefaultProtectionRules.CORE_FOLIAGE})).build();
      FOODS = new ArrayList(Arrays.asList(Items.f_42582_, Items.f_42698_, Items.f_42530_, Items.f_42410_, Items.f_42659_, Items.f_42531_, Items.f_42787_, Items.f_42486_));
      INSTANCE = ModRegistry.registerAbility("gourmet_snipe", "Gourmet Snipe", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Launches the user forward and converts everything cut into food.", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GourmetSnipeAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(400.0F), RangeComponent.getTooltip(2.0F, RangeComponent.RangeType.LINE), DealDamageComponent.getTooltip(20.0F)).setSourceType(SourceType.PHYSICAL).setSourceHakiNature(SourceHakiNature.HARDENING).build("mineminenomi");
      });
   }
}
