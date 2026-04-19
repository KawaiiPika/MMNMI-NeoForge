package xyz.pixelatedw.mineminenomi.api.entities.revenge;

import com.google.common.collect.Sets;
import java.util.Set;

public interface IRevengeEntity {
   default Set<RevengeMeter> getRevengeMeters() {
      return Sets.newHashSet();
   }
}
