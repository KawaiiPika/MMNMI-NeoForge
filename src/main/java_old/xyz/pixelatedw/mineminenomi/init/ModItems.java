package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.items.IFruitColor;
import xyz.pixelatedw.mineminenomi.api.items.IMultiChannelColorItem;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiBoxItem;
import xyz.pixelatedw.mineminenomi.items.BellyPouchItem;
import xyz.pixelatedw.mineminenomi.items.BottleOfRumItem;
import xyz.pixelatedw.mineminenomi.items.BubblyCoralItem;
import xyz.pixelatedw.mineminenomi.items.BulletItem;
import xyz.pixelatedw.mineminenomi.items.CannonItem;
import xyz.pixelatedw.mineminenomi.items.ChallengePosterItem;
import xyz.pixelatedw.mineminenomi.items.CharacterCreatorItem;
import xyz.pixelatedw.mineminenomi.items.CigarItem;
import xyz.pixelatedw.mineminenomi.items.ColaItem;
import xyz.pixelatedw.mineminenomi.items.CookedSeaKingMeatItem;
import xyz.pixelatedw.mineminenomi.items.DFEncyclopediaItem;
import xyz.pixelatedw.mineminenomi.items.DandelionItem;
import xyz.pixelatedw.mineminenomi.items.ExtolPouchItem;
import xyz.pixelatedw.mineminenomi.items.GoldDenDenMushiItem;
import xyz.pixelatedw.mineminenomi.items.HandcuffsItem;
import xyz.pixelatedw.mineminenomi.items.HeartItem;
import xyz.pixelatedw.mineminenomi.items.NetItem;
import xyz.pixelatedw.mineminenomi.items.SakeBottleItem;
import xyz.pixelatedw.mineminenomi.items.SakeCupItem;
import xyz.pixelatedw.mineminenomi.items.SeaKingMeatItem;
import xyz.pixelatedw.mineminenomi.items.ShadowItem;
import xyz.pixelatedw.mineminenomi.items.StrawDollItem;
import xyz.pixelatedw.mineminenomi.items.StrikerItem;
import xyz.pixelatedw.mineminenomi.items.TangerineItem;
import xyz.pixelatedw.mineminenomi.items.UltraColaItem;
import xyz.pixelatedw.mineminenomi.items.UnicycleItem;
import xyz.pixelatedw.mineminenomi.items.VivreCardItem;
import xyz.pixelatedw.mineminenomi.items.WantedPosterPackageItem;
import xyz.pixelatedw.mineminenomi.items.WateringCanItem;
import xyz.pixelatedw.mineminenomi.items.bullets.CannonBallItem;
import xyz.pixelatedw.mineminenomi.items.bullets.KairosekiBulletItem;
import xyz.pixelatedw.mineminenomi.items.bullets.NormalBulletItem;

