package com.interviewdot.weatherapp.services;

import com.interviewdot.weatherapp.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public interface WeatherService {

    public ResponseEntity<?> weatherForecastAverage(String city);

    ResponseEntity<?> saveUserCriteria(User user);


    ResponseEntity<?> getUsers();
}
