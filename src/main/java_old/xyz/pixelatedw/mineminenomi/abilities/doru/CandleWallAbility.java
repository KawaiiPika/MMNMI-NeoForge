package xyz.pixelatedw.mineminenomi.abilities.doru;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.WallAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class CandleWallAbility extends WallAbility {
   private static final float COOLDOWN = 120.0F;
   public static final RegistryObject<AbilityCore<CandleWallAbility>> INSTANCE = ModRegistry.registerAbility("candle_wall", "Candle Wall", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates a wax wall in front of the user", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, CandleWallAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(120.0F)).build("mineminenomi");
   });
   private int thiccness = 1;

   public CandleWallAbility(AbilityCore<CandleWallAbility> core) {
      super(core);
      this.addUseEvent(100, this::onUseEvent);
      this.continuousComponent.addEndEvent(100, this::endContinuityEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      boolean isChampionActive = (Boolean)AbilityCapability.get(entity).map((props) -> (CandleChampionAbility)props.getEquippedAbility((AbilityCore)CandleChampionAbility.INSTANCE.get())).map((abl) -> abl.isContinuous()).orElse(false);
      if (isChampionActive) {
         this.thiccness = 2;
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 120.0F);
   }

   public int getThickness() {
      return this.thiccness;
   }

   public int getHeight() {
      return 4;
   }

   public int getLength() {
      return 3;
   }

   public BlockState getWallBlock() {
      return ((Block)ModBlocks.WAX.get()).m_49966_();
   }

   public BlockProtectionRule getGriefingRule() {
      return DefaultProtectionRules.AIR_FOLIAGE;
   }

   public boolean stopAfterUse() {
      return true;
   }
}
