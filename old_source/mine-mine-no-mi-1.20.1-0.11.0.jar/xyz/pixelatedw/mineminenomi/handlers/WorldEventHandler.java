package xyz.pixelatedw.mineminenomi.handlers;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.crafting.SmithingDialRecipe;
import xyz.pixelatedw.mineminenomi.api.crafting.SmithingEnchantmentRecipe;
import xyz.pixelatedw.mineminenomi.api.events.SmithingTableEvent;
import xyz.pixelatedw.mineminenomi.api.util.TPSDelta;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;
import xyz.pixelatedw.mineminenomi.data.world.EventsWorldData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.data.world.NPCWorldData;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.handlers.entity.BountyHandler;
import xyz.pixelatedw.mineminenomi.handlers.protection.AbilityProtectionHandler;
import xyz.pixelatedw.mineminenomi.handlers.world.LootTablesHandler;
import xyz.pixelatedw.mineminenomi.handlers.world.OneFruitPerWorldHandler;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.world.spawners.AmbushSpawner;
import xyz.pixelatedw.mineminenomi.world.spawners.TraderSpawner;
import xyz.pixelatedw.mineminenomi.world.spawners.TrainerSpawner;

@EventBusSubscriber(
   modid = "mineminenomi"
)
public class WorldEventHandler {
   private static final TraderSpawner TRADER_SPAWNER = new TraderSpawner();
   private static final TrainerSpawner TRAINER_SPAWNER = new TrainerSpawner();
   private static final AmbushSpawner AMBUSH_SPAWNER = new AmbushSpawner();
   private static final Set<ResourceLocation> APPENDED_VANILLA_LOOT = Sets.newHashSet(new ResourceLocation[]{ResourceLocation.parse("chests/woodland_mansion"), ResourceLocation.parse("chests/underwater_ruin_small"), ResourceLocation.parse("chests/underwater_ruin_big"), ResourceLocation.parse("chests/stronghold_corridor"), ResourceLocation.parse("chests/spawn_bonus_chest"), ResourceLocation.parse("chests/shipwreck_treasure"), ResourceLocation.parse("chests/shipwreck_supply"), ResourceLocation.parse("chests/pillager_outpost"), ResourceLocation.parse("chests/jungle_temple"), ResourceLocation.parse("chests/desert_pyramid"), ResourceLocation.parse("chests/buried_treasure"), ResourceLocation.parse("chests/abandoned_mineshaft"), ResourceLocation.parse("chests/village/village_weaponsmith"), ResourceLocation.parse("chests/village/village_temple"), ResourceLocation.parse("chests/village/village_tannery"), ResourceLocation.parse("chests/village/village_shepherd"), ResourceLocation.parse("chests/village/village_fletcher"), ResourceLocation.parse("chests/village/village_fisher"), ResourceLocation.parse("chests/village/village_butcher"), ResourceLocation.parse("chests/village/village_armorer"), ResourceLocation.parse("blocks/brain_coral_block")});

   @SubscribeEvent
   public static void changeItemFuel(FurnaceFuelBurnTimeEvent event) {
      Item fuelItem = event.getItemStack().m_41720_();
      if (fuelItem.equals(((Block)ModBlocks.FLAME_DIAL.get()).m_5456_())) {
         event.setBurnTime(10000);
      } else if (fuelItem.equals(((Item)ModItems.WANTED_POSTER_PACKAGE.get()).m_5456_())) {
         event.setBurnTime(300);
      } else if (!fuelItem.equals(((Block)ModBlocks.WANTED_POSTER.get()).m_5456_()) && !fuelItem.equals(((Block)ModBlocks.FLAG.get()).m_5456_()) && !fuelItem.equals(ModItems.COLOR_PALETTE.get()) && !fuelItem.equals(ModItems.CHALLENGE_POSTER.get()) && !fuelItem.equals(ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get())) {
         if (fuelItem.equals(ModItems.VIVRE_CARD.get())) {
            event.setBurnTime(50);
         }
      } else {
         event.setBurnTime(200);
      }

   }

   @SubscribeEvent
   public static void onLevelLoad(LevelEvent.Load event) {
      ModDamageSources.init(event.getLevel());
   }

   @SubscribeEvent
   public static void onLevelTick(TickEvent.LevelTickEvent event) {
      if (event.level != null && event.phase != Phase.START && !event.level.f_46443_) {
         ServerLevel level = (ServerLevel)event.level;
         ProtectedAreasData.get(level).tick();
         TPSDelta.INSTANCE.tick();
         if (level.m_46472_() == Level.f_46428_) {
            if (level.m_46467_() % 20L == 0L) {
               event.level.m_46473_().m_6180_("faction");
               FactionsWorldData.get().tick(level);
               event.level.m_46473_().m_7238_();
               event.level.m_46473_().m_6180_("worldEvents");
               EventsWorldData.get().tick(level);
               event.level.m_46473_().m_7238_();
               event.level.m_46473_().m_6180_("trackedNPCs");
               NPCWorldData.get().tick(level);
               event.level.m_46473_().m_7238_();
            }

            if (ServerConfig.isWantedPosterPackagesEnabled()) {
               if (level.m_46467_() % (long)ServerConfig.getTimeBetweenPackages() == 0L) {
                  BountyHandler.updateEverybodysBounty(level);
                  BountyHandler.dropWantedPosters(level);
               }

               if (level.m_46467_() % 72000L == 0L) {
                  BountyHandler.clearDropCache();
               }
            }

            if (ServerConfig.hasChunkUnloadingRealisticLogic()) {
               OneFruitPerWorldHandler.tick();
            }

            if (event.level.m_46469_().m_46207_(GameRules.f_46134_)) {
               event.level.m_46473_().m_6180_("worldSpawners");
               if (ServerConfig.canSpawnTraders()) {
                  TRADER_SPAWNER.tick(level);
               }

               if (ServerConfig.canSpawnTrainers()) {
                  TRAINER_SPAWNER.tick(level);
               }

               if (ServerConfig.canSpawnAmbushes()) {
                  AMBUSH_SPAWNER.tick(level);
               }

               event.level.m_46473_().m_7238_();
            }
         } else if (NuWorld.isChallengeDimension(level)) {
            level.m_46473_().m_6180_("challengesManager");
            ChallengesWorldData.get().tick(level);
            level.m_46473_().m_7238_();
         }

      }
   }

   @SubscribeEvent
   public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
      Level level = event.getLevel();
      if (!level.f_46443_) {
         ServerLevel serverLevel = (ServerLevel)level;
         List<BlockPos> affectedBlocks = event.getAffectedBlocks();
         List<Entity> affectedEntities = event.getAffectedEntities();
         AbilityProtectionHandler.handleExplosions(serverLevel, event.getExplosion(), affectedBlocks, affectedEntities);
      }

   }

   @SubscribeEvent
   public static void onSmithingUpdate(SmithingTableEvent event) {
      SmithingRecipe var3 = event.getRecipe();
      if (var3 instanceof SmithingDialRecipe recipe) {
         event.getAdditionSlot().m_41774_(recipe.getAdditionalAmount() - 1);
      } else {
         var3 = event.getRecipe();
         if (var3 instanceof SmithingEnchantmentRecipe recipe) {
            event.getAdditionSlot().m_41774_(recipe.getAdditionalAmount(event.getContainer()) - 1);
         }
      }

   }

   @SubscribeEvent
   public static void onLootTableLoad(LootTableLoadEvent event) {
      if (APPENDED_VANILLA_LOOT.contains(event.getName())) {
         LootTablesHandler.appendVanillaLootTables(event.getName(), event.getTable());
      }

   }
}
