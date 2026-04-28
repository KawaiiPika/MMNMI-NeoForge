#!/bin/bash
# A cleaner way to strip those quests completely out of the ModQuests class
cat src/main/java/xyz/pixelatedw/mineminenomi/init/ModQuests.java | sed -n '1,/public static final DeferredHolder<QuestId<?>, QuestId<DoctorTrial05Quest>> DOCTOR_TRIAL_05/p' > ModQuests_fixed.java
echo " = ModRegistries.QUESTS_REGISTRY.register(\"doctor_trial_05\", () -> {" >> ModQuests_fixed.java
echo "        QuestId<DoctorTrial05Quest> id = DoctorTrial05Quest.INSTANCE;" >> ModQuests_fixed.java
echo "        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(\"mineminenomi\", \"doctor_trial_05\"));" >> ModQuests_fixed.java
echo "        return id;" >> ModQuests_fixed.java
echo "    });" >> ModQuests_fixed.java
echo "" >> ModQuests_fixed.java
echo "    public static void init(IEventBus bus) {" >> ModQuests_fixed.java
echo "    }" >> ModQuests_fixed.java
echo "}" >> ModQuests_fixed.java

mv ModQuests_fixed.java src/main/java/xyz/pixelatedw/mineminenomi/init/ModQuests.java

# Now clean up imports again
sed -i '/import xyz.pixelatedw.mineminenomi.quests.sniper.\*;/d; /import xyz.pixelatedw.mineminenomi.quests.artofweather.\*;/d' src/main/java/xyz/pixelatedw/mineminenomi/init/ModQuests.java
