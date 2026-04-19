package xyz.pixelatedw.mineminenomi.data.entity.projectileextra;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

public class ProjectileExtrasBase implements IProjectileExtras {
   private final Projectile owner;
   private boolean isBusoshokuImbued;
   private boolean isBusoshokuShrouded;
   private boolean isHaoshokuInfused;
   private ItemStack weaponUsed;

   public ProjectileExtrasBase(Projectile owner) {
      this.weaponUsed = ItemStack.f_41583_;
      this.owner = owner;
   }

   public void setProjectileBusoshokuImbued(boolean flag) {
      this.isBusoshokuImbued = flag;
   }

   public boolean isProjectileBusoshokuImbued() {
      return this.isBusoshokuImbued;
   }

   public void setProjectileBusoshokuShrouded(boolean flag) {
      this.isBusoshokuShrouded = flag;
   }

   public boolean isProjectileBusoshokuShrouded() {
      return this.isBusoshokuShrouded;
   }

   public void setProjectileHaoshokuInfused(boolean flag) {
      this.isHaoshokuInfused = flag;
   }

   public boolean isProjectileHaoshokuInfused() {
      return this.isHaoshokuInfused;
   }

   public void setWeaponUsed(ItemStack stack) {
      if (stack == null) {
         stack = ItemStack.f_41583_;
      }

      this.weaponUsed = stack;
   }

   public ItemStack getWeaponUsed() {
      return this.weaponUsed;
   }

   public CompoundTag serializeNBT() {
      CompoundTag nbt = new CompoundTag();
      nbt.m_128379_("isProjectileBusoshokuImbued", this.isProjectileBusoshokuImbued());
      nbt.m_128379_("isProjectileBusoshokuShrouded", this.isProjectileBusoshokuShrouded());
      nbt.m_128379_("isProjectileHaoshokuInfused", this.isProjectileHaoshokuInfused());
      ItemStack weaponUsed = this.getWeaponUsed();
      if (weaponUsed != null) {
         nbt.m_128359_("weaponUsed", ForgeRegistries.ITEMS.getKey(weaponUsed.m_41720_()).toString());
      }

      return nbt;
   }

   public void deserializeNBT(CompoundTag nbt) {
      this.setProjectileBusoshokuImbued(nbt.m_128471_("isProjectileBusoshokuImbued"));
      this.setProjectileBusoshokuShrouded(nbt.m_128471_("isProjectileBusoshokuShrouded"));
      this.setProjectileHaoshokuInfused(nbt.m_128471_("isProjectileHaoshokuInfused"));
      this.setWeaponUsed(new ItemStack((ItemLike)ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(nbt.m_128461_("weaponUsed")))));
   }
}
