package xyz.pixelatedw.mineminenomi.ui.screens;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.util.Map;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;
import xyz.pixelatedw.mineminenomi.api.entities.WantedPosterData;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class WantedPosterScreen extends Screen {
   public static final int TEXT_COLOR = -11455469;
   private WantedPosterData wantedPosterData;
   private final String name;
   private final JollyRoger jollyRoger;
   private final ResourceLocation background;
   private final ResourceLocation skin;
   private final boolean isPirate;
   private final boolean isRevo;
   private final float nameScale;

   public WantedPosterScreen(WantedPosterData wantedData) {
      super(Component.m_237119_());
      this.f_96541_ = Minecraft.m_91087_();
      this.wantedPosterData = wantedData;
      this.name = this.wantedPosterData.getOwnerName();
      float scale = 1.0F;
      int nameLength = this.name.length();
      if (nameLength > 13) {
         int extraLetters = nameLength - 11;
         float exp = (float)extraLetters / 2.3F;
         float extraSpace = (float)Math.pow((double)0.89F, (double)exp);
         scale = 1.0F * extraSpace;
      }

      this.nameScale = scale;
      String background = this.wantedPosterData.getBackground();
      this.background = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/wantedposters/backgrounds/" + background + ".jpg");
      this.jollyRoger = (JollyRoger)this.wantedPosterData.getOwnerCrew().map((crew) -> crew.getJollyRoger()).orElse((Object)null);
      if (this.wantedPosterData.getOwnerProfile().isPresent()) {
         Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = Minecraft.m_91087_().m_91109_().m_118815_((GameProfile)this.wantedPosterData.getOwnerProfile().get());
         if (map.containsKey(Type.SKIN)) {
            this.skin = Minecraft.m_91087_().m_91109_().m_118825_((MinecraftProfileTexture)map.get(Type.SKIN), Type.SKIN);
         } else {
            this.skin = DefaultPlayerSkin.m_118627_(this.wantedPosterData.getOwnerUuid());
         }
      } else if (this.wantedPosterData.getOwnerTexture().isPresent()) {
         this.skin = (ResourceLocation)this.wantedPosterData.getOwnerTexture().get();
      } else {
         this.skin = DefaultPlayerSkin.m_118627_(this.wantedPosterData.getOwnerUuid());
      }

      Optional<Faction> faction = this.wantedPosterData.getFaction();
      this.isPirate = (Boolean)faction.map((fa) -> fa.equals(ModFactions.PIRATE.get())).orElse(false);
      this.isRevo = (Boolean)faction.map((fa) -> fa.equals(ModFactions.REVOLUTIONARY_ARMY.get())).orElse(false);
   }

   public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      this.m_280273_(graphics);
      graphics.m_280246_(1.0F, 1.0F, 1.0F, 1.0F);
      int posX = (this.f_96543_ - 256) / 2;
      int posY = (this.f_96544_ - 256) / 2;
      graphics.m_280168_().m_252880_((float)(posX + 60), (float)(posY + 10), 0.0F);
      graphics.m_280168_().m_252880_(128.0F, 128.0F, 512.0F);
      graphics.m_280168_().m_85841_(1.0F, 0.9F, 0.0F);
      graphics.m_280168_().m_252880_(-128.0F, -128.0F, -512.0F);
      graphics.m_280218_(ModResources.BOUNTY_POSTER_LARGE, 0, 0, 0, 0, 256, 256);
      graphics.m_280168_().m_252880_(67.0F, 150.0F, 0.0F);
      graphics.m_280168_().m_252880_(128.0F, 128.0F, 512.0F);
      graphics.m_280168_().m_85841_(1.5F, 1.6F, 0.0F);
      graphics.m_280168_().m_252880_(-128.0F, -128.0F, -512.0F);
      graphics.m_280168_().m_85836_();
      graphics.m_280168_().m_85841_(0.34F, 0.245F, 0.0F);
      graphics.m_280218_(this.background, 23, -57, 0, 0, 256, 256);
      graphics.m_280168_().m_85841_(4.25F, 5.5F, 0.0F);
      graphics.m_280218_(this.skin, 20, -3, 32, 32, 32, 32);
      graphics.m_280218_(this.skin, 20, -3, 160, 32, 32, 32);
      graphics.m_280168_().m_85836_();
      float scale = 0.08F;
      graphics.m_280168_().m_85841_(scale, scale, scale);
      graphics.m_280168_().m_252880_(550.0F, 190.0F, 0.0F);
      if (this.isPirate) {
         if (this.jollyRoger != null) {
            this.jollyRoger.render(graphics.m_280168_(), 0, 0, 1.0F);
         }
      } else if (this.isRevo) {
         graphics.m_280168_().m_252880_(-120.0F, -80.0F, 0.0F);
         graphics.m_280168_().m_85841_(2.0F, 2.0F, 2.0F);
         graphics.m_280218_(ModResources.REVOLUTIONARY_ARMY_ICON, 0, 0, 256, 256, 256, 256);
      }

      graphics.m_280168_().m_85849_();
      if (this.wantedPosterData.isExpired()) {
         graphics.m_280168_().m_85836_();
         scale = 0.2F;
         graphics.m_280168_().m_85841_(scale + 0.022F, scale, scale);
         graphics.m_280168_().m_252880_(50.0F, -47.0F, 0.0F);
         graphics.m_280218_(ModResources.EXPIRED, 0, 0, 16, 16, 256, 256);
         graphics.m_280168_().m_85849_();
      }

      graphics.m_280168_().m_85849_();
      graphics.m_280218_(ModResources.CURRENCIES, -2, 63, 0, 0, 32, 32);
      graphics.m_280168_().m_85836_();
      graphics.m_280168_().m_85837_((double)45.0F - (double)this.f_96541_.f_91062_.m_92895_(this.name) / (double)2.0F * (double)this.nameScale, (double)62.0F, (double)0.0F);
      graphics.m_280168_().m_85841_(this.nameScale, this.nameScale, this.nameScale);
      Font var10001 = this.f_96547_;
      String var10002 = String.valueOf(ChatFormatting.BOLD);
      graphics.m_280056_(var10001, var10002 + this.name, 0, 0, -11455469, false);
      graphics.m_280168_().m_85849_();
      boolean flag = this.wantedPosterData.getBountyString().length() > 10;
      if (flag) {
         graphics.m_280168_().m_85836_();
         graphics.m_280168_().m_252880_(-21.0F, -5.0F, 0.0F);
         graphics.m_280168_().m_252880_(128.0F, 128.0F, 512.0F);
         graphics.m_280168_().m_85841_(0.82F, 0.89F, 0.0F);
         graphics.m_280168_().m_252880_(-128.0F, -128.0F, -512.0F);
      }

      graphics.m_280056_(this.f_96547_, String.valueOf(ChatFormatting.BOLD) + this.wantedPosterData.getBountyString(), 22, 76, -11455469, false);
      if (flag) {
         graphics.m_280168_().m_85849_();
      }

      graphics.m_280168_().m_252880_(-24.0F, -2.0F, 0.0F);
      graphics.m_280168_().m_252880_(128.0F, 128.0F, 512.0F);
      graphics.m_280168_().m_85841_(0.78F, 0.92F, 0.0F);
      graphics.m_280168_().m_252880_(-128.0F, -128.0F, -512.0F);
      float datePosX = (float)(36 - this.f_96541_.f_91062_.m_92895_(this.wantedPosterData.getDate()) / 2);
      graphics.drawString(this.f_96547_, String.valueOf(ChatFormatting.BOLD) + this.wantedPosterData.getDate(), datePosX, 89.0F, -11455469, false);
      super.m_88315_(graphics, mouseX, mouseY, partialTicks);
   }

   public boolean m_7043_() {
      return false;
   }
}
