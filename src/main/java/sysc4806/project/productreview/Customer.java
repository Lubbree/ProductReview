package sysc4806.project.productreview;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "user_follows",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private Set<Customer> following = new HashSet<>();

    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    public void setUserId(Long userId) {
        this.id = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFollowing(Set<Customer> following) {
        this.following = following;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Long getUserId() {return id;}

    public String getName() {return name;}

    public Set<Review> getReviews() {return reviews;}

    public void addFollowing(Customer customer) {
        following.add(customer);
        customer.getFollowing().add(this);
    }

    public Collection<Customer> getFollowing() {
        return this.following;
    }

    public void removeFollowing(Customer customer) {
        following.remove(customer);
        customer.getFollowing().remove(this);
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
