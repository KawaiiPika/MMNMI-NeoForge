package xyz.pixelatedw.mineminenomi.abilities.bari;

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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class BarrierAbility extends WallAbility {
   private static final int HOLD_TIME = 600;
   private static final int MIN_COOLDOWN = 100;
   private static final int MAX_COOLDOWN = 400;
   public static final RegistryObject<AbilityCore<BarrierAbility>> INSTANCE = ModRegistry.registerAbility("barrier", "Barrier", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user creates an impenetrable wall in front of themselves, which shields them from attacks.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BarrierAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F, 400.0F), ContinuousComponent.getTooltip(600.0F)).build("mineminenomi");
   });
   private static final BlockProtectionRule GRIEF_RULE;

   public BarrierAbility(AbilityCore<BarrierAbility> core) {
      super(core);
      this.continuousComponent.addEndEvent(this::endContinuityEvent);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      float cooldown = 100.0F + this.continuousComponent.getContinueTime() / 2.0F;
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   public int getHoldTime() {
      return 600;
   }

   public int getThickness() {
      return 1;
   }

   public int getHeight() {
      return 6;
   }

   public int getLength() {
      return 3;
   }

   public BlockState getWallBlock() {
      return ((Block)ModBlocks.BARRIER.get()).m_49966_();
   }

   public BlockProtectionRule getGriefingRule() {
      return GRIEF_RULE;
   }

   public boolean stopAfterUse() {
      return false;
   }

   static {
      GRIEF_RULE = (new BlockProtectionRule.Builder(new BlockProtectionRule[]{DefaultProtectionRules.AIR})).addApprovedTags(ModTags.Blocks.BLOCK_PROT_FOLIAGE).setBypassGriefRule().build();
   }
}
