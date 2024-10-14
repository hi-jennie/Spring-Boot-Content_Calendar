package dev.danvega.content_calendar;

import dev.danvega.content_calendar.model.Content;
import dev.danvega.content_calendar.model.Status;
import dev.danvega.content_calendar.model.Type;
import dev.danvega.content_calendar.repository.ContentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
//		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
//		// spring will use the method name as the bean name by default
//		RestTemplate restTemplate = (RestTemplate) context.getBean("restTemplate");
        // get bean by class but is not recommended because it is not type safe when there are multiple beans of the same type
//		RestTemplate restTemplate2 = context.getBean(RestTemplate.class);
//		System.out.println(restTemplate);
//		Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
    }

    // 使用这样的方式来初始化数据（删掉了schema.sql中Insert Data的部分）
    @Bean
    CommandLineRunner runner(ContentRepository repository) {
        return args -> {
            Content content = new Content(null,
                    "title",
                    "description",
                    Status.IDEA,
                    Type.VIDEO,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(1),
                    "http://www.baidu.com");
            repository.save(content);
            System.out.println("hello, world1");
        };
    }
    // 为什么没有调用runner()方法？还是执行了CommandLineRunner的run()方法？
    // 当 Spring Boot 应用启动时，Spring 会自动扫描所有实现了 CommandLineRunner 接口的 Bean 并调用它们的 run() 方法。
    // 这也是为什么DataLoader类仍然会被 Spring 扫描到并执行的原因。
}
