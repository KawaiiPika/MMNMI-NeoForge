package xyz.pixelatedw.mineminenomi.abilities.goe;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.goe.TodorokiProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SPinCameraPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUnpinCameraPacket;

public class TodorokiAbility extends Ability {
   public static final int SCREAM_TIME = 20;
   public static final float DAMAGE = 15.0F;
   private static final int CHARGE_TIME = 5;
   private static final float COOLDOWN = 160.0F;
   public static final RegistryObject<AbilityCore<TodorokiAbility>> INSTANCE = ModRegistry.registerAbility("todoroki", "Todoroki", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user shouts and creates a powerful sound blast.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, TodorokiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(160.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.SHOCKWAVE).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addEndEvent(this::endContinuityEvent);
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addEndEvent(100, this::endChargeEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);

   public TodorokiAbility(AbilityCore<TodorokiAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.animationComponent, this.continuousComponent, this.projectileComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 5.0F);
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 25, 0));
      this.animationComponent.start(entity, ModAnimations.SCREAM, 25);
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      TodorokiProjectile bolt = (TodorokiProjectile)this.projectileComponent.getNewProjectile(entity);
      TodorokiProjectile outer = (TodorokiProjectile)this.projectileComponent.getNewProjectile(entity);
      outer.setOuterRings();
      if (entity instanceof ServerPlayer player) {
         ModNetwork.sendTo(SPinCameraPacket.pinFixed(), player);
      }

      this.projectileComponent.shoot(bolt, entity, entity.m_146909_(), entity.m_146908_(), 1.0F, 1.0F);
      this.projectileComponent.shoot(outer, entity, entity.m_146909_(), entity.m_146908_(), 1.0F, 1.0F);
      this.continuousComponent.startContinuity(entity, 20.0F);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity instanceof ServerPlayer player) {
         ModNetwork.sendTo(new SUnpinCameraPacket(), player);
      }

      this.cooldownComponent.startCooldown(entity, 160.0F);
   }

   private TodorokiProjectile createProjectile(LivingEntity entity) {
      return new TodorokiProjectile(entity.m_9236_(), entity, this);
   }
}
