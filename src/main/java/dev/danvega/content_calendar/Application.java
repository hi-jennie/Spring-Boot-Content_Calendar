package dev.danvega.content_calendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
//		SpringApplication.run(Application.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		// spring will use the method name as the bean name by default
		RestTemplate restTemplate = (RestTemplate) context.getBean("restTemplate");
		// get bean by class but is not recommended because it is not type safe when there are multiple beans of the same type
		RestTemplate restTemplate2 = context.getBean(RestTemplate.class);
		System.out.println(restTemplate);
//		Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
	}

}
