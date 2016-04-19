package com.bird.beans;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BeanHelper {

    private static char         delimiter    = ',';
    private static char[]       allowedChars = new char[] { '.', '-' };
    // getPackage() below returns null on some platforms/jvm versions during the unit tests.
    // private static final String PACKAGE = BeanHelper.class.getPackage().getName() + ".";
    // 排除当前包的影响
    private static final String PACKAGE      = "com.bird.beans.";

    /**
     * <p>
     * Parse an incoming String of the form similar to an array initializer in the Java language into a
     * <code>List</code> individual Strings for each element, according to the following rules.
     * </p>
     * <ul>
     * <li>The string is expected to be a comma-separated list of values.</li>
     * <li>The string may optionally have matching '{' and '}' delimiters around the list.</li>
     * <li>Whitespace before and after each element is stripped.</li>
     * <li>Elements in the list may be delimited by single or double quotes. Within a quoted elements, the normal Java
     * escape sequences are valid.</li>
     * </ul>
     *
     * @param type The type to convert the value to
     * @param value String value to be parsed
     * @return List of parsed elements.
     * @throws ConversionException if the syntax of <code>svalue</code> is not syntactically valid
     * @throws NullPointerException if <code>svalue</code> is <code>null</code>
     */
    public static List<String> parseElements(Class<?> type, String value) {

        // Trim any matching '{' and '}' delimiters
        value = value.trim();
        if (value.startsWith("{") && value.endsWith("}")) {
            value = value.substring(1, value.length() - 1);
        }

        try {

            // Set up a StreamTokenizer on the characters in this String
            StreamTokenizer st = new StreamTokenizer(new StringReader(value));
            st.whitespaceChars(delimiter, delimiter); // Set the delimiters
            st.ordinaryChars('0', '9'); // Needed to turn off numeric flag
            st.wordChars('0', '9'); // Needed to make part of tokens
            for (int i = 0; i < allowedChars.length; i++) {
                st.ordinaryChars(allowedChars[i], allowedChars[i]);
                st.wordChars(allowedChars[i], allowedChars[i]);
            }

            // Split comma-delimited tokens into a List
            List<String> list = null;
            while (true) {
                int ttype = st.nextToken();
                if ((ttype == StreamTokenizer.TT_WORD) || (ttype > 0)) {
                    if (st.sval != null) {
                        if (list == null) {
                            list = new ArrayList<String>();
                        }
                        list.add(st.sval);
                    }
                } else if (ttype == StreamTokenizer.TT_EOF) {
                    break;
                } else {
                    throw new RuntimeException("Encountered token of type " + ttype + " parsing elements to '"
                                               + toString(type) + ".");
                }
            }

            if (list == null) {
                list = Collections.emptyList();
            }

            // Return the completed list
            return (list);

        } catch (IOException e) {

            throw new RuntimeException("Error converting from String to '" + toString(type) + "': " + e.getMessage(),
                                       e);

        }

    }

    /**
     * Provide a String representation of a <code>java.lang.Class</code>.
     * 
     * @param type The <code>java.lang.Class</code>.
     * @return The String representation.
     */
    public static String toString(Class<?> type) {
        String typeName = null;
        if (type == null) {
            typeName = "null";
        } else if (type.isArray()) {
            Class<?> elementType = type.getComponentType();
            int count = 1;
            while (elementType.isArray()) {
                elementType = elementType.getComponentType();
                count++;
            }
            typeName = elementType.getName();
            for (int i = 0; i < count; i++) {
                typeName += "[]";
            }
        } else {
            typeName = type.getName();
        }
        if (typeName.startsWith("java.lang.") || typeName.startsWith("java.util.")
            || typeName.startsWith("java.math.")) {
            typeName = typeName.substring("java.lang.".length());
        } else if (typeName.startsWith(PACKAGE)) {
            typeName = typeName.substring(PACKAGE.length());
        }
        return typeName;
    }

    /**
     * Return the first element from an Array (or Collection) or the value unchanged if not an Array (or Collection).
     * N.B. This needs to be overriden for array/Collection converters.
     *
     * @param value The value to convert
     * @return The first element in an Array (or Collection) or the value unchanged if not an Array (or Collection)
     */
    public static Object convertArray(Object value) {
        if (value == null) {
            return null;
        }
        if (value.getClass().isArray()) {
            if (Array.getLength(value) > 0) {
                return Array.get(value, 0);
            } else {
                return null;
            }
        }
        if (value instanceof Collection) {
            Collection<?> collection = (Collection<?>) value;
            if (collection.size() > 0) {
                return collection.iterator().next();
            } else {
                return null;
            }
        }
        return value;
    }

    /**
     * <p>
     * Convert the input object into a java.lang.Class.
     * </p>
     *
     * @param <T> Target type of the conversion.
     * @param type Data type to which this value should be converted.
     * @param value The input value to be converted.
     * @return The converted value.
     * @throws Throwable if an error occurs converting to the specified type
     */
    public static <T> T convertToType(Class<T> type, Object value) throws Throwable {
        if (Class.class.equals(type)) {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader != null) {
                try {
                    return type.cast(classLoader.loadClass(value.toString()));
                } catch (ClassNotFoundException ex) {
                    // Don't fail, carry on and try this class's class loader
                    // (see issue# BEANUTILS-263)
                }
            }

            // Try this class's class loader
            classLoader = BeanHelper.class.getClassLoader();
            return type.cast(classLoader.loadClass(value.toString()));
        }

        throw new Exception("Can't convert value '" + value + "' to type " + type);
    }
}
