package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Optional;
import java.util.Set;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.poi.NTEventTarget;
import xyz.pixelatedw.mineminenomi.api.poi.POIEventTarget;
import xyz.pixelatedw.mineminenomi.api.poi.TrackedNPC;
import xyz.pixelatedw.mineminenomi.data.world.EventsWorldData;
import xyz.pixelatedw.mineminenomi.data.world.NPCWorldData;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.world.spawners.AmbushSpawner;
import xyz.pixelatedw.mineminenomi.world.spawners.TraderSpawner;
import xyz.pixelatedw.mineminenomi.world.spawners.TrainerSpawner;

public class EventsCommand {
   public static LiteralArgumentBuilder<CommandSourceStack> create() {
      LiteralArgumentBuilder<CommandSourceStack> builder = (LiteralArgumentBuilder)Commands.m_82127_("events").requires(Requires.hasPermission(ModPermissions.EVENTS_COMMAND));
      ((LiteralArgumentBuilder)builder.then(Commands.m_82127_("list").executes((context) -> listEvents(context, ((CommandSourceStack)context.getSource()).m_81375_())))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("start").then(Commands.m_82127_("trader").executes((ctx) -> {
         (new TraderSpawner()).spawn(((CommandSourceStack)ctx.getSource()).m_81372_());
         return 1;
      }))).then(Commands.m_82127_("trainer").executes((ctx) -> {
         (new TrainerSpawner()).spawn(((CommandSourceStack)ctx.getSource()).m_81372_());
         return 1;
      }))).then(Commands.m_82127_("ambush").executes((ctx) -> {
         (new AmbushSpawner()).spawn(((CommandSourceStack)ctx.getSource()).m_81372_());
         return 1;
      }))).then(Commands.m_82127_("notorious_target").executes((context) -> startNotoriousTarget(context, ((CommandSourceStack)context.getSource()).m_81375_())))).then(Commands.m_82127_("caravan").executes((context) -> startCaravan(context, ((CommandSourceStack)context.getSource()).m_81375_())))).then(Commands.m_82127_("celestial_dragon").executes((context) -> startCelestialDragon(context, ((CommandSourceStack)context.getSource()).m_81375_()))));
      return builder;
   }

   private static int listEvents(CommandContext<CommandSourceStack> context, ServerPlayer player) {
      EventsWorldData worldData = EventsWorldData.get();
      Set<NTEventTarget> targets = worldData.getNotoriousTargets();
      Set<POIEventTarget> caravans = worldData.getCaravanPOIs();
      Set<POIEventTarget> visits = worldData.getCelestialVisitsPOIs();
      StringBuilder sb = new StringBuilder();
      sb.append("§6Targets:§r " + targets.size() + "\n");

      for(NTEventTarget poi : targets) {
         int var10001 = (int)poi.getPosition().f_82479_;
         sb.append("\n " + var10001 + " " + (int)poi.getPosition().f_82480_ + " " + (int)poi.getPosition().f_82481_);
      }

      sb.append("\n\n");
      sb.append("§6Caravans:§r " + caravans.size() + "\n");

      for(POIEventTarget poi : caravans) {
         int var14 = (int)poi.getPosition().f_82479_;
         sb.append("\n " + var14 + " " + (int)poi.getPosition().f_82480_ + " " + (int)poi.getPosition().f_82481_);
      }

      sb.append("\n\n");
      sb.append("§6Visits:§r " + visits.size() + "\n");

      for(POIEventTarget poi : visits) {
         int var15 = (int)poi.getPosition().f_82479_;
         sb.append("\n " + var15 + " " + (int)poi.getPosition().f_82480_ + " " + (int)poi.getPosition().f_82481_);
      }

      sb.append("\n\n");
      Component message = Component.m_237113_(sb.toString());
      WyHelper.sendMessage(player, message, true);
      return 1;
   }

   private static int startNotoriousTarget(CommandContext<CommandSourceStack> context, ServerPlayer player) {
      EventsWorldData eventsWorldData = EventsWorldData.get();
      Optional<TrackedNPC> tracked = NPCWorldData.get().getRandomTrackedMob((Faction)ModFactions.PIRATE.get());
      if (!tracked.isPresent()) {
         return 1;
      } else {
         Vec3 pos = player.m_20182_().m_82520_(WyHelper.randomWithRange(-10, 10), (double)0.0F, WyHelper.randomWithRange(-10, 10));
         eventsWorldData.addNotoriousTarget(player.m_284548_(), pos, 1200L, (TrackedNPC)tracked.get());
         return 1;
      }
   }

   private static int startCaravan(CommandContext<CommandSourceStack> context, ServerPlayer player) {
      EventsWorldData eventsWorldData = EventsWorldData.get();
      Vec3 pos = player.m_20182_().m_82520_(WyHelper.randomWithRange(-10, 10), (double)-1.0F, WyHelper.randomWithRange(-10, 10));
      eventsWorldData.addCaravan(player.m_284548_(), pos, 1200L);
      return 1;
   }

   private static int startCelestialDragon(CommandContext<CommandSourceStack> context, ServerPlayer player) {
      EventsWorldData eventsWorldData = EventsWorldData.get();
      Vec3 pos = player.m_20182_().m_82520_(WyHelper.randomWithRange(-10, 10), (double)0.0F, WyHelper.randomWithRange(-10, 10));
      eventsWorldData.addCelestialVisit(player.m_284548_(), pos);
      return 1;
   }
}
