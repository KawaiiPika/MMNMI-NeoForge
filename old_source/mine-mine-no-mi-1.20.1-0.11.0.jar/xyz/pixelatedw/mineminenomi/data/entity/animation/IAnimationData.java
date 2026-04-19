package xyz.pixelatedw.mineminenomi.data.entity.animation;

import java.util.LinkedList;
import net.minecraft.client.model.EntityModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public interface IAnimationData extends INBTSerializable<CompoundTag> {
   void startAnimation(AnimationId<?> var1, int var2, boolean var3);

   void stopAnimation(AnimationId<?> var1);

   boolean isAnimationPlaying(AnimationId<?> var1);

   <E extends LivingEntity, M extends EntityModel<E>> @Nullable Animation<E, M> getAnimation();

   void tickAnimations();

   LinkedList<Animation<?, ?>> getAnimations();
}
