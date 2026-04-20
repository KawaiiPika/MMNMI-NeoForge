package xyz.pixelatedw.mineminenomi.effects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

class AntidoteEffectTest {
    @Test
    void testShouldApplyEffectTick() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        // Set up the class path for javassist
        pool.appendClassPath("build/classes/java/main");

        CtClass ctClass = pool.get("xyz.pixelatedw.mineminenomi.effects.AntidoteEffect");
        // Remove superclass so it doesn't load MobEffect and crash JVM
        ctClass.setSuperclass(pool.get("java.lang.Object"));

        // Remove constructor because it calls super(MobEffectCategory.BENEFICIAL, 0x36BE4E)
        ctClass.removeConstructor(ctClass.getConstructors()[0]);
        // Add default constructor
        ctClass.addConstructor(javassist.CtNewConstructor.defaultConstructor(ctClass));

        // Remove applyEffectTick method which uses MobEffectInstance, MobEffects, LivingEntity
        CtMethod applyMethod = ctClass.getDeclaredMethod("applyEffectTick");
        ctClass.removeMethod(applyMethod);

        CtMethod isImmuneToMethod = ctClass.getDeclaredMethod("isImmuneTo");
        ctClass.removeMethod(isImmuneToMethod);

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
