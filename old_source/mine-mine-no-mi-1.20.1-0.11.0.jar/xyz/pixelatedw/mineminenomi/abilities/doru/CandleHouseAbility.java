package xyz.pixelatedw.mineminenomi.abilities.doru;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class CandleHouseAbility extends Ability {
   private static final float COOLDOWN = 1000.0F;
   public static final RegistryObject<AbilityCore<CandleHouseAbility>> INSTANCE = ModRegistry.registerAbility("candle_house", "Candle House", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates a big house-like structure made of wax, to protect the user", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, CandleHouseAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(1000.0F)).build("mineminenomi");
   });
   private static final BlockProtectionRule GRIEF_RULE;

   public CandleHouseAbility(AbilityCore<CandleHouseAbility> core) {
      super(core);
      this.addUseEvent(100, this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility abiltiy) {
      Level level = entity.m_9236_();
      if (level != null && !level.f_46443_) {
         int thiccness = 1;
         boolean isChampionActive = (Boolean)AbilityCapability.get(entity).map((props) -> (CandleChampionAbility)props.getEquippedAbility((AbilityCore)CandleChampionAbility.INSTANCE.get())).map((abl) -> abl.isContinuous()).orElse(false);
         if (isChampionActive) {
            thiccness = 3;
         }

         BlockState defaultWaxState = ((Block)ModBlocks.WAX.get()).m_49966_();

         for(int y = -3; y <= 3; ++y) {
            for(int x = 0; x < thiccness; ++x) {
               for(int z = -5; z < 5; ++z) {
                  NuWorld.setBlockState((Entity)entity, entity.m_20183_().m_7918_(6 - x, y, -z), defaultWaxState, 3, GRIEF_RULE);
               }
            }

            for(int x = 0; x < thiccness; ++x) {
               for(int z = -5; z < 5; ++z) {
                  NuWorld.setBlockState((Entity)entity, entity.m_20183_().m_7918_(-5 - x, y, -z), defaultWaxState, 3, GRIEF_RULE);
               }
            }

            for(int x = -5; x < 5; ++x) {
               for(int z = 0; z < thiccness; ++z) {
                  NuWorld.setBlockState((Entity)entity, entity.m_20183_().m_7918_(-x, y, 6 - z), defaultWaxState, 3, GRIEF_RULE);
               }
            }

            for(int x = -5; x < 5; ++x) {
               for(int z = 0; z < thiccness; ++z) {
                  NuWorld.setBlockState((Entity)entity, entity.m_20183_().m_7918_(-x, y, -5 - z), defaultWaxState, 3, GRIEF_RULE);
               }
            }
         }

         for(int x = -5; x < 5; ++x) {
            for(int z = -5; z < 5; ++z) {
               NuWorld.setBlockState((Entity)entity, entity.m_20183_().m_7918_(-x, 4, -z), defaultWaxState, 3, GRIEF_RULE);
            }
         }

         this.cooldownComponent.startCooldown(entity, 1000.0F);
      }
   }

   static {
      GRIEF_RULE = DefaultProtectionRules.AIR_FOLIAGE;
   }
}
