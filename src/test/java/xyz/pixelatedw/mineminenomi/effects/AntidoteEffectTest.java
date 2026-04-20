package xyz.pixelatedw.mineminenomi.effects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtConstructor;

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
}
