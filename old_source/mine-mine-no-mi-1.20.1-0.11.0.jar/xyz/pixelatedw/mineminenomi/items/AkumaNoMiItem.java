package xyz.pixelatedw.mineminenomi.items;

import com.google.common.collect.Sets;
import java.awt.Color;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.MinecartHopper;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.CommandAbility;
import xyz.pixelatedw.mineminenomi.api.DFEncyclopediaEntry;
import xyz.pixelatedw.mineminenomi.api.OneFruitEntry;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.enums.FruitType;
import xyz.pixelatedw.mineminenomi.api.events.onefruit.EatDevilFruitEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.items.IFruitColor;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.data.world.OneFruitWorldData;
import xyz.pixelatedw.mineminenomi.entities.AkumaNoMiEntity;
import xyz.pixelatedw.mineminenomi.handlers.ability.ProgressionHandler;
import xyz.pixelatedw.mineminenomi.handlers.world.RandomizedFruitsHandler;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncDevilFruitPacket;

public class AkumaNoMiItem extends Item implements IFruitColor {
   private ResourceLocation key;
   private int tier;
   public FruitType type;
   private RegistryObject<? extends AbilityCore<?>>[] abilities;
   public static final int GENERIC_FRUIT_VARIATIONS = 10;
   private static final Color[] STEM_COLORS = new Color[]{WyHelper.hexToRGB("#ccc774"), WyHelper.hexToRGB("#8a5216"), WyHelper.hexToRGB("#025f00"), WyHelper.hexToRGB("#aecd6d"), WyHelper.hexToRGB("#f56fe3"), WyHelper.hexToRGB("#f93434")};

   public AkumaNoMiItem(int tier, FruitType type, RegistryObject<? extends AbilityCore<?>>... abilitiesArray) {
      super((new Item.Properties()).m_41487_(1).m_41489_(Foods.f_38810_));
      this.type = type;
      this.abilities = abilitiesArray;
      this.tier = tier;
      if (this.type == FruitType.LOGIA) {
         ModValues.LOGIA_FRUITS.add(this);
      }

      ModValues.DEVIL_FRUITS.add(this);
   }

   public void setAbilities(RegistryObject<? extends AbilityCore<?>>... abilitiesArray) {
      this.abilities = abilitiesArray;
   }

