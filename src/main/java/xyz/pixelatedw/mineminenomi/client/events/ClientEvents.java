package xyz.pixelatedw.mineminenomi.client.events;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.client.gui.overlays.AbilityBarOverlay;
import xyz.pixelatedw.mineminenomi.client.gui.overlays.HaoshokuOverlay;

@EventBusSubscriber(modid = ModMain.PROJECT_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    @SubscribeEvent
    public static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(net.minecraft.resources.ResourceLocation.withDefaultNamespace("hotbar"), net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "ability_bar"), AbilityBarOverlay.INSTANCE);
        event.registerAboveAll(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "haoshoku_flash"), HaoshokuOverlay.INSTANCE);
    }

    @SubscribeEvent
    public static void onRegisterRenderers(net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModMobs.MARINE_GRUNT.get(), 
            ctx -> new xyz.pixelatedw.mineminenomi.client.renderers.entity.OPHumanoidRenderer<>(ctx, new net.minecraft.client.model.HumanoidModel<>(ctx.bakeLayer(net.minecraft.client.model.geom.ModelLayers.PLAYER)), 0.5F));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModMobs.PIRATE_GRUNT.get(), 
            ctx -> new xyz.pixelatedw.mineminenomi.client.renderers.entity.OPHumanoidRenderer<>(ctx, new net.minecraft.client.model.HumanoidModel<>(ctx.bakeLayer(net.minecraft.client.model.geom.ModelLayers.PLAYER)), 0.5F));
        
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.GOMU_PISTOL.get(), 
            xyz.pixelatedw.mineminenomi.client.renderers.entity.GomuPistolRenderer::new);
        
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.GOMU_BAZOOKA.get(), 
            xyz.pixelatedw.mineminenomi.client.renderers.entity.GomuBazookaRenderer::new);
        
<<<<<<< HEAD
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.HIGAN.get(),
            ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));

        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.HIKEN.get(),
            ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 3.0F, true));

        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.DAI_FUNKA.get(),
            ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 2.5F, true));

        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.YASAKANI.get(),
            ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));

        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.MEIGO.get(),
            ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.5F, true));

        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.RYUSEI_KAZAN.get(),
            ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 2.0F, true));

        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.AMATERASU.get(),
            ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.5F, true));

        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.PARTISAN.get(),
            ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.5F, true));

        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.SANGO.get(),
            ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));

        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.WHITE_BLOW.get(),
            ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.5F, true));

        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.DESERT_SPADA.get(),
            ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.5F, true));

        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.STRONG_RIGHT.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.RADICAL_BEAM.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.FRESH_FIRE.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.COUP_DE_VENT.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.ELECTRICAL_LUNA.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.ELECTRICAL_SHOWER.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.ELECTRO_VISUAL.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
