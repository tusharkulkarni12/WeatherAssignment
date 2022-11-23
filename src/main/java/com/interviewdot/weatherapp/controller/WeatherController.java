package com.interviewdot.weatherapp.controller;
import com.interviewdot.weatherapp.model.User;
import com.interviewdot.weatherapp.services.WeatherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;



    @ApiOperation("Return a JSON object that gives the weather averages.")
    @GetMapping(value = "/forecast", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> weatherForecastAverage(@ApiParam("City's name") @RequestParam(required = true) String city) {
        return weatherService.weatherForecastAverage(city);
    }

    @PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveUserCriteria(@RequestBody User user) {
        return weatherService.saveUserCriteria(user);
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsers() {
        return weatherService.getUsers();
    }



}
