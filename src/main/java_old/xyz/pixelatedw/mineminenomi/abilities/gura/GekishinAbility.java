package xyz.pixelatedw.mineminenomi.abilities.gura;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gura.GekishinProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GekishinAbility extends Ability {
   private static final Component KAISHIN_NAME = Component.m_237115_(ModRegistry.registerAbilityName("kaishin", "Kaishin"));
   private static final ResourceLocation GEKISHIN_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/gekishin.png");
   private static final ResourceLocation KAISHIN_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/kaishin.png");
   private static final float GEKISHIN_COOLDOWN = 240.0F;
   private static final float GEKISHIN_CHARGE_TIME = 20.0F;
   private static final float KAISHIN_COOLDOWN = 480.0F;
   private static final float KAISHIN_CHARGE_TIME = 40.0F;
   private static final int GEKISHIN_RANGE = 8;
   public static final RegistryObject<AbilityCore<GekishinAbility>> INSTANCE = ModRegistry.registerAbility("gekishin", "Gekishin", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user 'cracks' the air, launching a single concentrated shockwave forward which send blocks flying and deals damage to all enemies in its path", (Object)null), ImmutablePair.of("The user 'cracks' the air on their sides, launching two smaller shockwaves which send blocks flying and deals damage to all enemies in their path", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GekishinAbility::new)).addAdvancedDescriptionLine((e, a) -> Component.m_237113_(name).m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GREEN)), (e, a) -> desc[0], CooldownComponent.getTooltip(240.0F), ChargeComponent.getTooltip(20.0F)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, (e, a) -> KAISHIN_NAME.m_6881_().m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GREEN)), (e, a) -> desc[1], CooldownComponent.getTooltip(480.0F), ChargeComponent.getTooltip(40.0F)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE).setSourceElement(SourceElement.SHOCKWAVE).setSourceHakiNature(SourceHakiNature.IMBUING).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::onChargeStart).addEndEvent(this::onChargeEnd);
   private final AltModeComponent<Mode> altModeComponent;
   private final AnimationComponent animationComponent;
   private final ProjectileComponent projectileComponent;
   private final RangeComponent rangeComponent;
   private final ExplosionComponent explosionComponent;

   public GekishinAbility(AbilityCore<GekishinAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<Mode>(this, Mode.class, GekishinAbility.Mode.GEKISHIN)).addChangeModeEvent(this::onAltModeChange);
      this.animationComponent = new AnimationComponent(this);
      this.projectileComponent = new ProjectileComponent(this, this::createProjectile);
      this.rangeComponent = new RangeComponent(this);
      this.explosionComponent = new ExplosionComponent(this, 12000);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.altModeComponent, this.animationComponent, this.projectileComponent, this.rangeComponent, this.explosionComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (this.altModeComponent.getCurrentMode() == GekishinAbility.Mode.KAISHIN) {
         this.chargeComponent.startCharging(entity, 40.0F);
      } else if (this.altModeComponent.getCurrentMode() == GekishinAbility.Mode.GEKISHIN) {
         this.chargeComponent.startCharging(entity, 20.0F);
      }

   }

   private void onChargeStart(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.KAISHIN);
   }

   private void onChargeEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.altModeComponent.getCurrentMode() == GekishinAbility.Mode.GEKISHIN) {
            EntityHitResult trace = WyHelper.rayTraceEntities(entity, (double)1.5F);
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.AIR_CRACKS.get(), entity, trace.m_82450_().m_7096_(), entity.m_20188_(), trace.m_82450_().m_7094_());
            entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.GURA_SFX.get(), SoundSource.PLAYERS, 5.0F, 1.0F);
            this.projectileComponent.shoot(entity, 3.0F, 1.0F);
         } else if (this.altModeComponent.getCurrentMode() == GekishinAbility.Mode.KAISHIN) {
            Vec3 v1 = entity.m_20182_().m_82520_((double)0.0F, (double)entity.m_20192_(), (double)0.0F).m_82549_(entity.m_20154_().m_82490_((double)2.5F).m_82524_(180.0F));
            Vec3 v2 = entity.m_20182_().m_82520_((double)0.0F, (double)entity.m_20192_(), (double)0.0F).m_82549_(entity.m_20154_().m_82490_((double)2.5F).m_82524_(-180.0F));
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.AIR_CRACKS.get(), entity, v1.m_7096_(), entity.m_20186_() + (double)0.5F, v1.m_7094_());
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.AIR_CRACKS.get(), entity, v2.m_7096_(), entity.m_20186_() + (double)0.5F, v2.m_7094_());

            for(Entity target : this.rangeComponent.getTargetsInArea(entity, 8.0F)) {
               if (target instanceof NuProjectileEntity) {
                  NuProjectileEntity proj = (NuProjectileEntity)target;
                  if (proj.getDamage() < 20.0F && proj.isPhysical()) {
                     target.m_146870_();
                  }
               }

               if (target instanceof LivingEntity) {
                  Vec3 speed = target.m_20154_().m_82542_((double)3.0F, (double)2.0F, (double)3.0F);
                  AbilityHelper.setDeltaMovement(target, speed.f_82479_, speed.f_82480_, speed.f_82481_);
                  target.f_19789_ = 0.0F;
               }
            }

            entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.GURA_SFX.get(), SoundSource.PLAYERS, 0.5F, 1.0F);
            GekishinProjectile p1 = (GekishinProjectile)this.projectileComponent.getNewProjectile(entity);
            p1.setMaxLife((int)((float)p1.getMaxLife() * 0.5F));
            p1.m_37251_(entity, entity.m_146909_(), entity.m_146908_() + 90.0F, 0.0F, 1.75F, 1.0F);
            entity.m_9236_().m_7967_(p1);
            GekishinProjectile p2 = (GekishinProjectile)this.projectileComponent.getNewProjectile(entity);
            p2.setMaxLife((int)((float)p2.getMaxLife() * 0.5F));
            p2.m_37251_(entity, entity.m_146909_(), entity.m_146908_() - 90.0F, 0.0F, 1.75F, 1.0F);
            entity.m_9236_().m_7967_(p2);
         }

         this.animationComponent.stop(entity);
         if (this.altModeComponent.getCurrentMode() == GekishinAbility.Mode.KAISHIN) {
            super.cooldownComponent.startCooldown(entity, 480.0F);
         } else if (this.altModeComponent.getCurrentMode() == GekishinAbility.Mode.GEKISHIN) {
            super.cooldownComponent.startCooldown(entity, 240.0F);
         }

      }
   }

   private boolean onAltModeChange(LivingEntity entity, IAbility ability, Mode mode) {
      if (mode == GekishinAbility.Mode.KAISHIN) {
         super.setDisplayName(KAISHIN_NAME);
         super.setDisplayIcon(KAISHIN_ICON);
      } else if (mode == GekishinAbility.Mode.GEKISHIN) {
         super.setDisplayName(((AbilityCore)INSTANCE.get()).getLocalizedName());
         super.setDisplayIcon(GEKISHIN_ICON);
      }

      return true;
   }

   private GekishinProjectile createProjectile(LivingEntity entity) {
      GekishinProjectile proj = new GekishinProjectile(entity.m_9236_(), entity, this);
      return proj;
   }

   public static enum Mode {
      GEKISHIN,
      KAISHIN;

      // $FF: synthetic method
      private static Mode[] $values() {
         return new Mode[]{GEKISHIN, KAISHIN};
      }
   }
}
