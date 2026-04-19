package xyz.pixelatedw.mineminenomi.abilities.bane;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bane.SpringDeathKnockProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SpringDeathKnockAbility extends Ability {
   private static final int COOLDOWN = 80;
   public static final RegistryObject<AbilityCore<SpringDeathKnockAbility>> INSTANCE = ModRegistry.registerAbility("spring_death_knock", "Spring Death Knock", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("By turning the user's arm into a spring and compressing it, they can launch a powerful punch", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SpringDeathKnockAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(CooldownComponent.getTooltip(80.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);

   public SpringDeathKnockAbility(AbilityCore<SpringDeathKnockAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.projectileComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity, 3.0F, 1.0F);
      this.cooldownComponent.startCooldown(entity, 80.0F);
      entity.m_21011_(InteractionHand.MAIN_HAND, true);
   }

   private SpringDeathKnockProjectile createProjectile(LivingEntity entity) {
      SpringDeathKnockProjectile proj = new SpringDeathKnockProjectile(entity.m_9236_(), entity, this);
      return proj;
   }
}
