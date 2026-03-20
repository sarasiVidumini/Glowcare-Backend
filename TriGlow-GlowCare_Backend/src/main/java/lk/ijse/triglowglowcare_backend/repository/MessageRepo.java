package lk.ijse.triglowglowcare_backend.repository;

import lk.ijse.triglowglowcare_backend.entity.Chat_Type;
import lk.ijse.triglowglowcare_backend.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface MessageRepo extends JpaRepository<Message,Long> {

    // Fetch all public messages
    List<Message> findByChatTypeOrderByTimestampAsc(Chat_Type chat_type);

    // Fetch private history between a user and a specific expert
    @Query("SELECT m FROM Message m WHERE m.chat_type = 'PRIVATE_EXPERT' " +
            "AND ((m.senderEmail = :userEmail AND m.receiverId = :expertId) " +
            "OR (m.senderEmail = :expertId AND m.receiverId = :userEmail)) " +
            "ORDER BY m.timestamp ASC")
    List<Message> findPrivateConversation(String userEmail, Long expertId);
}
