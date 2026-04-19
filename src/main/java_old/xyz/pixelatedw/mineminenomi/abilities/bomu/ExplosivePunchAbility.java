package xyz.pixelatedw.mineminenomi.abilities.bomu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ExplosivePunchAbility extends PunchAbility {
   private static final float COOLDOWN = 200.0F;
   private static final float DAMAGE = 20.0F;
   public static final RegistryObject<AbilityCore<ExplosivePunchAbility>> INSTANCE = ModRegistry.registerAbility("explosive_punch", "Explosive Punch", (id, name) -> {
      Component[] DESCRIPTION = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user punches and creates an explosion around their fist", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, ExplosivePunchAbility::new)).addDescriptionLine(DESCRIPTION).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ContinuousComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setSourceElement(SourceElement.EXPLOSION).build("mineminenomi");
   });
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);

   public ExplosivePunchAbility(AbilityCore<ExplosivePunchAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.explosionComponent});
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      AbilityExplosion explosion = this.explosionComponent.createExplosion(entity, target.m_20185_(), target.m_20186_(), target.m_20189_(), 2.0F);
      explosion.setStaticDamage(35.0F);
      explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION4);
      explosion.m_46061_();
      this.continuousComponent.stopContinuity(entity);
      this.cooldownComponent.startCooldown(entity, 200.0F);
      return true;
   }

   public float getPunchDamage() {
      return 20.0F;
   }

   public float getPunchCooldown() {
      return 200.0F;
   }

   public int getUseLimit() {
      return 1;
   }
}
