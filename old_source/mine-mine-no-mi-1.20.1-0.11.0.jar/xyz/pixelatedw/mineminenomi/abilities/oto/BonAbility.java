package xyz.pixelatedw.mineminenomi.abilities.oto;

import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class BonAbility extends Ability {
   private static final float COOLDOWN = 60.0F;
   private static final float VOLUME = 3.5F;
   private static final int DISTANCE = 24;
   private static final float WIDTH = 3.0F;
   private static final float DAMAGE = 25.0F;
   private static final int ANIMATION_TICKS = 10;
   public static final RegistryObject<AbilityCore<BonAbility>> INSTANCE = ModRegistry.registerAbility("bon", "Bon", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user plucks a string created from their arm, creating a sound wave that internally damages all who hear it", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BonAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(60.0F), DealDamageComponent.getTooltip(25.0F), RangeComponent.getTooltip(24.0F, RangeComponent.RangeType.CONE)).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceType(SourceType.INTERNAL, SourceType.SLASH, SourceType.INDIRECT).setSourceElement(SourceElement.SHOCKWAVE).build("mineminenomi");
   });
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);

   public BonAbility(AbilityCore<BonAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.animationComponent, this.rangeComponent, this.dealDamageComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.BON.get(), SoundSource.PLAYERS, 3.5F, 0.2F + entity.m_217043_().m_188501_());
      List<LivingEntity> targets = this.rangeComponent.getTargetsInLine(entity, 24.0F, 3.0F);
      this.animationComponent.start(entity, ModAnimations.BON, 10);

      for(LivingEntity target : targets) {
         if (!target.m_21023_((MobEffect)ModEffects.SILENT.get())) {
            DamageSource source = this.dealDamageComponent.getDamageSource(entity);
            IDamageSourceHandler handler = IDamageSourceHandler.getHandler(source);
            handler.bypassLogia();
            handler.addAbilityPiercing(1.0F, this.getCore());
            if (this.dealDamageComponent.hurtTarget(entity, target, 25.0F, source)) {
               Vec3 dist = target.m_20182_().m_82546_(entity.m_20182_()).m_82520_((double)0.0F, (double)-1.0F, (double)0.0F).m_82541_();
               double power = (double)4.5F;
               double xSpeed = -dist.f_82479_ * power;
               double zSpeed = -dist.f_82481_ * power;
               AbilityHelper.setDeltaMovement(target, -xSpeed, 0.1, -zSpeed);

               for(int i = 0; i < 5; ++i) {
                  double offsetX = target.m_217043_().m_188500_() / (double)2.0F;
                  double offsetZ = target.m_217043_().m_188500_() / (double)2.0F;
                  WyHelper.spawnParticles(ParticleTypes.f_123766_, (ServerLevel)entity.m_9236_(), target.m_20185_() + offsetX, target.m_20186_() + (double)target.m_20192_() + offsetX, target.m_20189_() + offsetZ);
               }

               WyHelper.spawnParticles(ParticleTypes.f_123813_, (ServerLevel)entity.m_9236_(), target.m_20185_(), target.m_20186_() + (double)target.m_20192_(), target.m_20189_());
               break;
            }
         }
      }

      this.cooldownComponent.startCooldown(entity, 60.0F);
   }
}
