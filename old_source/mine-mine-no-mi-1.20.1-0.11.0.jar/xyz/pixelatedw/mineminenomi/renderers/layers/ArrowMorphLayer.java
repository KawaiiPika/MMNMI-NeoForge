package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ArrowMorphLayer<T extends LivingEntity, M extends EntityModel<T>> extends StuckInMorphBodyLayer<T, M> {
   private final EntityRenderDispatcher dispatcher;

   public ArrowMorphLayer(EntityRendererProvider.Context p_174465_, LivingEntityRenderer<T, M> p_174466_) {
      super(p_174466_);
      this.dispatcher = p_174465_.m_174022_();
   }

   protected int numStuck(T p_116567_) {
      return p_116567_.m_21234_();
   }

   protected void renderStuckItem(PoseStack p_116569_, MultiBufferSource p_116570_, int p_116571_, Entity p_116572_, float p_116573_, float p_116574_, float p_116575_, float p_116576_) {
      float f = Mth.m_14116_(p_116573_ * p_116573_ + p_116575_ * p_116575_);
      Arrow arrow = new Arrow(p_116572_.m_9236_(), p_116572_.m_20185_(), p_116572_.m_20186_(), p_116572_.m_20189_());
      arrow.m_146922_((float)(Math.atan2((double)p_116573_, (double)p_116575_) * (double)(180F / (float)Math.PI)));
      arrow.m_146926_((float)(Math.atan2((double)p_116574_, (double)f) * (double)(180F / (float)Math.PI)));
      arrow.f_19859_ = arrow.m_146908_();
      arrow.f_19860_ = arrow.m_146909_();
      this.dispatcher.m_114384_(arrow, (double)0.0F, (double)0.0F, (double)0.0F, 0.0F, p_116576_, p_116569_, p_116570_, p_116571_);
   }
}
