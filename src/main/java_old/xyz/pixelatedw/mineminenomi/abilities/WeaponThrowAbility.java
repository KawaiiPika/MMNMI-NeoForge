package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.ThrowingWeaponEntity;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class WeaponThrowAbility extends Ability {
   private static final float COOLDOWN = 200.0F;
   public static final RegistryObject<AbilityCore<WeaponThrowAbility>> INSTANCE = ModRegistry.registerAbility("weapon_throw", "Weapon Throw", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Throws the held weapon dealing damage based on the weapon's damage.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, WeaponThrowAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.BLUNT).build("mineminenomi");
   });
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);

   public WeaponThrowAbility(AbilityCore<WeaponThrowAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.projectileComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresMeleeWeapon);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      entity.m_21011_(InteractionHand.MAIN_HAND, true);
      this.projectileComponent.shoot(entity);
      ItemsHelper.removeItemStackFromInventory(entity, entity.m_21205_());
      this.cooldownComponent.startCooldown(entity, 200.0F);
   }

   private ThrowingWeaponEntity createProjectile(LivingEntity entity) {
      ThrowingWeaponEntity proj = new ThrowingWeaponEntity(entity.m_9236_(), entity);
      return proj;
   }
}
