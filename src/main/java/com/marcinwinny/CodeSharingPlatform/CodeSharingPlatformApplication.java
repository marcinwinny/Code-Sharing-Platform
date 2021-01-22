package com.marcinwinny.CodeSharingPlatform;

import com.marcinwinny.CodeSharingPlatform.model.Code;
import com.marcinwinny.CodeSharingPlatform.repository.CodeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CodeSharingPlatformApplication {

	@Bean
	public CommandLineRunner runApplication(CodeRepository codeRepository) {
		return (args -> {
//            codeRepository.save(new Code("console.log(Hello world!)"));
//            codeRepository.save(new Code("public static void main(String[] args) {\n SpringApplication.run(CodeSharingPlatform.class, args);\n}"));
//            codeRepository.save(new Code("private int x = 0;\nx++;\nSystem.out.println(x)"));
//            codeRepository.save(new Code("console.log(Hello world!)"));
//            codeRepository.save(new Code("public static void main(String[] args) {\n SpringApplication.run(CodeSharingPlatform.class, args);\n}"));
//            codeRepository.save(new Code("private int x = 0;\nx++;\nSystem.out.println(x)"));
//            codeRepository.save(new Code("console.log(Hello world!)"));
//            codeRepository.save(new Code("public static void main(String[] args) {\n SpringApplication.run(CodeSharingPlatform.class, args);\n}"));
//            codeRepository.save(new Code("private int x = 0;\nx++;\nSystem.out.println(x)"));
//            codeRepository.save(new Code("console.log(Hello world!)"));
//            codeRepository.save(new Code("public static void main(String[] args) {\n SpringApplication.run(CodeSharingPlatform.class, args);\n}"));
//            codeRepository.save(new Code("private int x = 0;\nx++;\nSystem.out.println(x)"));

		});
	}

	public static void main(String[] args) {
		SpringApplication.run(CodeSharingPlatformApplication.class, args);
	}

}
