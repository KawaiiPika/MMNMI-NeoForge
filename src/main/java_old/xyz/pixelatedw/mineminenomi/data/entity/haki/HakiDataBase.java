package xyz.pixelatedw.mineminenomi.data.entity.haki;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import xyz.pixelatedw.mineminenomi.api.enums.HakiType;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.events.stats.HakiExpEvent;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;

public class HakiDataBase implements IHakiData {
   private LivingEntity owner;
   private float kenbunshokuExp;
   private float busoshokuHardeningExp;
   private int hakiOveruse;
   private int haoshokuHakiColour;

   public HakiDataBase(LivingEntity owner) {
      this.owner = owner;
   }

   public float getTotalHakiExp() {
      return this.kenbunshokuExp + this.busoshokuHardeningExp;
   }

   public float getMaxHakiExp() {
      return (float)(ServerConfig.getHakiExpLimit() * (HakiType.values().length - 1));
   }

   public int getHakiOveruse() {
      return this.hakiOveruse;
   }

   public int getMaxOveruse() {
      return (int)(this.getTotalHakiExp() * 140.0F);
   }

   public void alterHakiOveruse(int value) {
      this.hakiOveruse = Mth.m_14045_(this.hakiOveruse + value, 0, this.getMaxOveruse());
   }

   public void setHakiOveruse(int value) {
      this.hakiOveruse = Mth.m_14045_(value, 0, this.getMaxOveruse());
   }

   public float getKenbunshokuHakiExp() {
      return this.kenbunshokuExp;
   }

   public boolean alterKenbunshokuHakiExp(float value, StatChangeSource source) {
      LivingEntity pre = this.owner;
      if (pre instanceof Player player) {
         HakiExpEvent.Pre pre = new HakiExpEvent.Pre(player, value, HakiType.KENBUNSHOKU, source);
         if (MinecraftForge.EVENT_BUS.post(pre)) {
            return false;
         }

         value = pre.getHakiExp();
      }

      this.kenbunshokuExp = Mth.m_14036_(this.kenbunshokuExp + value, 0.0F, (float)ServerConfig.getHakiExpLimit());
      pre = this.owner;
      if (pre instanceof Player player) {
         HakiExpEvent.Post post = new HakiExpEvent.Post(player, value, HakiType.KENBUNSHOKU, source);
         MinecraftForge.EVENT_BUS.post(post);
      }

      return true;
   }

   public void setKenbunshokuHakiExp(float value) {
      this.kenbunshokuExp = Mth.m_14036_(value, 0.0F, (float)ServerConfig.getHakiExpLimit());
   }

   public float getBusoshokuHakiExp() {
      return this.busoshokuHardeningExp;
   }

   public boolean alterBusoshokuHakiExp(float value, StatChangeSource source) {
      LivingEntity pre = this.owner;
      if (pre instanceof Player player) {
         HakiExpEvent.Pre pre = new HakiExpEvent.Pre(player, value, HakiType.BUSOSHOKU, source);
         if (MinecraftForge.EVENT_BUS.post(pre)) {
            return false;
         }

         value = pre.getHakiExp();
      }

      this.busoshokuHardeningExp = Mth.m_14036_(this.busoshokuHardeningExp + value, 0.0F, (float)ServerConfig.getHakiExpLimit());
      pre = this.owner;
      if (pre instanceof Player player) {
         HakiExpEvent.Post post = new HakiExpEvent.Post(player, value, HakiType.BUSOSHOKU, source);
         MinecraftForge.EVENT_BUS.post(post);
      }

      return true;
   }

   public void setBusoshokuHakiExp(float value) {
      this.busoshokuHardeningExp = Mth.m_14036_(value, 0.0F, (float)ServerConfig.getHakiExpLimit());
   }

   public int getHaoshokuHakiColour() {
      return this.haoshokuHakiColour;
   }

   public void setHaoshokuHakiColour(int colour) {
      this.haoshokuHakiColour = colour;
   }

   public CompoundTag serializeNBT() {
      CompoundTag props = new CompoundTag();
      props.m_128350_("kenHakiExp", this.getKenbunshokuHakiExp());
      props.m_128350_("busoHakiExp", this.getBusoshokuHakiExp());
      props.m_128405_("hakiOveruse", this.getHakiOveruse());
      return props;
   }

   public void deserializeNBT(CompoundTag nbt) {
      this.setKenbunshokuHakiExp(nbt.m_128457_("kenHakiExp"));
      this.setBusoshokuHakiExp(nbt.m_128457_("busoHakiExp"));
      this.setHakiOveruse(nbt.m_128451_("hakiOveruse"));
   }
}
