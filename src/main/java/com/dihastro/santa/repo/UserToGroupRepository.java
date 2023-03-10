package com.dihastro.santa.repo;

import com.dihastro.santa.model.Group;
import com.dihastro.santa.model.User;
import com.dihastro.santa.model.UserToGroup;
import org.springframework.data.repository.CrudRepository;

public interface UserToGroupRepository extends CrudRepository<UserToGroup, Long> {
    UserToGroup getByGroupAndUser(Group group, User user);
}
