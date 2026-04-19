package xyz.pixelatedw.mineminenomi.datagen.tags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class ModEntityTagsProvider extends EntityTypeTagsProvider {
   public ModEntityTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupFuture, ExistingFileHelper existingFileHelper) {
      super(output, lookupFuture, "mineminenomi", existingFileHelper);
   }

   protected void m_6577_(HolderLookup.Provider provider) {
      this.m_206424_(ModTags.Entities.KAIROSEKI).m_255179_(new EntityType[]{(EntityType)ModEntities.KAIROSEKI_NET.get(), (EntityType)ModProjectiles.KAIROSEKI_BULLET.get()});
      this.m_206424_(ModTags.Entities.MAGNETIC).m_255179_(new EntityType[]{EntityType.f_20460_, (EntityType)ModMobs.PACIFISTA.get(), (EntityType)ModProjectiles.CANNON_BALL.get(), (EntityType)ModProjectiles.NORMAL_BULLET.get()});
   }
}
