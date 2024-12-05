package sysc4806.project.productreview;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class ProductReviewApplication {

	public static void main(String[] args) {

		SpringApplication.run(ProductReviewApplication.class, args);
	}

	@Bean
	public CommandLineRunner setupDBData(
			CustomerRepository customerRepository,
			ProductRepository productRepository,
			ReviewRepository reviewRepository) {

		return (args) -> {
			//Customers
			Customer customer1 = new Customer();
			customer1.setName("Bill");
			customer1.setEmail("Bill@gmail.com");
			customer1.setPassword("Bill");
			customerRepository.save(customer1);

			Customer customer2 = new Customer();
			customer2.setName("Jill");
			customer2.setEmail("Jill@gmail.com");
			customer2.setPassword("Jill");
			customer2.addFollowing(customer1);
			customer1.setFollower_Count(customer1.getFollower_Count() + 1);
			customerRepository.save(customer2);

			Customer customer3 = new Customer();
			customer3.setName("Tom");
			customer3.setEmail("Tom@gmail.com");
			customer3.setPassword("Tom");
			customer3.addFollowing(customer1);
			customer1.setFollower_Count(customer1.getFollower_Count() + 1);
			customerRepository.save(customer3);

			//Products
			Product product1 = new Product();
			product1.setName("JetStream® 3 Piece Hardside Luggage Set");
			product1.setCategory("Furniture/Luggage");
			product1.setDescription("https://www.walmart.ca/en/ip/jetstream-3-piece-hardside-luggage-set-cream/6000206846841");
			productRepository.save(product1);

			Product product2 = new Product();
			product2.setName("AirPods Pro (2nd generation) with USB-C");
			product2.setCategory("Technology");
			product2.setDescription("https://www.walmart.ca/en/ip/AirPods-Pro-2nd-generation-with-USB-C/6000206604258");
			productRepository.save(product2);

			Product product3 = new Product();
			product3.setName("Pokémon 24'' Plush - Snorlax");
			product3.setCategory("Toys");
			product3.setDescription("https://www.walmart.ca/en/ip/pokmon-24-plush-snorlax-bluewhite/6000208095332");
			productRepository.save(product3);

			//Reviews
			LocalDateTime now = LocalDateTime.now();
			Review review11 = new Review();
			review11.setReviewText("This is an Amazing, It gets the Highest Recommendation!");
			review11.setStarRating(5);
			review11.setReviewer(customer1);
			review11.setReviewDate(now);
			review11.setProduct(product1);
			reviewRepository.save(review11);

			Review review12 = new Review();
			review12.setReviewText("Like all the other reviews say it is very good!");
			review12.setStarRating(4);
			review12.setReviewer(customer2);
			review12.setReviewDate(now);
			review12.setProduct(product1);
			reviewRepository.save(review12);

			Review review13 = new Review();
			review13.setReviewText("Everyone else must be confusing this for a different product");
			review13.setStarRating(2);
			review13.setReviewer(customer3);
			review13.setReviewDate(now);
			review13.setProduct(product1);
			reviewRepository.save(review13);

			Review review21 = new Review();
			review21.setReviewText("Eh it is ok.");
			review21.setStarRating(3);
			review21.setReviewer(customer1);
			review21.setReviewDate(now);
			review21.setProduct(product2);
			reviewRepository.save(review21);

			Review review22 = new Review();
			review22.setReviewText("Perfect in every way, how else could you describe it! There is nothing finer than this Product on the market!");
			review22.setStarRating(5);
			review22.setReviewer(customer2);
			review22.setReviewDate(now);
			review22.setProduct(product2);
			reviewRepository.save(review22);

			Review review23 = new Review();
			review23.setReviewText("Worth less than the material used to make it.");
			review23.setStarRating(1);
			review23.setReviewer(customer3);
			review23.setReviewDate(now);
			review23.setProduct(product2);
			reviewRepository.save(review23);

			Review review31 = new Review();
			review31.setReviewText("Who is this even for..., I guess it is of ok quality.");
			review31.setStarRating(2);
			review31.setReviewer(customer1);
			review31.setReviewDate(now);
			review31.setProduct(product3);
			reviewRepository.save(review31);

			Review review32 = new Review();
			review32.setReviewText("I mean the material is of good, can't I am that enthused with the rest.");
			review32.setStarRating(3);
			review32.setReviewer(customer2);
			review32.setReviewDate(now);
			review32.setProduct(product3);
			reviewRepository.save(review32);

			Review review33 = new Review();
			review33.setReviewText("Now this is a product you could purchase, though what else it there to say.");
			review33.setStarRating(3);
			review33.setReviewer(customer3);
			review33.setReviewDate(now);
			review33.setProduct(product3);
			reviewRepository.save(review33);
		};
	}

}
