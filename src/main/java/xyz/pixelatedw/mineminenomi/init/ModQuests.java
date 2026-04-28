package xyz.pixelatedw.mineminenomi.init;

import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.quests.doctor.*;

public class ModQuests {
    public static void init() {}

    public static final DeferredHolder<QuestId<?>, QuestId<DoctorTrial05Quest>> DOCTOR_TRIAL_05 = ModRegistries.QUESTS_REGISTRY.register("doctor_trial_05", () -> new QuestId.Builder<>(DoctorTrial05Quest::new).build());
}
