package core;

import annotation.Autowired;
import enums.ApplicationAnnotationTypes;
import helper.Helper;
import org.objectweb.asm.ClassReader;
import scanner.ClassScanner;
import server.ApplicationServer;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationContext implements IApplicationContext{
    private List<String> classes;
    private final Map<String, Map<String, List<String>>> applicationAnnotations = new HashMap<>();
    private final Map<String, Object> beanRegistry = new HashMap<>();

    public ApplicationContext(String basePath) {
        this.loadClasses(basePath);
    }

    public void run() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException, IOException {
        loadPackages();
        createBeans();
        injectBeans();
        ApplicationServer.runServer(8080);
    }

    public void loadPackages() {

        try {
            for (String clazz: classes){
                ClassScanner classScanner = new ClassScanner();
                FileInputStream fileInputStream = new FileInputStream(clazz);
                ClassReader classReader = new ClassReader(fileInputStream);
                classReader.accept(classScanner, 0);

                if (!classScanner.getClassAnnotations().isEmpty()){
                    applicationAnnotations
                            .computeIfAbsent(clazz, k -> new HashMap<>())
                            .put(ApplicationAnnotationTypes.CLASS_ANNOTATIONS.toString(), classScanner.getClassAnnotations());

                }

                if (!classScanner.getFieldAnnotations().isEmpty()){
                    applicationAnnotations
                            .computeIfAbsent(clazz, k -> new HashMap<>())
                            .put(ApplicationAnnotationTypes.FIELD_ANNOTATIONS.toString(), classScanner.getFieldAnnotations());

                }

            }
        }catch(Exception exception){
            System.out.println(exception.toString());
            System.exit(1);
        }

    }

    public void createBeans() {
        for (Map.Entry<String, Map<String, List<String>>> content : applicationAnnotations.entrySet()) {

            var classAnnotations = content
                    .getValue()
                    .get(ApplicationAnnotationTypes.CLASS_ANNOTATIONS.toString());

            if(classAnnotations != null){
                classAnnotations.stream()
                        .filter(annotation -> annotation.contains("Controller") || annotation.contains("Service"))
                        .forEach(_n -> {
                            String className = Helper.getClassName(content.getKey());
                            Class<?> clazz;
                            try {
                                clazz = Class.forName(className);
                                Object instance = clazz.getDeclaredConstructor().newInstance();
                                beanRegistry.put(className, instance);
                            } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
                                     IllegalAccessException | NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            }
                        });
            }

        }
    }

    public Object getBean(String classPath) {
        return beanRegistry.get(classPath);
    }

    public void injectBeans() throws ClassNotFoundException, IllegalAccessException {
        for (String className : classes) {
            Class<?> clazz = Class.forName(Helper.getClassName(className));

            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)){
                    // this field is an instance of what class?
                    Class<?> fieldType = field.getType();

                    field.setAccessible(true);

                    Object classInstance = getBean(Helper.getClassName(className));

                    field.set(classInstance, getBean(fieldType.getName()));

                    System.out.println("injected " +
                            fieldType.getName() + " into " +
                            Helper.getClassName(className));

                }
            }
        }
    }

    private void loadClasses(String basePath){
        this.classes = Helper.getClasses(basePath);
    }

    public Map<String, Map<String, List<String>>> getApplicationAnnotations() {
        return applicationAnnotations;
    }
}
