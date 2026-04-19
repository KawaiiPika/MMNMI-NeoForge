package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.quest.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quest.QuestCapability;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KnockdownAbility extends PassiveAbility {
   public static final RegistryObject<AbilityCore<KnockdownAbility>> INSTANCE = ModRegistry.registerAbility("knockdown", "Knockdown", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("When active, enemies will be left unconscious instead of killed.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, AbilityType.PASSIVE, KnockdownAbility::new)).addDescriptionLine(desc).setUnlockCheck(KnockdownAbility::canUnlock).build("mineminenomi");
   });

   public KnockdownAbility(AbilityCore<KnockdownAbility> core) {
      super(core);
      this.addEquipEvent(this::onEquipEvent);
   }

   private void onEquipEvent(LivingEntity entity, IAbility ability) {
      if (entity != null && !entity.m_9236_().f_46443_) {
         this.pauseTickComponent.setPause(entity, true);
      }

   }

   private static boolean canUnlock(LivingEntity entity) {
      if (entity instanceof Player player) {
         IQuestData questProps = (IQuestData)QuestCapability.get(player).orElse((Object)null);
         return questProps != null;
      } else {
         return false;
      }
   }
}
