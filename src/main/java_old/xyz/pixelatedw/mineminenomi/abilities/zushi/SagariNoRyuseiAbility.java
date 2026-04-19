package xyz.pixelatedw.mineminenomi.abilities.zushi;

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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.zushi.SagariNoRyuseiProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SagariNoRyuseiAbility extends Ability {
   private static final int COOLDOWN = 900;
   public static final RegistryObject<AbilityCore<SagariNoRyuseiAbility>> INSTANCE = ModRegistry.registerAbility("sagari_no_ryusei", "Sagari no Ryusei", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Using gravity, the user pulls one (or rarely two) meteorites down on their enemies.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SagariNoRyuseiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(900.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = new ContinuousComponent(this);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this, 13824);
   private int count;

   public SagariNoRyuseiAbility(AbilityCore<SagariNoRyuseiAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.projectileComponent, this.animationComponent, this.explosionComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.count = 0;
      this.animationComponent.start(entity, ModAnimations.RAISE_RIGHT_ARM, 7);

      for(int i = 2; i < 16; i += 2) {
         GraviZoneAbility.gravityRing(entity, 4, i, false);
      }

      boolean has2nd = entity.m_217043_().m_188503_(3) == 0;
      SagariNoRyuseiProjectile proj1 = (SagariNoRyuseiProjectile)this.projectileComponent.getNewProjectile(entity);
      entity.m_9236_().m_7967_(proj1);
      if (has2nd) {
         ++this.count;
         SagariNoRyuseiProjectile proj2 = (SagariNoRyuseiProjectile)this.projectileComponent.getNewProjectile(entity);
         entity.m_9236_().m_7967_(proj2);
      }

      this.cooldownComponent.startCooldown(entity, 900.0F);
   }

   private SagariNoRyuseiProjectile createProjectile(LivingEntity entity) {
      HitResult mop = WyHelper.rayTraceBlocksAndEntities(entity);
      double x = mop.m_82450_().f_82479_;
      double y = mop.m_82450_().f_82480_;
      double z = mop.m_82450_().f_82481_;
      float size = this.count == 0 ? (float)WyHelper.randomWithRange(24, 30) : (float)WyHelper.randomWithRange(8, 10);
      SagariNoRyuseiProjectile proj = new SagariNoRyuseiProjectile(entity.m_9236_(), entity, this);
      proj.setSize(size);
      proj.m_6034_(x, y + (double)90.0F, z);
      proj.m_146926_(0.0F);
      proj.m_146922_(0.0F);
      AbilityHelper.setDeltaMovement(proj, (double)0.0F, -1.85, (double)0.0F);
      return proj;
   }
}
