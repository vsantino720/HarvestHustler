package entities;

import entities.weather.*;

public enum Season {
    FALL("Fall", new Clouds(), "Orange"),
    WINTER("Winter", new SnowStorm(), "White"),
    SPRING("Spring", new Rain(), "LightBlue"),
    SUMMER("Summer", new Drought(), "yellow");

    private final String name;
    private final WeatherEvent weather;
    private final String color;

    Season(String name, WeatherEvent weather, String color) {
        this.name = name;
        this.weather = weather;
        this.color = color;
    }

    public String getName() {
        return this.name;
    }

    public String getColor() {
        return this.color;
    }

    public WeatherEvent getWeather() {
        return weather;
    }
}
