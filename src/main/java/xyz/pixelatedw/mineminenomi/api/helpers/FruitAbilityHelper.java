package xyz.pixelatedw.mineminenomi.api.helpers;

import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FruitAbilityHelper {
    
    private static final Map<ResourceLocation, List<ResourceLocation>> FRUIT_ABILITIES = new HashMap<>();

    static {
        // Gomu
        register(ResourceLocation.fromNamespaceAndPath("mineminenomi", "gomu_gomu_no_mi"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "gomu_gomu_no_pistol"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "gomu_gomu_no_gatling"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "gomu_gomu_no_bazooka"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "gomu_gomu_no_rocket")
        );
        // Ope
        register(ResourceLocation.fromNamespaceAndPath("mineminenomi", "ope_ope_no_mi"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "room"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "counter_shock"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "shambles"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "mes")
        );
        // Logias
        register(ResourceLocation.fromNamespaceAndPath("mineminenomi", "hie_hie_no_mi"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "hie_logia"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "ice_age"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "ice_saber"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "ice_block_partisan")
        );
        register(ResourceLocation.fromNamespaceAndPath("mineminenomi", "goro_goro_no_mi"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "goro_logia"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "el_thor"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "sango")
        );
        register(ResourceLocation.fromNamespaceAndPath("mineminenomi", "yami_yami_no_mi"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "black_hole"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "kurouzu")
        );
        register(ResourceLocation.fromNamespaceAndPath("mineminenomi", "moku_moku_no_mi"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "moku_logia"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "white_blow"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "white_out")
        );
        register(ResourceLocation.fromNamespaceAndPath("mineminenomi", "suna_suna_no_mi"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "suna_logia"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "desert_spada"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "sables")
        );
        register(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mera_mera_no_mi"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "mera_logia"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "higan"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "hiken"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "dai_enka_entei")
        );
        register(ResourceLocation.fromNamespaceAndPath("mineminenomi", "magu_magu_no_mi"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "magu_logia"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "dai_funka"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "magma_coating"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "lava_flow"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "meigo"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "ryusei_kazan")
        );
        register(ResourceLocation.fromNamespaceAndPath("mineminenomi", "pika_pika_no_mi"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "pika_logia"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "yasakani_no_magatama"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "amaterasu")
        );
        register(ResourceLocation.fromNamespaceAndPath("mineminenomi", "yuki_yuki_no_mi"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "yuki_logia")
        );
        register(ResourceLocation.fromNamespaceAndPath("mineminenomi", "gasu_gasu_no_mi"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "gasu_logia")
        );
        register(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zou_zou_no_mi"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "zou_guard_point"),
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "zou_heavy_point")
        );
    }

    private static void register(ResourceLocation fruit, ResourceLocation... abilities) {
        FRUIT_ABILITIES.put(fruit, List.of(abilities));
    }

    public static void giveFruitAbilities(LivingEntity entity, ResourceLocation fruitId) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats == null) return;

        List<ResourceLocation> abilities = FRUIT_ABILITIES.get(fruitId);
        if (abilities != null) {
            for (int i = 0; i < abilities.size() && i < 24; i++) {
                ResourceLocation abilityLoc = abilities.get(i);
                xyz.pixelatedw.mineminenomi.api.abilities.Ability ability = ModAbilities.REGISTRY.get(abilityLoc);
                if (ability != null && ability.isPassive()) {
                    stats.grantAbility(abilityLoc);
                } else {
                    stats.setEquippedAbility(i, abilityLoc);
                }
            }
            stats.sync(entity);
        }
    }
}
