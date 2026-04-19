package xyz.pixelatedw.mineminenomi.abilities.yomi;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.OutOfBodyAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.entities.PhysicalBodyEntity;
import xyz.pixelatedw.mineminenomi.init.ModDamageTypes;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class YomiNoReikiAbility extends OutOfBodyAbility {
   private static final int HOLD_TIME = 4000;
   private static final int MIN_COOLDOWN = 100;
   private static final int MAX_COOLDOWN = 2000;
   public static final RegistryObject<AbilityCore<YomiNoReikiAbility>> INSTANCE = ModRegistry.registerAbility("yomi_no_reiki", "Yomi no Reiki", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user's spirit leaves their body, allowing them to freely explore the nearby areas.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, YomiNoReikiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F, 2000.0F), ContinuousComponent.getTooltip(4000.0F)).setUnlockCheck(YomiNoReikiAbility::canUnlock).build("mineminenomi");
   });
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::damageTakenEvent);

   public YomiNoReikiAbility(AbilityCore<YomiNoReikiAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.damageTakenComponent});
      this.continuousComponent.addStartEvent(this::startContinuityEvent);
      this.continuousComponent.addEndEvent(this::endContinuityEvent);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      AbilityHelper.setDeltaMovement(entity, (double)0.0F, (double)5.0F, (double)0.0F);
      PhysicalBodyEntity body = new PhysicalBodyEntity(entity.m_9236_());
      body.m_7678_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity.m_146908_(), entity.m_146909_());
      body.setOwner(entity);
      entity.m_9236_().m_7967_(body);
      body.setParentAbility(this);
      this.setOriginalBody(body);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      float cooldown = Math.max(100.0F, this.continuousComponent.getContinueTime() / 2.0F);
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   public float damageTakenEvent(LivingEntity user, IAbility ability, DamageSource damageSource, float damage) {
      return this.isActive() && !damageSource.m_276093_(ModDamageTypes.OUT_OF_BODY) ? 0.0F : damage;
   }

   public int getHoldTime() {
      return 4000;
   }

   public float getMaxRange() {
      return 100.0F;
   }

   public boolean isPhysical() {
      return false;
   }

   private static boolean canUnlock(LivingEntity entity) {
      return (Boolean)DevilFruitCapability.get(entity).map((props) -> props.hasYomiPower()).orElse(false);
   }
}
