package xyz.pixelatedw.mineminenomi.abilities.doa;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class DoorDoorAbility extends Ability {
   private static final int COOLDOWN = 100;
   public static final RegistryObject<AbilityCore<DoorDoorAbility>> INSTANCE = ModRegistry.registerAbility("door_door", "Door Door", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("By making a door, the user transports to the other side of any surface.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, DoorDoorAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F)).build("mineminenomi");
   });

   public DoorDoorAbility(AbilityCore<DoorDoorAbility> core) {
      super(core);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      BlockHitResult hitBlock = WyHelper.rayTraceBlocks(entity, (double)16.0F);
      if (!(Math.sqrt(entity.m_20238_(hitBlock.m_82450_())) > (double)2.5F)) {
         Vec3 look = entity.m_20154_().m_82541_();
         Vec3 vec = entity.m_20182_();
         BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
         pos.m_122169_(vec.f_82479_, vec.f_82480_, vec.f_82481_);
         boolean firstSolid = false;
         int airBlocks = 0;
         Direction dir = entity.m_6350_().m_122424_();

         for(int i = 0; i < 40; ++i) {
            BlockState state = entity.m_9236_().m_8055_(pos);
            if (state.m_60734_() == Blocks.f_50016_ && (firstSolid || airBlocks > 1)) {
               entity.m_6021_(vec.m_7096_(), vec.m_7098_() + (double)1.0F, vec.m_7094_());
               break;
            }

            vec = vec.m_82520_(look.f_82479_, look.f_82480_, look.f_82481_);
            pos.m_122178_(Mth.m_14107_(vec.f_82479_), Mth.m_14165_(vec.f_82480_), Mth.m_14107_(vec.f_82481_));
            state = entity.m_9236_().m_8055_(pos);
            if (state.m_60659_(entity.m_9236_(), pos, dir, SupportType.FULL)) {
               firstSolid = true;
            }

            if (state.m_60734_() == Blocks.f_50016_) {
               ++airBlocks;
            }
         }

         entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.DOA_IN_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
         this.cooldownComponent.startCooldown(entity, 100.0F);
      }
   }
}
