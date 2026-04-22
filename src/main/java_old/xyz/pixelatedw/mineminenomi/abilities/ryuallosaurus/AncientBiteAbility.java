package xyz.pixelatedw.mineminenomi.abilities.ryuallosaurus;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class AncientBiteAbility extends PunchAbility {
   private static final int COOLDOWN = 140;
   public static final RegistryObject<AbilityCore<AncientBiteAbility>> INSTANCE = ModRegistry.registerAbility("ancient_bite", "Ancient Bite", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Bites the enemy dealing moderate damage.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AncientBiteAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.ALLOSAURUS_HEAVY, ModMorphs.ALLOSAURUS_WALK)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(140.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });

   public AncientBiteAbility(AbilityCore<AncientBiteAbility> core) {
      super(core);
      this.addCanUseCheck(RyuAllosaurusHelper::requiresEitherPoint);
      this.addContinueUseCheck(RyuAllosaurusHelper::requiresEitherPoint);
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      double x = WyHelper.randomDouble() * (double)2.0F;
      double y = WyHelper.randomDouble() * 0.3;
      double z = WyHelper.randomDouble() * (double)2.0F;
      entity.m_9236_().m_7106_(ParticleTypes.f_123797_, target.m_20185_(), target.m_20186_() + (double)1.0F, target.m_20189_(), x, y, z);
      target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.BLEEDING.get(), 20, 0));

      for(int i = 0; i < 50; ++i) {
         Vec3 vec3d = new Vec3(((double)entity.m_217043_().m_188501_() - (double)0.5F) * 0.1, xyz.pixelatedw.mineminenomi.api.WyHelper.random() * 0.1 + 0.1, (double)0.0F);
         vec3d = vec3d.m_82496_(-entity.m_146909_() * ((float)Math.PI / 180F));
         vec3d = vec3d.m_82524_(-entity.m_146908_() * ((float)Math.PI / 180F));
         ((ServerLevel)entity.m_9236_()).m_8767_(ParticleTypes.f_123797_, target.m_20185_(), target.m_20186_() + (double)1.5F, target.m_20189_(), 1, vec3d.f_82479_, vec3d.f_82480_, vec3d.f_82481_, 0.8);
      }

      return true;
   }

   public int getUseLimit() {
      return 1;
   }

   public float getPunchDamage() {
      return 20.0F;
   }

   public float getPunchCooldown() {
      return 140.0F;
   }

   public boolean isParallel() {
      return true;
   }
}
