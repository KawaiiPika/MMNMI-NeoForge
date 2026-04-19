package xyz.pixelatedw.mineminenomi.abilities.doru;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.GuardAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class DoruDoruBallAbility extends GuardAbility {
   private static final float HOLD_TIME = 200.0F;
   private static final float MIN_COOLDOWN = 30.0F;
   private static final float MAX_COOLDOWN = 230.0F;
   private static final GuardAbility.GuardValue STANDARD_GUARD_VALUE;
   private static final GuardAbility.GuardValue CHAMPION_GUARD_VALUE;
   public static final RegistryObject<AbilityCore<DoruDoruBallAbility>> INSTANCE;
   private final PoolComponent poolComponent;
   public double rotateAngleX;
   public double rotateAngleZ;
   public float[] color;
   private boolean canChanceColor;
   private GuardAbility.GuardValue guardValue;

   public DoruDoruBallAbility(AbilityCore<DoruDoruBallAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.GUARD_ABILITY, new AbilityPool[0]);
      this.rotateAngleX = (double)0.0F;
      this.rotateAngleZ = (double)0.0F;
      this.color = new float[3];
      this.addComponents(new AbilityComponent[]{this.poolComponent});
      this.continuousComponent.addStartEvent(100, this::startContinuityEvent).addTickEvent(100, this::tickContinuityEvent).addEndEvent(100, this::endContinuityEvent);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.rotateAngleX = (double)0.0F;
      this.rotateAngleZ = (double)0.0F;
      this.canChanceColor = true;
   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity.m_6060_()) {
         this.continuousComponent.stopContinuity(entity);
      } else {
         entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 20, 5, true, false));
         entity.m_7292_(new MobEffectInstance(MobEffects.f_19599_, 20, 5, true, false));
         boolean isChampionActive = (Boolean)AbilityCapability.get(entity).map((props) -> (CandleChampionAbility)props.getEquippedAbility((AbilityCore)CandleChampionAbility.INSTANCE.get())).map((abl) -> abl.isContinuous()).orElse(false);
         this.guardValue = STANDARD_GUARD_VALUE;
         if (isChampionActive) {
            this.guardValue = CHAMPION_GUARD_VALUE;
         }

      }
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.rotateAngleX = (double)0.0F;
      this.rotateAngleZ = (double)0.0F;
      entity.m_21195_(MobEffects.f_19597_);
      entity.m_21195_(MobEffects.f_19599_);
      float cooldown = 30.0F + this.continuousComponent.getContinueTime();
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   public float getHoldTime() {
      return 200.0F;
   }

   public void onGuard(LivingEntity entity, DamageSource source, float originalDamage, float newDamage) {
   }

   public GuardAbility.GuardValue getGuard(LivingEntity entity) {
      return this.guardValue;
   }

   public void randomizeColor(LivingEntity entity) {
      if (this.canChanceColor) {
         this.color[0] = 1.0F;
         this.color[1] = 1.0F;
         this.color[2] = 1.0F;
         if (ItemsHelper.countItemInInventory(entity, (Item)ModItems.COLOR_PALETTE.get()) > 0) {
            this.color[0] = entity.m_217043_().m_188501_();
            this.color[1] = entity.m_217043_().m_188501_();
            this.color[2] = entity.m_217043_().m_188501_();
         }

         this.canChanceColor = false;
      }
   }

   static {
      STANDARD_GUARD_VALUE = GuardAbility.GuardValue.percentage(0.15F, GuardAbility.GuardBreakKind.TOTAL, 200.0F);
      CHAMPION_GUARD_VALUE = GuardAbility.GuardValue.percentage(0.25F, GuardAbility.GuardBreakKind.TOTAL, 400.0F);
      INSTANCE = ModRegistry.registerAbility("doru_doru_ball", "Doru Doru Ball", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Puts the user into a hardened wax ball for max defense", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, DoruDoruBallAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(30.0F, 230.0F), ContinuousComponent.getTooltip(200.0F)).build("mineminenomi");
      });
   }
}
