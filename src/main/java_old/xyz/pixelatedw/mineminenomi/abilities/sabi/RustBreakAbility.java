package xyz.pixelatedw.mineminenomi.abilities.sabi;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class RustBreakAbility extends Ability {
   private static final float COOLDOWN = 400.0F;
   public static final RegistryObject<AbilityCore<RustBreakAbility>> INSTANCE = ModRegistry.registerAbility("rust_break", "Rust Break", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Rusts iron blocks in front of the user", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, RustBreakAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE).build("mineminenomi");
   });

   public RustBreakAbility(AbilityCore<RustBreakAbility> core) {
      super(core);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      HitResult mop = WyHelper.rayTraceBlocksAndEntities(entity);
      if (!(entity.m_20238_(mop.m_82450_()) > (double)50.0F)) {
         BlockPos tracePos = BlockPos.m_274446_(mop.m_82450_());

         for(BlockPos pos : WyHelper.getNearbyBlocks(tracePos, entity.m_9236_(), 1, (p) -> entity.m_9236_().m_8055_(p).m_204336_(ModTags.Blocks.RUSTY), ImmutableList.of(Blocks.f_50016_))) {
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.RUST_BREAK.get(), entity, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
            boolean isIngot = this.random.nextBoolean();

            for(int i = 0; i < this.random.nextInt(3); ++i) {
               ItemStack stack = isIngot ? new ItemStack(Items.f_42416_) : new ItemStack(Items.f_42749_);
               ItemEntity item = new ItemEntity(entity.m_9236_(), (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), stack);
               entity.m_9236_().m_7967_(item);
            }

            entity.m_9236_().m_7471_(pos, false);
         }

         this.cooldownComponent.startCooldown(entity, 400.0F);
      }
   }
}
