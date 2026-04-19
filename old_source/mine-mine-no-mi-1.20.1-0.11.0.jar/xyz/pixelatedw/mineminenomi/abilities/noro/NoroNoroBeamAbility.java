package xyz.pixelatedw.mineminenomi.abilities.noro;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.noro.NoroNoroBeamProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class NoroNoroBeamAbility extends Ability {
   private static final float COOLDOWN = 60.0F;
   private static final int ANIMATION_TICK = 10;
   public static final RegistryObject<AbilityCore<NoroNoroBeamAbility>> INSTANCE = ModRegistry.registerAbility("noro_noro_beam", "Noro Noro Beam", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Shoots a beam of photons at the opponent, completely slowing them down (multiple hits stack the Slowness effect)", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, NoroNoroBeamAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(60.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).build("mineminenomi");
   });
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final AnimationComponent animationComponent = new AnimationComponent(this);

   public NoroNoroBeamAbility(AbilityCore<NoroNoroBeamAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.projectileComponent, this.animationComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.AIM_SNIPER, 10);
      this.projectileComponent.shoot(entity, 4.0F, 1.0F);
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.NORO_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
      this.cooldownComponent.startCooldown(entity, 60.0F);
   }

   private NoroNoroBeamProjectile createProjectile(LivingEntity entity) {
      NoroNoroBeamProjectile proj = new NoroNoroBeamProjectile(entity.m_9236_(), entity, this);
      return proj;
   }
}
