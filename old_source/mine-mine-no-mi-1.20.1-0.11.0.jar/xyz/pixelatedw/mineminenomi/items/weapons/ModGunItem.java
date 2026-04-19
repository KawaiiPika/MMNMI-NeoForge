package xyz.pixelatedw.mineminenomi.items.weapons;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import xyz.pixelatedw.mineminenomi.abilities.bomu.BreezeBreathBombAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.HissatsuAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.items.BulletItem;

public class ModGunItem extends ProjectileWeaponItem {
   private static final String GUN_POWDER_TAG = "gunPowder";
   private int maxGunpowder;
   private float bulletSpeed;
   private float inaccuracy;
   private int shotCooldown;
   private int reloadCooldown;
   private float damageMultiplier;
   private Predicate<ItemStack> bulletCheck;
   public static final Predicate<ItemStack> GUN_AMMO = (itemStack) -> itemStack.m_204117_(ModTags.Items.GUN_AMMO);
   public static final Predicate<ItemStack> BAZOOKA_AMMO = (itemStack) -> itemStack.m_204117_(ModTags.Items.BAZOOKA_AMMO);

   public ModGunItem(int maxDamage) {
      this(maxDamage, GUN_AMMO);
   }

   public ModGunItem(int maxDamage, Predicate<ItemStack> bulletPredicate) {
      super((new Item.Properties()).m_41487_(1).m_41503_(maxDamage));
      this.maxGunpowder = 3;
      this.bulletSpeed = 2.0F;
      this.inaccuracy = 2.0F;
      this.shotCooldown = 10;
      this.reloadCooldown = 20;
      this.damageMultiplier = 1.0F;
      this.bulletCheck = bulletPredicate;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
      ItemStack itemStack = player.m_21120_(hand);
      boolean flag = !player.m_6298_(itemStack).m_41619_();
      boolean hasGunPowder = this.getLoadedGunPowder(itemStack) > 0;
      boolean hasBreezeBombAbility = false;
      IAbilityData abilityData = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
      if (abilityData != null) {
         BreezeBreathBombAbility ability = (BreezeBreathBombAbility)abilityData.getEquippedAbility((AbilityCore)BreezeBreathBombAbility.INSTANCE.get());
         hasBreezeBombAbility = ability != null && ability.isContinuous();
      }

      if (hasBreezeBombAbility) {
         playLoadSound(itemStack, player);
         player.m_6672_(hand);
         return InteractionResultHolder.m_19090_(itemStack);
      } else {
         if (!hasGunPowder) {
            for(int i = 0; i < player.m_150109_().m_6643_(); ++i) {
               ItemStack stack = player.m_150109_().m_8020_(i);
               if (stack.m_41720_() == Items.f_42403_) {
                  int count = this.maxGunpowder;
                  if (stack.m_41613_() < count) {
                     count = stack.m_41613_();
                  }

                  this.setLoadedGunPowder(itemStack, count * 5);
                  player.m_150109_().m_7407_(i, count);
                  hasGunPowder = true;
                  player.m_36335_().m_41524_(this, this.reloadCooldown);
                  break;
               }
            }
         }

         if (!hasGunPowder) {
            return InteractionResultHolder.m_19100_(itemStack);
         } else if (!player.m_150110_().f_35937_ && !flag) {
            return InteractionResultHolder.m_19100_(itemStack);
         } else {
            playLoadSound(itemStack, player);
            player.m_6672_(hand);
            return InteractionResultHolder.m_19096_(itemStack);
         }
      }
   }

