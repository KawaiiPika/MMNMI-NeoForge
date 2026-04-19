package xyz.pixelatedw.mineminenomi.mixins;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDisplayInfo;

@Mixin({DisplayInfo.class})
public abstract class DisplayInfoMixin {
   @Shadow
   @Final
   private boolean f_14964_;
   @Shadow
   @Final
   private boolean f_14963_;

   @Inject(
      method = {"fromJson"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void fromJson(JsonObject pObject, CallbackInfoReturnable<DisplayInfo> callback) {
      Component title = Serializer.m_130691_(pObject.get("title"));
      Component desc = Serializer.m_130691_(pObject.get("description"));
      if (title != null && desc != null) {
         JsonObject iconObj = GsonHelper.m_13930_(pObject, "icon");
         if (iconObj.has("ability")) {
            AbilityCore<?> icon = getAbility(iconObj);
            if (icon == null) {
               return;
            }

            ResourceLocation bg = pObject.has("background") ? ResourceLocation.parse(GsonHelper.m_13906_(pObject, "background")) : null;
            FrameType frame = pObject.has("frame") ? FrameType.m_15549_(GsonHelper.m_13906_(pObject, "frame")) : FrameType.TASK;
            boolean flag = GsonHelper.m_13855_(pObject, "show_toast", true);
            boolean flag1 = GsonHelper.m_13855_(pObject, "announce_to_chat", true);
            boolean flag2 = GsonHelper.m_13855_(pObject, "hidden", false);
            callback.setReturnValue(new AbilityDisplayInfo(icon, title, desc, bg, frame, flag, flag1, flag2));
         }

      } else {
         throw new JsonSyntaxException("Both title and description must be set");
      }
   }

   @Nullable
   private static AbilityCore<?> getAbility(JsonObject json) {
      ResourceLocation key = ResourceLocation.parse(GsonHelper.m_13906_(json, "ability"));
      AbilityCore<?> ability = (AbilityCore)((IForgeRegistry)WyRegistry.ABILITIES.get()).getValue(key);
      return ability;
   }

   @Inject(
      method = {"serializeToNetwork"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void serializeToNetwork(FriendlyByteBuf buf, CallbackInfo callback) {
      DisplayInfo info = (DisplayInfo)this;
      if (info instanceof AbilityDisplayInfo ablInfo) {
         ResourceLocation rs = ablInfo.getAbility().getRegistryKey();
         buf.writeInt(1);
         buf.m_130085_(rs);
      } else {
         buf.writeInt(0);
      }

   }

   @Inject(
      method = {"fromNetwork"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void fromNetwork(FriendlyByteBuf buf, CallbackInfoReturnable<DisplayInfo> callback) {
      int v = buf.readInt();
      if (v == 1) {
         ResourceLocation rs = buf.m_130281_();
         AbilityCore<?> ability = (AbilityCore)((IForgeRegistry)WyRegistry.ABILITIES.get()).getValue(rs);
         Component itextcomponent = buf.m_130238_();
         Component itextcomponent1 = buf.m_130238_();
         ItemStack _itemstack = buf.m_130267_();
         FrameType frametype = (FrameType)buf.m_130066_(FrameType.class);
         int i = buf.readInt();
         ResourceLocation resourcelocation = (i & 1) != 0 ? buf.m_130281_() : null;
         boolean flag = (i & 2) != 0;
         boolean flag1 = (i & 4) != 0;
         AbilityDisplayInfo displayinfo = new AbilityDisplayInfo(ability, itextcomponent, itextcomponent1, resourcelocation, frametype, flag, false, flag1);
         displayinfo.m_14978_(buf.readFloat(), buf.readFloat());
         callback.setReturnValue(displayinfo);
      }

   }

   @Inject(
      method = {"serializeIcon"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void serializeIcon(CallbackInfoReturnable<JsonObject> callback) {
      DisplayInfo info = (DisplayInfo)this;
      if (info instanceof AbilityDisplayInfo ablInfo) {
         JsonObject jsonobject = new JsonObject();
         if (ablInfo.getAbility() != null) {
            String abilityIcon = ablInfo.getAbility().getRegistryKey().toString();
            jsonobject.addProperty("ability", abilityIcon);
         }

         String itemIcon = ForgeRegistries.ITEMS.getKey(info.m_14990_().m_41720_()).toString();
         jsonobject.addProperty("item", itemIcon);
         if (info.m_14990_().m_41782_()) {
            jsonobject.addProperty("nbt", info.m_14990_().m_41783_().toString());
         }

         callback.setReturnValue(jsonobject);
      }

   }
}
