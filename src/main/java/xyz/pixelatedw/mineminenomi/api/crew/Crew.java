package xyz.pixelatedw.mineminenomi.api.crew;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class Crew {
   private String name;
   private boolean isTemporary;
   private long creationDate;
   private List<Member> members;
   private JollyRoger jollyRoger;

   private Crew() {
      this.members = new ArrayList<>();
      this.jollyRoger = new JollyRoger();
   }

   public Crew(String name, LivingEntity entity) {
      this(name, entity.getUUID(), entity.getName().getString(), Instant.now().getEpochSecond());
   }

   public static Crew from(CompoundTag nbt) {
      Crew crew = new Crew();
      crew.read(nbt);
      return crew;
   }

   public Crew(String name, UUID capId, String username, long creationDate) {
      this.members = new ArrayList<>();
      this.jollyRoger = new JollyRoger();
      this.name = name;
      this.isTemporary = true;
      this.addMember(capId, username).setIsCaptain(true);
      this.creationDate = creationDate;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public boolean isTemporary() {
      return this.isTemporary;
   }

   public Member addMember(LivingEntity entity, boolean saveMember) {
      Member member = this.addMember(entity.getUUID(), entity.getName().getString(), saveMember);
      return member;
   }

   public Member addMember(UUID uuid, String username) {
      return this.addMember(uuid, username, true);
   }

   public Member addMember(UUID uuid, String username, boolean saveMember) {
      Member member = (new Member(uuid, username)).setSaved(saveMember);
      this.members.add(member);
      return member;
   }

   public void removeMember(Level level, UUID id) {
      Member member = this.getMember(id);
      if (member != null) {
         if (member.isCaptain()) {
            Optional<Member> nextCaptain = this.getMembers().stream().filter((memx) -> !memx.isCaptain()).findFirst();
            if (nextCaptain.isPresent()) {
               member.setIsCaptain(false);
               nextCaptain.get().setIsCaptain(true);
               member.markedForDeletion = true;
            } else {
               for(Member mem : this.members) {
                  mem.markedForDeletion = true;
               }
            }
         } else {
            member.markedForDeletion = true;
         }

      }
   }

   public @Nullable Member getMember(UUID id) {
      return this.members.stream().filter((member) -> member.getUUID().equals(id)).findFirst().orElse((Member)null);
   }

   public boolean hasMember(UUID id) {
      return this.getMember(id) != null;
   }

   public void create(Level world) {
      this.isTemporary = false;
   }

   public @Nullable Member getCaptain() {
      return this.members.stream().filter((member) -> member.isCaptain()).findFirst().orElse((Member)null);
   }

   public boolean isEmpty() {
      return this.getMembers().size() <= 0;
   }

   public List<Member> getMembers() {
      return this.members;
   }

   public JollyRoger getJollyRoger() {
      return this.jollyRoger;
   }

   public void setJollyRoger(JollyRoger jr) {
      this.jollyRoger = jr;
   }

   public long getCreationDate() {
      return this.creationDate;
   }

   public void setCreationDate(long creationDate) {
      this.creationDate = creationDate;
   }

   public void tick() {
      Iterator<Member> iter = this.members.iterator();

      while(iter.hasNext()) {
         Member mem = iter.next();
         if (mem.markedForDeletion) {
            iter.remove();
         }
      }

   }

   public CompoundTag write() {
      CompoundTag crewNBT = new CompoundTag();
      crewNBT.putString("name", this.getName());
      ListTag members = new ListTag();

      for(Member member : this.getMembers()) {
         if (member.isSaved()) {
            CompoundTag memberNBT = new CompoundTag();
            memberNBT.putUUID("id", member.getUUID());
            memberNBT.putString("username", member.getUsername());
            memberNBT.putBoolean("isCaptain", member.isCaptain());
            members.add(memberNBT);
         }
      }

      crewNBT.put("members", members);
      CompoundTag jollyRogerData = this.jollyRoger.write();
      crewNBT.put("jollyRoger", jollyRogerData);
      crewNBT.putLong("creationDate", this.creationDate);
      return crewNBT;
   }

   public void read(CompoundTag nbt) {
      String name = nbt.getString("name");
      this.setName(name);
      ListTag members = nbt.getList("members", 10);

      for(int j = 0; j < members.size(); ++j) {
         CompoundTag memberNBT = members.getCompound(j);
         Member member = this.addMember(memberNBT.getUUID("id"), memberNBT.getString("username"));
         member.setIsCaptain(memberNBT.getBoolean("isCaptain"));
      }

      CompoundTag jollyRogerData = nbt.getCompound("jollyRoger");
      this.jollyRoger.read(jollyRogerData);
      Long creationDate = nbt.getLong("creationDate");
      if (creationDate == 0L) {
         this.creationDate = Instant.now().getEpochSecond();
      } else {
         this.creationDate = creationDate;
      }
   }

   public static class Member {
      private UUID uuid;
      private String username;
      private boolean isCaptain;
      private boolean isSaved;
      private boolean markedForDeletion;

      public Member(LivingEntity entity) {
         this(entity.getUUID(), entity.getName().getString());
      }

      public Member(UUID uuid, String username) {
         this.uuid = uuid;
         this.username = username;
      }

      public Member setIsCaptain(boolean flag) {
         this.isCaptain = flag;
         return this;
      }

      public Member setSaved(boolean flag) {
         this.isSaved = flag;
         return this;
      }

      public boolean isCaptain() {
         return this.isCaptain;
      }

      public boolean isSaved() {
         return this.isSaved;
      }

      public UUID getUUID() {
         return this.uuid;
      }

      public String getUsername() {
         return this.username;
      }
   }
}
