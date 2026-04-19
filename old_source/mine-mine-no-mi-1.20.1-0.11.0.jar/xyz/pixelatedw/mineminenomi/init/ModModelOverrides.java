package xyz.pixelatedw.mineminenomi.init;

import com.google.common.base.Strings;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import xyz.pixelatedw.mineminenomi.abilities.gasu.BlueSwordAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.handlers.world.RandomizedFruitsHandler;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class ModModelOverrides {
   public static final ResourceLocation ALT_TEXTURE_PROPERTY = ResourceLocation.parse("alt_texture");
   public static final ResourceLocation SIZE_PROPERTY = ResourceLocation.parse("size");
   public static final ResourceLocation FILLED_PROPERTY = ResourceLocation.parse("fillzed");
   public static final ResourceLocation DEN_TYPE_PROPERTY = ResourceLocation.parse("den_type");
   public static final ResourceLocation OPEN_PROPERTY = ResourceLocation.parse("open");
   public static final ResourceLocation PULL_PROPERTY = ResourceLocation.parse("pull");
   public static final ResourceLocation PULLING_PROPERTY = ResourceLocation.parse("pulling");
   public static final ResourceLocation FRUIT_TYPE_PROPERTY = ResourceLocation.parse("type");
   public static final ResourceLocation HAKI_PROPERTY = ResourceLocation.parse("haki");
   public static final ResourceLocation SHEATHED_PROPERTY = ResourceLocation.parse("sheathed");
   public static final ResourceLocation APRIL_PROPERTY = ResourceLocation.parse("april");
   private static final ClampedItemPropertyFunction DEN_DEN_MUSHI = (itemStack, world, livingEntity, i) -> (float)itemStack.m_41784_().m_128451_("type") / 2.0F;
   private static final ClampedItemPropertyFunction BLUE_GORO = (itemStack, world, livingEntity, i) -> {
      if (ServerConfig.getRandomizedFruits()) {
         return 0.0F;
      } else {
         return ClientConfig.isGoroBlue() ? 1.0F : 0.0F;
      }
   };
   private static final ItemPropertyFunction BELLY_POUCH_SIZE = (itemStack, world, livingEntity, i) -> {
      if (livingEntity == null) {
         return 0.0F;
      } else {
         long belly = itemStack.m_41784_().m_128454_("belly");
         int size = 0;
         if (belly > 1000L && belly < 5000L) {
            size = 1;
         } else if (belly >= 5000L) {
            size = 2;
         }

         return (float)size;
      }
   };
   private static final ItemPropertyFunction EXTOL_POUCH_SIZE = (itemStack, world, livingEntity, i) -> {
      if (livingEntity == null) {
         return 0.0F;
      } else {
         long extol = itemStack.m_41784_().m_128454_("extol");
         long belly = Currency.getBellyFromExtol(extol);
         int size = 0;
         if (belly > 1000L && belly < 5000L) {
            size = 1;
         } else if (belly >= 5000L) {
            size = 2;
         }

         return (float)size;
      }
   };
   private static final ItemPropertyFunction DEVIL_FRUITS_RANDOMIZER = (itemStack, world, livingEntity, i) -> {
      if (itemStack != null && !itemStack.m_41619_()) {
         Item patt3582$temp = itemStack.m_41720_();
         if (patt3582$temp instanceof AkumaNoMiItem) {
            AkumaNoMiItem fruit = (AkumaNoMiItem)patt3582$temp;
            if (!RandomizedFruitsHandler.Client.HAS_RANDOMIZED_FRUIT) {
               fruit.removeBaseColor(itemStack);
               fruit.removeStemColor(itemStack);
               return 0.0F;
            }

            if (world == null && livingEntity != null) {
               world = (ClientLevel)livingEntity.m_9236_();
            }

            fruit.applyRandomness(world, itemStack);
            return (float)itemStack.m_41784_().m_128451_("type");
         }
      }

      return 1.0F;
   };
   private static final ClampedItemPropertyFunction SAKE_CUP_FILLED = (itemStack, world, livingEntity, i) -> (float)(itemStack.m_41783_() != null && !Strings.isNullOrEmpty(itemStack.m_41783_().m_128461_("leader")) ? 1 : 0);
   private static final ClampedItemPropertyFunction SHEATHED_WEAPON = (itemStack, world, livingEntity, i) -> {
      if (livingEntity == null) {
         return 1.0F;
      } else {
         boolean mainHandFlag = ItemStack.m_41656_(livingEntity.m_21205_(), itemStack);
         boolean offHandFlag = ItemStack.m_41656_(livingEntity.m_21206_(), itemStack);
         boolean hasWeapon = mainHandFlag || offHandFlag;
         IAbilityData props = (IAbilityData)AbilityCapability.get(livingEntity).orElse((Object)null);
         if (hasWeapon && props != null) {
            ShiShishiSonsonAbility abl = (ShiShishiSonsonAbility)props.getEquippedAbility((AbilityCore)ShiShishiSonsonAbility.INSTANCE.get());
            if (abl != null && abl.isCharging()) {
               return 1.0F;
            }
         }

         return hasWeapon ? 0.0F : 1.0F;
      }
   };
   private static final ClampedItemPropertyFunction HAKI_WEAPON = (itemStack, world, livingEntity, i) -> {
      if (livingEntity == null) {
         return 0.0F;
      } else {
         boolean hakiActiveFlag = itemStack.m_41782_() && itemStack.m_41783_().m_128441_("imbuingHakiActive");
         return hakiActiveFlag ? 1.0F : 0.0F;
      }
   };
   private static final ClampedItemPropertyFunction UMBRELLA_OPEN = (itemStack, world, livingEntity, i) -> {
      if (livingEntity == null) {
         return 0.0F;
      } else {
         boolean mainHandFlag = livingEntity.m_21205_() == itemStack;
         boolean offHandFlag = livingEntity.m_21206_() == itemStack;
         boolean isInAir = !livingEntity.m_20096_() && livingEntity.m_20184_().f_82480_ < (double)0.0F;
         BlockPos blockpos = livingEntity.m_20183_();
         BlockPos entityHeadPos = new BlockPos(blockpos.m_123341_(), (int)livingEntity.m_20191_().f_82292_, blockpos.m_123343_());
         boolean isRaining = livingEntity.m_9236_().m_46758_(blockpos) || livingEntity.m_9236_().m_46758_(entityHeadPos);
         boolean checkMainHand = !livingEntity.m_21205_().m_41619_() && livingEntity.m_21205_().m_41720_() == ModWeapons.UMBRELLA.get();
         boolean checkOffHand = !livingEntity.m_21206_().m_41619_() && livingEntity.m_21206_().m_41720_() == ModWeapons.UMBRELLA.get();
         boolean holdsUmbrella = checkMainHand || checkOffHand;
         return (!mainHandFlag && !offHandFlag || !isInAir) && (!isRaining || !holdsUmbrella) ? 0.0F : 1.0F;
      }
   };
   private static final ClampedItemPropertyFunction BLUE_SWORD_WEAPON = (itemStack, world, livingEntity, i) -> {
      if (livingEntity == null) {
         return 1.0F;
      } else {
         boolean mainHandFlag = ItemStack.m_41656_(livingEntity.m_21205_(), itemStack);
         boolean offHandFlag = ItemStack.m_41656_(livingEntity.m_21206_(), itemStack);
         boolean hasAbl = (Boolean)AbilityCapability.get(livingEntity).map((props) -> props.hasUnlockedAbility((AbilityCore)BlueSwordAbility.INSTANCE.get())).orElse(false);
         return (mainHandFlag || offHandFlag) && hasAbl ? 0.0F : 1.0F;
      }
   };
   private static final ClampedItemPropertyFunction PULL = (itemStack, world, livingEntity, i) -> {
      if (livingEntity == null) {
         return 0.0F;
      } else {
         return livingEntity.m_21211_() != itemStack ? 0.0F : (float)(itemStack.m_41779_() - livingEntity.m_21212_()) / 20.0F;
      }
   };
   private static final ClampedItemPropertyFunction PULLING = (itemStack, world, livingEntity, i) -> livingEntity != null && livingEntity.m_6117_() && livingEntity.m_21211_() == itemStack ? 1.0F : 0.0F;
   private static final ClampedItemPropertyFunction LOLLIPOP = (itemStack, world, livingEntity, i) -> WyHelper.isAprilFirst() ? 1.0F : 0.0F;

   public static void register() {
      for(AkumaNoMiItem df : ModValues.DEVIL_FRUITS) {
         ItemProperties.register(df, FRUIT_TYPE_PROPERTY, DEVIL_FRUITS_RANDOMIZER);
      }

      ItemProperties.register((Item)ModFruits.GORO_GORO_NO_MI.get(), ALT_TEXTURE_PROPERTY, BLUE_GORO);
      ItemProperties.register((Item)ModItems.BELLY_POUCH.get(), SIZE_PROPERTY, BELLY_POUCH_SIZE);
      ItemProperties.register((Item)ModItems.EXTOL_POUCH.get(), SIZE_PROPERTY, EXTOL_POUCH_SIZE);
      ItemProperties.register((Item)ModItems.SAKE_CUP.get(), FILLED_PROPERTY, SAKE_CUP_FILLED);
      ItemProperties.register(((Block)ModBlocks.DEN_DEN_MUSHI.get()).m_5456_(), DEN_TYPE_PROPERTY, DEN_DEN_MUSHI);
      ItemProperties.register((Item)ModWeapons.UMBRELLA.get(), OPEN_PROPERTY, UMBRELLA_OPEN);
      ItemProperties.register((Item)ModWeapons.SORCERY_CLIMA_TACT.get(), OPEN_PROPERTY, SHEATHED_WEAPON);
      ItemProperties.register((Item)ModWeapons.KUJA_BOW.get(), PULL_PROPERTY, PULL);
      ItemProperties.register((Item)ModWeapons.KUJA_BOW.get(), PULLING_PROPERTY, PULLING);
      ItemProperties.register((Item)ModWeapons.KABUTO.get(), PULL_PROPERTY, PULL);
      ItemProperties.register((Item)ModWeapons.KABUTO.get(), PULLING_PROPERTY, PULLING);
      ItemProperties.register((Item)ModWeapons.BLACK_KABUTO.get(), PULL_PROPERTY, PULL);
      ItemProperties.register((Item)ModWeapons.BLACK_KABUTO.get(), PULLING_PROPERTY, PULLING);
      ItemProperties.register((Item)ModWeapons.GINGA_PACHINKO.get(), PULL_PROPERTY, PULL);
      ItemProperties.register((Item)ModWeapons.GINGA_PACHINKO.get(), PULLING_PROPERTY, PULLING);
      ItemProperties.register((Item)ModWeapons.PIPE.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.SCISSORS.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.KIKOKU.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.KIKOKU.get(), SHEATHED_PROPERTY, SHEATHED_WEAPON);
      ItemProperties.register((Item)ModWeapons.KIRIBACHI.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.YORU.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.MURAKUMOGIRI.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.HOOK.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.HOOK.get(), SHEATHED_PROPERTY, SHEATHED_WEAPON);
      ItemProperties.register((Item)ModWeapons.JITTE.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.NONOSAMA_STAFF.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.NONOSAMA_TRIDENT.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.HAMMER_5T.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.TONFA.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.ACES_KNIFE.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.MIHAWKS_KNIFE.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.MIHAWKS_KNIFE.get(), SHEATHED_PROPERTY, SHEATHED_WEAPON);
      ItemProperties.register((Item)ModWeapons.SANDAI_KITETSU.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.SANDAI_KITETSU.get(), SHEATHED_PROPERTY, SHEATHED_WEAPON);
      ItemProperties.register((Item)ModWeapons.WADO_ICHIMONJI.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.WADO_ICHIMONJI.get(), SHEATHED_PROPERTY, SHEATHED_WEAPON);
      ItemProperties.register((Item)ModWeapons.NIDAI_KITETSU.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.NIDAI_KITETSU.get(), SHEATHED_PROPERTY, SHEATHED_WEAPON);
      ItemProperties.register((Item)ModWeapons.SHUSUI.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.SHUSUI.get(), SHEATHED_PROPERTY, SHEATHED_WEAPON);
      ItemProperties.register((Item)ModWeapons.ENMA.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.ENMA.get(), SHEATHED_PROPERTY, SHEATHED_WEAPON);
      ItemProperties.register((Item)ModWeapons.AME_NO_HABAKIRI.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.AME_NO_HABAKIRI.get(), SHEATHED_PROPERTY, SHEATHED_WEAPON);
      ItemProperties.register((Item)ModWeapons.SOUL_SOLID.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.SOUL_SOLID.get(), SHEATHED_PROPERTY, SHEATHED_WEAPON);
      ItemProperties.register((Item)ModWeapons.DURANDAL.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.DURANDAL.get(), SHEATHED_PROPERTY, SHEATHED_WEAPON);
      ItemProperties.register((Item)ModWeapons.MACE.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.DAISENSO.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.MOGURA.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.DALTONS_SPADE.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.ACE.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.BLUE_SWORD.get(), SHEATHED_PROPERTY, BLUE_SWORD_WEAPON);
      ItemProperties.register((Item)ModWeapons.AXE.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.SPEAR.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.KATANA.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.DAGGER.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.CUTLASS.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.BROADSWORD.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.BISENTO.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.HASSAIKAI.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.GRYPHON.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.SAMEKIRI_BOCHO.get(), HAKI_PROPERTY, HAKI_WEAPON);
      ItemProperties.register((Item)ModWeapons.CHAKRAM.get(), HAKI_PROPERTY, HAKI_WEAPON);
      if (WyHelper.isAprilFirst()) {
         ItemProperties.register((Item)ModItems.CIGAR.get(), APRIL_PROPERTY, LOLLIPOP);
         ItemProperties.register((Item)ModItems.CIGARETTE.get(), APRIL_PROPERTY, LOLLIPOP);
         ItemProperties.register((Item)ModItems.SMOKING_PIPE.get(), APRIL_PROPERTY, LOLLIPOP);
         ItemProperties.register((Item)ModItems.THREE_CIGARS.get(), APRIL_PROPERTY, LOLLIPOP);
      }

   }
}
