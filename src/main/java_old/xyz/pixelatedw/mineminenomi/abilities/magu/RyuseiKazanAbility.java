package xyz.pixelatedw.mineminenomi.abilities.magu;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.magu.RyuseiKazanProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class RyuseiKazanAbility extends Ability {
   private static final float COOLDOWN = 400.0F;
   public static final RegistryObject<AbilityCore<RyuseiKazanAbility>> INSTANCE = ModRegistry.registerAbility("ryusei_kazan", "Ryusei Kazan", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Functions like 'Dai Funka', but multiple fists are launched at the opponent", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, RyuseiKazanAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(400.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.MAGMA).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::onContinuityStart);
   private final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addTriggerEvent(this::onRepeaterTrigger).addStopEvent(this::onRepeaterStop);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);

   public RyuseiKazanAbility(AbilityCore<RyuseiKazanAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.repeaterComponent, this.projectileComponent});
      this.addUseEvent(this::onUse);
   }

   private void onUse(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         this.repeaterComponent.stop(entity);
      } else {
         this.continuousComponent.triggerContinuity(entity);
      }
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      this.repeaterComponent.start(entity, 10, 5);
   }

   private void onRepeaterTrigger(LivingEntity entity, IAbility ability) {
      Vec3 lookVec = entity.m_20154_().m_82541_();
      RyuseiKazanProjectile proj = (RyuseiKazanProjectile)this.projectileComponent.getNewProjectile(entity);
      if (lookVec.f_82480_ > 0.7) {
         proj.m_37251_(entity, entity.m_146909_(), entity.m_146908_(), 0.0F, 3.0F, 8.0F);
         proj.setMaxLife(300);
         proj.setGravity(0.05F);
      } else {
         proj.m_37251_(entity, entity.m_146909_(), entity.m_146908_(), 0.0F, 2.5F, 4.0F);
      }

      entity.m_9236_().m_7967_(proj);
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.MAGU_SFX.get(), SoundSource.PLAYERS, 3.0F, 1.0F);
   }

   private void onRepeaterStop(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
      super.cooldownComponent.startCooldown(entity, 400.0F);
   }

   private RyuseiKazanProjectile createProjectile(LivingEntity entity) {
      return new RyuseiKazanProjectile(entity.m_9236_(), entity, this);
   }
}
