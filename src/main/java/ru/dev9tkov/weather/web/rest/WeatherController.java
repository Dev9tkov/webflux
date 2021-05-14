package ru.dev9tkov.weather.web.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import ru.dev9tkov.weather.model.Weather;
import ru.dev9tkov.weather.service.WeatherService;

import java.time.Duration;

@RestController
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /** Метод all использует задержку публикации данных.
     * Сделано это для демонстрации сервиса с долгой загрузкой
     *
     * Браузер получает ответ с типом text/event-stream. Это заставляет браузер работать в реактивном режиме
     */
    @GetMapping(value = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Weather> all() {
        Flux<Weather> data = weatherService.all();
        Flux<Long> delay = Flux.interval(Duration.ofSeconds(3));
        return Flux.zip(data, delay).map(Tuple2::getT1);
    }

    @GetMapping(value = "/get/{id}")
    public Mono<Weather> get(@PathVariable Integer id) {
        return weatherService.findById(id);
    }

    @GetMapping(value = "/cityGreatThen/{t}")
    public Mono<Weather> cityGreatThen(@PathVariable Integer t) {
        return weatherService.cityGreatThen(t);
    }


}
