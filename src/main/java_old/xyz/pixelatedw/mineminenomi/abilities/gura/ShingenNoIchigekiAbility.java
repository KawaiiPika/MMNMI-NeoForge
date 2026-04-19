package xyz.pixelatedw.mineminenomi.abilities.gura;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ShingenNoIchigekiAbility extends PunchAbility {
   private static final Component CROWD_NAME = Component.m_237115_(ModRegistry.registerAbilityName("shingen_no_ichigeki_crowd", "Shingen no Ichigeki: Crowd"));
   private static final ResourceLocation SHINGEN_NO_ICHIGEKI_SINGLE_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/shingen_no_ichigeki.png");
   private static final ResourceLocation SHINGEN_NO_ICHIGEKI_CROWD_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/shingen_no_ichigeki_crowd.png");
   private static final float COOLDOWN = 200.0F;
   private static final float PUNCH_DAMAGE = 40.0F;
   private static final float AOE_DAMAGE = 10.0F;
   private static final int RANGE = 10;
   private static final int EXPLOSION_SIZE = 3;
   public static final RegistryObject<AbilityCore<ShingenNoIchigekiAbility>> INSTANCE = ModRegistry.registerAbility("shingen_no_ichigeki", "Shingen no Ichigeki", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user focuses vibrations around their fist in an spherical bubble, releasing when hitting an enemy.", (Object)null), ImmutablePair.of("The user slams their fist on the ground pushing all nearby enemies.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, ShingenNoIchigekiAbility::new)).addDescriptionLine((e, a) -> Component.m_237113_(name).m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GREEN)), (e, a) -> desc[0], CooldownComponent.getTooltip(200.0F), DealDamageComponent.getTooltip(40.0F)).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, (e, a) -> CROWD_NAME.m_6881_().m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GREEN)), (e, a) -> desc[1], CooldownComponent.getTooltip(200.0F), DealDamageComponent.getTooltip(5.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setSourceElement(SourceElement.SHOCKWAVE).build("mineminenomi");
   });
   private final AltModeComponent<Mode> altModeComponent;
   private final DealDamageComponent dealDamageComponent;
   private final ExplosionComponent explosionComponent;
   private final RangeComponent rangeComponent;

   public ShingenNoIchigekiAbility(AbilityCore<ShingenNoIchigekiAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<Mode>(this, Mode.class, ShingenNoIchigekiAbility.Mode.SINGLE)).addChangeModeEvent(this::onAltModeChange);
      this.dealDamageComponent = new DealDamageComponent(this);
      this.explosionComponent = new ExplosionComponent(this);
      this.rangeComponent = new RangeComponent(this);
      this.addComponents(new AbilityComponent[]{this.altModeComponent, this.dealDamageComponent, this.explosionComponent, this.rangeComponent});
      this.addUseEvent(100, this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (this.altModeComponent.getCurrentMode() != ShingenNoIchigekiAbility.Mode.SINGLE) {
         if (this.altModeComponent.getCurrentMode() == ShingenNoIchigekiAbility.Mode.CROWD) {
            AbilityExplosion explosion = this.explosionComponent.createExplosion(entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), 3.0F);
            explosion.setDestroyBlocks(false);
            explosion.setDamageEntities(false);
            explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION4);
            explosion.m_46061_();
            List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, 10.0F);
            targets.remove(entity);

            for(LivingEntity target : targets) {
               if (this.dealDamageComponent.hurtTarget(entity, target, 10.0F)) {
                  Vec3 dirVec = entity.m_20182_().m_82546_(target.m_20182_()).m_82541_().m_82542_((double)4.0F, (double)1.0F, (double)4.0F);
                  AbilityHelper.setDeltaMovement(target, -dirVec.f_82479_, (double)0.5F + dirVec.f_82480_, -dirVec.f_82481_);
               }
            }

            this.cooldownComponent.startCooldown(entity, 200.0F);
         }

      }
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      IDamageSourceHandler handler = IDamageSourceHandler.getHandler(source);
      handler.bypassLogia();
      handler.addAbilityPiercing(0.75F, this.getCore());
      AbilityExplosion explosion = this.explosionComponent.createExplosion(entity, target.m_20185_(), target.m_20186_(), target.m_20189_(), 3.0F);
      explosion.setStaticDamage(10.0F);
      explosion.setDestroyBlocks(false);
      explosion.setDamageEntities(false);
      explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION4);
      explosion.m_46061_();
      Vec3 dirVec = entity.m_20182_().m_82546_(target.m_20182_()).m_82541_().m_82542_((double)2.0F, (double)1.0F, (double)2.0F);
      AbilityHelper.setDeltaMovement(target, -dirVec.f_82479_, (double)0.5F + dirVec.f_82480_, -dirVec.f_82481_);
      target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DIZZY.get(), 100, 0, false, false));
      return false;
   }

   private boolean onAltModeChange(LivingEntity entity, IAbility ability, Mode mode) {
      if (mode == ShingenNoIchigekiAbility.Mode.SINGLE) {
         this.setDisplayIcon(SHINGEN_NO_ICHIGEKI_SINGLE_ICON);
      } else if (mode == ShingenNoIchigekiAbility.Mode.CROWD) {
         this.setDisplayIcon(SHINGEN_NO_ICHIGEKI_CROWD_ICON);
      }

      return true;
   }

   public float getPunchCooldown() {
      return 200.0F;
   }

   public int getUseLimit() {
      return 1;
   }

   public float getPunchDamage() {
      return 40.0F;
   }

   public static enum Mode {
      SINGLE,
      CROWD;

      // $FF: synthetic method
      private static Mode[] $values() {
         return new Mode[]{SINGLE, CROWD};
      }
   }
}
