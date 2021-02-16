package org.mik.FirstMaven.export.json;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class JsonGenerator {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public String convertToJson(Object object) throws JsonSerializationException {
        try{
            StringBuilder sb = new StringBuilder().append(LINE_SEPARATOR);
            this.process(object,sb);
            return sb.toString();

        }
        catch(Exception e){
            throw new JsonSerializationException(e.getMessage(), e);
        }
    }

    private void process(Object object, StringBuilder stringBuilder) throws IllegalAccessException, InvocationTargetException {
        this.checkIfSerializable(object);
        this.initJson(object);
        this.convertToString(object,stringBuilder);
    }

    private void checkIfSerializable(Object object) throws JsonSerializationException {
        if(object == null) throw new JsonSerializationException("Json cannot be null");

        Class<?> clazz = object.getClass();

        if(!clazz.isAnnotationPresent(JsonSerializable.class))
            throw new JsonSerializationException(clazz.getName()+" is not annotated with JsonSerializable ");
    }

    private void initJson(Object object) throws IllegalAccessException, InvocationTargetException {
        if(object == null) throw new JsonSerializationException("Json cannot be null");

        Class<?> clazz = object.getClass();
        List<Method> methods = new ArrayList<Method>();
        Collections.addAll(methods, clazz.getDeclaredMethods());
        this.addParentMethods(clazz, methods);
        for (Method method : methods) {
            if (method.isAnnotationPresent(JsonInit.class)) {
                method.setAccessible(true);
                method.invoke(object);
            }
        }
    }


    private void addParentMethods(Class<?> clazz, List<Method> methods)
            throws IllegalAccessException, InvocationTargetException {
        Class<?> superclass = clazz.getSuperclass();
        if (superclass.isAnnotationPresent(JsonSerializable.class)) {
            Collections.addAll(methods, superclass.getDeclaredMethods());
            this.addParentMethods(superclass, methods);
        }
    }


    private void convertToString(Object object, StringBuilder sb)
            throws IllegalArgumentException,
            IllegalAccessException,
            InvocationTargetException {

        if (object == null)
            throw new JsonSerializationException("Json cannot be null ");

        Class<?> clazz = object.getClass();

        JsonSerializable jsonSerializable = clazz.getAnnotation(JsonSerializable.class);
        String classKey = "".equals(jsonSerializable.key())
                ? clazz.getSimpleName().toUpperCase() : jsonSerializable.key();

        sb.append("{ \"").append(classKey).append("\" : {").append(LINE_SEPARATOR);
        List<Field> fields = new ArrayList<>();
        Collections.addAll(fields, clazz.getDeclaredFields());
        this.addParentFields(clazz, fields);

        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            if (field.isAnnotationPresent(JsonElement.class)){
                field.setAccessible(true);
                JsonElement element = field.getAnnotation(JsonElement.class);
                String key = "".equals(element.key()) ? field.getName().toUpperCase() : element.key().toUpperCase();
                Object value = field.get(object);
                sb.append("\"").append(key).append("\" :");
                if (value != null && value.getClass().isAnnotationPresent(JsonSerializable.class)) {
                    sb.append(LINE_SEPARATOR);
                    this.process(value, sb);
                }
                else {
                    sb.append("\"").append(Objects.toString(value)).append("\"");
                }

                if(i<fields.size()-1){
                    sb.append(',');
                }
                sb.append(LINE_SEPARATOR);
            }
        }
        sb.append("}").append(LINE_SEPARATOR).append('}').append(LINE_SEPARATOR);
    }

    private void addParentFields(Class<?> clazz, List<Field> fields) {
        Class<?> superClass = clazz.getSuperclass();
        if (superClass.isAnnotationPresent(JsonSerializable.class)) {
            Collections.addAll(fields, superClass.getDeclaredFields());
            addParentFields(superClass, fields);
        }
    }


}
