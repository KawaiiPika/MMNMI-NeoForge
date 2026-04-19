package xyz.pixelatedw.mineminenomi.abilities.nikyu;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.nikyu.ChargingUrsusShockEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.nikyu.UrsusShockProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class UrsusShockAbility extends Ability {
   private static final int COOLDOWN = 400;
   private static final int CHARGE_TIME = 140;
   public static final RegistryObject<AbilityCore<UrsusShockAbility>> INSTANCE = ModRegistry.registerAbility("ursus_shock", "Ursus Shock", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user compresses air and sends it towards the opponent to create a huge shockwave", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, UrsusShockAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(400.0F)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.AIR).setSourceType(SourceType.PROJECTILE, SourceType.INTERNAL).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this, (comp) -> comp.getChargePercentage() >= 0.5F)).addStartEvent(this::startChargeEvent).addTickEvent(this::duringChargeEvent).addEndEvent(this::endChargeEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private ChargingUrsusShockEntity ursusShockEntity;

   public UrsusShockAbility(AbilityCore<UrsusShockAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.animationComponent, this.projectileComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 140.0F);
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.RAISE_ARMS, 140);
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.URSUS_SHOCK_SFX.get(), SoundSource.PLAYERS, 5.0F, 0.75F);
      ChargingUrsusShockEntity chargingUrsusShock = new ChargingUrsusShockEntity(entity.m_9236_());
      chargingUrsusShock.setOwner(entity);
      chargingUrsusShock.m_6034_(entity.m_20185_(), entity.m_20186_() + (double)2.0F, entity.m_20189_());
      entity.m_9236_().m_7967_(chargingUrsusShock);
      this.ursusShockEntity = chargingUrsusShock;
   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      if (this.ursusShockEntity == null) {
         this.chargeComponent.forceStopCharging(entity);
      } else {
         boolean atThreshold = (double)this.chargeComponent.getChargePercentage() < 0.4;
         float currentCharge = this.ursusShockEntity.getCharge();
         currentCharge = (float)((double)currentCharge + (atThreshold ? 0.065 : -0.055));
         currentCharge = Mth.m_14036_(currentCharge, -1.4F, 10.0F);
         this.ursusShockEntity.setCharge(currentCharge);
      }
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      float multiplier = this.chargeComponent.getChargePercentage();
      if (this.ursusShockEntity != null) {
         UrsusShockProjectile projectile = new UrsusShockProjectile(entity.m_9236_(), entity, this);
         projectile.multiplier = multiplier;
         projectile.setSize((double)multiplier > (double)0.75F ? 0.6F : 5.0F * (1.0F - multiplier));
         entity.m_9236_().m_7967_(projectile);
         projectile.m_37251_(entity, entity.m_146909_(), entity.m_146908_(), 0.0F, 2.0F, 0.0F);
      }

      this.ursusShockEntity.m_142687_(RemovalReason.DISCARDED);
      this.cooldownComponent.startCooldown(entity, 400.0F * multiplier);
   }

   private UrsusShockProjectile createProjectile(LivingEntity entity) {
      UrsusShockProjectile proj = new UrsusShockProjectile(entity.m_9236_(), entity, this);
      return proj;
   }
}
