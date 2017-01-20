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

import com.datapine.domain.Item;
import com.datapine.service.AclSecurityService;
import com.datapine.util.ContextUtil;

@Service
public class AclSecurityServiceImpl implements AclSecurityService {

    @Autowired private MutableAclService mutableAclService;

    @Override
    public void setPermission(Item item) {
        addPermission(item, new PrincipalSid(ContextUtil.getUserEmail()), BasePermission.WRITE, Item.class);
        addPermission(item, new GrantedAuthoritySid("ROLE_ADMIN"), BasePermission.ADMINISTRATION, Item.class);
    }

    private void addPermission(Item item, Sid sid, Permission permission, Class<?> clazz) {
        MutableAcl acl;
        ObjectIdentity oid = new ObjectIdentityImpl(clazz.getCanonicalName(), item.getId());

        try {
            acl = (MutableAcl) mutableAclService.readAclById(oid);
        } catch (NotFoundException nfe) {
            acl = mutableAclService.createAcl(oid);
        }

        acl.insertAce(acl.getEntries().size(), permission, sid, true);
        mutableAclService.updateAcl(acl);
    }

}
