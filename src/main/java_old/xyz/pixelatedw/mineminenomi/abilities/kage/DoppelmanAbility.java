package xyz.pixelatedw.mineminenomi.abilities.kage;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.DoppelmanEntity;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class DoppelmanAbility extends Ability {
   private static final int HOLD_TIME = 12000;
   private static final int MIN_COOLDOWN = 40;
   private static final int MAX_COOLDOWN = 6000;
   public static final RegistryObject<AbilityCore<DoppelmanAbility>> INSTANCE = ModRegistry.registerAbility("doppelman", "Doppelman", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates a living version of the user's shadow to help them fight", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, DoppelmanAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(40.0F, 6000.0F), ContinuousComponent.getTooltip()).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(100, this::startContinuityEvent).addTickEvent(100, this::onTickEvent).addEndEvent(100, this::stopContinuityEvent);
   private final StackComponent stackComponent = new StackComponent(this);
   private DoppelmanEntity doppelman = null;
   private int shadowsUsed = 0;
   private int prevShadowsUsed = 0;

   public DoppelmanAbility(AbilityCore<DoppelmanAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.stackComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.doppelman = new DoppelmanEntity(entity.m_9236_(), entity);
      this.doppelman.m_7678_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity.m_146908_(), entity.m_146909_());
      if (!entity.m_9236_().f_46443_) {
         this.doppelman.setShadow(this.shadowsUsed);
         this.stackComponent.setStacks(entity, this, this.shadowsUsed);
      }

      entity.m_9236_().m_7967_(this.doppelman);
   }

   private void onTickEvent(LivingEntity entity, IAbility ability) {
      if (this.doppelman != null && this.doppelman.m_6084_()) {
         this.shadowsUsed = this.doppelman.getShadows();
         if (this.shadowsUsed != this.prevShadowsUsed) {
            this.stackComponent.setStacks(entity, this, this.shadowsUsed);
            this.prevShadowsUsed = this.shadowsUsed;
         }

      } else {
         this.continuousComponent.stopContinuity(entity);
      }
   }

   private void stopContinuityEvent(LivingEntity entity, IAbility ability) {
      if (this.doppelman != null) {
         this.doppelman.m_146870_();
      }

      this.prevShadowsUsed = 0;
      float cooldown = Mth.m_14036_(this.continuousComponent.getContinueTime(), 40.0F, 6000.0F);
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   @Nullable
   public DoppelmanEntity getDoppelman() {
      return this.doppelman;
   }

   public void doppelmanDeathTrigger(LivingEntity owner) {
      this.shadowsUsed = 0;
      this.stackComponent.setStacks(owner, this, 0);
   }

   public void saveAdditional(CompoundTag nbt) {
      nbt.m_128405_("shadows", this.shadowsUsed);
   }

   public void loadAdditional(CompoundTag nbt) {
      this.shadowsUsed = nbt.m_128451_("shadows");
   }
}
