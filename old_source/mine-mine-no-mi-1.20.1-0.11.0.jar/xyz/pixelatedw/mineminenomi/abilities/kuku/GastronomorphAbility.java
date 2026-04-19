package xyz.pixelatedw.mineminenomi.abilities.kuku;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BlockTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockQueue;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.api.util.HashBlockPos;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GastronomorphAbility extends Ability {
   private static final int CHARGE_TIME = 60;
   private static final int COOLDOWN = 300;
   private static final int RANGE_Y = 3;
   private static final int RANGE_XZ = 128;
   public static final RegistryObject<AbilityCore<GastronomorphAbility>> INSTANCE = ModRegistry.registerAbility("gastronomorph", "Gastronomorph", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Turns the surroundings into cake sponge blocks.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GastronomorphAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F), ChargeComponent.getTooltip(60.0F), RangeComponent.getTooltip(128.0F, RangeComponent.RangeType.LINE)).build("mineminenomi");
   });
   public static final BlockProtectionRule GRIEF_RULE;
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::startChargeEvent).addTickEvent(this::duringChargeEvent).addEndEvent(this::endChargeEvent);
   private final BlockTrackerComponent blockTrackerComponent = new BlockTrackerComponent(this);

   public GastronomorphAbility(AbilityCore<GastronomorphAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.blockTrackerComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 60.0F);
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.blockTrackerComponent.initQueue(entity.m_9236_(), 50);
         double maxDistance = (double)384.0F;
         BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

         for(double y = (double)-3.0F; y < (double)3.0F; ++y) {
            for(double x = (double)-128.0F; x < (double)128.0F; ++x) {
               for(double z = (double)-128.0F; z < (double)128.0F; ++z) {
                  double distance = x * x + z * z + y * y;
                  if (!(distance >= (double)384.0F) && !(this.random.nextDouble() < distance / (double)384.0F)) {
                     double posX = entity.m_20185_() + x;
                     double posY = entity.m_20186_() + y;
                     double posZ = entity.m_20189_() + z;
                     mutpos.m_122169_(posX, posY, posZ);
                     if (entity.m_9236_().m_8055_(mutpos.m_7494_()).m_60795_()) {
                        HashBlockPos pos = new HashBlockPos(mutpos.m_7949_(), (int)(x * x + y * y + z * z));
                        this.blockTrackerComponent.addBlockToQueue(pos, ((Block)ModBlocks.SPONGE_CAKE.get()).m_49966_(), 3, DefaultProtectionRules.CORE_FOLIAGE_ORE_LIQUID, (BlockQueue.IAfterPlaceBlock)null);
                     }
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
      this.cooldownComponent.startCooldown(entity, 300.0F);
   }

   static {
      GRIEF_RULE = (new BlockProtectionRule.Builder(new BlockProtectionRule[]{DefaultProtectionRules.CORE_FOLIAGE})).build();
   }
}
