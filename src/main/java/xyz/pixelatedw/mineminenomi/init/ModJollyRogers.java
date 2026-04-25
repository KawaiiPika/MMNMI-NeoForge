package xyz.pixelatedw.mineminenomi.init;

import java.lang.invoke.SerializedLambda;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElementRegistryEntry;

public class ModJollyRogers {
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BASE_SKULL = base("skull", "Skull", JollyRogerElement::new);
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BASE_SMILE = base("smile", "Smile", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BASE_ARLONG = base("arlong", "Arlong", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BASE_METAL_SKULL = base("metal_skull", "Metal Skull", JollyRogerElement::new);
   private static final JollyRogerElement.ICanUse BASE_SKULL_ONLY = (player, crew) -> onlyWith(player, crew, BASE_SKULL);
   private static final JollyRogerElement.ICanUse BASE_SMILE_ONLY = (player, crew) -> onlyWith(player, crew, BASE_SMILE);
   private static final JollyRogerElement.ICanUse BASE_SKULL_SMILE_ONLY = (player, crew) -> onlyWith(player, crew, BASE_SKULL, BASE_SMILE);
   private static final JollyRogerElement.ICanUse BASE_SKULL_METAL_ONLY = (player, crew) -> onlyWith(player, crew, BASE_SKULL, BASE_METAL_SKULL);
   private static final JollyRogerElement.ICanUse BASE_SMILE_METAL_ONLY = (player, crew) -> onlyWith(player, crew, BASE_SMILE, BASE_METAL_SKULL);
   private static final JollyRogerElement.ICanUse BASE_SKULL_SMILE_METAL_ONLY = (player, crew) -> onlyWith(player, crew, BASE_SKULL, BASE_SMILE, BASE_METAL_SKULL);
   private static final JollyRogerElement.ICanUse SUPERNOVA_ONLY = (player, crew) -> true; // PatreonHandler not fully ported
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_STRAWHAT = detail("strawhat", "Strawhat", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_SMILE_METAL_ONLY));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_BICORNE = detail("bicorne", "Bicorne", (layer) -> (new JollyRogerElement(layer)).addUseCheck(SUPERNOVA_ONLY).addUseCheck(BASE_SKULL_SMILE_METAL_ONLY));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_ROBIN_HAT = detail("robin_hat", "Robin's Hat", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_SMILE_METAL_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_ACE_HAT = detail("ace_hat", "Ace's Hat", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_SMILE_METAL_ONLY));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_CHEF_HAT = detail("chef_hat", "Chef Hat", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_METAL_ONLY).setCanBeColored().setCanBeFlipped(true, false));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_MIHAWK_HAT = detail("mihawk_hat", "Mihawk's Hat", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_METAL_ONLY));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_HEADPHONES_1 = detail("headphones", "Headphones", (layer) -> (new JollyRogerElement(layer)).addUseCheck(SUPERNOVA_ONLY).addUseCheck(BASE_SKULL_SMILE_METAL_ONLY));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_BIZZARE_HAT = detail("bizzare_hat", "Bizzare Hat", (layer) -> (new JollyRogerElement(layer)).addUseCheck(SUPERNOVA_ONLY).addUseCheck(BASE_SKULL_METAL_ONLY));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_TRAINER_HAT = detail("trainer_hat", "Trainer Hat", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_METAL_ONLY).setCanBeFlipped(true, false));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_TOPHAT = detail("tophat", "Tophat", (layer) -> (new JollyRogerElement(layer)).addUseCheck(SUPERNOVA_ONLY).addUseCheck(BASE_SKULL_METAL_ONLY));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_HEADBAND_1 = detail("headband", "Headband", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_HEADBAND_2 = detail("headband2", "Headband", (layer) -> (new JollyRogerElement(layer)).addUseCheck(SUPERNOVA_ONLY).addUseCheck(BASE_SKULL_METAL_ONLY));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_BANDANA = detail("bandana", "Bandana", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_METAL_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_SKULL_HOLES = detail("skull_holes", "Skull Holes", (layer) -> (new JollyRogerElement(layer)).addUseCheck(SUPERNOVA_ONLY).addUseCheck(BASE_SKULL_ONLY).setCanBeColored().setCanBeFlipped(true, false));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_HEAD_ARROW = detail("head_arrow", "Head Arrow", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_METAL_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_HAIR_1 = detail("hair", "Hair", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_SMILE_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_HAIR_2 = detail("hair2", "Hair", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_HAIR_3 = detail("hair3", "Hair", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_HAIR_4 = detail("hair4", "Hair", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_HAIR_5 = detail("hair5", "Hair", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_HAIR_6 = detail("hair6", "Hair", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_SABO_HAIR = detail("sabo_hair", "Sabo's Hair", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_BROOK_AFRO = detail("brook_afro", "Brook's Afro", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_HORNS_1 = detail("horns", "Horns", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_HORNS_2 = detail("horns2", "Horns", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SMILE_METAL_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_HORNS_3 = detail("horns3", "Horns", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_METAL_ONLY));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_HORNS_4 = detail("horns4", "Horns", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_HORNS_5 = detail("horns5", "Horns", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_ANIMAL_EARS_1 = detail("animal_ears", "Animal Ears", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_METAL_ONLY));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_ANIMAL_EARS_2 = detail("animal_ears2", "Animal Ears", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_ANIMAL_EARS_3 = detail("animal_ears3", "Animal Ears", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_METAL_ONLY));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_EYEBROWS_1 = detail("eyebrows", "Eyebrows", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_SMILE_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_EYEBROWS_2 = detail("eyebrows2", "Eyebrows", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_SMILE_METAL_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_EYES_1 = detail("eyes", "Eyes", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_EYES_2 = detail("eyes2", "Eyes", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_METAL_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_ANGRY_EYES = detail("angry_eyes", "Angry Eyes", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_EYEPATCH_1 = detail("eyepatch", "Eyepatch", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY).setCanBeColored().setCanBeFlipped(true, false).setUseLimit(2));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_EYEPATCH_2 = detail("eyepatch2", "Eyepatch", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SMILE_ONLY).setCanBeColored().setCanBeFlipped(true, false).setUseLimit(2));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_GLASSES_1 = detail("glasses", "Glasses", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_GLASSES_2 = detail("glasses2", "Glasses", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_SMILE_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_GLASSES_3 = detail("glasses3", "Glasses", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_GLASSES_4 = detail("glasses4", "Glasses", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_SMILE_METAL_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_GLASSES_5 = detail("glasses5", "Glasses", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_MONOCLE = detail("monocle", "Monocle", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_SMILE_METAL_ONLY).setCanBeFlipped(true, false).setUseLimit(2));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_EYE_MARK_1 = detail("eye_mark", "Eye Mark", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY).setCanBeColored().setCanBeFlipped(true, false).setUseLimit(2));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_EYE_MARK_2 = detail("eye_mark2", "Eye Mark", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_METAL_ONLY).setCanBeColored().setCanBeFlipped(true, false).setUseLimit(2));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_EYE_MARK_3 = detail("eye_mark3", "Eye Mark", (layer) -> (new JollyRogerElement(layer)).addUseCheck(SUPERNOVA_ONLY).addUseCheck(BASE_SKULL_METAL_ONLY).setCanBeFlipped(true, false).setUseLimit(2));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_EYE_MARK_4 = detail("eye_mark4", "Eye Mark", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_METAL_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_EYE_SCAR = detail("eye_scar", "Eye Scar", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY).setCanBeFlipped(true, false).setUseLimit(2));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_EYE_FLAME = detail("eye_flame", "Eye Flame", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_METAL_ONLY).setCanBeColored().setCanBeFlipped(true, false).setUseLimit(2));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_THIRD_EYE = detail("third_eye", "Third Eye", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_HEART_EYE = detail("heart_eye", "Heart Eye", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_METAL_ONLY).setCanBeColored().setCanBeFlipped(true, false).setUseLimit(2));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_CYBORG_FACE = detail("cyborg_face", "Cyborg Face", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_METAL_ONLY).setCanBeFlipped(true, false));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_FACE_MARKS_1 = detail("face_marks", "Face Marks", (layer) -> (new JollyRogerElement(layer)).addUseCheck(SUPERNOVA_ONLY).addUseCheck(BASE_SKULL_METAL_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_LONG_EARS = detail("long_ears", "Long Ears", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_WHISKERS = detail("whiskers", "Whiskers", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_METAL_NOSE = detail("metal_nose", "Metal Nose", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_SMILE_METAL_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_LONG_NOSE = detail("long_nose", "Long Nose", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY).setCanBeColored().setCanBeFlipped(true, false));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_LIPS_1 = detail("lips", "Lips", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_SMILE_METAL_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_SHARK_TEETH = detail("shark_teeth", "Shark Teeth", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_TEETH = detail("teeth", "Teeth", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY).setCanBeColored().setCanBeFlipped(true, false));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_MOUSTACHE_1 = detail("moustache", "Moustache", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_SMILE_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_MOUSTACHE_2 = detail("moustache2", "Moustache", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_SMILE_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_MOUSTACHE_3 = detail("moustache3", "Moustache", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_SMILE_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_GOATEE = detail("goatee", "Goatee", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_SMILE_ONLY).setCanBeColored().setCanBeFlipped(true, false));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_SINGLE_CIGAR = detail("single_cigar", "Single Cigar", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY).setCanBeColored().setCanBeFlipped(true, false));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_TRIPLE_CIGARS = detail("triple_cigars", "Triple Cigars", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY).setCanBeFlipped(true, false));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_ROSE = detail("rose", "Rose", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_METAL_ONLY).setCanBeFlipped(true, false));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_SPARROW = detail("sparrow", "Sparrow", (layer) -> (new JollyRogerElement(layer)).addUseCheck(SUPERNOVA_ONLY).setCanBeColored().setCanBeFlipped(true, false).setUseLimit(2));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_CIRCLE_DECORATION = detail("circle_decoration", "Circle Decoration", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SMILE_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_BISCUIT = detail("biscuit", "Biscuit", (layer) -> (new JollyRogerElement(layer)).addUseCheck(SUPERNOVA_ONLY).addUseCheck(BASE_SKULL_SMILE_METAL_ONLY));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_PINEAPPLE = detail("pineapple", "Pineapple", (layer) -> (new JollyRogerElement(layer)).addUseCheck(SUPERNOVA_ONLY).addUseCheck(BASE_SKULL_ONLY).setCanBeFlipped(true, false).setUseLimit(2));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_RELIC = detail("relic", "Relic", (layer) -> (new JollyRogerElement(layer)).setCanBeColored().setCanBeFlipped(true, false).setUseLimit(2));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> DETAIL_FLINTLOCK = detail("flintlock", "Flintlock", (layer) -> (new JollyRogerElement(layer)).setCanBeFlipped(true, false).setUseLimit(2));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_SWORDS_1 = background("swords", "Swords", (layer) -> (new JollyRogerElement(layer)).setCanBeFlipped(false, true));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_SWORDS_2 = background("swords2", "Swords", JollyRogerElement::new);
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_SWORDS_3 = background("swords3", "Swords", (layer) -> (new JollyRogerElement(layer)).setCanBeFlipped(false, true));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_BISENTO_1 = background("bisento1", "Bisento", (layer) -> (new JollyRogerElement(layer)).setCanBeFlipped(true, false).setCanBeFlipped(false, true).setUseLimit(2));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_GUNS_1 = background("guns", "Guns", (layer) -> (new JollyRogerElement(layer)).setCanBeFlipped(false, true));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_GUNS_2 = background("guns2", "Guns", JollyRogerElement::new);
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_BOW_1 = background("bow", "Bow", (layer) -> (new JollyRogerElement(layer)).setCanBeFlipped(true, false).setCanBeFlipped(false, true));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_SKULLS = background("skulls", "Skulls", (layer) -> (new JollyRogerElement(layer)).addUseCheck(BASE_SKULL_ONLY));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_BONES_1 = background("bones", "Bones", JollyRogerElement::new);
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_BONES_2 = background("bones2", "Bones", JollyRogerElement::new);
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_SUN_1 = background("sun", "Sun", (layer) -> (new JollyRogerElement(layer)).setCanBeColored().setCanBeFlipped(true, false));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_SUN_2 = background("sun2", "Sun", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_SUN_3 = background("sun3", "Sun", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_STAR_1 = background("star", "Star", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_STAR_2 = background("star2", "Star", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_LINES_1 = background("lines", "Lines", (layer) -> (new JollyRogerElement(layer)).setCanBeColored().setUseLimit(2));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_LINES_2 = background("lines2", "Lines", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_BUBBLES_1 = background("bubbles", "Bubbles", (layer) -> (new JollyRogerElement(layer)).setCanBeColored().setCanBeFlipped(true, false).setUseLimit(2));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_TRIANGLES_1 = background("triangles", "Triangles", (layer) -> (new JollyRogerElement(layer)).setCanBeColored().setCanBeFlipped(true, false).setCanBeFlipped(false, true).setUseLimit(4));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_PLUS_1 = background("plus", "Plus", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_PLUS_2 = background("plus2", "Plus", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_CIRCLES_1 = background("circles", "Circles", (layer) -> (new JollyRogerElement(layer)).setCanBeColored().setCanBeFlipped(false, true));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_CIRCLE_1 = background("circle", "Circle", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_CIRCLE_2 = background("circle2", "Circle", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_BEADS = background("beads", "Beads", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_NAILS = background("nails", "Nails", (layer) -> (new JollyRogerElement(layer)).setCanBeColored().setCanBeFlipped(false, true));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_SOUND = background("sound", "Sound", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_BUTTERFLY = background("butterfly", "Butterfly", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_CHEF = background("chef", "Chef", JollyRogerElement::new);
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_Y = background("y_letter", "Y", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_BAROQUE = background("baroque", "Baroque", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_SNAKES = background("snakes", "Snakes", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_SPIDER = background("spider", "Spider", (layer) -> (new JollyRogerElement(layer)).setCanBeColored().setCanBeFlipped(false, true));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_WEIGHTS = background("weights", "Weights", JollyRogerElement::new);
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_LIGHTNING_1 = background("lightning", "Lightning", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_LIGHTNING_2 = background("lightning2", "Lightning", (layer) -> (new JollyRogerElement(layer)).setCanBeColored().setCanBeFlipped(false, true));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_HELM = background("helm", "Helm", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_SPADE = background("spade", "Spade", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_CLUB = background("club", "Club", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_DIAMOND = background("diamond", "Diamond", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_HEART = background("heart", "Heart", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_BISCUIT = background("biscuit", "Biscuit", JollyRogerElement::new);
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_CRAFTING = background("crafting", "Crafting", JollyRogerElement::new);
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_MINING = background("mining", "Mining", JollyRogerElement::new);
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_FISHBONES = background("fishbones", "Fishbones", (layer) -> (new JollyRogerElement(layer)).addUseCheck(SUPERNOVA_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_BIZZARE = background("bizzare", "Bizzare", (layer) -> (new JollyRogerElement(layer)).addUseCheck(SUPERNOVA_ONLY).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_MUSIC = background("music", "Music", JollyRogerElement::new);
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_TOMOE_1 = background("tomoe", "Tomoe Drums", (layer) -> (new JollyRogerElement(layer)).addUseCheck(SUPERNOVA_ONLY));
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_CLOUDS = background("clouds", "Clouds", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_FIRE = background("fire", "Fire", JollyRogerElement::new);
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_WINGS_1 = background("wings", "Wings", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());
   public static final DeferredHolder<JollyRogerElement, JollyRogerElement> BACKGROUND_WINGS_2 = background("wings2", "Wings", (layer) -> (new JollyRogerElement(layer)).setCanBeColored());

   public static boolean onlyWith(Player player, Crew crew, DeferredHolder<JollyRogerElement, JollyRogerElement>... elements) {
      JollyRoger jollyRoger = crew.getJollyRoger();

      for(DeferredHolder<JollyRogerElement, JollyRogerElement> element : elements) {
         if (jollyRoger.getBase() == null && element == null) {
            return true;
         }

         if (jollyRoger.getBase() != null && jollyRoger.getBase().equals(element.get())) {
            return true;
         }
      }

      return false;
   }

   private static DeferredHolder<JollyRogerElement, JollyRogerElement> base(String id, String name, JollyRogerElementRegistryEntry entry) {
      return ModRegistries.JOLLY_ROGER_ELEMENTS_REGISTRY.register(id, () -> entry.get(JollyRogerElement.LayerType.BASE).setLocalizedName(net.minecraft.network.chat.Component.literal(name)));
   }

   private static DeferredHolder<JollyRogerElement, JollyRogerElement> background(String id, String name, JollyRogerElementRegistryEntry entry) {
      return ModRegistries.JOLLY_ROGER_ELEMENTS_REGISTRY.register(id, () -> entry.get(JollyRogerElement.LayerType.BACKGROUND).setLocalizedName(net.minecraft.network.chat.Component.literal(name)));
   }

   private static DeferredHolder<JollyRogerElement, JollyRogerElement> detail(String id, String name, JollyRogerElementRegistryEntry entry) {
      return ModRegistries.JOLLY_ROGER_ELEMENTS_REGISTRY.register(id, () -> entry.get(JollyRogerElement.LayerType.DETAIL).setLocalizedName(net.minecraft.network.chat.Component.literal(name)));
   }

   public static void init() {
   }
}
