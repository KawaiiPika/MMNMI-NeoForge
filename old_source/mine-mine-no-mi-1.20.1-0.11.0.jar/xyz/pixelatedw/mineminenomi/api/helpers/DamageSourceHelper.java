package xyz.pixelatedw.mineminenomi.api.helpers;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;

public class DamageSourceHelper {
   private static final HashMap<EntityType<?>, IDamageSourceHandler.NuDamageValue> ENTITY_DAMAGE_VALUES = new HashMap();

   public static void registerEntityDamageValue(EntityType<?> entity, IDamageSourceHandler.NuDamageValue value) {
      ENTITY_DAMAGE_VALUES.put(entity, value);
   }

   public static IDamageSourceHandler.NuDamageValue getDamageValueFor(EntityType<?> entity) {
      return (IDamageSourceHandler.NuDamageValue)ENTITY_DAMAGE_VALUES.get(entity);
   }

   public static ImmutableMap<EntityType<?>, IDamageSourceHandler.NuDamageValue> getEntityDamageMap() {
      return ImmutableMap.copyOf(ENTITY_DAMAGE_VALUES);
   }

   public static IDamageSourceHandler.NuDamageValue initializeDamageValueFromSource(DamageSource source) {
      if (source instanceof AbilityDamageSource abilitySource) {
         SourceType[] types = (SourceType[])abilitySource.getTypes().toArray(new SourceType[0]);
         return new IDamageSourceHandler.NuDamageValue(abilitySource.getAbilityCore(), abilitySource.getElement(), abilitySource.getHakiNature(), types);
      } else {
         Entity sourceEntity = source.m_7639_();
         Entity directEntity = source.m_7640_();
         if (directEntity != null) {
            IDamageSourceHandler.NuDamageValue directValue = getDamageValueFor(directEntity.m_6095_());
            if (directValue != null) {
               return directValue;
            }
         }

         SourceElement element = SourceElement.NONE;
         SourceHakiNature hakiNature = SourceHakiNature.UNKNOWN;
         Set<SourceType> types = new HashSet();
         if (source.m_269533_(DamageTypeTags.f_268745_)) {
            element = SourceElement.FIRE;
         } else if (source.m_269533_(DamageTypeTags.f_268419_)) {
            element = SourceElement.ICE;
         } else if (source.m_269533_(DamageTypeTags.f_268415_)) {
            element = SourceElement.EXPLOSION;
         } else if (source.m_269533_(DamageTypeTags.f_268725_)) {
            element = SourceElement.LIGHTNING;
         } else if (source.m_269533_(DamageTypeTags.f_268524_)) {
            types.add(SourceType.PROJECTILE);
            hakiNature = SourceHakiNature.IMBUING;
         } else if (!source.m_276093_(DamageTypes.f_268566_) && !source.m_276093_(DamageTypes.f_268464_)) {
            if (source.m_276093_(DamageTypes.f_268679_)) {
               element = SourceElement.SHOCKWAVE;
               types.add(SourceType.INTERNAL);
            }
         } else {
            hakiNature = SourceHakiNature.HARDENING;
            if (directEntity != null && directEntity instanceof LivingEntity) {
               LivingEntity livingAttacker = (LivingEntity)directEntity;
               ItemStack heldItem = livingAttacker.m_21205_();
               if (heldItem.m_41619_()) {
                  types.add(SourceType.FIST);
               } else if (ItemsHelper.isSword(heldItem)) {
                  types.add(SourceType.SLASH);
               } else if (ItemsHelper.isBlunt(heldItem)) {
                  types.add(SourceType.BLUNT);
               }
            }
         }

         SourceType[] typesArr = (SourceType[])types.toArray(new SourceType[0]);
         if (directEntity != null) {
            UnmodifiableIterator var13 = getEntityDamageMap().entrySet().iterator();

            while(var13.hasNext()) {
               Map.Entry<EntityType<?>, IDamageSourceHandler.NuDamageValue> entry = (Map.Entry)var13.next();
               if (directEntity.m_6095_().equals(entry.getKey())) {
                  element = ((IDamageSourceHandler.NuDamageValue)entry.getValue()).element();
                  hakiNature = ((IDamageSourceHandler.NuDamageValue)entry.getValue()).hakiNature();
                  typesArr = ((IDamageSourceHandler.NuDamageValue)entry.getValue()).sourceTypes();
                  break;
               }
            }
         }

         return new IDamageSourceHandler.NuDamageValue((AbilityCore)null, element, hakiNature, typesArr);
      }
   }
}
