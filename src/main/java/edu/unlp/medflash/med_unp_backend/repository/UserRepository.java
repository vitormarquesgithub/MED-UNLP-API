package edu.unlp.medflash.med_unp_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.unlp.medflash.med_unp_backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {}