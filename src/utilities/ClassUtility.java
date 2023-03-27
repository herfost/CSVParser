package utilities;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ClassUtility {

    private ClassUtility() {
    }

    /**
     *
     * @param object
     * @return
     */
    public static List<Field> getFields(Class<?> object) {
        Field[] fields = object.getFields();
        return Arrays.asList(fields);
    }

    /**
     *
     * @param object
     * @param field
     * @param value
     * @throws ClassNotFoundException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void setField(Object object, Field field, String value) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
        Class<?> type = field.getType();
        Object fieldValue = ObjectUtility.parseObject(type, value);
        field.set(object, fieldValue);
    }

    public static Object getFieldValue(Object object, Field field) throws IllegalArgumentException, IllegalAccessException {
        boolean accessible = field.isAccessible();

        field.setAccessible(true);
        Object value = field.get(object);

        field.setAccessible(accessible);
        return value;
    }
}
