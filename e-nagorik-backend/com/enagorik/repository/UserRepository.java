package com.enagorik.repository;

import com.enagorik.model.User;
import com.enagorik.util.AppConstants;

public class UserRepository extends AbstractFileRepository<User, Integer>
{
    public UserRepository()
    {
        super(AppConstants.DATA_DIR + "/users.dat");
    }

    @Override
    protected Integer extractId(User entity)
    {
        return entity.getUserId();
    }
}