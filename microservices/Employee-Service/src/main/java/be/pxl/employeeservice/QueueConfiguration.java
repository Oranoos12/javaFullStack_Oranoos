package be.pxl.employeeservice;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfiguration {

    @Bean
    public Queue employeeQueue() {
        return new Queue("employee.created", true);
    }
}
