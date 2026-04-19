package xyz.pixelatedw.mineminenomi.abilities.suna;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.joml.Vector3d;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.suna.DesertGrandeEspadaProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DesertGrandeEspadaAbility extends Ability {
   private static final float COOLDOWN_BONUS = 0.8F;
   private static final float DAMAGE_BONUS = 1.15F;
   private static final int COOLDOWN = 400;
   private static final int CHARGE_TIME = 10;
   public static final RegistryObject<AbilityCore<DesertGrandeEspadaAbility>> INSTANCE = ModRegistry.registerAbility("desert_grande_espada", "Desert Grande Espada", (id, name) -> {
      Pair[] var10002 = new Pair[]{ImmutablePair.of("Spawns a large sand blade cutting through multiple enemies wherever the user is looking.", (Object)null), null};
      Object[] var10006 = new Object[]{"§a" + Math.round(19.999998F) + "%§r", null};
      float var10009 = Math.abs(-0.14999998F);
      var10006[1] = "§a" + Math.round(var10009 * 100.0F) + "%§r";
      var10002[1] = ImmutablePair.of("While in a desert the cooldown of this ability is reduced by %s and the damage is increased by %s.", var10006);
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, var10002);
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, DesertGrandeEspadaAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(400.0F), ChargeComponent.getTooltip(10.0F)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.SLASH).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addTickEvent(this::duringChargeEvent).addEndEvent(this::endChargeEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private Vector3d targetPos;

   public DesertGrandeEspadaAbility(AbilityCore<DesertGrandeEspadaAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.projectileComponent, this.chargeComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresDryUser);
      this.addCanUseCheck(AbilityUseConditions::requiresOnGround);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 10.0F);
   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      if (this.targetPos == null) {
         HitResult mop = WyHelper.rayTraceBlocksAndEntities(entity, (double)64.0F);
         double i = mop.m_82450_().f_82479_;
         double k = mop.m_82450_().f_82481_;
         int y = entity.m_9236_().m_6924_(Types.WORLD_SURFACE, (int)i, (int)k);
         this.targetPos = new Vector3d(i, (double)y, k);
      }

      if (!entity.m_9236_().f_46443_) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.SAND_BLADE_IDLE.get(), entity, this.targetPos.x, this.targetPos.y, this.targetPos.z);
      }

      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 2, 1, false, false));
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         DesertGrandeEspadaProjectile pillar = (DesertGrandeEspadaProjectile)this.projectileComponent.getNewProjectile(entity);
         pillar.m_7678_(this.targetPos.x, this.targetPos.y - (double)6.0F, this.targetPos.z, 0.0F, 0.0F);
         pillar.m_6686_((double)0.0F, 0.7, (double)0.0F, 1.4F, 0.0F);
         entity.m_9236_().m_7967_(pillar);
      }

      this.cooldownComponent.getBonusManager().removeBonus(SunaHelper.DESERT_COOLDOWN_BONUS);
      if (SunaHelper.isFruitBoosted(entity)) {
         this.cooldownComponent.getBonusManager().addBonus(SunaHelper.DESERT_COOLDOWN_BONUS, "Desert Cooldown Bonus", BonusOperation.MUL, 0.8F);
      }

      this.cooldownComponent.startCooldown(entity, 400.0F);
      this.targetPos = null;
   }

   private DesertGrandeEspadaProjectile createProjectile(LivingEntity entity) {
      boolean fruitBoosted = SunaHelper.isFruitBoosted(entity);
      this.projectileComponent.getDamageBonusManager().removeBonus(SunaHelper.DESERT_DAMAGE_BONUS);
      if (fruitBoosted) {
         this.projectileComponent.getDamageBonusManager().addBonus(SunaHelper.DESERT_DAMAGE_BONUS, "Desert Damage Bonus", BonusOperation.MUL, 1.15F);
      }

      DesertGrandeEspadaProjectile proj = new DesertGrandeEspadaProjectile(entity.m_9236_(), entity, this);
      return proj;
   }

   public void setTargetPos(Vector3d vec) {
      this.targetPos = vec;
   }
}
