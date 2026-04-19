package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.ComponentNotRegisteredException;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class ExplosionComponent extends AbilityComponent<IAbility> {
   private static final int DEFAULT_LIMIT = 4096;
   private int blocksAffectedLimit;
   private int blocksAffected;
   private boolean hasBlockDestructionLimit;
   private boolean removeOnBlockLimit;
   private Set<Entity> hits;
   private final PriorityEventPool<ICheckLimitEvent> checkLimitEvents;
   private Entity explosionSource;

   public ExplosionComponent(IAbility ability) {
      this(ability, 4096);
   }

   public ExplosionComponent(IAbility ability, int blocksAffectedLimit) {
      super((AbilityComponentKey)ModAbilityComponents.EXPLOSION.get(), ability);
      this.blocksAffected = 0;
      this.hasBlockDestructionLimit = true;
      this.removeOnBlockLimit = true;
      this.hits = new HashSet();
      this.checkLimitEvents = new PriorityEventPool<ICheckLimitEvent>();
      this.blocksAffectedLimit = blocksAffectedLimit;
   }

   public ExplosionComponent addLimitCheckEvent(int priority, ICheckLimitEvent event) {
      this.checkLimitEvents.addEvent(priority, event);
      return this;
   }

   public static void createExplosion(NuProjectileEntity proj, Consumer<? super ExplosionComponent> consumer) {
      if (proj != null) {
         createExplosion((IAbility)proj.getParent().orElse((Object)null), consumer);
      }
   }

   public static void createExplosion(@Nullable IAbility abl, Consumer<? super ExplosionComponent> consumer) {
      if (abl != null) {
         try {
            boolean hasComponent = abl.hasComponent((AbilityComponentKey)ModAbilityComponents.EXPLOSION.get());
            if (!hasComponent) {
               throw new ComponentNotRegisteredException((AbilityComponentKey)ModAbilityComponents.EXPLOSION.get());
            }
         } catch (Exception e) {
            e.printStackTrace();
            return;
         }

         consumer.accept(abl.getComponent((AbilityComponentKey)ModAbilityComponents.EXPLOSION.get()).get());
      }
   }

   protected void doTick(LivingEntity entity) {
      if (!entity.m_9236_().f_46443_) {
         if (this.explosionSource != null && this.explosionSource.m_6084_()) {
            if (this.hasReachedLimit()) {
               boolean canRemove = !this.checkLimitEvents.dispatchCancelable((event) -> event.checkLimit(entity, this.getAbility()));
               if (canRemove) {
                  this.tryRemoveSource();
               }
            }

         } else {
            if (this.blocksAffected > 0 || !this.hits.isEmpty()) {
               this.tryRemoveSource();
            }

         }
      }
   }

   public AbilityExplosion createExplosion(Entity exploder, double posX, double posY, double posZ, float size) {
      return this.createExplosion(exploder, (LivingEntity)null, posX, posY, posZ, size);
   }

   public AbilityExplosion createExplosion(Entity exploder, @Nullable LivingEntity spawner, double posX, double posY, double posZ, float size) {
      this.ensureIsRegistered();
      Level world = exploder.m_9236_();
      this.hits.clear();
      this.explosionSource = exploder;
      AbilityExplosion explosion = new AbilityExplosion(exploder, this.getAbility(), posX, posY, posZ, size);
      explosion.setSpawner(spawner);
      explosion.onBlockDestroyedEvent = (hitPos) -> {
         int destroySpeed = Math.max((int)world.m_8055_(hitPos).getExplosionResistance(world, hitPos, explosion), 1);
         this.increaseBlocksAffected(destroySpeed);
      };
      explosion.onEntityHitEvent = (hit) -> {
         if (spawner != null && hit.equals(spawner)) {
            return false;
         } else if (hit.equals(exploder)) {
            return false;
         } else {
            return this.canHit(hit);
         }
      };
      return explosion;
   }

   public boolean canHit(Entity target) {
      this.ensureIsRegistered();
      if (this.hits.contains(target)) {
         return false;
      } else {
         this.hits.add(target);
         return true;
      }
   }

   public void increaseBlocksAffected(int increaseAmount) {
      this.blocksAffected = Mth.m_14045_(this.blocksAffected + increaseAmount, 0, this.blocksAffectedLimit);
   }

   public int getBlocksAffected() {
      return this.blocksAffected;
   }

   public void setBlocksAffectedLimit(int limit) {
      this.blocksAffectedLimit = limit;
   }

   public int getBlocksAffectedLimit() {
      return this.blocksAffectedLimit;
   }

   public boolean hasReachedLimit() {
      if (this.blocksAffectedLimit == 0) {
         this.blocksAffectedLimit = 4096;
      }

      return this.hasBlockDestructionLimit && this.blocksAffected >= this.blocksAffectedLimit;
   }

   public void setKillOnBlockLimit(boolean flag) {
      this.removeOnBlockLimit = flag;
   }

   public void tryRemoveSource() {
      if (this.explosionSource != null && !(this.explosionSource instanceof Player) && this.removeOnBlockLimit) {
         this.explosionSource.m_142687_(RemovalReason.DISCARDED);
      }

      this.blocksAffected = 0;
      this.hits.clear();
   }

   public void appendDescription(Player player, List<Component> desc, float range, float damage) {
      desc.add(ModI18nAbilities.DESCRIPTION_STAT_NAME_EXPLOSION);
      AbilityStat.Builder rangeBuilder = (new AbilityStat.Builder(ModI18nAbilities.DESCRIPTION_STAT_NAME_RANGE, range)).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_BLOCKS);
      desc.add(rangeBuilder.buildComponent(2));
      AbilityStat.Builder damageBuilder = new AbilityStat.Builder(ModI18nAbilities.DESCRIPTION_STAT_NAME_DAMAGE, damage);
      desc.add(damageBuilder.buildComponent(2));
   }

   public @Nullable CompoundTag save() {
      CompoundTag nbt = super.save();
      nbt.m_128405_("blocksAffected", this.blocksAffected);
      nbt.m_128405_("blocksAffectedLimit", this.blocksAffectedLimit);
      return nbt;
   }

   public void load(CompoundTag nbt) {
      super.load(nbt);
      this.blocksAffected = nbt.m_128451_("blocksAffected");
      this.blocksAffectedLimit = nbt.m_128451_("blocksAffectedLimit");
   }

   @FunctionalInterface
   public interface ICheckLimitEvent {
      boolean checkLimit(LivingEntity var1, IAbility var2);
   }
}
