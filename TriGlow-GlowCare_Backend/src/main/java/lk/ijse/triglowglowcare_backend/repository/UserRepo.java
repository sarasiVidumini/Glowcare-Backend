package lk.ijse.triglowglowcare_backend.repository;

import lk.ijse.triglowglowcare_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {

}
