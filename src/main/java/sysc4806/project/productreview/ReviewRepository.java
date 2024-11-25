package sysc4806.project.productreview;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    Optional<Review> findById(long id);
    Optional<Review> findByProduct(Product product);
    List<Review> findByReviewer(Customer reviewer);
}
