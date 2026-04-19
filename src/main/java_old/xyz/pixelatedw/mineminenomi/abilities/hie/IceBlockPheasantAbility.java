package xyz.pixelatedw.mineminenomi.abilities.hie;

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
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hie.IceBlockPheasantProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class IceBlockPheasantAbility extends Ability {
   private static final int COOLDOWN = 500;
   public static final RegistryObject<AbilityCore<IceBlockPheasantAbility>> INSTANCE = ModRegistry.registerAbility("ice_block_pheasant", "Ice Block: Pheasant", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Releases a massive wave of ice in the shape of a pheasant", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, IceBlockPheasantAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(500.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.ICE).build("mineminenomi");
   });
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);

   public IceBlockPheasantAbility(AbilityCore<IceBlockPheasantAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.projectileComponent});
      super.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity, 2.0F, 0.0F);
      super.cooldownComponent.startCooldown(entity, 500.0F);
   }

   private IceBlockPheasantProjectile createProjectile(LivingEntity entity) {
      IceBlockPheasantProjectile proj = new IceBlockPheasantProjectile(entity.m_9236_(), entity, this);
      return proj;
   }
}
