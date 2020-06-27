package com.yc.projects.ibike.config;



import com.mongodb.MongoClient;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;


@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages="com.yc")
public class AppConfig {

    private Logger log=Logger.getLogger(AppConfig.class);


    @Bean //键是字符串  值为对象
    public RedisTemplate redsiTemplate(JedisConnectionFactory conn) {
        RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
        template.setConnectionFactory(conn);
        template.afterPropertiesSet();
        return template;
    }

    @Bean   //键是字符串  值为字符串
    public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory conn) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(conn);
        template.afterPropertiesSet();
        return template;

    }


    @Bean    //  MongoTemplate由spring 托管
    @Primary
    public MongoTemplate template() {
        return new MongoTemplate(factory());
    }

    /**
     * 功能描述: 创建数据库名称对应的工厂，数据库名称可以通过配置文件导入
     * @param
     * @return:org.springframework.data.mongodb.MongoDbFactory
     * @since: v1.0
     */
    @Bean("mongoDbFactory")
    public MongoDbFactory factory() {
        return new SimpleMongoDbFactory(client(), "ibike");
    }

    /**
     * 功能描述: 配置client，client中传入的ip和端口可以通过配置文件读入
     *
     * @param
     * @return:com.mongodb.MongoClient
     */
    @Bean("mongoClient")
    public MongoClient client() {
        return new MongoClient("192.168.111.200", 27017);
    }



    @Bean  //获取数据源
    public DriverManagerDataSource dataSource(){
        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/ibike?serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("a");
        log.info("创建数据源"+dataSource);
        return dataSource;
    }

    @Bean
    @Autowired
    public DataSourceTransactionManager tx(DriverManagerDataSource ds    ){
        log.info("创建事务管理器"+ds);
        DataSourceTransactionManager dtm=new DataSourceTransactionManager();
        AnnotationTransactionAspect.aspectOf().setTransactionManager(dtm);
        dtm.setDataSource(   ds );
        return dtm;
    }



}
