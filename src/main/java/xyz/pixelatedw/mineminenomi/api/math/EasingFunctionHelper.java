package xyz.pixelatedw.mineminenomi.api.math;

public class EasingFunctionHelper {
   public static float easeInOutQuad(float x) {
      return easeInQuad(x) * (1.0F - x) + easeOutQuad(x) * x;
   }

   public static float easeOutQuad(float x) {
      return (float)((double)1.0F - Math.pow((double)(1.0F - x), (double)2.0F));
   }

   public static float easeInQuad(float x) {
      return x * x;
   }

   public static float easeOutCubic(float x) {
      return (float)((double)1.0F - Math.pow((double)(1.0F - x), (double)3.0F));
   }

   public static float easeInCubic(float x) {
      return x * x * x;
   }

   public static float easeInOutCubic(float x) {
      return (float)((double)x < (double)0.5F ? (double)(4.0F * x * x * x) : (double)1.0F - Math.pow((double)(-2.0F * x + 2.0F), (double)3.0F) / (double)2.0F);
   }

   public static float easeOutQuart(float x) {
      return (float)((double)1.0F - Math.pow((double)(1.0F - x), (double)4.0F));
   }

   public static float easeInQuart(float x) {
      return x * x * x * x;
   }

   public static float easeInOutQuart(float x) {
      return (float)((double)x < (double)0.5F ? (double)(8.0F * x * x * x) : (double)1.0F - Math.pow((double)(-2.0F * x + 2.0F), (double)4.0F) / (double)2.0F);
   }

   public static float easeOutQuint(float x) {
      return (float)((double)1.0F - Math.pow((double)(1.0F - x), (double)5.0F));
   }

   public static float easeInQuint(float x) {
      return x * x * x * x * x;
   }

   public static float easeInOutQuint(float x) {
      return (float)((double)x < (double)0.5F ? (double)(16.0F * x * x * x) : (double)1.0F - Math.pow((double)(-2.0F * x + 2.0F), (double)5.0F) / (double)2.0F);
   }

   public static float easeOutSine(float x) {
      return (float)Math.sin((double)x * Math.PI / (double)2.0F);
   }

   public static float easeInSine(float x) {
      return (float)((double)1.0F - Math.cos((double)x * Math.PI / (double)2.0F));
   }

   public static float easeInOutSine(float x) {
      return (float)(-(Math.cos(Math.PI * (double)x) - (double)1.0F) / (double)2.0F);
   }

   public static float easeInBack(float x) {
      float y1 = 1.70158F;
      float y2 = y1 + 1.0F;
      return y2 * x * x * x - y1 * x * x;
   }

   public static float easeOutBack(float x) {
      float y1 = 1.70158F;
      float y2 = y1 + 1.0F;
      return (float)((double)1.0F + (double)y2 * Math.pow((double)(x - 1.0F), (double)3.0F) + (double)y1 * Math.pow((double)(x - 1.0F), (double)2.0F));
   }

   public static float easeInOutBack(float x) {
      float y1 = 1.70158F;
      float y2 = y1 + 1.525F;
      return (float)((double)x < (double)0.5F ? Math.pow((double)(2.0F * x), (double)2.0F) * (double)((y2 + 1.0F) * 2.0F * x - y2) / (double)2.0F : (Math.pow((double)(2.0F * x - 2.0F), (double)2.0F) * (double)((y2 + 1.0F) * (x * 2.0F - 2.0F) + y2) + (double)2.0F) / (double)2.0F);
   }

   public static float easeInElastic(float x) {
      float y = 2.0943952F;
      return (float)(x == 0.0F ? (double)0.0F : (x == 1.0F ? (double)1.0F : -Math.pow((double)2.0F, (double)(10.0F * x - 10.0F)) * Math.sin(((double)(x * 10.0F) - (double)10.75F) * (double)y)));
   }

   public static float easeOutElastic(float x) {
      float y = 2.0943952F;
      return (float)(x == 0.0F ? (double)0.0F : (x == 1.0F ? (double)1.0F : Math.pow((double)2.0F, (double)(-10.0F * x)) * Math.sin(((double)(x * 10.0F) - (double)0.75F) * (double)y) + (double)1.0F));
   }

   public static float easeInOutElastic(float x) {
      float y = 1.3962634F;
      return (float)(x == 0.0F ? (double)0.0F : (x == 1.0F ? (double)1.0F : ((double)x < (double)0.5F ? -(Math.pow((double)2.0F, (double)(20.0F * x - 10.0F)) * Math.sin(((double)(20.0F * x) - (double)11.125F) * (double)y)) / (double)2.0F : Math.pow((double)2.0F, (double)(-20.0F * x + 10.0F)) * Math.sin(((double)(20.0F * x) - (double)11.125F) * (double)y) / (double)2.0F + (double)1.0F)));
   }

   public static float easeInBounce(float x) {
      return easeOutBounce(1.0F - x);
   }

   public static float easeOutBounce(float x) {
      float n = 7.5625F;
      float d = 2.75F;
      if (x < 1.0F / d) {
         return n * x * x;
      } else if (x < 2.0F / d) {
         float var5 = x - 1.5F / d;
         return (float)((double)(n * var5 * var5) + (double)0.75F);
      } else {
         if ((double)x < (double)2.5F / (double)d) {
             float var3 = x - 2.25F / d;
             return (float)((double)(n * var3 * var3) + (double)0.9375F);
         } else {
             float var4 = x - 2.625F / d;
             return (float)((double)(n * var4 * var4) + (double)0.984375F);
         }
      }
   }

   public static float easeInOutBounce(float x) {
      return (double)x < (double)0.5F ? (1.0F - easeOutBounce(1.0F - 2.0F * x)) / 2.0F : (1.0F + easeOutBounce(2.0F * x - 1.0F)) / 2.0F;
   }

   public static float easeAbsoluteSine(float x) {
      return (float)Math.abs(Math.sin((double)x));
   }
}
