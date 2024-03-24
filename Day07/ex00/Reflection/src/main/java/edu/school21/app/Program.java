package edu.school21.app;

import edu.school21.exception.UnsupportedTypeException;
import edu.school21.reflector.ClassFinder;
import edu.school21.reflector.ClassReflector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Program {
    public static final String CLASSES_PACKAGE = "edu.school21.classes";

    public static final Map<String, Method> methodMap = new HashMap<>();

    public static void main(String[] args) {
        try (Scanner inp = new Scanner(System.in)) {
            printClasses();
            System.out.println("Enter class name:");
            String className = inp.nextLine();
            Class<?> selectedClass = Class.forName(CLASSES_PACKAGE +
                    '.' + className);
            printSeparator();
            printFields(selectedClass);
            printMethods(selectedClass);
            printSeparator();
            Object createdObject =
                    ClassReflector.createObject(selectedClass, inp);
            printSeparator();
            ClassReflector.changeField(createdObject, inp);
            ClassReflector.callObjectMethod(createdObject, methodMap, inp);
        } catch (ClassNotFoundException e) {
            printError("Wrong class name! Class " + e.getMessage()
                    + " does not exist");
        } catch (InvocationTargetException e) {
            printError("Wrong constructor or method argument");
        } catch (InstantiationException e) {
            printError("Can't create instance");
        } catch (NoSuchFieldException e) {
            printError("Field " + e.getMessage() + " does not exist");
        } catch (NoSuchMethodException e) {
            printError("Method " + e.getMessage() + " does not exist");
        } catch (IllegalAccessException e) {
            printError("Can't work with field/method. Access denied!");
        } catch (InputMismatchException e) {
            printError("Wrong input data");
        } catch (UnsupportedTypeException e) {
            printError(e.getMessage());
        }
    }

    public static void printClasses() {
        System.out.println("Classes:");
        for (Class<?> currentClass : ClassFinder.getClasses(CLASSES_PACKAGE)) {
            System.out.println("\t - " + currentClass.getSimpleName());
        }
        printSeparator();
    }

    public static void printSeparator() {
        System.out.println("---------------------");
    }

    public static void printFields(Class<?> selectedClass) {
        System.out.println("fields:");
        for (Field field : selectedClass.getDeclaredFields()) {
            System.out.println("\t\t" + field.getType().getSimpleName() +
                    " " + field.getName());
        }
    }

    public static void printMethods(Class<?> selectedClass) {
        methodMap.clear();
        System.out.println("methods:");
        for (Method method : selectedClass.getDeclaredMethods()) {
            String methodString = buildMethodString(method);
            methodMap.put(methodString, method);
            System.out.println("\t\t" + method.getReturnType().getSimpleName() +
                    " " + methodString);
        }
    }


    public static void printError(String message) {
        System.out.println(message);
        System.exit(-1);
    }

    private static String buildMethodString(Method method) {
        StringBuilder builder = new StringBuilder();
        builder.append(method.getName())
                .append('(');
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            builder.append(parameters[i].getType().getSimpleName());
            if (i != parameters.length - 1) {
                builder.append(", ");
            }
        }
        builder.append(')');
        return builder.toString();
    }
}