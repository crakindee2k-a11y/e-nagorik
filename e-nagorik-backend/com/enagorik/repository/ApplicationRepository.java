package com.enagorik.repository;

import com.enagorik.model.Application;
import com.enagorik.util.AppConstants;

public class ApplicationRepository extends AbstractFileRepository<Application, String>
{
    public ApplicationRepository()
    {
        super(AppConstants.DATA_DIR + "/applications.dat");
    }

    @Override
    protected String extractId(Application entity)
    {
        return entity.getApplicationNo();
    }
}