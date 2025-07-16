package helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Helper {
    private static final String ANNOTATION_DIR_EXCLUDE = "annotation";
    private static final String CLASS_EXT = ".class";
    public static  String BASE_PATH = "C:\\code-library\\dependency-injection-container-java\\target\\classes";

    public static List<String> getClasses(String baseDir){
        List<String> classes = new ArrayList<>();
        _getClasses(baseDir, classes);
        return classes;
    }

    public static String getClassName(String absoluteFileName){
        return absoluteFileName
                .replace(BASE_PATH, "")
                .replace("\\", ".")
                .replace(CLASS_EXT, "")
                .replaceFirst(".", "");

    }

    private static void _getClasses(String baseDir, List<String> classes){
        try {
            File dir = new File(baseDir); ;
            for (File file: Objects.requireNonNull(dir.listFiles())){
                if(file.isDirectory() && !file.getName().equals(ANNOTATION_DIR_EXCLUDE)){
                    _getClasses(file.getAbsolutePath(), classes);
                }else{
                    if(file.getName().endsWith(CLASS_EXT)){
                        classes.add(file.getAbsolutePath());
                    }
                }
            }
        }catch(NullPointerException exception){
            System.out.println("looks like a directory passed is not valid");
        }



    }
}