   public Component m_7626_(ItemStack stack) {
      if (ServerConfig.getRandomizedFruits()) {
         return stack.m_41782_() && stack.m_41783_().m_128471_("deciphered") ? this.getShiftedFruit().getDevilFruitName() : ModI18n.ITEM_GENERIC_FRUIT;
      } else {
         return super.m_7626_(stack);
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      player.m_6672_(hand);
      return InteractionResultHolder.m_19090_(itemstack);
   }

   public ItemStack m_5922_(ItemStack itemStack, Level world, LivingEntity livingEntity) {
      if (livingEntity instanceof Player player) {
         EatDevilFruitEvent.Pre preEvent = new EatDevilFruitEvent.Pre(player, itemStack);
         if (MinecraftForge.EVENT_BUS.post(preEvent)) {
            return itemStack;
         } else {
            if (!player.m_9236_().f_46443_) {
               IDevilFruit devilFruitProps = (IDevilFruit)DevilFruitCapability.get(player).orElse((Object)null);
               IAbilityData abilityDataProps = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
               OneFruitWorldData worldData = OneFruitWorldData.get();
               AkumaNoMiItem eatenItem = (AkumaNoMiItem)itemStack.m_41720_();
               if (ServerConfig.getRandomizedFruits()) {
                  eatenItem = eatenItem.getShiftedFruit();
               }

               ResourceLocation eatenFruit = ForgeRegistries.ITEMS.getKey(eatenItem);
               boolean hasAnyFruit = devilFruitProps.hasAnyDevilFruit();
               boolean hasYami = devilFruitProps.hasDevilFruit(ModFruits.YAMI_YAMI_NO_MI);
               boolean flag = worldData.isFruitInUse(eatenFruit);
               flag |= !worldData.updateOneFruit(eatenFruit, player.m_20148_(), OneFruitEntry.Status.IN_USE);
               if (ServerConfig.hasOneFruitPerWorldSimpleLogic() && flag) {
                  player.m_5661_(ModI18n.ITEM_MESSAGE_FRUIT_ALREADY_USED, false);
                  itemStack.m_41774_(1);
                  return itemStack;
               }

               if (!ServerConfig.isYamiPowerEnabled() && hasAnyFruit) {
                  this.applyCurseDeath(player);
                  itemStack.m_41774_(1);
                  worldData.lostOneFruit(eatenFruit, player, "Devil Fruits Curse");
                  return itemStack;
               }

               if (ServerConfig.isYamiPowerEnabled()) {
                  if (hasAnyFruit && eatenItem != ModFruits.YAMI_YAMI_NO_MI.get() && !hasYami) {
                     this.applyCurseDeath(player);
                     itemStack.m_41774_(1);
                     worldData.lostOneFruit(eatenFruit, player, "Devil Fruits Curse");
                     return itemStack;
                  }

                  if (eatenItem == ModFruits.YAMI_YAMI_NO_MI.get() && hasYami) {
                     this.applyCurseDeath(player);
                     itemStack.m_41774_(1);
                     worldData.lostOneFruit(eatenFruit, player, "Devil Fruits Curse");
                     return itemStack;
                  }

                  if (hasAnyFruit && !devilFruitProps.hasDevilFruit(ModFruits.YAMI_YAMI_NO_MI) && devilFruitProps.hasYamiPower()) {
                     this.applyCurseDeath(player);
                     itemStack.m_41774_(1);
                     worldData.lostOneFruit(eatenFruit, player, "Devil Fruits Curse");
                     return itemStack;
                  }
               }

               if (eatenItem == ModFruits.YAMI_YAMI_NO_MI.get() && ServerConfig.isYamiPowerEnabled()) {
                  devilFruitProps.setYamiPower(true);
               }

               boolean sendSync = false;
               if (!hasAnyFruit || hasYami && ServerConfig.isYamiPowerEnabled()) {
                  DFEncyclopediaEntry elements = eatenItem.getRandomElements(world);
                  DFEncyclopediaItem.updateEncyclopediae(player, eatenFruit, elements);
                  devilFruitProps.setDevilFruit(eatenItem);
                  sendSync = true;
               }

               if (player instanceof ServerPlayer) {
                  ServerPlayer serverPlayer = (ServerPlayer)player;
                  ModAdvancements.CONSUME_DEVIL_FRUIT.trigger(serverPlayer, this.getRegistryKey());
               }

               if (eatenItem != ModFruits.YOMI_YOMI_NO_MI.get()) {
                  for(RegistryObject<? extends AbilityCore<?>> reg : eatenItem.abilities) {
                     if (!AbilityHelper.verifyIfAbilityIsBanned((AbilityCore)reg.get()) && !abilityDataProps.hasUnlockedAbility((AbilityCore)reg.get())) {
                        AbilityHelper.checkAndUnlockAbility(livingEntity, (AbilityCore)reg.get());
                     }
                  }

                  if (eatenItem == ModFruits.KAGE_KAGE_NO_MI.get() || eatenItem == ModFruits.ITO_ITO_NO_MI.get()) {
                     AbilityHelper.checkAndUnlockAbility(livingEntity, (AbilityCore)CommandAbility.INSTANCE.get());
                  }

                  sendSync = true;
                  ModNetwork.sendTo(new SSyncAbilityDataPacket(player), player);
               }

               if (sendSync) {
                  ModNetwork.sendTo(new SSyncDevilFruitPacket(player), player);
               }

               if (eatenItem == ModFruits.HITO_HITO_NO_MI.get()) {
                  player.m_5661_(ModI18n.ITEM_MESSAGE_GAINED_ENLIGHTENMENT, false);
                  ProgressionHandler.checkForRacialUnlocks(player);
               }

               player.m_5584_(world, itemStack);
            }

            EatDevilFruitEvent.Post postEvent = new EatDevilFruitEvent.Post(player, itemStack);
            MinecraftForge.EVENT_BUS.post(postEvent);
            itemStack.m_41774_(1);
            return itemStack;
         }
      } else {
         return itemStack;
      }
   }

   public void m_7373_(ItemStack itemStack, @Nullable Level world, List<Component> list, TooltipFlag par4) {
      if (!RandomizedFruitsHandler.Client.HAS_RANDOMIZED_FRUIT) {
         if (ModFruits.MOCHI_MOCHI_NO_MI != null && this == ModFruits.MOCHI_MOCHI_NO_MI.get()) {
            list.add(Component.m_237113_(String.valueOf(ChatFormatting.GRAY) + "Yanagi Mochi"));
            list.add(Component.m_237113_(String.valueOf(ChatFormatting.GRAY) + "Mochi Tsuki"));
            list.add(Component.m_237113_(String.valueOf(ChatFormatting.GRAY) + "Kaku Mochi"));
            list.add(Component.m_237113_(String.valueOf(ChatFormatting.GRAY) + "Yaki Mochi"));
            list.add(Component.m_237113_(String.valueOf(ChatFormatting.GRAY) + "Zan Giri Mochi"));
            list.add(Component.m_237113_(String.valueOf(ChatFormatting.GRAY) + "Nagare Mochi"));
            list.add(Component.m_237113_(String.valueOf(ChatFormatting.GRAY) + "Muso Donuts"));
            list.add(Component.m_237113_(String.valueOf(ChatFormatting.GRAY) + "Kuzu Mochi"));
            list.add(Component.m_237113_(String.valueOf(ChatFormatting.GRAY) + "Mochi Invulnerability"));
         } else {
            for(int i = 0; i < this.abilities.length; ++i) {
               AbilityCore<?> core = (AbilityCore)this.abilities[i].get();
               if (core != null && !AbilityHelper.verifyIfAbilityIsBanned(core) && !core.isHidden()) {
                  list.add(core.getLocalizedName().m_6881_().m_130940_(ChatFormatting.GRAY));
               }
            }
         }

         list.add(CommonComponents.f_237098_);
         String var10001 = String.valueOf(this.type.getColor());
         list.add(Component.m_237113_(var10001 + this.type.getName()));
      } else {
         list.clear();
      }

   }

   public boolean hasCustomEntity(ItemStack stack) {
      return true;
   }

   public Entity createEntity(Level world, Entity entity, ItemStack itemStack) {
      if (!ServerConfig.hasOneFruitPerWorldSimpleLogic()) {
         return null;
      } else {
         this.applyRandomness(world, itemStack);
         AkumaNoMiEntity newEntity = new AkumaNoMiEntity(world, entity.m_20182_().f_82479_, entity.m_20182_().f_82480_, entity.m_20182_().f_82481_, itemStack);
         Entity owner = ((ItemEntity)entity).m_19749_();
         if (owner != null) {
            newEntity.m_32052_(owner.m_20148_());
         }

         newEntity.m_32010_(40);
         AbilityHelper.setDeltaMovement(newEntity, entity.m_20184_());
         return newEntity;
      }
   }

   public boolean onEntityItemUpdate(ItemStack itemStack, ItemEntity entity) {
      if (entity.m_9236_().f_46443_) {
         return false;
      } else {
         OneFruitWorldData worldData = OneFruitWorldData.get();
         ResourceLocation fruitKey = this.getRegistryKey();
         boolean shouldRemove = false;
         String removeReason = null;

         for(BlockPos pos : WyHelper.getNearbyTileEntities(entity.m_20183_(), entity.m_9236_(), 2)) {
            BlockEntity te = entity.m_9236_().m_7702_(pos);
            if (te instanceof HopperBlockEntity) {
               shouldRemove = true;
               removeReason = "Thrown into hopper";
               break;
            }
         }

         List<Entity> hopperMinecarts = WyHelper.<Entity>getNearbyEntities(entity.m_20182_(), entity.m_9236_(), (double)0.5F, (Predicate)null, MinecartHopper.class);
         if (hopperMinecarts.size() > 0) {
            shouldRemove = true;
            removeReason = "Thrown into Hopper Minecart";
         }

         List<Entity> foxes = WyHelper.<Entity>getNearbyEntities(entity.m_20182_(), entity.m_9236_(), (double)1.5F, (Predicate)null, Fox.class);
         if (foxes.size() > 0) {
            shouldRemove = true;
            removeReason = "Fox took fruit";
         }

         if (shouldRemove) {
            entity.m_142687_(RemovalReason.DISCARDED);
            if (entity.m_19749_() != null) {
               Player thrower = entity.m_9236_().m_46003_(entity.m_19749_().m_20148_());
               if (thrower != null) {
                  thrower.m_150109_().m_36054_(itemStack);
               } else {
                  worldData.lostOneFruit(fruitKey, (LivingEntity)null, removeReason);
               }
            } else {
               worldData.lostOneFruit(fruitKey, (LivingEntity)null, removeReason);
            }
         }

         return false;
      }
   }

   public void applyRandomness(@Nullable Level world, ItemStack itemStack) {
      if (RandomizedFruitsHandler.Client.HAS_RANDOMIZED_FRUIT) {
         RandomizedFruitsHandler.Client.DIRTY = true;
         if (!this.hasBaseColor(itemStack) || !this.hasStemColor(itemStack) || RandomizedFruitsHandler.Client.DIRTY) {
            this.removeBaseColor(itemStack);
            this.removeStemColor(itemStack);
            AkumaNoMiItem randomFruit = this.getShiftedFruit();
            DFEncyclopediaEntry randomElements = randomFruit.getRandomElements(world);
            if (randomElements == null) {
               return;
            }

            this.setBaseColor(itemStack, ((Color)randomElements.getBaseColor().get()).getRGB());
            this.setStemColor(itemStack, ((Color)randomElements.getStemColor().get()).getRGB());
            int type = (Integer)randomElements.getShape().get();
            itemStack.m_41784_().m_128405_("type", type);
            RandomizedFruitsHandler.Client.DIRTY = false;
         }
      }

   }

   public DFEncyclopediaEntry getRandomElements(@Nullable LevelAccessor world) {
      ResourceLocation key = ForgeRegistries.ITEMS.getKey(this);
      if (key == null) {
         return null;
      } else {
         long seed = RandomizedFruitsHandler.Common.SEED + (long)key.hashCode();
         if (world == null || world.m_5776_()) {
            seed = (Long)RandomizedFruitsHandler.Client.FRUIT_SEEDS.get(key.hashCode());
         }

         Random rand = new Random(seed);
         float red = rand.nextFloat();
         float green = rand.nextFloat();
         float blue = rand.nextFloat();
         Optional<Integer> type = Optional.of(rand.nextInt(10) + 1);
         Optional<Color> baseColor = Optional.of(new Color(red, green, blue));
         Optional<Color> stemColor = Optional.of(STEM_COLORS[rand.nextInt(STEM_COLORS.length)]);
         return DFEncyclopediaEntry.of(type, baseColor, stemColor);
      }
   }

   public AkumaNoMiItem getShiftedFruit() {
      int seed = Math.max(1, (int)(RandomizedFruitsHandler.Common.SEED % 10L));
      int pos = ModValues.DEVIL_FRUITS.indexOf(this);
      int shifted = (pos + seed) % ModValues.DEVIL_FRUITS.size();
      AkumaNoMiItem randomFruit = (AkumaNoMiItem)ModValues.DEVIL_FRUITS.get(shifted);
      return randomFruit;
   }

   @Nullable
   public AkumaNoMiItem getReverseShiftedFruit() {
      for(AkumaNoMiItem fruit : ModValues.DEVIL_FRUITS) {
         AkumaNoMiItem shiftedFruit = fruit.getShiftedFruit();
         if (shiftedFruit == this) {
            return fruit;
         }
      }

      return null;
   }

   private void applyCurseDeath(Player player) {
      player.m_6469_(ModDamageSources.getInstance().devilsCurse(), Float.MAX_VALUE);
   }

   public ResourceLocation getRegistryKey() {
      if (this.key == null) {
         this.key = ForgeRegistries.ITEMS.getKey(this);
      }

      return this.key;
   }

   public RegistryObject<? extends AbilityCore<?>>[] getRawRegistryAbilities() {
      return this.abilities;
   }

   public HashSet<RegistryObject<? extends AbilityCore<?>>> getRegistryAbilities() {
      return Sets.newHashSet(this.abilities);
   }

   public List<? extends AbilityCore<?>> getAbilities() {
      return Arrays.stream(this.abilities).filter(RegistryObject::isPresent).map(RegistryObject::get).toList();
   }

   public int getTier() {
      return this.tier;
   }

   public FruitType getType() {
      return this.type;
   }

   public Component getDevilFruitName() {
      return this.m_41466_();
   }
}
