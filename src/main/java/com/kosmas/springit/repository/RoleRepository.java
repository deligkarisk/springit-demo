package com.kosmas.springit.repository;

import com.kosmas.springit.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    public Role findByName(String name);
}
