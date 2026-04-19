package xyz.pixelatedw.mineminenomi.abilities.hasshoken;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.rokushiki.RokuoganProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KiryuKirikugiAbility extends PunchAbility {
   private static final float COOLDOWN = 700.0F;
   private static final int DORIKI = 10000;
   private static final int MARTIAL_ARTS_POINTS = 50;
   public static final RegistryObject<AbilityCore<KiryuKirikugiAbility>> INSTANCE = ModRegistry.registerAbility("kiryu_kirikugi", "Kiryu Kirikugi", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user focuses an intense amount of their body's vibrations into their next attack to enhance the damage.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, KiryuKirikugiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(700.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceElement(SourceElement.SHOCKWAVE).setSourceType(SourceType.FIST, SourceType.INTERNAL).setNodeFactories(KiryuKirikugiAbility::createNode).setUnlockCheck(KiryuKirikugiAbility::canUnlock).build("mineminenomi");
   });
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);
   private final PoolComponent poolComponent;

   public KiryuKirikugiAbility(AbilityCore<KiryuKirikugiAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.KIRYU_AND_MUKIRYU, new AbilityPool[0]);
      super.addComponents(new AbilityComponent[]{this.projectileComponent, this.explosionComponent, this.poolComponent});
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      IDamageSourceHandler sourceHandler = IDamageSourceHandler.getHandler(source);
      sourceHandler.bypassLogia();
      sourceHandler.addAbilityPiercing(1.0F, this.getCore());
      this.projectileComponent.shoot(entity);
      return true;
   }

   public int getUseLimit() {
      return 1;
   }

   public float getPunchDamage() {
      return 60.0F;
   }

   public float getPunchCooldown() {
      return 700.0F;
   }

   public Result canTriggerHit(LivingEntity entity) {
      return Result.success();
   }

   private RokuoganProjectile createProjectile(LivingEntity entity) {
      RokuoganProjectile proj = new RokuoganProjectile(entity.m_9236_(), entity, this);
      proj.setLife(20);
      proj.addBlockHitEvent(100, (result) -> {
         BlockPos hit = result.m_82425_();
         ExplosionComponent.createExplosion((NuProjectileEntity)proj, (comp) -> {
            AbilityExplosion explosion = comp.createExplosion(proj, entity, (double)hit.m_123341_(), (double)hit.m_123342_(), (double)hit.m_123343_(), 8.0F);
            explosion.setStaticDamage(5.0F);
            explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), new CommonExplosionParticleEffect.Details((double)8.0F));
            explosion.m_46061_();
         });
      });
      return proj;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode kiryuKirikugi = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-19.0F, 3.0F));
      kiryuKirikugi.addPrerequisites(((AbilityCore)MukiryuMukirikugiAbility.INSTANCE.get()).getNode(entity));
      NodeUnlockCondition unlockCondition = DorikiUnlockCondition.atLeast((double)10000.0F).and(TrainingPointsUnlockCondition.martialArts((double)50.0F));
      NodeUnlockAction unlockAction = SubtractPointsAction.martialArts(50).andThen(UnlockAbilityAction.unlock(INSTANCE));
      kiryuKirikugi.setUnlockRule(unlockCondition, unlockAction);
      return kiryuKirikugi;
   }

   private static boolean canUnlock(LivingEntity entity) {
      return ((AbilityCore)INSTANCE.get()).getNode(entity).isUnlocked(entity);
   }
}
