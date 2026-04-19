package xyz.pixelatedw.mineminenomi.datagen.advancements;

import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import xyz.pixelatedw.mineminenomi.abilities.BellyFlopAbility;
import xyz.pixelatedw.mineminenomi.abilities.PearlFireAbility;
import xyz.pixelatedw.mineminenomi.abilities.ShakushiAbility;
import xyz.pixelatedw.mineminenomi.abilities.StealPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.bomu.BreezeBreathBombAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.TackleAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.CandleChampionAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.DoruDoruArtsMoriAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.KarakusagawaraSeikenAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.MizuTaihoAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.abilities.kilo.KiloPress1Ability;
import xyz.pixelatedw.mineminenomi.abilities.mandemontactics.DemonicDanceAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.GrandeSablesAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.SablesAbility;
import xyz.pixelatedw.mineminenomi.abilities.supa.SparClawAbility;
import xyz.pixelatedw.mineminenomi.abilities.supa.SpiralHollowAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDisplayInfo;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.data.triggers.CompleteChallengeTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.UnlockChallengeTrigger;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAdvancements;

public class ChallengeAdvancements implements ForgeAdvancementProvider.AdvancementGenerator {
   private static final ResourceLocation BACKGROUND = ResourceLocation.parse("textures/gui/advancements/backgrounds/adventure.png");
   private ExistingFileHelper fileHelper;

   public ChallengeAdvancements(ExistingFileHelper fileHelper) {
      this.fileHelper = fileHelper;
   }

