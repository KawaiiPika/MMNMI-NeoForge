package xyz.pixelatedw.mineminenomi.abilities.bane;

import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class SpringHopperAbility extends Ability {
   private static final UUID SPRING_POWER_UUID = UUID.fromString("a44a9644-369a-4e18-88d9-323727d3d85b");
   private static final int COOLDOWN = 200;
   public static final RegistryObject<AbilityCore<SpringHopperAbility>> INSTANCE = ModRegistry.registerAbility("spring_hopper", "Spring Hopper", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("By turning the entity's legs into springs, they can jump around with great ease bouncing around surfaces", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SpringHopperAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ContinuousComponent.getTooltip()).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::onContinuityStart).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   private final MorphComponent morphComponent = new MorphComponent(this);
   private final ChangeStatsComponent changeStatsComponent = new ChangeStatsComponent(this);
   private int jumpPower = 0;
   private boolean canIncreaseJumpPower = false;
   private boolean startedFalling = false;

   public SpringHopperAbility(AbilityCore<SpringHopperAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.morphComponent, this.changeStatsComponent});
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   public void onContinuityStart(LivingEntity entity, IAbility ability) {
      this.morphComponent.startMorph(entity, (MorphInfo)ModMorphs.SPRING_LEGS.get());
   }

   public void onContinuityTick(LivingEntity entity, IAbility ability) {
      if (this.canUse(entity).isFail()) {
         this.jumpPower = 0;
         this.continuousComponent.stopContinuity(entity);
      } else {
         if (entity.f_19862_ && this.jumpPower > 2) {
            Vec3 speed = entity.m_20154_().m_82542_((double)-2.0F, (double)-2.0F, (double)-2.0F);
            AbilityHelper.setDeltaMovement(entity, speed.f_82479_, speed.f_82480_, speed.f_82481_);
            if (this.jumpPower < 9) {
               ++this.jumpPower;
            }

            if (entity instanceof Player) {
               Player player = (Player)entity;
               entity.m_9236_().m_5594_(player, entity.m_20183_(), (SoundEvent)ModSounds.SPRING_SFX.get(), SoundSource.PLAYERS, 1.0F, (float)Mth.m_14008_(entity.m_217043_().m_188500_() + (double)0.3F, (double)0.5F, (double)1.5F));
            }
         }

         if (entity.m_20096_()) {
            this.startedFalling = true;
            boolean isJumping = (Boolean)EntityStatsCapability.get(entity).map((props) -> props.isJumping()).orElse(false);
            if (isJumping) {
               if (this.jumpPower > 3) {
                  Vec3 speed = entity.m_20154_().m_82542_((double)0.25F + (double)this.jumpPower * (double)0.25F, (double)1.0F, (double)0.25F + (double)this.jumpPower * (double)0.25F);
                  AbilityHelper.setDeltaMovement(entity, speed.f_82479_, entity.m_20184_().f_82480_, speed.f_82481_);
               }

               if (this.jumpPower < 9 && this.canIncreaseJumpPower) {
                  ++this.jumpPower;
               }

               if (entity.m_21051_((Attribute)ModAttributes.JUMP_HEIGHT.get()).m_22135_() != (double)this.jumpPower * (double)1.5F) {
                  this.changeStatsComponent.removeModifiers(entity);
                  this.changeStatsComponent.addAttributeModifier((Supplier)ModAttributes.JUMP_HEIGHT, this.getJumpHeight(this.jumpPower), (e) -> this.continuousComponent.isContinuous());
               }

               if (entity instanceof Player) {
                  Player player = (Player)entity;
                  entity.m_9236_().m_5594_(player, entity.m_20183_(), (SoundEvent)ModSounds.SPRING_SFX.get(), SoundSource.PLAYERS, 0.3F, (float)Mth.m_14008_(entity.m_217043_().m_188500_() + (double)0.3F, (double)0.8F, (double)1.5F));
               }

               this.canIncreaseJumpPower = true;
            } else {
               this.jumpPower = 0;
            }
         } else {
            if (entity.f_19863_) {
               --this.jumpPower;
            }

            if ((double)0.0F > entity.m_20184_().f_82480_) {
               if (this.startedFalling) {
                  this.canIncreaseJumpPower = AbilityHelper.getDifferenceToFloor(entity) > (double)this.jumpPower;
                  this.startedFalling = false;
               }

               if (this.jumpPower > 3) {
                  AbilityHelper.setDeltaMovement(entity, entity.m_20184_().m_82490_(1.15));
               }
            }
         }

      }
   }

   public void onContinuityEnd(LivingEntity entity, IAbility ability) {
      this.morphComponent.stopMorph(entity);
      super.cooldownComponent.startCooldown(entity, 200.0F);
   }

   public AttributeModifier getJumpHeight(int jumpPower) {
      return new AbilityAttributeModifier(SPRING_POWER_UUID, INSTANCE, "Spring Movement Modifier", (double)jumpPower * (double)1.5F, Operation.MULTIPLY_BASE);
   }
}
