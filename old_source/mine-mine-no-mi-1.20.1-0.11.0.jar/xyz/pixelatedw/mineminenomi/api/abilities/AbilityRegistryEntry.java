package xyz.pixelatedw.mineminenomi.api.abilities;

@FunctionalInterface
public interface AbilityRegistryEntry<T> {
   T get(String var1, String var2);
}
