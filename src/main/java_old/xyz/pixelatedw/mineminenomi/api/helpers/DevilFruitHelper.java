package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.config.GeneralConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;
import xyz.pixelatedw.mineminenomi.data.world.OneFruitWorldData;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class DevilFruitHelper {
   private static final double REINCARNATION_RANGE = (double)30.0F;

   public static boolean hasDFLimitInInventory(Player player) {
      if (!ServerConfig.hasOneFruitPerWorldExtendedLogic()) {
         return false;
      } else {
         IDevilFruit devilFruitProps = (IDevilFruit)DevilFruitCapability.get(player).orElse((Object)null);
         int fruitsFound = ItemsHelper.getAllInventoryItems(player).stream().filter((itemStack) -> itemStack.m_41720_() instanceof AkumaNoMiItem).toList().size();
         if (devilFruitProps != null) {
            boolean canYamiPickupFruit = devilFruitProps.hasDevilFruit(ModFruits.YAMI_YAMI_NO_MI) && ServerConfig.isYamiPowerEnabled() && fruitsFound == 0;
            if (devilFruitProps.hasAnyDevilFruit() && ServerConfig.getUnableToPickDFAsUser() && !canYamiPickupFruit) {
               return true;
            }
         }

         return fruitsFound >= ServerConfig.getInventoryLimitForFruits();
      }
   }

   @Nullable
   public static AkumaNoMiItem findAvailableOneFruit(Level world, @Nullable Item devilFruitItem) {
      if (devilFruitItem instanceof AkumaNoMiItem fruit) {
         if (!ServerConfig.hasOneFruitPerWorldSimpleLogic()) {
            return fruit;
         } else {
            OneFruitWorldData worldProps = OneFruitWorldData.get();
            int tier = fruit.getTier();
            ResourceLocation key = fruit.getRegistryKey();
            boolean isAvailable = !worldProps.isFruitInWorld(key);
            if (isAvailable) {
               return fruit;
            } else {
               List<AkumaNoMiItem> fruitsLeft = WyHelper.shuffle(ModValues.DEVIL_FRUITS).stream().filter((f) -> f.getTier() == tier && !worldProps.isFruitInWorld(f.getRegistryKey())).toList();
               if (!fruitsLeft.isEmpty()) {
                  fruit = (AkumaNoMiItem)fruitsLeft.get(0);
                  return fruit;
               } else {
                  return null;
               }
            }
         }
      } else {
         return null;
      }
   }

   public static boolean canDevilFruitRespawn() {
      return !(Boolean)GeneralConfig.DEVIL_FRUIT_KEEP.get();
   }

   public static boolean respawnDevilFruit(LivingEntity entity, IDevilFruit props) {
      if (!canDevilFruitRespawn()) {
         return false;
      } else {
         ChallengesWorldData challengeWorldData = ChallengesWorldData.get();
         InProgressChallenge challenge = challengeWorldData.getInProgressChallengeFor(entity);
         if (challenge != null) {
            return false;
         } else if (!props.hasAnyDevilFruit()) {
            return false;
         } else {
            boolean isYomi = props.hasDevilFruit(ModFruits.YOMI_YOMI_NO_MI) && !((MorphInfo)ModMorphs.YOMI_SKELETON.get()).isActive(entity);
            if (isYomi) {
               return false;
            } else {
               double chance = WyHelper.randomWithRange(1, 100);
               OneFruitWorldData worldData = OneFruitWorldData.get();
               List<ItemEntity> droppedItems = TargetHelper.<ItemEntity>getEntitiesInArea(entity.m_9236_(), entity, (double)30.0F, (TargetPredicate)null, ItemEntity.class);
               droppedItems.removeIf((entry) -> !entry.m_32055_().m_204117_(ModTags.Items.FRUIT_REINCARNATION));
               if (!droppedItems.isEmpty() && chance <= ServerConfig.getChanceForDroppedAppleReincarnation()) {
                  AkumaNoMiItem fruit = (AkumaNoMiItem)props.getDevilFruitItem();
                  if (ServerConfig.getRandomizedFruits()) {
                     fruit = fruit.getReverseShiftedFruit();
                  }

                  ((ItemEntity)droppedItems.get(0)).m_32045_(new ItemStack(fruit));
                  BlockPos blockPos = new BlockPos(entity.m_146903_(), entity.m_146904_(), entity.m_146907_());
                  worldData.dropOneFruit(fruit, (LivingEntity)null, entity.m_9236_().m_46472_().m_135782_(), blockPos, "Reincarnated when " + entity.m_5446_().getString() + " died", true);
                  entity.m_9236_().m_5594_((Player)null, ((ItemEntity)droppedItems.get(0)).m_20183_(), (SoundEvent)ModSounds.FRUIT_REINCARNATION.get(), SoundSource.PLAYERS, 3.0F, 1.0F);
                  return true;
               } else {
                  List<Player> players = TargetHelper.<Player>getNearbyPlayers(entity.m_9236_(), entity.m_20182_(), (double)30.0F, (Predicate)null);
                  players.removeIf((entry) -> !hasFruitReincarnationInInventory(entry));
                  if (!players.isEmpty() && chance <= ServerConfig.getChanceForInventoryAppleReincarnation()) {
                     boolean flag = setFruitInInv(((Player)players.get(0)).m_150109_(), (LivingEntity)players.get(0), entity, entity.m_9236_(), (ResourceLocation)props.getDevilFruit().get());
                     if (flag) {
                        entity.m_9236_().m_5594_((Player)null, ((Player)players.get(0)).m_20183_(), (SoundEvent)ModSounds.FRUIT_REINCARNATION.get(), SoundSource.PLAYERS, 3.0F, 1.0F);
                        return true;
                     }
                  }

                  if (!ServerConfig.hasOneFruitPerWorldExtendedLogic()) {
                     List<Villager> villagers = TargetHelper.<Villager>getEntitiesInArea(entity.m_9236_(), entity, (double)30.0F, (TargetPredicate)null, Villager.class);
                     villagers.removeIf((entry) -> !hasFruitReincarnationInInventory(entry));
                     if (!villagers.isEmpty() && chance <= ServerConfig.getChanceForInventoryAppleReincarnation()) {
                        boolean flag = setFruitInInv(((Villager)villagers.get(0)).m_35311_(), (LivingEntity)villagers.get(0), entity, entity.m_9236_(), (ResourceLocation)props.getDevilFruit().get());
                        if (flag) {
                           entity.m_9236_().m_5594_((Player)null, ((Villager)villagers.get(0)).m_20183_(), (SoundEvent)ModSounds.FRUIT_REINCARNATION.get(), SoundSource.PLAYERS, 3.0F, 1.0F);
                           return true;
                        }
                     }

                     List<BlockPos> blockPosList = WyHelper.getNearbyBlocks(entity, 30);
                     if (!blockPosList.isEmpty() && chance <= ServerConfig.getChanceForChestAppleReincarnation()) {
                        for(BlockPos blockPos : blockPosList) {
                           BlockState state = entity.m_9236_().m_8055_(blockPos);
                           if (state.m_60734_() instanceof ChestBlock) {
                              Container inv = ChestBlock.m_51511_((ChestBlock)state.m_60734_(), state, entity.m_9236_(), blockPos, false);
                              boolean flag = setFruitInInv(inv, (LivingEntity)null, entity, entity.m_9236_(), (ResourceLocation)props.getDevilFruit().get());
                              if (flag) {
                                 entity.m_9236_().m_5594_((Player)null, blockPos, (SoundEvent)ModSounds.FRUIT_REINCARNATION.get(), SoundSource.PLAYERS, 3.0F, 1.0F);
                                 return true;
                              }
                           }
                        }
                     }
                  }

                  return false;
               }
            }
         }
      }
   }

   @Nullable
   public static Item getDevilFruitItem(ResourceLocation key) {
      return (Item)ForgeRegistries.ITEMS.getValue(key);
   }

   private static boolean setFruitInInv(@Nullable Container inv, @Nullable LivingEntity invOwner, LivingEntity entity, Level level, ResourceLocation df) {
      if (inv == null) {
         return false;
      } else {
         int stackIndex = -1;

         for(int i = 0; i < inv.m_6643_(); ++i) {
            if (inv.m_8020_(i).m_204117_(ModTags.Items.FRUIT_REINCARNATION)) {
               stackIndex = i;
               break;
            }
         }

         if (stackIndex != -1) {
            AkumaNoMiItem fruit = (AkumaNoMiItem)getDevilFruitItem(df);
            if (ServerConfig.getRandomizedFruits()) {
               fruit = fruit.getReverseShiftedFruit();
            }

            inv.m_6836_(stackIndex, new ItemStack(fruit));
            OneFruitWorldData.get().inventoryOneFruit(fruit, invOwner, "Reincarnated in " + entity.m_5446_().getString() + "'s inventory", true);
            return true;
         } else {
            return false;
         }
      }
   }

   private static boolean hasFruitReincarnationInInventory(LivingEntity entity) {
      for(ItemStack stack : ItemsHelper.getAllInventoryItems(entity)) {
         if (stack.m_204117_(ModTags.Items.FRUIT_REINCARNATION)) {
            return true;
         }
      }

      return false;
   }
}
