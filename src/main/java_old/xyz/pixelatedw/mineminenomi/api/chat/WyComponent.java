package xyz.pixelatedw.mineminenomi.api.chat;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.registries.RegistryObject;

public interface WyComponent extends Component {
   static MutableComponent reference(RegistryObject<?> object) {
      return MutableComponent.m_237204_(new ReferenceContents(object));
   }
}
