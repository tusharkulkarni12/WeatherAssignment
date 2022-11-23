package com.interviewdot.weatherapp.model;
import javax.persistence.*;


@Entity
@Table(name = "userscriteria")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @Column(name = "userName")
    private String userName;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    @Column(name = "city")
    private String city;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public User() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!userName.equals(user.userName)) return false;
        return city.equals(user.city);
    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + city.hashCode();
        return result;
    }

    public User(String userName, String city) {
        this.userName = userName;
        this.city = city;
    }
}
