package xyz.pixelatedw.mineminenomi.abilities.suna;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.entities.TornadoEntity;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SablesAbility extends Ability {
   private static final float COOLDOWN_BONUS = 0.8F;
   private static final int COOLDOWN = 360;
   private static final int HOLD_TIME = 120;
   public static final RegistryObject<AbilityCore<SablesAbility>> INSTANCE = ModRegistry.registerAbility("sables", "Sables", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user launches a compressed sandstorm at the opponent, which sends them flying.", (Object)null), ImmutablePair.of("While in a desert the cooldown of this ability is reduced by %s.", new Object[]{"§a" + Math.round(19.999998F) + "%§r"}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SablesAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(360.0F), ContinuousComponent.getTooltip(120.0F)).build("mineminenomi");
   });
   private final ContinuousComponent continuityComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private TornadoEntity proj = null;

   public SablesAbility(AbilityCore<SablesAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuityComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresDryUser);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuityComponent.triggerContinuity(entity, 120.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.proj = null;
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      HitResult mop = WyHelper.rayTraceBlocksAndEntities(entity, (double)100.0F);
      if (this.proj == null) {
         this.proj = new TornadoEntity(entity.m_9236_(), entity);
         this.proj.setMaxLife(300);
         this.proj.setSize(30.0F);
         this.proj.setSpeed(-2.0F);
         this.proj.m_6034_(mop.m_82450_().f_82479_, mop.m_82450_().f_82480_, mop.m_82450_().f_82481_);
         entity.m_9236_().m_7967_(this.proj);
      }

      if (this.proj.m_6084_() && this.proj != null) {
         double distance = Math.sqrt(this.proj.m_20238_(mop.m_82450_()));
         if (mop.m_6662_().equals(Type.BLOCK) && distance < (double)100.0F) {
            this.proj.setVector(mop.m_82450_().m_82520_((double)0.0F, (double)10.0F, (double)0.0F));
         }

      } else {
         this.continuityComponent.stopContinuity(entity);
      }
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      if (this.proj != null && this.proj.m_6084_()) {
         this.proj.m_146870_();
      }

      this.cooldownComponent.getBonusManager().removeBonus(SunaHelper.DESERT_COOLDOWN_BONUS);
      if (SunaHelper.isFruitBoosted(entity)) {
         this.cooldownComponent.getBonusManager().addBonus(SunaHelper.DESERT_COOLDOWN_BONUS, "Desert Cooldown Bonus", BonusOperation.MUL, 0.8F);
      }

      this.cooldownComponent.startCooldown(entity, 360.0F);
   }
}
