package xyz.pixelatedw.mineminenomi.abilities.hitodaibutsu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hitodaibutsu.ImpactBlastProjectile;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class ImpactBlastAbility extends PunchAbility {
   private static final int COOLDOWN = 120;
   public static final RegistryObject<AbilityCore<ImpactBlastAbility>> INSTANCE = ModRegistry.registerAbility("impact_blast", "Impact Blast", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Launches a golden shock wave forward when punching an enemy or the air, hurting every entity in its path.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, ImpactBlastAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.DAIBUTSU)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(120.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceType(SourceType.FIST).setSourceElement(SourceElement.SHOCKWAVE).build("mineminenomi");
   });
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this, 12288);

   public ImpactBlastAbility(AbilityCore<ImpactBlastAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.projectileComponent, this.explosionComponent});
      this.addCanUseCheck((ent, abl) -> AbilityUseConditions.requiresMorph(ent, abl, (MorphInfo)ModMorphs.DAIBUTSU.get()));
      this.continuousComponent.addTickEvent(100, this::tickContinuityEvent);
   }

   private ImpactBlastProjectile createProjectile(LivingEntity entity) {
      ImpactBlastProjectile proj = new ImpactBlastProjectile(entity.m_9236_(), entity, this);
      return proj;
   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_ && entity.f_20911_) {
         this.projectileComponent.shoot(entity, 2.0F, 0.0F);
         this.continuousComponent.stopContinuity(entity);
      }

   }

   public float getPunchCooldown() {
      return 120.0F;
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      this.projectileComponent.shoot(entity, 3.0F, 0.0F);
      return true;
   }

   public int getUseLimit() {
      return 1;
   }
}
