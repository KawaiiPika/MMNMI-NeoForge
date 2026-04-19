package xyz.pixelatedw.mineminenomi.abilities.ito;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ito.StringPillarProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class FullbrightAbility extends Ability {
   private static final int COOLDOWN = 240;
   private static final int CHARGE_TIME = 20;
   public static final RegistryObject<AbilityCore<FullbrightAbility>> INSTANCE = ModRegistry.registerAbility("fullbright", "Fullbright", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Throws five strings to impale a target from above.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, FullbrightAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F), ChargeComponent.getTooltip(20.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceType(SourceType.SLASH).setSourceHakiNature(SourceHakiNature.IMBUING).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(100, this::startChargeEvent).addEndEvent(100, this::endChargeEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private LivingEntity target;

   public FullbrightAbility(AbilityCore<FullbrightAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.animationComponent, this.projectileComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 20.0F);
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.RAISE_RIGHT_ARM, 20);
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      HitResult mop = WyHelper.rayTraceBlocksAndEntities(entity, (double)32.0F);

      for(int a = 0; a < 5; ++a) {
         double x = (double)0.0F;
         double y = (double)0.0F;
         double z = (double)0.0F;
         if (mop == null) {
            return;
         }

         x = mop.m_82450_().f_82479_;
         y = mop.m_82450_().f_82480_;
         z = mop.m_82450_().f_82481_;
         double i = x + WyHelper.randomDouble() * (double)2.0F;
         double k = z + WyHelper.randomDouble() * (double)2.0F;
         StringPillarProjectile pillar = (StringPillarProjectile)this.projectileComponent.getNewProjectile(entity);
         pillar.m_6034_(i, y + (double)24.0F, k);
         AbilityHelper.setDeltaMovement(pillar, (double)0.0F, (double)-1.75F, (double)0.0F);
         entity.m_9236_().m_7967_(pillar);
      }

      this.cooldownComponent.startCooldown(entity, 240.0F);
   }

   private StringPillarProjectile createProjectile(LivingEntity entity) {
      StringPillarProjectile pillar = new StringPillarProjectile(entity.m_9236_(), entity, this);
      pillar.m_146926_(90.0F);
      return pillar;
   }

   public void setTarget(LivingEntity target) {
      this.target = target;
   }
}
