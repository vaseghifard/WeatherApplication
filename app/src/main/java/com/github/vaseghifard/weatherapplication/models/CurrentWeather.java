package com.github.vaseghifard.weatherapplication.models;

public class CurrentWeather {
    private String city_name;
    private String weather_description;
    private int current_temperature_image;
    private Object minTemp;
    private Object maxTemp;
    private Double current_temperature;

    public CurrentWeather() {
    }

    public CurrentWeather(String city_name, String weather_description, int current_temperature_image, Object minTemp, Object maxTemp, Double current_temperature) {
        this.city_name = city_name;
        this.weather_description = weather_description;
        this.current_temperature_image = current_temperature_image;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.current_temperature = current_temperature;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getWeather_description() {
        return weather_description;
    }

    public void setWeather_description(String weather_description) {
        this.weather_description = weather_description;
    }

    public int getCurrent_temperature_image() {
        return current_temperature_image;
    }

    public void setCurrent_temperature_image(int current_temperature_image) {
        this.current_temperature_image = current_temperature_image;
    }

    public Object getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(Object minTemp) {
        this.minTemp = minTemp;
    }

    public Object getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Object maxTemp) {
        this.maxTemp = maxTemp;
    }

    public Double getCurrent_temperature() {
        return current_temperature;
    }

    public void setCurrent_temperature(Double current_temperature) {
        this.current_temperature = current_temperature;
    }
}
