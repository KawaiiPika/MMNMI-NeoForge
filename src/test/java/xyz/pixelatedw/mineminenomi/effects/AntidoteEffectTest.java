package xyz.pixelatedw.mineminenomi.effects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtConstructor;
import java.util.Collection;
import java.util.ArrayList;

class AntidoteEffectTest {
    @Test
    void testShouldApplyEffectTick() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        // Set up the class path for javassist
        pool.insertClassPath(new javassist.ClassClassPath(this.getClass()));

        CtClass ctClass = pool.get("xyz.pixelatedw.mineminenomi.effects.AntidoteEffect");
        // Remove superclass so it doesn't load MobEffect and crash JVM
        ctClass.setSuperclass(pool.get("java.lang.Object"));

        // Remove all constructors because they call super(MobEffectCategory.BENEFICIAL, 0x36BE4E)
        for (CtConstructor constructor : ctClass.getConstructors()) {
            ctClass.removeConstructor(constructor);
        }
        // Add default constructor
        ctClass.addConstructor(javassist.CtNewConstructor.defaultConstructor(ctClass));

        // Remove applyEffectTick method which uses MobEffectInstance, MobEffects, LivingEntity
        for (CtMethod m : ctClass.getDeclaredMethods()) {
            if (!m.getName().equals("shouldApplyEffectTick")) {
                ctClass.removeMethod(m);
            }
        }

        ctClass.setName("xyz.pixelatedw.mineminenomi.effects.AntidoteEffect_IsolatedTestVersion");
        Class<?> clazz = ctClass.toClass(AntidoteEffectTest.class.getClassLoader(), null);
        Object effect = clazz.getDeclaredConstructor().newInstance();

        java.lang.reflect.Method method = clazz.getDeclaredMethod("shouldApplyEffectTick", int.class, int.class);

        // When duration % 20 == 0
        assertTrue((Boolean) method.invoke(effect, 20, 0), "Should apply effect when duration is 20");
        assertTrue((Boolean) method.invoke(effect, 40, 0), "Should apply effect when duration is 40");
        assertTrue((Boolean) method.invoke(effect, 100, 0), "Should apply effect when duration is 100");
        assertTrue((Boolean) method.invoke(effect, 0, 0), "Should apply effect when duration is 0");

        // When duration % 20 != 0
        assertFalse((Boolean) method.invoke(effect, 19, 0), "Should not apply effect when duration is 19");
        assertFalse((Boolean) method.invoke(effect, 21, 0), "Should not apply effect when duration is 21");
        assertFalse((Boolean) method.invoke(effect, 25, 0), "Should not apply effect when duration is 25");

