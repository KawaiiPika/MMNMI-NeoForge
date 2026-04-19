package xyz.pixelatedw.mineminenomi.abilities.magu;

import java.awt.Color;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.magu.DaiFunkaProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class DaiFunkaAbility extends Ability {
   private static final float CHARGE_TIME = 20.0F;
   private static final float COOLDOWN = 500.0F;
   public static final RegistryObject<AbilityCore<DaiFunkaAbility>> INSTANCE = ModRegistry.registerAbility("dai_funka", "Dai Funka", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Transforms the user's fist into pure magma before expanding and throwing it forward", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, DaiFunkaAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(500.0F), ChargeComponent.getTooltip(20.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.MAGMA).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::onChargeStart).addEndEvent(this::onChargeEnd);
   private final SkinOverlayComponent skinOverlayComponent;
   private final ProjectileComponent projectileComponent;
   private static final AbilityOverlay OVERLAY;

   public DaiFunkaAbility(AbilityCore<DaiFunkaAbility> core) {
      super(core);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.projectileComponent = new ProjectileComponent(this, this::createProjectile);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.skinOverlayComponent, this.projectileComponent});
      this.addUseEvent(this::onUse);
   }

   private void onUse(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 20.0F);
   }

   private void onChargeStart(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.showAll(entity);
   }

   private void onChargeEnd(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity, 3.0F, 1.0F);
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.MAGU_SFX.get(), SoundSource.PLAYERS, 3.0F, 1.0F);
      this.skinOverlayComponent.hideAll(entity);
      super.cooldownComponent.startCooldown(entity, 500.0F);
   }

   private DaiFunkaProjectile createProjectile(LivingEntity entity) {
      return new DaiFunkaProjectile(entity.m_9236_(), entity, this);
   }

   static {
      OVERLAY = (new AbilityOverlay.Builder()).setOverlayPart(AbilityOverlay.OverlayPart.LIMB).setTexture(ModResources.DOKU_COATING).setColor(new Color(160, 0, 0)).build();
   }
}
