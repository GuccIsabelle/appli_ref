package server.BRi;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("rawtypes")
public class ServiceRegistry {
    private static List<Class<?>> services;

    /**
     * ~ STATIC BLOCK ~
     *
     * initiate the synchronized List into a Collection
     * from an empty ArrayList.
     *
     * Once upon a time, a professor told us to use Vector...
     * But like when programming in Rust, I seek efficiency.
     * And Java's Vectors come nowhere near Rust's Vectors
     * when we talk raw performance.
     * So screw that guy, I'm going full Collections.
     */
    static {
        services = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * Goes through all those nasty if statements
     * to check if the new Service is up to BLTi standards.
     *
     * @param serviceToAdd The service we want to add.
     * @throws addServiceException The criterion the class does not meet.
     */
    public static void addService(Class<?> serviceToAdd) throws addServiceException {
        int modifiers = serviceToAdd.getModifiers();
        Class[] interfaces = serviceToAdd.getInterfaces();

        if (!checksInterfaces(interfaces)) {
            throw new addServiceException("Class doesn't implements iService");
        }
        if (!Modifier.isPublic(modifiers)) {
            throw new addServiceException("Class isn't public");
        }
        if (Modifier.isAbstract(modifiers)) {
            throw new addServiceException("Class is abstract");
        }
        if (!checksConstructor(serviceToAdd)) {
            throw new addServiceException("Class has no conform constructor");
        }
        if (!checksFields(serviceToAdd.getDeclaredFields())) {
            throw new addServiceException("Class has no Socket field");
        }
        if (!checksPrintingMethod(serviceToAdd.getMethods())) {
            throw new addServiceException("Class has no correct toString equivalent (\"printDescription\" method)");
        }
        services.add(serviceToAdd);
    }

    /**
     * Return the Class from the given index.
     * "-1" bc we started at "1" for human convenience.
     *
     * @param index Service's index (from 1 to n).
     * @return The corresponding Class.
     */
    public static Class<?> getServiceFromIndex(int index) {
        return services.get(index - 1);
    }

    /**
     * Like its name implies, it converts the List
     * into a nice looking String.
     * (all `return` will be later replace by true return)
     *
     * @return The prettified String
     * @throws NoSuchMethodException u know the things
     */

    public static String printServicesList() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        StringBuilder result = new StringBuilder("Available services :`return`");
        int index = 1;
        Method method;
        for (Class<?> c : services) {
            result.append(String.format("  %-3d -> name: %s, description: %s`return`", index++, c.getSimpleName(), c.getMethod("printDescription").invoke(c)));
        }
        return result.toString();
    }

    /**
     * Tries to get the constructor:
     * if it fails    -> false
     * if it succeeds -> true
     *
     * @param c Service's Class.
     * @return True or False.
     */
    private static boolean checksConstructor(Class<?> c) {
        try {
            c.getConstructor(Socket.class);
        } catch (NoSuchMethodException e) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the Service implements the needed Interface:
     * if none at all      -> false
     * if he implements it -> true
     * if none is correct  -> false
     *
     * @param interfaces Service's interfaces List.
     * @return True or False.
     */
    private static boolean checksInterfaces(Class[] interfaces) {
        if (interfaces.length > 0)
            for (Class<?> c : interfaces)
                if (c.equals(iService.class))
                    return true;
        return false;
    }

    /**
     * Checks if the Service has the needed field:
     * if none at all     -> false
     * if he has it       -> true
     * if none is correct -> false
     *
     * @param fields Service's field(s) List.
     * @return True or False.
     */
    private static boolean checksFields(Field[] fields) {
        if (fields.length > 0)
            for (Field f : fields)
                if (f.getType().equals(Socket.class))
                    return true;
        return false;
    }

    /**
     * Checks if the Service has the needed method:
     * if none at all     -> false
     * if he has it       -> true
     * if none is correct -> false
     *
     * @param methods Service's method(s) List.
     * @return True or False.
     */
    private static boolean checksPrintingMethod(Method[] methods) {
        if (methods.length > 0)
            for (Method m : methods)
                /* we make sure the name is correct AND also the return type */
                if (m.getName().equals("printDescription") && m.getReturnType().equals(String.class))
                    return true;
        return false;
    }

    /* auto-generated */
    private static class addServiceException extends Exception {
        public addServiceException(String message) {
            super(message);
        }
    }
}
