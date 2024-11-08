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


    public void setReviewer(Customer reviewer) {
        this.reviewer = reviewer;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }
}
