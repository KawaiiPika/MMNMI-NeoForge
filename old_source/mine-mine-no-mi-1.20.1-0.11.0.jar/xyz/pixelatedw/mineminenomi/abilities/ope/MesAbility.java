package xyz.pixelatedw.mineminenomi.abilities.ope;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.SoulboundItemHelper;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class MesAbility extends PunchAbility {
   private static final float COOLDOWN = 600.0F;
   public static final RegistryObject<AbilityCore<MesAbility>> INSTANCE = ModRegistry.registerAbility("mes", "MES", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Removes the heart of the user's target which they can then damage to hurt the opponent", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, MesAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(600.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });

   public MesAbility(AbilityCore<MesAbility> core) {
      super(core);
      this.continuousComponent.addTickEvent(100, this::onContinuityTick);
      this.addCanUseCheck(RoomAbility::hasRoomActive);
      this.addContinueUseCheck(RoomAbility::hasRoomActive);
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_ && RoomAbility.hasRoomActive(entity, this).isFail()) {
         this.continuousComponent.stopContinuity(entity);
      }

   }

   public float getPunchCooldown() {
      return 600.0F;
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      IDevilFruit targetFruitProps = (IDevilFruit)DevilFruitCapability.get(target).orElse((Object)null);
      IEntityStats targetProps = (IEntityStats)EntityStatsCapability.get(target).orElse((Object)null);
      if (targetProps != null && targetFruitProps != null) {
         if (target instanceof Player) {
            Player playerTarget = (Player)target;
            if (targetFruitProps.hasDevilFruit(ModFruits.WARA_WARA_NO_MI)) {
               for(int i = 0; i < playerTarget.m_150109_().f_35974_.size(); ++i) {
                  if (((ItemStack)playerTarget.m_150109_().f_35974_.get(i)).m_41720_() == ModItems.STRAW_DOLL.get()) {
                     this.continuousComponent.stopContinuity(entity);
                     return true;
                  }
               }
            }
         }

         boolean targetNoHeart = targetFruitProps.hasDevilFruit(ModFruits.YOMI_YOMI_NO_MI) || target.m_6336_() == MobType.f_21641_;
         if (targetProps.hasHeart() && !targetNoHeart) {
            ItemStack heart = new ItemStack((ItemLike)ModItems.HEART.get());
            SoulboundItemHelper.setOwner(heart, target);
            heart.m_41714_(Component.m_237113_(target.m_5446_().getString() + "'s Heart"));
            if (entity instanceof Player) {
               Player player = (Player)entity;
               player.m_150109_().m_36054_(heart);
            }

            targetProps.setHeart(false);
         }

         return true;
      } else {
         return false;
      }
   }

   public int getUseLimit() {
      return 1;
   }
}
