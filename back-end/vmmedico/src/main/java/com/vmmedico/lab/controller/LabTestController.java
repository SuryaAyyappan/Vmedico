package com.vmmedico.lab.controller;

import com.vmmedico.lab.entity.LabTest;
import com.vmmedico.lab.service.LabTestService;
import com.vmmedico.authentication.service.UserService;
import com.vmmedico.authentication.service.LabService;
import com.vmmedico.authentication.entity.User;
import com.vmmedico.authentication.entity.Lab;
import com.vmmedico.authentication.configuration.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lab")
@CrossOrigin
public class LabTestController {

    @Autowired private JwtTokenUtil jwtTokenUtil;
    @Autowired private UserService userService;
    @Autowired private LabService labService;
    @Autowired private LabTestService labTestService;


    @PostMapping("/add-tests")
    public ResponseEntity<?> createTest(@RequestHeader("Authorization") String authHeader,
                                        @RequestBody LabTest request) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(400).body(Map.of("success", false, "message", "Missing or invalid Authorization header"));
        }

        String token = authHeader.substring(7);
        if (!jwtTokenUtil.validateToken(token)) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Invalid or expired token"));
        }

        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Lab lab = labService.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Lab profile not found"));

        request.setLab(lab);
        LabTest saved = labTestService.createTest(lab, request);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Test added successfully",
                "testId", saved.getId(),
                "testName", saved.getName(),
                "price", saved.getPrice()
        ));
    }



    @PutMapping("/test/{id}")
    public ResponseEntity<?> updateTest(
            Principal principal,
            @PathVariable Long id,
            @RequestBody LabTest update
    ) {
        try {
            // principal may be null if the filter didn't set Authentication (-> fix filter)
            if (principal == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                        "success", false, "message", "Unauthenticated"
                ));
            }

            String username = principal.getName();
            User user = userService.findByUsernameOrEmail(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Lab lab = labService.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Lab profile not found"));

            LabTest existing = labTestService.findByIdAndLab(id, lab.getId());
            if (existing == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "success", false, "message", "Test not found for this lab"
                ));
            }

            LabTest updated = labTestService.updateTest(
                    existing.getId(),
                    update.getPrice(),
                    update.getDescription(),
                    update.getActive()
            );

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Test updated successfully",
                    "testId", updated.getId(),
                    "updatedPrice", updated.getPrice(),
                    "updatedDescription", updated.getDescription(),
                    "active", updated.getActive()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));
        }
    }

    @GetMapping("test/all")
    public ResponseEntity<?> listOwnTests(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        if (!jwtTokenUtil.validateToken(token)) return ResponseEntity.status(401).body("Invalid token");
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        User user = userService.findById(userId).orElseThrow();
        Lab lab = labService.findByUser(user).orElseThrow();
        List<LabTest> tests = labTestService.listActiveTests(lab);
        return ResponseEntity.ok(tests);
    }
}
