package xyz.pixelatedw.mineminenomi.abilities.mera;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mera.HidarumaProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class HidarumaAbility extends Ability {
   private static final int MAX_THRESHOLD = 60;
   private static final int MIN_COOLDOWN = 200;
   private static final int MAX_COOLDOWN = 500;
   private static final int MAX_FIREFLIES = 30;
   private final Interval fireflyInterval = new Interval(2);
   public static final RegistryObject<AbilityCore<HidarumaAbility>> INSTANCE = ModRegistry.registerAbility("hidaruma", "Hidaruma", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates small green fireballs that set the target on fire", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, HidarumaAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F, 500.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.FIRE).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);

   public HidarumaAbility(AbilityCore<HidarumaAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.projectileComponent});
      this.addCanUseCheck(MeraHelper::canUseMeraAbilities);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity player, IAbility ability) {
      this.continuousComponent.triggerContinuity(player, 60.0F);
   }

   private void onContinuityTick(LivingEntity player, IAbility ability) {
      if (this.fireflyInterval.canTick()) {
         HidarumaProjectile proj = (HidarumaProjectile)this.projectileComponent.getNewProjectile(player);
         double offsetX = (super.random.nextDouble() - (double)0.5F) * 0.2;
         double offsetY = (super.random.nextDouble() - (double)0.5F) * 0.2;
         double offsetZ = (super.random.nextDouble() - (double)0.5F) * 0.2;
         AbilityHelper.setDeltaMovement(proj, player.m_20154_().m_82541_().m_82490_((double)0.25F).m_82520_(offsetX, offsetY, offsetZ));
         player.m_9236_().m_7967_(proj);
      }

   }

   private void onContinuityEnd(LivingEntity player, IAbility ability) {
      this.fireflyInterval.restartIntervalToZero();
      super.cooldownComponent.startCooldown(player, 200.0F + this.continuousComponent.getContinueTime() * 5.0F);
   }

   private HidarumaProjectile createProjectile(LivingEntity entity) {
      HidarumaProjectile proj = new HidarumaProjectile(entity.m_9236_(), entity, this);
      return proj;
   }
}
