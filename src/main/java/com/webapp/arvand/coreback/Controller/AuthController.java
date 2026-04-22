package com.webapp.arvand.coreback.Controller;
import com.webapp.arvand.coreback.Dtos.ReqResponse;
import com.webapp.arvand.coreback.Dtos.SignupForm;
import com.webapp.arvand.coreback.Guava.GuavaCache;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/agent/api")
public class AuthController {
    @PostMapping("/signup")
    public ResponseEntity<ReqResponse> signup(@Valid @RequestBody SignupForm signupForm)
        throws Exception{
        return null;
    }
    @GetMapping("/success")
    public String success(){
        return "success";
    }
    @GetMapping("/mee")
    public ResponseEntity<?> me(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "authenticated", authentication.isAuthenticated(),
                    "message", "Not authenticated"
            ));
        }
        GuavaCache guavaCache = (GuavaCache) authentication.getPrincipal();
        return ResponseEntity.ok(Map.of(
                "userId", guavaCache.getUserId(),
                "username", guavaCache.getUsername(),
                "authenticated", authentication.isAuthenticated()
        ));
    }
}
