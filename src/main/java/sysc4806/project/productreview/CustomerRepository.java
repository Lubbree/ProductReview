package sysc4806.project.productreview;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    // customer by email
    Optional<Customer> findByEmail(String email);
}
