package xyz.pixelatedw.mineminenomi.abilities.kage;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.NightmareSoldierEntity;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class NightmareSoldiersAbility extends Ability {
   private static final int CHARGE_TIME = 200;
   private static final int MIN_COOLDOWN = 100;
   private static final int MAX_COOLDOWN = 300;
   public static final RegistryObject<AbilityCore<NightmareSoldiersAbility>> INSTANCE = ModRegistry.registerAbility("nightmare_soldiers", "Nightmare Soldiers", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates Nightmare Soldiers using Shadows from the user's inventory, the longer the ability charges the more soldiers it'll create", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, NightmareSoldiersAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F, 300.0F), ChargeComponent.getTooltip(200.0F)).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this, (comp) -> this.getShadowsUsed() > 0)).addTickEvent(100, this::duringChargingEvent).addEndEvent(100, this::stopChargingEvent);
   private final StackComponent stackComponent = new StackComponent(this);
   private int prevShadowValue;

   public NightmareSoldiersAbility(AbilityCore<NightmareSoldiersAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.stackComponent});
      this.addCanUseCheck((entity, abl) -> KageHelper.hasEnoughShadows(entity, abl, 1));
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 200.0F);
   }

   private void duringChargingEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_ && this.chargeComponent.getChargeTime() % 2.0F == 0.0F) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.CHARGE_KAGE.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      }

      entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 20, 2, false, false));
      int shadowsUsed = this.getShadowsUsed();
      if (ItemsHelper.countItemInInventory(entity, (Item)ModItems.SHADOW.get()) < shadowsUsed) {
         WyHelper.sendMessage(entity, ModI18nAbilities.MESSAGE_NOT_ENOUGH_SHADOWS);
         this.chargeComponent.stopCharging(entity);
      } else {
         if (shadowsUsed != this.prevShadowValue) {
            this.stackComponent.setStacks(entity, this, shadowsUsed);
            this.prevShadowValue = shadowsUsed;
         }

      }
   }

   private boolean stopChargingEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, this.chargeComponent.getChargeTime() + 100.0F);
      int shadowsUsed = this.getShadowsUsed();
      KageHelper.removeShadows(entity, shadowsUsed);

      for(int i = 0; i < shadowsUsed; ++i) {
         NightmareSoldierEntity soldier = new NightmareSoldierEntity(entity.m_9236_(), entity);
         soldier.m_7678_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity.m_146908_(), entity.m_146909_());
         entity.m_9236_().m_7967_(soldier);
      }

      this.prevShadowValue = 0;
      this.stackComponent.setStacks(entity, this, 0);
      return true;
   }

   private int getShadowsUsed() {
      return (int)(this.chargeComponent.getChargeTime() / 40.0F);
   }
}
