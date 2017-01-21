package com.datapine.service;

import com.datapine.domain.Identifiable;

public interface AclSecurityService {

    void setPermission(Identifiable<?> obj);

}