   public void generate(HolderLookup.Provider registries, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {
      Advancement root = Builder.m_138353_().m_138371_((ItemLike)ModBlocks.PONEGLYPH.get(), ModI18nAdvancements.CHALLENGES_ROOT.title(), ModI18nAdvancements.CHALLENGES_ROOT.description(), BACKGROUND, FrameType.TASK, false, false, false).m_138386_("unlock_challenge", UnlockChallengeTrigger.Instance.unlockAnyChallenge()).save(consumer, ResourceLocation.fromNamespaceAndPath("mineminenomi", "challenges/root"), this.fileHelper);
      Advancement higuma = this.standard(root, (ChallengeCore)ModChallenges.HIGUMA.get(), (ItemLike)ModWeapons.CUTLASS.get(), consumer);
      this.hard(higuma, (ChallengeCore)ModChallenges.HIGUMA_HARD.get(), (AbilityCore)ShiShishiSonsonAbility.INSTANCE.get(), consumer);
      ItemStack marineAxe = new ItemStack((ItemLike)ModWeapons.AXE.get());
      marineAxe.m_41698_("display").m_128405_("color", 33980);
      Advancement morgan = this.standard(root, (ChallengeCore)ModChallenges.MORGAN.get(), marineAxe, consumer);
      this.hard(morgan, (ChallengeCore)ModChallenges.MORGAN_HARD.get(), (AbilityCore)TackleAbility.INSTANCE.get(), consumer);
      Advancement kuro = this.standard(root, (ChallengeCore)ModChallenges.KURO.get(), (ItemLike)ModArmors.KURO_CHEST.get(), consumer);
      this.hard(kuro, (ChallengeCore)ModChallenges.KURO_HARD.get(), (AbilityCore)ShakushiAbility.INSTANCE.get(), consumer);
      Advancement nyanban = this.standard(root, (ChallengeCore)ModChallenges.NYANBAN_BROTHERS.get(), (AbilityCore)StealPunchAbility.INSTANCE.get(), consumer);
      this.hard(nyanban, (ChallengeCore)ModChallenges.NYANBAN_BROTHERS_HARD.get(), (AbilityCore)BellyFlopAbility.INSTANCE.get(), consumer);
      Advancement krieg = this.standard(root, (ChallengeCore)ModChallenges.DON_KRIEG.get(), (ItemLike)ModArmors.MH5_GAS_MASK.get(), consumer);
      this.hard(krieg, (ChallengeCore)ModChallenges.DON_KRIEG_HARD.get(), (ItemLike)ModWeapons.DAISENSO.get(), consumer);
      Advancement gin = this.standard(root, (ChallengeCore)ModChallenges.GIN.get(), (ItemLike)ModWeapons.TONFA.get(), consumer);
      this.hard(gin, (ChallengeCore)ModChallenges.GIN_HARD.get(), (AbilityCore)DemonicDanceAbility.INSTANCE.get(), consumer);
      Advancement pearl = this.standard(root, (ChallengeCore)ModChallenges.PEARL.get(), (ItemLike)ModArmors.PEARL_HAT.get(), consumer);
      this.hard(pearl, (ChallengeCore)ModChallenges.PEARL_HARD.get(), (AbilityCore)PearlFireAbility.INSTANCE.get(), consumer);
      Advancement arlong = this.standard(root, (ChallengeCore)ModChallenges.ARLONG.get(), (ItemLike)ModArmors.ARLONG_CHEST.get(), consumer);
      this.hard(arlong, (ChallengeCore)ModChallenges.ARLONG_HARD.get(), (ItemLike)ModWeapons.KIRIBACHI.get(), consumer);
      Advancement kuroobi = this.standard(root, (ChallengeCore)ModChallenges.KUROOBI.get(), (ItemLike)ModArmors.KUROOBI_CHEST.get(), consumer);
      this.hard(kuroobi, (ChallengeCore)ModChallenges.KUROOBI_HARD.get(), (AbilityCore)KarakusagawaraSeikenAbility.INSTANCE.get(), consumer);
      Advancement chew = this.standard(root, (ChallengeCore)ModChallenges.CHEW.get(), (ItemLike)ModArmors.CHEW_CHEST.get(), consumer);
      this.hard(chew, (ChallengeCore)ModChallenges.CHEW_HARD.get(), (AbilityCore)MizuTaihoAbility.INSTANCE.get(), consumer);
      Advancement mr0 = this.standard(root, (ChallengeCore)ModChallenges.MR_0.get(), (AbilityCore)SablesAbility.INSTANCE.get(), consumer);
      this.hard(mr0, (ChallengeCore)ModChallenges.MR_0_HARD.get(), (AbilityCore)GrandeSablesAbility.INSTANCE.get(), consumer);
      Advancement mr1 = this.standard(root, (ChallengeCore)ModChallenges.MR_1.get(), (AbilityCore)SparClawAbility.INSTANCE.get(), consumer);
      this.hard(mr1, (ChallengeCore)ModChallenges.MR_1_HARD.get(), (AbilityCore)SpiralHollowAbility.INSTANCE.get(), consumer);
      Advancement mr3 = this.standard(root, (ChallengeCore)ModChallenges.MR_3.get(), (AbilityCore)DoruDoruArtsMoriAbility.INSTANCE.get(), consumer);
      this.hard(mr3, (ChallengeCore)ModChallenges.MR_3_HARD.get(), (AbilityCore)CandleChampionAbility.INSTANCE.get(), consumer);
      Advancement mr5MissValentine = this.standard(root, (ChallengeCore)ModChallenges.MR_5_AND_MISS_VALENTINE.get(), (AbilityCore)KiloPress1Ability.INSTANCE.get(), consumer);
      this.hard(mr5MissValentine, (ChallengeCore)ModChallenges.MR_5_AND_MISS_VALENTINE_HARD.get(), (AbilityCore)BreezeBreathBombAbility.INSTANCE.get(), consumer);
   }

   private Advancement standard(Advancement parent, ChallengeCore<?> challengeCore, ItemLike itemProvider, Consumer<Advancement> consumer) {
      DisplayInfo displayInfo = new DisplayInfo(new ItemStack(itemProvider.m_5456_()), challengeCore.getLocalizedTitle(), challengeCore.getLocalizedObjective(), (ResourceLocation)null, FrameType.TASK, true, false, true);
      return this.challenge(parent, challengeCore, displayInfo, consumer);
   }

   private Advancement standard(Advancement parent, ChallengeCore<?> challengeCore, ItemStack itemStack, Consumer<Advancement> consumer) {
      DisplayInfo displayInfo = new DisplayInfo(itemStack, challengeCore.getLocalizedTitle(), challengeCore.getLocalizedObjective(), (ResourceLocation)null, FrameType.TASK, true, false, true);
      return this.challenge(parent, challengeCore, displayInfo, consumer);
   }

   private Advancement standard(Advancement parent, ChallengeCore<?> challengeCore, AbilityCore<?> abilityCore, Consumer<Advancement> consumer) {
      AbilityDisplayInfo displayInfo = new AbilityDisplayInfo(abilityCore, challengeCore.getLocalizedTitle(), challengeCore.getLocalizedObjective(), (ResourceLocation)null, FrameType.TASK, true, false, true);
      return this.challenge(parent, challengeCore, displayInfo, consumer);
   }

   private Advancement hard(Advancement parent, ChallengeCore<?> challengeCore, ItemLike itemProvider, Consumer<Advancement> consumer) {
      DisplayInfo displayInfo = new DisplayInfo(new ItemStack(itemProvider.m_5456_()), challengeCore.getLocalizedTitle(), challengeCore.getLocalizedObjective(), (ResourceLocation)null, FrameType.CHALLENGE, true, true, false);
      return this.challenge(parent, challengeCore, displayInfo, consumer);
   }

   private Advancement hard(Advancement parent, ChallengeCore<?> challengeCore, ItemStack itemStack, Consumer<Advancement> consumer) {
      DisplayInfo displayInfo = new DisplayInfo(itemStack, challengeCore.getLocalizedTitle(), challengeCore.getLocalizedObjective(), (ResourceLocation)null, FrameType.CHALLENGE, true, true, false);
      return this.challenge(parent, challengeCore, displayInfo, consumer);
   }

   private Advancement hard(Advancement parent, ChallengeCore<?> challengeCore, AbilityCore<?> abilityCore, Consumer<Advancement> consumer) {
      AbilityDisplayInfo displayInfo = new AbilityDisplayInfo(abilityCore, challengeCore.getLocalizedTitle(), challengeCore.getLocalizedObjective(), (ResourceLocation)null, FrameType.CHALLENGE, true, true, false);
      return this.challenge(parent, challengeCore, displayInfo, consumer);
   }

   private Advancement challenge(Advancement parent, ChallengeCore<?> challengeCore, DisplayInfo display, Consumer<Advancement> consumer) {
      return Builder.m_138353_().m_138398_(parent).m_138358_(display).m_138386_("unlock_challenge", CompleteChallengeTrigger.Instance.completeChallenge(challengeCore)).save(consumer, ResourceLocation.fromNamespaceAndPath("mineminenomi", "challenges/" + challengeCore.getId()), this.fileHelper);
   }
}
