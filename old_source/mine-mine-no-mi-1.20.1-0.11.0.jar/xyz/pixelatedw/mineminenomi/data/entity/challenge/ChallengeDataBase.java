package xyz.pixelatedw.mineminenomi.data.entity.challenge;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInvitation;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;

public class ChallengeDataBase implements IChallengeData {
   private Player owner;
   private boolean isArenaDirty;
   private ChallengeCore<?> previousChallenge;
   private ArenaStyle previousArenaStyle;
   private String previousArenaClass;
   private UUID groupOwner;
   private List<UUID> group = new ArrayList();
   private List<Challenge> challenges = new ArrayList();
   private List<ChallengeInvitation> invitations = new ArrayList();

   public ChallengeDataBase(Player owner) {
      this.owner = owner;
   }

   public boolean isInGroup() {
      return this.groupOwner != null;
   }

   public void setInGroup(@Nullable UUID groupOwnerId) {
      this.groupOwner = groupOwnerId;
   }

   public ImmutableList<UUID> getGroupMembersIds() {
      return ImmutableList.copyOf(this.group);
   }

   public boolean isGroupMember(UUID id) {
      return this.group.contains(id);
   }

   public void removeGroupMember(UUID playerId) {
      this.group.remove(playerId);
   }

   public void addGroupMember(Player player) {
      if (player != null) {
         ChallengeCapability.get(player).ifPresent((data) -> data.setInGroup(this.owner.m_20148_()));
         this.group.add(player.m_20148_());
      }
   }

   public ImmutableList<ChallengeInvitation> getInvitations() {
      return ImmutableList.copyOf(this.invitations);
   }

   public Optional<ChallengeInvitation> getInvitationFrom(UUID senderId) {
      for(ChallengeInvitation invite : this.invitations) {
         if (invite.getSenderId().equals(senderId)) {
            return Optional.ofNullable(invite);
         }
      }

      return Optional.empty();
   }

   public boolean hasInvitationFrom(UUID senderId) {
      for(ChallengeInvitation invite : this.invitations) {
         if (invite.getSenderId().equals(senderId)) {
            return true;
         }
      }

      return false;
   }

   public boolean hasInvitationFrom(Player sender) {
      return this.hasInvitationFrom(sender.m_20148_());
   }

   public void addInvitation(ChallengeInvitation challenge) {
      if (!this.hasInvitationFrom(challenge.getSenderId())) {
         this.invitations.add(challenge);
      }
   }

   public void removeInvitationFrom(Player player) {
      Iterator<ChallengeInvitation> iter = this.invitations.iterator();

      while(iter.hasNext()) {
         ChallengeInvitation invite = (ChallengeInvitation)iter.next();
         if (invite.getSenderId().equals(player.m_20148_())) {
            iter.remove();
            return;
         }
      }

   }

   public void tickInvitations() {
      if (!this.invitations.isEmpty()) {
         Level world = this.owner.m_20193_();
         long currentTime = world.m_46467_();
         ChallengesWorldData worldData = ChallengesWorldData.get();
         Iterator<ChallengeInvitation> iter = this.invitations.iterator();

         while(iter.hasNext()) {
            ChallengeInvitation invite = (ChallengeInvitation)iter.next();
            Player sender = invite.getSender(world);
            if (!world.m_6907_().contains(sender)) {
               ModMain.LOGGER.debug("Removed invitation due to missing sender");
               iter.remove();
            } else if (invite.isExpired(world)) {
               ModMain.LOGGER.debug("Removed invitation due to timeout");
               iter.remove();
            } else if (worldData.getInProgressChallengeFor((LivingEntity)sender) != null) {
               ModMain.LOGGER.debug("Removed invitation due to it already starting");
               iter.remove();
            }
         }

      }
   }

   public ChallengeCore<?> getPreviousChallenge() {
      return this.previousChallenge;
   }

   public ArenaStyle getPreviousArenaStyle() {
      return this.previousArenaStyle;
   }

   public String getPreviousArenaClass() {
      return this.previousArenaClass;
   }

   public void setPreviousChallenge(ChallengeCore<?> challenge, ArenaStyle style, String clz) {
      this.previousChallenge = challenge;
      this.previousArenaStyle = style;
      this.previousArenaClass = clz;
   }

