package com.hackathon.eva.repository;

import com.hackathon.eva.domain.Message;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Message entity.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    default Optional<Message> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Message> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Message> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct message from Message message left join fetch message.annonce",
        countQuery = "select count(distinct message) from Message message"
    )
    Page<Message> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct message from Message message left join fetch message.annonce order by message.id desc")
    List<Message> findAllWithToOneRelationships();

    @Query("select message from Message message left join fetch message.annonce where message.id =:id")
    Optional<Message> findOneWithToOneRelationships(@Param("id") Long id);
}
