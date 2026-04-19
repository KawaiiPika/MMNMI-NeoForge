package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.nikyu;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.abilities.nikyu.UrsusShockAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.IFlexibleSizeProjectile;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class UrsusShockProjectile extends NuProjectileEntity implements IFlexibleSizeProjectile {
   private static final EntityDataAccessor<Float> SIZE;
   private static final EntityDataAccessor<Boolean> FINISHED;
   private static final double DAMAGE_RADIUS = (double)13.75F;
   private static final double KNOCKBACK_RADIUS = (double)22.0F;
   public float multiplier = 0.0F;

   public UrsusShockProjectile(EntityType<? extends UrsusShockProjectile> type, Level world) {
      super(type, world);
   }

   public UrsusShockProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.URSUS_SHOCK.get(), world, player, ability);
      this.setDamage(15.0F);
      this.setMaxLife(400);
      this.setArmorPiercing(1.0F);
      this.setPassThroughBlocks();
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
   }

   public void m_8119_() {
      super.m_8119_();
      this.f_19811_ = true;
      if (this.isFinished()) {
         this.setSize(Math.min(this.getSize() + 0.5F, 50.0F));
         AbilityHelper.setDeltaMovement(this, (double)0.0F, (double)0.0F, (double)0.0F);
         this.m_6021_(this.m_20182_().f_82479_, this.m_20182_().f_82480_, this.m_20182_().f_82481_);
         this.m_19915_(90.0F, 0.0F);
         this.f_19860_ = this.m_146909_();
         this.f_19859_ = this.m_146908_();
      }

   }

   private void onBlockImpactEvent(BlockHitResult result) {
      if (!this.isFinished()) {
         BlockPos pos = result.m_82425_();
         this.m_9236_().m_5594_((Player)null, this.m_20183_(), (SoundEvent)ModSounds.PAD_HO_SFX.get(), SoundSource.PLAYERS, 10.0F, 0.25F);
         SimpleBlockPlacer placer = (new SimpleBlockPlacer()).setBlock(Blocks.f_50016_.m_49966_()).setSize(55, 5).setRule(DefaultProtectionRules.CORE_FOLIAGE_ORE);
         placer.generate(this.m_9236_(), pos, BlockGenerators.SPHERE);
         if (this.getOwner() != null) {
            List<LivingEntity> targets = TargetHelper.<LivingEntity>getEntitiesInArea(this.m_9236_(), this.getOwner(), this.m_20183_(), (double)22.0F, TargetPredicate.DEFAULT_AREA_CHECK, LivingEntity.class);
            DamageSource source = ModDamageSources.getInstance().ability(this, this.getOwner(), (AbilityCore)UrsusShockAbility.INSTANCE.get());
            IDamageSourceHandler handler = IDamageSourceHandler.getHandler(source);
            handler.addDamage(85.0F, SourceElement.SHOCKWAVE, SourceHakiNature.IMBUING, SourceType.INTERNAL);
            handler.bypassLogia();
            handler.addAbilityPiercing(1.0F, (AbilityCore)this.getParent().map((abl) -> abl.getCore()).orElse((Object)null));

            for(LivingEntity target : targets) {
               if (target.m_20280_(this) < (double)13.75F) {
                  target.f_20916_ = target.f_19802_ = 0;
                  target.m_6469_(source, handler.getTotalDamage());
               }

               Vec3 speed = target.m_20154_().m_82542_((double)-1.0F, (double)-1.0F, (double)-1.0F).m_82542_((double)1.0F, (double)0.0F, (double)1.0F);
               AbilityHelper.setDeltaMovement(target, speed.f_82479_, (double)0.25F, speed.f_82481_);
            }
         }

         this.setFinished();
      }
   }

   public void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(SIZE, 0.0F);
      this.f_19804_.m_135372_(FINISHED, false);
   }

   public void setSize(float size) {
      this.f_19804_.m_135381_(SIZE, size);
   }

   public float getSize() {
      return (Float)this.f_19804_.m_135370_(SIZE);
   }

   public boolean isFinished() {
      return (Boolean)this.f_19804_.m_135370_(FINISHED);
   }

   public void setFinished() {
      this.f_19804_.m_135381_(FINISHED, true);
   }

   static {
      SIZE = SynchedEntityData.m_135353_(UrsusShockProjectile.class, EntityDataSerializers.f_135029_);
      FINISHED = SynchedEntityData.m_135353_(UrsusShockProjectile.class, EntityDataSerializers.f_135035_);
   }
}
