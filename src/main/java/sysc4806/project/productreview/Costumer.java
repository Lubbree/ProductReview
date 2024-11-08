package sysc4806.project.productreview;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Costumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String name;

    @ManyToMany
    @JoinTable(
            name = "user_follows",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private Set<Costumer> following = new HashSet<>();

    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFollowing(Set<Costumer> following) {
        this.following = following;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public void addFollowing(Costumer costumer) {
        following.add(costumer);
        costumer.getFollowing().add(this);
    }

    private Collection<Costumer> getFollowing() {

        return this.following;
    }

    public void removeFollowing(Costumer costumer) {
        following.remove(costumer);
        costumer.getFollowing().remove(this);
    }

    public void addReview(Review review) {
        reviews.add(review);
        review.setReviewer(this);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
        review.setReviewer(null);
    }
}
