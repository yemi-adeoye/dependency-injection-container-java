package core;

import enums.ApplicationAnnotationTypes;
import helper.Helper;
import org.objectweb.asm.ClassReader;
import scanner.ClassScanner;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationContext {
    private List<String> classes;
    private final Map<String, Map<String, List<String>>> applicationAnnotations = new HashMap<>();

    public ApplicationContext(String basePath) {
        this.loadClasses(basePath);
    }

    public void run() {
        loadPackages();
        createBeans();
        getBean("Any");
    }

    public void loadPackages() {

        try {
            for (String clazz: classes){
                ClassScanner classScanner = new ClassScanner();
                FileInputStream fileInputStream = new FileInputStream(clazz);
                ClassReader classReader = new ClassReader(fileInputStream);
                classReader.accept(classScanner, 0);

                applicationAnnotations
                        .computeIfAbsent(clazz, k -> new HashMap<>())
                        .put(ApplicationAnnotationTypes.CLASS_ANNOTATIONS.toString(), classScanner.getClassAnnotations());

                applicationAnnotations
                        .computeIfAbsent(clazz, k -> new HashMap<>())
                        .put(ApplicationAnnotationTypes.FIELD_ANNOTATIONS.toString(), classScanner.getFieldAnnotations());

            }
        }catch(Exception exception){
            System.out.println(exception.toString());
            System.exit(1);
        }

    }

    public void createBeans() {
        System.out.println("Creating beans");
    }

    public void getBean(String beanName) {
        System.out.println("Fetching bean " + beanName);
    }

    private void loadClasses(String basePath){
        Helper helper = new Helper();

        this.classes = helper.getClasses(basePath);
    }

    public Map<String, Map<String, List<String>>> getApplicationAnnotations() {
        return applicationAnnotations;
    }
}