   public void m_5551_(ItemStack itemStack, Level level, LivingEntity living, int timeLeft) {
      if (living instanceof Player player) {
         ItemStack ammoStack = player.m_6298_(itemStack);
         if (!player.m_9236_().f_46443_) {
            IAbilityData abilityData = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
            if (abilityData != null) {
               BreezeBreathBombAbility ability = (BreezeBreathBombAbility)abilityData.getEquippedAbility((AbilityCore)BreezeBreathBombAbility.INSTANCE.get());
               if (ability != null && (Boolean)ability.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).map(ContinuousComponent::isContinuous).orElse(false)) {
                  ability.getComponent((AbilityComponentKey)ModAbilityComponents.PROJECTILE.get()).ifPresent((comp) -> comp.shoot(living));
                  return;
               }
            }
         }

         Item powder = ammoStack.m_41720_();
         if (!(powder instanceof BulletItem)) {
            return;
         }

         BulletItem bulletItem = (BulletItem)powder;
         int var23 = this.getLoadedGunPowder(itemStack);
         if (!level.f_46443_) {
            boolean flag = player.m_7500_() || itemStack.getEnchantmentLevel(Enchantments.f_44952_) > 0;
            int i = this.m_8105_(itemStack) - timeLeft;
            i = ForgeEventFactory.onArrowLoose(itemStack, level, player, i, !itemStack.m_41619_() || flag);
            if (i < 0) {
               return;
            }

            boolean isHitScan = HissatsuAbility.checkHitScan(player);
            int amount = itemStack.getEnchantmentLevel(Enchantments.f_44959_) > 0 ? 3 : 1;

            for(int n = 0; n < amount; ++n) {
               NuProjectileEntity proj = bulletItem.createProjectile(level, player);
               int powerEnchLevel = itemStack.getEnchantmentLevel(Enchantments.f_44988_);
               if (powerEnchLevel > 0) {
                  proj.setDamage((float)((double)proj.getDamage() + (double)powerEnchLevel * (double)0.5F + (double)0.5F));
               }

               int punchEnchLevel = itemStack.getEnchantmentLevel(Enchantments.f_44989_);
               if (punchEnchLevel > 0) {
                  proj.setKnockback(punchEnchLevel);
               }

               if (itemStack.getEnchantmentLevel(Enchantments.f_44990_) > 0) {
                  proj.m_20254_(100);
               }

               proj.setDamage((float)Math.round(proj.getDamage() * this.damageMultiplier));
               if (isHitScan) {
                  float distance = (float)proj.getMaxLife();
                  EntityHitResult result = WyHelper.rayTraceEntities(player, (double)distance, (double)1.25F, ProjectileComponent.TARGET_CHECK);
                  if (result.m_82443_() != null && result.m_82443_() instanceof LivingEntity) {
                     proj.m_6532_(result);
                  }
               } else {
                  if (n > 0) {
                     Vec3 upVec = player.m_20289_(1.0F);
                     Quaternionf quaternionf = (new Quaternionf()).setAngleAxis((double)((float)n * ((float)Math.PI / 180F)), upVec.f_82479_, upVec.f_82480_, upVec.f_82481_);
                     Vec3 viewVec = player.m_20252_(1.0F);
                     Vector3f shootVec = viewVec.m_252839_().rotate(quaternionf);
                     proj.m_6686_((double)shootVec.x(), (double)shootVec.y(), (double)shootVec.z(), this.bulletSpeed, this.inaccuracy);
                  } else {
                     proj.m_37251_(player, player.m_146909_(), player.m_146908_(), 0.0F, this.bulletSpeed, this.inaccuracy);
                  }

                  player.m_9236_().m_7967_(proj);
               }
            }

            playShootSound(itemStack, player);
            this.spawnParticles(player);
            itemStack.m_41622_(1, player, (user) -> user.m_21190_(player.m_7655_()));
            player.m_36246_(Stats.f_12982_.m_12902_(this));
         }

         player.m_36335_().m_41524_(this, this.shotCooldown);
         boolean hasInfinite = player.m_7500_() || itemStack.getEnchantmentLevel(Enchantments.f_44952_) > 0;
         if (hasInfinite) {
            return;
         }

         --var23;
         this.setLoadedGunPowder(itemStack, Math.max(0, var23));
         ammoStack.m_41774_(1);
         if (ammoStack.m_41619_()) {
            player.m_150109_().m_36057_(ammoStack);
         }
      }

   }

   public Predicate<ItemStack> m_6437_() {
      return this.bulletCheck;
   }

   public static void playLoadSound(ItemStack stack, LivingEntity entity) {
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.GUN_LOAD.get(), SoundSource.PLAYERS, 1.5F, 0.75F + entity.m_217043_().m_188501_() / 4.0F);
   }

   public static void playShootSound(ItemStack stack, LivingEntity entity) {
      if (stack.m_41720_() != ModWeapons.FLINTLOCK.get() && stack.m_41720_() != ModWeapons.WALKER.get()) {
         if (stack.m_41720_() == ModWeapons.BAZOOKA.get()) {
            entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.GENERIC_EXPLOSION.get(), SoundSource.PLAYERS, 2.0F, 0.75F + entity.m_217043_().m_188501_() / 4.0F);
         } else if (stack.m_41720_() == ModWeapons.SENRIKU.get()) {
            entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.SNIPER_SHOOT.get(), SoundSource.PLAYERS, 2.0F, 0.75F + entity.m_217043_().m_188501_() / 4.0F);
         }
      } else {
         entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.PISTOL_SHOOT.get(), SoundSource.PLAYERS, 2.0F, 0.75F + entity.m_217043_().m_188501_() / 4.0F);
      }

   }

   public void spawnParticles(LivingEntity entity) {
      Vec3 lookVec = entity.m_20154_().m_82524_((float)Math.toRadians((double)-30.0F));
      Vec3 particlePos = entity.m_20182_().m_82520_((double)0.0F, (double)entity.m_20192_(), (double)0.0F).m_82549_(lookVec);

      for(int p = 0; p < 10; ++p) {
         if (p % 2 == 0) {
            WyHelper.spawnParticles(ParticleTypes.f_123759_, (ServerLevel)entity.m_9236_(), particlePos.f_82479_, particlePos.f_82480_, particlePos.f_82481_);
         } else {
            WyHelper.spawnParticles(ParticleTypes.f_123762_, (ServerLevel)entity.m_9236_(), particlePos.f_82479_, particlePos.f_82480_, particlePos.f_82481_);
         }
      }

   }

   public void setLoadedGunPowder(ItemStack itemStack, int powder) {
      itemStack.m_41783_().m_128405_("gunPowder", powder);
   }

   public int getLoadedGunPowder(ItemStack itemStack) {
      return itemStack.m_41783_().m_128451_("gunPowder");
   }

   public int m_6615_() {
      return 32;
   }

   public int m_8105_(ItemStack stack) {
      return 72000;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.BOW;
   }

   public void m_7373_(ItemStack itemStack, @Nullable Level world, List<Component> list, TooltipFlag par4) {
      if (itemStack.m_41782_()) {
         list.add(Component.m_237110_(ModI18n.GUN_POWDER_AMOUNT, new Object[]{this.getLoadedGunPowder(itemStack)}));
      }

   }

   public ModGunItem setGunpowderLimit(int limit) {
      this.maxGunpowder = limit;
      return this;
   }

   public ModGunItem setDamageMultiplier(float damage) {
      this.damageMultiplier = damage;
      return this;
   }

   public ModGunItem setShotCooldown(int cd) {
      this.shotCooldown = cd;
      return this;
   }

   public ModGunItem setReloadCooldown(int cd) {
      this.reloadCooldown = cd;
      return this;
   }

   public ModGunItem setInaccuracy(float acc) {
      this.inaccuracy = acc;
      return this;
   }

   public ModGunItem setBulletSpeed(float speed) {
      this.bulletSpeed = speed;
      return this;
   }
}