public class ModItems {
   public static final RegistryObject<Item> CHARACTER_CREATOR = ModRegistry.<Item>registerItem("Character Creator", CharacterCreatorItem::new);
   public static final RegistryObject<Item> KAIROSEKI = ModRegistry.<Item>registerItem("Kairoseki", () -> new Item(new Item.Properties()));
   public static final RegistryObject<Item> DENSE_KAIROSEKI = ModRegistry.<Item>registerItem("Dense Kairoseki", () -> new Item(new Item.Properties()));
   public static final RegistryObject<Item> BELLY_POUCH = ModRegistry.<Item>registerItem("Belly Pouch", BellyPouchItem::new);
   public static final RegistryObject<Item> EXTOL_POUCH = ModRegistry.<Item>registerItem("Extol Pouch", ExtolPouchItem::new);
   public static final RegistryObject<Item> KEY = ModRegistry.<Item>registerItem("Key", () -> new Item(new Item.Properties()));
   public static final RegistryObject<Item> VIVRE_CARD = ModRegistry.<Item>registerItem("Vivre Card", VivreCardItem::new);
   public static final RegistryObject<Item> BUBBLY_CORAL = ModRegistry.<Item>registerItem("Bubbly Coral", BubblyCoralItem::new);
   public static final RegistryObject<Item> GOLD_DEN_DEN_MUSHI = ModRegistry.<Item>registerItem("Golden Den Den Mushi", GoldDenDenMushiItem::new);
   public static final RegistryObject<Item> NORMAL_HANDCUFFS = ModRegistry.<Item>registerItem("Handcuffs", () -> new HandcuffsItem(ModEffects.HANDCUFFED));
   public static final RegistryObject<Item> KAIROSEKI_HANDCUFFS = ModRegistry.<Item>registerItem("Kairoseki Handcuffs", () -> new HandcuffsItem(ModEffects.HANDCUFFED_KAIROSEKI));
   public static final RegistryObject<Item> EXPLOSIVE_HANDCUFFS = ModRegistry.<Item>registerItem("Explosive Handcuffs", () -> new HandcuffsItem(ModEffects.HANDCUFFED_EXPLOSIVE));
   public static final RegistryObject<Item> DEVIL_FRUIT_ENCYCLOPEDIA = ModRegistry.<Item>registerItem("Devil Fruit Encyclopedia", DFEncyclopediaItem::new);
   public static final RegistryObject<Item> CHALLENGE_POSTER = ModRegistry.<Item>registerItem("Challenge Poster", ChallengePosterItem::new);
   public static final RegistryObject<NetItem> ROPE_NET = ModRegistry.<NetItem>registerItem("Rope Net", () -> new NetItem(ModEffects.CAUGHT_IN_NET));
   public static final RegistryObject<NetItem> KAIROSEKI_NET = ModRegistry.<NetItem>registerItem("Kairoseki Net", () -> new NetItem(ModEffects.CAUGHT_IN_KAIROSEKI_NET));
   public static final RegistryObject<Item> WANTED_POSTER_PACKAGE = ModRegistry.<Item>registerItem("Wanted Poster Package", WantedPosterPackageItem::new);
   public static final RegistryObject<Item> STRIKER = ModRegistry.<Item>registerItem("Striker", StrikerItem::new);
   public static final RegistryObject<Item> UNICYCLE = ModRegistry.<Item>registerItem("Unicycle", UnicycleItem::new);
   public static final RegistryObject<Item> CANNON = ModRegistry.<Item>registerItem("Cannon", CannonItem::new);
   public static final RegistryObject<Item> SHADOW = ModRegistry.<Item>registerItem("Shadow", ShadowItem::new);
   public static final RegistryObject<Item> DANDELION = ModRegistry.<Item>registerItem("Dandelion", DandelionItem::new);
   public static final RegistryObject<Item> HEART = ModRegistry.<Item>registerItem("Heart", HeartItem::new);
   public static final RegistryObject<Item> COLOR_PALETTE = ModRegistry.<Item>registerItem("Color Palette", () -> new Item((new Item.Properties()).m_41487_(1)));
   public static final RegistryObject<Item> WATERING_CAN = ModRegistry.<Item>registerItem("Watering Can", WateringCanItem::new);
   public static final RegistryObject<Item> STRAW_DOLL = ModRegistry.<Item>registerItem("Straw Doll", StrawDollItem::new);
   public static final RegistryObject<AkumaNoMiBoxItem> TIER_1_BOX = ModRegistry.<AkumaNoMiBoxItem>registerItem("Wooden Box", () -> new AkumaNoMiBoxItem(AkumaNoMiBoxItem.TIER_1_FRUITS));
   public static final RegistryObject<AkumaNoMiBoxItem> TIER_2_BOX = ModRegistry.<AkumaNoMiBoxItem>registerItem("Iron Box", () -> new AkumaNoMiBoxItem(AkumaNoMiBoxItem.TIER_2_FRUITS));
   public static final RegistryObject<AkumaNoMiBoxItem> TIER_3_BOX = ModRegistry.<AkumaNoMiBoxItem>registerItem("Golden Box", () -> new AkumaNoMiBoxItem(AkumaNoMiBoxItem.TIER_3_FRUITS));
   public static final RegistryObject<BulletItem> BULLET = ModRegistry.<BulletItem>registerItem("Bullet", NormalBulletItem::new);
   public static final RegistryObject<BulletItem> KAIROSEKI_BULLET = ModRegistry.<BulletItem>registerItem("Kairoseki Bullet", KairosekiBulletItem::new);
   public static final RegistryObject<Item> POP_GREEN = ModRegistry.<Item>registerItem("Pop Green", () -> new Item(new Item.Properties()));
   public static final RegistryObject<BulletItem> CANNON_BALL = ModRegistry.<BulletItem>registerItem("Cannon Ball", CannonBallItem::new);
   public static final RegistryObject<Item> SEA_KING_MEAT = ModRegistry.<Item>registerItem("Sea King Meat", SeaKingMeatItem::new);
   public static final RegistryObject<Item> COOKED_SEA_KING_MEAT = ModRegistry.<Item>registerItem("Cooked Sea King Meat", CookedSeaKingMeatItem::new);
   public static final RegistryObject<Item> COLA = ModRegistry.<Item>registerItem("Cola", ColaItem::new);
   public static final RegistryObject<Item> ULTRA_COLA = ModRegistry.<Item>registerItem("Ultra Cola", UltraColaItem::new);
   public static final RegistryObject<Item> EMPTY_COLA = ModRegistry.<Item>registerItem("Empty Cola", () -> new Item((new Item.Properties()).m_41487_(16)));
   public static final RegistryObject<Item> EMPTY_ULTRA_COLA = ModRegistry.<Item>registerItem("Empty Ultra Cola", () -> new Item((new Item.Properties()).m_41487_(16)));
   public static final RegistryObject<Item> SAKE_BOTTLE = ModRegistry.<Item>registerItem("Sake Bottle", SakeBottleItem::new);
   public static final RegistryObject<Item> SAKE_CUP = ModRegistry.<Item>registerItem("Sake Cup", SakeCupItem::new);
   public static final RegistryObject<Item> BOTTLE_OF_RUM = ModRegistry.<Item>registerItem("Bottle of Rum", BottleOfRumItem::new);
   public static final RegistryObject<Item> TANGERINE = ModRegistry.<Item>registerItem("Tangerine", TangerineItem::new);
   public static final RegistryObject<Item> THREE_CIGARS = ModRegistry.<Item>registerItem("Three Cigars", () -> new CigarItem(10));
   public static final RegistryObject<Item> CIGARETTE = ModRegistry.<Item>registerItem("Cigarette", () -> new CigarItem(15));
   public static final RegistryObject<Item> SMOKING_PIPE = ModRegistry.<Item>registerItem("Smoking Pipe", () -> new CigarItem(15));
   public static final RegistryObject<Item> CIGAR = ModRegistry.<Item>registerItem("Cigar", () -> new CigarItem(10));

