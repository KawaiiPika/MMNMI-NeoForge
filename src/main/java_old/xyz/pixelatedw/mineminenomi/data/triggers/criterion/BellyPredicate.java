package xyz.pixelatedw.mineminenomi.data.triggers.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.Nullable;

public class BellyPredicate {
   public static final BellyPredicate ANY;
   private final MinMaxBounds.Ints amount;
   private final Boolean isBountyReward;

   public BellyPredicate(MinMaxBounds.Ints amount, Boolean isBountyReward) {
      this.amount = amount;
      this.isBountyReward = isBountyReward;
   }

   public boolean matches(ServerPlayer player, int amount, boolean isBountyReward) {
      if (this == ANY) {
         return true;
      } else {
         return this.amount.m_55390_(amount);
      }
   }

   public JsonElement serializeToJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         jsonobject.add("amount", this.amount.m_55328_());
         if (this.isBountyReward != null) {
            jsonobject.addProperty("isBountyReward", this.isBountyReward);
         }

         return jsonobject;
      }
   }

   public static BellyPredicate fromJson(@Nullable JsonElement element) {
      if (element != null && !element.isJsonNull()) {
         JsonObject jsonobject = GsonHelper.m_13918_(element, "belly");
         MinMaxBounds.Ints amount = Ints.m_55373_(jsonobject.get("amount"));
         Boolean isBountyReward = jsonobject.has("isBountyReward") ? GsonHelper.m_13912_(jsonobject, "isBountyReward") : null;
         return new BellyPredicate(amount, isBountyReward);
      } else {
         return ANY;
      }
   }

   static {
      ANY = new BellyPredicate(Ints.f_55364_, Boolean.FALSE);
   }
}
