package xyz.pixelatedw.mineminenomi.abilities.yuki;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.effects.FrostbiteEffect;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class FubukiAbility extends Ability {
   private static final int COOLDOWN = 400;
   private static final int DAMAGE = 20;
   private static final int RANGE = 25;
   private static final int CHARGE_TIME = 100;
   private static final BlockProtectionRule GRIEF_RULE;
   public static final RegistryObject<AbilityCore<FubukiAbility>> INSTANCE;
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final ChargeComponent chargeComponent = (new ChargeComponent(this, true)).addStartEvent(this::onChargeStart).addTickEvent(this::onChargeTick).addEndEvent(this::onChargeEnd);
   private final Interval damageInterval = new Interval(20);
   private int multiplier = 1;

   public FubukiAbility(AbilityCore<FubukiAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.dealDamageComponent, this.rangeComponent, this.chargeComponent});
      super.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (this.chargeComponent.isCharging()) {
         this.chargeComponent.stopCharging(entity);
      } else {
         this.chargeComponent.startCharging(entity, 100.0F);
      }

   }

   public void onChargeStart(LivingEntity entity, IAbility ability) {
      this.damageInterval.restartIntervalToZero();
   }

   public void onChargeTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.damageInterval.canTick()) {
            int currentRange = 5 * this.multiplier;
            float currentDamage = 4.0F * (float)this.multiplier;
            SimpleBlockPlacer placer = (new SimpleBlockPlacer()).setBlock(Blocks.f_50125_.m_49966_()).setSize(currentRange).setRule(GRIEF_RULE);
            placer.generate(entity.m_9236_(), entity.m_20183_(), BlockGenerators.SPHERE);

            for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, (float)currentRange)) {
               FrostbiteEffect.addFrostbiteStacks(target, 5);
               if (target.m_21023_((MobEffect)ModEffects.FROSTBITE.get()) || target.m_21023_((MobEffect)ModEffects.FROZEN.get())) {
                  this.dealDamageComponent.hurtTarget(entity, target, currentDamage);
               }
            }

            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.FUBUKI.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
            ++this.multiplier;
         }

      }
   }

   public void onChargeEnd(LivingEntity entity, IAbility ability) {
      this.multiplier = 1;
      super.cooldownComponent.startCooldown(entity, 400.0F);
   }

   static {
      GRIEF_RULE = (new BlockProtectionRule.Builder(new BlockProtectionRule[]{DefaultProtectionRules.AIR_FOLIAGE})).addReplaceRules((world, pos, state) -> Block.m_49863_(world, pos.m_7495_(), Direction.UP) && world.m_8055_(pos.m_7495_()).m_60734_() != Blocks.f_50125_).build();
      INSTANCE = ModRegistry.registerAbility("fubuki", "Fubuki", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user releases an extremely cold stream around themselves inflicting %s and causing serious damage to those affected by it.", new Object[]{ModEffects.FROSTBITE}));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, FubukiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(400.0F), DealDamageComponent.getTooltip(20.0F), RangeComponent.getTooltip(25.0F, RangeComponent.RangeType.AOE)).setSourceHakiNature(SourceHakiNature.SPECIAL).build("mineminenomi");
      });
   }
}
