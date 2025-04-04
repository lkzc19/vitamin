package org.example.service;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
public class LdapAuthService {

    @Resource
    private LdapTemplate ldapTemplate;

    public boolean authenticate(String username, String password) {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("uid", username));

        return ldapTemplate.authenticate("", filter.toString(), password);
    }

    public List<String> getAllUserNames() {
        return ldapTemplate.search(
                query().where("objectclass").is("person"),
                (AttributesMapper<String>) attrs -> (String) attrs.get("cn").get());
    }

    public void createUser(String username, String password) {
        DirContextAdapter context = new DirContextAdapter();
        context.setAttributeValues("objectclass",
                new String[] { "top", "person", "organizationalPerson", "inetOrgPerson" });
        context.setAttributeValue("cn", username);
        context.setAttributeValue("sn", username);
        context.setAttributeValue("userPassword", password);

        ldapTemplate.bind("uid=" + username + ",ou=users", context, null);
    }
}
