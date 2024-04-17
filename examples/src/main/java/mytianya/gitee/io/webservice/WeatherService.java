package mytianya.gitee.io.webservice;



import java.util.List;

public interface WeatherService {
    String getWeather(String city);
    List<String> getWeather15Day(String city);
    String test();
}
