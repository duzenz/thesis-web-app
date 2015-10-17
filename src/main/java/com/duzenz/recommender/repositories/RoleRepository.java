package com.duzenz.recommender.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duzenz.recommender.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Serializable>
{

}
