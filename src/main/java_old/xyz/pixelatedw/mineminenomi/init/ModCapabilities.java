package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.animation.AnimationCapability;
import xyz.pixelatedw.mineminenomi.data.entity.cameralock.CameraLockCapability;
import xyz.pixelatedw.mineminenomi.data.entity.carry.CarryCapability;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.ChallengeCapability;
import xyz.pixelatedw.mineminenomi.data.entity.combat.CombatCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.gcd.GCDCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.hisolookout.HisoLookoutCapability;
import xyz.pixelatedw.mineminenomi.data.entity.kairosekicoating.KairosekiCoatingCapability;
import xyz.pixelatedw.mineminenomi.data.entity.nbtgoals.NBTGoalsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.projectileextra.ProjectileExtrasCapability;
import xyz.pixelatedw.mineminenomi.data.entity.quest.QuestCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.handlers.entity.CommandReceiverHandler;
import xyz.pixelatedw.mineminenomi.integrations.curios.CuriosIntegration;

@EventBusSubscriber(
   modid = "mineminenomi"
)
public class ModCapabilities {
   @SubscribeEvent
   public static void attachItemCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
      if (ModMain.hasCuriosInstalled()) {
         CuriosIntegration.setupCurioCapabilities(event);
      }

   }

   @SubscribeEvent
   public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
      if (event.getObject() != null) {
         Object var2 = event.getObject();
         if (var2 instanceof Player) {
            Player player = (Player)var2;
            event.addCapability(ResourceLocation.fromNamespaceAndPath("mineminenomi", "quest"), new QuestCapability(player));
            event.addCapability(ResourceLocation.fromNamespaceAndPath("mineminenomi", "carry"), new CarryCapability(player));
            event.addCapability(ResourceLocation.fromNamespaceAndPath("mineminenomi", "challenge"), new ChallengeCapability(player));
            event.addCapability(ResourceLocation.fromNamespaceAndPath("mineminenomi", "camera_lock"), new CameraLockCapability());
         }

         var2 = event.getObject();
         if (var2 instanceof LivingEntity) {
            LivingEntity living = (LivingEntity)var2;
            event.addCapability(ResourceLocation.fromNamespaceAndPath("mineminenomi", "ability"), new AbilityCapability(living));
            event.addCapability(ResourceLocation.fromNamespaceAndPath("mineminenomi", "haki"), new HakiCapability(living));
            event.addCapability(ResourceLocation.fromNamespaceAndPath("mineminenomi", "stats"), new EntityStatsCapability(living));
            event.addCapability(ResourceLocation.fromNamespaceAndPath("mineminenomi", "devil_fruit"), new DevilFruitCapability(living));
            event.addCapability(ResourceLocation.fromNamespaceAndPath("mineminenomi", "gcd"), new GCDCapability(living));
            event.addCapability(ResourceLocation.fromNamespaceAndPath("mineminenomi", "animation"), new AnimationCapability(living));
            event.addCapability(ResourceLocation.fromNamespaceAndPath("mineminenomi", "combat"), new CombatCapability(living));
         }

         var2 = event.getObject();
         if (var2 instanceof Mob) {
            Mob mob = (Mob)var2;
            CommandReceiverHandler.registerCommandReceiver(event, mob);
            event.addCapability(ResourceLocation.fromNamespaceAndPath("mineminenomi", "nbt_goals"), new NBTGoalsCapability());
         }

         if (event.getObject() instanceof Animal) {
            event.addCapability(ResourceLocation.fromNamespaceAndPath("mineminenomi", "hiso_lookout"), new HisoLookoutCapability());
         }

         var2 = event.getObject();
         if (var2 instanceof Projectile) {
            Projectile proj = (Projectile)var2;
            event.addCapability(ResourceLocation.fromNamespaceAndPath("mineminenomi", "projectile_extras"), new ProjectileExtrasCapability(proj));
         }

         var2 = event.getObject();
         if (var2 instanceof Boat) {
            Boat boat = (Boat)var2;
            event.addCapability(ResourceLocation.fromNamespaceAndPath("mineminenomi", "kairoseki_coating"), new KairosekiCoatingCapability(boat));
         }

      }
   }
}
