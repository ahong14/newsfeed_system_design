package com.newsfeed_system_design.newsfeed;

import com.newsfeed_system_design.newsfeed.models.User;
import com.newsfeed_system_design.newsfeed.repositories.UserRepository;
import com.newsfeed_system_design.newsfeed.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void getUserFound() {
        UUID userFoundId = UUID.randomUUID();
        String firstName = "FirstName";
        String lastName = "LastName";
        String email = "test@mail.com";
        String testPassword = "testpassword";
        LocalDate userDob = LocalDate.parse("01-01-1999", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        LocalDateTime createdAt = LocalDateTime.now();
        User testFoundUser = new User(userFoundId, firstName, lastName, email, testPassword, userDob, createdAt);

        when(userRepository.findById(userFoundId)).thenReturn(Optional.of(testFoundUser));

        User foundUser = userService.getUser(userFoundId);

        assertNotNull(foundUser);
        assertEquals(foundUser.getFirstName(), firstName);
        assertEquals(foundUser.getLastName(), lastName);
        assertEquals(foundUser.getEmail(), email);
        assertEquals(foundUser.getId(), userFoundId);
        assertEquals(foundUser.getPassword(), testPassword);
        assertEquals(foundUser.getDateOfBirth(), userDob);
        assertEquals(foundUser.getCreatedAt(), createdAt);
    }

    @Test
    public void getUserNotFound() {
        UUID userNotFoundId = UUID.randomUUID();
        when(userRepository.findById(userNotFoundId)).thenThrow(new NoSuchElementException("User not found for ID: " + userNotFoundId));
        assertThrows(NoSuchElementException.class, () -> userService.getUser(userNotFoundId));
    }
}
