package xyz.pixelatedw.mineminenomi.abilities.sniper;

import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class HissatsuAbility extends Ability {
   private static final float COOLDOWN = 300.0F;
   public static final RegistryObject<AbilityCore<HissatsuAbility>> INSTANCE = ModRegistry.registerAbility("hissatsu", "Hissatsu", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the next sniper ability to instantly hit the target", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, HissatsuAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F), ContinuousComponent.getTooltip()).setNodeFactories(HissatsuAbility::createNode).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addEndEvent(this::onEndContinuity);

   public HissatsuAbility(AbilityCore<HissatsuAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent});
      this.addUseEvent(this::onUseEvent);
   }

   public void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void onEndContinuity(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 300.0F);
   }

   public static boolean checkHitScan(LivingEntity entity) {
      Optional<HissatsuAbility> hissatsuAbility = AbilityCapability.get(entity).map((props) -> (HissatsuAbility)props.getEquippedAbility((AbilityCore)INSTANCE.get()));
      boolean isHitScan = false;
      if (hissatsuAbility.isPresent()) {
         isHitScan = ((HissatsuAbility)hissatsuAbility.get()).isContinuous();
         if (isHitScan) {
            ((HissatsuAbility)hissatsuAbility.get()).getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).ifPresent((comp) -> comp.stopContinuity(entity));
            entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.DASH_ABILITY_SWOOSH_SFX.get(), SoundSource.PLAYERS, 2.0F, 3.0F);
            return true;
         }
      }

      return false;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-10.0F, -7.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.SNIPER.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)HiNoToriBoshiAbility.INSTANCE.get()).getNode(entity), ((AbilityCore)RenpatsuNamariBoshiAbility.INSTANCE.get()).getNode(entity), ((AbilityCore)KemuriBoshiAbility.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
