package xyz.pixelatedw.mineminenomi.abilities.gura;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.BreakingBlocksParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class TenchiMeidoAbility extends Ability {
   private static final int COOLDOWN = 400;
   private static final int CHARGE_TIME = 20;
   private static final int RANGE = 26;
   public static final RegistryObject<AbilityCore<TenchiMeidoAbility>> INSTANCE = ModRegistry.registerAbility("tenchi_meido", "Tenchi Meido", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user grabs the air and pulls it downwards, after which all of the opponents are tossed into the air.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, TenchiMeidoAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(400.0F), ChargeComponent.getTooltip(20.0F), RangeComponent.getTooltip(26.0F, RangeComponent.RangeType.AOE)).setSourceElement(SourceElement.SHOCKWAVE).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addTickEvent(this::duringChargeEvent).addEndEvent(this::endChargeEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private BreakingBlocksParticleEffect.Details details = new BreakingBlocksParticleEffect.Details(40);

   public TenchiMeidoAbility(AbilityCore<TenchiMeidoAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.rangeComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresOnGround);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 20.0F);
   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      if (this.chargeComponent.getChargeTime() % 2.0F == 0.0F) {
         List<Vec3> positions = new ArrayList();
         int range = (int)Math.ceil((double)26.0F);

         for(int x = -range; x < range; ++x) {
            for(int z = -range; z < range; ++z) {
               if (entity.m_217043_().m_188503_(100) == 0) {
                  double posX = entity.m_20185_() + (double)x;
                  double posY = entity.m_20186_() - (double)1.0F;
                  double posZ = entity.m_20189_() + (double)z;
                  Vec3 pos = new Vec3(posX, posY, posZ);
                  positions.add(pos);
               }
            }
         }

         if (positions.size() > 0) {
            this.details.setVecPositions(positions);
         }

         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.BREAKING_BLOCKS.get(), entity, (double)0.0F, (double)0.0F, (double)0.0F, this.details);
      }

      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 10, 0));

      for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 26.0F)) {
         target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DIZZY.get(), 10, 0, false, false));
      }

   }

   private void endChargeEvent(LivingEntity player, IAbility ability) {
      if (!player.m_9236_().f_46443_) {
         for(LivingEntity target : this.rangeComponent.getTargetsInArea(player, 26.0F)) {
            Vec3 dirVec = player.m_20182_().m_82546_(target.m_20182_()).m_82541_().m_82542_((double)5.0F, (double)1.0F, (double)5.0F);
            AbilityHelper.setDeltaMovement(target, -dirVec.f_82479_, (double)3.0F, -dirVec.f_82481_);
         }

         this.cooldownComponent.startCooldown(player, 400.0F);
      }
   }
}
