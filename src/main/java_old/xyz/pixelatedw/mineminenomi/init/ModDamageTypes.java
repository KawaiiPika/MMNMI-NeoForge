package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {
   public static final ResourceKey<DamageType> HEART_ATTACK = register("heart_attack", "%1$s died due to a heart attack");
   public static final ResourceKey<DamageType> DEVILS_CURSE = register("devils_curse", "%1$s died while trying to eat multiple Devil Fruits");
   public static final ResourceKey<DamageType> SOULBOUND = register("souldbound", "%1$s died because their soulbound item got destoyed", "%1$s died because their soulbound item got destoyed by %2$s");
   public static final ResourceKey<DamageType> OUT_OF_BODY = register("out_of_body", "%1$s's body was killed");
   public static final ResourceKey<DamageType> FROSTBITES = register("frostbites", "%1$s freezed to death");
   public static final ResourceKey<DamageType> FROZEN = register("frozen", "%1$s freezed to death");
   public static final ResourceKey<DamageType> POISON = register("poison", "%1$s died due to poison");
   public static final ResourceKey<DamageType> GRAVITY = register("gravity", "%1$s died due to gravity");
   public static final ResourceKey<DamageType> SUN_INCINERATION = register("sun_incineration", "%1$s was killed by the sun");
   public static final ResourceKey<DamageType> STORED_DAMAGE = register("stored_damage", "%1$s got killed after waking up from their drunkenness");
   public static final ResourceKey<DamageType> BLEED = register("bleed", "%1$s bled to death");
   public static final ResourceKey<DamageType> ABILITY = register("ability", "%1$s was killed by %2$s", "%1$s was killed by %2$s using %3$s");
   public static final ResourceKey<DamageType> REJECT_DIAL = register("reject_dial", "%1$s was killed by a Reject Dial", "%1$s was killed by %2$s using a Reject Dial");
   public static final ResourceKey<DamageType> PROJECTILE = register("projectile", "%1$s was shot dead", "%1$s was killed by %2$s");

   public static void bootstrap(BootstapContext<DamageType> context) {
      context.m_255272_(HEART_ATTACK, new DamageType("heart_attack", DamageScaling.NEVER, 0.0F));
      context.m_255272_(DEVILS_CURSE, new DamageType("devils_curse", DamageScaling.NEVER, 0.0F));
      context.m_255272_(SOULBOUND, new DamageType("souldbound", DamageScaling.NEVER, 0.0F));
      context.m_255272_(OUT_OF_BODY, new DamageType("out_of_body", DamageScaling.NEVER, 0.0F));
      context.m_255272_(FROSTBITES, new DamageType("frostbites", DamageScaling.NEVER, 0.0F));
      context.m_255272_(FROZEN, new DamageType("frozen", DamageScaling.NEVER, 0.0F));
      context.m_255272_(POISON, new DamageType("poison", DamageScaling.NEVER, 0.0F));
      context.m_255272_(GRAVITY, new DamageType("gravity", DamageScaling.NEVER, 0.0F));
      context.m_255272_(SUN_INCINERATION, new DamageType("sun_incineration", DamageScaling.NEVER, 0.0F));
      context.m_255272_(STORED_DAMAGE, new DamageType("stored_damage", DamageScaling.NEVER, 0.0F));
      context.m_255272_(BLEED, new DamageType("bleed", DamageScaling.NEVER, 0.0F));
      context.m_255272_(ABILITY, new DamageType("ability", DamageScaling.NEVER, 0.0F));
      context.m_255272_(REJECT_DIAL, new DamageType("reject_dial", DamageScaling.NEVER, 0.0F));
      context.m_255272_(PROJECTILE, new DamageType("projectile", DamageScaling.NEVER, 0.0F));
   }

   private static ResourceKey<DamageType> register(String id, String deathMessage) {
      return register(id, deathMessage, deathMessage);
   }

   private static ResourceKey<DamageType> register(String id, String deathMessage, String playerDeathMessage) {
      ModRegistry.registerName("death.attack." + id, deathMessage);
      ModRegistry.registerName("death.attack." + id + ".item", deathMessage);
      ModRegistry.registerName("death.attack." + id + ".player", playerDeathMessage);
      return ResourceKey.m_135785_(Registries.f_268580_, ResourceLocation.fromNamespaceAndPath("mineminenomi", id));
   }

   private static ResourceKey<DamageType> register(String name) {
      return ResourceKey.m_135785_(Registries.f_268580_, ResourceLocation.fromNamespaceAndPath("mineminenomi", name));
   }

   public static void init() {
   }
}
