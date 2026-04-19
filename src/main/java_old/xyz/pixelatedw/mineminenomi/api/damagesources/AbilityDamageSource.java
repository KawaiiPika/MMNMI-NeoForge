package xyz.pixelatedw.mineminenomi.api.damagesources;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;

public class AbilityDamageSource extends BaseDamageSource {
   private AbilityCore<?> core;

   public AbilityDamageSource(Holder<DamageType> type, @Nullable Entity directEntity, @Nullable Entity causeEntity, @Nullable Vec3 pos, AbilityCore<?> core) {
      super(type, directEntity, causeEntity, pos, core.getSourceElement(), core.getSourceTypes(), core.getSourceHakiNature());
      this.core = core;
   }

   public AbilityDamageSource(Holder<DamageType> type, @Nullable Entity directEntity, @Nullable Entity causeEntity, AbilityCore<?> core) {
      this(type, directEntity, causeEntity, (Vec3)null, core);
   }

   public AbilityDamageSource(Holder<DamageType> type, Vec3 pos, AbilityCore<?> core) {
      this(type, (Entity)null, (Entity)null, pos, core);
   }

   public AbilityDamageSource(Holder<DamageType> type, @Nullable Entity directEntity, AbilityCore<?> core) {
      this(type, directEntity, directEntity, core);
   }

   public AbilityDamageSource(Holder<DamageType> type, AbilityCore<?> ability) {
      this(type, (Entity)null, (Entity)null, (Vec3)null, ability);
   }

   public AbilityCore<?> getAbilityCore() {
      return this.core;
   }

   public Component m_6157_(LivingEntity target) {
      String s = "death.attack." + this.m_269415_().f_268677_();
      if (this.m_7639_() != null && this.m_7639_() instanceof LivingEntity) {
         String s1 = s + ".player";
         return Component.m_237110_(s1, new Object[]{target.m_5446_(), this.m_7639_().m_5446_(), this.getAbilityCore().getLocalizedName()});
      } else {
         return Component.m_237110_(s, new Object[]{target.m_5446_(), this.getAbilityCore().getLocalizedName()});
      }
   }
}
