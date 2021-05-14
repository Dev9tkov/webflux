package ru.dev9tkov.weather.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.dev9tkov.weather.model.Weather;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WeatherService {
    private final Map<Integer, Weather> weathers = new ConcurrentHashMap<>();
    {
        weathers.put(1, new Weather(1, "Msc", 20));
        weathers.put(2, new Weather(2, "SPb", 15));
        weathers.put(3, new Weather(3, "Bryansk", 15));
        weathers.put(4, new Weather(4, "Smolensk", 15));
        weathers.put(5, new Weather(5, "Kiev", 15));
        weathers.put(6, new Weather(6, "Minsk", 15));
    }

    /** Mono - поток состоящий из одного элемента или пустой поток */
    public Mono<Weather> findById(Integer id) {
        return Mono.justOrEmpty(weathers.get(id));
    }

    /** Flux - поток состоящий из набора объектов или пустой поток */

    public Flux<Weather> all() {
        return Flux.fromIterable(weathers.values());
    }

    public Mono<Weather> cityGreatThen(Integer t) {
        return Mono.justOrEmpty(
                weathers.values().stream()
                .filter(e->e.getTemperature() == t)
                .findFirst()
                .orElse(null)
        );
    }
}
