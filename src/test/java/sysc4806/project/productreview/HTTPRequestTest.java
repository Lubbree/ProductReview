package sysc4806.project.productreview;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class HTTPRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepositoryMock;

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
}
