package xyz.pixelatedw.mineminenomi.data.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;

public class PlayerStats {

    public record Identity(Optional<ResourceLocation> faction, Optional<ResourceLocation> race,
                           Optional<ResourceLocation> subRace, Optional<ResourceLocation> fightingStyle,
                           Optional<ResourceLocation> devilFruit) {
        public static final Codec<Identity> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceLocation.CODEC.optionalFieldOf("faction").forGetter(Identity::faction),
                ResourceLocation.CODEC.optionalFieldOf("race").forGetter(Identity::race),
                ResourceLocation.CODEC.optionalFieldOf("subRace").forGetter(Identity::subRace),
                ResourceLocation.CODEC.optionalFieldOf("fightingStyle").forGetter(Identity::fightingStyle),
                ResourceLocation.CODEC.optionalFieldOf("devilFruit").forGetter(Identity::devilFruit)
        ).apply(instance, Identity::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, Identity> STREAM_CODEC = new StreamCodec<RegistryFriendlyByteBuf, Identity>() {
            @Override
            public Identity decode(RegistryFriendlyByteBuf buffer) {
                return new Identity(
                        ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC).decode(buffer),
                        ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC).decode(buffer),
                        ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC).decode(buffer),
                        ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC).decode(buffer),
                        ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC).decode(buffer)
                );
            }

            @Override
            public void encode(RegistryFriendlyByteBuf buffer, Identity value) {
                ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC).encode(buffer, value.faction());
                ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC).encode(buffer, value.race());
                ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC).encode(buffer, value.subRace());
                ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC).encode(buffer, value.fightingStyle());
                ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC).encode(buffer, value.devilFruit());
            }
        };
    }

    public record BasicStats(double doriki, int cola, int ultraCola, double loyalty, long bounty, long belly, long extol,
                             Identity identity, boolean hasShadow, boolean hasHeart, boolean hasStrawDoll,
                             boolean isRogue, double stamina, double maxStamina, Map<String, Integer> trainingPoints) {
        public static final Codec<BasicStats> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.DOUBLE.fieldOf("doriki").forGetter(BasicStats::doriki),
                Codec.INT.fieldOf("cola").forGetter(BasicStats::cola),
                Codec.INT.fieldOf("ultraCola").forGetter(BasicStats::ultraCola),
                Codec.DOUBLE.fieldOf("loyalty").forGetter(BasicStats::loyalty),
                Codec.LONG.fieldOf("bounty").forGetter(BasicStats::bounty),
                Codec.LONG.fieldOf("belly").forGetter(BasicStats::belly),
                Codec.LONG.fieldOf("extol").forGetter(BasicStats::extol),
                Identity.CODEC.fieldOf("identity").forGetter(BasicStats::identity),
                Codec.BOOL.fieldOf("hasShadow").forGetter(BasicStats::hasShadow),
                Codec.BOOL.fieldOf("hasHeart").forGetter(BasicStats::hasHeart),
                Codec.BOOL.fieldOf("hasStrawDoll").forGetter(BasicStats::hasStrawDoll),
                Codec.BOOL.fieldOf("isRogue").forGetter(BasicStats::isRogue),
                Codec.DOUBLE.fieldOf("stamina").forGetter(BasicStats::stamina),
                Codec.DOUBLE.fieldOf("maxStamina").forGetter(BasicStats::maxStamina),
                Codec.unboundedMap(Codec.STRING, Codec.INT).fieldOf("trainingPoints").forGetter(BasicStats::trainingPoints)
        ).apply(instance, BasicStats::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, BasicStats> STREAM_CODEC = new StreamCodec<RegistryFriendlyByteBuf, BasicStats>() {
            @Override
            public BasicStats decode(RegistryFriendlyByteBuf buffer) {
                return new BasicStats(
                        buffer.readDouble(), buffer.readInt(), buffer.readInt(), buffer.readDouble(),
                        buffer.readLong(), buffer.readLong(), buffer.readLong(),
                        Identity.STREAM_CODEC.decode(buffer),
                        buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean(),
                        buffer.readBoolean(),
                        buffer.readDouble(), buffer.readDouble(),
                        ByteBufCodecs.<RegistryFriendlyByteBuf, String, Integer, Map<String, Integer>>map(HashMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.VAR_INT).decode(buffer)
                );
            }

            @Override
            public void encode(RegistryFriendlyByteBuf buffer, BasicStats value) {
                buffer.writeDouble(value.doriki());
                buffer.writeInt(value.cola());
                buffer.writeInt(value.ultraCola());
                buffer.writeDouble(value.loyalty());
                buffer.writeLong(value.bounty());
                buffer.writeLong(value.belly());
                buffer.writeLong(value.extol());
                Identity.STREAM_CODEC.encode(buffer, value.identity());
                buffer.writeBoolean(value.hasShadow());
                buffer.writeBoolean(value.hasHeart());
                buffer.writeBoolean(value.hasStrawDoll());
                buffer.writeBoolean(value.isRogue());
                buffer.writeDouble(value.stamina());
                buffer.writeDouble(value.maxStamina());
                ByteBufCodecs.<RegistryFriendlyByteBuf, String, Integer, Map<String, Integer>>map(HashMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.VAR_INT).encode(buffer, value.trainingPoints());
            }
        };
    }

    public record CombatStats(boolean isLogia, boolean hasYamiPower, boolean hasYomiPower, boolean hasAwakenedFruit,
                              float busoshokuHakiExp, float kenbunshokuHakiExp, int hakiOveruse, java.util.List<String> equippedAbilities,
                              java.util.List<String> activeAbilities,
                              int currentCombatBarSet, int selectedAbilitySlot, boolean isInCombatMode, boolean busoshokuActive, boolean kenbunshokuActive,
                              java.util.Map<String, Long> abilityCooldowns, java.util.Map<String, Long> abilityMaxCooldowns) {
        public static final Codec<CombatStats> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.fieldOf("isLogia").forGetter(CombatStats::isLogia),
                Codec.BOOL.fieldOf("hasYamiPower").forGetter(CombatStats::hasYamiPower),
                Codec.BOOL.fieldOf("hasYomiPower").forGetter(CombatStats::hasYomiPower),
                Codec.BOOL.fieldOf("hasAwakenedFruit").forGetter(CombatStats::hasAwakenedFruit),
                Codec.FLOAT.fieldOf("busoshokuHakiExp").forGetter(CombatStats::busoshokuHakiExp),
                Codec.FLOAT.fieldOf("kenbunshokuHakiExp").forGetter(CombatStats::kenbunshokuHakiExp),
                Codec.INT.fieldOf("hakiOveruse").forGetter(CombatStats::hakiOveruse),
                Codec.STRING.listOf().fieldOf("equippedAbilities").forGetter(CombatStats::equippedAbilities),
                Codec.STRING.listOf().fieldOf("activeAbilities").forGetter(CombatStats::activeAbilities),
                Codec.INT.fieldOf("currentCombatBarSet").forGetter(CombatStats::currentCombatBarSet),
                Codec.INT.fieldOf("selectedAbilitySlot").forGetter(CombatStats::selectedAbilitySlot),
                Codec.BOOL.fieldOf("isInCombatMode").forGetter(CombatStats::isInCombatMode),
                Codec.BOOL.fieldOf("busoshokuActive").forGetter(CombatStats::busoshokuActive),
                Codec.BOOL.fieldOf("kenbunshokuActive").forGetter(CombatStats::kenbunshokuActive),
                Codec.unboundedMap(Codec.STRING, Codec.LONG).fieldOf("abilityCooldowns").forGetter(CombatStats::abilityCooldowns),
                Codec.unboundedMap(Codec.STRING, Codec.LONG).fieldOf("abilityMaxCooldowns").forGetter(CombatStats::abilityMaxCooldowns)
        ).apply(instance, CombatStats::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, CombatStats> STREAM_CODEC = new StreamCodec<RegistryFriendlyByteBuf, CombatStats>() {
            @Override
            public CombatStats decode(RegistryFriendlyByteBuf buffer) {
                return new CombatStats(
                        buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean(),
                        buffer.readFloat(), buffer.readFloat(), buffer.readInt(),
                        ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()).decode(buffer),
                        ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()).decode(buffer),
                        buffer.readVarInt(), buffer.readVarInt(), buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean(),
                        ByteBufCodecs.<RegistryFriendlyByteBuf, String, Long, java.util.Map<String, Long>>map(java.util.HashMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.VAR_LONG).decode(buffer),
                        ByteBufCodecs.<RegistryFriendlyByteBuf, String, Long, java.util.Map<String, Long>>map(java.util.HashMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.VAR_LONG).decode(buffer)
                );
            }

            @Override
            public void encode(RegistryFriendlyByteBuf buffer, CombatStats value) {
                buffer.writeBoolean(value.isLogia());
                buffer.writeBoolean(value.hasYamiPower());
                buffer.writeBoolean(value.hasYomiPower());
                buffer.writeBoolean(value.hasAwakenedFruit());
                buffer.writeFloat(value.busoshokuHakiExp());
                buffer.writeFloat(value.kenbunshokuHakiExp());
                buffer.writeInt(value.hakiOveruse());
                ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()).encode(buffer, value.equippedAbilities());
                ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()).encode(buffer, value.activeAbilities());
                buffer.writeVarInt(value.currentCombatBarSet());
                buffer.writeVarInt(value.selectedAbilitySlot());
                buffer.writeBoolean(value.isInCombatMode());
                buffer.writeBoolean(value.busoshokuActive());
                buffer.writeBoolean(value.kenbunshokuActive());
                ByteBufCodecs.<RegistryFriendlyByteBuf, String, Long, java.util.Map<String, Long>>map(java.util.HashMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.VAR_LONG).encode(buffer, value.abilityCooldowns());
                ByteBufCodecs.<RegistryFriendlyByteBuf, String, Long, java.util.Map<String, Long>>map(java.util.HashMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.VAR_LONG).encode(buffer, value.abilityMaxCooldowns());
            }
        };
    }

    public static final Codec<PlayerStats> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BasicStats.CODEC.fieldOf("basic").forGetter(PlayerStats::getBasic),
            CombatStats.CODEC.fieldOf("combat").forGetter(PlayerStats::getCombat)
    ).apply(instance, (basic, combat) -> {
        PlayerStats stats = new PlayerStats();
        stats.deserialize(basic);
        stats.deserialize(combat);
        return stats;
    }));

    public static final StreamCodec<RegistryFriendlyByteBuf, PlayerStats> STREAM_CODEC = StreamCodec.composite(
            BasicStats.STREAM_CODEC, PlayerStats::getBasic,
            CombatStats.STREAM_CODEC, PlayerStats::getCombat,
            (basic, combat) -> {
                PlayerStats stats = new PlayerStats();
                stats.deserialize(basic);
                stats.deserialize(combat);
                return stats;
            }
    );

    private BasicStats basic = new BasicStats(0, 100, 0, 0, 0, 0, 0, new Identity(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()), true, true, true, false, 100.0, 100.0, new HashMap<>());
    private CombatStats combat = new CombatStats(false, false, false, false, 0, 0, 0, new java.util.ArrayList<>(java.util.Collections.nCopies(24, "")), new java.util.ArrayList<>(), 0, 0, false, false, false, new java.util.HashMap<>(), new java.util.HashMap<>());

    public PlayerStats() {}

    public static PlayerStats get(LivingEntity entity) {
        return entity.getData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.PLAYER_STATS);
    }

    public void deserialize(BasicStats basic) {
        this.basic = basic;
    }

    public void deserialize(CombatStats combat) {
        this.combat = combat;
    }

    // Delegation getters/setters
    public double getDoriki() { return basic.doriki(); }
    public long getBelly() { return basic.belly(); }
    public long getBounty() { return basic.bounty(); }
    public Optional<ResourceLocation> getFaction() { return basic.identity().faction(); }
    public Optional<ResourceLocation> getRace() { return basic.identity().race(); }
    public Optional<ResourceLocation> getSubRace() { return basic.identity().subRace(); }
    public Optional<ResourceLocation> getFightingStyle() { return basic.identity().fightingStyle(); }
    public float getBusoshokuHakiExp() { return combat.busoshokuHakiExp(); }
    public float getKenbunshokuHakiExp() { return combat.kenbunshokuHakiExp(); }
    public int getSelectedAbilitySlot() { return combat.selectedAbilitySlot(); }
    public int getCombatBarSet() { return combat.currentCombatBarSet(); }
    public boolean hasShadow() { return basic.hasShadow(); }
    public boolean isBusoshokuActive() { return combat.busoshokuActive(); }
    public boolean isKenbunshokuActive() { return combat.kenbunshokuActive(); }
    public double getStamina() { return basic.stamina(); }
    public double getMaxStamina() { return basic.maxStamina(); }

    public void setStamina(double stamina) {
        this.basic = updateBasicStats(basic.doriki(), basic.cola(), basic.ultraCola(), basic.loyalty(), basic.bounty(), basic.belly(), basic.extol(), basic.hasShadow(), basic.hasHeart(), basic.hasStrawDoll(), basic.isRogue(), stamina, basic.maxStamina());
    }

    public void setMaxStamina(double maxStamina) {
        this.basic = updateBasicStats(basic.doriki(), basic.cola(), basic.ultraCola(), basic.loyalty(), basic.bounty(), basic.belly(), basic.extol(), basic.hasShadow(), basic.hasHeart(), basic.hasStrawDoll(), basic.isRogue(), basic.stamina(), maxStamina);
    }

    public boolean hasDevilFruit() { return basic.identity().devilFruit().isPresent(); }
    public boolean isRogue() { return basic.isRogue(); }
    public boolean isLogia() { return combat.isLogia(); }

    public boolean isPirate() { return basic.identity().faction().isPresent() && basic.identity().faction().get().getPath().equals("pirate"); }
    public boolean isMarine() { return basic.identity().faction().isPresent() && basic.identity().faction().get().getPath().equals("marine"); }
    public boolean isBountyHunter() { return basic.identity().faction().isPresent() && basic.identity().faction().get().getPath().equals("bounty_hunter"); }
    public boolean isRevolutionary() { return basic.identity().faction().isPresent() && basic.identity().faction().get().getPath().equals("revolutionary"); }
    public boolean isBandit() { return basic.identity().faction().isPresent() && basic.identity().faction().get().getPath().equals("bandit"); }

    public void setLogia(boolean logia) {
        this.combat = new CombatStats(logia, combat.hasYamiPower(), combat.hasYomiPower(), combat.hasAwakenedFruit(), combat.busoshokuHakiExp(), combat.kenbunshokuHakiExp(), combat.hakiOveruse(), combat.equippedAbilities(), combat.activeAbilities(), combat.currentCombatBarSet(), combat.selectedAbilitySlot(), combat.isInCombatMode(), combat.busoshokuActive(), combat.kenbunshokuActive(), combat.abilityCooldowns(), combat.abilityMaxCooldowns());
    }

    public int getTrainingPoints(TrainingPointType type) {
        return basic.trainingPoints().getOrDefault(type.name(), 0);
    }

    public void setTrainingPoints(TrainingPointType type, int amount) {
        Map<String, Integer> map = new HashMap<>(basic.trainingPoints());
        map.put(type.name(), Math.max(0, amount));
        this.basic = new BasicStats(basic.doriki(), basic.cola(), basic.ultraCola(), basic.loyalty(), basic.bounty(), basic.belly(), basic.extol(), basic.identity(), basic.hasShadow(), basic.hasHeart(), basic.hasStrawDoll(), basic.isRogue(), basic.stamina(), basic.maxStamina(), map);
    }

    public void alterTrainingPoints(TrainingPointType type, int amount) {
        setTrainingPoints(type, getTrainingPoints(type) + amount);
    }
    public Optional<ResourceLocation> getDevilFruit() { return basic.identity().devilFruit(); }
    public boolean isInCombatMode() { return combat.isInCombatMode(); }

    public void setBelly(long belly) {
        this.basic = updateBasicStats(basic.doriki(), basic.cola(), basic.ultraCola(), basic.loyalty(), basic.bounty(), belly, basic.extol(), basic.hasShadow(), basic.hasHeart(), basic.hasStrawDoll(), basic.isRogue(), basic.stamina(), basic.maxStamina());
    }

    public void alterBelly(long amount) {
        setBelly(getBelly() + amount);
    }

    public void setExtol(long extol) {
        this.basic = updateBasicStats(basic.doriki(), basic.cola(), basic.ultraCola(), basic.loyalty(), basic.bounty(), basic.belly(), extol, basic.hasShadow(), basic.hasHeart(), basic.hasStrawDoll(), basic.isRogue(), basic.stamina(), basic.maxStamina());
    }

    public void alterExtol(long amount) {
        setExtol(getExtol() + amount);
    }

    public long getExtol() { return basic.extol(); }
    
    public void setHasShadow(boolean hasShadow) {
        this.basic = updateBasicStats(basic.doriki(), basic.cola(), basic.ultraCola(), basic.loyalty(), basic.bounty(), basic.belly(), basic.extol(), hasShadow, basic.hasHeart(), basic.hasStrawDoll(), basic.isRogue(), basic.stamina(), basic.maxStamina());
    }

    public void setHasHeart(boolean hasHeart) {
        this.basic = updateBasicStats(basic.doriki(), basic.cola(), basic.ultraCola(), basic.loyalty(), basic.bounty(), basic.belly(), basic.extol(), basic.hasShadow(), hasHeart, basic.hasStrawDoll(), basic.isRogue(), basic.stamina(), basic.maxStamina());
    }

    public boolean hasHeart() { return basic.hasHeart(); }

    public void setHasStrawDoll(boolean hasStrawDoll) {
        this.basic = updateBasicStats(basic.doriki(), basic.cola(), basic.ultraCola(), basic.loyalty(), basic.bounty(), basic.belly(), basic.extol(), basic.hasShadow(), basic.hasHeart(), hasStrawDoll, basic.isRogue(), basic.stamina(), basic.maxStamina());
    }

    public boolean hasStrawDoll() { return basic.hasStrawDoll(); }

    public void setDoriki(double doriki) {
        this.basic = updateBasicStats(doriki, basic.cola(), basic.ultraCola(), basic.loyalty(), basic.bounty(), basic.belly(), basic.extol(), basic.hasShadow(), basic.hasHeart(), basic.hasStrawDoll(), basic.isRogue(), basic.stamina(), basic.maxStamina());
    }

    public void setBounty(long bounty) {
        this.basic = updateBasicStats(basic.doriki(), basic.cola(), basic.ultraCola(), basic.loyalty(), bounty, basic.belly(), basic.extol(), basic.hasShadow(), basic.hasHeart(), basic.hasStrawDoll(), basic.isRogue(), basic.stamina(), basic.maxStamina());
    }

    public int getMaxCola() {
        int maxCola = 100;
        maxCola += basic.ultraCola() * 20;
        return maxCola;
    }

    public void setCola(int cola) {
        this.basic = updateBasicStats(basic.doriki(), cola, basic.ultraCola(), basic.loyalty(), basic.bounty(), basic.belly(), basic.extol(), basic.hasShadow(), basic.hasHeart(), basic.hasStrawDoll(), basic.isRogue(), basic.stamina(), basic.maxStamina());
    }

    public void updateCola() {
        int clampedCola = Math.clamp(basic.cola(), 0, getMaxCola());
        if (clampedCola != basic.cola()) {
            setCola(clampedCola);
        }
    }

    public boolean isCyborg() {
        return basic.identity().race().map(race -> race.equals(xyz.pixelatedw.mineminenomi.init.ModRegistries.RACES.getKey(xyz.pixelatedw.mineminenomi.init.ModRaces.CYBORG.get()))).orElse(false);
    }

    public void setFaction(ResourceLocation faction) {
        Identity newIdentity = new Identity(Optional.ofNullable(faction), basic.identity().race(), basic.identity().subRace(), basic.identity().fightingStyle(), basic.identity().devilFruit());
        this.basic = updateBasicStats(newIdentity);
    }

    public void setRace(ResourceLocation race) {
        Identity newIdentity = new Identity(basic.identity().faction(), Optional.ofNullable(race), basic.identity().subRace(), basic.identity().fightingStyle(), basic.identity().devilFruit());
        this.basic = updateBasicStats(newIdentity);
    }

    public void setSubRace(ResourceLocation subRace) {
        Identity newIdentity = new Identity(basic.identity().faction(), basic.identity().race(), Optional.ofNullable(subRace), basic.identity().fightingStyle(), basic.identity().devilFruit());
        this.basic = updateBasicStats(newIdentity);
    }

    public void setFightingStyle(ResourceLocation fightingStyle) {
        Identity newIdentity = new Identity(basic.identity().faction(), basic.identity().race(), basic.identity().subRace(), Optional.ofNullable(fightingStyle), basic.identity().devilFruit());
        this.basic = updateBasicStats(newIdentity);
    }

    public void setDevilFruit(ResourceLocation devilFruit) {
        Identity newIdentity = new Identity(basic.identity().faction(), basic.identity().race(), basic.identity().subRace(), basic.identity().fightingStyle(), Optional.ofNullable(devilFruit));
        this.basic = updateBasicStats(newIdentity);
    }

    public void setBusoshokuHakiExp(float exp) {
        this.combat = new CombatStats(combat.isLogia(), combat.hasYamiPower(), combat.hasYomiPower(), combat.hasAwakenedFruit(), exp, combat.kenbunshokuHakiExp(), combat.hakiOveruse(), combat.equippedAbilities(), combat.activeAbilities(), combat.currentCombatBarSet(), combat.selectedAbilitySlot(), combat.isInCombatMode(), combat.busoshokuActive(), combat.kenbunshokuActive(), combat.abilityCooldowns(), combat.abilityMaxCooldowns());
    }

    public void setKenbunshokuHakiExp(float exp) {
        this.combat = new CombatStats(combat.isLogia(), combat.hasYamiPower(), combat.hasYomiPower(), combat.hasAwakenedFruit(), combat.busoshokuHakiExp(), exp, combat.hakiOveruse(), combat.equippedAbilities(), combat.activeAbilities(), combat.currentCombatBarSet(), combat.selectedAbilitySlot(), combat.isInCombatMode(), combat.busoshokuActive(), combat.kenbunshokuActive(), combat.abilityCooldowns(), combat.abilityMaxCooldowns());
    }

    public void setBusoshokuActive(boolean active) {
        this.combat = new CombatStats(combat.isLogia(), combat.hasYamiPower(), combat.hasYomiPower(), combat.hasAwakenedFruit(), combat.busoshokuHakiExp(), combat.kenbunshokuHakiExp(), combat.hakiOveruse(), combat.equippedAbilities(), combat.activeAbilities(), combat.currentCombatBarSet(), combat.selectedAbilitySlot(), combat.isInCombatMode(), active, combat.kenbunshokuActive(), combat.abilityCooldowns(), combat.abilityMaxCooldowns());
    }

    public void setKenbunshokuActive(boolean active) {
        this.combat = new CombatStats(combat.isLogia(), combat.hasYamiPower(), combat.hasYomiPower(), combat.hasAwakenedFruit(), combat.busoshokuHakiExp(), combat.kenbunshokuHakiExp(), combat.hakiOveruse(), combat.equippedAbilities(), combat.activeAbilities(), combat.currentCombatBarSet(), combat.selectedAbilitySlot(), combat.isInCombatMode(), combat.busoshokuActive(), active, combat.abilityCooldowns(), combat.abilityMaxCooldowns());
    }

    public void setCombatBarSet(int set) {
        this.combat = new CombatStats(combat.isLogia(), combat.hasYamiPower(), combat.hasYomiPower(), combat.hasAwakenedFruit(), combat.busoshokuHakiExp(), combat.kenbunshokuHakiExp(), combat.hakiOveruse(), combat.equippedAbilities(), combat.activeAbilities(), set, combat.selectedAbilitySlot(), combat.isInCombatMode(), combat.busoshokuActive(), combat.kenbunshokuActive(), combat.abilityCooldowns(), combat.abilityMaxCooldowns());
    }

    public void setInCombatMode(boolean inCombatMode) {
        this.combat = new CombatStats(combat.isLogia(), combat.hasYamiPower(), combat.hasYomiPower(), combat.hasAwakenedFruit(), combat.busoshokuHakiExp(), combat.kenbunshokuHakiExp(), combat.hakiOveruse(), combat.equippedAbilities(), combat.activeAbilities(), combat.currentCombatBarSet(), combat.selectedAbilitySlot(), inCombatMode, combat.busoshokuActive(), combat.kenbunshokuActive(), combat.abilityCooldowns(), combat.abilityMaxCooldowns());
    }

    public void setSelectedAbilitySlot(int slot) {
        this.combat = new CombatStats(combat.isLogia(), combat.hasYamiPower(), combat.hasYomiPower(), combat.hasAwakenedFruit(), combat.busoshokuHakiExp(), combat.kenbunshokuHakiExp(), combat.hakiOveruse(), combat.equippedAbilities(), combat.activeAbilities(), combat.currentCombatBarSet(), slot, combat.isInCombatMode(), combat.busoshokuActive(), combat.kenbunshokuActive(), combat.abilityCooldowns(), combat.abilityMaxCooldowns());
    }

    private BasicStats updateBasicStats(Identity identity) {
        return new BasicStats(basic.doriki(), basic.cola(), basic.ultraCola(), basic.loyalty(), basic.bounty(), basic.belly(), basic.extol(), identity, basic.hasShadow(), basic.hasHeart(), basic.hasStrawDoll(), basic.isRogue(), basic.stamina(), basic.maxStamina(), basic.trainingPoints());
    }

    private BasicStats updateBasicStats(double doriki, int cola, int ultraCola, double loyalty, long bounty, long belly, long extol, boolean hasShadow, boolean hasHeart, boolean hasStrawDoll, boolean isRogue, double stamina, double maxStamina) {
        return new BasicStats(doriki, cola, ultraCola, loyalty, bounty, belly, extol, basic.identity(), hasShadow, hasHeart, hasStrawDoll, isRogue, stamina, maxStamina, basic.trainingPoints());
    }

    public void setEquippedAbility(int slot, ResourceLocation ability) {
        java.util.List<String> list = new java.util.ArrayList<>(combat.equippedAbilities());
        if (slot >= 0 && slot < list.size()) {
            list.set(slot, ability != null ? ability.toString() : "");
            this.combat = new CombatStats(combat.isLogia(), combat.hasYamiPower(), combat.hasYomiPower(), combat.hasAwakenedFruit(), combat.busoshokuHakiExp(), combat.kenbunshokuHakiExp(), combat.hakiOveruse(), list, combat.activeAbilities(), combat.currentCombatBarSet(), combat.selectedAbilitySlot(), combat.isInCombatMode(), combat.busoshokuActive(), combat.kenbunshokuActive(), combat.abilityCooldowns(), combat.abilityMaxCooldowns());
        }
    }

    public void grantAbility(ResourceLocation ability) {
        if (ability == null) return;
        String abilityId = ability.toString();
        java.util.List<String> list = new java.util.ArrayList<>(combat.equippedAbilities());
        if (!list.contains(abilityId)) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isEmpty()) {
                    list.set(i, abilityId);
                    
                    xyz.pixelatedw.mineminenomi.api.abilities.Ability instance = xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY.get(ability);
                    if (instance != null && instance.isPassive()) {
                        java.util.List<String> activeList = new java.util.ArrayList<>(combat.activeAbilities());
                        if (!activeList.contains(abilityId)) activeList.add(abilityId);
                        this.combat = new CombatStats(combat.isLogia(), combat.hasYamiPower(), combat.hasYomiPower(), combat.hasAwakenedFruit(), combat.busoshokuHakiExp(), combat.kenbunshokuHakiExp(), combat.hakiOveruse(), list, activeList, combat.currentCombatBarSet(), combat.selectedAbilitySlot(), combat.isInCombatMode(), combat.busoshokuActive(), combat.kenbunshokuActive(), combat.abilityCooldowns(), combat.abilityMaxCooldowns());
                        return;
                    }

                    break;
                }
            }
            this.combat = new CombatStats(combat.isLogia(), combat.hasYamiPower(), combat.hasYomiPower(), combat.hasAwakenedFruit(), combat.busoshokuHakiExp(), combat.kenbunshokuHakiExp(), combat.hakiOveruse(), list, combat.activeAbilities(), combat.currentCombatBarSet(), combat.selectedAbilitySlot(), combat.isInCombatMode(), combat.busoshokuActive(), combat.kenbunshokuActive(), combat.abilityCooldowns(), combat.abilityMaxCooldowns());
        }
    }

    public String getEquippedAbility(int slot) {
        if (slot >= 0 && slot < combat.equippedAbilities().size()) {
            return combat.equippedAbilities().get(slot);
        }
        return "";
    }

    public boolean isAbilityActive(String abilityId) {
        return combat.activeAbilities().contains(abilityId);
    }

    public void setAbilityActive(String abilityId, boolean active) {
        java.util.List<String> activeList = new java.util.ArrayList<>(combat.activeAbilities());
        if (active) {
            if (!activeList.contains(abilityId)) activeList.add(abilityId);
        } else {
            activeList.remove(abilityId);
        }
        this.combat = new CombatStats(combat.isLogia(), combat.hasYamiPower(), combat.hasYomiPower(), combat.hasAwakenedFruit(),
                combat.busoshokuHakiExp(), combat.kenbunshokuHakiExp(), combat.hakiOveruse(), combat.equippedAbilities(),
                activeList, combat.currentCombatBarSet(), combat.selectedAbilitySlot(), combat.isInCombatMode(),
                combat.busoshokuActive(), combat.kenbunshokuActive(), combat.abilityCooldowns(), combat.abilityMaxCooldowns());
    }

    public void setAbilityCooldown(String abilityId, long cooldownDurationTicks, long currentGameTime) {
        java.util.Map<String, Long> cooldowns = new java.util.HashMap<>(combat.abilityCooldowns());
        java.util.Map<String, Long> maxCooldowns = new java.util.HashMap<>(combat.abilityMaxCooldowns());
        cooldowns.put(abilityId, currentGameTime + cooldownDurationTicks);
        maxCooldowns.put(abilityId, cooldownDurationTicks);
        this.combat = new CombatStats(combat.isLogia(), combat.hasYamiPower(), combat.hasYomiPower(), combat.hasAwakenedFruit(),
                combat.busoshokuHakiExp(), combat.kenbunshokuHakiExp(), combat.hakiOveruse(), combat.equippedAbilities(),
                combat.activeAbilities(), combat.currentCombatBarSet(), combat.selectedAbilitySlot(), combat.isInCombatMode(),
                combat.busoshokuActive(), combat.kenbunshokuActive(), cooldowns, maxCooldowns);
    }

    public java.util.List<String> getActiveAbilities() {
        return combat.activeAbilities();
    }

    public BasicStats getBasic() { return basic; }
    public CombatStats getCombat() { return combat; }

    public void setBasic(BasicStats basic) { this.basic = basic; }
    public void setCombat(CombatStats combat) { this.combat = combat; }

    public void sync(LivingEntity entity) {
        if (entity instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
            xyz.pixelatedw.mineminenomi.networking.ModNetworking.sendTo(new xyz.pixelatedw.mineminenomi.networking.packets.SUpdatePlayerStatsPacket(this), serverPlayer);
        }
    }
}
