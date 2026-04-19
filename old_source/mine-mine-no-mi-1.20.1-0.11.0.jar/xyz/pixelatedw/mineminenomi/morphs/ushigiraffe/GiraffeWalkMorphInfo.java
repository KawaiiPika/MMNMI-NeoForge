package xyz.pixelatedw.mineminenomi.morphs.ushigiraffe;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.ushigiraffe.GiraffeWalkPointAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class GiraffeWalkMorphInfo extends MorphInfo {
   private static final EntityDimensions STANDING_SIZE = EntityDimensions.m_20395_(1.3F, 5.0F);
   private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.m_20395_(1.3F, 4.0F);
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/giraffe_walk.png");

   public @Nullable ResourceLocation getTexture(LivingEntity entity) {
      return TEXTURE;
   }

   @OnlyIn(Dist.CLIENT)
   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, PoseStack matrixStack, float partialTickTime) {
      matrixStack.m_85841_(2.0F, 2.0F, 2.0F);
   }

   public Component getDisplayName() {
      return ((AbilityCore)GiraffeWalkPointAbility.INSTANCE.get()).getLocalizedName();
   }

   public float getEyeHeight(LivingEntity entity) {
      return entity.m_6047_() ? 3.5F : 4.9F;
   }

   public float getShadowSize() {
      return 1.2F;
   }

   public boolean canMount() {
      return false;
   }

   public void positionRider(LivingEntity entity, Entity passenger) {
      double height = entity.m_20186_() + (double)(entity.m_6972_(entity.m_20089_()).f_20378_ * 0.4F);
      passenger.m_6034_(entity.m_20185_(), height, entity.m_20189_());
   }

   @OnlyIn(Dist.CLIENT)
   public boolean hasCulling() {
      return true;
   }

   public Map<Pose, EntityDimensions> getSizes() {
      return ImmutableMap.builder().put(Pose.STANDING, STANDING_SIZE).put(Pose.CROUCHING, CROUCHING_SIZE).build();
   }
}
