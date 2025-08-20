package duy.com.learnspringboot.repository;

import duy.com.learnspringboot.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
