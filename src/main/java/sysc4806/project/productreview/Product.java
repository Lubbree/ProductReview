package sysc4806.project.productreview;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;
    private String name;
    private String category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addReview(Review review) {
        reviews.add(review);
        review.setProduct(this);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
        review.setProduct(null);
    }

    public String getName() {
        return name;
    }

    public String getProductId() {return productId;}

    public List<Review> getReviews() {return reviews;}

    public String getCategory() {
        return category;
    }
}