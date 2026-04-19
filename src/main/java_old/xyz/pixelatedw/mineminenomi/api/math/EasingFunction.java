package xyz.pixelatedw.mineminenomi.api.math;

import java.util.function.Function;

public enum EasingFunction {
   SINE_IN(EasingFunctionHelper::easeInSine),
   QUAD_IN(EasingFunctionHelper::easeInQuad),
   CUBIC_IN(EasingFunctionHelper::easeInCubic),
   ELASTIC_IN(EasingFunctionHelper::easeInElastic),
   BACK_IN(EasingFunctionHelper::easeInBack),
   BOUNCE_IN(EasingFunctionHelper::easeInBounce),
   SINE_OUT(EasingFunctionHelper::easeOutSine),
   QUAD_OUT(EasingFunctionHelper::easeOutQuad),
   CUBIC_OUT(EasingFunctionHelper::easeOutCubic),
   ELASTIC_OUT(EasingFunctionHelper::easeOutElastic),
   BACK_OUT(EasingFunctionHelper::easeOutBack),
   BOUNCE_OUT(EasingFunctionHelper::easeOutBounce),
   SINE_IN_OUT(EasingFunctionHelper::easeInOutSine),
   QUAD_IN_OUT(EasingFunctionHelper::easeInOutQuad),
   CUBIC_IN_OUT(EasingFunctionHelper::easeInOutCubic),
   ELASTIC_IN_OUT(EasingFunctionHelper::easeInOutElastic),
   BACK_IN_OUT(EasingFunctionHelper::easeInOutBack),
   BOUNCE_IN_OUT(EasingFunctionHelper::easeInOutBounce);

   private Function<Float, Float> func;

   private EasingFunction(Function<Float, Float> func) {
      this.func = func;
   }

   public float apply(float x) {
      return (Float)this.func.apply(x);
   }

   // $FF: synthetic method
   private static EasingFunction[] $values() {
      return new EasingFunction[]{SINE_IN, QUAD_IN, CUBIC_IN, ELASTIC_IN, BACK_IN, BOUNCE_IN, SINE_OUT, QUAD_OUT, CUBIC_OUT, ELASTIC_OUT, BACK_OUT, BOUNCE_OUT, SINE_IN_OUT, QUAD_IN_OUT, CUBIC_IN_OUT, ELASTIC_IN_OUT, BACK_IN_OUT, BOUNCE_IN_OUT};
   }
}
