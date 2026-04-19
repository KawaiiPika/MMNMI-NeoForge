package xyz.pixelatedw.mineminenomi.api.math;

public class EasingFunctionHelper {
    public static float easeInOutQuad(float x) {
        return easeInQuad(x) * (1.0F - x) + easeOutQuad(x) * x;
    }

    public static float easeOutQuad(float x) {
        return (float) (1.0F - Math.pow(1.0F - x, 2.0F));
    }

    public static float easeInQuad(float x) {
        return x * x;
    }

    public static float easeOutCubic(float x) {
        return (float) (1.0F - Math.pow(1.0F - x, 3.0F));
    }

    public static float easeInCubic(float x) {
        return x * x * x;
    }

    public static float easeInOutCubic(float x) {
        return (float) (x < 0.5F ? (4.0F * x * x * x) : 1.0F - Math.pow(-2.0F * x + 2.0F, 3.0F) / 2.0F);
    }

    public static float easeOutQuart(float x) {
        return (float) (1.0F - Math.pow(1.0F - x, 4.0F));
    }

    public static float easeInQuart(float x) {
        return x * x * x * x;
    }

    public static float easeInOutQuart(float x) {
        return (float) (x < 0.5F ? (8.0F * x * x * x) : 1.0F - Math.pow(-2.0F * x + 2.0F, 4.0F) / 2.0F);
    }

    public static float easeOutQuint(float x) {
        return (float) (1.0F - Math.pow(1.0F - x, 5.0F));
    }

    public static float easeInQuint(float x) {
        return x * x * x * x * x;
    }

    public static float easeInOutQuint(float x) {
        return (float) (x < 0.5F ? (16.0F * x * x * x) : 1.0F - Math.pow(-2.0F * x + 2.0F, 5.0F) / 2.0F);
    }

    public static float easeOutSine(float x) {
        return (float) Math.sin(x * Math.PI / 2.0F);
    }

    public static float easeInSine(float x) {
        return (float) (1.0F - Math.cos(x * Math.PI / 2.0F));
    }

    public static float easeInOutSine(float x) {
        return (float) (-(Math.cos(Math.PI * x) - 1.0F) / 2.0F);
    }

    public static float easeInBack(float x) {
        float y1 = 1.70158F;
        float y2 = y1 + 1.0F;
        return y2 * x * x * x - y1 * x * x;
    }

    public static float easeOutBack(float x) {
        float y1 = 1.70158F;
        float y2 = y1 + 1.0F;
        return (float) (1.0F + y2 * Math.pow(x - 1.0F, 3.0F) + y1 * Math.pow(x - 1.0F, 2.0F));
    }

    public static float easeInOutBack(float x) {
        float y1 = 1.70158F;
        float y2 = y1 * 1.525F;
        return (float) (x < 0.5F ? Math.pow(2.0F * x, 2.0F) * ((y2 + 1.0F) * 2.0F * x - y2) / 2.0F : (Math.pow(2.0F * x - 2.0F, 2.0F) * ((y2 + 1.0F) * (x * 2.0F - 2.0F) + y2) + 2.0F) / 2.0F);
    }

    public static float easeInElastic(float x) {
        float y = 2.0943952F;
        return (float) (x == 0.0F ? 0.0F : (x == 1.0F ? 1.0F : -Math.pow(2.0F, 10.0F * x - 10.0F) * Math.sin((x * 10.0F - 10.75F) * y)));
    }

    public static float easeOutElastic(float x) {
        float y = 2.0943952F;
        return (float) (x == 0.0F ? 0.0F : (x == 1.0F ? 1.0F : Math.pow(2.0F, -10.0F * x) * Math.sin((x * 10.0F - 0.75F) * y) + 1.0F));
    }

    public static float easeInOutElastic(float x) {
        float y = 1.3962634F;
        return (float) (x == 0.0F ? 0.0F : (x == 1.0F ? 1.0F : (x < 0.5F ? -(Math.pow(2.0F, 20.0F * x - 10.0F) * Math.sin((20.0F * x - 11.125F) * y)) / 2.0F : Math.pow(2.0F, -20.0F * x + 10.0F) * Math.sin((20.0F * x - 11.125F) * y) / 2.0F + 1.0F)));
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
            return n * (x -= 1.5F / d) * x + 0.75F;
        } else if (x < 2.5F / d) {
            return n * (x -= 2.25F / d) * x + 0.9375F;
        } else {
            return n * (x -= 2.625F / d) * x + 0.984375F;
        }
    }

    public static float easeInOutBounce(float x) {
        return x < 0.5F ? (1.0F - easeOutBounce(1.0F - 2.0F * x)) / 2.0F : (1.0F + easeOutBounce(2.0F * x - 1.0F)) / 2.0F;
    }

    public static float easeAbsoluteSine(float x) {
        return (float) Math.abs(Math.sin(x));
    }
}
