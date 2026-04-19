package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.UUID;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class AttributeHelper {
   public static final UUID MORPH_HEALTH_UUID = UUID.fromString("47f8957b-e442-4f12-ac91-f7bedf821e89");
   public static final UUID MORPH_MOVEMENT_SPEED_UUID = UUID.fromString("73ec9572-1641-48f5-a464-f1a5f361cd2a");
   public static final UUID MORPH_ARMOR_UUID = UUID.fromString("7963cb37-cf63-4358-a5c6-7793f08e6beb");
   public static final UUID MORPH_ARMOR_TOUGHNESS_UUID = UUID.fromString("4eb40c49-8e26-450c-a42d-6e6e772d5848");
   public static final UUID MORPH_STRENGTH_UUID = UUID.fromString("99309bfc-1622-4ba5-8fa9-b0cad5152f6a");
   public static final UUID MORPH_ATTACK_SPEED_UUID = UUID.fromString("da37e4fa-67e1-4bfd-802a-72997d0b10c9");
   public static final UUID MORPH_ATTACK_REACH_UUID = UUID.fromString("7c3df274-a274-4fee-9cc8-b8cd90741b10");
   public static final UUID MORPH_STEP_HEIGHT_UUID = UUID.fromString("82e093a0-3762-48f9-befa-eed78ff34fa1");
   public static final UUID MORPH_KNOCKBACK_RESISTANCE_UUID = UUID.fromString("5e0a4164-1560-4d10-b627-90e0c24db30f");
   public static final UUID MORPH_FALL_RESISTANCE_UUID = UUID.fromString("1bac6f14-8f9a-4199-b2b7-96d68c1fcd90");
   public static final UUID MORPH_TOUGHNESS_UUID = UUID.fromString("eb3978a7-1056-401b-8d79-1ab76b5c9418");
   public static final UUID MORPH_JUMP_BOOST_UUID = UUID.fromString("f9e7706a-a628-4af9-8d62-e49ac7d59c02");
   public static final UUID MORPH_REGEN_RATE_UUID = UUID.fromString("44e331cd-347a-43dc-93ab-e8a55de54c77");
   public static final UUID MORPH_DAMAGE_REDUCTION_UUID = UUID.fromString("4807404c-713c-4795-b9df-dffbc24915ff");
   public static final UUID MORPH_SWIM_SPEED_UUID = UUID.fromString("f0a12d8e-7398-4e6a-afc1-9ceee88572e6");
   public static final UUID MORPH_GRAVITY_UUID = UUID.fromString("b3a23595-08de-45b8-9629-fdd6dc5f7de1");
   public static final UUID MORPH_MINING_SPEED_UUID = UUID.fromString("0f623bed-fd48-4619-9d4d-60a6e40614bc");
   public static final UUID MORPH_FRICTION_UUID = UUID.fromString("d0aa5f32-4905-40f2-b7b5-54733001209c");
   public static final UUID PASSIVE_HEALTH_UUID = UUID.fromString("89dda1d0-be5b-4d7b-8375-bec92df55f78");
   public static final UUID PASSIVE_TOUGHNESS_UUID = UUID.fromString("18837309-bfc8-483d-8c40-44ab292e6776");
   public static final UUID ATTACK_DAMAGE_MODIFIER = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
   public static final UUID SPEED_MODIFIER_SPRINTING_UUID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");

   public static void updateHPAttribute(LivingEntity entity) {
      if (entity != null && !entity.m_9236_().f_46443_ && ServerConfig.isExtraHeartsEnabled()) {
         AttributeInstance attr = entity.m_21051_(Attributes.f_22276_);
         if (attr == null) {
            return;
         }

         double doriki = (Double)EntityStatsCapability.get(entity).map((data) -> data.getDoriki()).orElse((double)0.0F);
         int freq = ServerConfig.getHealthGainFrequency();
         double hpDifference = doriki / (double)freq - attr.m_22115_();
         attr.m_22120_(PASSIVE_HEALTH_UUID);
         if (hpDifference > (double)0.0F) {
            AttributeModifier mod = new AttributeModifier(PASSIVE_HEALTH_UUID, "Passive HP Bonus", hpDifference, Operation.ADDITION);
            attr.m_22125_(mod);
         }

         if ((double)entity.m_21223_() > attr.m_22135_()) {
            entity.m_21153_((float)attr.m_22135_());
         }

         if (entity instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)entity;
            player.f_8906_.m_9829_(new ClientboundSetHealthPacket(entity.m_21223_(), player.m_36324_().m_38702_(), player.m_36324_().m_38722_()));
         }
      }

   }

   public static void updateToughnessAttribute(LivingEntity entity) {
      if (entity != null && !entity.m_9236_().f_46443_) {
         AttributeInstance attr = entity.m_21051_((Attribute)ModAttributes.TOUGHNESS.get());
         if (attr == null) {
            return;
         }

         double doriki = (Double)EntityStatsCapability.get(entity).map((props) -> props.getDoriki()).orElse((double)0.0F);
         AttributeModifier mod = new AttributeModifier(PASSIVE_TOUGHNESS_UUID, "Passive Toughness Bonus", 0.08 * (doriki / (double)ServerConfig.getDorikiLimit()) * (double)100.0F, Operation.ADDITION);
         attr.m_22120_(PASSIVE_TOUGHNESS_UUID);
         attr.m_22118_(mod);
      }

   }

   public static double getAttackRangeDistance(LivingEntity entity, double baseReachDistance) {
      AttributeInstance reachDistance = entity.m_21051_((Attribute)ForgeMod.ENTITY_REACH.get());
      return reachDistance != null ? baseReachDistance + reachDistance.m_22135_() : baseReachDistance;
   }

   public static double getSquaredAttackRangeDistance(LivingEntity entity, double sqBaseReachDistance) {
      double reachDistance = getAttackRangeDistance(entity, Math.sqrt(sqBaseReachDistance));
      return reachDistance * reachDistance;
   }
}
