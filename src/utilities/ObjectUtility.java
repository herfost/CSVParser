package utilities;

public final class ObjectUtility {

    private ObjectUtility() {
    }

    /**
     * restituisce un oggetto impostandone il valore corrispettivo
     * <italic>value</italic>
     *
     * @param classType classe dell'oggeto da restituire
     * @param value valore dell'oggetto costruito
     * @return l'oggetto costruito
     * @throws ClassNotFoundException
     */
    public static Object parseObject(Class<?> classType, String value) throws ClassNotFoundException {
        Object object = null;

        if (Byte.class == classType) {
            object = Byte.parseByte(value);
        }

        if (Character.class == classType) {
            object = Byte.parseByte(value);

        }

        if (Integer.class == classType) {
            object = Integer.parseInt(value);
        }

        if (Double.class == classType) {
            object = Double.parseDouble(value);
        }

        if (String.class == classType) {
            object = value;
        }

        if (Boolean.class == classType) {
            object = Boolean.parseBoolean(value);
        }

        if (null == object) {
            throw new ClassNotFoundException();
        }

        return object;
    }

}
