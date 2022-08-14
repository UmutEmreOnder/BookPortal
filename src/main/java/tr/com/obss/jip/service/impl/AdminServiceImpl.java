package tr.com.obss.jip.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.exception.UserNotFoundException;
import tr.com.obss.jip.model.Admin;
import tr.com.obss.jip.repository.AdminRepository;
import tr.com.obss.jip.service.AdminService;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Admin getUserByUsername(String username) {
        return adminRepository.findAdminByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    public void createUser(Admin adminUser) {
        adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword()));
        adminRepository.save(adminUser);
    }
}
