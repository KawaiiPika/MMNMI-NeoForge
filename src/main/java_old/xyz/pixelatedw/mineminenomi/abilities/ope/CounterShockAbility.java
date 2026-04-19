package xyz.pixelatedw.mineminenomi.abilities.ope;

import net.minecraft.network.chat.Component;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class CounterShockAbility extends PunchAbility {
   private static final float COOLDOWN = 200.0F;
   public static final RegistryObject<AbilityCore<CounterShockAbility>> INSTANCE = ModRegistry.registerAbility("counter_shock", "Counter Shock", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Releases an electrical surge like a defibrillator from the users fist which shocks the opponent.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, CounterShockAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setSourceElement(SourceElement.SHOCKWAVE).build("mineminenomi");
   });
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);

   public CounterShockAbility(AbilityCore<CounterShockAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.explosionComponent});
      this.addCanUseCheck(RoomAbility::hasRoomActive);
      this.addContinueUseCheck(RoomAbility::hasRoomActive);
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      AbilityExplosion explosion = this.explosionComponent.createExplosion(entity, target.m_20185_(), target.m_20186_(), target.m_20189_(), 2.0F);
      explosion.setStaticDamage(3.0F);
      explosion.setExplosionSound(true);
      explosion.setDamageOwner(false);
      explosion.setDestroyBlocks(false);
      explosion.setFireAfterExplosion(false);
      explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION4);
      explosion.setDamageEntities(false);
      explosion.m_46061_();
      Vec3 dirVec = entity.m_20182_().m_82546_(target.m_20182_()).m_82541_().m_82542_((double)3.0F, (double)1.0F, (double)3.0F);
      target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DIZZY.get(), 40, 0));
      AbilityHelper.setDeltaMovement(target, target.m_20184_().m_82520_(-dirVec.f_82479_, 0.65, -dirVec.f_82481_));
      target.m_6853_(false);
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.COUNTER_SHOCK.get(), entity, target.m_20185_(), target.m_20186_(), target.m_20189_());
      return true;
   }

   public float getPunchDamage() {
      return 40.0F;
   }

   public float getPunchCooldown() {
      return 200.0F;
   }

   public int getUseLimit() {
      return 1;
   }
}
