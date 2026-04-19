package xyz.pixelatedw.mineminenomi.abilities.ito;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.BlackKnightEntity;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BlackKnightAbility extends Ability {
   private static final int HOLD_TIME = 20000;
   private static final int MIN_COOLDOWN = 40;
   private static final int MAX_COOLDOWN = 6000;
   public static final RegistryObject<AbilityCore<BlackKnightAbility>> INSTANCE = ModRegistry.registerAbility("black_knight", "Black Knight", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates a clone of the user made entirely out of compressed strings", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BlackKnightAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(40.0F, 6000.0F), ContinuousComponent.getTooltip()).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(100, this::startContinuityEvent).addTickEvent(100, this::onTickEvent).addEndEvent(100, this::stopContinuityEvent);
   private BlackKnightEntity blackKnight = null;

   public BlackKnightAbility(AbilityCore<BlackKnightAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.blackKnight = new BlackKnightEntity(entity.m_9236_(), entity);
      this.blackKnight.m_7678_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity.m_146908_(), entity.m_146909_());

      for(EquipmentSlot slot : EquipmentSlot.values()) {
         ItemStack stack = entity.m_6844_(slot);
         this.blackKnight.m_8061_(slot, stack);
      }

      entity.m_9236_().m_7967_(this.blackKnight);
   }

   private void onTickEvent(LivingEntity entity, IAbility ability) {
      if (this.blackKnight == null || !this.blackKnight.m_6084_()) {
         this.continuousComponent.stopContinuity(entity);
      }
   }

   private void stopContinuityEvent(LivingEntity entity, IAbility ability) {
      if (this.blackKnight != null) {
         this.blackKnight.m_146870_();
      }

      float cooldown = Mth.m_14036_(this.continuousComponent.getContinueTime(), 40.0F, 6000.0F);
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   @Nullable
   public BlackKnightEntity getBlackKnight() {
      return this.blackKnight;
   }
}
