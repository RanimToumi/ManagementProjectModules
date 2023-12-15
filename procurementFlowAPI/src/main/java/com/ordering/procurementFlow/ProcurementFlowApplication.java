package com.ordering.procurementFlow;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configurable
@RestController
@SpringBootApplication
public class ProcurementFlowApplication {


	public static void main(String[] args) {
		SpringApplication.run(ProcurementFlowApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:4200") // Remplacez http://localhost:4200 par l'URL de votre application Angular
						.allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")
						.allowedHeaders("*")

						.allowCredentials(true);
			}
		};

	}

	/*
	@Bean
	public CommandLineRunner commandLineRunner(AuthenticationService service){
		return args -> {
			var admin= RegisterRequest.builder()
					.firstname("Admin")
					.lastname("Admin")
					.email("admin@gmail.com")
					.password("password")
					.role(ADMIN)
					.build();
			System.out.println("Admin token: "+service.register(admin).getToken());


			var procurementOfficer= RegisterRequest.builder()
					.firstname("procurementOfficer")
					.lastname("procurementOfficer")
					.email("procurementOfficer@gmail.com")
					.password("password")
					.role(PROCUREMENT_OFFICER)
					.build();
			System.out.println("procurementOfficer token: "+service.register(procurementOfficer).getToken());

			var authenticationRequest=AuthenticationRequest.builder()
					.email("admin@gmail.com")
					.password("password")
					.build();



		}

	}*/

}
