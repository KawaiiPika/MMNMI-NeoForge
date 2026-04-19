package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
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
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.CombatHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.entities.projectiles.HookEntity;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class HookGrabAbility extends Ability {
   private static final int COOLDOWN = 240;
   public static final RegistryObject<AbilityCore<HookGrabAbility>> INSTANCE = ModRegistry.registerAbility("hook_grab", "Hook Grab", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Shoot's the equipped Hook catching the enemy and dragging them towards the user.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.EQUIPMENT, HookGrabAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private LivingEntity grabbedEntity = null;
   private ItemStack hookStack;
   private InteractionHand hookHand;
   private HookEntity proj;

   public HookGrabAbility(AbilityCore<HookGrabAbility> core) {
      super(core);
      this.hookStack = ItemStack.f_41583_;
      this.hookHand = InteractionHand.MAIN_HAND;
      this.proj = null;
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.projectileComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresDryUser);
      this.addCanUseCheck(this::canUseCheck);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.grabbedEntity = null;
      this.proj = (HookEntity)this.projectileComponent.getNewProjectile(entity);
      this.proj.addEntityHitEvent(100, (result) -> {
         Entity patt3508$temp = result.m_82443_();
         if (patt3508$temp instanceof LivingEntity target) {
            this.grabbedEntity = target;
         }

      });
      ItemStack mainHandStack = entity.m_21205_();
      ItemStack offHandStack = entity.m_21205_();
      boolean hasHookInMainHand = !mainHandStack.m_41619_() && mainHandStack.m_41720_() == ModWeapons.HOOK.get();
      boolean hasHookInOffHand = !offHandStack.m_41619_() && offHandStack.m_41720_() == ModWeapons.HOOK.get();
      if (hasHookInMainHand) {
         this.hookStack = entity.m_21205_();
         this.hookHand = InteractionHand.MAIN_HAND;
      } else if (hasHookInOffHand) {
         this.hookStack = entity.m_21205_();
         this.hookHand = InteractionHand.OFF_HAND;
      }

      entity.m_21008_(this.hookHand, ItemStack.f_41583_);
      entity.m_9236_().m_7967_(this.proj);
      this.proj.m_37251_(entity, entity.m_146909_(), entity.m_146908_(), 0.0F, 4.0F, 0.0F);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if ((this.proj == null || !this.proj.m_6084_()) && this.grabbedEntity == null) {
            this.continuousComponent.stopContinuity(entity);
         } else if (this.grabbedEntity == null || this.grabbedEntity.m_6084_() && !CombatHelper.isGuardBlocking(this.grabbedEntity) && entity.m_142582_(this.grabbedEntity)) {
            if (this.grabbedEntity != null) {
               if (this.grabbedEntity.m_20270_(entity) < 2.0F) {
                  AbilityHelper.setDeltaMovement(this.grabbedEntity, (double)0.0F, (double)0.0F, (double)0.0F);
                  this.continuousComponent.stopContinuity(entity);
                  return;
               }

               if (this.continuousComponent.getContinueTime() % 2.0F == 0.0F) {
                  Vec3 dirVec = this.grabbedEntity.m_20182_().m_82546_(entity.m_20182_()).m_82541_();
                  AbilityHelper.setDeltaMovement(this.grabbedEntity, -dirVec.f_82479_, -dirVec.f_82480_, -dirVec.f_82481_);
                  this.grabbedEntity.f_19789_ = 0.0F;
               }
            }

         } else {
            this.continuousComponent.stopContinuity(entity);
         }
      }
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.proj = null;
      this.grabbedEntity = null;
      entity.m_21008_(this.hookHand, this.hookStack);
      this.cooldownComponent.startCooldown(entity, 240.0F);
   }

   private HookEntity createProjectile(LivingEntity entity) {
      HookEntity proj = new HookEntity(entity.m_9236_(), entity, this);
      return proj;
   }

   private Result canUseCheck(LivingEntity entity, IAbility ability) {
      ItemStack mainHandStack = entity.m_21205_();
      ItemStack offHandStack = entity.m_21205_();
      boolean hasHookInMainHand = !mainHandStack.m_41619_() && mainHandStack.m_41720_() == ModWeapons.HOOK.get();
      boolean hasHookInOffHand = !offHandStack.m_41619_() && offHandStack.m_41720_() == ModWeapons.HOOK.get();
      return !hasHookInMainHand && !hasHookInOffHand ? Result.fail((Component)null) : Result.success();
   }
}