   public static void init() {
   }

   public static class Client {
      public static void registerColorHandlers(RegisterColorHandlersEvent.Item event) {
         ItemLike[] fruitColorItems = (ItemLike[])ModValues.DEVIL_FRUITS.toArray(new ItemLike[0]);
         event.register(Client::createFruitItemColor, fruitColorItems);
         ItemLike[] multiChannelColorItems = new ItemLike[]{(ItemLike)ModArmors.STRAW_HAT.get(), (ItemLike)ModArmors.MARINE_CAPTAIN_CAPE.get(), (ItemLike)ModArmors.PIRATE_CAPTAIN_CAPE.get(), (ItemLike)ModItems.STRAW_DOLL.get(), (ItemLike)ModArmors.BANDANA.get(), (ItemLike)ModArmors.VICE_ADMIRAL_CHEST.get(), (ItemLike)ModArmors.VICE_ADMIRAL_FEET.get(), (ItemLike)ModArmors.VICE_ADMIRAL_LEGS.get(), (ItemLike)ModArmors.TRICORNE.get(), (ItemLike)ModArmors.FLUFFY_CAPE.get(), (ItemLike)ModArmors.MIHAWK_HAT.get(), (ItemLike)ModArmors.DOFFY_GLASSES.get(), (ItemLike)ModArmors.BANDANA.get(), (ItemLike)ModArmors.CELESTIAL_DRAGON_BUBBLE.get(), (ItemLike)ModArmors.PLUME_HAT.get(), (ItemLike)ModArmors.BICORNE.get(), (ItemLike)ModArmors.WIDE_BRIM_HAT.get(), (ItemLike)ModArmors.WIZARD_HAT.get(), (ItemLike)ModWeapons.AXE.get(), (ItemLike)ModWeapons.KATANA.get(), (ItemLike)ModWeapons.DAGGER.get(), (ItemLike)ModWeapons.CUTLASS.get(), (ItemLike)ModWeapons.BISENTO.get(), (ItemLike)ModWeapons.BROADSWORD.get(), (ItemLike)ModWeapons.JITTE.get(), (ItemLike)ModWeapons.CLEAVER.get(), (ItemLike)ModWeapons.MACE.get(), (ItemLike)ModWeapons.SPEAR.get(), (ItemLike)ModWeapons.KUJA_BOW.get(), (ItemLike)ModWeapons.DORU_PICKAXE.get(), (ItemLike)ModWeapons.DORU_DORU_ARTS_KEN.get()};
         event.register(Client::createMultiChannelItemColor, multiChannelColorItems);
      }

      private static int createFruitItemColor(ItemStack itemStack, int layer) {
         IFruitColor color = (IFruitColor)itemStack.m_41720_();
         return layer > 0 ? color.getStemColor(itemStack) : color.getBaseColor(itemStack);
      }

      private static int createMultiChannelItemColor(ItemStack itemStack, int layer) {
         IMultiChannelColorItem color = (IMultiChannelColorItem)itemStack.m_41720_();
         return color.getLayerColor(itemStack, layer);
      }
   }
}