   public boolean isArenaDirty() {
      return this.isArenaDirty;
   }

   public void markArenaDirty(boolean flag) {
      this.isArenaDirty = flag;
   }

   public boolean addChallenge(Challenge challenge) {
      if (this.hasChallenge(challenge.getCore())) {
         return false;
      } else {
         this.challenges.add(challenge);
         Player var3 = this.owner;
         if (var3 instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer)var3;
            ModAdvancements.UNLOCK_CHALLENGE.trigger(serverPlayer, challenge.getCore());
         }

         return true;
      }
   }

   public boolean addChallenge(ChallengeCore<?> core) {
      if (this.hasChallenge(core)) {
         return false;
      } else {
         Challenge challenge = core.createChallenge();
         return this.addChallenge(challenge);
      }
   }

   public boolean removeChallenge(ChallengeCore<?> core) {
      if (!this.hasChallenge(core)) {
         return false;
      } else {
         this.challenges.removeIf((ch) -> ch.getCore().equals(core));
         return true;
      }
   }

   public boolean hasChallenge(ChallengeCore<?> core) {
      return this.getChallenge(core) != null;
   }

   public boolean isChallengeCompleted(ChallengeCore<?> core) {
      Challenge ch = this.getChallenge(core);
      return ch == null ? false : ch.isComplete();
   }

   public <T extends Challenge> @Nullable T getChallenge(ChallengeCore<?> core) {
      return (T)(this.challenges.stream().filter((ch) -> ch.getCore().equals(core)).findFirst().orElse((Object)null));
   }

   public List<Challenge> getChallenges() {
      return (List)this.challenges.stream().filter((ch) -> ch != null).collect(Collectors.toList());
   }

   public int countChallenges() {
      this.challenges.removeIf((chl) -> chl == null);
      return this.challenges.size();
   }

   public void clearChallenges() {
      this.challenges.clear();
   }

   public CompoundTag serializeNBT() {
      CompoundTag nbt = new CompoundTag();
      nbt.m_128379_("isArenaDirty", this.isArenaDirty());
      if (this.getPreviousChallenge() != null) {
         nbt.m_128359_("previousChallenge", this.getPreviousChallenge().getRegistryKey().toString());
         nbt.m_128359_("previousArenaStyle", this.getPreviousArenaStyle().name());
         nbt.m_128359_("previousArenaClass", this.getPreviousArenaClass());
      }

      ListTag challenges = new ListTag();

      for(int i = 0; i < this.getChallenges().size(); ++i) {
         Challenge challenge = (Challenge)this.getChallenges().get(i);
         CompoundTag nbtData = new CompoundTag();
         nbtData.m_128359_("id", challenge.getCore().getRegistryKey().toString());
         challenge.save(nbtData);
         challenges.add(nbtData);
      }

      nbt.m_128365_("challenges", challenges);
      return nbt;
   }

   public void deserializeNBT(CompoundTag nbt) {
      this.clearChallenges();

      try {
         this.markArenaDirty(nbt.m_128471_("isArenaDirty"));
         ChallengeCore<?> previousChallenge = (ChallengeCore)((IForgeRegistry)WyRegistry.CHALLENGES.get()).getValue(ResourceLocation.parse(nbt.m_128461_("previousChallenge")));
         ArenaStyle previousStyle = ArenaStyle.valueOf(nbt.m_128461_("previousArenaStyle"));
         String previousArenaClass = nbt.m_128461_("previousArenaClass");
         this.setPreviousChallenge(previousChallenge, previousStyle, previousArenaClass);
      } catch (Exception var8) {
      }

      ListTag challenges = nbt.m_128437_("challenges", 10);

      for(int i = 0; i < challenges.size(); ++i) {
         try {
            CompoundTag nbtData = challenges.m_128728_(i);
            ChallengeCore<?> core = (ChallengeCore)((IForgeRegistry)WyRegistry.CHALLENGES.get()).getValue(ResourceLocation.parse(nbtData.m_128461_("id")));
            if (core != null) {
               Challenge challenge = core.createChallenge();
               challenge.load(nbtData);
               this.addChallenge(challenge);
            }
         } catch (Exception var7) {
         }
      }

   }
}
