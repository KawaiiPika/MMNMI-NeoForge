package xyz.pixelatedw.mineminenomi.api.helpers;

import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.abilities.ZoomAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModEnchantments;
import xyz.pixelatedw.mineminenomi.init.ModModelOverrides;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class ItemsHelper {
   private static final String[] WANTED_POSTER_BACKGROUNDS = new String[]{"forest1", "forest2", "jungle1", "jungle2", "hills1", "hills2", "hills3", "plains1", "plains2", "plains3", "taiga1", "taiga2"};
   public static final Color MARINE_HANDLE_COLOR;
   public static final Color GENERIC_HANDLE_COLOR;
   public static final String CLONE_TAG = "isClone";
   private static final float RAINBOW_UPDATE_MS = 1000.0F;
   public static final ChatFormatting[] RAINBOW_COLORS;

   public static final ChatFormatting getRainbowFormatting() {
      int color = Math.round((float)Util.m_137550_() % 1000.0F / (1000.0F / (float)RAINBOW_COLORS.length)) % RAINBOW_COLORS.length;
      return RAINBOW_COLORS[color];
   }

   public static void itemBreakParticles(Level level, Vec3 pos, int count, ItemStack stack) {
      for(int i = 0; i < count; ++i) {
         Vector3d vec3d = new Vector3d(((double)level.m_213780_().m_188501_() - (double)0.5F) * 0.1, Math.random() * 0.1 + 0.1, (double)0.0F);
         double d0 = (double)(-level.m_213780_().m_188501_()) * 0.6 - 0.3;
         Vector3d vec3d1 = new Vector3d(((double)level.m_213780_().m_188501_() - (double)0.5F) * 0.3, d0, 0.6);
         if (level instanceof ServerLevel serverLevel) {
            serverLevel.m_8767_(new ItemParticleOption(ParticleTypes.f_123752_, stack), vec3d1.x, vec3d1.y, vec3d1.z, 1, vec3d.x, vec3d.y + 0.05, vec3d.z, 0.2);
         } else {
            level.m_7106_(new ItemParticleOption(ParticleTypes.f_123752_, stack), vec3d1.x, vec3d1.y, vec3d1.z, vec3d.x, vec3d.y + 0.05, vec3d.z);
         }
      }

   }

   public static void itemBreakParticles(LivingEntity entity, int count, ItemStack stack) {
      for(int i = 0; i < count; ++i) {
         Vec3 vec3d = new Vec3(((double)entity.m_217043_().m_188501_() - (double)0.5F) * 0.1, Math.random() * 0.1 + 0.1, (double)0.0F);
         vec3d = vec3d.m_82496_(-entity.m_146909_() * ((float)Math.PI / 180F));
         vec3d = vec3d.m_82524_(-entity.m_146908_() * ((float)Math.PI / 180F));
         double d0 = (double)(-entity.m_217043_().m_188501_()) * 0.6 - 0.3;
         Vec3 vec3d1 = new Vec3(((double)entity.m_217043_().m_188501_() - (double)0.5F) * 0.3, d0, 0.6);
         vec3d1 = vec3d1.m_82496_(-entity.m_146909_() * ((float)Math.PI / 180F));
         vec3d1 = vec3d1.m_82524_(-entity.m_146908_() * ((float)Math.PI / 180F));
         vec3d1 = vec3d1.m_82520_(entity.m_20185_(), entity.m_20188_(), entity.m_20189_());
         Level var9 = entity.m_9236_();
         if (var9 instanceof ServerLevel level) {
            level.m_8767_(new ItemParticleOption(ParticleTypes.f_123752_, stack), vec3d1.f_82479_, vec3d1.f_82480_, vec3d1.f_82481_, 1, vec3d.f_82479_, vec3d.f_82480_ + 0.05, vec3d.f_82481_, 0.2);
         } else {
            entity.m_9236_().m_7106_(new ItemParticleOption(ParticleTypes.f_123752_, stack), vec3d1.f_82479_, vec3d1.f_82480_, vec3d1.f_82481_, vec3d.f_82479_, vec3d.f_82480_ + 0.05, vec3d.f_82481_);
         }
      }

   }

   public static Optional<ItemStack> findItemInSlot(LivingEntity entity, EquipmentSlot slot, Item item) {
      ItemStack stack = entity.m_6844_(slot);
      if (!stack.m_41619_() && stack.m_41720_() == item) {
         return Optional.ofNullable(stack);
      } else {
         if (ModMain.hasCuriosInstalled()) {
            List<SlotResult> curiosList = (List)CuriosApi.getCuriosInventory(entity).resolve().map((props) -> props.findCurios(item)).orElse((Object)null);
            if (curiosList != null) {
               for(SlotResult result : curiosList) {
                  if (result.stack().m_41720_() == item) {
                     return Optional.ofNullable(result.stack());
                  }
               }
            }
         }

         return Optional.empty();
      }
   }

   public static boolean hasItemInSlot(LivingEntity entity, EquipmentSlot slot, Item item) {
      ItemStack stack = entity.m_6844_(slot);
      if (!stack.m_41619_() && stack.m_41720_() == item) {
         return true;
      } else {
         if (ModMain.hasCuriosInstalled()) {
            List<SlotResult> curiosList = (List)CuriosApi.getCuriosInventory(entity).resolve().map((props) -> props.findCurios(item)).orElse((Object)null);
            if (curiosList != null) {
               for(SlotResult result : curiosList) {
                  if (result.stack().m_41720_() == item) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   public static void stopShieldAndStartCooldown(LivingEntity entity, int cooldown) {
      if (CombatHelper.isShieldBlocking(entity)) {
         entity.m_5810_();
         if (entity instanceof Player) {
            Player player = (Player)entity;
            player.m_36335_().m_41524_(Items.f_42740_, cooldown);
         }
      }

   }

   public static boolean giveItemStackToEntity(LivingEntity entity, ItemStack itemStack, EquipmentSlot slotType) {
      if (entity instanceof Player player) {
         player.m_150109_().m_36054_(itemStack);
         return true;
      } else if (!entity.m_21033_(slotType)) {
         entity.m_8061_(slotType, itemStack);
         return true;
      } else {
         return false;
      }
   }

   public static void removeItemStackFromInventory(LivingEntity entity, ItemStack searchStack) {
      if (entity instanceof Player player) {
         player.m_150109_().m_36057_(searchStack);
      } else {
         for(ItemStack stack : entity.m_20158_()) {
            if (stack.equals(searchStack)) {
               stack.m_41774_(stack.m_41613_());
            }
         }
      }

   }

   public static boolean hasItemStackInInventory(LivingEntity entity, ItemStack stack) {
      return getAllInventoryItems(entity).contains(stack);
   }

   public static int countItemInInventory(LivingEntity entity, Item item) {
      return (Integer)getAllInventoryItems(entity).stream().filter((stack) -> stack.m_41720_().equals(item)).map(ItemStack::m_41613_).reduce(0, Integer::sum);
   }

   public static int getFreeOrSameSlot(Player player, ItemStack other) {
      for(int i = player.m_150109_().f_35974_.size() - 1; i > 0; --i) {
         ItemStack stack = (ItemStack)player.m_150109_().f_35974_.get(i);
         if (i != player.m_150109_().f_35977_ && stack.m_41753_() && stack.m_41613_() + other.m_41613_() <= stack.m_41741_() && matchItemStacks(stack, other)) {
            return i;
         }
      }

      for(int i = player.m_150109_().f_35974_.size() - 1; i > 0; --i) {
         ItemStack stack = (ItemStack)player.m_150109_().f_35974_.get(i);
         if (i != player.m_150109_().f_35977_ && stack.m_41619_()) {
            return i;
         }
      }

      return -1;
   }

   private static boolean matchItemStacks(ItemStack stack, ItemStack other) {
      if (stack.m_41619_() && other.m_41619_()) {
         return true;
      } else if (!stack.m_41619_() && !other.m_41619_()) {
         if (stack.m_41720_() == other.m_41720_() && (stack.m_41783_() != null || other.m_41783_() == null)) {
            return (stack.m_41783_() == null || stack.m_41783_().equals(other.m_41783_())) && stack.areCapsCompatible(other);
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public static int getFreeSlotReversed(Player player) {
      for(int i = player.m_150109_().f_35974_.size() - 1; i > 0; --i) {
         if (((ItemStack)player.m_150109_().f_35974_.get(i)).m_41619_()) {
            return i;
         }
      }

      return -1;
   }

   public static boolean hasInventoryFull(LivingEntity entity) {
      return !getInventoryItems(entity).stream().anyMatch(ItemStack::m_41619_);
   }

   public static long getFreeSlotsCount(LivingEntity entity) {
      return getInventoryItems(entity).stream().filter(ItemStack::m_41619_).count();
   }

   public static List<ItemStack> getInventoryItems(LivingEntity entity) {
      List<ItemStack> inventory = new ArrayList();
      if (entity instanceof Player player) {
         inventory.addAll(player.m_150109_().f_35974_);
      } else {
         Iterable var10000 = entity.m_6167_();
         Objects.requireNonNull(inventory);
         var10000.forEach(inventory::add);
      }

      return inventory;
   }

   public static List<ItemStack> getAllInventoryItems(LivingEntity entity) {
      List<ItemStack> inventory = new ArrayList();
      if (entity instanceof Player player) {
         inventory.addAll(player.m_150109_().f_35974_);
         inventory.addAll(player.m_150109_().f_35976_);
         inventory.addAll(player.m_150109_().f_35975_);
      } else {
         Iterable var10000 = entity.m_20158_();
         Objects.requireNonNull(inventory);
         var10000.forEach(inventory::add);
      }

      if (ModMain.hasCuriosInstalled()) {
         Collection<SlotResult> curiosList = (Collection)CuriosApi.getCuriosInventory(entity).resolve().map((props) -> props.findCurios((stack) -> true)).orElse((Object)null);
         if (curiosList != null) {
            for(SlotResult result : curiosList) {
               inventory.add(result.stack());
            }
         }
      }

      inventory.removeIf((stack) -> stack == null || stack.m_41619_());
      return inventory;
   }

   public static boolean hasKairosekiItem(LivingEntity entity) {
      Iterable<ItemStack> iter = entity.m_20158_();
      if (entity instanceof Player player) {
         iter = Iterables.concat(player.m_150109_().f_35974_, entity.m_20158_());
      }

      for(ItemStack stack : iter) {
         if (!stack.m_41619_() && stack.m_204117_(ModTags.Items.KAIROSEKI)) {
            return true;
         }
      }

      return false;
   }

   public static boolean isKairosekiWeapon(ItemStack heldItem) {
      if (heldItem != null && !heldItem.m_41619_()) {
         if (heldItem.m_204117_(ModTags.Items.KAIROSEKI)) {
            return true;
         } else {
            return heldItem.m_41793_() && heldItem.getEnchantmentLevel((Enchantment)ModEnchantments.KAIROSEKI.get()) > 0 || heldItem.m_41720_() == ModWeapons.JITTE.get();
         }
      } else {
         return false;
      }
   }

   public static float getSniperInaccuracy(float inaccuracy, LivingEntity entity) {
      boolean isSniper = (Boolean)EntityStatsCapability.get(entity).map(IEntityStats::isSniper).orElse(false);
      if (isSniper) {
         inaccuracy /= 2.0F;
      }

      if (entity.m_6844_(EquipmentSlot.HEAD).m_41720_() == ModArmors.SNIPER_GOGGLES.get()) {
         boolean isZoomActive = (Boolean)AbilityCapability.get(entity).map((props) -> (ZoomAbility)props.getEquippedAbility((AbilityCore)ZoomAbility.INSTANCE.get())).map(Ability::isContinuous).orElse(false);
         if (isZoomActive) {
            inaccuracy /= 4.0F;
         }
      }

      return inaccuracy;
   }

   public static float getItemDamage(ItemStack stack) {
      Multimap<Attribute, AttributeModifier> multimap = stack.m_41638_(EquipmentSlot.MAINHAND);
      double modifier = (double)EnchantmentHelper.m_44833_(stack, MobType.f_21640_);

      for(Map.Entry<Attribute, AttributeModifier> entry : multimap.entries()) {
         AttributeModifier attr = (AttributeModifier)entry.getValue();
         if (attr.m_22209_().equals(AttributeHelper.ATTACK_DAMAGE_MODIFIER)) {
            double damage = attr.m_22218_() + modifier;
            return (float)damage;
         }
      }

      return -1.0F;
   }

   public static boolean isSword(ItemStack stack) {
      return stack.m_41619_() ? false : stack.m_204117_(ItemTags.f_271388_);
   }

   public static boolean isBlunt(ItemStack stack) {
      return stack.m_41619_() ? false : stack.m_204117_(ModTags.Items.BLUNTS);
   }

   public static boolean isBowOrGun(ItemStack stack) {
      if (stack.m_41619_()) {
         return false;
      } else {
         return stack.m_204117_(net.minecraftforge.common.Tags.Items.TOOLS_BOWS) || stack.m_204117_(ModTags.Items.GUNS);
      }
   }

   public static boolean isClimaTact(ItemStack stack) {
      return stack != null && !stack.m_41619_() ? stack.m_204117_(ModTags.Items.CLIMA_TACTS) : false;
   }

   public static boolean hasImbuingOverride(@Nullable ItemStack itemStack) {
      if (itemStack != null && !itemStack.m_41619_()) {
         return ItemProperties.m_117829_(itemStack.m_41720_(), ModModelOverrides.HAKI_PROPERTY) != null;
      } else {
         return false;
      }
   }

   public static boolean isImbuable(@Nullable ItemStack itemStack) {
      if (itemStack != null && !itemStack.m_41619_()) {
         if (itemStack.m_204117_(ModTags.Items.IMBUING_DAMAGE_BONUS)) {
            return true;
         } else {
            boolean hasDamageAttribute = itemStack.m_41720_().getAttributeModifiers(EquipmentSlot.MAINHAND, itemStack).get(Attributes.f_22281_).size() > 0;
            return hasDamageAttribute;
         }
      } else {
         return false;
      }
   }

   public static boolean isImmuneToRust(@Nullable LivingEntity entity, ItemStack stack) {
      if (entity != null) {
         boolean isImbued = (Boolean)AbilityCapability.get(entity).map((props) -> (BusoshokuHakiImbuingAbility)props.getEquippedAbility((AbilityCore)BusoshokuHakiImbuingAbility.INSTANCE.get())).map(Ability::isContinuous).orElse(false);
         if (isImbued) {
            return true;
         }
      }

      return stack.m_204117_(ModTags.Items.RUST_IMMUNITY);
   }

   static {
      MARINE_HANDLE_COLOR = FactionHelper.MARINE_COLOR;
      GENERIC_HANDLE_COLOR = new Color(7817288);
      RAINBOW_COLORS = new ChatFormatting[]{ChatFormatting.RED, ChatFormatting.GOLD, ChatFormatting.YELLOW, ChatFormatting.GREEN, ChatFormatting.AQUA, ChatFormatting.BLUE, ChatFormatting.LIGHT_PURPLE};
   }
}
