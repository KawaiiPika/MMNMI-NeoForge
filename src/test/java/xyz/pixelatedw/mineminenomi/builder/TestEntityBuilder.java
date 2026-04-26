package xyz.pixelatedw.mineminenomi.builder;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.GameType;

import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModDataAttachments;

import java.util.HashMap;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestEntityBuilder {

    private final Player player;
    private final PlayerStats stats;

    private TestEntityBuilder() {
        this.player = mock(Player.class);
        this.stats = new PlayerStats();
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

    public Player build() {
        when(player.getData(ModDataAttachments.PLAYER_STATS)).thenReturn(stats);
        when(player.level()).thenReturn(mock(net.minecraft.world.level.Level.class));
        return player;
    }


    public Player create(GameTestHelper helper, GameType gameType) {
        Player testPlayer = helper.makeMockPlayer(gameType);
        testPlayer.setData(ModDataAttachments.PLAYER_STATS, stats);
        return testPlayer;
    }

    public PlayerStats getStats() {
        return stats;
    }
}
