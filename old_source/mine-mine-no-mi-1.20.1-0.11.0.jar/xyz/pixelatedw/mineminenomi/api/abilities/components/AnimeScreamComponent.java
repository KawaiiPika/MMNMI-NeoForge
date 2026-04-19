package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeHooks;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class AnimeScreamComponent extends AbilityComponent<IAbility> {
   private static final Ability.IOnUseEvent SIMPLE_SCREAM = (e, a) -> a.getComponent((AbilityComponentKey)ModAbilityComponents.ANIME_SCREAM.get()).ifPresent((comp) -> comp.scream(e));
   private static final TargetingConditions SCREAM_TARGETTING = TargetingConditions.m_148352_().m_26883_((double)100.0F);

   public AnimeScreamComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.ANIME_SCREAM.get(), ability);
   }

   public void postInit(IAbility ability) {
      if (ability.hasComponent((AbilityComponentKey)ModAbilityComponents.CHARGE.get())) {
         ability.getComponent((AbilityComponentKey)ModAbilityComponents.CHARGE.get()).ifPresent((comp) -> {
            String[] split = this.getAbility().getDisplayName().getString().split(" ", 0);
            comp.addStartEvent((e, a) -> this.scream(e, split[0] + (split.length > 1 ? "..." : "")));
            if (split.length > 1) {
               comp.addEndEvent((e, a) -> this.scream(e, "..." + split[1]));
            }

            if (ability instanceof Ability) {
               ((Ability)ability).removeUseEvent(SIMPLE_SCREAM);
            }

         });
      } else if (ability.hasComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get())) {
         ability.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).ifPresent((comp) -> {
            comp.addStartEvent((e, a) -> this.scream(e));
            if (ability instanceof Ability) {
               ((Ability)ability).removeUseEvent(SIMPLE_SCREAM);
            }

         });
      } else if (ability instanceof Ability && !((Ability)ability).hasUseEvent(SIMPLE_SCREAM)) {
         ((Ability)ability).addUseEvent(SIMPLE_SCREAM);
      }

   }

   public void scream(LivingEntity entity) {
      this.scream(entity, this.getAbility().getDisplayName().getString());
   }

   public void scream(LivingEntity entity, String message) {
      this.ensureIsRegistered();
      Level level = entity.m_9236_();
      if (level != null) {
         if (!level.f_46443_ && ServerConfig.isAnimeScreamingEnabled()) {
            Level world = entity.m_20193_();
            ChatType.Bound chatBound = ChatType.m_240980_(ChatType.f_240674_, entity).m_241018_(entity.m_5446_());
            Component messageComponent = Component.m_237110_("chat.type.text", new Object[]{entity.m_5446_(), ForgeHooks.newChatWithLinks(message.toUpperCase())}).m_6270_(Style.f_131099_.m_131155_(true));
            AABB aabb = entity.m_20191_().m_82377_((double)100.0F, (double)50.0F, (double)100.0F);
            OutgoingChatMessage chatMessage = new OutgoingChatMessage.Disguised(messageComponent);

            for(Player otherPlayer : world.m_45955_(SCREAM_TARGETTING, entity, aabb)) {
               if (otherPlayer instanceof ServerPlayer) {
                  ServerPlayer player = (ServerPlayer)otherPlayer;
                  player.m_245069_(chatMessage, false, chatBound);
               }
            }
         }

      }
   }
}
