package xyz.pixelatedw.mineminenomi.abilities.rokushiki;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class ShiganAbility extends PunchAbility {
   private static final float COOLDOWN = 160.0F;
   private static final int DORIKI = 3000;
   public static final RegistryObject<AbilityCore<ShiganAbility>> INSTANCE = ModRegistry.registerAbility("shigan", "Shigan", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user thrusts their finger at the opponent to pierce them", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, ShiganAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(160.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setNodeFactories(ShiganAbility::createNode).build("mineminenomi");
   });

   public ShiganAbility(AbilityCore<ShiganAbility> core) {
      super(core);
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      entity.m_9236_().m_5594_((Player)null, target.m_20183_(), (SoundEvent)ModSounds.SHIGAN_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
      return true;
   }

   public int getUseLimit() {
      return 1;
   }

   public boolean isParallel() {
      return true;
   }

   public float getPunchDamage() {
      return 25.0F;
   }

   public float getPunchCooldown() {
      return 160.0F;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode shigan = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(6.0F, 2.0F));
      NodeUnlockCondition unlockCondition = RequiredRaceUnlockCondition.requires((Race)ModRaces.HUMAN.get()).and(DorikiUnlockCondition.atLeast((double)3000.0F));
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      shigan.setUnlockRule(unlockCondition, unlockAction);
      return shigan;
   }
}
