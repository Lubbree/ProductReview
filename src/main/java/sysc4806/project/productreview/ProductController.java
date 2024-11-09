package sysc4806.project.productreview;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "home";
    }

    @GetMapping("/home/{productName}")
    public String viewProduct(@PathVariable String productName, Model model) {
        model.addAttribute("productName", productName);
        return "product";
    }
}
