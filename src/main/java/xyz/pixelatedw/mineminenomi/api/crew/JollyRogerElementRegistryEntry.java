package xyz.pixelatedw.mineminenomi.api.crew;

@FunctionalInterface
public interface JollyRogerElementRegistryEntry {
   JollyRogerElement get(JollyRogerElement.LayerType layerType);
}
