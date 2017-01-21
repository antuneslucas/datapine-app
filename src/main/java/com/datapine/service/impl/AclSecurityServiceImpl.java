package com.datapine.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Service;

import com.datapine.domain.Identifiable;
import com.datapine.service.AclSecurityService;
import com.datapine.util.ContextUtil;

@Service
public class AclSecurityServiceImpl implements AclSecurityService {

    @Autowired private MutableAclService mutableAclService;

    @Override
    public void setPermission(Identifiable<?> obj) {
        addPermission(obj, new PrincipalSid(ContextUtil.getUserEmail()), BasePermission.WRITE);
        addPermission(obj, new GrantedAuthoritySid("ROLE_ADMIN"), BasePermission.ADMINISTRATION);
    }

    private void addPermission(Identifiable<?> obj, Sid sid, Permission permission) {
        ObjectIdentity oid = new ObjectIdentityImpl(obj.getClass().getCanonicalName(), obj.getId());
        MutableAcl acl;

        try {
            acl = (MutableAcl) mutableAclService.readAclById(oid);
        } catch (NotFoundException nfe) {
            acl = mutableAclService.createAcl(oid);
        }

        acl.insertAce(acl.getEntries().size(), permission, sid, true);
        mutableAclService.updateAcl(acl);
    }

}
