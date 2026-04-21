package xyz.pixelatedw.mineminenomi.api.abilities.nodes;

import java.util.LinkedHashSet;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class AbilityNode {
    private final Set<AbilityNode> prerequisites = new LinkedHashSet<>();
    private boolean unlocked = false;
    private final Component localizedName;
    private final ResourceLocation icon;
    private final NodePos position;

    public AbilityNode(Component localizedName, ResourceLocation icon, NodePos position) {
        this.localizedName = localizedName;
        this.icon = icon;
        this.position = position;
    }

    public Component getLocalizedName() { return this.localizedName; }
    public ResourceLocation getIcon() { return this.icon; }
    public NodePos getPosition() { return this.position; }

    public Set<AbilityNode> getPrerequisites() { return Set.copyOf(this.prerequisites); }

    public final void addPrerequisites(AbilityNode... nodes) {
        for (AbilityNode node : nodes) {
            this.prerequisites.add(node);
        }
    }

    public boolean isUnlocked() { return this.unlocked; }

    public void setUnlocked(boolean unlocked) { this.unlocked = unlocked; }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("unlocked", this.unlocked);
        return tag;
    }

    public void load(CompoundTag tag) {
        this.unlocked = tag.getBoolean("unlocked");
    }

    public static record NodePos(float x, float y) {}
}
