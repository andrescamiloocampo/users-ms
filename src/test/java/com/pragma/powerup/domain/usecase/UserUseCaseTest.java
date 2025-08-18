package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.*;
import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IRolePersistencePort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserUseCaseTest {

    private IUserPersistencePort userPersistencePort;
    private IRolePersistencePort rolePersistencePort;
    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        userPersistencePort = mock(IUserPersistencePort.class);
        rolePersistencePort = mock(IRolePersistencePort.class);
        userUseCase = new UserUseCase(userPersistencePort, rolePersistencePort);
    }

    private UserModel validUser() {
        return new UserModel(1, "John", "Cardona", 12813289,
                "+57303451248", LocalDate.of(1995, 2, 26),
                "john@gmail.com", "password", List.of());
    }

    @Test
    void saveUser_ShouldCallPersistence_WhenUserIsValid() {
        UserModel user = validUser();
        RoleModel ownerRole = new RoleModel(1, "OWNER", "Owner access");

        when(userPersistencePort.existsByEmail(user.getEmail())).thenReturn(false);
        when(rolePersistencePort.findByName("OWNER")).thenReturn(ownerRole);

        userUseCase.saveUser(user, "OWNER");

        assertTrue(user.getRoles().stream().anyMatch(r -> r.getName().equals("OWNER")));
        verify(userPersistencePort, times(1)).saveUser(user);
    }

    @Test
    void saveUser_ShouldThrowUserAlreadyExistsException_WhenEmailAlreadyExists() {
        UserModel user = validUser();
        when(userPersistencePort.existsByEmail(user.getEmail())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userUseCase.saveUser(user, "OWNER"));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void saveUser_ShouldThrowInvalidEmailException_WhenEmailIsInvalid() {
        UserModel user = validUser();
        user.setEmail("invalid-email");

        when(userPersistencePort.existsByEmail(user.getEmail())).thenReturn(false);

        assertThrows(InvalidEmailException.class, () -> userUseCase.saveUser(user, "OWNER"));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void saveUser_ShouldThrowInvalidDocumentException_WhenDocumentIsNegative() {
        UserModel user = validUser();
        user.setIdentityDocument(-1);

        when(userPersistencePort.existsByEmail(user.getEmail())).thenReturn(false);

        assertThrows(InvalidDocumentException.class, () -> userUseCase.saveUser(user, "OWNER"));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void saveUser_ShouldThrowInvalidDocumentException_WhenDocumentIsZero() {
        UserModel user = validUser();
        user.setIdentityDocument(0);

        when(userPersistencePort.existsByEmail(user.getEmail())).thenReturn(false);

        assertThrows(InvalidDocumentException.class, () -> userUseCase.saveUser(user, "OWNER"));
    }

    @Test
    void saveUser_ShouldThrowInvalidPhoneException_WhenPhoneIsInvalid() {
        UserModel user = validUser();
        user.setPhone("abc123");

        when(userPersistencePort.existsByEmail(user.getEmail())).thenReturn(false);

        assertThrows(InvalidPhoneException.class, () -> userUseCase.saveUser(user, "OWNER"));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void saveUser_ShouldThrowUnderageException_WhenUserIsUnder18() {
        UserModel user = validUser();
        user.setBirthdate(LocalDate.now().minusYears(17));

        when(userPersistencePort.existsByEmail(user.getEmail())).thenReturn(false);

        assertThrows(UnderageException.class, () -> userUseCase.saveUser(user, "OWNER"));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void getAllUsers_ShouldReturnListFromPersistence() {
        List<UserModel> expectedUsers = List.of(validUser(), validUser());
        when(userPersistencePort.getAllUsers()).thenReturn(expectedUsers);

        List<UserModel> result = userUseCase.getAllUsers();

        assertEquals(2, result.size());
        verify(userPersistencePort).getAllUsers();
    }

    @Test
    void getUserById_ShouldReturnUserFromPersistence() {
        UserModel user = validUser();
        when(userPersistencePort.getUserById(1)).thenReturn(user);

        UserModel result = userUseCase.getUserById(1);

        assertEquals(user, result);
        verify(userPersistencePort).getUserById(1);
    }
}
