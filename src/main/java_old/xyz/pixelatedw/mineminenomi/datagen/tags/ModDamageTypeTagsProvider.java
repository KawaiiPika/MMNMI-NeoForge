package xyz.pixelatedw.mineminenomi.datagen.tags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraftforge.common.data.ExistingFileHelper;
import xyz.pixelatedw.mineminenomi.init.ModDamageTypes;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class ModDamageTypeTagsProvider extends DamageTypeTagsProvider {
   public ModDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, ExistingFileHelper existingFileHelper) {
      super(output, future, "mineminenomi", existingFileHelper);
   }

   protected void m_6577_(HolderLookup.Provider provider) {
      this.m_206424_(ModTags.DamageTypes.UNAVOIDABLE).m_211101_(new ResourceKey[]{ModDamageTypes.BLEED, ModDamageTypes.HEART_ATTACK, ModDamageTypes.DEVILS_CURSE, ModDamageTypes.SOULBOUND, ModDamageTypes.OUT_OF_BODY, ModDamageTypes.SUN_INCINERATION, ModDamageTypes.STORED_DAMAGE, DamageTypes.f_268722_});
      this.m_206424_(DamageTypeTags.f_268490_).m_206428_(ModTags.DamageTypes.UNAVOIDABLE).m_211101_(new ResourceKey[]{ModDamageTypes.FROSTBITES, ModDamageTypes.FROZEN, ModDamageTypes.POISON, ModDamageTypes.GRAVITY});
      this.m_206424_(DamageTypeTags.f_276146_).m_206428_(DamageTypeTags.f_268490_);
      this.m_206424_(DamageTypeTags.f_268437_).m_206428_(ModTags.DamageTypes.UNAVOIDABLE).m_255204_(ModDamageTypes.GRAVITY);
      this.m_206424_(DamageTypeTags.f_268630_).m_206428_(ModTags.DamageTypes.UNAVOIDABLE).m_211101_(new ResourceKey[]{ModDamageTypes.FROSTBITES, ModDamageTypes.FROZEN, ModDamageTypes.POISON, ModDamageTypes.GRAVITY});
      this.m_206424_(DamageTypeTags.f_268413_).m_206428_(ModTags.DamageTypes.UNAVOIDABLE).m_211101_(new ResourceKey[]{ModDamageTypes.FROSTBITES, ModDamageTypes.FROZEN, ModDamageTypes.POISON, ModDamageTypes.GRAVITY});
      this.m_206424_(ModTags.DamageTypes.BYPASSES_LOGIA).m_206428_(ModTags.DamageTypes.UNAVOIDABLE).m_211101_(new ResourceKey[]{ModDamageTypes.FROSTBITES, ModDamageTypes.FROZEN, ModDamageTypes.POISON, ModDamageTypes.GRAVITY});
      this.m_206424_(DamageTypeTags.f_268419_).m_211101_(new ResourceKey[]{ModDamageTypes.FROSTBITES, ModDamageTypes.FROZEN});
      this.m_206424_(DamageTypeTags.f_268745_).m_255204_(ModDamageTypes.SUN_INCINERATION);
      this.m_206424_(DamageTypeTags.f_268524_).m_255204_(ModDamageTypes.PROJECTILE);
   }
}
