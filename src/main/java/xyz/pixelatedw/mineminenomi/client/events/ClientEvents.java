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

        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.PHOENIX_GOEN.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.5F, true));

        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.SPHERE.get(),
            xyz.pixelatedw.mineminenomi.client.renderers.entities.SphereRenderer::new);

        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.SNIPER_PELLET.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.GEKISHIN.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.PAD_HO.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.OVERHEAT.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.MERO_MERO_MELLOW.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.NOSE_FANCY_CANNON.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.BRICK_BAT.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.BARA_BARA_HO.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.NEGATIVE_HOLLOW.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.GOMU_GOMU_NO_BAZOOKA.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.NORO_NORO_BEAM.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.PISTOL_KISS.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.WHITE_OUT.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.THROWN_SPEAR.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.YAKKODORI.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.SANBYAKUROKUJU_POUND_HO.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.SANJUROKU_POUND_HO_PROJECTILE.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.TATSU_MAKI_PROJECTILE.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.NANAJUNI_POUND_HO_PROJECTILE.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.NANAHYAKUNIJU_POUND_HO_PROJECTILE.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.HYAKUHACHI_POUND_HO_PROJECTILE.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.SENHACHIJU_POUND_HO_PROJECTILE.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.RELAX_HOUR_PROJECTILE.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
        event.registerEntityRenderer(xyz.pixelatedw.mineminenomi.init.ModEntities.VIVRE_CARD.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx, 1.0F, true));
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(xyz.pixelatedw.mineminenomi.client.models.morphs.ZouHeavyModel.LAYER_LOCATION, xyz.pixelatedw.mineminenomi.client.models.morphs.ZouHeavyModel::createBodyLayer);
        event.registerLayerDefinition(xyz.pixelatedw.mineminenomi.client.models.morphs.ZouGuardModel.LAYER_LOCATION, xyz.pixelatedw.mineminenomi.client.models.morphs.ZouGuardModel::createBodyLayer);
        event.registerLayerDefinition(xyz.pixelatedw.mineminenomi.client.models.morphs.AllosaurusWalkModel.LAYER_LOCATION, xyz.pixelatedw.mineminenomi.client.models.morphs.AllosaurusWalkModel::createBodyLayer);
        event.registerLayerDefinition(xyz.pixelatedw.mineminenomi.client.models.morphs.KameGuardModel.LAYER_LOCATION, xyz.pixelatedw.mineminenomi.client.models.morphs.KameGuardModel::createBodyLayer);
        event.registerLayerDefinition(xyz.pixelatedw.mineminenomi.client.models.morphs.PhoenixFlyModel.LAYER_LOCATION, xyz.pixelatedw.mineminenomi.client.models.morphs.PhoenixFlyModel::createBodyLayer);
        event.registerLayerDefinition(xyz.pixelatedw.mineminenomi.client.models.morphs.PteranodonFlyModel.LAYER_LOCATION, xyz.pixelatedw.mineminenomi.client.models.morphs.PteranodonFlyModel::createBodyLayer);
        event.registerLayerDefinition(xyz.pixelatedw.mineminenomi.client.models.morphs.LeopardWalkModel.LAYER_LOCATION, xyz.pixelatedw.mineminenomi.client.models.morphs.LeopardWalkModel::createBodyLayer);
        event.registerLayerDefinition(xyz.pixelatedw.mineminenomi.client.models.morphs.MammothGuardModel.LAYER_LOCATION, xyz.pixelatedw.mineminenomi.client.models.morphs.MammothGuardModel::createBodyLayer);
        event.registerLayerDefinition(xyz.pixelatedw.mineminenomi.client.models.morphs.SaiHeavyModel.LAYER_LOCATION, xyz.pixelatedw.mineminenomi.client.models.morphs.SaiHeavyModel::createBodyLayer);
        event.registerLayerDefinition(xyz.pixelatedw.mineminenomi.client.models.morphs.BisonWalkModel.LAYER_LOCATION, xyz.pixelatedw.mineminenomi.client.models.morphs.BisonWalkModel::createBodyLayer);
        event.registerLayerDefinition(xyz.pixelatedw.mineminenomi.client.models.morphs.BrachiosaurusGuardModel.LAYER_LOCATION, xyz.pixelatedw.mineminenomi.client.models.morphs.BrachiosaurusGuardModel::createBodyLayer);
        event.registerLayerDefinition(xyz.pixelatedw.mineminenomi.client.models.morphs.SaiWalkModel.LAYER_LOCATION, xyz.pixelatedw.mineminenomi.client.models.morphs.SaiWalkModel::createBodyLayer);
        event.registerLayerDefinition(xyz.pixelatedw.mineminenomi.client.models.morphs.AxolotlHeavyModel.LAYER_LOCATION, xyz.pixelatedw.mineminenomi.client.models.morphs.AxolotlHeavyModel::createBodyLayer);
        event.registerLayerDefinition(xyz.pixelatedw.mineminenomi.client.models.morphs.BrachiosaurusHeavyModel.LAYER_LOCATION, xyz.pixelatedw.mineminenomi.client.models.morphs.BrachiosaurusHeavyModel::createBodyLayer);
        event.registerLayerDefinition(xyz.pixelatedw.mineminenomi.client.models.morphs.MoguHeavyModel.LAYER_LOCATION, xyz.pixelatedw.mineminenomi.client.models.morphs.MoguHeavyModel::createBodyLayer);
        event.registerLayerDefinition(xyz.pixelatedw.mineminenomi.client.models.morphs.AxolotlWalkModel.LAYER_LOCATION, xyz.pixelatedw.mineminenomi.client.models.morphs.AxolotlWalkModel::createBodyLayer);
        event.registerLayerDefinition(xyz.pixelatedw.mineminenomi.client.models.morphs.Phoenix2Model.LAYER_LOCATION, xyz.pixelatedw.mineminenomi.client.models.morphs.Phoenix2Model::createBodyLayer);
        event.registerLayerDefinition(xyz.pixelatedw.mineminenomi.client.models.morphs.LeopardHeavyModel.LAYER_LOCATION, xyz.pixelatedw.mineminenomi.client.models.morphs.LeopardHeavyModel::createBodyLayer);
        event.registerLayerDefinition(xyz.pixelatedw.mineminenomi.client.models.morphs.GiraffeWalkModel.LAYER_LOCATION, xyz.pixelatedw.mineminenomi.client.models.morphs.GiraffeWalkModel::createBodyLayer);
        event.registerLayerDefinition(xyz.pixelatedw.mineminenomi.client.models.morphs.GiraffeHeavyModel.LAYER_LOCATION, xyz.pixelatedw.mineminenomi.client.models.morphs.GiraffeHeavyModel::createBodyLayer);
        event.registerLayerDefinition(xyz.pixelatedw.mineminenomi.client.models.morphs.BisonHeavyModel.LAYER_LOCATION, xyz.pixelatedw.mineminenomi.client.models.morphs.BisonHeavyModel::createBodyLayer);
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

        xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderingHandler.registerRenderer(
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "allosaurus_walk"),
            new xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderer<>(ctx, new xyz.pixelatedw.mineminenomi.client.models.morphs.AllosaurusWalkModel<>(ctx.bakeLayer(xyz.pixelatedw.mineminenomi.client.models.morphs.AllosaurusWalkModel.LAYER_LOCATION)), 1.0f, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/allosaurus_walk.png")));
        xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderingHandler.registerRenderer(
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "kame_guard"),
            new xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderer<>(ctx, new xyz.pixelatedw.mineminenomi.client.models.morphs.KameGuardModel<>(ctx.bakeLayer(xyz.pixelatedw.mineminenomi.client.models.morphs.KameGuardModel.LAYER_LOCATION)), 1.0f, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/kame_guard.png")));
        xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderingHandler.registerRenderer(
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "phoenix_fly"),
            new xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderer<>(ctx, new xyz.pixelatedw.mineminenomi.client.models.morphs.PhoenixFlyModel<>(ctx.bakeLayer(xyz.pixelatedw.mineminenomi.client.models.morphs.PhoenixFlyModel.LAYER_LOCATION)), 1.0f, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/phoenix_fly.png")));
        xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderingHandler.registerRenderer(
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "pteranodon_fly"),
            new xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderer<>(ctx, new xyz.pixelatedw.mineminenomi.client.models.morphs.PteranodonFlyModel<>(ctx.bakeLayer(xyz.pixelatedw.mineminenomi.client.models.morphs.PteranodonFlyModel.LAYER_LOCATION)), 1.0f, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/pteranodon_fly.png")));
        xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderingHandler.registerRenderer(
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "leopard_walk"),
            new xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderer<>(ctx, new xyz.pixelatedw.mineminenomi.client.models.morphs.LeopardWalkModel<>(ctx.bakeLayer(xyz.pixelatedw.mineminenomi.client.models.morphs.LeopardWalkModel.LAYER_LOCATION)), 1.0f, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/leopard_walk.png")));
        xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderingHandler.registerRenderer(
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "mammoth_guard"),
            new xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderer<>(ctx, new xyz.pixelatedw.mineminenomi.client.models.morphs.MammothGuardModel<>(ctx.bakeLayer(xyz.pixelatedw.mineminenomi.client.models.morphs.MammothGuardModel.LAYER_LOCATION)), 1.0f, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/mammoth_guard.png")));
        xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderingHandler.registerRenderer(
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "sai_heavy"),
            new xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderer<>(ctx, new xyz.pixelatedw.mineminenomi.client.models.morphs.SaiHeavyModel<>(ctx.bakeLayer(xyz.pixelatedw.mineminenomi.client.models.morphs.SaiHeavyModel.LAYER_LOCATION)), 1.0f, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/sai_heavy.png")));
        xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderingHandler.registerRenderer(
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "bison_walk"),
            new xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderer<>(ctx, new xyz.pixelatedw.mineminenomi.client.models.morphs.BisonWalkModel<>(ctx.bakeLayer(xyz.pixelatedw.mineminenomi.client.models.morphs.BisonWalkModel.LAYER_LOCATION)), 1.0f, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/bison_walk.png")));
        xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderingHandler.registerRenderer(
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "brachiosaurus_guard"),
            new xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderer<>(ctx, new xyz.pixelatedw.mineminenomi.client.models.morphs.BrachiosaurusGuardModel<>(ctx.bakeLayer(xyz.pixelatedw.mineminenomi.client.models.morphs.BrachiosaurusGuardModel.LAYER_LOCATION)), 1.0f, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/brachiosaurus_guard.png")));
        xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderingHandler.registerRenderer(
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "sai_walk"),
            new xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderer<>(ctx, new xyz.pixelatedw.mineminenomi.client.models.morphs.SaiWalkModel<>(ctx.bakeLayer(xyz.pixelatedw.mineminenomi.client.models.morphs.SaiWalkModel.LAYER_LOCATION)), 1.0f, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/sai_walk.png")));
        xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderingHandler.registerRenderer(
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "axolotl_heavy"),
            new xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderer<>(ctx, new xyz.pixelatedw.mineminenomi.client.models.morphs.AxolotlHeavyModel<>(ctx.bakeLayer(xyz.pixelatedw.mineminenomi.client.models.morphs.AxolotlHeavyModel.LAYER_LOCATION)), 1.0f, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/axolotl_heavy.png")));
        xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderingHandler.registerRenderer(
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "brachiosaurus_heavy"),
            new xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderer<>(ctx, new xyz.pixelatedw.mineminenomi.client.models.morphs.BrachiosaurusHeavyModel<>(ctx.bakeLayer(xyz.pixelatedw.mineminenomi.client.models.morphs.BrachiosaurusHeavyModel.LAYER_LOCATION)), 1.0f, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/brachiosaurus_heavy.png")));
        xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderingHandler.registerRenderer(
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "mogu_heavy"),
            new xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderer<>(ctx, new xyz.pixelatedw.mineminenomi.client.models.morphs.MoguHeavyModel<>(ctx.bakeLayer(xyz.pixelatedw.mineminenomi.client.models.morphs.MoguHeavyModel.LAYER_LOCATION)), 1.0f, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/mogu_heavy.png")));
        xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderingHandler.registerRenderer(
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "axolotl_walk"),
            new xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderer<>(ctx, new xyz.pixelatedw.mineminenomi.client.models.morphs.AxolotlWalkModel<>(ctx.bakeLayer(xyz.pixelatedw.mineminenomi.client.models.morphs.AxolotlWalkModel.LAYER_LOCATION)), 1.0f, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/axolotl_walk.png")));
        xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderingHandler.registerRenderer(
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "phoenix_2"),
            new xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderer<>(ctx, new xyz.pixelatedw.mineminenomi.client.models.morphs.Phoenix2Model<>(ctx.bakeLayer(xyz.pixelatedw.mineminenomi.client.models.morphs.Phoenix2Model.LAYER_LOCATION)), 1.0f, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/phoenix2.png")));
        xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderingHandler.registerRenderer(
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "leopard_heavy"),
            new xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderer<>(ctx, new xyz.pixelatedw.mineminenomi.client.models.morphs.LeopardHeavyModel<>(ctx.bakeLayer(xyz.pixelatedw.mineminenomi.client.models.morphs.LeopardHeavyModel.LAYER_LOCATION)), 1.0f, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/leopard_heavy.png")));
        xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderingHandler.registerRenderer(
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "giraffe_walk"),
            new xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderer<>(ctx, new xyz.pixelatedw.mineminenomi.client.models.morphs.GiraffeWalkModel<>(ctx.bakeLayer(xyz.pixelatedw.mineminenomi.client.models.morphs.GiraffeWalkModel.LAYER_LOCATION)), 1.0f, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/giraffe_walk.png")));
        xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderingHandler.registerRenderer(
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "giraffe_heavy"),
            new xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderer<>(ctx, new xyz.pixelatedw.mineminenomi.client.models.morphs.GiraffeHeavyModel<>(ctx.bakeLayer(xyz.pixelatedw.mineminenomi.client.models.morphs.GiraffeHeavyModel.LAYER_LOCATION)), 1.0f, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/giraffe_heavy.png")));
        xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderingHandler.registerRenderer(
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "bison_heavy"),
            new xyz.pixelatedw.mineminenomi.client.render.morphs.MorphRenderer<>(ctx, new xyz.pixelatedw.mineminenomi.client.models.morphs.BisonHeavyModel<>(ctx.bakeLayer(xyz.pixelatedw.mineminenomi.client.models.morphs.BisonHeavyModel.LAYER_LOCATION)), 1.0f, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/bison_heavy.png")));

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
