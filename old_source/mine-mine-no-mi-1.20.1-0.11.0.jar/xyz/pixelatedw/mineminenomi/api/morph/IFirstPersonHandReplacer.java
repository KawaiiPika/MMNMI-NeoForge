package xyz.pixelatedw.mineminenomi.api.morph;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.entity.HumanoidArm;

public interface IFirstPersonHandReplacer {
   void renderFirstPersonArm(PoseStack var1, VertexConsumer var2, int var3, int var4, float var5, float var6, float var7, float var8, HumanoidArm var9, boolean var10);
}
