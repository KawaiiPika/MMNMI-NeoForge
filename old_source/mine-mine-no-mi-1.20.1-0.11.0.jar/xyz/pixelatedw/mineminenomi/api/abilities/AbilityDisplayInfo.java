package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class AbilityDisplayInfo extends DisplayInfo {
   private AbilityCore<?> icon;

   public AbilityDisplayInfo(AbilityCore<?> pIcon, Component pTitle, Component pDescription, ResourceLocation pBackground, FrameType pFrame, boolean pShowToast, boolean pAnnounceChat, boolean pHidden) {
      super(new ItemStack(Items.f_42516_), pTitle, pDescription, pBackground, pFrame, pShowToast, pAnnounceChat, pHidden);
      this.icon = pIcon;
   }

   public AbilityDisplayInfo(AbilityCore<?> pIcon, ItemStack fallback, Component pTitle, Component pDescription, ResourceLocation pBackground, FrameType pFrame, boolean pShowToast, boolean pAnnounceChat, boolean pHidden) {
      super(fallback, pTitle, pDescription, pBackground, pFrame, pShowToast, pAnnounceChat, pHidden);
      this.icon = pIcon;
   }

   public AbilityCore<?> getAbility() {
      return this.icon;
   }
}
