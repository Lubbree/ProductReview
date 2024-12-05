package sysc4806.project.productreview;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class HTTPRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private ProductRepository productRepositoryMock;

    @MockBean
    private ReviewRepository reviewRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void homeGetRequestTest() throws Exception {
        this.mockMvc.perform(get("/home")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    public void productGetRequestTest() throws Exception {
        String name = "test";
        String category = "cat";
        String description = "hello world";
        Long id = 1L;

        Product mockProduct = new Product();
        mockProduct.setName(name);
        mockProduct.setCategory(category);
        mockProduct.setDescription(description);
        mockProduct.setId(id);

        when(productRepositoryMock.findById(id)).thenReturn(Optional.of(mockProduct));

        this.mockMvc.perform(get("/home/product/{id}", "1")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("product"))
                .andExpect(content().string(containsString(name)))
                .andExpect(content().string(containsString(category)))
                .andExpect(content().string(containsString(description)));
    }

    @Test
    public void productGetRequestWithReviewTest() throws Exception {
        String name = "test";
        String category = "cat";
        String description = "hello world";
        Long id = 1L;

        String reviewText = "This is Review";
        int starRating = 4;

        String customerName = "Joy";

        Customer mockCustomer = new Customer();
        mockCustomer.setName(customerName);

        Product mockProduct = new Product();
        mockProduct.setName(name);
        mockProduct.setCategory(category);
        mockProduct.setDescription(description);
        mockProduct.setId(id);

        Review mockReview = new Review();
        mockReview.setReviewer(mockCustomer);
        mockReview.setProduct(mockProduct);
        mockReview.setReviewText(reviewText);
        mockReview.setStarRating(starRating);

        List<Review> mockReviews = new ArrayList<>();
        mockReviews.add(mockReview);

        when(productRepositoryMock.findById(id)).thenReturn(Optional.of(mockProduct));
        when(reviewRepository.findByProduct(mockProduct)).thenReturn(mockReviews);

        this.mockMvc.perform(get("/home/product/{id}", "1")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("product"))
                .andExpect(content().string(containsString(name)))
                .andExpect(content().string(containsString(category)))
                .andExpect(content().string(containsString(description)))
                .andExpect(content().string(containsString(reviewText)))
                .andExpect(content().string(containsString(String.valueOf(starRating))))
                .andExpect(content().string(containsString(customerName)));
    }

    @Test
    public void loginGetRequestTest() throws Exception {
        this.mockMvc.perform(get("/home/login")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void loginPostRequestTest() throws Exception {
        String email = "Bill@gmail.com";
        String password = "Bill123";
        String name = "Bill";

        Customer mockCustomer = new Customer();
        mockCustomer.setEmail(email);
        mockCustomer.setPassword(password);
        mockCustomer.setName(name);

        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(mockCustomer));
        when(customerService.loginCustomer(email, password)).thenReturn(mockCustomer);

        this.mockMvc.perform(post("/api/customers/login")
                        .content("{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }



}
