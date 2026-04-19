package xyz.pixelatedw.mineminenomi.abilities.baku;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.baku.BeroCannonProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BakuTsuihoAbility extends Ability {
   private static final float CHARGE_TIME = 60.0F;
   private static final float COOLDOWN = 160.0F;
   public static final RegistryObject<AbilityCore<BakuTsuihoAbility>> INSTANCE = ModRegistry.registerAbility("baku_tsuiho", "Baku Tsuiho", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user to charge multiple blocks from their inventory in their mouth and shoot them all at the same time.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BakuTsuihoAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(160.0F), ChargeComponent.getTooltip(60.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.BLUNT).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(100, this::startContinuityEvent);
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addEndEvent(100, this::endChargeEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addTriggerEvent(100, this::triggerRepeaterEvent).addStopEvent(100, this::stopRepeaterEvent);
   private int ammo;

   public BakuTsuihoAbility(AbilityCore<BakuTsuihoAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.chargeComponent, this.projectileComponent, this.repeaterComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         this.repeaterComponent.stop(entity);
      } else {
         this.chargeComponent.startCharging(entity, 60.0F);
      }
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      this.ammo = 0;
      List<ItemStack> availableBlocks = this.getBlocksInInventory(entity);

      for(int idx = 0; this.ammo < 20 && idx < availableBlocks.size(); ++idx) {
         ItemStack stack = (ItemStack)availableBlocks.get(idx);
         ItemStack copy = stack.m_41777_();
         this.ammo += copy.m_41613_();
         stack.m_41774_(this.ammo);
      }

      this.continuousComponent.startContinuity(entity, -1.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.repeaterComponent.start(entity, this.ammo, 3);
   }

   private void triggerRepeaterEvent(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shootWithSpread(entity, 3.0F, 4.0F, 1);
   }

   private void stopRepeaterEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
      this.cooldownComponent.startCooldown(entity, 160.0F);
   }

   private BeroCannonProjectile createProjectile(LivingEntity entity) {
      BeroCannonProjectile proj = new BeroCannonProjectile(entity.m_9236_(), entity, this);
      return proj;
   }

   private List<ItemStack> getBlocksInInventory(LivingEntity entity) {
      List<ItemStack> projectiles = new ArrayList();
      Set<TagKey<Block>> tags = DefaultProtectionRules.CORE_FOLIAGE_ORE.getApprovedTags();

      for(ItemStack item : ItemsHelper.getInventoryItems(entity)) {
         if (item != null && !item.m_41619_()) {
            Item var7 = item.m_41720_();
            if (var7 instanceof BlockItem) {
               BlockItem blockItem = (BlockItem)var7;
               Optional<Holder<Block>> holder = ForgeRegistries.BLOCKS.getHolder(blockItem.m_40614_());
               if (holder.isPresent()) {
                  for(TagKey<Block> tag : tags) {
                     if (((Holder)holder.get()).m_203656_(tag)) {
                        projectiles.add(item);
                        break;
                     }
                  }
               }
            }
         }
      }

      return projectiles;
   }
}
