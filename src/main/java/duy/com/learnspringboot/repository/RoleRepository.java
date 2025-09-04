package duy.com.learnspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import duy.com.learnspringboot.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String> {}
