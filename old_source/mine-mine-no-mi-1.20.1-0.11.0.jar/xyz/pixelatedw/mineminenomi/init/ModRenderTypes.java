package xyz.pixelatedw.mineminenomi.init;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.renderers.blocks.FlagBlockEntityRenderer;

public class ModRenderTypes extends RenderType {
   public static ShaderInstance transparentColorShaderInstance;
   public static ShaderInstance auraHakiShaderInstance;
   protected static final RenderStateShard.ShaderStateShard TRANSPARENT_COLOR_SHADER = new RenderStateShard.ShaderStateShard(() -> transparentColorShaderInstance);
   protected static final RenderStateShard.ShaderStateShard AURA_HAKI_SHADER = new RenderStateShard.ShaderStateShard(() -> auraHakiShaderInstance);
   public static final RenderStateShard.OutputStateShard AURA_TARGET = new RenderStateShard.OutputStateShard("mineminenomi:aura_target", () -> Minecraft.m_91087_().f_91060_.m_109827_().m_83947_(false), () -> Minecraft.m_91087_().m_91385_().m_83947_(false));
   public static final RenderType ENERGY;
   public static final RenderType TRANSPARENT_COLOR;
   public static final RenderType LIGHTNING;
   public static final RenderType FLAG;
   public static final RenderType FLAG_ON_FIRE;
   public static final VertexFormat PARTICLE_POSITION_TEX_COLOR_LMAP;

   public ModRenderTypes(String name, VertexFormat format, VertexFormat.Mode drawMode, int bufferSize, boolean useDelegate, boolean needsSorting, Runnable setupTask, Runnable clearTask) {
      super(name, format, drawMode, bufferSize, useDelegate, needsSorting, setupTask, clearTask);
   }

   public static RenderType getPosColorTexLightmap(ResourceLocation texture) {
      RenderStateShard.TextureStateShard textureState = new RenderStateShard.TextureStateShard(texture, false, false);
      RenderType.CompositeState state = CompositeState.m_110628_().m_173292_(f_173103_).m_110685_(f_110139_).m_173290_(textureState).m_110661_(f_110110_).m_110671_(f_110152_).m_110691_(true);
      return m_173215_("mineminenomi:position_color_texture_lightmap", DefaultVertexFormat.f_85820_, Mode.QUADS, 256, true, true, state);
   }

   public static RenderType getAuraRenderType() {
      return m_173215_("mineminenomi:aura", DefaultVertexFormat.f_85818_, Mode.QUADS, 256, true, true, CompositeState.m_110628_().m_173292_(AURA_HAKI_SHADER).m_110661_(f_110110_).m_110663_(f_110111_).m_110675_(AURA_TARGET).m_110691_(true));
   }

   public static RenderType getEyesLayerRenderType(ResourceLocation texture) {
      RenderStateShard.TextureStateShard textureState = new RenderStateShard.TextureStateShard(texture, false, false);
      RenderType.CompositeState state = CompositeState.m_110628_().m_173292_(f_173073_).m_110685_(f_110135_).m_110687_(f_110115_).m_173290_(textureState).m_110691_(false);
      return m_173215_("mineminenomi:eyes_layer", DefaultVertexFormat.f_85812_, Mode.QUADS, 256, true, true, state);
   }

   public static RenderType getAbilityBody(ResourceLocation texture) {
      return getAbilityBody(texture, false, false);
   }

   public static RenderType getAbilityBody(ResourceLocation texture, boolean equalDepthTest, boolean culling) {
      RenderStateShard.TextureStateShard textureState = new RenderStateShard.TextureStateShard(texture, false, false);
      RenderType.CompositeState state = CompositeState.m_110628_().m_173292_(f_173066_).m_110685_(f_110139_).m_173290_(textureState).m_110661_(culling ? f_110158_ : f_110110_).m_110663_(equalDepthTest ? f_110112_ : f_110113_).m_110671_(f_110152_).m_110677_(f_110154_).m_110691_(true);
      return m_173215_("mineminenomi:ability_body", DefaultVertexFormat.f_85812_, Mode.QUADS, 256, true, true, state);
   }

   public static RenderType getAbilityHand(ResourceLocation texture) {
      RenderStateShard.TextureStateShard textureState = new RenderStateShard.TextureStateShard(texture, false, false);
      RenderType.CompositeState state = CompositeState.m_110628_().m_173292_(f_173103_).m_110685_(f_110139_).m_173290_(textureState).m_110661_(f_110110_).m_110671_(f_110152_).m_110691_(true);
      return m_173215_("mineminenomi:ability_hand", DefaultVertexFormat.f_85812_, Mode.QUADS, 256, true, true, state);
   }

   public static RenderType getWantedPoster(ResourceLocation texture) {
      RenderStateShard.TextureStateShard textureState = new RenderStateShard.TextureStateShard(texture, false, true);
      RenderType.CompositeState state = CompositeState.m_110628_().m_173292_(f_173107_).m_110685_(f_110139_).m_110669_(f_110119_).m_173290_(textureState).m_110661_(f_110110_).m_110671_(f_110152_).m_110691_(true);
      return m_173215_("mineminenomi:wanted_poster", DefaultVertexFormat.f_85811_, Mode.QUADS, 256, true, true, state);
   }

   static {
      ENERGY = m_173215_("mineminenomi:energy", DefaultVertexFormat.f_85816_, Mode.QUADS, 256, false, true, CompositeState.m_110628_().m_173292_(f_173091_).m_110685_(f_110139_).m_173290_(f_110147_).m_110661_(f_110110_).m_110691_(true));
      TRANSPARENT_COLOR = m_173215_("mineminenomi:rendertype_entity_translucent_color_no_texture", DefaultVertexFormat.f_85812_, Mode.QUADS, 256, true, true, CompositeState.m_110628_().m_173292_(TRANSPARENT_COLOR_SHADER).m_110685_(f_110139_).m_173290_(f_110147_).m_110661_(f_110110_).m_110671_(f_110152_).m_110677_(f_110154_).m_110691_(true));
      LIGHTNING = m_173215_("mineminenomi:lightning", DefaultVertexFormat.f_85816_, Mode.QUADS, 256, false, true, CompositeState.m_110628_().m_173292_(f_173091_).m_110685_(f_110136_).m_173290_(f_110147_).m_110661_(f_110110_).m_110691_(true));
      FLAG = m_173215_("mineminenomi:flag", DefaultVertexFormat.f_85816_, Mode.QUADS, 256, false, true, CompositeState.m_110628_().m_173292_(f_173091_).m_110685_(f_110139_).m_173290_(f_110147_).m_110661_(f_110158_).m_110671_(f_110152_).m_110691_(true));
      FLAG_ON_FIRE = m_173215_("mineminenomi:flag_on_fire", DefaultVertexFormat.f_85812_, Mode.QUADS, 256, false, true, CompositeState.m_110628_().m_173292_(f_173091_).m_110685_(f_110139_).m_173290_(new RenderStateShard.TextureStateShard(FlagBlockEntityRenderer.ON_FIRE_TEXTURE, false, false)).m_110661_(f_110158_).m_110671_(f_110152_).m_110683_(f_110151_).m_110669_(f_110119_).m_110691_(true));
      PARTICLE_POSITION_TEX_COLOR_LMAP = new VertexFormat(ImmutableMap.builder().put("Position", DefaultVertexFormat.f_85804_).put("UV0", DefaultVertexFormat.f_85806_).put("Color", DefaultVertexFormat.f_85805_).put("UV2", DefaultVertexFormat.f_85808_).build());
   }
}
