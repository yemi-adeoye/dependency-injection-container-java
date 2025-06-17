package helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Helper {

    public List<String> getClasses(String baseDir){
        List<String> classes = new ArrayList<String>();
        _getClasses(baseDir, classes);
        return classes;
    }

    public void _getClasses(String baseDir, List<String> classes){
        try {
            File dir = new File(baseDir); ;
            for (File file: Objects.requireNonNull(dir.listFiles())){
                if(file.isDirectory()){
                    _getClasses(file.getAbsolutePath(), classes);
                }else{
                    if(file.getName().endsWith(".class")){
                        classes.add(file.getAbsolutePath());
                    }
                }
            }
        }catch(NullPointerException exception){
            System.out.println("looks like a directory passed is not valid");
        }



    }
}
