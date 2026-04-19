package xyz.pixelatedw.mineminenomi.abilities.mero;

import net.minecraft.network.chat.Component;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mero.PistolKissProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PistolKissAbility extends Ability {
   private static final int COOLDOWN = 100;
   public static final RegistryObject<AbilityCore<PistolKissAbility>> INSTANCE = ModRegistry.registerAbility("pistol_kiss", "Pistol Kiss", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("A weaker but faster variant of %s.", new Object[]{MeroMeroMellowAbility.INSTANCE}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, PistolKissAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).build("mineminenomi");
   });
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final AnimationComponent animationComponent = new AnimationComponent(this);

   public PistolKissAbility(AbilityCore<PistolKissAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.projectileComponent, this.animationComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.AIM_SNIPER, 7);
      this.projectileComponent.shoot(entity, 3.5F, 1.0F);
      this.cooldownComponent.startCooldown(entity, 100.0F);
   }

   private PistolKissProjectile createProjectile(LivingEntity entity) {
      PistolKissProjectile proj = new PistolKissProjectile(entity.m_9236_(), entity, this);
      return proj;
   }
}
