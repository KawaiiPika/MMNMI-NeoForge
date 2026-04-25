package xyz.pixelatedw.mineminenomi.builder;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.data.entity.AnimationStateData;
import xyz.pixelatedw.mineminenomi.data.entity.MorphData;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModDataAttachments;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.lenient;

public class TestEntityBuilder {

    private final Player player;
    private final PlayerStats stats;
    private MorphData morphData;
    private AnimationStateData animationData;

    private TestEntityBuilder() {
        this.player = mock(Player.class);
        this.stats = new PlayerStats();
        this.morphData = new MorphData();
        this.animationData = new AnimationStateData();
    }

    public static TestEntityBuilder instance() {
        return new TestEntityBuilder();
    }

    public TestEntityBuilder withDoriki(double doriki) {
        PlayerStats.BasicStats current = stats.getBasic();
        stats.setBasic(new PlayerStats.BasicStats(
                doriki, current.cola(), current.ultraCola(), current.loyalty(),
                current.bounty(), current.belly(), current.extol(),
                current.identity(), current.hasShadow(), current.hasHeart(),
                current.hasStrawDoll(), current.isRogue(), current.stamina(),
                current.maxStamina(), current.trainingPoints()
        ));
        return this;
    }

    public TestEntityBuilder withBusoshokuHakiExp(float exp) {
        stats.setBusoshokuHakiExp(exp);
        return this;
    }

    public TestEntityBuilder withKenbunshokuHakiExp(float exp) {
        stats.setKenbunshokuHakiExp(exp);
        return this;
    }

    public TestEntityBuilder withBusoshokuActive(boolean active) {
        stats.setBusoshokuActive(active);
        return this;
    }

    public TestEntityBuilder withKenbunshokuActive(boolean active) {
        stats.setKenbunshokuActive(active);
        return this;
    }

    public TestEntityBuilder withDevilFruit(ResourceLocation fruit) {
        stats.setDevilFruit(fruit);
        return this;
    }

    public TestEntityBuilder withFaction(ResourceLocation faction) {
        PlayerStats.BasicStats current = stats.getBasic();
        PlayerStats.Identity currentIdentity = current.identity();
        PlayerStats.Identity newIdentity = new PlayerStats.Identity(
                Optional.ofNullable(faction), currentIdentity.race(),
                currentIdentity.subRace(), currentIdentity.fightingStyle(),
                currentIdentity.devilFruit()
        );
        stats.setBasic(new PlayerStats.BasicStats(
                current.doriki(), current.cola(), current.ultraCola(), current.loyalty(),
                current.bounty(), current.belly(), current.extol(),
                newIdentity, current.hasShadow(), current.hasHeart(),
                current.hasStrawDoll(), current.isRogue(), current.stamina(),
                current.maxStamina(), current.trainingPoints()
        ));
        return this;
    }

    public TestEntityBuilder withRace(ResourceLocation race) {
        PlayerStats.BasicStats current = stats.getBasic();
        PlayerStats.Identity currentIdentity = current.identity();
        PlayerStats.Identity newIdentity = new PlayerStats.Identity(
                currentIdentity.faction(), Optional.ofNullable(race),
                currentIdentity.subRace(), currentIdentity.fightingStyle(),
                currentIdentity.devilFruit()
        );
        stats.setBasic(new PlayerStats.BasicStats(
                current.doriki(), current.cola(), current.ultraCola(), current.loyalty(),
                current.bounty(), current.belly(), current.extol(),
                newIdentity, current.hasShadow(), current.hasHeart(),
                current.hasStrawDoll(), current.isRogue(), current.stamina(),
                current.maxStamina(), current.trainingPoints()
        ));
        return this;
    }

    public TestEntityBuilder withFightingStyle(ResourceLocation style) {
        PlayerStats.BasicStats current = stats.getBasic();
        PlayerStats.Identity currentIdentity = current.identity();
        PlayerStats.Identity newIdentity = new PlayerStats.Identity(
                currentIdentity.faction(), currentIdentity.race(),
                currentIdentity.subRace(), Optional.ofNullable(style),
                currentIdentity.devilFruit()
        );
        stats.setBasic(new PlayerStats.BasicStats(
                current.doriki(), current.cola(), current.ultraCola(), current.loyalty(),
                current.bounty(), current.belly(), current.extol(),
                newIdentity, current.hasShadow(), current.hasHeart(),
                current.hasStrawDoll(), current.isRogue(), current.stamina(),
                current.maxStamina(), current.trainingPoints()
        ));
        return this;
    }

    public TestEntityBuilder withEquippedAbility(int slot, ResourceLocation ability) {
        stats.setEquippedAbility(slot, ability);
        return this;
    }

    public TestEntityBuilder withActiveAbility(String abilityId) {
        stats.setAbilityActive(abilityId, true);
        return this;
    }

    public TestEntityBuilder withMorph(ResourceLocation morph) {
        List<ResourceLocation> activeMorphs = new ArrayList<>(morphData.activeMorphs());
        if (!activeMorphs.contains(morph)) {
            activeMorphs.add(morph);
        }
        this.morphData = new MorphData(Optional.of(morph), activeMorphs);
        return this;
    }

    public Player build() {
        lenient().when(player.hasData(ModDataAttachments.PLAYER_STATS)).thenReturn(true);
        lenient().when(player.getData(ModDataAttachments.PLAYER_STATS)).thenReturn(stats);

        lenient().when(player.hasData(ModDataAttachments.MORPH_DATA)).thenReturn(true);
        lenient().when(player.getData(ModDataAttachments.MORPH_DATA)).thenReturn(morphData);

        lenient().when(player.hasData(ModDataAttachments.ANIMATION_STATE)).thenReturn(true);
        lenient().when(player.getData(ModDataAttachments.ANIMATION_STATE)).thenReturn(animationData);

        lenient().when(player.level()).thenReturn(null);
        return player;
    }

    public PlayerStats getStats() {
        return stats;
    }
}
