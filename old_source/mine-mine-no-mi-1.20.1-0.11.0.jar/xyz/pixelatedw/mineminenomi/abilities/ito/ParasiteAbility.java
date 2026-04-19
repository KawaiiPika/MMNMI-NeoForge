package xyz.pixelatedw.mineminenomi.abilities.ito;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.CombatHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.BlackKnightEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class ParasiteAbility extends Ability {
   private static final int COOLDOWN = 600;
   public static final RegistryObject<AbilityCore<ParasiteAbility>> INSTANCE = ModRegistry.registerAbility("parasite", "Parasite", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("By attaching your strings to nearby enemies, they get completely immobilized", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, ParasiteAbility::new)).addDescriptionLine(desc).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.PHYSICAL).build("mineminenomi");
   });
   private final RangeComponent rangeComponent = new RangeComponent(this);

   public ParasiteAbility(AbilityCore<ParasiteAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.rangeComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability2) {
      TorikagoAbility torikago = (TorikagoAbility)AbilityCapability.get(entity).map((props) -> (TorikagoAbility)props.getEquippedAbility((AbilityCore)TorikagoAbility.INSTANCE.get())).orElse((Object)null);
      if (torikago != null && torikago.isPositionInTorikago(entity.m_20183_())) {
         BlockPos centerPos = torikago.getCenter();

         for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, centerPos, 60.0F)) {
            if (torikago.isPositionInTorikago(target.m_20183_())) {
               inflictParasiteStun(entity, target);
            }
         }
      } else {
         EntityHitResult trace = WyHelper.rayTraceEntities(entity, (double)32.0F);
         if (trace.m_6662_().equals(Type.ENTITY) && trace.m_82443_() instanceof LivingEntity) {
            LivingEntity target = (LivingEntity)trace.m_82443_();
            inflictParasiteStun(entity, target);
         }
      }

      this.cooldownComponent.startCooldown(entity, 600.0F);
   }

   public static void inflictParasiteStun(LivingEntity attacker, LivingEntity target) {
      if (!(target instanceof BlackKnightEntity)) {
         if (!CombatHelper.isTargetBlocking(attacker, target)) {
            double attackerDoriki = (Double)EntityStatsCapability.get(attacker).map((props) -> props.getDoriki()).orElse((double)0.0F);
            double targetDoriki = (Double)EntityStatsCapability.get(target).map((props) -> props.getDoriki()).orElse((double)0.0F);
            if (!(attackerDoriki < targetDoriki)) {
               if (attackerDoriki == targetDoriki) {
                  target.m_7292_(new MobEffectInstance(MobEffects.f_19610_, 100, 0, false, false));
                  target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 100, 2, false, false));
               } else {
                  target.m_7292_(new MobEffectInstance(MobEffects.f_19610_, 300, 0, false, false));
                  target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 300, 0, false, false));
               }

            }
         }
      }
   }
}
