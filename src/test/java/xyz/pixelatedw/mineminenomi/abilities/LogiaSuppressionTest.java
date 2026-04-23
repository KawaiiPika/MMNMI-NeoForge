package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.pixelatedw.mineminenomi.AbstractMinecraftTest;
import xyz.pixelatedw.mineminenomi.builder.TestEntityBuilder;
import xyz.pixelatedw.mineminenomi.events.CommonEvents;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.resources.ResourceLocation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LogiaSuppressionTest extends AbstractMinecraftTest {

    private Player player;
    private DamageSource fireSource;

    @BeforeEach
    public void init() {
        player = TestEntityBuilder.instance().build();
        fireSource = mock(DamageSource.class);
        when(fireSource.is(net.minecraft.tags.DamageTypeTags.IS_FIRE)).thenReturn(true);
        when(fireSource.getEntity()).thenReturn(mock(net.minecraft.world.entity.LivingEntity.class));

        // Ensure registry is populated in the test environment
        if (xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY == null) {
            xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY = new net.minecraft.core.MappedRegistry<>(xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY_KEY, com.mojang.serialization.Lifecycle.stable());
        }

        if (xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY.get(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mera_logia")) == null) {
            net.minecraft.core.Registry.register(xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY,
                ResourceLocation.fromNamespaceAndPath("mineminenomi", "mera_logia"),
                new xyz.pixelatedw.mineminenomi.abilities.mera.MeraLogiaAbility());
        }
    }

    @Test
    public void testLogiaInvulnerabilityWithoutKairoseki() {
        // Player has Mera Mera no Mi
        xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(player);
        stats.setDevilFruit(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mera_mera_no_mi"));
        stats.grantAbility(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mera_logia"));

        EntityInvulnerabilityCheckEvent event = new EntityInvulnerabilityCheckEvent(player, fireSource, false);
        CommonEvents.onEntityInvulnerabilityCheck(event);

        assertTrue(event.isInvulnerable(), "Player should be immune to fire due to Mera Logia.");
    }

    @Test
    public void testLogiaInvulnerabilityWithKairoseki() {
        // Player has Mera Mera no Mi
        xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(player);
        stats.setDevilFruit(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mera_mera_no_mi"));
        stats.grantAbility(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mera_logia"));

        // Add Kairoseki
        when(player.hasEffect(ModEffects.HANDCUFFED_KAIROSEKI)).thenReturn(true);

        EntityInvulnerabilityCheckEvent event = new EntityInvulnerabilityCheckEvent(player, fireSource, false);
        CommonEvents.onEntityInvulnerabilityCheck(event);

        assertFalse(event.isInvulnerable(), "Player should NOT be immune to fire due to Kairoseki suppression.");
    }
}
