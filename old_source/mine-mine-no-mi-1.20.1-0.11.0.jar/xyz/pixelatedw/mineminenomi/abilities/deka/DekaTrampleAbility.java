package xyz.pixelatedw.mineminenomi.abilities.deka;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.particles.effects.BreakingBlocksParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DekaTrampleAbility extends PassiveAbility {
   private static final float TRAMPLE_AREA = 5.0F;
   private static final int BLOCK_BREAKING_AREA = 7;
   private static final float MAX_SPEED = 0.45F;
   private static final float DAMAGE = 8.0F;
   public static final RegistryObject<AbilityCore<DekaTrampleAbility>> INSTANCE = ModRegistry.registerAbility("deka_trample", "Deka Trample", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Running speed increases with acceleration trampling any nearby entity.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, DekaTrampleAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.DEKA)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, RangeComponent.getTooltip(5.0F, RangeComponent.RangeType.AOE), DealDamageComponent.getTooltip(8.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private BreakingBlocksParticleEffect.Details details = new BreakingBlocksParticleEffect.Details(100);
   public float speed = 0.0F;

   public DekaTrampleAbility(AbilityCore<DekaTrampleAbility> ability) {
      super(ability);
      this.addComponents(new AbilityComponent[]{this.rangeComponent, this.dealDamageComponent});
      this.addCanUseCheck((entity, abl) -> AbilityUseConditions.requiresMorph(entity, abl, (MorphInfo)ModMorphs.DEKA.get()));
      this.addDuringPassiveEvent(this::duringPassiveEvent);
   }

   public void duringPassiveEvent(LivingEntity entity) {
      if (entity.m_20096_()) {
         boolean isDekaActive = (Boolean)AbilityCapability.get(entity).map((props) -> (DekaDekaAbility)props.getEquippedAbility((AbilityCore)DekaDekaAbility.INSTANCE.get())).map((abl) -> abl.isContinuous()).orElse(false);
         if (isDekaActive) {
            if (!entity.m_20142_()) {
               this.speed = 0.0F;
            } else {
               List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, entity.m_20183_(), 5.0F);
               float acceleration = 0.004F;
               acceleration *= this.speed > 0.0F ? 1.0F - this.speed / 0.45F : 1.0F;
               if (!(entity.f_20902_ > 0.0F) || entity.f_19862_) {
                  acceleration = -0.044999998F;
               }

               this.speed = Mth.m_14036_(this.speed + acceleration, 0.2F, 0.45F);
               int d2 = entity.f_20902_ > 0.0F ? 1 : 0;
               Vec3 vec = entity.m_20154_();
               double x = vec.f_82479_ * (double)this.speed * (double)d2;
               double z = vec.f_82481_ * (double)this.speed * (double)d2;
               AbilityHelper.setDeltaMovement(entity, x, entity.m_20184_().f_82480_, z);
               if (!entity.m_9236_().f_46443_) {
                  if (entity.f_19797_ % 20 == 0) {
                     List<BlockPos> blocks = WyHelper.getNearbyBlocks(entity.m_20183_(), entity.m_9236_(), 7, 7, 7, (state) -> !state.m_60795_() && state.m_204336_(ModTags.Blocks.BLOCK_PROT_FOLIAGE));
                     List<BlockPos> positions = new ArrayList();

                     for(BlockPos pos : blocks) {
                        if (NuWorld.setBlockState((Entity)entity, pos, Blocks.f_50016_.m_49966_(), 3, DefaultProtectionRules.FOLIAGE)) {
                           positions.add(pos);
                        }
                     }

                     if (positions.size() > 0) {
                        this.details.setPositions(positions);
                        WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.BREAKING_BLOCKS.get(), entity, (double)0.0F, (double)0.0F, (double)0.0F, this.details);
                     }
                  }

                  for(LivingEntity target : targets) {
                     if (this.dealDamageComponent.hurtTarget(entity, target, 8.0F)) {
                        Vec3 speed = entity.m_20154_().m_82542_((double)2.0F, (double)1.0F, (double)2.0F);
                        AbilityHelper.setDeltaMovement(target, speed.f_82479_, 0.2, speed.f_82481_);
                     }
                  }
               }
            }

         }
      }
   }
}