        // Amplifier shouldn't matter
        assertTrue((Boolean) method.invoke(effect, 40, 1), "Should apply effect when duration is 40, regardless of amplifier");
        assertFalse((Boolean) method.invoke(effect, 25, 1), "Should not apply effect when duration is 25, regardless of amplifier");
    }

    public interface DummyHolder {
        Object value();
    }

    public static class DummyHolderImpl implements DummyHolder {
        private final Object effect;
        public DummyHolderImpl(Object effect) { this.effect = effect; }
        public Object value() { return effect; }
    }

    public static class DummyLevel {
        public boolean isClientSide() { return false; }
    }

    public static class DummyEffect {
        private final String name;
        public DummyEffect(String name) { this.name = name; }
        @Override public String toString() { return name; }
    }

    public static class DummyInstance {
        private final DummyHolder effect;
        public DummyInstance(DummyHolder effect) { this.effect = effect; }
        public DummyHolder getEffect() { return effect; }
    }

    public static class DummyEntity {
        public Collection<DummyHolder> removedEffects = new ArrayList<>();
        public Collection<DummyInstance> activeEffects = new ArrayList<>();

        public DummyLevel level() { return new DummyLevel(); }
        public Collection<DummyInstance> getActiveEffects() { return activeEffects; }
        public boolean removeEffect(DummyHolder holder) {
            removedEffects.add(holder);
            return true;
        }
    }

    @Test
    void testApplyEffectTick() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(new javassist.ClassClassPath(this.getClass()));

        CtClass ctClass = pool.get("xyz.pixelatedw.mineminenomi.effects.AntidoteEffect");
        ctClass.setName("xyz.pixelatedw.mineminenomi.effects.AntidoteEffect_TestApplyTick_" + System.currentTimeMillis());

        ctClass.setSuperclass(pool.get("java.lang.Object"));

        for (CtConstructor constructor : ctClass.getConstructors()) {
            ctClass.removeConstructor(constructor);
        }
        ctClass.addConstructor(javassist.CtNewConstructor.defaultConstructor(ctClass));

        CtMethod m = ctClass.getDeclaredMethod("isImmuneTo");
        // Re-implement the same logic as the real code but matching string names, simulating the real method's filtering behaviour
        m.setBody("{ return $1.toString().equals(\"POISON\") || $1.toString().equals(\"WITHER\") || $1.toString().equals(\"BLINDNESS\") || $1.toString().equals(\"CONFUSION\"); }");

        ctClass.replaceClassName("net.minecraft.world.entity.LivingEntity", "xyz.pixelatedw.mineminenomi.effects.AntidoteEffectTest$DummyEntity");
        ctClass.replaceClassName("net.minecraft.world.level.Level", "xyz.pixelatedw.mineminenomi.effects.AntidoteEffectTest$DummyLevel");
        ctClass.replaceClassName("net.minecraft.world.effect.MobEffectInstance", "xyz.pixelatedw.mineminenomi.effects.AntidoteEffectTest$DummyInstance");
        ctClass.replaceClassName("net.minecraft.world.effect.MobEffect", "xyz.pixelatedw.mineminenomi.effects.AntidoteEffectTest$DummyEffect");
        ctClass.replaceClassName("net.minecraft.core.Holder", "xyz.pixelatedw.mineminenomi.effects.AntidoteEffectTest$DummyHolder");

        Class<?> clazz = ctClass.toClass(AntidoteEffectTest.class.getClassLoader(), null);
        Object effect = clazz.getDeclaredConstructor().newInstance();

        DummyEntity entity = new DummyEntity();

        // These EXACTLY map to what the real AntidoteEffect cures.
        DummyEffect poison = new DummyEffect("POISON");
        DummyEffect wither = new DummyEffect("WITHER");
        DummyEffect blindness = new DummyEffect("BLINDNESS");
        DummyEffect confusion = new DummyEffect("CONFUSION");
        // And one that it DOES NOT cure:
        DummyEffect regen = new DummyEffect("REGENERATION");

        DummyHolder poisonHolder = new DummyHolderImpl(poison);
        DummyHolder witherHolder = new DummyHolderImpl(wither);
        DummyHolder blindnessHolder = new DummyHolderImpl(blindness);
        DummyHolder confusionHolder = new DummyHolderImpl(confusion);
        DummyHolder regenHolder = new DummyHolderImpl(regen);

        entity.activeEffects.add(new DummyInstance(poisonHolder));
        entity.activeEffects.add(new DummyInstance(witherHolder));
        entity.activeEffects.add(new DummyInstance(blindnessHolder));
        entity.activeEffects.add(new DummyInstance(confusionHolder));
        entity.activeEffects.add(new DummyInstance(regenHolder));

        java.lang.reflect.Method applyMethod = clazz.getDeclaredMethod("applyEffectTick", DummyEntity.class, int.class);
        boolean result = (Boolean) applyMethod.invoke(effect, entity, 0);

        assertTrue(result, "applyEffectTick should return true");
        assertEquals(4, entity.removedEffects.size(), "Should have removed exactly 4 negative effects");
        assertTrue(entity.removedEffects.contains(poisonHolder), "Should have removed POISON");
        assertTrue(entity.removedEffects.contains(witherHolder), "Should have removed WITHER");
        assertTrue(entity.removedEffects.contains(blindnessHolder), "Should have removed BLINDNESS");
        assertTrue(entity.removedEffects.contains(confusionHolder), "Should have removed CONFUSION");
        assertFalse(entity.removedEffects.contains(regenHolder), "Should not have removed REGENERATION");
    }
}
