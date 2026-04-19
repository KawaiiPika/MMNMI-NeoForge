package xyz.pixelatedw.mineminenomi.abilities.brawler;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoPistolProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PunchRushAbility extends Ability {
   public static final RegistryObject<AbilityCore<PunchRushAbility>> INSTANCE = ModRegistry.registerAbility("punch_rush", "Punch Rush", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, PunchRushAbility::new)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi"));
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent);
   private final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addTriggerEvent(this::repeaterTriggerEvent).addStopEvent(this::repeaterStopEvent);
   private int waves = 30;
   private int punchesPerWave = 5;

   public PunchRushAbility(AbilityCore<PunchRushAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.repeaterComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresEmptyHand);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         this.repeaterComponent.stop(entity);
      } else {
         this.continuousComponent.triggerContinuity(entity);
      }
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.repeaterComponent.start(entity, this.waves, 1);
      }

   }

   private void repeaterTriggerEvent(LivingEntity entity, IAbility ability) {
      float speed = 2.2F;
      int projectileSpace = 2;
      float projDmageReduction = 0.6F;

      for(int i = 0; i < this.punchesPerWave; ++i) {
         NuProjectileEntity projectile = new GomuGomuNoPistolProjectile(entity.m_9236_(), entity, this);
         projectile.setEntityCollisionSize((double)1.25F);
         projectile.setMaxLife(3);
         projectile.setDamage(projectile.getDamage() * (1.0F - projDmageReduction));
         projectile.setMaxLife((int)((double)projectile.getMaxLife() * (double)0.75F));
         double px = entity.m_20185_() + WyHelper.randomWithRange(-projectileSpace, projectileSpace) + WyHelper.randomDouble();
         double py = entity.m_20188_() + WyHelper.randomWithRange(0, projectileSpace) + WyHelper.randomDouble();
         double pz = entity.m_20189_() + WyHelper.randomWithRange(-projectileSpace, projectileSpace) + WyHelper.randomDouble();
         projectile.m_7678_(px, py, pz, 0.0F, 0.0F);
         entity.m_9236_().m_7967_(projectile);
         projectile.m_37251_(entity, entity.m_146909_(), entity.m_146908_(), 0.0F, speed, 3.0F);
      }

      entity.m_21011_(InteractionHand.MAIN_HAND, true);
   }

   private void repeaterStopEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
      super.cooldownComponent.startCooldown(entity, WyHelper.secondsToTicks(10.0F));
   }

   public void setWaveDetails(int waves, int punchesPerWave) {
      this.waves = waves;
      this.punchesPerWave = punchesPerWave;
   }
}
