package lk.ijse.glowcare_backend.repository;

import lk.ijse.glowcare_backend.entity.PublicChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicChatMessageRepository extends JpaRepository<PublicChatMessage, Long> {

    List<PublicChatMessage> findTop100ByOrderByTimestampDesc();

}
