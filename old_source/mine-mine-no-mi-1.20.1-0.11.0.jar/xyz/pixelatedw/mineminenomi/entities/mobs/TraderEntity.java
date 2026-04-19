package xyz.pixelatedw.mineminenomi.entities.mobs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
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
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SelfHealEatGoal;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenTraderScreenPacket;

public abstract class TraderEntity extends OPEntity {
   private static final float SPAWNER_DESPAWN_DISTANCE = 40000.0F;
   protected List<TradeEntry> tradeEntries;
   private boolean isTrading;
   private boolean canBuy;
   private int customerCheckTick;

   public TraderEntity(EntityType<? extends TraderEntity> type, Level world) {
      this(type, world, (ResourceLocation[])null);
   }

   public TraderEntity(EntityType<? extends TraderEntity> type, Level world, ResourceLocation[] textures) {
      super(type, world, textures);
      this.tradeEntries = new ArrayList();
      this.isTrading = false;
      this.customerCheckTick = 40;
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new PanicGoal(this, (double)2.0F));
      this.f_21345_.m_25352_(3, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.f_21345_.m_25352_(4, new SelfHealEatGoal(this));
      this.f_21345_.m_25352_(5, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.f_21345_.m_25352_(5, new RandomLookAroundGoal(this));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)25.0F).m_22268_(Attributes.f_22279_, (double)0.2F).m_22268_(Attributes.f_22281_, (double)1.0F).m_22268_(Attributes.f_22276_, (double)20.0F).m_22268_(Attributes.f_22284_, (double)2.0F);
   }

   public boolean m_6785_(double distance) {
      if (this.isSpawnedViaSpawner() && distance < (double)40000.0F) {
         return false;
      } else {
         return !this.m_8077_() || ServerConfig.getDespawnWithNametag();
      }
   }

   public abstract boolean canTrade(Player var1);

   public abstract Component getTradeFailMessage();

   public abstract ResourceLocation getTradeTable();

   public abstract Currency getCurrency();

   public void m_8119_() {
      super.m_8119_();
      if (--this.customerCheckTick <= 0) {
         List<Player> customers = TargetHelper.<Player>getNearbyPlayers(this.m_9236_(), this.m_20182_(), (double)3.0F, (Predicate)null);
         if (customers.size() > 0) {
            this.m_7618_(Anchor.EYES, ((Player)customers.get(0)).m_20299_(0.0F));
            this.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 40, 0, false, false));
         }

         this.customerCheckTick = 40;
      }

   }

   public void m_7380_(CompoundTag nbt) {
      super.m_7380_(nbt);
      ListTag items = new ListTag();

      for(TradeEntry stack : this.tradeEntries) {
         CompoundTag nbtTrade = new CompoundTag();
         nbtTrade = stack.getItemStack().m_41739_(nbtTrade);
         items.add(nbtTrade);
      }

      nbt.m_128365_("Items", items);
   }

   public void m_7378_(CompoundTag nbt) {
      super.m_7378_(nbt);
      ListTag tradeList = nbt.m_128437_("Items", 10);

      for(int i = 0; i < tradeList.size(); ++i) {
         CompoundTag nbtTrade = tradeList.m_128728_(i);
         ItemStack stack = ItemStack.m_41712_(nbtTrade);
         this.tradeEntries.add(new TradeEntry(stack));
      }

   }

   public void setStacksFromNBT(CompoundTag nbt) {
      ListTag tradeList = nbt.m_128437_("Items", 10);

      for(int i = 0; i < tradeList.size(); ++i) {
         CompoundTag nbtTrade = tradeList.m_128728_(i);
         ItemStack stack = ItemStack.m_41712_(nbtTrade);
         this.tradeEntries.add(new TradeEntry(stack));
      }

   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
      spawnData = super.m_6518_(world, difficulty, reason, spawnData, dataTag);
      LootTable lootTable = this.m_9236_().m_7654_().m_278653_().m_278676_(this.getTradeTable());
      LootParams.Builder builder = new LootParams.Builder((ServerLevel)this.m_9236_());
      LootParams params = builder.m_287235_(LootContextParamSets.f_81410_);
      List<ItemStack> stacks = lootTable.m_287195_(params);
      this.tradeEntries = (List)stacks.stream().map(TradeEntry::new).collect(Collectors.toList());
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

   protected InteractionResult m_6071_(Player player, InteractionHand hand) {
      if (hand == InteractionHand.MAIN_HAND && !player.m_9236_().f_46443_) {
         ModNetwork.sendTo(new SOpenTraderScreenPacket(this.m_19879_(), this.tradeEntries), player);
         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }
}
