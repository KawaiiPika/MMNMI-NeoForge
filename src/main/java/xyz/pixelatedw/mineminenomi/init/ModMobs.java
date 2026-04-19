package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import xyz.pixelatedw.mineminenomi.entities.mobs.GruntEntity;

import java.util.function.Supplier;

public class ModMobs {

    public static final Supplier<EntityType<GruntEntity>> MARINE_GRUNT = ModRegistry.ENTITY_TYPES.register("marine_grunt",
            () -> EntityType.Builder.of(GruntEntity::createMarineGrunt, MobCategory.MONSTER)
                    .sized(0.6F, 1.8F)
                    .build("marine_grunt"));

    public static final Supplier<EntityType<GruntEntity>> PIRATE_GRUNT = ModRegistry.ENTITY_TYPES.register("pirate_grunt",
            () -> EntityType.Builder.of(GruntEntity::createPirateGrunt, MobCategory.MONSTER)
                    .sized(0.6F, 1.8F)
                    .build("pirate_grunt"));

    public static void init() {}

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(MARINE_GRUNT.get(), GruntEntity.createAttributes().build());
        event.put(PIRATE_GRUNT.get(), GruntEntity.createAttributes().build());
    }
}
