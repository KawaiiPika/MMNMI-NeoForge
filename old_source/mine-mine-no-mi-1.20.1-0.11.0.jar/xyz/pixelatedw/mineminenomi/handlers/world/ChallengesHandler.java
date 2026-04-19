package xyz.pixelatedw.mineminenomi.handlers.world;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Optional;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCache;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nChallenges;
import xyz.pixelatedw.mineminenomi.renderers.ArenaSkybox;

public class ChallengesHandler {
   public static void kickPlayerFromChallengeOnLogin(ServerPlayer player, Level level) {
      ServerLevel overworld = player.m_20194_().m_129880_(Level.f_46428_);
      ChallengesWorldData worldData = ChallengesWorldData.get();
      InProgressChallenge challenge = worldData.getInProgressChallengeFor((LivingEntity)player);
      if (challenge != null) {
         challenge.despawnChallenger(player);
      } else {
         Optional<ChallengeCache> cache = worldData.getChallengerCache(player.m_20148_());
         if (cache.isPresent()) {
            ((ChallengeCache)cache.get()).restore(player);
            worldData.removeChallengerCache(player.m_20148_());
         }

         player.m_8999_(overworld, (double)overworld.m_220360_().m_123341_(), (double)overworld.m_220360_().m_123342_(), (double)overworld.m_220360_().m_123343_(), 270.0F, 0.0F);
      }

   }

   public static boolean isItemBlockedInChallenge(LivingEntity entity, ItemStack stack) {
      if (!entity.m_9236_().f_46443_ && NuWorld.isChallengeDimension(entity.m_9236_())) {
         ChallengesWorldData worldData = ChallengesWorldData.get();
         InProgressChallenge challenge = worldData.getInProgressChallengeFor(entity);
         if (challenge != null) {
            boolean isFood = stack.m_41720_().m_41472_();
            boolean isPotion = stack.m_41720_() instanceof PotionItem;
            boolean hasRestrictions = challenge.hasRestrictions() && challenge.hasActiveRestrictions() && (isFood || isPotion);
            boolean isItemBanned = stack.m_204117_(ModTags.Items.BANNED_ITEMS_CHALLANGES);
            if (hasRestrictions || isItemBanned) {
               if (!entity.m_9236_().f_46443_) {
                  entity.m_213846_(ModI18nChallenges.MESSAGE_CANNOT_USE_ITEM);
               }

               return true;
            }
         }
      }

      return false;
   }

   public static class Client {
      public static final ArenaSkybox SKYSPHERE = (new ArenaSkybox()).setTexture(true, ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/skyboxes/darkness.png")).setAnimationSpeed(110000).setDetailLevel(25);
      public static final ArenaSkybox SKYSPHERE_OVERLAY = (new ArenaSkybox()).setTexture(true, ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/skyboxes/clouds.png")).setAnimationSpeed(80000).setDetailLevel(16).setAlpha(0.5F);

      public static void renderMainSkysphere(PoseStack stack, Camera info) {
         Minecraft mc = Minecraft.m_91087_();
         Player player = mc.f_91074_;
         if (player != null) {
            if (NuWorld.isChallengeDimension(player.m_9236_())) {
               stack = new PoseStack();
               stack.m_166856_();
               stack.m_85837_(-info.m_90583_().f_82479_, (double)0.0F, -info.m_90583_().f_82481_);
               int renderDistance = (Integer)mc.f_91066_.m_231984_().m_231551_();
               int xzAreaSize = renderDistance * 25;
               SKYSPHERE.setRadius((float)xzAreaSize);
               SKYSPHERE_OVERLAY.setRadius((float)(xzAreaSize - 5));
               stack.m_85836_();
               SKYSPHERE.renderSphereInWorld(stack, info, (double)0.0F, (double)0.0F, (double)0.0F);
               SKYSPHERE_OVERLAY.renderSphereInWorld(stack, info, (double)0.0F, (double)0.0F, (double)0.0F);
               stack.m_85849_();
            }

         }
      }
   }
}
