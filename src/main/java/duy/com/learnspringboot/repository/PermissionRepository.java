package duy.com.learnspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import duy.com.learnspringboot.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {}
