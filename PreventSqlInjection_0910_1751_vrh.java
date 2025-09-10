// 代码生成时间: 2025-09-10 17:51:38
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 配置类，负责创建SqlSessionFactory
public class MyBatisConfig {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            // 加载MyBatis配置文件
            String resource = "mybatis-config.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            // 创建SqlSessionFactory
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}

// 继承Mapper接口，定义方法
public interface UserMapper {
    List<User> findAllUsers();
    User findUserById(int id);
    void insertUser(User user);
}

// 实体类
public class User {
    private int id;
    private String name;
    private String email;

    // 省略getter和setter方法
}

// 服务类，实现业务逻辑
public class UserService {
    private UserMapper userMapper;

    public UserService(SqlSessionFactory sqlSessionFactory) {
        // 获取SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 获取mapper接口的代理对象
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    public List<User> getAllUsers() {
        try {
            return userMapper.findAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserById(int id) {
        try {
            return userMapper.findUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addUser(User user) {
        try {
            userMapper.insertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// 主类，执行程序
public class PreventSqlInjection {
    public static void main(String[] args) {
        // 获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = MyBatisConfig.getSqlSessionFactory();

        // 创建UserService对象
        UserService userService = new UserService(sqlSessionFactory);

        // 获取所有用户
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail());
        }

        // 通过ID获取用户
        User user = userService.getUserById(1);
        if (user != null) {
            System.out.println("User found: ID: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail());
        } else {
            System.out.println("User not found");
        }

        // 添加用户
        User newUser = new User();
        newUser.setName("John Doe");
        newUser.setEmail("john.doe@example.com");
        userService.addUser(newUser);
    }
}