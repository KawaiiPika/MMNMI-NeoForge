package xyz.pixelatedw.mineminenomi.items;

import com.google.common.base.Strings;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.ChallengeCapability;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.IChallengeData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nChallenges;

public class ChallengePosterItem extends Item {
   public ChallengePosterItem() {
      super((new Item.Properties()).m_41487_(1));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemStack = player.m_21120_(hand);
      if (!world.f_46443_) {
         IChallengeData props = (IChallengeData)ChallengeCapability.get(player).orElse((Object)null);
         if (props == null) {
            return InteractionResultHolder.m_19100_(itemStack);
         }

         String challengeStrId = itemStack.m_41784_().m_128461_("challengeId");
         if (!Strings.isNullOrEmpty(challengeStrId)) {
            ResourceLocation challengeId = ResourceLocation.parse(challengeStrId);
            ChallengeCore<?> challenge = (ChallengeCore)((IForgeRegistry)WyRegistry.CHALLENGES.get()).getValue(challengeId);
            if (props.hasChallenge(challenge)) {
               player.m_213846_(ModI18nChallenges.MESSAGE_ALREADY_UNLOCKED);
            } else {
               props.addChallenge(challenge);
               player.m_213846_(Component.m_237110_(ModI18nChallenges.MESSAGE_UNLOCKED, new Object[]{challenge.getLocalizedTitle().getString()}));
               player.m_150109_().m_36057_(itemStack);
            }
         }
      }

      return InteractionResultHolder.m_19096_(itemStack);
   }
}
