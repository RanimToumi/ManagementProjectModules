package com.Marketing.MarketingAPI.controllers;
import com.Marketing.MarketingAPI.DTO.AuthenticationRequest;
import com.Marketing.MarketingAPI.DTO.AuthenticationResponse;
import com.Marketing.MarketingAPI.DTO.UserDTO;
import com.Marketing.MarketingAPI.DTO.VerificationRequest;
import com.Marketing.MarketingAPI.services.AuthenticationService;
import com.Marketing.MarketingAPI.services.EmailService;
import com.Marketing.MarketingAPI.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final EmailService emailService;
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getUsers() throws AccessDeniedException {
        return ResponseEntity.ok(userService.findAllUsers());
    }
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerEmployee(@RequestBody UserDTO request) {
        var response=authenticationService.register(request);
        if(request.isTfaEnabled()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.accepted().build();
    }
    @GetMapping
    public String get(){
        return "hello auth!!!!";
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request,response);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestBody VerificationRequest verificationRequest) {
        // Your method implementation here
        return ResponseEntity.ok(authenticationService.verifyCode(verificationRequest));
    }

}
