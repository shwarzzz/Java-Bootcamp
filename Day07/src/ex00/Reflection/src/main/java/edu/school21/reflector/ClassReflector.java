package edu.school21.reflector;

import edu.school21.exception.UnsupportedTypeException;

import java.lang.reflect.*;
import java.util.Map;
import java.util.Scanner;

public class ClassReflector {
    public ClassReflector() {
    }

    public static Object createObject(Class<?> selectedClass, Scanner inp)
            throws InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        System.out.println("Let’s create an object.");
        Constructor<?> classConstructor = selectedClass
                .getConstructor(getFieldsClasses(selectedClass));
        Field[] fields = selectedClass.getDeclaredFields();
        Object[] values = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i].getName() + ":");
            values[i] = scanField(inp, fields[i].getType());
        }
        Object result = classConstructor.newInstance(values);
        System.out.println("Object created: " + result);
        return result;
    }

    public static void changeField(Object classInstance, Scanner inp)
            throws NoSuchFieldException, IllegalAccessException {
        System.out.println("Enter name of the field for changing:");
        String field = inp.nextLine();
        Field changedField = classInstance.getClass().getDeclaredField(field);
        System.out.println("Enter " + changedField.getType().getSimpleName()
                + " value:");
        Object value = scanField(inp, changedField.getType());
        changedField.setAccessible(true);
        changedField.set(classInstance, value);
        System.out.println("Object updated: " + classInstance);
    }

    public static void callObjectMethod(Object classInstance,
                                        Map<String, Method> methodMap, Scanner inp)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object returnedValue;
        System.out.println("Enter name of the method for call:");
        String methodName = inp.nextLine();
        if (!methodMap.containsKey(methodName)) {
            throw new NoSuchMethodException(methodName);
        }
        Method calledMethod = methodMap.get(methodName);
        calledMethod.setAccessible(true);
        if (calledMethod.getParameterCount() > 0) {
            Object[] args = new Object[calledMethod.getParameterCount()];
            Parameter[] parameters = calledMethod.getParameters();
            for (int i = 0; i < calledMethod.getParameterCount(); i++) {
                System.out.println("Enter " + parameters[i].getType()
                        .getSimpleName() + " value:");
                args[i] = scanField(inp, parameters[i].getType());
            }
            returnedValue = calledMethod.invoke(classInstance, args);
        } else {
            returnedValue = calledMethod.invoke(classInstance);
        }
        if (returnedValue != null) {
            System.out.println("Method returned:");
            System.out.println(returnedValue);
        }
    }

    private static Object scanField(Scanner inp, Type valueType) {
        Object result;
        if (valueType.equals(Integer.class)
                || valueType.equals(Integer.TYPE)) {
            result = inp.nextInt();
            inp.nextLine();
        } else if (valueType.equals(Double.class)
                || valueType.equals(Double.TYPE)) {
            result = inp.nextDouble();
            inp.nextLine();
        } else if (valueType.equals(Boolean.class)
                || valueType.equals(Boolean.TYPE)) {
            result = inp.nextBoolean();
            inp.nextLine();
        } else if (valueType.equals(Long.class)
                || valueType.equals(Long.TYPE)) {
            result = inp.nextLong();
            inp.nextLine();
        } else if (valueType.equals(String.class)) {
            return inp.nextLine();
        } else {
            throw new UnsupportedTypeException("Type " + valueType.getTypeName()
                    + " unsupported");
        }
        return result;
    }

    private static Class<?>[] getFieldsClasses(Class<?> selectedClass) {
        Field[] fields = selectedClass.getDeclaredFields();
        Class<?>[] fieldsClasses = new Class<?>[fields.length];
        for (int i = 0; i < fieldsClasses.length; i++) {
            fieldsClasses[i] = fields[i].getType();
        }
        return fieldsClasses;
    }
}
