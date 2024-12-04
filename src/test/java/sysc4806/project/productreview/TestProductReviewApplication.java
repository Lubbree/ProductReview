package sysc4806.project.productreview;

import org.springframework.boot.SpringApplication;

public class TestProductReviewApplication {

	public static void main(String[] args) {
		SpringApplication.from(ProductReviewApplication::main).with(TestcontainersConfiguration.class).run(args);
	}
}
