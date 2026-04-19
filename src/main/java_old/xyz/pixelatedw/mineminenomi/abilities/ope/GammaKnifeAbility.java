package xyz.pixelatedw.mineminenomi.abilities.ope;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ItemSpawnComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class GammaKnifeAbility extends PunchAbility {
   private static final float COOLDOWN = 500.0F;
   private static final float DAMAGE = 70.0F;
   public static final RegistryObject<AbilityCore<GammaKnifeAbility>> INSTANCE = ModRegistry.registerAbility("gamma_knife", "Gamma Knife", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates a blade of gamma radiation which massively damages the opponent's organs", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GammaKnifeAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(500.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.SLASH, SourceType.INTERNAL).build("mineminenomi");
   });
   private final ItemSpawnComponent itemSpawnComponent = new ItemSpawnComponent(this);

   public GammaKnifeAbility(AbilityCore<GammaKnifeAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.itemSpawnComponent});
      this.continuousComponent.addStartEvent(100, this::onContinuityStart).addTickEvent(100, this::onContinuityTick).addEndEvent(100, this::onContinuityEnd);
      this.hitTriggerComponent.addTryHitEvent(90, this::tryHitEvent);
      this.addCanUseCheck(RoomAbility::hasRoomActive);
      this.addCanUseCheck(AbilityUseConditions::requiresEmptyHand);
      this.addContinueUseCheck(RoomAbility::hasRoomActive);
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      this.itemSpawnComponent.spawnItem(entity, new ItemStack((ItemLike)ModWeapons.GAMMA_KNIFE.get()), EquipmentSlot.MAINHAND, true);
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (RoomAbility.hasRoomActive(entity, this).isFail()) {
            this.continuousComponent.stopContinuity(entity);
         }

      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      this.itemSpawnComponent.despawnItems(entity);
   }

   private HitTriggerComponent.HitResult tryHitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      if (this.continuousComponent.isContinuous() && entity.m_21205_().m_41720_().equals(ModWeapons.GAMMA_KNIFE.get())) {
         IDamageSourceHandler handler = IDamageSourceHandler.getHandler(source);
         handler.bypassLogia();
         handler.addAbilityPiercing(1.0F, this.getCore());
         return HitTriggerComponent.HitResult.HIT;
      } else {
         return HitTriggerComponent.HitResult.FAIL;
      }
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      return false;
   }

   public float getPunchDamage() {
      return 70.0F;
   }

   public int getUseLimit() {
      return 1;
   }

   public boolean isParallel() {
      return true;
   }

   public float getPunchCooldown() {
      return 500.0F;
   }
}
