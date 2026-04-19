package xyz.pixelatedw.mineminenomi.abilities.pero;

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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.pero.CandyEscalatorProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class CandyEscalatorAbility extends Ability {
   private static final int COOLDOWN = 160;
   public static final RegistryObject<AbilityCore<CandyEscalatorAbility>> INSTANCE = ModRegistry.registerAbility("candy_escalator", "Candy Escalator", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates a line made out of candy in front of the user.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, CandyEscalatorAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(160.0F)).build("mineminenomi");
   });
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);

   public CandyEscalatorAbility(AbilityCore<CandyEscalatorAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.projectileComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity, 2.0F, 1.0F);
      this.cooldownComponent.startCooldown(entity, 160.0F);
   }

   private CandyEscalatorProjectile createProjectile(LivingEntity entity) {
      CandyEscalatorProjectile proj = new CandyEscalatorProjectile(entity.m_9236_(), entity, this);
      return proj;
   }
}
