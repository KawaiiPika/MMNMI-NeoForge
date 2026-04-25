package xyz.pixelatedw.mineminenomi.entities.mobs.marines;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class MorganEntity extends OPEntity {

   public MorganEntity(EntityType<? extends MorganEntity> type, Level world) {
      super(type, world);
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes()
         .add(Attributes.MAX_HEALTH, 200.0D)
         .add(Attributes.MOVEMENT_SPEED, 0.29D)
         .add(Attributes.ATTACK_DAMAGE, 6.0D)
         .add(Attributes.FOLLOW_RANGE, 200.0D)
         .add(Attributes.ARMOR, 10.0D)
         .add(Attributes.ATTACK_SPEED, 0.3D)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.5D);
   }
}
