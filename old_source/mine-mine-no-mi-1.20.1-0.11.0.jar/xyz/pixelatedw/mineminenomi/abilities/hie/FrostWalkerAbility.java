package xyz.pixelatedw.mineminenomi.abilities.hie;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class FrostWalkerAbility extends PassiveAbility {
   public static final RegistryObject<AbilityCore<FrostWalkerAbility>> INSTANCE = ModRegistry.registerAbility("frost_walker", "Frost Walker", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Turns all water the user walks on into ice", (Object)null), ImmutablePair.of("§aCrouching§r temporarily disables the ability", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, FrostWalkerAbility::new)).addDescriptionLine(desc).build("mineminenomi");
   });
   private static final BlockProtectionRule GRIEF_RULE;

   public FrostWalkerAbility(AbilityCore<FrostWalkerAbility> ability) {
      super(ability);
      this.addDuringPassiveEvent(this::duringPassiveEvent);
   }

   private void duringPassiveEvent(LivingEntity entity) {
      if (!entity.m_9236_().f_46443_) {
         if (!entity.m_6047_() && !entity.m_20069_() && entity.m_20202_() == null && !(entity.m_21223_() < entity.m_21233_() / 5.0F)) {
            if (entity instanceof Player) {
               Player player = (Player)entity;
               if (player.m_150110_().f_35935_) {
                  return;
               }
            }

            BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

            for(int x = -1; x <= 1; ++x) {
               for(int y = -2; y <= 2; ++y) {
                  for(int z = -1; z <= 1; ++z) {
                     mutpos.m_122169_(entity.m_20185_() + (double)x, entity.m_20186_() + (double)y, entity.m_20189_() + (double)z);
                     if (NuWorld.setBlockState((Entity)entity, mutpos, Blocks.f_50449_.m_49966_(), 3, GRIEF_RULE)) {
                        entity.m_9236_().m_186460_(mutpos, Blocks.f_50449_, Mth.m_216271_(entity.m_217043_(), 20, 60));
                     }
                  }
               }
            }

         }
      }
   }

   static {
      GRIEF_RULE = (new BlockProtectionRule.Builder(new BlockProtectionRule[0])).addApprovedTags(ModTags.Blocks.BLOCK_PROT_OCEAN_PLANTS, ModTags.Blocks.BLOCK_PROT_WATER).build();
   }
}
