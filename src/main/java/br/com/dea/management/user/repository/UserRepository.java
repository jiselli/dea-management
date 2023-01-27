package br.com.dea.management.user.repository;

import br.com.dea.management.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE id = :id")
    public Optional<User> findById(String id);


    @Query("SELECT u FROM User u WHERE name = :name")
    public Optional<User> findByName(String name);

    @Query("SELECT u FROM User u")
    public Page<User> findAllPaginated(Pageable pageable);
}