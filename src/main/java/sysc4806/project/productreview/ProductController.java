package sysc4806.project.productreview;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


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

    @GetMapping("/home/product/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productRepository.findById(id));
        return "product";
    }

    @GetMapping("home/login")
    public String login(Model model) {
        return "login";
    }

}
