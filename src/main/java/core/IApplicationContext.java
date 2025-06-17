package core;

public interface IApplicationContext {

    void loadPackages();

    void createBeans();

    void getBean(String beanName);
}
