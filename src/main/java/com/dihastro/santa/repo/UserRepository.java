package com.dihastro.santa.repo;

import com.dihastro.santa.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User getByUsername(String name);
}
