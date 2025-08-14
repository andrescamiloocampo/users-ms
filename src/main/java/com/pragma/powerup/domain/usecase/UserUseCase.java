package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.exception.InvalidDocumentException;
import com.pragma.powerup.domain.exception.InvalidEmailException;
import com.pragma.powerup.domain.exception.InvalidPhoneException;
import com.pragma.powerup.domain.exception.UnderageException;
import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IRolePersistencePort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.regex.Pattern;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IRolePersistencePort rolePersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IRolePersistencePort rolePersistencePort) {
        this.userPersistencePort = userPersistencePort;
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public void saveUser(UserModel user) {
        validateUser(user);
        userPersistencePort.saveUser(user);
    }

    @Override
    public void saveOwner(UserModel user) {
        validateUser(user);
        RoleModel ownerRole = rolePersistencePort.findByName("OWNER");
        user.setRoles(List.of(ownerRole));
        userPersistencePort.saveUser(user);
    }

    @Override
    public UserModel getUserById(Integer id) {
        return userPersistencePort.getUserById(id);
    }


    @Override
    public List<UserModel> getAllUsers() {
        return userPersistencePort.getAllUsers();
    }

    private void validateUser(UserModel user){
        if (!Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", user.getEmail())) {
            throw new InvalidEmailException();
        }

        if (!(String.valueOf(user.getIdentityDocument()).matches("\\d+"))) {
            throw new InvalidDocumentException();
        }

        if (user.getIdentityDocument() <= 0) {
            throw new InvalidDocumentException();
        }

        if (!user.getPhone().matches("^\\+?\\d{1,13}$")) {
            throw new InvalidPhoneException();
        }

        if (Period.between(user.getBirthdate(), LocalDate.now()).getYears() < 18) {
            throw new UnderageException();
        }
    }
}
