package xyz.pixelatedw.mineminenomi.client.render.morphs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid = ModMain.PROJECT_ID, value = Dist.CLIENT)
public class MorphRenderingHandler {

    private static final Map<ResourceLocation, MorphRenderer<?, ?>> RENDERERS = new HashMap<>();

    public static void registerRenderer(ResourceLocation id, MorphRenderer<?, ?> renderer) {
        RENDERERS.put(id, renderer);
    }

    @SubscribeEvent
    public static void onPlayerRenderPre(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();
        PlayerStats stats = PlayerStats.get(player);
        if (stats == null) return;

        ResourceLocation activeMorphModelId = null;

        for (String activeAbilityStr : stats.getActiveAbilities()) {
            ResourceLocation abilityId = ResourceLocation.parse(activeAbilityStr);
            Ability ability = ModAbilities.REGISTRY.get(abilityId);
            if (ability instanceof ZoanAbility zoanAbility) {
                activeMorphModelId = zoanAbility.getMorphModelId();
                break;
            }
        }

        if (activeMorphModelId != null) {
            MorphRenderer<?, ?> renderer = RENDERERS.get(activeMorphModelId);
            if (renderer != null) {
                event.setCanceled(true);
                // Call our custom rendering logic here
                ((MorphRenderer<AbstractClientPlayer, EntityModel<AbstractClientPlayer>>) renderer)
                        .render((AbstractClientPlayer) event.getEntity(), event.getEntity().getYRot(), event.getPartialTick(), 
                                event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
            } else {
                // If the model is not ported yet, we can either cancel or let it be scaled.
                // We'll let it render the normal player model (which will be scaled up natively!)
                // if there's no custom renderer registered.
            }
        }
    }
}
