package xyz.pixelatedw.mineminenomi.api.quests;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.data.entity.quest.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quest.QuestCapability;

public class QuestId<A extends Quest> {
   private ResourceLocation key;
   private String titleId;
   private Component title;
   private IFactory<A> factory;
   private long repeatCooldown = -1L;
   private List<RegistryObject<QuestId<?>>> requirements = new ArrayList();

   protected QuestId(IFactory<A> factory) {
      this.factory = factory;
   }

   public List<RegistryObject<QuestId<?>>> getRequirements() {
      return this.requirements;
   }

   private void setRequirements(List<RegistryObject<QuestId<?>>> requirements) {
      this.requirements = requirements;
   }

   protected String getOrCreateId() {
      if (this.titleId == null) {
         ResourceLocation key = ((IForgeRegistry)WyRegistry.QUESTS.get()).getKey(this);
         if (key != null) {
            this.titleId = Util.m_137492_("quest", key);
         }
      }

      return this.titleId;
   }

   public String getTitleId() {
      return this.titleId;
   }

   public Component getTitle() {
      if (this.title == null) {
         this.title = Component.m_237115_(this.getOrCreateId());
      }

      return this.title;
   }

   public boolean isLocked(Player player) {
      IQuestData questData = (IQuestData)QuestCapability.get(player).orElse((Object)null);
      if (questData == null) {
         return true;
      } else {
         List<RegistryObject<QuestId<?>>> reqs = this.getRequirements();
         if (reqs != null && reqs.size() > 0) {
            for(RegistryObject<QuestId<?>> quest : reqs) {
               if (!questData.hasFinishedQuest((QuestId)quest.get())) {
                  return true;
               }
            }

            return false;
         } else {
            return false;
         }
      }
   }

   public boolean isRepeatable() {
      return this.repeatCooldown >= 0L;
   }

   public long getRepeatCooldown() {
      return this.repeatCooldown;
   }

   public void setRepeatCooldown(long repeatCooldown) {
      this.repeatCooldown = repeatCooldown;
   }

   public A createQuest() {
      return this.factory.create(this);
   }

   public static <A extends Quest> @Nullable QuestId<?> get(ResourceLocation res) {
      QuestId<?> core = (QuestId)((IForgeRegistry)WyRegistry.QUESTS.get()).getValue(res);
      return core;
   }

   public @Nullable ResourceLocation getRegistryKey() {
      if (this.key == null) {
         this.key = ((IForgeRegistry)WyRegistry.QUESTS.get()).getKey(this);
      }

      return this.key;
   }

   public static class Builder<A extends Quest> {
      private IFactory<A> factory;
      private long repeatCooldown = -1L;
      private List<RegistryObject<QuestId<?>>> requirements = new ArrayList();

      public Builder(IFactory<A> factory) {
         this.factory = factory;
      }

      public Builder<A> setRepeatCooldown(long repeatCooldown) {
         this.repeatCooldown = repeatCooldown;
         return this;
      }

      public Builder<A> addRequirements(RegistryObject<QuestId<? extends Quest>>... requirements) {
         for(RegistryObject<QuestId<?>> req : requirements) {
            this.requirements.add(req);
         }

         return this;
      }

      public QuestId<A> build() {
         QuestId<A> core = new QuestId<A>(this.factory);
         core.setRepeatCooldown(this.repeatCooldown);
         core.setRequirements(this.requirements);
         return core;
      }
   }

   public interface IFactory<A extends Quest> {
      A create(QuestId<A> var1);
   }
}
