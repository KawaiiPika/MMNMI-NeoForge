package xyz.pixelatedw.mineminenomi.entities.mobs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import xyz.pixelatedw.mineminenomi.api.TradeEntry;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SelfHealEatGoal;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public abstract class TraderEntity extends OPEntity {
   protected List<TradeEntry> tradeEntries;
   private boolean isTrading;
   private boolean canBuy;
   private int customerCheckTick;

   public TraderEntity(EntityType<? extends TraderEntity> type, Level world) {
      super(type, world);
      this.tradeEntries = new ArrayList<>();
      this.isTrading = false;
      this.customerCheckTick = 40;
   }

   @Override
   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
      this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.8D));
      this.goalSelector.addGoal(4, new SelfHealEatGoal(this));
      this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes()
              .add(Attributes.MAX_HEALTH, 25.0D)
              .add(Attributes.MOVEMENT_SPEED, 0.2D)
              .add(Attributes.ATTACK_DAMAGE, 1.0D);
   }

   public abstract boolean canTrade(Player player);

   public abstract Component getTradeFailMessage();

   public abstract ResourceKey<LootTable> getTradeTable();

   public abstract Currency getCurrency();

   @Override
   public void tick() {
      super.tick();
      if (!this.level().isClientSide() && --this.customerCheckTick <= 0) {
         List<Player> customers = TargetHelper.getEntitiesInArea(this.level(), this, 3.0D, Player.class);
         if (!customers.isEmpty()) {
            this.lookAt(Anchor.EYES, customers.get(0).getEyePosition());
            this.addEffect(new net.minecraft.world.effect.MobEffectInstance(ModEffects.MOVEMENT_BLOCKED, 40, 0, false, false));
         }

         this.customerCheckTick = 40;
      }

   }

   @Override
   public void addAdditionalSaveData(CompoundTag nbt) {
      super.addAdditionalSaveData(nbt);
      ListTag items = new ListTag();

      for(TradeEntry stack : this.tradeEntries) {
         CompoundTag nbtTrade = new CompoundTag();
         nbtTrade = (CompoundTag)stack.getItemStack().save(this.registryAccess(), nbtTrade);
         items.add(nbtTrade);
      }

      nbt.put("Items", items);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag nbt) {
      super.readAdditionalSaveData(nbt);
      setStacksFromNBT(nbt);
   }

   public void setStacksFromNBT(CompoundTag nbt) {
      ListTag tradeList = nbt.getList("Items", 10);

      for(int i = 0; i < tradeList.size(); ++i) {
         CompoundTag nbtTrade = tradeList.getCompound(i);
         java.util.Optional<ItemStack> stackOpt = ItemStack.parse(this.registryAccess(), nbtTrade);
         stackOpt.ifPresent(stack -> this.tradeEntries.add(new TradeEntry(stack)));
      }
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
      spawnData = super.finalizeSpawn(world, difficulty, reason, spawnData);
      if (!world.isClientSide()) {
         LootTable lootTable = world.getServer().reloadableRegistries().getLootTable(this.getTradeTable());
         LootParams.Builder builder = new LootParams.Builder((ServerLevel)this.level());
         LootParams params = builder.create(LootContextParamSets.EMPTY);
         List<ItemStack> stacks = lootTable.getRandomItems(params);
         this.tradeEntries = stacks.stream().map(TradeEntry::new).collect(Collectors.toList());
      }
      return spawnData;
   }

   public List<TradeEntry> getTradingItems() {
      return this.tradeEntries;
   }

   public void setIsTrading(boolean flag) {
      this.isTrading = flag;
   }

   public void setTradingItems(List<TradeEntry> entries) {
      this.tradeEntries = entries;
   }

   public boolean canBuyFromPlayers() {
      return this.canBuy;
   }

   public void setCanBuyFromPlayers() {
      this.canBuy = true;
   }

   @Override
   protected InteractionResult mobInteract(Player player, InteractionHand hand) {
      if (hand == InteractionHand.MAIN_HAND && !this.level().isClientSide()) {
         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }
}
