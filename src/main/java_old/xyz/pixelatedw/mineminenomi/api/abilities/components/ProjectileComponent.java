package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuLightningEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class ProjectileComponent extends AbilityComponent<IAbility> {
   private static final UUID PROJ_DAMAGE_BONUS_MANAGER_UUID = UUID.fromString("2ab0d674-f9ca-4c11-976a-c8a105332318");
   private static final UUID PROJ_INACCURACY_BONUS_MANAGER_UUID = UUID.fromString("2ad65e4e-5edf-4c95-a826-d1f7bba33c23");
   public static final Predicate<Entity> TARGET_CHECK = (target) -> target != null && target.m_6084_() && target instanceof LivingEntity;
   private final IProjectileFactory<?> factory;
   private NuProjectileEntity cachedProjectile;
   private NuProjectileEntity projectileShot;
   private boolean isHitScan;
   private final PriorityEventPool<IAfterShootEvent> afterShootEvents = new PriorityEventPool<IAfterShootEvent>();
   private final BonusManager damageBonusManager;
   private final BonusManager inaccuracyBonusManager;

   public static AbilityDescriptionLine.IDescriptionLine[] getProjectileTooltips() {
      AbilityDescriptionLine.IDescriptionLine[] list = new AbilityDescriptionLine.IDescriptionLine[3];
      list[0] = (entity, ability) -> Component.m_237113_("§a" + ModI18nAbilities.DESCRIPTION_STAT_NAME_PROJECTILE.getString() + "§r");
      list[1] = getDamageFromProjectileTooltip();
      list[2] = getPiercingFromProjectileTooltip();
      return list;
   }

   private static AbilityDescriptionLine.IDescriptionLine getDamageFromProjectileTooltip() {
      return (entity, ability) -> {
         NuProjectileEntity proj = (NuProjectileEntity)ability.getComponent((AbilityComponentKey)ModAbilityComponents.PROJECTILE.get()).map((comp) -> comp.getCachedProjectile(entity)).orElse((Object)null);
         if (proj != null && proj.getDamage() > 0.0F) {
            float bonus = (Float)ability.getComponent((AbilityComponentKey)ModAbilityComponents.PROJECTILE.get()).map(ProjectileComponent::getDamageBonusManager).map((manager) -> manager.applyBonus(proj.getDamage()) - proj.getDamage()).orElse(0.0F);
            AbilityStat.AbilityStatType bonusType = bonus > 0.0F ? AbilityStat.AbilityStatType.BUFF : (bonus < 0.0F ? AbilityStat.AbilityStatType.DEBUFF : AbilityStat.AbilityStatType.NEUTRAL);
            AbilityStat.Builder statBuilder = (new AbilityStat.Builder(ModI18nAbilities.DESCRIPTION_STAT_NAME_DAMAGE, proj.getDamage())).withBonus(bonus, bonusType);
            return statBuilder.build().getStatDescription(2);
         } else {
            return null;
         }
      };
   }

   private static AbilityDescriptionLine.IDescriptionLine getPiercingFromProjectileTooltip() {
      return (e, a) -> null;
   }

   public <P extends NuProjectileEntity> ProjectileComponent(IAbility ability, IProjectileFactory<P> projectileFactory) {
      super((AbilityComponentKey)ModAbilityComponents.PROJECTILE.get(), ability);
      this.damageBonusManager = new BonusManager(PROJ_DAMAGE_BONUS_MANAGER_UUID);
      this.inaccuracyBonusManager = new BonusManager(PROJ_INACCURACY_BONUS_MANAGER_UUID);
      this.factory = projectileFactory;
      this.addBonusManager(this.damageBonusManager);
      this.addBonusManager(this.inaccuracyBonusManager);
   }

   public ProjectileComponent addAfterShootEvent(int priority, IAfterShootEvent event) {
      this.afterShootEvents.addEvent(priority, event);
      return this;
   }

   public void shoot(LivingEntity entity) {
      this.shoot(entity, 2.0F, 1.0F);
   }

   public void shoot(LivingEntity entity, float speed, float inaccuracy) {
      NuProjectileEntity proj = this.getNewProjectile(entity);
      this.shoot(proj, entity, entity.m_146909_(), entity.m_146908_(), speed, inaccuracy);
   }

   public void shootWithSpread(LivingEntity entity, float speed, float inaccuracy, int spread) {
      NuProjectileEntity proj = this.getNewProjectile(entity);
      double px = entity.m_20185_() + WyHelper.randomWithRange(-spread, spread) + WyHelper.randomDouble();
      double py = entity.m_20188_() + WyHelper.randomWithRange(0, spread) + WyHelper.randomDouble();
      double pz = entity.m_20189_() + WyHelper.randomWithRange(-spread, spread) + WyHelper.randomDouble();
      proj.m_7678_(px, py, pz, entity.m_146908_(), entity.m_146909_());
      this.shoot(proj, entity, entity.m_146909_(), entity.m_146908_(), speed, inaccuracy);
   }

   public void shoot(NuProjectileEntity projectile, LivingEntity entity, float xRot, float yRot, float speed, float inaccuracy) {
      this.ensureIsRegistered();
      projectile.m_5602_(entity);
      if (this.isHitScan) {
         float distance = (float)projectile.getMaxLife();
         EntityHitResult result = WyHelper.rayTraceEntities(entity, (double)distance, (double)projectile.m_20205_(), TARGET_CHECK);
         if (result.m_82443_() != null && result.m_82443_() instanceof LivingEntity) {
            projectile.m_6532_(result);
         } else if (result.m_82443_() == null) {
            BlockHitResult blockResult = WyHelper.rayTraceBlocks(entity, (double)distance);
            projectile.m_6034_(blockResult.m_82450_().f_82479_, result.m_82450_().f_82480_, result.m_82450_().f_82481_);
            projectile.m_6532_(blockResult);
         }
      } else {
         inaccuracy = ItemsHelper.getSniperInaccuracy(inaccuracy, entity);
         inaccuracy = this.inaccuracyBonusManager.applyBonus(inaccuracy);
         this.projectileShot = projectile;
         if (projectile instanceof NuLightningEntity) {
            NuLightningEntity beamProjectile = (NuLightningEntity)projectile;
         } else {
            label34: {
               if (entity instanceof Mob) {
                  Mob mob = (Mob)entity;
                  if (mob.m_5448_() != null) {
                     double x = mob.m_5448_().m_20185_() - entity.m_20185_();
                     double y = mob.m_5448_().m_20227_(0.3) - projectile.m_20186_();
                     double z = mob.m_5448_().m_20189_() - entity.m_20189_();
                     double d = Math.sqrt(x * x + z * z);
                     projectile.m_6686_(x, y + d * 0.05, z, 2.5F, 0.0F);
                     break label34;
                  }
               }

               projectile.m_37251_(entity, entity.m_146909_(), entity.m_146908_(), 0.0F, speed, inaccuracy);
            }
         }

         entity.m_9236_().m_7967_(projectile);
      }

      this.afterShootEvents.dispatch((event) -> event.triggerAfterEvent(entity));
   }

   public @Nullable NuProjectileEntity getShotProjectile() {
      return this.projectileShot;
   }

   public boolean hasProjectileAlive() {
      return this.projectileShot != null && this.projectileShot.m_6084_();
   }

   public <P extends NuProjectileEntity> P getNewProjectile(LivingEntity entity) {
      P proj = this.factory.createProjectile(entity);
      proj.setDamage(this.damageBonusManager.applyBonus(proj.getDamage()));
      return proj;
   }

   public BonusManager getDamageBonusManager() {
      return this.damageBonusManager;
   }

   public BonusManager getInaccuracyBonusManager() {
      return this.inaccuracyBonusManager;
   }

   public NuProjectileEntity getCachedProjectile(LivingEntity entity) {
      if (this.cachedProjectile == null) {
         this.cachedProjectile = this.factory.createProjectile(entity);
      }

      return this.cachedProjectile;
   }

   public void setHitScan(boolean flag) {
      this.isHitScan = flag;
   }

   public Component getDamageTooltip(float damage) {
      float damageBonus = this.damageBonusManager.applyBonus(damage) - damage;
      AbilityStat.AbilityStatType damageBonusType = AbilityStat.AbilityStatType.from(damageBonus, false);
      AbilityStat.Builder builder = (new AbilityStat.Builder(ModI18nAbilities.DESCRIPTION_STAT_NAME_DAMAGE, damage)).withBonus(damageBonus, damageBonusType);
      return builder.buildComponent();
   }

   @FunctionalInterface
   public interface IAfterShootEvent {
      void triggerAfterEvent(LivingEntity var1);
   }

   @FunctionalInterface
   public interface IProjectileFactory<P extends NuProjectileEntity> {
      P createProjectile(LivingEntity var1);
   }
}
