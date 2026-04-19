package xyz.pixelatedw.mineminenomi.abilities.sui;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
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
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.mixins.ILivingEntityMixin;

public class FreeSwimmingAbility extends Ability {
   private static final int COOLDOWN = 20;
   public static final RegistryObject<AbilityCore<FreeSwimmingAbility>> INSTANCE = ModRegistry.registerAbility("free_swimming", "Free Swimming", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Lets the user swim trough blocks. Swimming is activated by running into a ground block.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, FreeSwimmingAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(20.0F), ContinuousComponent.getTooltip()).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addTickEvent(this::tickContinuityEvent).addEndEvent(this::endContinuityEvent);
   private boolean isSwimming = false;

   public FreeSwimmingAbility(AbilityCore<FreeSwimmingAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent});
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      entity.f_19794_ = true;
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props != null) {
         boolean flying = false;
         if (entity instanceof Player) {
            Player player = (Player)entity;
            flying = player.m_150110_().f_35935_;
         }

         BlockPos pos = entity.m_20183_();
         boolean isMidAir = entity.m_9236_().m_8055_(pos.m_7494_()).m_60795_() && entity.m_9236_().m_8055_(pos.m_7495_()).m_60795_();
         boolean groundCheck = entity.m_20182_().f_82480_ - AbilityHelper.getFloorLevel(entity).m_7098_() > (double)0.0F && entity.m_9236_().m_8055_(pos.m_7495_()).m_60795_();
         NekomimiPunchAbility nekomimiPunch = (NekomimiPunchAbility)props.getEquippedAbility((AbilityCore)NekomimiPunchAbility.INSTANCE.get());
         boolean isNekomimiPunchActive = nekomimiPunch != null && nekomimiPunch.isContinuous();
         boolean isNekomimiFresh = nekomimiPunch != null && entity.m_9236_().m_46467_() - nekomimiPunch.getLastUseGametime() < 100L;
         if (!entity.m_9236_().f_46443_ && entity.m_20069_()) {
            this.continuousComponent.stopContinuity(entity);
         } else {
            boolean isOutsideGround = false;
            if (groundCheck && !isNekomimiPunchActive && !isNekomimiFresh && !isEntityInsideOpaqueBlock(entity)) {
               isOutsideGround = true;
            }

            if (isOutsideGround) {
               entity.f_19794_ = false;
            } else {
               boolean isInsideBlock = isEntityInsideOpaqueBlock(entity);
               if ((isInsideBlock || entity.m_20142_()) && !flying) {
                  entity.m_20124_(Pose.SWIMMING);
                  entity.f_19789_ = 0.0F;
               }

               if (isMidAir && !isNekomimiPunchActive) {
                  entity.f_19794_ = true;
               } else {
                  NyanNyanSuplexAbility nyanSuplex = (NyanNyanSuplexAbility)props.getEquippedAbility((AbilityCore)NyanNyanSuplexAbility.INSTANCE.get());
                  if (nyanSuplex == null || !nyanSuplex.isCharging()) {
                     entity.m_20282_(true);
                     boolean swimming = entity.m_20089_() == Pose.SWIMMING || isNekomimiPunchActive;
                     if (!entity.m_9236_().f_46443_) {
                        this.isSwimming = swimming;
                     }

                     if (swimming && (isInsideBlock || isMidAir && isNekomimiPunchActive)) {
                        double x = (double)0.0F;
                        double y = (double)0.0F;
                        double z = (double)0.0F;
                        double swimSpeed = entity.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22135_() / (double)2.0F;
                        Vec3 lookVector = entity.m_20154_();
                        if (entity.f_20902_ != 0.0F) {
                           double speed = Mth.m_14008_(swimSpeed, 1.3, (double)6.0F);
                           x = lookVector.f_82479_ * speed * (double)entity.f_20902_;
                           y = lookVector.f_82480_ * speed * (double)entity.f_20902_;
                           z = lookVector.f_82481_ * speed * (double)entity.f_20902_;
                        }

                        if (isNekomimiPunchActive) {
                           double speed = 1.6;
                           x = lookVector.f_82479_ * speed * (double)1.0F;
                           y = lookVector.f_82480_ * speed * (double)1.0F;
                           z = lookVector.f_82481_ * speed * (double)1.0F;
                        }

                        boolean isJumping = ((ILivingEntityMixin)entity).isJumping();
                        if (entity.m_6144_()) {
                           y = (double)-0.2F;
                        } else if (isJumping && !entity.m_9236_().m_8055_(pos.m_7495_()).m_60795_()) {
                           y = (double)0.2F;
                           if (entity.m_9236_().m_8055_(pos.m_7494_()).m_60795_()) {
                              y = (double)0.6F;
                           }
                        }

                        BlockPos frontPos = BlockPos.m_274446_(entity.m_20182_().m_82520_(x, y + (double)entity.m_20192_(), z));
                        BlockState frontBlock = entity.m_9236_().m_8055_(frontPos);
                        if (frontBlock.m_204336_(ModTags.Blocks.BLOCK_PROT_RESTRICTED)) {
                           Vec3 reversedLookVector = entity.f_20902_ < 0.0F ? lookVector : lookVector.m_82548_();
                           x = reversedLookVector.f_82479_;
                           y = reversedLookVector.f_82480_;
                           z = reversedLookVector.f_82481_;
                        }

                        if (entity.m_20186_() < (double)-60.0F) {
                           y = (double)0.0F;
                        }

                        AbilityHelper.setDeltaMovement(entity, x, y, z);
                     }

                  }
               }
            }
         }
      }
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.isSwimming = false;
      this.cooldownComponent.startCooldown(entity, 20.0F);
      entity.f_19794_ = false;
   }

   public void setSwimming(boolean flag) {
      this.isSwimming = flag;
   }

   public boolean isSwimming() {
      return this.isSwimming;
   }

   public static boolean isEntityInsideOpaqueBlock(LivingEntity entity) {
      BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();

      for(int i = 0; i < 8; ++i) {
         int j = Mth.m_14107_(entity.m_20186_() + (double)(((float)((i >> 0) % 2) - 0.5F) * 0.1F) + (double)entity.m_20192_());
         int k = Mth.m_14107_(entity.m_20185_() + (double)(((float)((i >> 1) % 2) - 0.5F) * entity.m_20206_() * 0.8F));
         int l = Mth.m_14107_(entity.m_20189_() + (double)(((float)((i >> 2) % 2) - 0.5F) * entity.m_20205_() * 0.8F));
         if (blockPos.m_123341_() != k || blockPos.m_123342_() != j || blockPos.m_123343_() != l) {
            blockPos.m_122178_(k, j, l);
            boolean isSolid = entity.m_9236_().m_8055_(blockPos).m_60783_(entity.m_9236_(), blockPos, Direction.UP);
            boolean isLiquid = entity.m_9236_().m_6425_(blockPos).m_76170_();
            if (isSolid || isLiquid) {
               return true;
            }
         }
      }

      return false;
   }
}
