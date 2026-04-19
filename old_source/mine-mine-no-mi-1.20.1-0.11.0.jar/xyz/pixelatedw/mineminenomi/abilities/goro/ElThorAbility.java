package xyz.pixelatedw.mineminenomi.abilities.goro;

import java.awt.Color;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.goro.ElThorProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ElThorAbility extends Ability {
   private static final ResourceLocation DEFAULT_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/el_thor.png");
   private static final ResourceLocation ALT_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/alts/el_thor.png");
   public static final Color YELLOW_THUNDER = new Color(-135916, true);
   public static final Color BLUE_THUNDER = WyHelper.hexToRGB("#70EAFF22");
   private static final int CHARGE_TIME = 80;
   private static final int COOLDOWN = 360;
   public static final RegistryObject<AbilityCore<ElThorAbility>> INSTANCE = ModRegistry.registerAbility("el_thor", "El Thor", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Focuses a large cluster of electricity above the target, then sends a powerful lightning bolt crashing down from the sky", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, ElThorAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(360.0F), ChargeComponent.getTooltip(80.0F)).setSourceElement(SourceElement.LIGHTNING).setSourceHakiNature(SourceHakiNature.SPECIAL).setIcon(DEFAULT_ICON).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this, (component) -> component.getChargeTime() >= 10.0F)).addStartEvent(this::onChargeStart).addTickEvent(this::onChargeTick).addEndEvent(this::onChargeEnd);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this, 8150);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final Interval particleInterval = new Interval(2);

   public ElThorAbility(AbilityCore<ElThorAbility> core) {
      super(core);
      this.setDisplayIcon(DEFAULT_ICON);
      if (ClientConfig.isGoroBlue()) {
         this.setDisplayIcon(ALT_ICON);
      }

      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.animationComponent, this.explosionComponent, this.projectileComponent});
      this.addUseEvent(this::onUseEvent);
      this.addEquipEvent(this::equipEvent);
   }

   public void equipEvent(LivingEntity entity, IAbility ability) {
      super.setDisplayIcon(DEFAULT_ICON);
      if (ClientConfig.isGoroBlue()) {
         super.setDisplayIcon(ALT_ICON);
      }

   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 80.0F);
   }

   private void onChargeStart(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.particleInterval.restartIntervalToZero();
         this.animationComponent.start(entity, ModAnimations.RAISE_RIGHT_ARM);
      }
   }

   private void onChargeTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         AbilityHelper.slowEntityFall(entity);
         if (this.particleInterval.canTick()) {
            HitResult mop = WyHelper.rayTraceBlocksAndEntities(entity, (double)256.0F, 0.4F);
            double i = mop.m_82450_().f_82479_;
            double j = mop.m_82450_().f_82480_;
            double k = mop.m_82450_().f_82481_;
            if (entity instanceof Player) {
               Player player = (Player)entity;
               if (entity.f_19797_ % 5 == 0) {
                  WyHelper.spawnParticleEffectForOwner((ParticleEffect)ModParticleEffects.EL_THOR_AIM.get(), player, i, j, k, (ParticleEffect.Details)null);
               }
            }

         }
      }
   }

   private void onChargeEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         ElThorProjectile proj = (ElThorProjectile)this.projectileComponent.getNewProjectile(entity);
         entity.m_9236_().m_5594_((Player)null, proj.m_20183_(), (SoundEvent)ModSounds.EL_THOR_SFX.get(), SoundSource.PLAYERS, 20.0F, 1.0F);
         this.projectileComponent.shoot(proj, entity, entity.m_146909_(), entity.m_146908_(), 1.0F, 1.0F);
         this.animationComponent.stop(entity);
         this.cooldownComponent.startCooldown(entity, 360.0F);
      }
   }

   private ElThorProjectile createProjectile(LivingEntity entity) {
      HitResult mop = WyHelper.rayTraceBlocksAndEntities(entity);
      Vec3 aimPos = mop.m_82450_();
      BlockHitResult hitResult = entity.m_9236_().m_45547_(new ClipContext(aimPos, aimPos.m_82520_((double)0.0F, (double)192.0F, (double)0.0F), Block.COLLIDER, Fluid.ANY, entity));
      int targetY = hitResult.m_6662_().equals(Type.BLOCK) ? (int)hitResult.m_82450_().f_82480_ : 192;
      int travelLength = Math.round((float)targetY + 14.0F);
      float multi = 1.0F;
      if (((MorphInfo)ModMorphs.VOLT_AMARU.get()).isActive(entity)) {
         multi += 0.25F;
      }

      return new ElThorProjectile(entity.m_9236_(), entity, aimPos.f_82479_, aimPos.f_82480_, aimPos.f_82481_, targetY, (float)travelLength, multi, this);
   }
}
