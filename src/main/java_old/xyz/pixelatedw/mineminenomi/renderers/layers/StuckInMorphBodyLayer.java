package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.google.common.collect.Iterables;
import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nullable;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.IAgeableListModelExtension;

@OnlyIn(Dist.CLIENT)
public abstract class StuckInMorphBodyLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   public StuckInMorphBodyLayer(LivingEntityRenderer<T, M> p_117564_) {
      super(p_117564_);
   }

   protected abstract int numStuck(T var1);

   protected abstract void renderStuckItem(PoseStack var1, MultiBufferSource var2, int var3, Entity var4, float var5, float var6, float var7, float var8);

   public void render(PoseStack p_117586_, MultiBufferSource p_117587_, int p_117588_, T p_117589_, float p_117590_, float p_117591_, float p_117592_, float p_117593_, float p_117594_, float p_117595_) {
      int i = this.numStuck(p_117589_);
      RandomSource randomsource = RandomSource.m_216335_((long)p_117589_.m_19879_());
      if (i > 0) {
         for(int j = 0; j < i; ++j) {
            p_117586_.m_85836_();
            ModelPart modelpart = this.getRandomPart(randomsource);
            if (modelpart != null && modelpart.f_104207_) {
               ModelPart.Cube cube = modelpart.m_233558_(randomsource);
               modelpart.m_104299_(p_117586_);
               float f = randomsource.m_188501_();
               float f1 = randomsource.m_188501_();
               float f2 = randomsource.m_188501_();
               float f3 = Mth.m_14179_(f, cube.f_104335_, cube.f_104338_) / 16.0F;
               float f4 = Mth.m_14179_(f1, cube.f_104336_, cube.f_104339_) / 16.0F;
               float f5 = Mth.m_14179_(f2, cube.f_104337_, cube.f_104340_) / 16.0F;
               p_117586_.m_252880_(f3, f4, f5);
               f = -1.0F * (f * 2.0F - 1.0F);
               f1 = -1.0F * (f1 * 2.0F - 1.0F);
               f2 = -1.0F * (f2 * 2.0F - 1.0F);
               this.renderStuckItem(p_117586_, p_117587_, p_117588_, p_117589_, f, f1, f2, p_117592_);
               p_117586_.m_85849_();
            }
         }
      }

   }

   @Nullable
   private ModelPart getRandomPart(RandomSource rand) {
      EntityModel var3 = this.m_117386_();
      if (var3 instanceof PlayerModel model) {
         return model.m_233438_(rand);
      } else {
         EntityModel iter = this.m_117386_();
         if (iter instanceof AgeableListModel model) {
            Iterable<ModelPart> iter = ((IAgeableListModelExtension)model).getParts();
            int size = Iterables.size(iter);
            return (ModelPart)Iterables.get(iter, rand.m_188503_(size));
         } else {
            iter = this.m_117386_();
            if (iter instanceof HierarchicalModel model) {
               return (ModelPart)model.m_142109_().m_171331_().findAny().orElse((Object)null);
            } else {
               return null;
            }
         }
      }
   }
}
