package cheboksarov.blps_lab4.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic doBetRequestTopic(){
        return new NewTopic(
                "do_bet_request_topic",
                3,
                (short) 1
        );
    }
}
