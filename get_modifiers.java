import net.neoforged.neoforge.common.damagesource.DamageContainer;
public class get_modifiers {
    public static void main(String[] args) {
        for (java.lang.reflect.Method m : DamageContainer.class.getMethods()) {
             if (m.getName().equals("addModifier")) {
                 System.out.println(m);
             }
        }
    }
}
