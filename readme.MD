# Spring Boot 整合ActiveMQ的过程

1. 安装ActiveMQ服务器，（也可以不安装，如果不安装，会使用内存mq）
2. 构建Spring boot项目，增加依赖项，只需要添加这一项即可
```xml
<!-- 添加acitivemq依赖 -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-activemq</artifactId>
</dependency>
```
3. 增加Application类
```java
@SpringBootApplication
@EnableScheduling //使用定时任务发送消息
public class MqTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(MqTestApplication.class, args);
    }
}
```
4. 配置application.yml
```yaml
spring:
  activemq:
    broker-url: tcp://127.0.01:61616
    packages:
      trust-all: true
```

5. 构建一个数据Model,可以发送和消费的数据类型有: String, byte array, Map<tring,?>, Serializable object.
```java
// 如果发送的消息是一个对象，必须implements Serializable接口
public class TModel implements Serializable {
    private static final long serialVersionUID = -921008687184331557L;

    private int count;

    public TModel(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "TModel [count=" + count + "]";
    }

}
```
6. 构建Producer
```java
@Component
public class Producer {
    // 在Producer中注入JmsTemplate，我们可以通过这个template发送消息
    private final JmsTemplate jmsTemplate;
    private int count = 0;

    @Autowired
    public Producer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    // 这里使用Spring Boot的定时任务发送消息
    @Scheduled(fixedRate = 1000)
    public void create() {
        // 使用convertAndSend发送消息
        jmsTemplate.convertAndSend("queue1", new TModel(count++));
    }
}
```
7. 构建Consumer
```java
@Component
public class Consumer {
    @JmsListener(destination = "queue1")
    public void comsume(TModel content) {
        System.out.println("recive message from queue1 [" + content + "]");
    }
}
```


特别备注：如果我们的生产者和消费者在不同的Module中时，最好将要消费的数据抽象成公共Module.程序是通过Serializable来序列化和反序列化对象的。必须保证生产者和消费者的对象模型的serialVersionUID是一致的。
