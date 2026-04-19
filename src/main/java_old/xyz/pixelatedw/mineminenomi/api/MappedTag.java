package xyz.pixelatedw.mineminenomi.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.registries.IForgeRegistry;

public class MappedTag<K> extends SimpleJsonResourceReloadListener {
   private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
   private HashMap<K, Float> values = new HashMap();
   private final ResourceLocation id;
   private final IForgeRegistry<K> registry;

   public MappedTag(ResourceLocation id, String directory, IForgeRegistry<K> registry) {
      super(GSON, directory);
      this.id = id;
      this.registry = registry;
   }

   protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager manager, ProfilerFiller filter) {
      JsonObject obj = ((JsonElement)map.get(this.id)).getAsJsonObject();

      for(Map.Entry<String, JsonElement> objEntry : obj.entrySet()) {
         ResourceLocation resourceId = ResourceLocation.parse((String)objEntry.getKey());
         K key = (K)this.registry.getValue(resourceId);
         float value = ((JsonElement)objEntry.getValue()).getAsFloat();
         this.values.put(key, value);
      }

   }

   public float getValue(K type) {
      return (Float)this.values.getOrDefault(type, 0.0F);
   }
}
