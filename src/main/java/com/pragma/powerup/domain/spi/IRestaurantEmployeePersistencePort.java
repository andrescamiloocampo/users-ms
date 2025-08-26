package com.pragma.powerup.domain.spi;

public interface IRestaurantEmployeePersistencePort {
    void assignEmployeeToRestaurant(int userId, int restaurantId, boolean active);
    boolean getOwnership(int restaurantId, int ownerId);
}
