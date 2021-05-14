package edu.neu.info6205.helper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * a tool use to parse .properties file
 *
 * @author Joseph Yuanhao Li
 * @date 4/10/21 14:46
 */
public class ConfigParser {
    public static Properties parseConfig(String filePath) {
        String f = "src/main/resources/" + filePath;
        Properties props = new Properties();

        try{
            props.load(new java.io.FileInputStream(f));
       }catch (IOException e){
            e.printStackTrace();
            return null;
        }

        return props;
    }

    public static Object configToClass(String filePath, final Class<?> tClass){
        Properties props = parseConfig(filePath);

        Object objectClass = null;
        try {
            objectClass = tClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Method[] methods = tClass.getDeclaredMethods();
        for (Method method : methods) {
            String name = method.getName();
            if (name.startsWith("set")) {
                String key = name.substring(3, 4).toLowerCase() + name.substring(4);
                String property = props.getProperty(key);
//                System.out.printf("key = %s, property = %s\n", key, property);

                // use the value in props to set the field, if not, use the default value
                if (property != null) {
                    Class<?>[] parameterType = method.getParameterTypes();
                    String simpleName = parameterType[0].getSimpleName();
                    Object propertyValue = null;

//                    System.out.printf("simpleName = %s, property = %s\n", simpleName, property);

                    if (simpleName.equalsIgnoreCase("String")) {
                        propertyValue = property;
                    } else if (simpleName.equalsIgnoreCase("int")) {
                        propertyValue = Integer.parseInt(property);
                    } else if (simpleName.equalsIgnoreCase("double")) {
                        propertyValue = Double.parseDouble(property);
                    } else if (simpleName.equalsIgnoreCase("long")) {
                        propertyValue = Long.parseLong(property);
                    } else if (simpleName.equalsIgnoreCase("float")) {
                        propertyValue = Float.parseFloat(property);
                    } else if (simpleName.equalsIgnoreCase("boolean")) {
                        propertyValue = Boolean.parseBoolean(property);
                    }

                    //invoke set methods to set the instance fields
                    try {
                        method.invoke(objectClass, new Object[]{propertyValue});
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return objectClass;
    }

    // use reflection to print object
    public static List<String> printObject(Object object) {

        List<String> logs = new ArrayList<>();

        logs.add(object.getClass().toString());
        System.out.println("/**********   " + object.getClass() + "   **********/");

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field filed : fields) {
            try {
                filed.setAccessible(true);

                logs.add(filed.getName() + "," + filed.get(object));
                System.out.println(filed.getName() + " : " + filed.get(object));

                filed.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        System.out.println();

        return logs;
    }
}

