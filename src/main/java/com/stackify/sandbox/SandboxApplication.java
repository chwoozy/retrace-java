package com.stackify.sandbox;

import com.stackify.api.common.log.direct.LogManager;
import com.stackify.api.common.log.direct.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SandboxApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SandboxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
                //Logger.queueMessage("info", "Test startup");
                //LogManager.shutdown();
	}
}
