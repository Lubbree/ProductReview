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

    @Column(unique = true, nullable = false)
    private String email; // Email will be the login credential

    @Column(nullable = false)
    private String password; // Securely store password (we'll hash it in the service layer)

    @ManyToMany
    @JoinTable(
            name = "user_follows",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private Set<Customer> following = new HashSet<>();

    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    // Getters and setters for new fields
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void createAccount(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password; // Store hashed password (handled in service)
    }

    // Existing getters, setters, and methods remain unchanged
    public Long getUserId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setUserId(Long userId) {
        this.id = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReview(Review review) {
        reviews.add(review);
        review.setReviewer(this);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
        review.setReviewer(null);
    }

    public Collection<Customer> getFollowing() {
        return this.following;
    }

    public void addFollowing(Customer customer) {
        following.add(customer);
        customer.getFollowing().add(this);
    }

    public void removeFollowing(Customer customer) {
        following.remove(customer);
        customer.getFollowing().remove(this);
    }
}
