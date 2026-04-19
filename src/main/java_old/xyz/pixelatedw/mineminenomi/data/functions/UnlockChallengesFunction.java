package xyz.pixelatedw.mineminenomi.data.functions;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.ChallengeCapability;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class UnlockChallengesFunction extends LootItemConditionalFunction {
   private ChallengeCore<?>[] unlocks;

   protected UnlockChallengesFunction(LootItemCondition[] conditions, ChallengeCore<?>[] unlocks) {
      super(conditions);
      this.unlocks = unlocks;
   }

   protected ItemStack m_7372_(ItemStack stack, LootContext context) {
      Entity entity = (Entity)context.m_78953_(LootContextParams.f_81455_);
      if (entity != null && entity instanceof Player player) {
         ChallengeCapability.get(player).ifPresent((props) -> {
            for(ChallengeCore<?> core : this.unlocks) {
               if (core != null) {
                  props.addChallenge(core);
               }
            }

         });
         stack.m_41714_(ModI18n.REWARDS_FUNC_NAME);
         stack.m_41784_().m_128405_("_unlocksAmount", this.unlocks.length);
         ListTag unlocksNbt = new ListTag();

         for(ChallengeCore<?> core : this.unlocks) {
            if (core != null) {
               StringTag stringNbt = StringTag.m_129297_(core.getLocalizedTitle().getString());
               unlocksNbt.add(stringNbt);
            }
         }

         stack.m_41784_().m_128365_("_unlocks", unlocksNbt);
      }

      return stack;
   }

   public LootItemFunctionType m_7162_() {
      return (LootItemFunctionType)ModLootTypes.UNLOCK_CHALLENGES.get();
   }

   public static LootItemConditionalFunction.Builder<?> builder(ChallengeCore<?>... unlocks) {
      return m_80683_((condition) -> new UnlockChallengesFunction(condition, unlocks));
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<UnlockChallengesFunction> {
      public void serialize(JsonObject object, UnlockChallengesFunction func, JsonSerializationContext context) {
         super.m_6170_(object, func, context);
         JsonArray unlocks = new JsonArray();

         for(ChallengeCore<?> core : func.unlocks) {
            if (core != null) {
               unlocks.add(core.getRegistryKey().toString());
            }
         }

         object.add("unlocks", unlocks);
      }

      public UnlockChallengesFunction deserialize(JsonObject object, JsonDeserializationContext context, LootItemCondition[] cond) {
         JsonArray unlocksJson = GsonHelper.m_13832_(object, "unlocks", new JsonArray());
         ChallengeCore<?>[] unlocksId = new ChallengeCore[unlocksJson.size()];

         for(int i = 0; i < unlocksId.length; ++i) {
            ResourceLocation id = ResourceLocation.parse(GsonHelper.m_13805_(unlocksJson.get(i), "unlocks[" + i + "]"));
            unlocksId[i] = (ChallengeCore)((IForgeRegistry)WyRegistry.CHALLENGES.get()).getValue(id);
         }

         return new UnlockChallengesFunction(cond, unlocksId);
      }
   }
}
