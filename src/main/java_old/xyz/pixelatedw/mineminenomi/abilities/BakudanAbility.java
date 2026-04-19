package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.IntRange;
import xyz.pixelatedw.mineminenomi.entities.BombEntity;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BakudanAbility extends Ability {
   private static final int COOLDOWN = 200;
   private static final int RANGE = 15;
   public static final TargetPredicate TARGET_TEST = (new TargetPredicate()).testEnemyFaction().testSimpleInView();
   public static final RegistryObject<AbilityCore<BakudanAbility>> INSTANCE = ModRegistry.registerAbility("bakudan", "Bakudan", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Throws a bunch of bombs near the user, when enemies come in close proximity with these bombs they'll explode.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, BakudanAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), RangeComponent.getTooltip(15.0F, RangeComponent.RangeType.AOE)).build("mineminenomi");
   });
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private IntRange amount = new IntRange(5, 7);

   public BakudanAbility(AbilityCore<BakudanAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.animationComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.RAISE_RIGHT_ARM, 7);
      int amount = (int)WyHelper.randomWithRange(entity.m_217043_(), this.amount.getMin(), this.amount.getMax());

      for(int i = 0; i < amount; ++i) {
         BombEntity bomb = new BombEntity(entity.m_9236_(), entity);
         BlockPos pos = WyHelper.findValidGroundLocation((Entity)entity, (BlockPos)entity.m_20183_(), 15, 7);
         if (pos != null) {
            bomb.m_7678_((double)pos.m_123341_(), (double)((float)pos.m_123342_() + 1.5F), (double)pos.m_123343_(), (float)entity.m_217043_().m_188503_(360), 0.0F);
            entity.m_9236_().m_7967_(bomb);
         }
      }

      this.cooldownComponent.startCooldown(entity, 200.0F);
   }

   public void setAmount(int amount) {
      this.setAmount(amount, amount);
   }

   public void setAmount(int min, int max) {
      this.amount = new IntRange(min, max);
   }
}
