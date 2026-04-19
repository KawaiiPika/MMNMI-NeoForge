package xyz.pixelatedw.mineminenomi.abilities.hie;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hie.IceBlockAvalancheProjectile;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class IceBlockAvalancheAbility extends Ability {
   private static final int COOLDOWN = 360;
   private static final int CHARGE_TIME = 100;
   public static final RegistryObject<AbilityCore<IceBlockAvalancheAbility>> INSTANCE = ModRegistry.registerAbility("ice_block_avalanche", "Ice Block: Avalanche", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates a sharp blade made of compressed ice", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, IceBlockAvalancheAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(360.0F), ChargeComponent.getTooltip(100.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceElement(SourceElement.ICE).setSourceType(SourceType.BLUNT).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(100, this::startChargeEvent).addTickEvent(100, this::tickChargeEvent).addEndEvent(100, this::stopChargeEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private IceBlockAvalancheProjectile proj;

   public IceBlockAvalancheAbility(AbilityCore<IceBlockAvalancheAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.projectileComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 100.0F);
   }

   private void startChargeEvent(LivingEntity player, IAbility ability) {
      HitResult ray = WyHelper.rayTraceBlocksAndEntities(player, (double)64.0F);
      this.removeDuplicate();
      this.proj = (IceBlockAvalancheProjectile)this.projectileComponent.getNewProjectile(player);
      this.proj.m_6034_(ray.m_82450_().m_7096_(), ray.m_82450_().m_7098_() + (double)20.0F, ray.m_82450_().m_7094_());
      AbilityHelper.setDeltaMovement(this.proj, (double)0.0F, (double)0.0F, (double)0.0F);
      player.m_9236_().m_7967_(this.proj);
   }

   private void tickChargeEvent(LivingEntity entity, IAbility ability) {
      if (entity != null && this.proj != null && this.chargeComponent.getChargeTime() % 2.0F == 0.0F) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.ICE_BLOCK_AVALANCHE.get(), entity, this.proj.m_20185_(), this.proj.m_20186_(), this.proj.m_20189_());
      }

   }

   private void stopChargeEvent(LivingEntity entity, IAbility ability) {
      if (this.proj != null) {
         this.proj.finalized = true;
         AbilityHelper.setDeltaMovement(this.proj, (double)0.0F, (double)-2.0F, (double)0.0F);
         this.cooldownComponent.startCooldown(entity, 360.0F);
      }
   }

   private void removeDuplicate() {
      if (this.proj != null && this.proj.isAddedToWorld()) {
         this.proj.m_142687_(RemovalReason.DISCARDED);
      }

   }

   private IceBlockAvalancheProjectile createProjectile(LivingEntity entity) {
      IceBlockAvalancheProjectile proj = new IceBlockAvalancheProjectile(entity.m_9236_(), entity, this);
      return proj;
   }
}
