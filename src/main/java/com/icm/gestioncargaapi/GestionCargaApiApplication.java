package com.icm.gestioncargaapi;

import com.icm.gestioncargaapi.models.EmpresasModel;
import com.icm.gestioncargaapi.repositories.EmpresasRepository;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class GestionCargaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionCargaApiApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(){
		return new OpenAPI()
				.info(new Info()
						.title("API Gestor de baterias")
						.version("0.11")
						.description("Sample example")
						.termsOfService("http://swagger.io/terms/")
						.license(new License().name("Apache").url("http://springdoc.org"))
				);
	}

	/*
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	EmpresasRepository empresasRepository;

	@Bean
	CommandLineRunner init(){
		return args -> {

			EmpresasModel empresasModel = EmpresasModel.builder()
					.nombre("ICM")
					.username("ICM")
					.password(passwordEncoder.encode("1234"))
					.build();


			empresasRepository.save(empresasModel);

		};
	}
*/
}
