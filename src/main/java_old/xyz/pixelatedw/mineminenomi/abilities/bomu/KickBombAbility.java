package xyz.pixelatedw.mineminenomi.abilities.bomu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KickBombAbility extends Ability {
   private static final float COOLDOWN = 400.0F;
   public static final RegistryObject<AbilityCore<KickBombAbility>> INSTANCE = ModRegistry.registerAbility("kick_bomb", "Kick Bomb", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user kicks the ground, detonating their leg on impact", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, KickBombAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(400.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceElement(SourceElement.EXPLOSION).build("mineminenomi");
   });
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);

   public KickBombAbility(AbilityCore<KickBombAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.explosionComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         AbilityExplosion explosion = this.explosionComponent.createExplosion(entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), 7.0F);
         explosion.setStaticDamage(70.0F);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION8);
         explosion.m_46061_();
      }

      this.cooldownComponent.startCooldown(entity, 400.0F);
   }
}
