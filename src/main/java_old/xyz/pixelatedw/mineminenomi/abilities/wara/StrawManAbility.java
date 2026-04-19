package xyz.pixelatedw.mineminenomi.abilities.wara;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.CombatHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.items.StrawDollItem;

public class StrawManAbility extends PunchAbility {
   private static final int COOLDOWN = 600;
   public static final RegistryObject<AbilityCore<StrawManAbility>> INSTANCE = ModRegistry.registerAbility("straw_man", "Straw Man", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates a doll that representing the entity you attacked, any damage will get redirected to it.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, StrawManAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(600.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST, SourceType.FRIENDLY).build("mineminenomi");
   });

   public StrawManAbility(AbilityCore<StrawManAbility> core) {
      super(core);
   }

   public float getPunchDamage() {
      return 1.0F;
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      if (entity instanceof Player player) {
         IEntityStats targetProps = (IEntityStats)EntityStatsCapability.get(target).orElse((Object)null);
         if (targetProps == null) {
            return false;
         }

         int dolls = player.m_150109_().m_18947_((Item)ModItems.STRAW_DOLL.get());
         if (dolls < 10) {
            if (CombatHelper.isTargetBlocking(player, target) || CombatHelper.isHakiBlocking(player, target)) {
               return true;
            }

            if (targetProps.hasStrawDoll()) {
               ItemStack doll = StrawDollItem.createDollStack(target);
               player.m_150109_().m_36054_(doll);
               targetProps.setStrawDoll(false);
            }
         }

         this.continuousComponent.stopContinuity(player);
      }

      return true;
   }

   public int getUseLimit() {
      return 1;
   }

   public float getPunchCooldown() {
      return 600.0F;
   }
}
