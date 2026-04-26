package xyz.pixelatedw.mineminenomi.abilities.kuku;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
public class GourmetamorphosisAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kuku_kuku_no_mi");
    public GourmetamorphosisAbility() { super(FRUIT); }
    @Override protected void startUsing(LivingEntity entity) {}
    @Override protected void onTick(LivingEntity entity, long duration) { if (getDuration(entity) >= 1200) { this.stop(entity); } }
    @Override protected void stopUsing(LivingEntity entity) { this.startCooldown(entity, (long) (20.0F + this.getDuration(entity) / 2.0F)); }
    @Override public Component getDisplayName() { return Component.translatable("ability.mineminenomi.gourmetamorphosis"); }
}
