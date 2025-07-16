package core;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface IApplicationContext {

    void loadPackages();

    void createBeans();

    void injectBeans() throws ClassNotFoundException, IllegalAccessException;

    void run() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException, IOException;

    Object getBean(String beanName);
}
