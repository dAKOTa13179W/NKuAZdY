// 代码生成时间: 2025-09-08 17:37:40
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views ViewsBundle;
import java.util.Random;

// 程序主类
public class RandomNumberGenerator extends Application<RandomNumberGeneratorConfiguration> {
    // 程序入口点
    public static void main(String[] args) throws Exception {
        new RandomNumberGenerator().run(args);
    }

    @Override
    public void initialize(Bootstrap<RandomNumberGeneratorConfiguration> bootstrap) {
        // 添加资源和提供者到引导阶段
        bootstrap.addBundle(new ViewsBundle<>());
    }

    @Override
    public void run(RandomNumberGeneratorConfiguration configuration, Environment environment) {
        // 注册服务和资源
        environment.jersey().register(new RandomNumberResource());
    }
}

// 配置类
class RandomNumberGeneratorConfiguration extends Configuration {
    // 这里可以添加配置属性
}

// 资源类，提供REST API接口
class RandomNumberResource {
    private final Random random;

    public RandomNumberResource() {
        this.random = new Random();
    }

    // 获取随机数的方法
    public Integer getRandomNumber() {
        return random.nextInt();
    }
}
