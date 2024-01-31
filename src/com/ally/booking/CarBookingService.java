package com.ally.booking;

import com.ally.car.Car;
import com.ally.car.CarService;
import com.ally.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class CarBookingService {
    private final  CarBookingDao carBookingDao = new CarBookingDao();
    private CarService carService= new CarService();

    private UUID bookCar(User user, String regNumber) {
        Car[] availableCars = getAvailableCars();

        if (availableCars.length == 0) {
            throw new IllegalStateException("No Car available for renting");
        }

        for (Car availableCar : availableCars) {
            if (availableCar.getRegNumber().equals(regNumber)) {
                Car car = carService.getCar(regNumber);
                UUID bookingId = UUID.randomUUID();
                carBookingDao.book(
                        new CarBooking(bookingId, user, car, LocalDateTime.now())
                );
                return bookingId;
            }
        }

        throw new IllegalStateException("Already booked. car with regNumber" + regNumber);
    }
    public Car[] getUserBookedCars(UUID userId) {
        CarBooking[] carBookings = carBookingDao.getCarBookings();

        int numberOfBookingsForUser = 0;

        for (CarBooking cb : carBookings) {
            if (cb != null && cb.getUser().getId().equals(userId)) {
                ++numberOfBookingsForUser;
            }
        }

        if (numberOfBookingsForUser == 0) {
            return new Car[0];
        }

        Car[] userCars = new Car[numberOfBookingsForUser];

        int index = 0;
        for (CarBooking carBooking : carBookings) {
            if (carBooking != null && carBooking.getUser().getId().equals(userId)) {
                userCars[index++] = carBooking.getCar();
            }
        }
        return userCars;
    }




    public Car[] getAvailableCars(){
        return getCars(carService.getAllCars());
    }

    public Car[] getAvailableElectricCars() {
        return getCars(carService.getAllElectricCars());

    }

    private Car[] getCars(Car[] cars) {
        if(cars.length == 0){
            return new Car[0];
        }

        CarBooking[] carBookings = carBookingDao.getCarBookings();

        if (carBookings.length == 0) {
            return cars;
        }

        int availableCarsCount = 0;

        for (Car car : cars) {
            boolean booked = false;
            for (CarBooking carBooking : carBookings) {
                if (carBooking == null || !carBooking.getCar().equals(car)) {
                    continue;
                }
                booked = true;
            }
            if (!booked) {
                ++availableCarsCount;
            }
        }

        Car[] availableCars = new Car[availableCarsCount];
        int index = 0;

        for (Car car : cars) {
            boolean booked  = false;
            for (CarBooking carBooking : carBookings) {
                if (carBooking == null || !carBooking.getCar().equals(car)) {
                    continue;
                }
                booked = true;
            }
            if (!booked) {
                availableCars[index++] = car;
            }
        }

        return availableCars;
    }

    public CarBooking[] getBookings() {
        CarBooking[] carBookings = carBookingDao.getCarBookings();

        int numberOfBookings = 0;

        for (CarBooking cb : carBookings) {
            if (cb != null) {
                ++numberOfBookings;
            }
        }

        if (numberOfBookings == 0) {
            return new CarBooking[0];
        }

        CarBooking[] bookings = new CarBooking[numberOfBookings];

        int index = 0;
        for (CarBooking carBooking : carBookings) {
            if (carBooking != null) {
                bookings[index++] = carBooking;
            }
        }

        return bookings;
    }

}
