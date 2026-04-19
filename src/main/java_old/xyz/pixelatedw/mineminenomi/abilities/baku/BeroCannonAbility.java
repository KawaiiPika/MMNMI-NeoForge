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
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.baku.BeroCannonProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class BeroCannonAbility extends Ability {
   private static final float COOLDOWN = 40.0F;
   public static final RegistryObject<AbilityCore<BeroCannonAbility>> INSTANCE = ModRegistry.registerAbility("bero_cannon", "Bero Cannon", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Transforms the user's tongue into a cannon and fires a random block from their inventory.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BeroCannonAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(40.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.BLUNT).build("mineminenomi");
   });
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);

   public BeroCannonAbility(AbilityCore<BeroCannonAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.projectileComponent});
      this.addCanUseCheck(this::canStartAbility);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      List<ItemStack> projectiles = this.getBlocksInInventory(entity);
      if (!projectiles.isEmpty()) {
         this.projectileComponent.shoot(entity);
         ItemStack stack = (ItemStack)projectiles.stream().findFirst().orElse((Object)null);
         if (stack != null && stack.m_41613_() > 1) {
            stack.m_41774_(1);
         }
      }

      this.cooldownComponent.startCooldown(entity, 40.0F);
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

   private Result canStartAbility(LivingEntity entity, IAbility ability) {
      return this.getBlocksInInventory(entity).isEmpty() ? Result.fail(ModI18nAbilities.MESSAGE_NOT_ENOUGH_BLOCKS) : Result.success();
   }
}
