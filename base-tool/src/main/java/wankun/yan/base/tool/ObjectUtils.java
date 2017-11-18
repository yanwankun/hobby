package wankun.yan.base.tool;

/**
 * Created
 * User  wankunYan
 * Date  2017/11/18
 * Time  10:29
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectUtils {
    private static Logger logger = LoggerFactory.getLogger(ObjectUtils.class);

    private ObjectUtils() {
    }

    public static boolean isEmpty(Object object) {
        return object == null?true:(object instanceof String?StrUtils.isEmpty((String)object):(object instanceof CharSequence?((CharSequence)object).length() == 0:(object instanceof Collection?((Collection)object).isEmpty():(object instanceof Map?((Map)object).isEmpty():(object.getClass().isArray()?Array.getLength(object) == 0:false)))));
    }

    public static boolean notEmpty(Object object) {
        return !isEmpty(object);
    }

    public static String toString(Object object) {
        return object != null?object.toString():null;
    }

    public static boolean isAllEmpty(Object... objects) {
        Object[] var1 = objects;
        int var2 = objects.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Object object = var1[var3];
            if(!isEmpty(object)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isAnyEmpty(Object... objects) {
        Object[] var1 = objects;
        int var2 = objects.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Object object = var1[var3];
            if(isEmpty(object)) {
                return true;
            }
        }

        return false;
    }

    public static Map<String, Object> objectToMap(Object parameterObject) {
        return objectToMap(parameterObject, false);
    }

    public static Map<String, Object> objectToMap(Object parameterObject, boolean ignoreNullValue) {
        Map<String, Object> map = new LinkedHashMap();
        if(parameterObject == null) {
            return map;
        } else {
            for(Class cls = parameterObject.getClass(); cls != Object.class; cls = cls.getSuperclass()) {
                processMapFields(parameterObject, map, cls, ignoreNullValue);
            }

            return map;
        }
    }

    private static void processMapFields(Object parameterObject, Map<String, Object> map, Class<?> cls, boolean ignoreNullValue) {
        Field[] fields = cls.getDeclaredFields();
        Field[] var5 = fields;
        int var6 = fields.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Field field = var5[var7];
            int modifiers = field.getModifiers();
            if(modifiers == 1 || modifiers == 2 || modifiers == 4) {
                try {
                    field.setAccessible(true);
                    if(field.isAccessible()) {
                        Object value = field.get(parameterObject);
                        if(ignoreNullValue) {
                            if(value != null) {
                                map.put(field.getName(), value);
                            }
                        } else {
                            map.put(field.getName(), value);
                        }
                    }
                } catch (IllegalAccessException var11) {
                    logger.error(var11.getMessage(), var11);
                }
            }
        }

    }

    public static <T> T clone(T object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (T)ois.readObject();
        } catch (Exception var5) {
            throw new RuntimeException(var5);
        }
    }

    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList();

        for(Class tempClass = clazz; tempClass != null && tempClass != Object.class; tempClass = tempClass.getSuperclass()) {
            Field[] fields = tempClass.getDeclaredFields();
            if(notEmpty(fields)) {
                Field[] var4 = fields;
                int var5 = fields.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    Field field = var4[var6];
                    if(field.getModifiers() == 2) {
                        fieldList.add(field);
                    }
                }
            }
        }

        return fieldList;
    }
}

