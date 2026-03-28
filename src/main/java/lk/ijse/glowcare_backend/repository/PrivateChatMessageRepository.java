package lk.ijse.glowcare_backend.repository;

import lk.ijse.glowcare_backend.entity.PrivateChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PrivateChatMessageRepository extends JpaRepository<PrivateChatMessage, Long> {
    List<PrivateChatMessage> findByRoomIdOrderByTimestampAsc(String roomId);
}