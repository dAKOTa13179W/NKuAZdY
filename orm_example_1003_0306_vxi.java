// 代码生成时间: 2025-10-03 03:06:22
import io.dropwizard.Application;
import io.dropwizard.configuration.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

// 使用Dropwizard框架定义一个RESTful服务
@Path("/")
public class ORMExample extends Application<ORMExampleConfiguration> {

    // Hibernate Bundle配置
    private final HibernateBundle<ORMExampleConfiguration> hibernate = new HibernateBundle<ORMExampleConfiguration>(
            EntityClass.class) { // 假设EntityClass是我们要操作的实体类
        @Override
        public DataSourceFactory getDataSourceFactory(ORMExampleConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    // 定义资源类
    @Path("/entities")
    public static class EntityResource {
        private final SessionFactory sessionFactory;

        public EntityResource(SessionFactory sessionFactory) {
            // 使用SessionFactory来管理数据库Session
            this.sessionFactory = sessionFactory;
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public List<EntityClass> listEntities() throws Exception {
            // 在事务中获取实体列表
            return currentSession().list("from EntityClass");
        }

        private Session currentSession() {
            // 使用UnitOfWorkAwareProxyFactory来确保当前线程的Session是活动的
            return UnitOfWorkAwareProxyFactory.unitOfWork(sessionFactory)
                    .currentSession();
        }
    }

    // 配置Dropwizard应用
    @Override
    public void initialize(Bootstrap<ORMExampleConfiguration> bootstrap) {
        // 绑定HibernateBundle
        bootstrap.addBundle(hibernate);
    }

    // 运行Dropwizard环境配置
    @Override
    public void run(ORMExampleConfiguration configuration, Environment environment) throws Exception {
        // 将EntityResource注册到环境中
        environment.jersey().register(new EntityResource(hibernate.getSessionFactory()));
    }

    public static void main(String[] args) throws Exception {
        // 启动Dropwizard应用
        new ORMExample().run(args);
    }
}

// 假设的实体类
class EntityClass {
    // 实体类属性和方法...
}

// Dropwizard配置类
class ORMExampleConfiguration extends Configuration {
    // 配置属性和方法...
}
