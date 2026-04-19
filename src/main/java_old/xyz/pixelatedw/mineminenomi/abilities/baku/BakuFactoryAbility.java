package xyz.pixelatedw.mineminenomi.abilities.baku;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BakuFactoryAbility extends Ability {
   public static final RegistryObject<AbilityCore<BakuFactoryAbility>> INSTANCE = ModRegistry.registerAbility("baku_factory", "Baku Factory", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user to craft items and blocks without the need for a Crafting Table.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BakuFactoryAbility::new)).addDescriptionLine(desc).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(100, this::startContinuityEvent).addTickEvent(100, this::tickContinuityEvent);
   private BlockState previousBlock;

   public BakuFactoryAbility(AbilityCore<BakuFactoryAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity instanceof Player player) {
         BlockPos pos = player.m_20183_();
         this.previousBlock = player.m_9236_().m_8055_(pos);
         player.m_9236_().m_46597_(pos, Blocks.f_50091_.m_49966_());
         player.m_5893_(player.m_9236_().m_8055_(pos).m_60750_(player.m_9236_(), pos));
         player.m_9236_().m_46597_(pos, this.previousBlock);
      }

   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      Level level = entity.m_9236_();
      if (level != null) {
         if (!level.f_46443_ && entity instanceof Player) {
            Player player = (Player)entity;
            AbstractContainerMenu container = player.f_36096_;
            if (!(container instanceof CraftingMenu)) {
               this.continuousComponent.stopContinuity(entity);
            }
         }

      }
   }
}
