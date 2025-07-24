package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.InvalidDocumentException;
import com.pragma.powerup.domain.exception.InvalidEmailException;
import com.pragma.powerup.domain.exception.InvalidPhoneException;
import com.pragma.powerup.domain.exception.UnderageException;
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
        RoleModel validRole = new RoleModel(1, "OWNER", "Owner access");
        return new UserModel(1, "John", "Cardona", 12813289, "+57303451248", LocalDate.of(1995, 2, 26), "john@gmail.com", "password", validRole);
    }

    @Test
    void saveUser_ShouldCallPersistence_WhenUserIsValid() {
        UserModel user = validUser();

        userUseCase.saveUser(user);

        verify(userPersistencePort, times(1)).saveUser(user);
    }

    @Test
    void saveOwner_ShouldAssignOwnerRole_AndSaveUser() {
        UserModel user = validUser();
        RoleModel ownerRole = new RoleModel(1, "OWNER", "OWNER ACCESS");
        when(rolePersistencePort.findByName("OWNER")).thenReturn(ownerRole);

        userUseCase.saveOwner(user);

        assertEquals("OWNER", user.getRole().getName());
        verify(userPersistencePort).saveUser(user);
    }

    @Test
    void saveUser_ShouldThrowInvalidEmailException_WhenEmailIsInvalid() {
        UserModel user = validUser();
        user.setEmail("invalid-email");

        assertThrows(InvalidEmailException.class, () -> userUseCase.saveUser(user));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void saveUser_ShouldThrowInvalidDocumentException_WhenDocumentIsNegative() {
        UserModel user = validUser();
        user.setIdentityDocument(-1);

        assertThrows(InvalidDocumentException.class, () -> userUseCase.saveUser(user));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void saveUser_ShouldThrowInvalidDocumentException_WhenDocumentIsNotNumeric() {
        UserModel user = validUser();
        user.setIdentityDocument(0);

        assertThrows(InvalidDocumentException.class, () -> userUseCase.saveUser(user));
    }

    @Test
    void saveUser_ShouldThrowInvalidPhoneException_WhenPhoneIsInvalid() {
        UserModel user = validUser();
        user.setPhone("abc123");

        assertThrows(InvalidPhoneException.class, () -> userUseCase.saveUser(user));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void saveUser_ShouldThrowUnderageException_WhenUserIsUnder18() {
        UserModel user = validUser();
        user.setBirthdate(LocalDate.now().minusYears(17));

        assertThrows(UnderageException.class, () -> userUseCase.saveUser(user));
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
}
