package xyz.pixelatedw.mineminenomi.datagen.advancements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.advancements.critereon.KilledTrigger.TriggerInstance;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiEmissionAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.HaoshokuHakiInfusionAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.KenbunshokuHakiAuraAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.KenbunshokuHakiFutureSightAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDisplayInfo;
import xyz.pixelatedw.mineminenomi.data.triggers.DFEncyclopediaCompletionTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.ObtainBellyTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.SubtleTweaksTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.UnlockAbilityTrigger;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAdvancements;

public class AdventureAdvancements implements ForgeAdvancementProvider.AdvancementGenerator {
   private static final ResourceLocation BACKGROUND = ResourceLocation.parse("textures/gui/advancements/backgrounds/adventure.png");
   private ExistingFileHelper fileHelper;

   public AdventureAdvancements(ExistingFileHelper fileHelper) {
      this.fileHelper = fileHelper;
   }

   public void generate(HolderLookup.Provider registries, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {
      Advancement root = Builder.m_138353_().m_138371_((ItemLike)ModBlocks.FLAG.get(), Component.m_237115_("advancements.adventure.root.title"), Component.m_237115_("advancements.adventure.root.description"), BACKGROUND, FrameType.TASK, false, false, false).m_138360_(RequirementsStrategy.f_15979_).m_138386_("killed_something", TriggerInstance.m_48141_()).m_138386_("killed_by_something", TriggerInstance.m_48142_()).save(consumer, ResourceLocation.fromNamespaceAndPath("mineminenomi", "adventure/root"), this.fileHelper);
      Builder.m_138353_().m_138398_(root).m_138371_((ItemLike)ModItems.SEA_KING_MEAT.get(), ModI18nAdvancements.ADVENTURE_MEAT.title(), ModI18nAdvancements.ADVENTURE_MEAT.description(), (ResourceLocation)null, FrameType.TASK, true, false, false).m_138360_(RequirementsStrategy.f_15979_).m_138386_("sea_king_meat", net.minecraft.advancements.critereon.ConsumeItemTrigger.TriggerInstance.m_23703_((ItemLike)ModItems.SEA_KING_MEAT.get())).m_138386_("cooked_sea_king_meat", net.minecraft.advancements.critereon.ConsumeItemTrigger.TriggerInstance.m_23703_((ItemLike)ModItems.COOKED_SEA_KING_MEAT.get())).save(consumer, ResourceLocation.fromNamespaceAndPath("mineminenomi", "adventure/meat"), this.fileHelper);
      Advancement catBurglar = Builder.m_138353_().m_138398_(root).m_138371_((ItemLike)ModItems.BELLY_POUCH.get(), ModI18nAdvancements.ADVENTURE_CAT_BURGLAR.title(), ModI18nAdvancements.ADVENTURE_CAT_BURGLAR.description(), (ResourceLocation)null, FrameType.TASK, true, false, false).m_138386_("obtain_belly", ObtainBellyTrigger.Instance.collectBelly(100000)).save(consumer, ResourceLocation.fromNamespaceAndPath("mineminenomi", "adventure/cat_burglar"), this.fileHelper);
      Builder.m_138353_().m_138398_(catBurglar).m_138371_((ItemLike)ModItems.BELLY_POUCH.get(), ModI18nAdvancements.ADVENTURE_MY_TREASURE.title(), ModI18nAdvancements.ADVENTURE_MY_TREASURE.description(), (ResourceLocation)null, FrameType.CHALLENGE, true, true, true).m_138386_("obtain_belly", ObtainBellyTrigger.Instance.collectBelly(10000000)).save(consumer, ResourceLocation.fromNamespaceAndPath("mineminenomi", "adventure/my_treasure"), this.fileHelper);
      AbilityDisplayInfo whatsKairosekiDisplayInfo = new AbilityDisplayInfo((AbilityCore)BusoshokuHakiHardeningAbility.INSTANCE.get(), ((Item)ModItems.KEY.get()).m_7968_(), ModI18nAdvancements.ADVENTURE_WHATS_KAIROSEKI.title(), ModI18nAdvancements.ADVENTURE_WHATS_KAIROSEKI.description(), (ResourceLocation)null, FrameType.GOAL, true, false, false);
      Advancement whatsKairoseki = Builder.m_138353_().m_138398_(root).m_138358_(whatsKairosekiDisplayInfo).m_138360_(RequirementsStrategy.f_15979_).m_138386_("unlock_busoshoku_hardening", UnlockAbilityTrigger.Instance.ability((AbilityCore)BusoshokuHakiHardeningAbility.INSTANCE.get())).m_138386_("unlock_busoshoku_imbuing", UnlockAbilityTrigger.Instance.ability((AbilityCore)BusoshokuHakiImbuingAbility.INSTANCE.get())).m_138386_("unlock_kenbunshoku_aura", UnlockAbilityTrigger.Instance.ability((AbilityCore)KenbunshokuHakiAuraAbility.INSTANCE.get())).save(consumer, ResourceLocation.fromNamespaceAndPath("mineminenomi", "adventure/whats_kairoseki"), this.fileHelper);
      AbilityDisplayInfo hakiMasterDisplayInfo = new AbilityDisplayInfo((AbilityCore)HaoshokuHakiInfusionAbility.INSTANCE.get(), ((Item)ModItems.KEY.get()).m_7968_(), ModI18nAdvancements.ADVENTURE_HAKI_MASTER.title(), ModI18nAdvancements.ADVENTURE_HAKI_MASTER.description(), (ResourceLocation)null, FrameType.GOAL, true, false, false);
      Builder.m_138353_().m_138398_(whatsKairoseki).m_138358_(hakiMasterDisplayInfo).m_138360_(RequirementsStrategy.f_15978_).m_138386_("unlock_busoshoku_hardening", UnlockAbilityTrigger.Instance.ability((AbilityCore)BusoshokuHakiHardeningAbility.INSTANCE.get())).m_138386_("unlock_busoshoku_imbuing", UnlockAbilityTrigger.Instance.ability((AbilityCore)BusoshokuHakiImbuingAbility.INSTANCE.get())).m_138386_("unlock_busoshoku_fullbody", UnlockAbilityTrigger.Instance.ability((AbilityCore)BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get())).m_138386_("unlock_busoshoku_emission", UnlockAbilityTrigger.Instance.ability((AbilityCore)BusoshokuHakiEmissionAbility.INSTANCE.get())).m_138386_("unlock_busoshoku_internal_destruction", UnlockAbilityTrigger.Instance.ability((AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get())).m_138386_("unlock_kenbunshoku_aura", UnlockAbilityTrigger.Instance.ability((AbilityCore)KenbunshokuHakiAuraAbility.INSTANCE.get())).m_138386_("unlock_kenbunshoku_future_sight", UnlockAbilityTrigger.Instance.ability((AbilityCore)KenbunshokuHakiFutureSightAbility.INSTANCE.get())).save(consumer, ResourceLocation.fromNamespaceAndPath("mineminenomi", "adventure/haki_master"), this.fileHelper);
      Map<MobEffect, MobEffectsPredicate.MobEffectInstancePredicate> drunk4EffectsMap = new HashMap();
      drunk4EffectsMap.put((MobEffect)ModEffects.DRUNK.get(), new MobEffectsPredicate.MobEffectInstancePredicate(Ints.m_55386_(4), Ints.f_55364_, (Boolean)null, (Boolean)null));
      Advancement whyRumGone = Builder.m_138353_().m_138398_(root).m_138371_((ItemLike)ModItems.BOTTLE_OF_RUM.get(), ModI18nAdvancements.ADVENTURE_WHY_IS_THE_RUM_GONE.title(), ModI18nAdvancements.ADVENTURE_WHY_IS_THE_RUM_GONE.description(), (ResourceLocation)null, FrameType.TASK, true, false, true).m_138386_("the_rum_is_indeed_gone", net.minecraft.advancements.critereon.EffectsChangedTrigger.TriggerInstance.m_26780_(new MobEffectsPredicate(drunk4EffectsMap))).save(consumer, ResourceLocation.fromNamespaceAndPath("mineminenomi", "adventure/why_is_the_rum_gone"), this.fileHelper);
      ContextAwarePredicate grogPlayerPredicate = ContextAwarePredicate.m_286108_(new LootItemCondition[]{LootItemEntityPropertyCondition.m_81867_(EntityTarget.THIS, net.minecraft.advancements.critereon.EntityPredicate.Builder.m_36633_().m_36652_(new MobEffectsPredicate(drunk4EffectsMap)).m_36662_()).m_6409_()});
      ItemPredicate grogItemPredicate1 = net.minecraft.advancements.critereon.ItemPredicate.Builder.m_45068_().m_151445_(new ItemLike[]{(ItemLike)ModItems.BOTTLE_OF_RUM.get()}).m_45077_();
      ItemPredicate grogItemPredicate2 = net.minecraft.advancements.critereon.ItemPredicate.Builder.m_45068_().m_151445_(new ItemLike[]{(ItemLike)ModItems.SAKE_BOTTLE.get()}).m_45077_();
      Builder.m_138353_().m_138398_(whyRumGone).m_138371_((ItemLike)ModItems.BOTTLE_OF_RUM.get(), ModI18nAdvancements.ADVENTURE_ALL_FOR_ME_GROG.title(), ModI18nAdvancements.ADVENTURE_ALL_FOR_ME_GROG.description(), (ResourceLocation)null, FrameType.CHALLENGE, true, false, true).m_138360_(RequirementsStrategy.f_15979_).m_138386_("me_jolly_jolly_rum_grog", new ConsumeItemTrigger.TriggerInstance(grogPlayerPredicate, grogItemPredicate1)).m_138386_("me_jolly_jolly_sake_grog", new ConsumeItemTrigger.TriggerInstance(grogPlayerPredicate, grogItemPredicate2)).save(consumer, ResourceLocation.fromNamespaceAndPath("mineminenomi", "adventure/all_for_me_grog"), this.fileHelper);
      CompoundTag cloneNbtTest = new CompoundTag();
      cloneNbtTest.m_128379_("isClone", false);
      ItemPredicate supremeGradePredicate = net.minecraft.advancements.critereon.ItemPredicate.Builder.m_45068_().m_204145_(ModTags.Items.SUPREME_GRADE).m_45075_(cloneNbtTest).m_45077_();
      Builder.m_138353_().m_138398_(root).m_138371_((ItemLike)ModWeapons.ACE.get(), ModI18nAdvancements.ADVENTURE_PRICELESS_BLADE.title(), ModI18nAdvancements.ADVENTURE_PRICELESS_BLADE.description(), (ResourceLocation)null, FrameType.CHALLENGE, true, false, false).m_138360_(RequirementsStrategy.f_15979_).m_138386_("obtain_supreme_grade", net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.m_43197_(new ItemPredicate[]{supremeGradePredicate})).save(consumer, ResourceLocation.fromNamespaceAndPath("mineminenomi", "adventure/priceless_blade"), this.fileHelper);
      Advancement halfwayThere = Builder.m_138353_().m_138398_(root).m_138371_((ItemLike)ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get(), ModI18nAdvancements.ADVENTURE_HALFWAY_THERE.title(), ModI18nAdvancements.ADVENTURE_HALFWAY_THERE.description(), (ResourceLocation)null, FrameType.CHALLENGE, true, false, true).m_138386_("encyclopedia_completion", DFEncyclopediaCompletionTrigger.Instance.completion(0.5F)).save(consumer, ResourceLocation.fromNamespaceAndPath("mineminenomi", "adventure/halfway_there"), this.fileHelper);
      Builder.m_138353_().m_138398_(halfwayThere).m_138371_((ItemLike)ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get(), ModI18nAdvancements.ADVENTURE_ONE_FOR_THE_BOOKS.title(), ModI18nAdvancements.ADVENTURE_ONE_FOR_THE_BOOKS.description(), (ResourceLocation)null, FrameType.CHALLENGE, true, true, true).m_138386_("encyclopedia_completion", DFEncyclopediaCompletionTrigger.Instance.completion(1.0F)).save(consumer, ResourceLocation.fromNamespaceAndPath("mineminenomi", "adventure/one_for_the_books"), this.fileHelper);
      Builder.m_138353_().m_138398_(root).m_138371_((ItemLike)ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get(), ModI18nAdvancements.ADVENTURE_SUBTLE_TWEAKS.title(), ModI18nAdvancements.ADVENTURE_SUBTLE_TWEAKS.description(), (ResourceLocation)null, FrameType.CHALLENGE, true, false, true).m_138386_("subtle_tweaks", SubtleTweaksTrigger.Instance.create()).save(consumer, ResourceLocation.fromNamespaceAndPath("mineminenomi", "adventure/subtle_tweaks"), this.fileHelper);
      ItemPredicate emptyItemPredicate = net.minecraft.advancements.critereon.ItemPredicate.Builder.m_45068_().m_151445_(new ItemLike[]{Items.f_41852_}).m_45077_();
      EntityEquipmentPredicate emptyHandsPredicate = new EntityEquipmentPredicate(ItemPredicate.f_45028_, ItemPredicate.f_45028_, ItemPredicate.f_45028_, ItemPredicate.f_45028_, emptyItemPredicate, ItemPredicate.f_45028_);
      EntityPredicate drunkenFistTargetPredicate = net.minecraft.advancements.critereon.EntityPredicate.Builder.m_36633_().m_36652_(new MobEffectsPredicate(drunk4EffectsMap)).m_36640_(emptyHandsPredicate).m_36662_();
      DamageSourcePredicate drunkenFistPredicate = new DamageSourcePredicate(new ArrayList(), EntityPredicate.f_36550_, drunkenFistTargetPredicate);
      Builder.m_138353_().m_138398_(whyRumGone).m_138371_((ItemLike)ModItems.BOTTLE_OF_RUM.get(), ModI18nAdvancements.ADVENTURE_DRUNKEN_FIST.title(), ModI18nAdvancements.ADVENTURE_DRUNKEN_FIST.description(), (ResourceLocation)null, FrameType.CHALLENGE, true, false, true).m_138386_("drunked_fist_kill", new KilledTrigger.TriggerInstance(CriteriaTriggers.f_10568_.m_7295_(), ContextAwarePredicate.f_285567_, ContextAwarePredicate.f_285567_, drunkenFistPredicate)).save(consumer, ResourceLocation.fromNamespaceAndPath("mineminenomi", "adventure/drunken_fist"), this.fileHelper);
      Builder.m_138353_().m_138398_(root).m_138371_((ItemLike)ModBlocks.PONEGLYPH.get(), ModI18nAdvancements.ADVENTURE_PAST_MEMORIES.title(), ModI18nAdvancements.ADVENTURE_PAST_MEMORIES.description(), (ResourceLocation)null, FrameType.GOAL, true, false, false).m_138386_("unlock_challenge", net.minecraft.advancements.critereon.ItemUsedOnLocationTrigger.TriggerInstance.m_285945_(net.minecraft.advancements.critereon.LocationPredicate.Builder.m_52651_().m_52652_(net.minecraft.advancements.critereon.BlockPredicate.Builder.m_17924_().m_146726_(new Block[]{(Block)ModBlocks.PONEGLYPH.get()}).m_17931_()), net.minecraft.advancements.critereon.ItemPredicate.Builder.m_45068_().m_151445_(new ItemLike[]{Items.f_42516_}))).save(consumer, ResourceLocation.fromNamespaceAndPath("mineminenomi", "adventure/past_memories"), this.fileHelper);
   }
}
