package xyz.pixelatedw.mineminenomi.data.entity.challenge;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInvitation;

public interface IChallengeData extends INBTSerializable<CompoundTag> {
   boolean isInGroup();

   void setInGroup(@Nullable UUID var1);

   ImmutableList<UUID> getGroupMembersIds();

   boolean isGroupMember(UUID var1);

   void addGroupMember(Player var1);

   void removeGroupMember(UUID var1);

   ImmutableList<ChallengeInvitation> getInvitations();

   Optional<ChallengeInvitation> getInvitationFrom(UUID var1);

   boolean hasInvitationFrom(UUID var1);

   boolean hasInvitationFrom(Player var1);

   void addInvitation(ChallengeInvitation var1);

   void removeInvitationFrom(Player var1);

   void tickInvitations();

   ChallengeCore<?> getPreviousChallenge();

   ArenaStyle getPreviousArenaStyle();

   String getPreviousArenaClass();

   void setPreviousChallenge(ChallengeCore<?> var1, ArenaStyle var2, String var3);

   boolean isArenaDirty();

   void markArenaDirty(boolean var1);

   boolean addChallenge(Challenge var1);

   boolean addChallenge(ChallengeCore<?> var1);

   boolean removeChallenge(ChallengeCore<?> var1);

   boolean hasChallenge(ChallengeCore<?> var1);

   boolean isChallengeCompleted(ChallengeCore<?> var1);

   <T extends Challenge> T getChallenge(ChallengeCore<?> var1);

   List<Challenge> getChallenges();

   void clearChallenges();

   int countChallenges();
}
