package xyz.pixelatedw.mineminenomi.api.entities.charactercreator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class Race implements ICharacterCreatorEntry {
    private ResourceLocation key;
    private Component label;
    private CharacterCreatorSelectionInfo bookInfo;
    private boolean inBook = false;
    private boolean hasRenderFeatures = false;
    private boolean isSubRace;
    private List<Supplier<Race>> subRaces = new ArrayList<>();

    public static Race subRace() {
        Race race = new Race();
        race.isSubRace = true;
        return race;
    }

    public Race setRenderFeatures() {
        this.hasRenderFeatures = true;
        return this;
    }

    public Race setBookDetails(CharacterCreatorSelectionInfo info) {
        this.bookInfo = info;
        this.inBook = true;
        return this;
    }

    public boolean hasRenderFeatures() {
        return this.hasRenderFeatures;
    }

    public boolean isMainRace() {
        return !this.isSubRace;
    }

    public boolean isSubRace() {
        return this.isSubRace;
    }

    public boolean hasSubRaces() {
        return !this.subRaces.isEmpty();
    }

    public List<Supplier<Race>> getSubRaces() {
        return this.subRaces;
    }

    public Race setSubRaces(List<Supplier<Race>> subRaces) {
        this.subRaces = subRaces;
        return this;
    }

    public Component getLabel() {
        if (this.label == null) {
            if (this.key == null && xyz.pixelatedw.mineminenomi.init.ModRegistries.RACES != null) {
                this.key = xyz.pixelatedw.mineminenomi.init.ModRegistries.RACES.getKey(this);
            }
            if (this.key != null) {
                this.label = Component.translatable("race.mineminenomi." + this.key.getPath());
            }
        }
        return this.label != null ? this.label : Component.literal("Unknown Race");
    }

    public CharacterCreatorSelectionInfo getSelectionInfo() {
        return this.bookInfo;
    }

    public boolean isInBook() {
        return this.inBook;
    }

    public int getBookOrder() {
        return this.bookInfo == null ? -1 : this.bookInfo.getOrder();
    }

    public void setRegistryName(ResourceLocation name) {
        this.key = name;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.key;
    }
}
