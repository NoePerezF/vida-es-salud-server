package com.vidaEsSalud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com")
public class VidaEsSaludApplication {
    
	public static void main(String[] args) {
		SpringApplication.run(VidaEsSaludApplication.class, args);
	}

}
