package xyz.pixelatedw.mineminenomi.api.damagesources;

import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class BaseDamageSource extends DamageSource {
   private SourceElement element;
   private Collection<SourceType> sourceTypes;
   private SourceHakiNature hakiNature;

   public BaseDamageSource(Holder<DamageType> type, @Nullable Entity directEntity, @Nullable Entity causeEntity, @Nullable Vec3 pos, @Nullable SourceElement element, @Nullable Collection<SourceType> types, @Nullable SourceHakiNature hakiNature) {
      super(type, directEntity, causeEntity, pos);
      this.element = element == null ? SourceElement.NONE : element;
      this.sourceTypes = (Collection<SourceType>)(types == null ? new ArrayList() : types);
      this.hakiNature = hakiNature == null ? SourceHakiNature.UNKNOWN : hakiNature;
   }

   public BaseDamageSource(Holder<DamageType> type, @Nullable Entity directEntity, @Nullable Entity causeEntity, @Nullable SourceElement element, @Nullable Collection<SourceType> types, @Nullable SourceHakiNature hakiNature) {
      this(type, directEntity, causeEntity, (Vec3)null, element, types, hakiNature);
   }

   public BaseDamageSource(Holder<DamageType> type, Vec3 pos, @Nullable SourceElement element, @Nullable Collection<SourceType> types, @Nullable SourceHakiNature hakiNature) {
      this(type, (Entity)null, (Entity)null, pos, element, types, hakiNature);
   }

   public BaseDamageSource(Holder<DamageType> type, @Nullable Entity directEntity, @Nullable SourceElement element, @Nullable Collection<SourceType> types, @Nullable SourceHakiNature hakiNature) {
      this(type, directEntity, directEntity, element, types, hakiNature);
   }

   public BaseDamageSource(Holder<DamageType> type, @Nullable Entity directEntity) {
      this(type, directEntity, directEntity, (SourceElement)null, (Collection)null, (SourceHakiNature)null);
   }

   public BaseDamageSource(Holder<DamageType> type, @Nullable SourceElement element, @Nullable Collection<SourceType> types, @Nullable SourceHakiNature hakiNature) {
      this(type, (Entity)null, (Entity)null, (Vec3)null, element, types, hakiNature);
   }

   public BaseDamageSource(Holder<DamageType> type) {
      this(type, (Entity)null, (Entity)null, (Vec3)null, (SourceElement)null, (Collection)null, (SourceHakiNature)null);
   }

   public SourceElement getElement() {
      return this.element;
   }

   public Collection<SourceType> getTypes() {
      return this.sourceTypes;
   }

   public SourceHakiNature getHakiNature() {
      return this.hakiNature;
   }
}
