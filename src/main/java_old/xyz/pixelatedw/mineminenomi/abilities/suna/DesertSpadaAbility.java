package xyz.pixelatedw.mineminenomi.abilities.suna;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.suna.DesertSpadaProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class DesertSpadaAbility extends Ability {
   private static final float COOLDOWN_BONUS = 0.8F;
   private static final float DAMAGE_BONUS = 1.5F;
   private static final int COOLDOWN = 160;
   public static final RegistryObject<AbilityCore<DesertSpadaAbility>> INSTANCE = ModRegistry.registerAbility("desert_spada", "Desert Spada", (id, name) -> {
      Pair[] var10002 = new Pair[]{ImmutablePair.of("The user forms their hand into a blade and stabs it into the ground, creating a sand blade that destroys anything on its path.", (Object)null), null};
      Object[] var10006 = new Object[]{"§a" + Math.round(19.999998F) + "%§r", null};
      float var10009 = Math.abs(-0.5F);
      var10006[1] = "§a" + Math.round(var10009 * 100.0F) + "%§r";
      var10002[1] = ImmutablePair.of("While in a desert the cooldown of this ability is reduced by %s and the damage is increased by %s.", var10006);
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, var10002);
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, DesertSpadaAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(160.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.SLASH).build("mineminenomi");
   });
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);

   public DesertSpadaAbility(AbilityCore<DesertSpadaAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.projectileComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresOnGround);
      this.addCanUseCheck(AbilityUseConditions::requiresDryUser);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.DESERT_SPADA_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
      boolean fruitBoosted = SunaHelper.isFruitBoosted(entity);
      this.projectileComponent.shoot(entity, 3.0F, 1.0F);
      this.cooldownComponent.getBonusManager().removeBonus(SunaHelper.DESERT_COOLDOWN_BONUS);
      if (fruitBoosted) {
         this.cooldownComponent.getBonusManager().addBonus(SunaHelper.DESERT_COOLDOWN_BONUS, "Desert Cooldown Bonus", BonusOperation.MUL, 0.8F);
      }

      this.cooldownComponent.startCooldown(entity, 160.0F);
   }

   private DesertSpadaProjectile createProjectile(LivingEntity entity) {
      boolean fruitBoosted = SunaHelper.isFruitBoosted(entity);
      this.projectileComponent.getDamageBonusManager().removeBonus(SunaHelper.DESERT_DAMAGE_BONUS);
      if (fruitBoosted) {
         this.projectileComponent.getDamageBonusManager().addBonus(SunaHelper.DESERT_DAMAGE_BONUS, "Desert Damage Bonus", BonusOperation.MUL, 1.5F);
      }

      DesertSpadaProjectile proj = new DesertSpadaProjectile(entity.m_9236_(), entity, this);
      proj.setMaxLife(fruitBoosted ? 30 : 20);
      return proj;
   }
}
