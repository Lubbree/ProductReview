package sysc4806.project.productreview;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

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
    private Set<User> following = new HashSet<>();

    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public void addFollowing(User user) {
        following.add(user);
        user.getFollowing().add(this);
    }

    private Collection<User> getFollowing() {

        return -1;
    }

    public void removeFollowing(User user) {
        following.remove(user);
        user.getFollowing().remove(this);
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
