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
			customer1.setFollower_Count(customer1.getFollower_Count() + 1);
			customerRepository.save(customer1);

			Customer customer2 = new Customer();
			customer2.setName("Jill");
			customer2.setEmail("Jill@gmail.com");
			customer2.setPassword("Jill");
			customer2.addFollowing(customer1);
			customer2.setFollower_Count(customer1.getFollower_Count() + 3);
			customerRepository.save(customer2);

			Customer customer3 = new Customer();
			customer3.setName("Tom");
			customer3.setEmail("Tom@gmail.com");
			customer3.setPassword("Tom");
			customer3.addFollowing(customer2);
			customer3.setFollower_Count(customer1.getFollower_Count() + 2);
			customerRepository.save(customer3);

			Customer customer4 = new Customer();
			customer4.setName("Jack");
			customer4.setEmail("Jack@gmail.com");
			customer4.setPassword("Jack");
			customer4.addFollowing(customer2);
			customer4.addFollowing(customer3);
			customer4.setFollower_Count(customer1.getFollower_Count() + 1);
			customerRepository.save(customer4);

			Customer customer5 = new Customer();
			customer5.setName("Chris");
			customer5.setEmail("Chris@gmail.com");
			customer5.setPassword("Chris");
			customer5.addFollowing(customer2);
			customer5.addFollowing(customer3);
			customer5.addFollowing(customer4);
			customer5.setFollower_Count(0);
			customerRepository.save(customer5);

			//Products
			Product product1 = new Product();
			product1.setName("JetStream® 3 Piece Hardside Luggage Set");
			product1.setCategory("Furniture/Luggage");
			product1.setDescription("https://www.walmart.ca/en/ip/jetstream-3-piece-hardside-luggage-set-cream/6000206846841");
			productRepository.save(product1);

			Product product4 = new Product();
			product4.setName("Northlight Full Buffalo Fir Artificial Christmas Tree - 12' - Unlit");
			product4.setCategory("Furniture/Luggage");
			product4.setDescription("https://www.walmart.ca/en/ip/Northlight-Full-Buffalo-Fir-Artificial-Christmas-Tree-12-Unlit/24N8MLJRSAZ4?selectedSellerId=1");
			productRepository.save(product4);

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

			Product product5 = new Product();
			product5.setName("Monopoly Knockout");
			product5.setCategory("Toys");
			product5.setDescription("https://www.walmart.ca/en/ip/monopoly-knockout-multi/6000207004177?selectedSellerId=0");
			productRepository.save(product5);

			Product product6 = new Product();
			product6.setName("Decacer Pure Maple Syrup, 540mL");
			product6.setCategory("Grocery");
			product6.setDescription("https://www.walmart.ca/en/ip/Decacer-Pure-Maple-Syrup/10179837");
			productRepository.save(product6);

			Product product7 = new Product();
			product7.setName("Waterpik Cordless Plus Water Flosser Black");
			product7.setCategory("Hygiene");
			product7.setDescription("https://www.walmart.ca/en/ip/waterpik-cordless-plus-water-flosser-black-black/6000192076681?selectedSellerId=0");
			productRepository.save(product7);

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

			Review review34 = new Review();
			review34.setReviewText("As good as it gets! Love this plushie.");
			review34.setStarRating(5);
			review34.setReviewer(customer4);
			review34.setReviewDate(now);
			review34.setProduct(product3);
			reviewRepository.save(review34);

			Review review35 = new Review();
			review35.setReviewText("Always cozy to sleep with.");
			review35.setStarRating(4);
			review35.setReviewer(customer5);
			review35.setReviewDate(now);
			review35.setProduct(product3);
			reviewRepository.save(review35);

			Review review41 = new Review();
			review41.setReviewText("Way overpriced but easier than getting a real tree.");
			review41.setStarRating(2);
			review41.setReviewer(customer1);
			review41.setReviewDate(now);
			review41.setProduct(product4);
			reviewRepository.save(review41);

			Review review44 = new Review();
			review44.setReviewText("Fake trees are the worse, go cut down your own!!");
			review44.setStarRating(1);
			review44.setReviewer(customer2);
			review44.setReviewDate(now);
			review44.setProduct(product4);
			reviewRepository.save(review44);

			Review review53 = new Review();
			review53.setReviewText("It's another Monopoly game, what is there to say at this point.");
			review53.setStarRating(2);
			review53.setReviewer(customer3);
			review53.setReviewDate(now);
			review53.setProduct(product5);
			reviewRepository.save(review53);

			Review review61 = new Review();
			review61.setReviewText("I'm Canadian, what's not to love about this stuff.");
			review61.setStarRating(5);
			review61.setReviewer(customer1);
			review61.setReviewDate(now);
			review61.setProduct(product6);
			reviewRepository.save(review61);

			Review review63 = new Review();
			review63.setReviewText("Not the purest syrup but good enough for me.");
			review63.setStarRating(4);
			review63.setReviewer(customer3);
			review63.setReviewDate(now);
			review63.setProduct(product6);
			reviewRepository.save(review63);

			Review review64 = new Review();
			review64.setReviewText("Can't get enough on my pancakes! Good price too");
			review64.setStarRating(5);
			review64.setReviewer(customer4);
			review64.setReviewDate(now);
			review64.setProduct(product6);
			reviewRepository.save(review64);

			Review review65 = new Review();
			review65.setReviewText("It's good for store bought syrup, my family makes it better");
			review65.setStarRating(4);
			review65.setReviewer(customer5);
			review65.setReviewDate(now);
			review65.setProduct(product6);
			reviewRepository.save(review65);

			Review review71 = new Review();
			review71.setReviewText("Recently got braces so this thing is incredible!");
			review71.setStarRating(5);
			review71.setReviewer(customer1);
			review71.setReviewDate(now);
			review71.setProduct(product7);
			reviewRepository.save(review71);

			Review review72 = new Review();
			review72.setReviewText("Broke within a month and haven't gotten my money back, this thing is a scam!!!");
			review72.setStarRating(1);
			review72.setReviewer(customer2);
			review72.setReviewDate(now);
			review72.setProduct(product7);
			reviewRepository.save(review72);
		};
	}

}
