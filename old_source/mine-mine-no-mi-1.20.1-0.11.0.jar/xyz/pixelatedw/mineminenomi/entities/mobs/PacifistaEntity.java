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
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.pacifista.PacifistaRadicalBeamGoal;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class PacifistaEntity extends OPEntity implements IThreatLevel {
   public static final byte START_CHARGE_LASER_EVENT = 100;
   public static final byte END_CHARGE_LASER_EVENT = 101;
   private boolean isChargingLaser = false;

   public PacifistaEntity(EntityType<? extends PacifistaEntity> type, Level world) {
      super(type, world);
      if (world != null && !world.f_46443_) {
         this.getEntityStats().setRace((Race)ModRaces.CYBORG.get());
         this.getEntityStats().setFaction((Faction)ModFactions.MARINE.get());
         this.getEntityStats().setFightingStyle((FightingStyle)ModFightingStyles.BRAWLER.get());
         MobsHelper.addBasicNPCGoals(this);
         this.f_21345_.m_25352_(1, new ImprovedMeleeAttackGoal(this, (double)1.0F, true));
         this.f_21345_.m_25352_(2, new PacifistaRadicalBeamGoal(this));
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.25F).m_22268_(Attributes.f_22281_, (double)6.0F).m_22268_(Attributes.f_22276_, (double)150.0F).m_22268_(Attributes.f_22284_, (double)20.0F).m_22268_(Attributes.f_22278_, 0.8).m_22268_(Attributes.f_22282_, (double)2.0F);
   }

   public float getThreatLevel() {
      return 0.4F;
   }

   public void m_6667_(DamageSource cause) {
      super.m_6667_(cause);
      if (!this.m_9236_().f_46443_) {
         AbilityExplosion explosion = new AbilityExplosion(this, (IAbility)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), 5.0F);
         explosion.setStaticDamage(25.0F);
         explosion.setDestroyBlocks(false);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION5);
         explosion.m_46061_();
      }

   }

   @OnlyIn(Dist.CLIENT)
   public void m_7822_(byte id) {
      switch (id) {
         case 100 -> this.isChargingLaser = true;
         case 101 -> this.isChargingLaser = false;
      }

      super.m_7822_(id);
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isChargingLaser() {
      return this.isChargingLaser;
   }

   public static boolean checkSpawnRules(EntityType<? extends OPEntity> type, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
      double chance = random.m_188500_() * (double)100.0F;
      return chance > ServerConfig.getPacifistaSpawnChance() ? false : OPEntity.checkSpawnRules(type, world, reason, pos, random);
   }
}
