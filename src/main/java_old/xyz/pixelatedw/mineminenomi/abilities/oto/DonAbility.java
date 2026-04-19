package xyz.pixelatedw.mineminenomi.abilities.oto;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DonAbility extends Ability {
   private static final float COOLDOWN = 60.0F;
   private static final float VOLUME = 3.0F;
   private static final int DISTANCE = 24;
   private static final float WIDTH = 3.0F;
   private static final float DAMAGE = 25.0F;
   private static final int ANIMATION_TICKS = 20;
   public static final RegistryObject<AbilityCore<DonAbility>> INSTANCE = ModRegistry.registerAbility("don", "Don", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user plays the drum, creating a explosion inside all who hear it", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, DonAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(60.0F), DealDamageComponent.getTooltip(25.0F), RangeComponent.getTooltip(24.0F, RangeComponent.RangeType.CONE)).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceType(SourceType.INTERNAL).setSourceElement(SourceElement.EXPLOSION).build("mineminenomi");
   });
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);

   public DonAbility(AbilityCore<DonAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.animationComponent, this.rangeComponent, this.dealDamageComponent, this.explosionComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.DON.get(), SoundSource.PLAYERS, 3.0F, 0.2F + entity.m_217043_().m_188501_());
      List<LivingEntity> targets = this.rangeComponent.getTargetsInLine(entity, 24.0F, 3.0F);
      this.animationComponent.start(entity, ModAnimations.DON, 20);

      for(LivingEntity target : targets) {
         if (!target.m_21023_((MobEffect)ModEffects.SILENT.get())) {
            DamageSource source = this.dealDamageComponent.getDamageSource(entity);
            if (this.dealDamageComponent.hurtTarget(entity, target, 25.0F, source)) {
               AbilityExplosion explosion = this.explosionComponent.createExplosion(entity, entity, target.m_20185_(), target.m_20186_(), target.m_20189_(), 1.0F);
               explosion.setStaticDamage(25.0F);
               explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION2);
               explosion.m_46061_();
               break;
            }
         }
      }

      this.cooldownComponent.startCooldown(entity, 60.0F);
   }
}
