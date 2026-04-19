package xyz.pixelatedw.mineminenomi.abilities.saraaxolotl;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.player.Player;
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
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class AxolotlHealAbility extends Ability {
   private static final int COOLDOWN = 300;
   private static final float RANGE = 15.0F;
   private static final TargetPredicate TARGET_PREDICATE = (new TargetPredicate()).testFriendlyFaction().selector((target) -> {
      if (target instanceof Player player) {
         if (DevilFruitCapability.hasDevilFruit(player, ModFruits.SARA_SARA_NO_MI_AXOLOTL)) {
            return true;
         }
      }

      return target instanceof Axolotl;
   });
   public static final RegistryObject<AbilityCore<AxolotlHealAbility>> INSTANCE = ModRegistry.registerAbility("axolotl_heal", "Axolotl Heal", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Regenerates all friendlies nearby based on how many other Axolotls are around.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AxolotlHealAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.AXOLOTL_HEAVY, ModMorphs.AXOLOTL_WALK)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F), RangeComponent.getTooltip(15.0F, RangeComponent.RangeType.AOE)).build("mineminenomi");
   });
   private final RangeComponent rangeComponent = new RangeComponent(this);

   public AxolotlHealAbility(AbilityCore<AxolotlHealAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.rangeComponent});
      this.addCanUseCheck(SaraAxolotlHelper::requiresEitherPoint);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, 15.0F, TARGET_PREDICATE);
      int axolots = targets.size();
      axolots = Math.min(axolots, 3);
      int time = 100 + Math.min(axolots * 100, 300);
      targets.add(entity);

      for(LivingEntity target : targets) {
         target.m_7292_(new MobEffectInstance(MobEffects.f_19605_, time, axolots));
      }

      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.CHIYUPOPO.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      this.cooldownComponent.startCooldown(entity, 300.0F);
   }
}
