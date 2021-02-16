package org.mik.FirstMaven.export;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class XMLGenerator {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public String convertToXML(Object object) throws XMLSerializationException {
        try {
            StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" ?>").append(LINE_SEPARATOR);
            this.process(object, sb);
            return sb.toString();
        } catch (Exception e) {
            throw new XMLSerializationException(e.getMessage(), e);
        }
    }

    private void process(Object object, StringBuilder sb) throws IllegalAccessException, InvocationTargetException {
        this.checkIfSerializable(object);
        this.initXML(object);
        this.convertToString(object, sb);
    }


    private void checkIfSerializable(Object object) throws XMLSerializationException {
        if (object == null) throw new XMLSerializationException("The XML cannot be null ");

        Class<?> clazz = object.getClass();
        if (!clazz.isAnnotationPresent(XMLSerializable.class))
            throw new XMLSerializationException(clazz.getName() + " is not annotated with XMLSerializable");
    }

    private void initXML(Object object) throws IllegalAccessException, InvocationTargetException {
        if (object == null) throw new XMLSerializationException("The XML cannot be null ");

        Class<?> clazz = object.getClass();
        List<Method> methods = new ArrayList<Method>();
        Collections.addAll(methods, clazz.getDeclaredMethods());
        this.addParentMethods(clazz, methods);
        for (Method method : methods) {
            if (method.isAnnotationPresent(XMLInit.class)) {
                method.setAccessible(true);
                method.invoke(object);
            }
        }
    }

    private void addParentMethods(Class<?> clazz, List<Method> methods)
            throws IllegalAccessException, InvocationTargetException {
        Class<?> superclass = clazz.getSuperclass();
        if (superclass.isAnnotationPresent(XMLSerializable.class)) {
            Collections.addAll(methods, superclass.getDeclaredMethods());
            this.addParentMethods(superclass, methods);
        }
    }


    private void convertToString(Object object, StringBuilder sb) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (object == null) throw new XMLSerializationException("The XML cannot be null ");

        Class<?> clazz = object.getClass();
        XMLSerializable xmlSerializable = clazz.getAnnotation(XMLSerializable.class);
        String classKey = "".equals(xmlSerializable.key()) ? clazz.getSimpleName().toUpperCase() : xmlSerializable.key();

        sb.append("<").append(classKey).append(">").append(LINE_SEPARATOR);
        List<Field> fields = new ArrayList<>();
        Collections.addAll(fields, clazz.getDeclaredFields());
        this.addParentFields(clazz, fields);

        for (Field field : fields) {
            if (field.isAnnotationPresent(XMLElement.class)) {
                field.setAccessible(true);
                XMLElement element = field.getAnnotation(XMLElement.class);
                String key = "".equals(element.key()) ? field.getName().toUpperCase() : element.key().toUpperCase();
                Object value = field.get(object);
                sb.append("<").append(key).append(">");
                if (value != null && value.getClass().isAnnotationPresent(XMLSerializable.class)) {
                    sb.append(LINE_SEPARATOR);
                    this.process(value, sb);
                } else {
                    sb.append(Objects.toString(value));
                }
                sb.append("</").append(key).append(">").append(LINE_SEPARATOR);
            }
        }
        sb.append("</").append(classKey).append(">").append(LINE_SEPARATOR);
    }

    private void addParentFields(Class<?> clazz, List<Field> fields) throws IllegalAccessException, InvocationTargetException {
        Class<?> superclass = clazz.getSuperclass();
        if (superclass.isAnnotationPresent(XMLSerializable.class)) {
            Collections.addAll(fields, superclass.getDeclaredFields());
            addParentFields(superclass, fields);
        }
    }
}