package com.booking.patient_booking.service;

import com.booking.patient_booking.dto.AdminLoginRequest;
import com.booking.patient_booking.dto.AdminLoginResponse;
import com.booking.patient_booking.entity.Admin;
import com.booking.patient_booking.repository.AdminRepository;
import com.booking.patient_booking.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    Logger log = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private JwtUtil jwtUtil;

    public AdminLoginResponse login(AdminLoginRequest request) {
        log.info("user trying to login : "+ request);
        Admin admin = adminRepo
                .findByUsernameAndActiveTrue(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!admin.getPassword().equals(request.getPassword())) {
            log.info("login request declinend");
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(admin);
        log.info("token generated : " + token);
        log.info("logged in successfully");
        return new AdminLoginResponse(
                token,
                admin.getUsername(),
                admin.getRole()
        );
    }
}
