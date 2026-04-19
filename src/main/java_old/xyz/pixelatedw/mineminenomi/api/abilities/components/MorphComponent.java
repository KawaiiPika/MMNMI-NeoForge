package xyz.pixelatedw.mineminenomi.api.abilities.components;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.IPartType;
import xyz.pixelatedw.mineminenomi.api.entities.PartEntityType;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SChangeMorphPacket;

public class MorphComponent extends AbilityComponent<IAbility> {
   private MorphInfo morphInfo;
   private boolean isMorphed;
   private final HashMap<String, PartEntity<?>> parts = new HashMap();

   public MorphComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.MORPH.get(), ability);
   }

   public void startMorph(LivingEntity entity, MorphInfo info) {
      if (!this.isMorphed()) {
         this.ensureIsRegistered();
         this.morphInfo = info;
         DevilFruitCapability.get(entity).ifPresent((props) -> props.addMorph(info));
         this.isMorphed = true;
         this.updateMorphSize(entity);
         if (!entity.m_9236_().f_46443_) {
            ModNetwork.sendToAllTrackingAndSelf(new SChangeMorphPacket(entity, this.getAbility(), this.morphInfo, true), entity);
         }

      }
   }

   public void updateMorphSize(LivingEntity entity) {
      entity.m_20153_();
      entity.m_6210_();
      if (!entity.m_9236_().f_46443_) {
         ((ServerLevel)entity.m_9236_()).m_7726_().m_8394_(entity, new ClientboundSetPassengersPacket(entity));
      }

   }

   public void stopMorph(LivingEntity entity) {
      if (this.isMorphed()) {
         DevilFruitCapability.get(entity).ifPresent((props) -> props.removeMorph(this.morphInfo));
         this.isMorphed = false;
         this.updateMorphSize(entity);
         if (!entity.m_9236_().f_46443_) {
            ModNetwork.sendToAllTrackingAndSelf(new SChangeMorphPacket(entity, this.getAbility(), this.morphInfo, false), entity);
         }

         this.morphInfo = null;
         this.removePartsFromWorld();
         this.parts.clear();
      }
   }

   public @Nullable MorphInfo getMorphInfo() {
      return this.morphInfo;
   }

   public boolean isMorphed() {
      return this.isMorphed;
   }

   public HashMap<String, PartEntity<?>> getParts() {
      return new HashMap(this.parts);
   }

   public void addPart(String name, PartEntity<? extends Entity> part) {
      this.parts.put(name, part);
      Level level = part.m_9236_();
      if (level.f_46443_) {
         Int2ObjectMap<PartEntity<?>> partEntities = (Int2ObjectMap)WyHelper.getPrivateValue(ClientLevel.class, (ClientLevel)level, "partEntities");
         partEntities.put(part.m_19879_(), part);
      } else {
         Int2ObjectMap<PartEntity<?>> dragonParts = (Int2ObjectMap)WyHelper.getPrivateValue(ServerLevel.class, (ServerLevel)level, "dragonParts");
         dragonParts.put(part.m_19879_(), part);
      }

   }

   private void removePartsFromWorld() {
      for(PartEntity<?> part : this.getParts().values()) {
         Level level = part.m_9236_();
         if (level.f_46443_) {
            Int2ObjectMap<PartEntity<?>> partEntities = (Int2ObjectMap)WyHelper.getPrivateValue(ClientLevel.class, (ClientLevel)level, "partEntities");
            partEntities.remove(part.m_19879_());
         } else {
            Int2ObjectMap<PartEntity<?>> dragonParts = (Int2ObjectMap)WyHelper.getPrivateValue(ServerLevel.class, (ServerLevel)level, "dragonParts");
            dragonParts.remove(part.m_19879_());
         }
      }

   }

   public @Nullable CompoundTag save() {
      CompoundTag nbt = super.save();
      if (this.morphInfo != null) {
         ResourceLocation morphId = this.morphInfo.getKey();
         if (morphId != null) {
            nbt.m_128359_("morph", morphId.toString());
         }
      }

      nbt.m_128379_("isMorphed", this.isMorphed);
      CompoundTag partsTag = new CompoundTag();

      for(Map.Entry<String, PartEntity<?>> entry : this.parts.entrySet()) {
         CompoundTag partTag = new CompoundTag();
         String name = (String)entry.getKey();
         PartEntity<?> part = (PartEntity)entry.getValue();
         if (part != null && part instanceof IPartType && ((IPartType)part).getPartType() != null) {
            partTag.m_128405_("parentId", part.getParent().m_19879_());
            partTag.m_128359_("partType", ((IPartType)part).getPartType().getKey().toString());
            partTag.m_128405_("partId", part.m_19879_());
            partTag.m_128365_("data", part.serializeNBT());
            partsTag.m_128365_(name, partTag);
         }
      }

      nbt.m_128365_("parts", partsTag);
      return nbt;
   }

   public void load(CompoundTag nbt) {
      super.load(nbt);
      if (nbt.m_128441_("morph")) {
         ResourceLocation morphId = ResourceLocation.parse(nbt.m_128461_("morph"));
         this.morphInfo = (MorphInfo)((IForgeRegistry)WyRegistry.MORPHS.get()).getValue(morphId);
      }

      this.isMorphed = nbt.m_128471_("isMorphed");
      Minecraft mc = Minecraft.m_91087_();
      LocalPlayer player = mc.f_91074_;
      if (player != null) {
         this.parts.clear();
         if (nbt.m_128425_("parts", 10)) {
            CompoundTag partsTag = nbt.m_128469_("parts");

            for(String name : partsTag.m_128431_()) {
               CompoundTag partTag = partsTag.m_128469_(name);
               int parentId = partTag.m_128451_("parentId");
               Entity parent = player.m_9236_().m_6815_(parentId);
               if (parent != null) {
                  ResourceLocation partTypeKey = ResourceLocation.parse(partTag.m_128461_("partType"));
                  PartEntityType partType = (PartEntityType)((IForgeRegistry)WyRegistry.PART_ENTITY_TYPES.get()).getValue(partTypeKey);
                  if (partType != null) {
                     PartEntity<? extends Entity> part = partType.create(parent);
                     int partId = partTag.m_128451_("partId");
                     CompoundTag data = partTag.m_128469_("data");
                     part.m_20234_(partId);
                     part.deserializeNBT(data);
                     this.addPart(name, part);
                  }
               }
            }
         }

      }
   }
}
