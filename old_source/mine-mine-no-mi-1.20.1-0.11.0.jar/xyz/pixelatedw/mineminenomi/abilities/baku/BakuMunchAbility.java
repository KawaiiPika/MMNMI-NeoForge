package xyz.pixelatedw.mineminenomi.abilities.baku;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.particles.effects.BreakingBlocksParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class BakuMunchAbility extends Ability {
   private static final float COOLDOWN = 60.0F;
   public static final RegistryObject<AbilityCore<BakuMunchAbility>> INSTANCE = ModRegistry.registerAbility("baku_munch", "Baku Munch", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user to eat a big chunk of blocks in front of him, obtaining all of them as blocks in their inventory", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BakuMunchAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(60.0F)).build("mineminenomi");
   });
   private BreakingBlocksParticleEffect.Details details = new BreakingBlocksParticleEffect.Details(100);

   public BakuMunchAbility(AbilityCore<BakuMunchAbility> core) {
      super(core);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      BlockHitResult mop = WyHelper.rayTraceBlocks(entity, (double)16.0F);
      BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();
      if (Mth.m_14116_((float)entity.m_20275_(mop.m_82450_().f_82479_, mop.m_82450_().f_82480_, mop.m_82450_().f_82481_)) < 5.0F) {
         List<BlockPos> positions = new ArrayList();

         for(int x = -2; x < 2; ++x) {
            for(int y = 0; y < 3; ++y) {
               for(int z = -2; z < 2; ++z) {
                  int posX = (int)mop.m_82450_().f_82479_ + x;
                  int posY = (int)mop.m_82450_().f_82480_ - y;
                  int posZ = (int)mop.m_82450_().f_82481_ + z;
                  mutpos.m_122178_(posX, posY, posZ);
                  BlockState tempBlock = entity.m_9236_().m_8055_(mutpos);
                  if (!tempBlock.m_204336_(ModTags.Blocks.KAIROSEKI) && NuWorld.setBlockState((Level)entity.m_9236_(), mutpos, Blocks.f_50016_.m_49966_(), 2, DefaultProtectionRules.CORE_FOLIAGE_ORE)) {
                     if (entity instanceof Player) {
                        Player player = (Player)entity;
                        player.m_150109_().m_36054_(new ItemStack(tempBlock.m_60734_()));
                     }

                     positions.add(mutpos.m_7949_());
                  }
               }
            }
         }

         if (positions.size() > 0) {
            this.details.setPositions(positions);
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.BREAKING_BLOCKS.get(), entity, (double)0.0F, (double)0.0F, (double)0.0F, this.details);
         }
      }

      this.cooldownComponent.startCooldown(entity, 60.0F);
   }
}
