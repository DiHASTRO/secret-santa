package com.dihastro.santa.repo;

import com.dihastro.santa.model.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GroupRepository extends CrudRepository<Group, Long> {
    Group getByGroupname(String groupname);
}
