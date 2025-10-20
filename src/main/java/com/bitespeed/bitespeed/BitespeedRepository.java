package com.bitespeed.bitespeed;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface BitespeedRepository extends JpaRepository<UserDetails, Long> {

    @Query(value = "SELECT * FROM user_details WHERE user_details.email_id = :email OR user_details.phone_number = :phoneNumber ORDER BY created_at ASC", nativeQuery = true)
    List<UserDetails> findUsers(@Param("email") String email, @Param("phoneNumber") String phoneNumber);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE user_details SET link_precedence = 'SECONDARY', linked_id = :linkedId, updated_at = NOW() WHERE id = :id", nativeQuery = true)
    void updateUserAsSecondary(@Param("id") Long id, @Param("linkedId") Long linkedId);

    @Query(value = "SELECT * FROM user_details WHERE user_details.email_id = :email AND user_details.phone_number = :phoneNumber", nativeQuery = true)
    UserDetails findExactUser(@Param("email") String email, @Param("phoneNumber") String phoneNumber);

    @Query(value = "SELECT * FROM user_details WHERE id=:id", nativeQuery = true)
    UserDetails findPrimaryUser(@Param("id") Long id);
}
