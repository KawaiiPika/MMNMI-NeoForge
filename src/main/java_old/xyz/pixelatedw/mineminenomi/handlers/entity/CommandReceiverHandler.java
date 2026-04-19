package xyz.pixelatedw.mineminenomi.handlers.entity;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import xyz.pixelatedw.mineminenomi.api.caps.command.CommandReceiverCapability;
import xyz.pixelatedw.mineminenomi.api.caps.command.ICommandReceiver;
import xyz.pixelatedw.mineminenomi.api.enums.NPCCommand;
import xyz.pixelatedw.mineminenomi.api.factions.MarineRank;
import xyz.pixelatedw.mineminenomi.entities.commands.MarineCommand;
import xyz.pixelatedw.mineminenomi.entities.commands.TamableCommand;
import xyz.pixelatedw.mineminenomi.entities.commands.TraceableCommand;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.BlackKnightEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.DoppelmanEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.NightmareSoldierEntity;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class CommandReceiverHandler {
   public static void canMaintainCommand(Mob mob) {
      LazyOptional<ICommandReceiver> lazyCommand = CommandReceiverCapability.get(mob);
      lazyCommand.ifPresent((command) -> {
         if (!command.canMaintainCommand()) {
            command.setCurrentCommand((LivingEntity)null, NPCCommand.IDLE);
         }

      });
   }

   public static void registerCommandReceiver(AttachCapabilitiesEvent<Entity> event, Mob entity) {
      if (entity.m_6095_() == ModMobs.MARINE_GRUNT.get()) {
         register(event, entity, LazyOptional.of(() -> new MarineCommand(entity, MarineRank.COMMODORE)));
      } else if (entity.m_6095_() != ModMobs.MARINE_BRUTE.get() && entity.m_6095_() != ModMobs.MARINE_SNIPER.get() && entity.m_6095_() != ModMobs.MARINE_CAPTAIN.get()) {
         if (entity instanceof TamableAnimal) {
            TamableAnimal tamable = (TamableAnimal)entity;
            register(event, entity, LazyOptional.of(() -> new TamableCommand(tamable)));
         } else if (entity instanceof DoppelmanEntity || entity instanceof NightmareSoldierEntity || entity instanceof BlackKnightEntity) {
            register(event, entity, LazyOptional.of(() -> new TraceableCommand(entity)));
         }
      } else {
         register(event, entity, LazyOptional.of(() -> new MarineCommand(entity, MarineRank.ADMIRAL)));
      }

   }

   private static void register(AttachCapabilitiesEvent<Entity> event, Mob entity, final LazyOptional<ICommandReceiver> commandLazy) {
      event.addCapability(CommandReceiverCapability.ID, new ICapabilityProvider() {
         public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            return CommandReceiverCapability.INSTANCE.orEmpty(cap, commandLazy);
         }
      });
   }
}
