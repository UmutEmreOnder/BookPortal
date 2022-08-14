package tr.com.obss.jip.service;

import tr.com.obss.jip.model.Admin;

public interface AdminService {
    Admin getUserByUsername(String s);

    void createUser(Admin adminUser);
}
