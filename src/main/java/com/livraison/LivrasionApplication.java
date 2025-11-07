package com.livraison;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
public class LivrasionApplication {
    public static void main(String[] args) {
        loadEnv();
        SpringApplication.run(LivrasionApplication.class, args);
    }

    private static void loadEnv(){
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(entry -> {
            if(entry.getKey().equalsIgnoreCase("ACTIVE_PROFILE")){
                Dotenv env = Dotenv.configure().filename(".env."+entry.getValue()).ignoreIfMissing().load();
                env.entries().forEach(entry1 -> {
                   System.setProperty(entry1.getKey(), entry1.getValue());
                });
            }
            System.setProperty(entry.getKey(), entry.getValue());
        });
    }
}
