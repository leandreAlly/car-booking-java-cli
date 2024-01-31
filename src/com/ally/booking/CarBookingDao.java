package com.ally.booking;

import java.util.UUID;

public class CarBookingDao {
    private static final CarBooking[] carBookings;

    static {
        carBookings = new CarBooking[10];
    }

    public CarBooking[] getCarBookings() {
        return carBookings;
    }

    public void book(CarBooking carBooking) {
        int nextFreeIndex = -1;

        for (int i = 0; i < carBookings.length; i++) {
            if (carBookings[i] == null) {
                nextFreeIndex = i;
            }
        }

        if (nextFreeIndex > -1) {
            carBookings[nextFreeIndex] = carBooking;
            return;
        }

        CarBooking[] biggerCarBookings = new CarBooking[carBookings.length + 10];

        for (int i = 0; i < carBookings.length; i++) {
            biggerCarBookings[i] = carBookings[i];
        }

        biggerCarBookings[carBookings.length] = carBooking;

    }

    public void cancelCarBooking(UUID id){

    }




}
