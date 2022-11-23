package com.interviewdot.weatherapp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviewdot.weatherapp.constants.Constant;
import com.interviewdot.weatherapp.dto.WeatherAverageDTO;
import com.interviewdot.weatherapp.dto.WeatherMapDTO;
import com.interviewdot.weatherapp.dto.WeatherMapTimeDTO;
import com.interviewdot.weatherapp.model.User;
import com.interviewdot.weatherapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final String URI = "http://api.openweathermap.org/data/2.5/forecast";
    private final String API_ID = "7cde1336aaafbe40cb99aa605e6152bc";
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    UserRepository userRepository;
    // Notification system log messages
    //  city tempreture >  40 then show the notification
    //

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    private WeatherAverageDTO average(List<WeatherMapTimeDTO> list) {
        WeatherAverageDTO result = new WeatherAverageDTO();

        for (WeatherMapTimeDTO item : list) {
            result.setDate(item.getDt().toLocalDate());
            result.plusMap(item);
        }

        //result.totalize();

        return result;
    }

    private String url(String city) {
        return String.format(URI.concat("?q=%s").concat("&appid=%s").concat("&units=metric"), city, API_ID);
    }

    @Override
    public ResponseEntity<?> weatherForecastAverage(String city) {
        List<WeatherAverageDTO> result = new ArrayList<WeatherAverageDTO>();
        try {
            WeatherMapDTO weatherMap = this.restTemplate().getForObject(this.url(city), WeatherMapDTO.class);

            for (LocalDate reference = LocalDate.now();
                 reference.isBefore(LocalDate.now().plusDays(4));
                 reference = reference.plusDays(1)) {
                final LocalDate ref = reference;
                List<WeatherMapTimeDTO> collect = weatherMap.getList().stream()
                        .filter(x -> x.getDt().toLocalDate().equals(ref)).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(collect)) {
                    result.add(this.average(collect));
                }
            }
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(new Json(e.getResponseBodyAsString()), e.getStatusCode());
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //  save users with their location preferences
    @Override
    public ResponseEntity<?> saveUserCriteria(User user) {
        try {
            User userCriteria = userRepository.save(new User("Jack", "London"));
            userRepository.save(new User("Micael", "Krakow"));
            userRepository.save(new User("Kim", "Pune"));
            userRepository.save(new User("David", "Mumbai"));
            userRepository.save(new User("Michelle", "Warsaw"));


            return new ResponseEntity<>(userCriteria, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  get users based on their location preferences
    @Override
    public ResponseEntity<?> getUsers() {
        try {
            List<User> users =  userRepository.findAll();
           System.out.println("size " + users.size());

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  Getting data from 2 mock API's

    private double getTemperatureDataFromMockRapidApi() {
        //create ObjectMapper instance
        Double tempValue = null;
        try {
        //convert JSON file to map
            Map<?, ?> map = objectMapper.readValue(new FileInputStream("src/main/resources/temperature.json"), Map.class);

        //iterate over map entries and print to console
            for (Map.Entry<?, ?> entry : map.entrySet()) {

                System.out.println(entry.getKey() + "=" + entry.getValue());
                 tempValue = (Double) entry.getValue();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempValue != null ? tempValue : 0;
    }

    private double getTemperatureDataFromMockOpenApi() {
//        //create ObjectMapper instance
        Double tempValue = null;
        try {
            //convert JSON file to map
            Map<?, ?> map = objectMapper.readValue(new FileInputStream("src/main/resources/temperatureopenweather.json"), Map.class);

            //iterate over map entries and print to console
            for (Map.Entry<?, ?> entry : map.entrySet()) {

                System.out.println(entry.getKey() + "=" + entry.getValue());
                tempValue = (Double) entry.getValue();

           }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempValue != null ? tempValue : 0;
    }

 //  calculate mean tempreture from mock api
    private Double calcualteMeanTempreature() {
        Double temp1 = getTemperatureDataFromMockOpenApi();
        Double temp2 = getTemperatureDataFromMockRapidApi();
        return (temp1 + temp2) / 2;
    }

    //  send Notification to user
    private void sendNotification(){
       double temperature =  getTemperatureDataFromMockRapidApi();
        String userName = "David";
        Double tempCriteria = 35.0;
        if(Constant.CACHE_MAP.get("userName") == null){
            Constant.CACHE_MAP.put(userName, tempCriteria);
        }else{
            System.out.println("print data from cache");

            Constant.CACHE_MAP.get(userName);

        }
        if(temperature > tempCriteria){
            System.out.println("Notification sent to user");
        }
    }

}
