package com.rentalhub.backend

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootTest
class RentalHubBackendApplicationTests {

	@RestController
	@RequestMapping("/api")
	class HelloController {
		@GetMapping("/hello")
		fun hello(): String = "Backend is working 🎉"
	}


}
