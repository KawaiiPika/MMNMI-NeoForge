package xyz.pixelatedw.mineminenomi.abilities.pero;

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
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class CandyWallAbility extends WallAbility {
   private static final int COOLDOWN = 100;
   public static final RegistryObject<AbilityCore<CandyWallAbility>> INSTANCE = ModRegistry.registerAbility("candy_wall", "Candy Wall", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates a wall made out of candy in front of the user.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, CandyWallAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F)).build("mineminenomi");
   });

   public CandyWallAbility(AbilityCore<CandyWallAbility> core) {
      super(core);
      this.continuousComponent.addEndEvent(100, this::endContinuityEvent);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 100.0F);
   }

   public int getThickness() {
      return 1;
   }

   public int getHeight() {
      return 4;
   }

   public int getLength() {
      return 3;
   }

   public BlockState getWallBlock() {
      return ((Block)ModBlocks.CANDY.get()).m_49966_();
   }

   public BlockProtectionRule getGriefingRule() {
      return DefaultProtectionRules.AIR;
   }

   public boolean stopAfterUse() {
      return true;
   }
}
