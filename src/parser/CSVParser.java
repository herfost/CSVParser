package parser;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CSVParser {

    private CSVParser() {
    }

    public static List<Field> getFields(Class<?> classType) throws NoSuchFieldException {
        Field[] declaredFields = classType.getDeclaredFields();

        ArrayList<Field> fields = new ArrayList<>(Arrays.asList(declaredFields));
        fields.remove(classType.getDeclaredField("serialVersionUID"));

        return fields;
    }

    /**
     *
     * @param path
     * @param delimiter
     * @param encodingCharset
     * @return
     * @throws IOException
     */
    public static List<String> getFieldsName(String path, String delimiter, Charset encodingCharset) throws IOException {
        List<String> lines = utilities.FileUtility.readLines(path, encodingCharset);
        String line = lines.get(0);
        String[] fieldNames = line.split(delimiter);

        return Arrays.asList(fieldNames);
    }

    /**
     *
     * @param csv
     * @param delimiter
     * @return
     */
    public static List<String> getFieldNames(String csv, String delimiter) {
        String fieldNameLine = csv.split("\n")[0];
        String[] split = fieldNameLine.split(delimiter);
        for (String s : split) {
            s = s.replace("\r", "");
        }
        return Arrays.asList((split));
    }

    public static List<String> getFieldNames(Class<?> classType) throws NoSuchFieldException {
        List<Field> fields = getFields(classType);
        List<String> fieldsName = new ArrayList<>();
        fields.forEach((field) -> {
            fieldsName.add(field.getName());
        });

        return fieldsName;
    }

    /**
     *
     * @param path
     * @param encodingCharset
     * @return
     * @throws IOException
     */
    public static List<String> getRecords(String path, Charset encodingCharset) throws IOException {
        List<String> lines = utilities.FileUtility.readLines(path, encodingCharset);
        lines.remove(0);

        return lines;
    }

    /**
     *
     * @param csv
     * @return
     * @throws IOException
     */
    public static List<String> getRecords(String csv) throws IOException {
        String[] lines = csv.split("\n");
        List<String> asList = new ArrayList<>(Arrays.asList(lines));
        asList.remove(0); // Field names

        return asList;
    }

    /**
     *
     * @param record
     * @param delimiter
     * @return
     */
    public static List<String> getValues(String record, String delimiter) {
        String[] values = record.split(delimiter);
        return new ArrayList<>(Arrays.asList(values));
    }

    /**
     *
     * @param classType
     * @param filePath
     * @param encodingCharset
     * @param delimiter
     * @return
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws NoSuchFieldException
     * @throws ClassNotFoundException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public static List<Object> objectsFromCSVFile(Class<?> classType, String filePath, Charset encodingCharset, String delimiter) throws IOException, NoSuchMethodException, NoSuchFieldException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException {
        List<String> fields = getFieldsName(filePath, delimiter, encodingCharset);
        List<String> records = getRecords(filePath, encodingCharset);

        List<Object> objects = new ArrayList();
        for (String record : records) {
            Object object = objectFromCSVRecord(classType, record, fields, delimiter);
            objects.add(object);
        }

        return objects;
    }

    /**
     *
     * @param classType
     * @param record
     * @param fieldNames
     * @param delimiter
     * @return
     * @throws NoSuchMethodException
     * @throws NoSuchFieldException
     * @throws ClassNotFoundException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public static Object objectFromCSVRecord(Class<?> classType, String record, List<String> fieldNames, String delimiter) throws NoSuchMethodException, NoSuchFieldException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException {
        List<String> values = getValues(record, delimiter);
        Object object = classType.getConstructor().newInstance();

        for (int i = 0; i < values.size(); ++i) {
            String fieldName = fieldNames.get(i);

            try {
                Field field = classType.getDeclaredField(fieldName);
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                utilities.ClassUtility.setField(object, field, values.get(i));
                field.setAccessible(accessible);
            } catch (NoSuchFieldException ex) {
                throw new NoSuchFieldException(ex.getMessage() + ": " + fieldName);
            }

        }

        return object;
    }

    /**
     *
     * @param classType
     * @param csv
     * @param encodingCharset
     * @param delimiter
     * @return
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws NoSuchFieldException
     * @throws ClassNotFoundException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public static List<Object> objectsFromCSV(Class<?> classType, String csv, String delimiter) throws IOException, NoSuchMethodException, NoSuchFieldException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException {
        List<String> fields = getFieldNames(csv, delimiter);
        List<String> records = getRecords(csv);

        List<Object> objects = new ArrayList();
        for (String record : records) {
            Object object = objectFromCSVRecord(classType, record, fields, delimiter);
            objects.add(object);
        }

        return objects;
    }

    /**
     *
     * @param classType
     * @param object
     * @param delimiter
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static String objectToCSVRecord(Class<?> classType, Object object, String delimiter) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
        StringBuilder stringBuilder = new StringBuilder();
        List<Field> fields = getFields(classType);

        for (Field field : fields) {
            Object value = utilities.ClassUtility.getFieldValue(object, field);
            stringBuilder.append(value);
            stringBuilder.append(delimiter);
        }

        stringBuilder.setCharAt(stringBuilder.length() - 1, '\n');
        return stringBuilder.toString();
    }

    /**
     *
     * @param classType
     * @param objects
     * @param delimiter
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static String objectsToCSVRecords(Class<?> classType, List<Object> objects, String delimiter) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object object : objects) {
            stringBuilder.append(objectToCSVRecord(classType, object, delimiter));
        }

        return stringBuilder.toString();
    }

    /**
     *
     * @param classType
     * @param objects
     * @param delimiter
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static String objectsToCSV(Class<?> classType, List<Object> objects, String delimiter) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
        StringBuilder stringBuilder = new StringBuilder();

        List<String> fieldNames = getFieldNames(classType);
        for (String fieldName : fieldNames) {
            stringBuilder.append(fieldName + delimiter);
        }

        stringBuilder.append('\n');

        for (Object object : objects) {
            stringBuilder.append(objectToCSVRecord(classType, object, delimiter));
        }

        return stringBuilder.toString();
    }
}
