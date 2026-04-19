package xyz.pixelatedw.mineminenomi.items.armors;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.init.ModMaterials;

public class BigRedNoseItem extends Mod3DArmorItem {
   private static final Component DESCRIPTION = createDescription();

   private static Component createDescription() {
      StringBuilder sb = new StringBuilder();
      sb.append("§k");
      Random rand = new Random();
      int max = 50 + rand.nextInt(100);

      for(int i = 0; i < max; ++i) {
         sb.append("?");
         boolean split = rand.nextInt(100) == 0;
         if (split) {
            sb.append("\n");
         }
      }

      sb.append("§r");
      return Component.m_237113_(sb.toString());
   }

   public BigRedNoseItem() {
      super(ModMaterials.SCARF_MATERIAL, "big_nose", Type.HELMET);
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
      list.add(DESCRIPTION);
   }
}
