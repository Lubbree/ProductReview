package sysc4806.project.productreview;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class ProductReviewApplicationTests {

	@Autowired
	private ProductController productController;

	@Test
	void contextLoads() {
		assertThat(productController).isNotNull();
	}

}
