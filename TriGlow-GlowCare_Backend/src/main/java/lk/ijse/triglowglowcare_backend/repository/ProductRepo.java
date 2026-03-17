package lk.ijse.triglowglowcare_backend.repository;

import lk.ijse.triglowglowcare_backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
