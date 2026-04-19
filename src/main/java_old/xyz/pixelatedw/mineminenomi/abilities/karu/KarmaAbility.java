package xyz.pixelatedw.mineminenomi.abilities.karu;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.text.DecimalFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdateAbilityNBTPacket;

public class KarmaAbility extends PassiveAbility {
   private static final DecimalFormat FORMAT = new DecimalFormat("#0.0");
   public static final float MAX_KARMA = 100.0F;
   public static final RegistryObject<AbilityCore<KarmaAbility>> INSTANCE = ModRegistry.registerAbility("karma", "Karma", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Converts received damage into karma.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, KarmaAbility::new)).addDescriptionLine(desc).setHidden().build("mineminenomi");
   });
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnDamageEvent(this::onDamageTaken);
   private float karma = 0.0F;
   private float prevKarma = 0.0F;

   public KarmaAbility(AbilityCore<KarmaAbility> ability) {
      super(ability);
      this.addComponents(new AbilityComponent[]{this.damageTakenComponent});
      if (super.isClientSide()) {
         GaugeComponent gaugeComponent = new GaugeComponent(this, this::renderGauge);
         super.addComponents(gaugeComponent);
      }

   }

   private float onDamageTaken(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      if (!damageSource.m_276093_(DamageTypes.f_268515_) && !damageSource.m_276093_(DamageTypes.f_268530_) && !(this.karma >= 100.0F)) {
         IDamageSourceHandler sourceHandler = IDamageSourceHandler.getHandler(damageSource);
         if (!sourceHandler.hasType(SourceType.INTERNAL)) {
            this.addKarma(entity, damage);
         }

         return damage;
      } else {
         return damage;
      }
   }

   public void addKarma(LivingEntity entity, float amount) {
      this.prevKarma = this.karma;
      this.karma = Mth.m_14036_(this.karma + amount, 0.0F, 100.0F);
      if (entity instanceof Player player) {
         ModNetwork.sendTo(new SUpdateAbilityNBTPacket(entity, this), player);
      }

   }

   public float getKarma() {
      return this.karma;
   }

   public void setPrevKarma(float prevKarma) {
      this.prevKarma = prevKarma;
   }

   public float getPrevKarma() {
      return this.prevKarma;
   }

   public void saveAdditional(CompoundTag nbt) {
      nbt.m_128350_("karma", this.karma);
   }

   public void loadAdditional(CompoundTag nbt) {
      this.karma = nbt.m_128457_("karma");
   }

   @OnlyIn(Dist.CLIENT)
   public void renderGauge(Player player, GuiGraphics graphics, int posX, int posY, KarmaAbility ability) {
      RenderSystem.enableBlend();
      Minecraft mc = Minecraft.m_91087_();
      RenderSystem.setShaderTexture(0, ModResources.WIDGETS);
      RendererHelper.drawIcon(((AbilityCore)IngaZarashiAbility.INSTANCE.get()).getIcon(), graphics.m_280168_(), (float)posX, (float)(posY - 38), 0.0F, 32.0F, 32.0F);
      String karma = FORMAT.format((double)ability.getKarma());
      RendererHelper.drawStringWithBorder(Minecraft.m_91087_().f_91062_, graphics, karma, posX + 16 - mc.f_91062_.m_92895_(karma) / 2, posY - 25, Color.WHITE.getRGB());
      RenderSystem.disableBlend();
   }
}
