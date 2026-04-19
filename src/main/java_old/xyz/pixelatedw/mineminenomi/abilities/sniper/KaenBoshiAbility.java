package xyz.pixelatedw.mineminenomi.abilities.sniper;

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
import xyz.pixelatedw.mineminenomi.api.abilities.components.BowTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper.HiNoToriBoshiProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KaenBoshiAbility extends Ability {
   private static final float COOLDOWN = 100.0F;
   public static final RegistryObject<AbilityCore<KaenBoshiAbility>> INSTANCE = ModRegistry.registerAbility("kaen_boshi", "Kaen Boshi", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Fires a flaming pellet that sets the target on fire", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, KaenBoshiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F), ContinuousComponent.getTooltip()).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.FIRE).setSourceType(SourceType.BULLET).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = new ContinuousComponent(this);
   private final BowTriggerComponent bowTriggerComponent = (new BowTriggerComponent(this)).addShootEvent(this::shoot);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);

   public KaenBoshiAbility(AbilityCore<KaenBoshiAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.bowTriggerComponent, this.projectileComponent});
      this.setDisplayIcon((AbilityCore)HiNoToriBoshiAbility.INSTANCE.get());
      this.addUseEvent(this::onUseEvent);
   }

   public void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   public boolean shoot(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         boolean isHitScan = HissatsuAbility.checkHitScan(entity);
         this.projectileComponent.setHitScan(isHitScan);
         this.projectileComponent.shoot(entity, 4.0F, 1.0F);
         this.continuousComponent.stopContinuity(entity);
         this.cooldownComponent.startCooldown(entity, 100.0F);
         return true;
      } else {
         return false;
      }
   }

   public HiNoToriBoshiProjectile createProjectile(LivingEntity entity) {
      HiNoToriBoshiProjectile proj = new HiNoToriBoshiProjectile(entity.m_9236_(), entity, this);
      proj.m_6034_(entity.m_20185_(), entity.m_20186_() + (double)entity.m_20192_(), entity.m_20189_());
      proj.setDamage(10.0F);
      return proj;
   }
}
