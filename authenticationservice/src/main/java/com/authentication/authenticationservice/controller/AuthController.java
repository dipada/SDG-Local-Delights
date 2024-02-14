package com.authentication.authenticationservice.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.authentication.authenticationservice.dto.LoginRequest;
import com.authentication.authenticationservice.model.UserDetails;
import com.authentication.authenticationservice.rabbitMQ.RabbitMQSender;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

  private static final long TOKEN_EXP = 600000; // 10 minute
  private final String SECRET_KEY = "secret";
  private final RabbitMQSender rabbitMQSender;

  @Autowired
  public AuthController(RabbitMQSender rabbitMQSender) {
    this.rabbitMQSender = rabbitMQSender;
  }

  // Entrypoint will be triggered by Spring Security when the user is not authenticated.
  // This method is called when the user is successfully authenticated with Google.
  @Operation(summary = "Login with Google")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "302", description = "Redirect to the client with the token"),
  })
  @GetMapping("/google")
  public ResponseEntity<String> successLogin(Authentication auth, @RequestParam(name = "redirect_uri", required = false) String redirectUri) {
    OAuth2User user = (OAuth2User) auth.getPrincipal();

    //make jwt token
    String token = JWT.create().withSubject(user.getAttribute("email")).withClaim("name", (String) user.getAttribute("given_name")).withClaim("surname", (String) user.getAttribute("family_name")).withClaim("picture", (String) user.getAttribute("picture")).withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXP))
            .sign(Algorithm.HMAC256(SECRET_KEY));

    if (redirectUri == null || redirectUri.isEmpty()) {
      redirectUri = "http://localhost:30073/redirect/oauth";
    }
    //send a message to the user service to create the user
    UserDetails userDetails = new UserDetails();
    userDetails.setEmail(user.getAttribute("email"));
    userDetails.setFirstName(user.getAttribute("given_name"));
    userDetails.setLastName(user.getAttribute("family_name"));
    userDetails.setPicture(user.getAttribute("picture"));
    userDetails.setGoogleAccount(true);

    rabbitMQSender.sendAddUserRequest(userDetails);

    // redirect to the client with the token
    HttpHeaders headers = new HttpHeaders();
    headers.add("Location", URLDecoder.decode(redirectUri, StandardCharsets.UTF_8) + "?token=" + token);
    return new ResponseEntity<>(headers, HttpStatus.FOUND);
  }

  @Value("${userservice.url}")
  String userServiceUrl;

  @Operation(summary = "Signup")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "User add request sent successfully"),
          @ApiResponse(responseCode = "400", description = "Invalid request"),
  })
  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody UserDetails userDetails) {
    userDetails.setGoogleAccount(false);
    final String verifyUrl = userServiceUrl + "/api/v1/user/createUser";

    try {
      RestTemplate restTemplate = new RestTemplate();
      HttpStatusCode responseStatusCode = restTemplate.postForEntity(verifyUrl, userDetails, String.class).getStatusCode();

      if (responseStatusCode != HttpStatus.OK) {
        return ResponseEntity.status(responseStatusCode).body("Invalid request");
      }
      return ResponseEntity.status(HttpStatus.OK).body("User add request sent successfully");

    } catch (HttpClientErrorException e) {
      return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error occurred");
    }
  }


  @Operation(summary = "Login")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Login successful"),
          @ApiResponse(responseCode = "401", description = "Invalid credentials"),
          @ApiResponse(responseCode = "500", description = "An error occurred"),
  })
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

    final String emailEncoded = URLEncoder.encode(loginRequest.getEmail(), StandardCharsets.UTF_8);
    final String passwordEncoded = URLEncoder.encode(loginRequest.getPassword(), StandardCharsets.UTF_8);

    final String verifyUrl = userServiceUrl + "/api/v1/user/verify?email=" + emailEncoded + "&password=" + passwordEncoded;
    try {
      RestTemplate restTemplate = new RestTemplate();
      HttpStatusCode responseStatusCode = restTemplate.getForEntity(verifyUrl, String.class).getStatusCode();
      if (responseStatusCode != HttpStatus.OK) {
        return ResponseEntity.status(responseStatusCode).body("Invalid credentials");
      }

      final String userInfoUrl = userServiceUrl + "/api/v1/user/client/" + loginRequest.getEmail();
      ResponseEntity<UserDetails> response = restTemplate.getForEntity(userInfoUrl, UserDetails.class);

      if (response.getBody() == null) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error occurred");
      }
      // Generate JWT token
      String token = JWT.create()
              .withSubject(loginRequest.getEmail())
              .withClaim("name", response.getBody().getFirstName() != null ? response.getBody().getFirstName() : "")
              .withClaim("surname", response.getBody().getLastName() != null ? response.getBody().getLastName() : "")
              .withClaim("picture", response.getBody().getPicture() != null ? response.getBody().getPicture() : "")
              .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXP))
              .sign(Algorithm.HMAC256(SECRET_KEY));

      Map<String, String> body = new HashMap<>();
      body.put("token", token);

      return ResponseEntity.status(HttpStatus.OK).body(body);

    } catch (HttpClientErrorException e) {
      return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error occurred");
    }
  }


  // This method is called when the user is not successfully authenticated
  @Operation(summary = "Login failed")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "401", description = "Login failed"),
  })
  @GetMapping("/failureLogin")
  public ResponseEntity<String> failureLogin() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
  }

  @Operation(summary = "Logout")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "302", description = "Redirect to the client"),
  })
  @GetMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
    new SecurityContextLogoutHandler().logout(request, response, null);
    // TODO: jwt blacklist or invalidation

    String redirectUri = "http://localhost:30073/";

    // redirect to the client
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(URI.create(redirectUri));
    return new ResponseEntity<>(headers, HttpStatus.FOUND);
  }
}
