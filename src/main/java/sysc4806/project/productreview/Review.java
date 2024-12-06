package sysc4806.project.productreview;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private Customer reviewer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int starRating;
    private String reviewText;
    private LocalDateTime reviewDate;
    private double jaccard_index;

    public void setReviewer(Customer reviewer) {
        this.reviewer = reviewer;
    }

    public Customer getReviewer() {return reviewer;}

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {return product;}

    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }

    public int getStarRating() {return starRating;}

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getReviewText() {return reviewText;}

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    public LocalDateTime getReviewDate() {return reviewDate;}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public double getJaccard_index() {return jaccard_index;}

    public void setJaccard_index(double jaccard_index) {}
}
