package xyz.pixelatedw.mineminenomi.handlers.world;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;

public class LootTablesHandler {
   public static void appendVanillaLootTables(ResourceLocation id, LootTable table) {
      ResourceLocation modKey = ResourceLocation.fromNamespaceAndPath("mineminenomi", "vanilla/" + id.m_135815_());
      LootPoolSingletonContainer.Builder<?> entry = LootTableReference.m_79776_(modKey);
      table.addPool(LootPool.m_79043_().m_79076_(entry).m_79082_());
   }
}
