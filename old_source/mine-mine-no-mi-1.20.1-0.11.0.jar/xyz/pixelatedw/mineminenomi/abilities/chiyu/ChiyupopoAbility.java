package xyz.pixelatedw.mineminenomi.abilities.chiyu;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ChiyupopoAbility extends Ability {
   private static final float COOLDOWN = 16800.0F;
   private static final float RANGE = 15.0F;
   public static final RegistryObject<AbilityCore<ChiyupopoAbility>> INSTANCE = ModRegistry.registerAbility("chiyupopo", "Chiyupopo", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Releases dandelions made of tears that temporarily increase the healing rate of those around the user. This can only be applied once per person.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, ChiyupopoAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(16800.0F), RangeComponent.getTooltip(15.0F, RangeComponent.RangeType.AOE)).build("mineminenomi");
   });
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private TargetPredicate predicate;

   public ChiyupopoAbility(AbilityCore<ChiyupopoAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.rangeComponent});
      super.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity player, IAbility ability) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
      if (props != null) {
         props.setChiyuEffect(false);
         if (this.predicate == null) {
            this.predicate = (new TargetPredicate()).testFriendlyFaction().selector((entityx) -> {
               boolean var10000;
               if (entityx instanceof LivingEntity living) {
                  if (!(Boolean)EntityStatsCapability.get(living).map((entityStats) -> entityStats.hadChiyuEffect()).orElse(false)) {
                     var10000 = true;
                     return var10000;
                  }
               }

               var10000 = false;
               return var10000;
            });
         }

         List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(player, 15.0F, this.predicate);
         if (ModEntityPredicates.getFriendlyFactions(player).test(player) && !props.hadChiyuEffect()) {
            targets.add(player);
         }

         for(LivingEntity entity : targets) {
            if (entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.CHIYUPOPO.get(), 4800, 5))) {
               EntityStatsCapability.get(entity).ifPresent((entityStats) -> entityStats.setChiyuEffect(true));
            }
         }

         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.CHIYUPOPO.get(), player, player.m_20185_(), player.m_20186_(), player.m_20189_());
         super.cooldownComponent.startCooldown(player, 16800.0F);
      }
   }
}
