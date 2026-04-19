package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.registries.RegistryObject;

public class ModAttributes {
   public static final RegistryObject<Attribute> FALL_RESISTANCE = ModRegistry.registerAttribute("Fall Resistance", (id) -> new RangedAttribute(id, (double)0.0F, (double)-256.0F, (double)256.0F));
   public static final RegistryObject<Attribute> JUMP_HEIGHT = ModRegistry.registerAttribute("Jump Height", (id) -> (new RangedAttribute(id, (double)1.0F, (double)-256.0F, (double)256.0F)).m_22084_(true));
   public static final RegistryObject<Attribute> REGEN_RATE = ModRegistry.registerAttribute("Regen Rate", (id) -> (new RangedAttribute(id, (double)1.0F, (double)0.0F, (double)32.0F)).m_22084_(true));
   /** @deprecated */
   @Deprecated(
      forRemoval = true
   )
   public static final RegistryObject<Attribute> STEP_HEIGHT;
   public static final RegistryObject<Attribute> PUNCH_DAMAGE;
   public static final RegistryObject<Attribute> TIME_PROGRESSION;
   public static final RegistryObject<Attribute> FLOATING_TIME;
   public static final RegistryObject<Attribute> TOUGHNESS;
   public static final RegistryObject<Attribute> GCD;
   public static final RegistryObject<Attribute> MINING_SPEED;
   /** @deprecated */
   @Deprecated
   public static final RegistryObject<Attribute> FAUX_PROTECTION;
   public static final RegistryObject<Attribute> FRICTION;

   public static void init() {
   }

   public static void changeAttributes(EntityAttributeModificationEvent event) {
      for(EntityType<? extends LivingEntity> type : event.getTypes()) {
         event.add(type, (Attribute)FALL_RESISTANCE.get());
         event.add(type, (Attribute)JUMP_HEIGHT.get());
         event.add(type, (Attribute)REGEN_RATE.get());
         event.add(type, (Attribute)PUNCH_DAMAGE.get());
         event.add(type, (Attribute)TIME_PROGRESSION.get());
         event.add(type, (Attribute)TOUGHNESS.get());
         event.add(type, (Attribute)GCD.get());
         event.add(type, (Attribute)FRICTION.get());
         if (type == EntityType.f_20532_) {
            event.add(type, (Attribute)MINING_SPEED.get());
         }

         if (type == ModMobs.BIG_DUCK.get()) {
            event.add(type, (Attribute)FLOATING_TIME.get());
         }
      }

   }

   static {
      STEP_HEIGHT = ForgeMod.STEP_HEIGHT_ADDITION;
      PUNCH_DAMAGE = ModRegistry.registerAttribute("Punch Damage", (id) -> (new RangedAttribute(id, (double)0.0F, (double)-1024.0F, (double)1024.0F)).m_22084_(true));
      TIME_PROGRESSION = ModRegistry.registerAttribute("Time Progression", (id) -> (new RangedAttribute(id, (double)1.0F, (double)0.0F, (double)1024.0F)).m_22084_(true));
      FLOATING_TIME = ModRegistry.registerAttribute("Floating Time", (id) -> (new RangedAttribute(id, (double)100.0F, (double)0.0F, (double)2048.0F)).m_22084_(true));
      TOUGHNESS = ModRegistry.registerAttribute("Toughness", (id) -> (new RangedAttribute(id, (double)0.0F, (double)0.0F, (double)20.0F)).m_22084_(true));
      GCD = ModRegistry.registerAttribute("GCD", (id) -> (new RangedAttribute(id, (double)40.0F, (double)0.0F, (double)72000.0F)).m_22084_(true));
      MINING_SPEED = ModRegistry.registerAttribute("Mining Speed", (id) -> (new RangedAttribute(id, (double)1.0F, (double)-128.0F, (double)128.0F)).m_22084_(true));
      FAUX_PROTECTION = ModRegistry.registerAttribute("Faux Protection", (id) -> (new RangedAttribute(id, (double)0.0F, (double)0.0F, (double)20.0F)).m_22084_(true));
      FRICTION = ModRegistry.registerAttribute("Friction", (id) -> (new RangedAttribute(id, (double)0.0F, (double)0.0F, 0.99)).m_22084_(true));
   }
}
