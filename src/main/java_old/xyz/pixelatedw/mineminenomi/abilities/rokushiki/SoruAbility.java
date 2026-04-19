package xyz.pixelatedw.mineminenomi.abilities.rokushiki;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class SoruAbility extends Ability {
   private static final float LONG_COOLDOWN = 200.0F;
   private static final float SHORT_COOLDOWN = 10.0F;
   private static final int DORIKI = 500;
   public static final RegistryObject<AbilityCore<SoruAbility>> INSTANCE = ModRegistry.registerAbility("soru", "Soru", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user to move at an extremely high speed in bursts.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, SoruAbility::new)).addDescriptionLine(desc).setNodeFactories(SoruAbility::createNode).build("mineminenomi");
   });
   private final StackComponent stackComponent = (new StackComponent(this, 5)).addStackChangeEvent(this::onStacksChange);

   public SoruAbility(AbilityCore<SoruAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.stackComponent});
      this.setOGCD();
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      Vec3 look = entity.m_20154_().m_82542_((double)1.75F, entity.m_20154_().f_82480_ >= (double)0.0F ? (double)0.0F : 0.6, (double)1.75F);
      if (entity.f_20902_ < 0.0F) {
         look = look.m_82542_((double)-1.0F, (double)1.0F, (double)-1.0F);
      }

      if (entity.m_20069_()) {
         look = look.m_82542_(0.2, 0.2, 0.2);
      }

      AbilityHelper.setDeltaMovement(entity, look);
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.VANISH.get(), 5, 0, false, false));
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.TELEPORT_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
      this.stackComponent.addStacks(entity, this, -1);
   }

   private void onStacksChange(LivingEntity entity, IAbility ability, int stacks) {
      if (stacks <= 0) {
         this.cooldownComponent.startCooldown(entity, 200.0F);
         this.stackComponent.revertStacksToDefault(entity, this);
      }

      this.cooldownComponent.startCooldown(entity, 10.0F);
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode soru = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(9.0F, 0.0F));
      NodeUnlockCondition unlockCondition = RequiredRaceUnlockCondition.requires((Race)ModRaces.HUMAN.get()).and(DorikiUnlockCondition.atLeast((double)500.0F));
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      soru.setUnlockRule(unlockCondition, unlockAction);
      return soru;
   }
}
