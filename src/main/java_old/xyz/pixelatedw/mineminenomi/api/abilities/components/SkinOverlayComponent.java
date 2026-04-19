package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSetOverlayLayersPacket;

public class SkinOverlayComponent extends AbilityComponent<IAbility> {
   private Map<Integer, AbilityOverlay> overlays = new HashMap();
   private Set<Integer> shownOverlays = new HashSet();

   public SkinOverlayComponent(IAbility ability, AbilityOverlay mainOverlay, AbilityOverlay... abilityOverlays) {
      super((AbilityComponentKey)ModAbilityComponents.SKIN_OVERLAY.get(), ability);
      this.addOverlay(mainOverlay);

      for(AbilityOverlay overlay : abilityOverlays) {
         this.addOverlay(overlay);
      }

   }

   public void addOverlay(AbilityOverlay overlay) {
      this.overlays.put(overlay.hashCode(), overlay);
   }

   public void removeOverlay(AbilityOverlay overlay) {
      this.overlays.remove(overlay.hashCode());
   }

   public Optional<AbilityOverlay> getShownOverlay(AbilityOverlay.OverlayPart... parts) {
      if (parts.length <= 0) {
         return Optional.empty();
      } else {
         List<AbilityOverlay.OverlayPart> overlays = new ArrayList();
         Collections.addAll(overlays, parts);
         return this.shownOverlays.stream().map((hash) -> (AbilityOverlay)this.overlays.get(hash)).filter(Objects::nonNull).filter((overlay) -> overlays.contains(overlay.getOverlayPart())).findFirst();
      }
   }

   public boolean hasAnyShownOverlay(AbilityOverlay.OverlayPart... parts) {
      if (parts.length <= 0) {
         return false;
      } else {
         List<AbilityOverlay.OverlayPart> overlays = new ArrayList();
         Collections.addAll(overlays, parts);
         return this.shownOverlays.stream().map((hash) -> (AbilityOverlay)this.overlays.get(hash)).filter(Objects::nonNull).filter((overlay) -> overlays.contains(overlay.getOverlayPart())).count() > 0L;
      }
   }

   public void showAll(LivingEntity entity) {
      this.ensureIsRegistered();
      this.shownOverlays.addAll(this.overlays.keySet());
      this.updateShownOverlays(entity);
   }

   public void show(LivingEntity entity, AbilityOverlay overlay) {
      this.ensureIsRegistered();
      this.shownOverlays.add(overlay.hashCode());
      this.updateShownOverlays(entity);
   }

   public void hideAll(LivingEntity entity) {
      this.shownOverlays.clear();
      this.updateShownOverlays(entity);
   }

   public void hide(LivingEntity entity, AbilityOverlay overlay) {
      this.shownOverlays.remove(overlay.hashCode());
      this.updateShownOverlays(entity);
   }

   public void setShownOverlays(Set<Integer> overlays) {
      this.shownOverlays = overlays;
   }

   private void updateShownOverlays(LivingEntity entity) {
      if (!entity.m_9236_().f_46443_) {
         ModNetwork.sendToAllTrackingAndSelf(new SSetOverlayLayersPacket(entity, this.getAbility(), this.shownOverlays), entity);
      }

   }
}
