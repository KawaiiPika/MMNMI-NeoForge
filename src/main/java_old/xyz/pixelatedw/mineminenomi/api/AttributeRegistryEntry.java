package xyz.pixelatedw.mineminenomi.api;

import net.minecraft.world.entity.ai.attributes.Attribute;

@FunctionalInterface
public interface AttributeRegistryEntry {
   Attribute get(String var1);
}
