package xyz.pixelatedw.mineminenomi.abilities;

import java.awt.Color;
import java.util.Optional;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.caps.command.CommandReceiverCapability;
import xyz.pixelatedw.mineminenomi.api.caps.command.ICommandReceiver;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.enums.NPCCommand;
import xyz.pixelatedw.mineminenomi.api.factions.MarineRank;
import xyz.pixelatedw.mineminenomi.api.factions.RevolutionaryRank;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.commands.AttackCommandGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.commands.FollowCommandGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.commands.GuardCommandGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.commands.StayCommandGoal;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.CommandMarkParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class CommandAbility extends Ability {
   private static final int COOLDOWN = 10;
   private static final float RANGE = 20.0F;
   private static final int LIMIT = 20;
   private static final float ATTACK_RAYTRACE_DISTANCE = 30.0F;
   private static final float ATTACK_RAYTRACE_WIDTH = 1.6F;
   private static final int[] COMMAND_COLORS;
   private static final Component IDLE_COMMAND_NAME;
   private static final Component ATTACK_COMMAND_NAME;
   private static final Component FOLLOW_COMMAND_NAME;
   private static final Component STAY_COMMAND_NAME;
   private static final Component GUARD_COMMAND_NAME;
   private static final Component[] COMMAND_NAMES;
   private static final ResourceLocation IDLE_ICON;
   private static final ResourceLocation ATTACK_ICON;
   private static final ResourceLocation FOLLOW_ICON;
   private static final ResourceLocation STAY_ICON;
   private static final ResourceLocation GUARD_ICON;
   private static final ResourceLocation[] COMMAND_ICONS;
   public static final RegistryObject<AbilityCore<CommandAbility>> INSTANCE;
   protected final RangeComponent rangeComponent = new RangeComponent(this);
   private final AltModeComponent<NPCCommand> altModeComponent;
   private CommandMarkParticleEffect.Details mainDetails;
   private CommandMarkParticleEffect.Details subDetails;
   private TargetPredicate targetPredicate;
   private NPCCommand command;
   private int markColor;

   public CommandAbility(AbilityCore<? extends CommandAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<NPCCommand>(this, NPCCommand.class, NPCCommand.IDLE)).addChangeModeEvent(100, this::onChangeModEvent);
      this.targetPredicate = new TargetPredicate();
      this.command = NPCCommand.IDLE;
      this.markColor = COMMAND_COLORS[0];
      this.addComponents(new AbilityComponent[]{this.altModeComponent, this.rangeComponent});
      this.mainDetails = new CommandMarkParticleEffect.Details(this.getMarkColor(), true);
      this.subDetails = new CommandMarkParticleEffect.Details(this.getMarkColor(), false);
      this.addUseEvent(this::onUseEvent);
   }

   public void onUseEvent(LivingEntity entity, IAbility ability) {
      this.rangeComponent.getTargetsInArea(entity, entity.m_20183_(), 20.0F, TargetPredicate.EVERYTHING, Mob.class).stream().limit(20L).map((target) -> ImmutablePair.of(target, target.getCapability(CommandReceiverCapability.INSTANCE, (Direction)null).resolve())).filter((pair) -> ((Optional)pair.getValue()).isPresent()).filter((pair) -> ((ICommandReceiver)((Optional)pair.getValue()).get()).canReceiveCommandFrom(entity)).forEach((pair) -> {
         this.spawnCommandMark(entity, (LivingEntity)pair.getKey());
         ((ICommandReceiver)((Optional)pair.getValue()).get()).setCurrentCommand(entity, this.getCommand());
         this.sendCommand(entity, (Mob)pair.getKey());
      });
      this.spawnCommandMark(entity, entity);
      this.cooldownComponent.startCooldown(entity, 10.0F);
   }

   private boolean onChangeModEvent(LivingEntity entity, IAbility ability, NPCCommand newCommand) {
      this.command = newCommand;
      this.updateCommandDetails();
      return true;
   }

   private void updateCommandDetails() {
      this.markColor = COMMAND_COLORS[this.command.ordinal()];
      this.setDisplayName(COMMAND_NAMES[this.command.ordinal()]);
      this.setDisplayIcon(COMMAND_ICONS[this.command.ordinal()]);
      this.mainDetails = new CommandMarkParticleEffect.Details(this.getMarkColor(), true);
      this.subDetails = new CommandMarkParticleEffect.Details(this.getMarkColor(), false);
   }

   private void spawnCommandMark(LivingEntity entity, LivingEntity target) {
      if (entity instanceof ServerPlayer player) {
         WyHelper.spawnParticleEffectForOwner((ParticleEffect)ModParticleEffects.COMMAND_MARK.get(), player, target.m_20185_(), target.m_20186_() + (double)target.m_20206_(), target.m_20189_(), entity == target ? this.mainDetails : this.subDetails);
      }

   }

   public NPCCommand getCommand() {
      return this.command;
   }

   public int getMarkColor() {
      return this.markColor;
   }

   public void sendCommand(LivingEntity commandSender, Mob commandListener) {
      switch (this.command) {
         case ATTACK:
            LivingEntity target = (LivingEntity)this.rangeComponent.getTargetsInLine(commandSender, 30.0F, 1.6F).stream().findFirst().orElse((Object)null);
            if (target != null && target.m_6084_()) {
               commandListener.m_6710_(target);
            } else {
               this.rangeComponent.getTargetsInArea(commandSender, 30.0F).stream().sorted(TargetHelper.closestComparator(commandSender.m_20182_())).findFirst().ifPresent((aoeTarget) -> commandListener.m_6710_(aoeTarget));
            }
         case FOLLOW:
         case GUARD:
         case IDLE:
         case STAY:
         default:
      }
   }

   public static boolean canUnlock(LivingEntity entity) {
      if (entity instanceof Player player) {
         IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
         if (props == null) {
            return false;
         } else if (props.isMarine() && props.hasRank(MarineRank.CAPTAIN)) {
            return true;
         } else if (props.isRevolutionary() && props.hasRank(RevolutionaryRank.COMMANDER)) {
            return true;
         } else {
            return !DevilFruitCapability.hasDevilFruit(entity, ModFruits.KAGE_KAGE_NO_MI) && !DevilFruitCapability.hasDevilFruit(entity, ModFruits.ITO_ITO_NO_MI) ? true : true;
         }
      } else {
         return false;
      }
   }

   public void saveAdditional(CompoundTag nbt) {
      nbt.m_128405_("command", this.command.ordinal());
   }

   public void loadAdditional(CompoundTag nbt) {
      this.command = NPCCommand.values()[nbt.m_128451_("command")];
      this.updateCommandDetails();
   }

   public static void addCommandGoals(Mob mob, ICommandReceiver command) {
      mob.f_21345_.m_25352_(0, new StayCommandGoal(mob, command));
      mob.f_21345_.m_25352_(0, new FollowCommandGoal(mob, command));
      mob.f_21345_.m_25352_(0, new GuardCommandGoal(mob, command));
      mob.f_21346_.m_25352_(0, new AttackCommandGoal(mob, command));
      mob.f_21346_.m_25352_(0, new StayCommandGoal(mob, command));
      mob.f_21346_.m_25352_(0, new FollowCommandGoal(mob, command));
      mob.f_21346_.m_25352_(0, new GuardCommandGoal(mob, command));
   }

   static {
      COMMAND_COLORS = new int[]{Color.WHITE.getRGB(), Color.RED.getRGB(), Color.GREEN.getRGB(), Color.YELLOW.getRGB(), Color.BLUE.getRGB()};
      IDLE_COMMAND_NAME = Component.m_237115_(ModRegistry.registerAbilityName("idle_command", "Command: Idle"));
      ATTACK_COMMAND_NAME = Component.m_237115_(ModRegistry.registerAbilityName("attack_command", "Command: Attack"));
      FOLLOW_COMMAND_NAME = Component.m_237115_(ModRegistry.registerAbilityName("follow_command", "Command: Follow"));
      STAY_COMMAND_NAME = Component.m_237115_(ModRegistry.registerAbilityName("stay_command", "Command: Stay"));
      GUARD_COMMAND_NAME = Component.m_237115_(ModRegistry.registerAbilityName("guard_command", "Command: Guard"));
      COMMAND_NAMES = new Component[]{IDLE_COMMAND_NAME, ATTACK_COMMAND_NAME, FOLLOW_COMMAND_NAME, STAY_COMMAND_NAME, GUARD_COMMAND_NAME};
      IDLE_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/idle_command.png");
      ATTACK_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/attack_command.png");
      FOLLOW_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/follow_command.png");
      STAY_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/stay_command.png");
      GUARD_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/guard_command.png");
      COMMAND_ICONS = new ResourceLocation[]{IDLE_ICON, ATTACK_ICON, FOLLOW_ICON, STAY_ICON, GUARD_ICON};
      INSTANCE = ModRegistry.registerAbility("command", "Command", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Sends a command to all nearby entities that the player has control over.", (Object)null), ImmutablePair.of("  §aIDLE§r removes any previously issued command from all nearby entities", (Object)null), ImmutablePair.of("  §aATTACK§r either start attacking the entity the player is looking at (if any) or the closest one", (Object)null), ImmutablePair.of("  §aFOLLOW§r will start following the command sender", (Object)null), ImmutablePair.of("  §aSTAY§r will stay in place, attacking only after being hit first", (Object)null), ImmutablePair.of("  §aGUARD§r wil attack any nearby enemies but then return back to its guarding point", (Object)null));
         return (new AbilityCore.Builder(name, id, AbilityCategory.FACTION, AbilityType.ACTION, CommandAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(10.0F), RangeComponent.getTooltip(20.0F, RangeComponent.RangeType.AOE)).setUnlockCheck(CommandAbility::canUnlock).setIcon(IDLE_ICON).build("mineminenomi");
      });
   }
}
