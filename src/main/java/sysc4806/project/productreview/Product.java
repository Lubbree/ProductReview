package sysc4806.project.productreview;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public void setCategory(String category) {
        this.category = category;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setName(String name) {this.name = name;}

    public void setId(Long id) { this.id = id;}

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
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

    public List<Review> getReviews() {return reviews;}

    public String getCategory() {
        return category;
    }

    public Long getId() {return id;}
}
