package xyz.pixelatedw.mineminenomi.ui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.LinkedHashSet;
import java.util.Set;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import xyz.pixelatedw.mineminenomi.api.factions.IFactionRank;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.ChallengeCapability;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.IChallengeData;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.data.entity.quest.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quest.QuestCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.handlers.world.RandomizedFruitsHandler;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.packets.client.CRequestSyncQuestDataPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenAbilitiesListScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenAbilityTreeScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenChallengesScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenCrewScreenPacket;
import xyz.pixelatedw.mineminenomi.ui.widget.PlankButton;

public class PlayerStatsScreen extends Screen {
   private static final ItemStack YAMI_STACK;
   private final AbstractClientPlayer player;
   private IEntityStats entityStatsProps;
   private IDevilFruit devilFruitProps;
   private IChallengeData challengesProps;
   private IQuestData questProps;
   private IAbilityData abilityProps;
   private final double doriki;
   private final boolean hasQuestsConfigEnabled;
   private final int questAmount;
   private final boolean hasChallengesConfigEnabled;
   private final int challengeAmount;
   private final boolean isInCombat;
   private final boolean isInChallengeDimension;
   private final int invites;
   private final boolean hasCrew;
   private Component colaLabel;
   private Component dorikiLabel;
   private Component factionLabel;
   private Component raceLabel;
   private Component styleLabel;
   private Component bellyLabel;
   private Component extolLabel;

   public PlayerStatsScreen(double doriki, boolean hasQuests, int questAmount, boolean hasChallenges, int challengeAmount, boolean isInCombat, boolean isInChallengeDimension, int invites, boolean hasCrew) {
      super(Component.m_237119_());
      this.player = Minecraft.m_91087_().f_91074_;
      this.doriki = doriki;
      this.hasQuestsConfigEnabled = hasQuests;
      this.questAmount = questAmount;
      this.hasChallengesConfigEnabled = hasChallenges;
      this.challengeAmount = challengeAmount;
      this.isInCombat = isInCombat;
      this.isInChallengeDimension = isInChallengeDimension;
      this.invites = invites;
      this.hasCrew = hasCrew;
      this.entityStatsProps = (IEntityStats)EntityStatsCapability.get(this.player).orElse((Object)null);
      this.devilFruitProps = (IDevilFruit)DevilFruitCapability.get(this.player).orElse((Object)null);
      this.challengesProps = (IChallengeData)ChallengeCapability.get(this.player).orElse((Object)null);
      this.questProps = (IQuestData)QuestCapability.get(this.player).orElse((Object)null);
      this.abilityProps = (IAbilityData)AbilityCapability.get(this.player).orElse((Object)null);

      try {
         this.initLabels();
      } catch (Exception ex) {
         ex.printStackTrace();
      }

   }

   public void m_88315_(GuiGraphics graphics, int x, int y, float f) {
      this.m_280273_(graphics);
      int posX = (this.f_96543_ - 256) / 2;
      int posY = (this.f_96544_ - 256) / 2;
      if (this.entityStatsProps.isCyborg()) {
         RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (Component)this.colaLabel, posX - 50, posY + 30, -1);
      }

      RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (Component)this.dorikiLabel, posX - 50, posY + 50, -1);
      RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (Component)this.factionLabel, posX - 50, posY + 70, -1);
      RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (Component)this.raceLabel, posX - 50, posY + 90, -1);
      RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (Component)this.styleLabel, posX - 50, posY + 110, -1);
      if (this.entityStatsProps.getBelly() > 0L) {
         RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (Component)this.bellyLabel, posX + 215, posY + 52, -1);
         graphics.m_280218_(ModResources.CURRENCIES, posX + 190, posY + 40, 0, 32, 32, 64);
      }

      if (this.entityStatsProps.getExtol() > 0L) {
         RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (Component)this.extolLabel, posX + 215, posY + 82, -1);
         graphics.m_280218_(ModResources.CURRENCIES, posX + 190, posY + 69, 34, 32, 64, 86);
      }

      if (this.devilFruitProps.hasAnyDevilFruit()) {
         Item var8 = this.devilFruitProps.getDevilFruitItem();
         if (var8 instanceof AkumaNoMiItem) {
            AkumaNoMiItem fruitItem = (AkumaNoMiItem)var8;
            ItemStack mainFruit = new ItemStack(RandomizedFruitsHandler.Client.HAS_RANDOMIZED_FRUIT ? fruitItem.getReverseShiftedFruit() : fruitItem);
            boolean hasYamiSecondary = this.devilFruitProps.getDevilFruitItem() != ModFruits.YAMI_YAMI_NO_MI.get() && this.devilFruitProps.hasYamiPower();
            if (!mainFruit.m_41619_()) {
               String mainFruitName = fruitItem.getDevilFruitName().getString();
               int color = -1;
               if (this.devilFruitProps.hasAwakenedFruit()) {
                  color = 15509033;
               }

               StringBuilder sb = new StringBuilder();
               sb.append(mainFruitName);
               if (hasYamiSecondary) {
                  sb.append(" + " + ((AkumaNoMiItem)ModFruits.YAMI_YAMI_NO_MI.get()).getDevilFruitName().getString());
                  graphics.m_280480_(YAMI_STACK, posX - 56, posY + 147);
               }

               RendererHelper.drawStringWithBorder(this.f_96547_, graphics, String.valueOf(ChatFormatting.BOLD) + sb.toString(), posX - 28, posY + 154, color);
               graphics.m_280480_(mainFruit, posX - 50, posY + 150);
            } else {
               RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (String)"§4§lUnknown Fruit§r", posX - 28, posY + 154, -1);
            }
         }
      }

      super.m_88315_(graphics, x, y, f);
   }

   public void m_7856_() {
      this.initLabels();
      Set<Button> buttons = new LinkedHashSet();
      PlankButton abilitiesButton = new PlankButton(0, 0, 80, 26, ModI18n.GUI_ABILITIES, (b) -> ModNetwork.sendToServer(new COpenAbilitiesListScreenPacket()));
      boolean hasAbilities = this.abilityProps.countUnlockedAbilities() > 0;
      if (!hasAbilities) {
         abilitiesButton.f_93623_ = false;
      }

      buttons.add(abilitiesButton);
      if (this.hasQuestsConfigEnabled) {
         boolean hasQuests = this.questAmount > 0;
         Tooltip tooltip = null;
         if (this.questAmount <= 0) {
            tooltip = Tooltip.m_257550_(ModI18n.GUI_NO_QUESTS_AVAILABLE);
         }

         PlankButton questsButton = new PlankButton(0, 0, 80, 26, ModI18n.GUI_QUESTS, (b) -> ModNetwork.sendToServer(new CRequestSyncQuestDataPacket(true)));
         questsButton.m_257544_(tooltip);
         if (!hasQuests) {
            questsButton.f_93623_ = false;
         }

         buttons.add(questsButton);
      }

      PlankButton abilityTreeButton = new PlankButton(0, 0, 80, 26, ModI18n.GUI_ABILITY_TREE, (b) -> ModNetwork.sendToServer(new COpenAbilityTreeScreenPacket()));
      buttons.add(abilityTreeButton);
      if (this.entityStatsProps.isPirate()) {
         PlankButton crewButton = new PlankButton(0, 0, 80, 26, ModI18n.GUI_CREW, (b) -> ModNetwork.sendToServer(new COpenCrewScreenPacket()));
         if (!this.hasCrew) {
            crewButton.f_93623_ = false;
         }

         buttons.add(crewButton);
      }

      if (this.hasChallengesConfigEnabled) {
         boolean challengesAmountCheck = this.challengeAmount > 0;
         Tooltip tooltip = null;
         if (!challengesAmountCheck) {
            tooltip = Tooltip.m_257550_(ModI18n.GUI_NO_CHALLENGES_AVAILABLE);
         } else if (this.isInChallengeDimension) {
            tooltip = Tooltip.m_257550_(ModI18n.GUI_CHALLENGE_DIM_ACCESS_MENU);
         } else if (this.isInCombat) {
            tooltip = Tooltip.m_257550_(ModI18n.GUI_COMBAT_ACCESS_MENU);
         }

         PlankButton challengesButton = new PlankButton(0, 0, 80, 26, ModI18n.GUI_CHALLENGES, (b) -> ModNetwork.sendToServer(new COpenChallengesScreenPacket()));
         challengesButton.m_257544_(tooltip);
         if (!challengesAmountCheck || this.isInCombat || this.isInChallengeDimension) {
            challengesButton.f_93623_ = false;
         }

         buttons.add(challengesButton);
      }

      int posX = this.f_96543_ / 2 - 190;
      int posY = this.f_96544_ - this.f_96544_ / 4 - 10;
      int i = 0;

      for(Button btn : buttons) {
         btn.m_252865_(posX + i * 60);
         btn.m_253211_(posY + i % 2 * 30);
         this.m_142416_(btn);
         ++i;
      }

   }

   public boolean m_7043_() {
      return false;
   }

   private void initLabels() {
      int var10000 = this.entityStatsProps.getCola();
      Component actualCola = Component.m_237113_(": " + var10000 + " / " + this.entityStatsProps.getMaxCola()).m_6270_(Style.f_131099_.m_131136_(false));
      this.colaLabel = ModI18n.GUI_COLA.m_6881_().m_6270_(Style.f_131099_.m_131136_(true)).m_7220_(actualCola);
      Component actualDoriki = Component.m_237113_(": " + Math.round(this.doriki)).m_6270_(Style.f_131099_.m_131136_(false));
      this.dorikiLabel = ModI18n.GUI_DORIKI.m_6881_().m_6270_(Style.f_131099_.m_131136_(true)).m_7220_(actualDoriki);
      String actualRank = (String)this.entityStatsProps.getRank().map((rank) -> " - " + ((IFactionRank)rank).getLocalizedName().getString()).orElse("");
      String var7 = ((Component)this.entityStatsProps.getFaction().map((entry) -> entry.getLabel()).orElse(ModI18n.FACTION_EMPTY)).getString();
      String factionActual = var7 + actualRank;
      String raceActual = ((Component)this.entityStatsProps.getRace().map((entry) -> entry.getLabel()).orElse(ModI18n.FACTION_EMPTY)).getString();
      String styleActual = ((Component)this.entityStatsProps.getFightingStyle().map((entry) -> entry.getLabel()).orElse(ModI18n.FACTION_EMPTY)).getString();
      String var10001 = String.valueOf(ChatFormatting.BOLD);
      this.factionLabel = Component.m_237113_(var10001 + ModI18n.GUI_FACTION.getString() + ": " + String.valueOf(ChatFormatting.RESET) + factionActual);
      var10001 = String.valueOf(ChatFormatting.BOLD);
      this.raceLabel = Component.m_237113_(var10001 + ModI18n.GUI_RACE.getString() + ": " + String.valueOf(ChatFormatting.RESET) + raceActual);
      var10001 = String.valueOf(ChatFormatting.BOLD);
      this.styleLabel = Component.m_237113_(var10001 + ModI18n.GUI_STYLE.getString() + ": " + String.valueOf(ChatFormatting.RESET) + styleActual);
      this.bellyLabel = Component.m_237113_("" + this.entityStatsProps.getBelly());
      this.extolLabel = Component.m_237113_("" + this.entityStatsProps.getExtol());
   }

   private void drawItemStack(PoseStack matrixStack, ItemStack itemStack, int x, int y, String string) {
   }

   public static void open(double doriki, boolean hasQuests, int questAmount, boolean hasChallenges, int challengeAmount, boolean isInCombat, boolean isInChallengeDimension, int invites, boolean hasCrew) {
      Minecraft.m_91087_().m_91152_(new PlayerStatsScreen(doriki, hasQuests, questAmount, hasChallenges, challengeAmount, isInCombat, isInChallengeDimension, invites, hasCrew));
   }

   static {
      YAMI_STACK = new ItemStack((ItemLike)ModFruits.YAMI_YAMI_NO_MI.get());
   }
}
