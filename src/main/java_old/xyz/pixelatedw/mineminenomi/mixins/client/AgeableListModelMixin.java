package xyz.pixelatedw.mineminenomi.mixins.client;

import com.google.common.collect.Iterables;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.pixelatedw.mineminenomi.api.IAgeableListModelExtension;

@Mixin({AgeableListModel.class})
public abstract class AgeableListModelMixin implements IAgeableListModelExtension {
   @Shadow
   public abstract Iterable<ModelPart> m_5607_();

   @Shadow
   public abstract Iterable<ModelPart> m_5608_();

   public Iterable<ModelPart> getParts() {
      Collection<ModelPart> headParts = new HashSet();
      Collection<ModelPart> bodyParts = new HashSet();
      this.m_5607_().forEach((part) -> {
         Stream var10000 = part.m_171331_();
         Objects.requireNonNull(headParts);
         var10000.forEach(headParts::add);
      });
      this.m_5608_().forEach((part) -> {
         Stream var10000 = part.m_171331_();
         Objects.requireNonNull(bodyParts);
         var10000.forEach(bodyParts::add);
      });
      return Iterables.concat(headParts, bodyParts);
   }
}
