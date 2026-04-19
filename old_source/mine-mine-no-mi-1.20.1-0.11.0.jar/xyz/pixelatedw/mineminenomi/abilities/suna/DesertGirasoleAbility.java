package xyz.pixelatedw.mineminenomi.abilities.suna;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockQueue;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.api.util.HashBlockPos;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DesertGirasoleAbility extends Ability {
   private static final float COOLDOWN_BONUS = 0.8F;
   private static final float RANGE_BONUS = 2.0F;
   private static final int COOLDOWN = 600;
   private static final int CHARGE_TIME = 100;
   private static final int SAND_RANGE_XZ = 200;
   private static final int SAND_RANGE_Y = 4;
   public static final RegistryObject<AbilityCore<DesertGirasoleAbility>> INSTANCE = ModRegistry.registerAbility("desert_girasole", "Desert Girasole", (id, name) -> {
      Pair[] var10002 = new Pair[]{ImmutablePair.of("A giant pit of quicksand will be formed.", (Object)null), ImmutablePair.of("Can only be used on sand.", (Object)null), null};
      Object[] var10006 = new Object[]{"§a" + Math.round(19.999998F) + "%§r", null};
      float var10009 = Math.abs(-1.0F);
      var10006[1] = "§a" + Math.round(var10009 * 100.0F) + "%§r";
      var10002[2] = ImmutablePair.of("While in a desert the cooldown of this ability is reduced by %s and range is increased by %s.", var10006);
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, var10002);
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, DesertGirasoleAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(600.0F), ChargeComponent.getTooltip(100.0F), RangeComponent.getTooltip(200.0F, RangeComponent.RangeType.AOE)).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::startChargeEvent).addTickEvent(this::duringChargeEvent).addEndEvent(this::endChargingEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final BlockTrackerComponent blockTrackerComponent = new BlockTrackerComponent(this);

   public DesertGirasoleAbility(AbilityCore<DesertGirasoleAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.rangeComponent, this.chargeComponent, this.blockTrackerComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresDryUser);
      this.addCanUseCheck(AbilityUseConditions::requiresOnGround);
      this.addCanUseCheck(SunaHelper::requiresInactiveGroundDeath);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 100.0F);
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      this.rangeComponent.getBonusManager().removeBonus(SunaHelper.DESERT_RANGE_BONUS);
      if (SunaHelper.isFruitBoosted(entity)) {
         this.rangeComponent.getBonusManager().addBonus(SunaHelper.DESERT_RANGE_BONUS, "Desert Range Bonus", BonusOperation.MUL, 2.0F);
      }

      this.blockTrackerComponent.initQueue(entity.m_9236_(), 1000);
      double maxDistance = (double)800.0F;
      BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

      for(int y = -4; y < 4; ++y) {
         for(float x = -200.0F; x < 200.0F; ++x) {
            for(float z = -200.0F; z < 200.0F; ++z) {
               double distance = (double)(x * x + z * z + (float)(y * y));
               if (!(distance >= (double)800.0F) && !(this.random.nextDouble() < distance / (double)800.0F)) {
                  double posX = entity.m_20185_() + (double)x;
                  double posY = entity.m_20186_() + (double)y;
                  double posZ = entity.m_20189_() + (double)z;
                  mutpos.m_122169_(posX, posY, posZ);
                  if (entity.m_9236_().m_8055_(mutpos).m_60734_().equals(Blocks.f_49992_)) {
                     HashBlockPos pos = new HashBlockPos(mutpos.m_7949_(), (int)(x * x + (float)(y * y) + z * z));
                     this.blockTrackerComponent.addBlockToQueue(pos, ((Block)ModBlocks.SUNA_SAND.get()).m_49966_(), 3, DefaultProtectionRules.CORE_FOLIAGE_ORE_LIQUID, (BlockQueue.IAfterPlaceBlock)null);
                  }
               }
            }
         }
      }

      if (this.blockTrackerComponent.getQueue().getQueueSize() > 0) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.DESERT_GIRASOLE_1.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      }

   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 1, false, false));
         this.blockTrackerComponent.tickBlockQueue(entity.m_9236_());
      }
   }

   private void endChargingEvent(LivingEntity entity, IAbility ability) {
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.DESERT_GIRASOLE_2.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      this.cooldownComponent.getBonusManager().removeBonus(SunaHelper.DESERT_COOLDOWN_BONUS);
      if (SunaHelper.isFruitBoosted(entity)) {
         this.cooldownComponent.getBonusManager().addBonus(SunaHelper.DESERT_COOLDOWN_BONUS, "Desert Cooldown Bonus", BonusOperation.MUL, 0.8F);
      }

      this.cooldownComponent.startCooldown(entity, 600.0F);
   }
}
