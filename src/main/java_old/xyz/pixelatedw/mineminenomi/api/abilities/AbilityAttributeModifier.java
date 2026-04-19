package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.Nullable;

public class AbilityAttributeModifier extends AttributeModifier {
   private AbilityCore<?> core;
   private Supplier<? extends AbilityCore<?>> supplier;

   public AbilityAttributeModifier(UUID uuid, IAbility ability, String name, double amount, AttributeModifier.Operation operation) {
      this(uuid, ability.getCore(), name, amount, operation);
   }

   public AbilityAttributeModifier(UUID uuid, AbilityCore<?> core, String name, double amount, AttributeModifier.Operation operation) {
      super(uuid, name, amount, operation);
      this.core = core;
   }

   public AbilityAttributeModifier(UUID uuid, Supplier<? extends AbilityCore<?>> supplier, String name, double amount, AttributeModifier.Operation operation) {
      super(uuid, name, amount, operation);
      this.supplier = supplier;
   }

   public @Nullable AbilityCore<?> getAbilityCore() {
      if (this.core == null && this.supplier != null) {
         this.core = (AbilityCore)this.supplier.get();
      }

      return this.core;
   }
}
