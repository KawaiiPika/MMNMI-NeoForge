package xyz.pixelatedw.mineminenomi.entities.mobs;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.IThreatLevel;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.pacifista.PacifistaRadicalBeamGoal;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class PacifistaEntity extends OPEntity implements IThreatLevel {
   public static final byte START_CHARGE_LASER_EVENT = 100;
   public static final byte END_CHARGE_LASER_EVENT = 101;
   private boolean isChargingLaser = false;

   public PacifistaEntity(EntityType<? extends PacifistaEntity> type, Level world) {
      super(type, world);
      if (world != null && !world.isClientSide) {
         this.getStats().getIdentity().setRace("cyborg");
         this.getStats().getIdentity().setFaction("marine");
         this.getStats().getIdentity().setFightingStyle("brawler");
         MobsHelper.addBasicNPCGoals(this);
         this.goalSelector.addGoal(1, new ImprovedMeleeAttackGoal(this, 1.0D, true));
         this.goalSelector.addGoal(2, new PacifistaRadicalBeamGoal(this));
      }
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes()
              .add(Attributes.MAX_HEALTH, 60.0D)
              .add(Attributes.MOVEMENT_SPEED, 0.25D)
              .add(Attributes.ATTACK_DAMAGE, 6.0D)
              .add(Attributes.FOLLOW_RANGE, 150.0D)
              .add(Attributes.ARMOR, 20.0D)
              .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
              .add(Attributes.ATTACK_KNOCKBACK, 2.0D);
   }

   @Override
   public float getThreatLevel() {
      return 0.4F;
   }

   @Override
   public void die(DamageSource cause) {
      super.die(cause);
      if (!this.level().isClientSide) {
         AbilityExplosion explosion = new AbilityExplosion(this, null, this.getX(), this.getY(), this.getZ(), 5.0F);
         explosion.setStaticDamage(25.0F);
         explosion.setDestroyBlocks(false);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION5);
         explosion.explode();
      }
   }

   @Override
   public void handleEntityEvent(byte id) {
      switch (id) {
         case 100 -> this.isChargingLaser = true;
         case 101 -> this.isChargingLaser = false;
      }
      super.handleEntityEvent(id);
   }

   public boolean isChargingLaser() {
      return this.isChargingLaser;
   }

   public static boolean checkSpawnRules(EntityType<? extends OPEntity> type, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
      double chance = random.nextFloat() * 100.0F;
      return chance <= ServerConfig.getPacifistaSpawnChance() && OPEntity.checkSpawnRules(type, world, reason, pos, random);
   }
}
