package xyz.pixelatedw.mineminenomi.entities;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.api.entities.WantedPosterData;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.poi.TrackedNPC;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.world.EventsWorldData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.data.world.NPCWorldData;
import xyz.pixelatedw.mineminenomi.entities.mobs.NotoriousEntity;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.items.WantedPosterItem;

public class WantedPosterPackageEntity extends Entity {
   private int life;

   public WantedPosterPackageEntity(EntityType<? extends WantedPosterPackageEntity> type, Level world) {
      super(type, world);
      this.life = 6000;
   }

   public WantedPosterPackageEntity(Level world) {
      this((EntityType)ModEntities.WANTED_POSTER_PACKAGE.get(), world);
   }

   public boolean m_6469_(DamageSource source, float amount) {
      if (this.m_6673_(source)) {
         return false;
      } else {
         if (!this.m_213877_() && !this.m_9236_().f_46443_) {
            this.m_6074_();
            this.m_5834_();
         }

         return true;
      }
   }

   public boolean m_7337_(Entity other) {
      return true;
   }

   public boolean m_5829_() {
      return true;
   }

   public boolean m_6094_() {
      return false;
   }

   public boolean m_6087_() {
      return this.m_6084_();
   }

   public void m_6074_() {
      if (!this.m_9236_().f_46443_) {
         FactionsWorldData worldData = FactionsWorldData.get();
         Vec3 newPos = new Vec3(this.m_20182_().m_7096_() + (double)0.5F, this.m_20182_().m_7098_() - (double)0.5F, this.m_20182_().m_7094_() + (double)0.5F);
         this.spawnPlayerPosters(this.m_9236_(), newPos, worldData);
         this.spawnNotoriousPosters(this.m_9236_(), newPos, worldData);
         this.spawnChallengePosters(this.m_9236_(), newPos);
      }

      super.m_6074_();
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_ && this.life-- <= 0) {
         this.m_142687_(RemovalReason.DISCARDED);
      } else {
         if (!this.m_20068_()) {
            this.m_20256_(this.m_20184_().m_82520_((double)0.0F, -0.003, (double)0.0F));
         }

         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_(0.98));
      }
   }

   private void spawnNotoriousPosters(Level level, Vec3 pos, FactionsWorldData worldData) {
      int notoriousPosters = 1 + level.m_213780_().m_188503_(3);
      NPCWorldData npcWorldData = NPCWorldData.get();
      EventsWorldData eventsWorldData = EventsWorldData.get();

      for(int i = 0; i < notoriousPosters; ++i) {
         Optional<TrackedNPC> npc = npcWorldData.getRandomTrackedMob((Faction)ModFactions.PIRATE.get());
         if (npc.isPresent() && !eventsWorldData.hasNTEventFor((TrackedNPC)npc.get())) {
            ItemStack stack = new ItemStack((ItemLike)ModBlocks.WANTED_POSTER.get());
            NotoriousEntity entity = ((TrackedNPC)npc.get()).createTrackedMob(level);
            Vec3 pos2 = null;

            for(int j = 0; j < 5; ++j) {
               int extraX = 1000 + level.m_213780_().m_188503_(5000);
               if (level.m_213780_().m_188499_()) {
                  extraX *= -1;
               }

               int extraZ = 1000 + level.m_213780_().m_188503_(5000);
               if (level.m_213780_().m_188499_()) {
                  extraZ *= -1;
               }

               Vec3 vecPos = pos.m_82520_((double)extraX, (double)1.0F, (double)extraZ);
               BlockPos check = new BlockPos((int)vecPos.f_82479_, (int)vecPos.f_82480_, (int)vecPos.f_82481_);
               if (!level.m_204166_(check).m_203656_(BiomeTags.f_207603_)) {
                  pos2 = vecPos;
                  break;
               }
            }

            if (pos2 != null) {
               eventsWorldData.addNotoriousTarget((ServerLevel)level, pos2, 72000L, (TrackedNPC)npc.get());
               WantedPosterData wantedData = new WantedPosterData(entity, ((TrackedNPC)npc.get()).getBounty());
               wantedData.setTrackedPosition(pos2);
               if (EntityStatsCapability.canReceiveBounty(entity)) {
                  worldData.issueBounty(((TrackedNPC)npc.get()).getUUID(), ((TrackedNPC)npc.get()).getBounty());
               }

               CompoundTag nbt = wantedData.write();
               stack.m_41784_().m_128365_("WPData", nbt);
               level.m_7967_(new ItemEntity(level, pos.f_82479_, pos.f_82480_ + (double)1.0F, pos.f_82481_, stack));
            }
         }
      }

   }

   private void spawnChallengePosters(Level level, Vec3 pos) {
      int challengePosters = level.m_213780_().m_188503_(3);
      List<ChallengeCore<?>> list = (List)((IForgeRegistry)WyRegistry.CHALLENGES.get()).getValues().stream().filter((core) -> core.getDifficulty() == ChallengeDifficulty.STANDARD).collect(WyHelper.toShuffledList());
      if (list.size() > 0) {
         for(int i = 0; i < challengePosters; ++i) {
            int pick = level.m_213780_().m_188503_(list.size());
            ChallengeCore<?> challenge = (ChallengeCore)list.get(pick);
            ItemStack stack = new ItemStack((ItemLike)ModItems.CHALLENGE_POSTER.get());
            stack.m_41714_(challenge.getLocalizedTitle());
            stack.m_41784_().m_128359_("challengeId", challenge.getRegistryKey().toString());
            level.m_7967_(new ItemEntity(level, pos.f_82479_, pos.f_82480_ + (double)1.0F, pos.f_82481_, stack));
         }
      }

   }

   private void spawnPlayerPosters(Level level, Vec3 pos, FactionsWorldData worldData) {
      Set<Pair<UUID, Long>> playerBountiesInPackage = new HashSet();
      if (worldData.getAllBounties().size() > 0) {
         Predicate<Player> hasBounty = (playerx) -> playerx != null && playerx.m_6084_() && EntityStatsCapability.canReceiveBounty(playerx) && worldData.getBounty(playerx.m_20148_()) != 0L;
         TargetHelper.getNearbyPlayers(level, pos, (double)10.0F, (Predicate)null).stream().filter(hasBounty).limit(5L).forEach((playerx) -> {
            Pair<UUID, Long> pair = ImmutablePair.of(playerx.m_20148_(), worldData.getBounty(playerx.m_20148_()));
            playerBountiesInPackage.add(pair);
         });
         if (playerBountiesInPackage.size() < 5) {
            List<Pair<UUID, Long>> playerBounties = (List)worldData.getAllBounties().entrySet().stream().filter((entry) -> !playerBountiesInPackage.contains(entry)).map((entry) -> ImmutablePair.of((UUID)entry.getKey(), (Long)entry.getValue())).collect(Collectors.toList());
            Collections.shuffle(playerBounties);
            int size = Math.min(3, playerBounties.size());
            playerBountiesInPackage.addAll(playerBounties.subList(0, size));
         }

         if (!playerBountiesInPackage.isEmpty()) {
            for(Pair<UUID, Long> pair : playerBountiesInPackage) {
               Player player = level.m_46003_((UUID)pair.getKey());
               if (player != null) {
                  ItemStack stack = WantedPosterItem.getWantedPosterStack(player, (Long)pair.getValue());
                  level.m_7967_(new ItemEntity(level, pos.f_82479_, pos.f_82480_ + (double)1.0F, pos.f_82481_, stack));
               }
            }
         }
      }

   }

   protected void m_8097_() {
   }

   protected void m_7378_(CompoundTag tag) {
   }

   protected void m_7380_(CompoundTag tag) {
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
