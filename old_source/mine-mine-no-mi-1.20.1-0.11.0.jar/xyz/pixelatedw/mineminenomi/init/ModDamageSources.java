package xyz.pixelatedw.mineminenomi.init;

import java.util.ArrayList;
import java.util.Objects;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.BaseDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;

public class ModDamageSources {
   private static ModDamageSources instance;
   private final Registry<DamageType> registry;
   private final DamageSource devilsCurse;
   private final DamageSource outOfBody;
   private final BaseDamageSource frostbites;
   private final BaseDamageSource frozen;
   private final BaseDamageSource poison;
   private final BaseDamageSource gravity;
   private final BaseDamageSource sunIncineration;
   private final DamageSource storedDamage;
   private final DamageSource bleed;

   public static final void init(LevelAccessor level) {
      if (instance == null) {
         instance = new ModDamageSources(level.m_9598_());
      }

   }

   public static final ModDamageSources getInstance() {
      Objects.requireNonNull(instance, "Mod Damage Sources is null after attempted initialization!");
      return instance;
   }

   public ModDamageSources(RegistryAccess registry) {
      this.registry = registry.m_175515_(Registries.f_268580_);
      this.devilsCurse = this.genericSource(ModDamageTypes.DEVILS_CURSE);
      this.outOfBody = this.genericSource(ModDamageTypes.OUT_OF_BODY);
      this.frostbites = this.genericBaseSource(ModDamageTypes.FROSTBITES, SourceElement.ICE, (ArrayList)null, (SourceHakiNature)null);
      this.frozen = this.genericBaseSource(ModDamageTypes.FROZEN, SourceElement.ICE, (ArrayList)null, (SourceHakiNature)null);
      this.poison = this.genericBaseSource(ModDamageTypes.POISON, SourceElement.POISON, (ArrayList)null, (SourceHakiNature)null);
      this.gravity = this.genericBaseSource(ModDamageTypes.GRAVITY, SourceElement.GRAVITY, (ArrayList)null, (SourceHakiNature)null);
      this.sunIncineration = this.genericBaseSource(ModDamageTypes.SUN_INCINERATION, SourceElement.FIRE, (ArrayList)null, (SourceHakiNature)null);
      this.storedDamage = this.genericSource(ModDamageTypes.STORED_DAMAGE);
      this.bleed = this.genericSource(ModDamageTypes.BLEED);
   }

   private BaseDamageSource genericBaseSource(ResourceKey<DamageType> key, @Nullable SourceElement element, @Nullable ArrayList<SourceType> types, @Nullable SourceHakiNature hakiNature) {
      return new BaseDamageSource(this.registry.m_246971_(key), element, types, hakiNature);
   }

   private DamageSource genericSource(ResourceKey<DamageType> key) {
      return new DamageSource(this.registry.m_246971_(key));
   }

   private DamageSource genericSource(ResourceKey<DamageType> key, @Nullable Entity cause) {
      return new DamageSource(this.registry.m_246971_(key), cause);
   }

   public DamageSource heartAttack(@Nullable LivingEntity attacker) {
      return this.genericSource(ModDamageTypes.HEART_ATTACK, attacker);
   }

   public DamageSource devilsCurse() {
      return this.devilsCurse;
   }

   public DamageSource souldbound(@Nullable LivingEntity attacker) {
      return this.genericSource(ModDamageTypes.SOULBOUND, attacker);
   }

   public DamageSource outOfBody() {
      return this.outOfBody;
   }

   public BaseDamageSource frostbites() {
      return this.frostbites;
   }

   public BaseDamageSource frozen() {
      return this.frozen;
   }

   public BaseDamageSource poison() {
      return this.poison;
   }

   public BaseDamageSource gravity() {
      return this.gravity;
   }

   public BaseDamageSource sunIncineration() {
      return this.sunIncineration;
   }

   public DamageSource storedDamage() {
      return this.storedDamage;
   }

   public DamageSource bleed() {
      return this.bleed;
   }

   public AbilityDamageSource ability(@Nullable Entity source, AbilityCore<?> ability) {
      return new AbilityDamageSource(this.registry.m_246971_(ModDamageTypes.ABILITY), source, ability);
   }

   public AbilityDamageSource ability(@Nullable Entity direct, @Nullable Entity source, AbilityCore<?> ability) {
      return new AbilityDamageSource(this.registry.m_246971_(ModDamageTypes.ABILITY), direct, source, ability);
   }

   public BaseDamageSource projectile(NuProjectileEntity direct, @Nullable Entity source) {
      return new BaseDamageSource(this.registry.m_246971_(ModDamageTypes.PROJECTILE), direct, source, direct.getSourceElement(), direct.getSourceTypes(), direct.getSourceHakiNature());
   }

   public DamageSource rejectDial(@Nullable Entity source) {
      return new DamageSource(this.registry.m_246971_(ModDamageTypes.REJECT_DIAL), source);
   }
}
