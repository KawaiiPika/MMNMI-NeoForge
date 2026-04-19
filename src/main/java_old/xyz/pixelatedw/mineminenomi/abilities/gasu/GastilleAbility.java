package xyz.pixelatedw.mineminenomi.abilities.gasu;

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
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gasu.GastilleProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GastilleAbility extends Ability {
   private static final int COOLDOWN = 300;
   public static final RegistryObject<AbilityCore<GastilleAbility>> INSTANCE = ModRegistry.registerAbility("gastille", "Gastille", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Shoots a beam of lit gas from the users mouth, that explodes on impact", (Object)null), ImmutablePair.of("If %s is active a bigger and more destructive laser will be shot.", new Object[]{ShinokuniAbility.INSTANCE}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GastilleAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F)).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.EXPLOSION).build("mineminenomi");
   });
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this, 800);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);

   public GastilleAbility(AbilityCore<GastilleAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.explosionComponent, this.projectileComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity);
      this.cooldownComponent.startCooldown(entity, 300.0F);
   }

   private GastilleProjectile createProjectile(LivingEntity entity) {
      GastilleProjectile projectile = new GastilleProjectile(entity.m_9236_(), entity, this);
      return projectile;
   }
}
