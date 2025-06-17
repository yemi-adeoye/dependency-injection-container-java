import core.ApplicationContext;

import java.io.IOException;
import java.util.List;
import java.util.Map;


class Main {
    public static void main(String[] args) throws IOException {
        String BASE_PATH = "D:\\code-library\\reflection-annotation-di-cc\\target\\classes";
        ApplicationContext applicationContext = new ApplicationContext(BASE_PATH);
        applicationContext.run();

        Map<String, Map<String, List<String>>> allAnnotations = applicationContext.getApplicationAnnotations();

        for (Map.Entry<String, Map<String, List<String>>> content : allAnnotations.entrySet() ){
            String[] readableClassNameArr = content.getKey().split("\\\\");
            String readableClassName = readableClassNameArr[readableClassNameArr.length - 1];
            System.out.println(readableClassName + "\n");

            for(Map.Entry<String, List<String>> annotations: content.getValue().entrySet()){
                System.out.println("\t" + annotations.getKey());
                System.out.println(" ");

                for(String annotation: annotations.getValue()){
                    System.out.println("\t" + annotation);
                }
                System.out.println(" ");
            }
        }
        System.out.println();
    }
}



