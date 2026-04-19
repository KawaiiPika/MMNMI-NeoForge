package xyz.pixelatedw.mineminenomi.abilities.horo;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.horo.TokuHollowProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class TokuHollowAbility extends Ability {
   private static final int COOLDOWN = 300;
   public static final RegistryObject<AbilityCore<TokuHollowAbility>> INSTANCE = ModRegistry.registerAbility("toku_hollow", "Toku Hollow", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates a huge ghost that causes a massive explosion upon impact.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, TokuHollowAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).build("mineminenomi");
   });
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);

   public TokuHollowAbility(AbilityCore<TokuHollowAbility> core) {
      super(core);
      super.addComponents(this.projectileComponent, this.explosionComponent);
      super.addUseEvent(this::onUse);
   }

   private void onUse(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity);
      super.cooldownComponent.startCooldown(entity, WyHelper.secondsToTicks(15.0F));
   }

   private TokuHollowProjectile createProjectile(LivingEntity entity) {
      TokuHollowProjectile proj = new TokuHollowProjectile(entity.m_9236_(), entity, this);
      return proj;
   }
}
