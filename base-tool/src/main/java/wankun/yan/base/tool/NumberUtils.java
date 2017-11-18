package wankun.yan.base.tool;

/**
 * Created
 * User  wankunYan
 * Date  2017/11/18
 * Time  0:49
 */
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberUtils {
    private static Logger logger = LoggerFactory.getLogger(NumberUtils.class);
    private static final int DEF_DIV_SCALE = 10;

    private NumberUtils() {
    }

    public static Integer valueOf(Integer value) {
        return Integer.valueOf(value != null?value.intValue():0);
    }

    public static Long valueOf(Long value) {
        return Long.valueOf(value != null?value.longValue():0L);
    }

    public static Double valueOf(Double value) {
        return Double.valueOf(value != null?value.doubleValue():0.0D);
    }

    public static Float valueOf(Float value) {
        return Float.valueOf(value != null?value.floatValue():0.0F);
    }

    public static BigDecimal valueOf(BigDecimal value) {
        return value != null?value:new BigDecimal(0);
    }

    public static Integer intValue(String str) {
        if(StrUtils.notEmpty(str)) {
            try {
                return Integer.valueOf(Integer.parseInt(str));
            } catch (NumberFormatException var2) {
                logger.error(var2.getMessage(), var2);
            }
        }

        return null;
    }

    public static Long longValue(String str) {
        if(StrUtils.notEmpty(str)) {
            try {
                return Long.valueOf(Long.parseLong(str));
            } catch (NumberFormatException var2) {
                logger.error(var2.getMessage(), var2);
            }
        }

        return null;
    }

    public static Double doubleValue(String str) {
        if(StrUtils.notEmpty(str)) {
            try {
                return Double.valueOf(Double.parseDouble(str));
            } catch (NumberFormatException var2) {
                logger.error(var2.getMessage(), var2);
            }
        }

        return null;
    }

    public static Float floatValue(String str) {
        if(StrUtils.notEmpty(str)) {
            try {
                return Float.valueOf(Float.parseFloat(str));
            } catch (NumberFormatException var2) {
                logger.error(var2.getMessage(), var2);
            }
        }

        return null;
    }

    public static boolean isEqual(Integer value1, Integer value2) {
        return value1 != null && value2 != null && value1.equals(value2);
    }

    public static boolean notEqual(Integer value1, Integer value2) {
        return !isEqual(value1, value2);
    }

    public static boolean isEqual(Long value1, Long value2) {
        return value1 != null && value2 != null && value1.equals(value2);
    }

    public static boolean notEqual(Long value1, Long value2) {
        return !isEqual(value1, value2);
    }

    public static boolean isEqual(Double value1, Double value2) {
        return value1 != null && value2 != null && value1.equals(value2);
    }

    public static boolean notEqual(Double value1, Double value2) {
        return !isEqual(value1, value2);
    }

    public static boolean isEqual(BigDecimal value1, Number value2) {
        return value1 != null && value2 != null && value1.compareTo(new BigDecimal(value2.toString())) == 0;
    }

    public static boolean notEqual(BigDecimal value1, Number value2) {
        return !isEqual(value1, value2);
    }

    public static int random(int min, int max) {
        return (int)((double)(max - min + 1) * Math.random()) + min;
    }

    public static double random(double min, double max) {
        return (max - min) * Math.random() + min;
    }

    public static long random(long min, long max) {
        return (long)((double)(max - min + 1L) * Math.random()) + min;
    }

    public static Integer parseInt(String s) {
        if(s != null && s.length() > 0) {
            try {
                return Integer.valueOf(Integer.parseInt(s));
            } catch (Exception var2) {
                logger.error(var2.getMessage(), var2);
            }
        }

        return null;
    }

    public static Long parseLong(String s) {
        if(s != null && s.length() > 0) {
            try {
                return Long.valueOf(Long.parseLong(s));
            } catch (Exception var2) {
                logger.error(var2.getMessage(), var2);
            }
        }

        return null;
    }

    public static Double parseDouble(String s) {
        if(s != null && s.length() > 0) {
            try {
                return Double.valueOf(Double.parseDouble(s));
            } catch (Exception var2) {
                logger.error(var2.getMessage(), var2);
            }
        }

        return null;
    }

    public static Float parseFloat(String s) {
        if(s != null && s.length() > 0) {
            try {
                return Float.valueOf(Float.parseFloat(s));
            } catch (Exception var2) {
                logger.error(var2.getMessage(), var2);
            }
        }

        return null;
    }

    public static BigDecimal parseBigDecimal(String s) {
        if(s != null && s.length() > 0) {
            try {
                return new BigDecimal(s);
            } catch (Exception var2) {
                logger.error(var2.getMessage(), var2);
            }
        }

        return null;
    }

    public static List<Integer> getRandomIndexList(int size, int indexCount) {
        List<Integer> indexList = new LinkedList();
        List<Integer> allIndexList = new LinkedList();

        int i;
        for(i = 0; i < size; ++i) {
            allIndexList.add(Integer.valueOf(i));
        }

        for(i = 0; i < indexCount; ++i) {
            int allIndexSize = allIndexList.size();
            if(allIndexSize == 0) {
                break;
            }

            indexList.add(allIndexList.remove(random(0, allIndexSize - 1)));
        }

        return indexList;
    }

    public static int getChanceIndex(double[] chances) {
        return getChanceIndex(0, chances);
    }

    public static int getChanceIndex(int total, double[] chances) {
        int temp = total;
        if(total <= 0) {
            for(int i = 0; i < chances.length; ++i) {
                temp = (int)((double)temp + chances[i] * 100.0D);
            }
        } else {
            temp = total * 100;
        }

        Random random = new Random();

        for(int i = 0; i < chances.length; ++i) {
            double chance = chances[i] * 100.0D;
            if((double)random.nextInt(temp) < chance) {
                return i;
            }

            temp = (int)((double)temp - chance);
        }

        return -1;
    }

    public static double add(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.add(b2).doubleValue();
    }

    public static BigDecimal add(BigDecimal value1, BigDecimal value2) {
        return valueOf(value1).add(valueOf(value2));
    }

    public static double addAll(double... values) {
        double total = 0.0D;
        if(values.length > 0) {
            double[] var3 = values;
            int var4 = values.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                double value = var3[var5];
                total = add(total, value);
            }
        }

        return total;
    }

    public static BigDecimal addAll(BigDecimal... values) {
        BigDecimal total = new BigDecimal(0);
        if(values.length > 0) {
            BigDecimal[] var2 = values;
            int var3 = values.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                BigDecimal value = var2[var4];
                total = add(total, value);
            }
        }

        return total;
    }

    public static double subtract(Double value1, Double value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(valueOf(value1).doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(valueOf(value2).doubleValue()));
        return b1.subtract(b2).doubleValue();
    }

    public static BigDecimal subtract(BigDecimal value1, BigDecimal value2) {
        return valueOf(value1).subtract(valueOf(value2));
    }

    public static double multiply(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.multiply(b2).doubleValue();
    }

    public static double multiply(double value1, double value2, int scale) {
        if(scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b1 = new BigDecimal(Double.toString(value1));
            BigDecimal b2 = new BigDecimal(Double.toString(value2));
            return round(b1.multiply(b2).doubleValue(), scale);
        }
    }

    public static double divide(double value1, double value2) {
        return divide(value1, value2, 10);
    }

    public static double divide(double value1, double value2, int scale) {
        if(scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b1 = new BigDecimal(Double.toString(value1));
            BigDecimal b2 = new BigDecimal(Double.toString(value2));
            return b1.divide(b2, scale, 4).doubleValue();
        }
    }

    public static float add(float value1, float value2) {
        BigDecimal b1 = new BigDecimal(Float.toString(value1));
        BigDecimal b2 = new BigDecimal(Float.toString(value2));
        return b1.add(b2).floatValue();
    }

    public static float subtract(float value1, float value2) {
        BigDecimal b1 = new BigDecimal(Float.toString(value1));
        BigDecimal b2 = new BigDecimal(Float.toString(value2));
        return b1.subtract(b2).floatValue();
    }

    public static float multiply(float value1, float value2) {
        BigDecimal b1 = new BigDecimal(Float.toString(value1));
        BigDecimal b2 = new BigDecimal(Float.toString(value2));
        return b1.multiply(b2).floatValue();
    }

    public static float multiply(float value1, float value2, int scale) {
        if(scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b1 = new BigDecimal(Float.toString(value1));
            BigDecimal b2 = new BigDecimal(Float.toString(value2));
            return round(b1.multiply(b2).floatValue(), scale);
        }
    }

    public static float divide(float value1, float value2) {
        return divide(value1, value2, 10);
    }

    public static float divide(float value1, float value2, int scale) {
        if(scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b1 = new BigDecimal(Float.toString(value1));
            BigDecimal b2 = new BigDecimal(Float.toString(value2));
            return b1.divide(b2, scale, 4).floatValue();
        }
    }

    public static double round(double value, int scale) {
        return rounding(value, scale, 4);
    }

    public static float round(float value, int scale) {
        return rounding(value, scale, 4);
    }

    public static double roundUp(double value, int scale) {
        return rounding(value, scale, 0);
    }

    public static float roundUp(float value, int scale) {
        return rounding(value, scale, 0);
    }

    public static double roundDown(double value, int scale) {
        return rounding(value, scale, 1);
    }

    public static float roundDown(float value, int scale) {
        return rounding(value, scale, 1);
    }

    private static double rounding(double value, int scale, int roundingMode) {
        if(scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b = new BigDecimal(Double.toString(value));
            BigDecimal one = new BigDecimal("1");
            return b.divide(one, scale, roundingMode).doubleValue();
        }
    }

    private static float rounding(float value, int scale, int roundingMode) {
        if(scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b = new BigDecimal(Float.toString(value));
            BigDecimal one = new BigDecimal("1");
            return b.divide(one, scale, roundingMode).floatValue();
        }
    }
}

