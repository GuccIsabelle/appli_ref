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
     * Checks if the Class is up to BLTi standards.
     * If yes, adds it to the list.
     *
     * @param serviceToAdd Why bother ?
     * @return The stat of the adding.
     * @throws addServiceException The criterion the class does not meet.
     */
    public static boolean addService(Class<?> serviceToAdd) throws addServiceException {
        isOK(serviceToAdd);
        return services.add(serviceToAdd);
    }

    /**
     * Again, checks if the Class is up to BLTi standards.
     * Remove the old one and then add the new one.
     * (we pretty much a sure this doesn't work at all...)
     *
     * @param serviceToUpdate You don't want a drawing with it ?
     * @return The stat of the adding.
     * @throws addServiceException The criterion the class does not meet.
     */
    public static boolean updateService(Class<?> serviceToUpdate) throws addServiceException {
        isOK(serviceToUpdate);
        /* kill me if this works */
        services.remove(serviceToUpdate);
        return services.add(serviceToUpdate);
    }

    /**
     * Do I really need to explain this ?
     *
     * @param serviceToUpdate duh
     * @return The stat of the remove.
     */
    public static boolean removeService(Class<?> serviceToUpdate) {
        return services.remove(serviceToUpdate);
    }

    /**
     * Goes through all those nasty if statements
     * to check if the new Service is up to BLTi standards.
     *
     * @param service The service we have to check.
     * @throws addServiceException The criterion the class does not meet.
     */
    public static void isOK(Class<?> service) throws addServiceException {
        int modifiers = service.getModifiers();
        Class[] interfaces = service.getInterfaces();

        if (!checksInterfaces(interfaces)) {
            throw new addServiceException("Class doesn't implements iService");
        }
        if (!Modifier.isPublic(modifiers)) {
            throw new addServiceException("Class isn't public");
        }
        if (Modifier.isAbstract(modifiers)) {
            throw new addServiceException("Class is abstract");
        }
        if (!checksConstructor(service)) {
            throw new addServiceException("Class has no conform constructor");
        }
        if (!checksFields(service.getDeclaredFields())) {
            throw new addServiceException("Class has no Socket field");
        }
        if (!checksPrintingMethod(service.getMethods())) {
            throw new addServiceException("Class has no correct toString equivalent (\"printDescription\" method)");
        }
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
    static class addServiceException extends Exception {
        public addServiceException(String message) {
            super(message);
        }
    }
}
