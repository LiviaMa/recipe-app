package com.manesculivia.receipe.repository;

import com.manesculivia.receipe.model.Authority;
import com.manesculivia.receipe.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    Authority findByRoleType(RoleType roleType);
}
