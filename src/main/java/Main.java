import core.ApplicationContext;
import helper.Helper;

class Main {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new ApplicationContext(Helper.BASE_PATH);
        try {
            applicationContext.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
