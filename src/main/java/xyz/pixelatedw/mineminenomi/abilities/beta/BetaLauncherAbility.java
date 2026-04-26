package xyz.pixelatedw.mineminenomi.abilities.beta;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
public class BetaLauncherAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "beta_beta_no_mi");
    private int triggers = 0; private long lastTriggerTick = 0;
    public BetaLauncherAbility() { super(FRUIT); }
    @Override protected void startUsing(LivingEntity entity) { this.triggers = 0; this.lastTriggerTick = entity.level().getGameTime(); }
    @Override protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            long currentTick = entity.level().getGameTime();
            if (triggers < 6) {
                if (currentTick - lastTriggerTick >= 3) { triggers++; lastTriggerTick = currentTick; }
            } else { this.stop(entity); }
        }
    }
    @Override protected void stopUsing(LivingEntity entity) { this.startCooldown(entity, 180); }
    @Override public Component getDisplayName() { return Component.translatable("ability.mineminenomi.beta_launcher"); }
}
