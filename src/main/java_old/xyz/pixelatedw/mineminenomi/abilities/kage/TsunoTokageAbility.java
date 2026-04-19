package xyz.pixelatedw.mineminenomi.abilities.kage;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.kage.TsunoTokagePillarEntity;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class TsunoTokageAbility extends Ability {
   private static final float COOLDOWN = 200.0F;
   public static final RegistryObject<AbilityCore<TsunoTokageAbility>> INSTANCE = ModRegistry.registerAbility("tsuno_tokage", "Tsuno Tokage", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user creates a lizard-like shadow under his opponent, which pierces them from below", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, TsunoTokageAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).build("mineminenomi");
   });
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);

   public TsunoTokageAbility(AbilityCore<TsunoTokageAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.projectileComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      HitResult mop = WyHelper.rayTraceBlocksAndEntities(entity, (double)64.0F);
      if (mop.m_6662_() != Type.MISS) {
         double i = mop.m_82450_().f_82479_;
         double j = mop.m_82450_().f_82480_;
         double k = mop.m_82450_().f_82481_;
         TsunoTokagePillarEntity pillar = (TsunoTokagePillarEntity)this.projectileComponent.getNewProjectile(entity);
         pillar.m_7678_(i, j - (double)6.0F, k, 0.0F, 0.0F);
         pillar.m_6686_((double)0.0F, 0.7, (double)0.0F, 1.4F, 0.0F);
         entity.m_9236_().m_7967_(pillar);
         this.cooldownComponent.startCooldown(entity, 200.0F);
      }
   }

   private TsunoTokagePillarEntity createProjectile(LivingEntity entity) {
      TsunoTokagePillarEntity pillar = new TsunoTokagePillarEntity(entity.m_9236_(), entity, this);
      return pillar;
   }
}
