package xyz.pixelatedw.mineminenomi.abilities.cyborg;

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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ColaUsageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.CoupDeVentProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class CoupDeVentAbility extends Ability {
   private static final float COOLDOWN = 240.0F;
   private static final int COLA_REQUIRED = 30;
   public static final RegistryObject<AbilityCore<CoupDeVentAbility>> INSTANCE = ModRegistry.registerAbility("coup_de_vent", "Coup de Vent", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Launches a powerful blast of compressed air that blows the opponent away.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, CoupDeVentAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F), ColaUsageComponent.getColaTooltip(30)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.SHOCKWAVE).setUnlockCheck(CoupDeVentAbility::canUnlock).build("mineminenomi");
   });
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final ColaUsageComponent colaUsageComponent = new ColaUsageComponent(this);

   public CoupDeVentAbility(AbilityCore<CoupDeVentAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.projectileComponent, this.colaUsageComponent});
      this.addCanUseCheck(ColaUsageComponent.hasEnoughCola(30));
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
      if (props != null) {
         this.projectileComponent.shoot(entity, 3.0F, 1.0F);
         this.colaUsageComponent.consumeCola(entity, 30);
         this.cooldownComponent.startCooldown(entity, 240.0F);
      }
   }

   private CoupDeVentProjectile createProjectile(LivingEntity entity) {
      CoupDeVentProjectile proj = new CoupDeVentProjectile(entity.m_9236_(), entity, this);
      return proj;
   }

   private static boolean canUnlock(LivingEntity user) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(user).orElse((Object)null);
      return props == null ? false : props.isCyborg();
   }
}
