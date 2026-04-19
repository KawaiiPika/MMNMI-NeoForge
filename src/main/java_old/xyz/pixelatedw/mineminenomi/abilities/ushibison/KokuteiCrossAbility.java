package xyz.pixelatedw.mineminenomi.abilities.ushibison;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
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
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KokuteiCrossAbility extends PunchAbility {
   private static final int COOLDOWN = 140;
   public static final RegistryObject<AbilityCore<KokuteiCrossAbility>> INSTANCE = ModRegistry.registerAbility("kokutei_cross", "Kokutei Cross", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The transformed user crosses their hooves to hit the opponent.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, KokuteiCrossAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.BISON_HEAVY)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(140.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });

   public KokuteiCrossAbility(AbilityCore<KokuteiCrossAbility> core) {
      super(core);
      this.addCanUseCheck(UshiBisonHelper::requiresHeavyPoint);
      this.addContinueUseCheck(UshiBisonHelper::requiresHeavyPoint);
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      Vec3 speed = entity.m_20154_().m_82542_((double)1.5F, (double)0.0F, (double)1.5F);
      AbilityHelper.setDeltaMovement(target, speed.f_82479_, entity.m_20184_().m_7098_(), speed.f_82481_);
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.KOKUTEI_CROSS.get(), entity, target.m_20185_(), target.m_20186_(), target.m_20189_());
      return true;
   }

   public float getPunchDamage() {
      return 20.0F;
   }

   public float getPunchCooldown() {
      return 140.0F;
   }

   public int getUseLimit() {
      return 1;
   }

   public boolean isParallel() {
      return true;
   }
}
