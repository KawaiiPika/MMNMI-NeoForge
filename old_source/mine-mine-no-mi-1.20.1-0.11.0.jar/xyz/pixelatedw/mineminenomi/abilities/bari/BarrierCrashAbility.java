package xyz.pixelatedw.mineminenomi.abilities.bari;

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
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bari.BarrierCrashProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BarrierCrashAbility extends Ability {
   private static final int COOLDOWN = 160;
   public static final RegistryObject<AbilityCore<BarrierCrashAbility>> INSTANCE = ModRegistry.registerAbility("barrier_crash", "Barrier Crash", (id, name) -> {
      Component[] DESCRIPTION = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Launches a barrier towards the opponent, smashing it against them.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BarrierCrashAbility::new)).addDescriptionLine(DESCRIPTION).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(160.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.BLUNT).build("mineminenomi");
   });
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);

   public BarrierCrashAbility(AbilityCore<BarrierCrashAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.projectileComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity, 2.0F, 1.0F);
      this.cooldownComponent.startCooldown(entity, 160.0F);
   }

   private BarrierCrashProjectile createProjectile(LivingEntity entity) {
      BarrierCrashProjectile proj = new BarrierCrashProjectile(entity.m_9236_(), entity, this);
      proj.m_6034_(proj.m_20185_(), proj.m_20186_() + (double)1.5F, proj.m_20189_());
      return proj;
   }
}