=======
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.HIGAN.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.HIKEN.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 3.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.DAI_FUNKA.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 2.5F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.YASAKANI.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.MEIGO.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.5F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.SPRING_DEATH_KNOCK_PROJECTILE.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.RYUSEI_KAZAN.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 2.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.PARTISAN.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.5F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.SNIPER_PELLET.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 0.5F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.GEKISHIN.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.5F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.PAD_HO.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.MERO_MERO_MELLOW.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.BARA_BARA_HO.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.NEGATIVE_HOLLOW.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.GOMU_GOMU_NO_BAZOOKA.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.NORO_NORO_BEAM.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.PISTOL_KISS.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 0.5F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.THROWN_SPEAR.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.YAKKODORI.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.SANBYAKUROKUJU_POUND_HO.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.5F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.SANJUROKU_POUND_HO_PROJECTILE.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.TATSU_MAKI_PROJECTILE.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.5F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.NANAJUNI_POUND_HO_PROJECTILE.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.NANAHYAKUNIJU_POUND_HO_PROJECTILE.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 2.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.HYAKUHACHI_POUND_HO_PROJECTILE.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.2F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.SENHACHIJU_POUND_HO_PROJECTILE.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 2.5F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.RELAX_HOUR_PROJECTILE.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
>>>>>>> 687cfa0f (Port Batch 6 fruit abilities and custom projectiles to NeoForge 1.21.1)

        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.STRONG_RIGHT.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.RADICAL_BEAM.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.FRESH_FIRE.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.COUP_DE_VENT.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.ELECTRICAL_LUNA.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.ELECTRICAL_SHOWER.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.ELECTRO_VISUAL.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));

        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.UCHIMIZU.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.YARINAMI.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.MIZU_TAIHO.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.MIZU_SHURYUDAN.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.PACK_OF_SHARKS.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));

        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.SPHERE.get(),
            xyz.pixelatedw.mineminenomi.client.renderers.entities.SphereRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(xyz.pixelatedw.mineminenomi.client.models.morphs.ZouHeavyModel.LAYER_LOCATION, xyz.pixelatedw.mineminenomi.client.models.morphs.ZouHeavyModel::createBodyLayer);
        event.registerLayerDefinition(xyz.pixelatedw.mineminenomi.client.models.morphs.ZouGuardModel.LAYER_LOCATION, xyz.pixelatedw.mineminenomi.client.models.morphs.ZouGuardModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onClientSetup(net.neoforged.fml.event.lifecycle.FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            xyz.pixelatedw.mineminenomi.client.render.ModRenderTypeBuffers.getInstance().initHakiAuraShader(Minecraft.getInstance());
        });
    }

    @SubscribeEvent
    public static void onRenderLevelStage(net.neoforged.neoforge.client.event.RenderLevelStageEvent event) {
        if (event.getStage() == net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null) return;
            
            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(mc.player);
            if (stats != null && stats.isKenbunshokuActive()) {
                var chain = xyz.pixelatedw.mineminenomi.client.render.ModRenderTypeBuffers.getInstance().getHakiAuraPostChain();
                if (chain != null) {
                    chain.process(event.getPartialTick().getGameTimeDeltaPartialTick(true));
                    mc.getMainRenderTarget().bindWrite(false);
                }
            }

            if (stats != null) {
                long timeSinceLogiaDodge = mc.player.level().getGameTime() - stats.getCombat().lastLogiaDodgeTime();
                if (timeSinceLogiaDodge >= 0 && timeSinceLogiaDodge < 10) { // 0.5 second duration
                    var chain = xyz.pixelatedw.mineminenomi.client.render.ModRenderTypeBuffers.getInstance().getMorphTransitionPostChain();
                    if (chain != null) {
                        chain.process(event.getPartialTick().getGameTimeDeltaPartialTick(true));
                        mc.getMainRenderTarget().bindWrite(false);
                    }
                }
            }

            if (mc.player.hasData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.MORPH_DATA)) {
                xyz.pixelatedw.mineminenomi.data.entity.MorphData morphData = mc.player.getData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.MORPH_DATA);
                long timeSinceMorph = mc.player.level().getGameTime() - morphData.lastMorphTime();

                if (timeSinceMorph >= 0 && timeSinceMorph < 20) { // 1 second duration
                    var chain = xyz.pixelatedw.mineminenomi.client.render.ModRenderTypeBuffers.getInstance().getMorphTransitionPostChain();
                    if (chain != null) {
                        float progress = timeSinceMorph / 20.0f;
                        float intensity = (float) Math.sin(progress * Math.PI); // Peak in middle

                        // Just an example uniform if using wobble, but for blur_transition we can try to set a uniform if defined in code, otherwise we process the blur.
                        // Setting uniforms dynamically for the blur pass if they exist
                        // We will rely on PostChain#process for now which runs the passes
                        chain.process(event.getPartialTick().getGameTimeDeltaPartialTick(true));
                        mc.getMainRenderTarget().bindWrite(false);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onAddLayers(net.neoforged.neoforge.client.event.EntityRenderersEvent.AddLayers event) {
        net.minecraft.client.renderer.entity.EntityRendererProvider.Context ctx = event.getContext();
        xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderingHandler.registerRenderer(
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "zou_guard"), 
            new xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderer<>(ctx, new xyz.pixelatedw.mineminenomi.client.models.morphs.ZouGuardModel<>(ctx.bakeLayer(xyz.pixelatedw.mineminenomi.client.models.morphs.ZouGuardModel.LAYER_LOCATION)), 1.5f, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/zou_guard.png")));
            
        xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderingHandler.registerRenderer(
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "zou_heavy"), 
            new xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderer<>(ctx, new xyz.pixelatedw.mineminenomi.client.models.morphs.ZouHeavyModel<>(ctx.bakeLayer(xyz.pixelatedw.mineminenomi.client.models.morphs.ZouHeavyModel.LAYER_LOCATION)), 0.8f, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/zou_heavy.png")));

        for (net.minecraft.client.resources.PlayerSkin.Model skin : event.getSkins()) {
            net.minecraft.client.renderer.entity.LivingEntityRenderer<net.minecraft.client.player.AbstractClientPlayer, net.minecraft.client.model.PlayerModel<net.minecraft.client.player.AbstractClientPlayer>> renderer = event.getSkin(skin);
            if (renderer != null) {
                renderer.addLayer(new xyz.pixelatedw.mineminenomi.client.renderers.layers.BodyCoatingLayer<>(renderer));
                renderer.addLayer(new xyz.pixelatedw.mineminenomi.client.renderers.layers.FrostbiteLayer<>(renderer));
                renderer.addLayer(new xyz.pixelatedw.mineminenomi.client.renderers.layers.AuraLayer<>(renderer));
            }
        }
    }
}
