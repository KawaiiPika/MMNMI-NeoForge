package xyz.pixelatedw.mineminenomi.abilities.suna;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BlockTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockQueue;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.api.util.HashBlockPos;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GroundDeathAbility extends Ability {
   private static final float COOLDOWN_BONUS = 0.8F;
   private static final float RANGE_BONUS = 1.5F;
   private static final int COOLDOWN = 200;
   private static final int CHARGE_TIME = 60;
   private static final int SAND_RANGE_XZ = 256;
   private static final int SAND_RANGE_Y = 8;
   private static final float ENTITY_EFFECT_RANGE = 5.0F;
   public static final RegistryObject<AbilityCore<GroundDeathAbility>> INSTANCE = ModRegistry.registerAbility("ground_death", "Ground Death", (id, name) -> {
      Pair[] var10002 = new Pair[]{ImmutablePair.of("Dries out the surrounding ground turning everything into sand.", (Object)null), null};
      Object[] var10006 = new Object[]{"§a" + Math.round(19.999998F) + "%§r", null};
      float var10009 = Math.abs(-0.5F);
      var10006[1] = "§a" + Math.round(var10009 * 100.0F) + "%§r";
      var10002[1] = ImmutablePair.of("While in a desert the cooldown of this ability is reduced by %s and range is increased by %s.", var10006);
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, var10002);
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GroundDeathAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ChargeComponent.getTooltip(60.0F), RangeComponent.getTooltip(256.0F, RangeComponent.RangeType.AOE)).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this, (comp) -> (double)comp.getChargePercentage() > 0.2)).addStartEvent(this::startChargeEvent).addTickEvent(this::duringChargeEvent).addEndEvent(this::endChargeEvent);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final BlockTrackerComponent blockTrackerComponent = new BlockTrackerComponent(this);

   public GroundDeathAbility(AbilityCore<GroundDeathAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.hitTrackerComponent, this.rangeComponent, this.blockTrackerComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresDryUser);
      this.addCanUseCheck(AbilityUseConditions::requiresOnGround);
      this.addCanUseCheck(SunaHelper::requiresInactiveDesertGirasole);
      super.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 60.0F);
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.blockTrackerComponent.initQueue(entity.m_9236_(), 1000);
         this.rangeComponent.getBonusManager().removeBonus(SunaHelper.DESERT_RANGE_BONUS);
         if (SunaHelper.isFruitBoosted(entity)) {
            this.rangeComponent.getBonusManager().addBonus(SunaHelper.DESERT_RANGE_BONUS, "Desert Range Bonus", BonusOperation.MUL, 1.5F);
         }

         BlockQueue.IAfterPlaceBlock placementCallback = (posx, oldState, newState) -> {
            if (posx.hashCode() % 10 == 0) {
               for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, posx, 5.0F)) {
                  if (this.hitTrackerComponent.canHit(target)) {
                     target.m_7292_(new MobEffectInstance(MobEffects.f_19599_, 200, 3, false, false));
                     target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DEHYDRATION.get(), 200, 3, false, false));
                     WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GROUND_DEATH.get(), entity, target.m_20185_(), target.m_20186_(), target.m_20189_());
                  }
               }

            }
         };
         double maxDistance = (double)2048.0F;
         BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

         for(int y = -8; y < 8; ++y) {
            for(float x = -256.0F; x < 256.0F; ++x) {
               for(float z = -256.0F; z < 256.0F; ++z) {
                  double distance = (double)(x * x + z * z + (float)(y * y));
                  if (!(distance >= (double)2048.0F) && !(this.random.nextDouble() < distance / (double)2048.0F)) {
                     double posX = entity.m_20185_() + (double)x;
                     double posY = entity.m_20186_() + (double)y;
                     double posZ = entity.m_20189_() + (double)z;
                     mutpos.m_122169_(posX, posY, posZ);
                     HashBlockPos pos = new HashBlockPos(mutpos.m_7949_(), (int)(x * x + (float)(y * y) + z * z));
                     this.blockTrackerComponent.addBlockToQueue(pos, Blocks.f_49992_.m_49966_(), 3, DefaultProtectionRules.CORE_FOLIAGE_ORE_LIQUID, placementCallback);
                  }
               }
            }
         }

      }
   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 2, 1, false, false));
         this.blockTrackerComponent.tickBlockQueue(entity.m_9236_());
      }
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.cooldownComponent.getBonusManager().removeBonus(SunaHelper.DESERT_COOLDOWN_BONUS);
         if (SunaHelper.isFruitBoosted(entity)) {
            this.cooldownComponent.getBonusManager().addBonus(SunaHelper.DESERT_COOLDOWN_BONUS, "Desert Cooldown Bonus", BonusOperation.MUL, 0.8F);
         }

         this.cooldownComponent.startCooldown(entity, 200.0F);
      }
   }
}
