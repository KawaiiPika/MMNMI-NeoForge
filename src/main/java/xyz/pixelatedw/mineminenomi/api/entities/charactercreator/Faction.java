package xyz.pixelatedw.mineminenomi.api.entities.charactercreator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.factions.IFactionRank;
import xyz.pixelatedw.mineminenomi.init.ModRegistries;

public class Faction implements ICharacterCreatorEntry {
    private ResourceLocation key;
    private Component label;
    private CharacterCreatorSelectionInfo bookInfo;
    private boolean inBook = false;
    private Predicate<LivingEntity> canReceiveBounty = (entity) -> false;
    private Predicate<LivingEntity> canReceiveLoyalty = (entity) -> false;
    private final List<BiPredicate<LivingEntity, LivingEntity>> canHurtList = new ArrayList<>();
    private Class<? extends Enum<?>> ranks = null;
    private Color flagBackgroundColor = Color.WHITE;

    public Faction() {}

    @Override
    public Component getLabel() {
        if (this.label == null) {
            if (this.key == null && ModRegistries.FACTIONS != null) {
                this.key = ModRegistries.FACTIONS.getKey(this);
            }
            if (this.key != null) {
                this.label = Component.translatable("faction.mineminenomi." + this.key.getPath());
            }
        }
        return this.label != null ? this.label : Component.literal("Unknown Faction");
    }

    public Faction setBookDetails(CharacterCreatorSelectionInfo info) {
        this.bookInfo = info;
        this.inBook = true;
        return this;
    }

    public boolean canReceiveBounty(LivingEntity player) {
        return this.canReceiveBounty.test(player);
    }

    public Faction setCanReceiveBountyCheck(Predicate<LivingEntity> check) {
        this.canReceiveBounty = check;
        return this;
    }

    public boolean canReceiveLoyalty(LivingEntity player) {
        return this.canReceiveLoyalty.test(player);
    }

    public Faction setCanReceiveLoyaltyCheck(Predicate<LivingEntity> check) {
        this.canReceiveLoyalty = check;
        return this;
    }

    public boolean canHurt(LivingEntity attacker, LivingEntity target) {
        for (BiPredicate<LivingEntity, LivingEntity> canHurt : this.canHurtList) {
            if (!canHurt.test(attacker, target)) {
                return false;
            }
        }
        return true;
    }

    public Faction addCanHurtCheck(BiPredicate<LivingEntity, LivingEntity> check) {
        this.canHurtList.add(check);
        return this;
    }

    public <E extends Enum<E> & IFactionRank> Faction setRanks(Class<E> ranks) {
        this.ranks = ranks;
        return this;
    }

    @SuppressWarnings("unchecked")
    public <E extends Enum<E> & IFactionRank> E[] getRanks() {
        if (this.ranks == null) {
            return null;
        } else {
            return (E[]) (this.ranks.getEnumConstants());
        }
    }

    @Override
    public CharacterCreatorSelectionInfo getSelectionInfo() {
        return this.bookInfo;
    }

    @Override
    public boolean isInBook() {
        return this.inBook;
    }

    @Override
    public int getBookOrder() {
        return this.bookInfo == null ? -1 : this.bookInfo.getOrder();
    }

    public Faction setFlagBackgroundColor(int color) {
        this.flagBackgroundColor = new Color(color);
        return this;
    }

    public Color getFlagBackgroundColor() {
        return this.flagBackgroundColor;
    }

    public void setRegistryName(ResourceLocation name) {
        this.key = name;
    }

    @Override
    public ResourceLocation getRegistryName() {
        if (this.key == null && ModRegistries.FACTIONS != null) {
            this.key = ModRegistries.FACTIONS.getKey(this);
        }
        return this.key;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Faction otherFaction) {
            return this.getRegistryName() != null && otherFaction.getRegistryName() != null && this.getRegistryName().equals(otherFaction.getRegistryName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getRegistryName() != null ? this.getRegistryName().hashCode() : 0;
    }
}
