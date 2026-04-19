package xyz.pixelatedw.mineminenomi.init;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.mixins.client.ILevelRendererMixin;
import xyz.pixelatedw.mineminenomi.renderers.buffers.HakiAuraBuffer;

public class ModRenderTypeBuffers {
   private static final ResourceLocation HAKI_AURA_SHADER = ResourceLocation.fromNamespaceAndPath("mineminenomi", "shaders/post/haki_aura.json");
   private static ModRenderTypeBuffers INSTANCE;
   private Minecraft minecraft;
   private PostChain hakiAuraShader;
   private HakiAuraBuffer hakiAuraBufferSource;

   public void initHakiAuraShader() {
      if (this.hakiAuraShader != null) {
         this.hakiAuraShader.close();
      }

      try {
         this.hakiAuraShader = new PostChain(this.minecraft.m_91097_(), this.minecraft.m_91098_(), this.minecraft.m_91385_(), HAKI_AURA_SHADER);
         this.hakiAuraShader.m_110025_(this.minecraft.m_91268_().m_85441_(), this.minecraft.m_91268_().m_85442_());
         ((ILevelRendererMixin)this.minecraft.f_91060_).setEntityTarget(this.hakiAuraShader.m_110036_("final"));
      } catch (IOException ioexception) {
         ioexception.printStackTrace();
         WyDebug.error("Failed to load haki aura shader: " + String.valueOf(HAKI_AURA_SHADER));
      }

   }

   public static ModRenderTypeBuffers getInstance() {
      if (INSTANCE == null) {
         ModRenderTypeBuffers buffers = new ModRenderTypeBuffers();
         buffers.minecraft = Minecraft.m_91087_();
         buffers.hakiAuraBufferSource = new HakiAuraBuffer(Minecraft.m_91087_().m_91269_().m_110104_());
         INSTANCE = buffers;
      }

      return INSTANCE;
   }

   public HakiAuraBuffer getHakiAuraBuffer() {
      return this.hakiAuraBufferSource;
   }

   public PostChain getHakiAuraPostChain() {
      return this.hakiAuraShader;
   }
}
