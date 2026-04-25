package xyz.pixelatedw.mineminenomi.api.entities.charactercreator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class CharacterCreatorSelectionInfo {
    private final ResourceLocation icon;
    private final int order;
    private final List<Supplier<? extends Ability>> topAbilities = new ArrayList<>();
    private final List<Supplier<? extends Ability>> bottomAbilities = new ArrayList<>();

    public CharacterCreatorSelectionInfo(ResourceLocation icon, int order) {
        this.icon = icon;
        this.order = order;
    }

    @SafeVarargs
    public final void addTopAbilities(Supplier<? extends Ability>... cores) {
        this.topAbilities.addAll(Arrays.asList(cores));
    }

    @SafeVarargs
    public final void addBottomAbilities(Supplier<? extends Ability>... cores) {
        this.bottomAbilities.addAll(Arrays.asList(cores));
    }

    public ResourceLocation getIcon() {
        return this.icon;
    }

    public int getOrder() {
        return this.order;
    }

    public List<Supplier<? extends Ability>> getTopAbilities() {
        return this.topAbilities;
    }

    public List<Supplier<? extends Ability>> getBottomAbilities() {
        return this.bottomAbilities;
    }
}
