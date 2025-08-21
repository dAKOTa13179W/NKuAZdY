// 代码生成时间: 2025-08-22 05:51:57
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import java.util.Arrays;
import java.util.Comparator;

public class SortingAlgorithmApplication extends Application<SortingAlgorithmConfiguration> {

    /*
    * 主方法，程序入口
    */
    public static void main(String[] args) throws Exception {
        new SortingAlgorithmApplication().run(args);
    }

    /*
    * 配置类的初始化方法
    */
    @Override
    public String getName() {
        return "SortingAlgorithm";
    }

    /*
    * 实例化配置类
    */
    @Override
    public void initialize(Bootstrap<SortingAlgorithmConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle<>());
    }

    /*
    * 程序启动后运行的方法
    */
    @Override
    public void run(SortingAlgorithmConfiguration configuration, Environment environment) {
        try {
            int[] numbers = configuration.getNumbers();
            Arrays.sort(numbers); // 默认升序排序
            System.out.println("Sorted Numbers: " + Arrays.toString(numbers));
            
            // 降序排序
            Arrays.sort(numbers, Comparator.reverseOrder());
            System.out.println("Reverse Sorted Numbers: " + Arrays.toString(numbers));
        } catch (Exception e) {
            System.err.println("Error occurred while sorting: " + e.getMessage());
        }
    }
}

/*
* 配置类
*/
class SortingAlgorithmConfiguration extends io.dropwizard.Configuration {
    private int[] numbers;

    /*
    * Getter 方法
    */
    public int[] getNumbers() {
        return numbers;
    }

    /*
    * Setter 方法
    */
    public void setNumbers(int[] numbers) {
        this.numbers = numbers;
    }
}
