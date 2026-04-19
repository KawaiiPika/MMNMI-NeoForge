package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.resources.ResourceLocation;

public class ModResources {
    public static final ResourceLocation NULL_ENTITY_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/null.png");
    
    // GUI Icons
    public static final ResourceLocation PIRATE_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/pirate.png");
    public static final ResourceLocation MARINE_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/marine.png");
    public static final ResourceLocation BOUNTY_HUNTER_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/bounty_hunter.png");
    public static final ResourceLocation REVOLUTIONARY_ARMY_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/revolutionary.png");
    public static final ResourceLocation WORLD_GOV_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/world_government.png");
    
    public static final ResourceLocation HUMAN = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/human.png");
    public static final ResourceLocation FISHMAN = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/fishman.png");
    public static final ResourceLocation CYBORG = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/cyborg.png");
    public static final ResourceLocation MINK1 = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/mink1.png");
    public static final ResourceLocation MINK2 = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/mink2.png");
    public static final ResourceLocation MINK3 = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/mink3.png");
    public static final ResourceLocation SKYPIEAN = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/skypiean.png");

    public static final ResourceLocation SWORDSMAN = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/swordsman.png");
    public static final ResourceLocation SNIPER = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/sniper.png");
    public static final ResourceLocation DOCTOR = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/doctor.png");
    public static final ResourceLocation ART_OF_WEATHER = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/art_of_weather.png");
    public static final ResourceLocation BRAWLER = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/brawler.png");
    public static final ResourceLocation BLACK_LEG = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/black_leg.png");

    public static final ResourceLocation WIDGETS = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/widgets.png");
    public static final ResourceLocation BOOK = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/book.png");
    public static final ResourceLocation RANDOM = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/random.png");

    public static final ResourceLocation RED_ARROW_LEFT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/red_arrow_left.png");
    public static final ResourceLocation RED_ARROW_RIGHT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/red_arrow_right.png");
    public static final ResourceLocation WOOD_ARROW_LEFT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/wood_arrow_left.png");
    public static final ResourceLocation WOOD_ARROW_RIGHT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/wood_arrow_right.png");

    public static final ResourceLocation BIG_WOOD_BUTTON_LEFT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/big_left_button.png");
    public static final ResourceLocation BIG_WOOD_BUTTON_RIGHT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/big_right_button.png");
    public static final ResourceLocation BUTTON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/button.png");
    public static final ResourceLocation BLANK_NEW = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/blank_new.png");
    public static final ResourceLocation BOARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/board.png");
    public static final ResourceLocation BUSOSHOKU_HAKI_ARM = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/buso.png");
    public static final ResourceLocation HOT_BOILING_SPECIAL_ARM = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/hot_boiling_special.png");
    public static final ResourceLocation RUST_TOUCH_ARM = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/rust_touch.png");
    public static final ResourceLocation DIAMOND_BODY = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/diamond_body.png");
    public static final ResourceLocation CANDY_ARMOR = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/candy_armor.png");
    public static final ResourceLocation AWA_SOAP = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/soap.png");
    public static final ResourceLocation BETA_COATING = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/blocks/mucus.png");
    public static final ResourceLocation DOKU_COATING = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/blocks/poison.png");
    public static final ResourceLocation BUDDHA_COATING = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/daibutsu.png");
    public static final ResourceLocation FROSTBITE_1 = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/frostbite_1.png");
    public static final ResourceLocation FROSTBITE_2 = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/frostbite_2.png");
    public static final ResourceLocation FROSTBITE_3 = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/frostbite_3.png");
    public static final ResourceLocation FROSTBITE_4 = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/frostbite_4.png");
    public static final ResourceLocation FROSTBITE_5 = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/frostbite_5.png");

    // Grunt textures
    public static ResourceLocation getMarineTexture(int index) {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/marine" + index + ".png");
    }
    
    public static ResourceLocation getPirateTexture(int index) {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/pirate" + index + ".png");
    }

    public static void init() {}
}
