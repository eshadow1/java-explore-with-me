package ru.practicum.explorewithmestatclient.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explorewithmestatclient.model.InfoToGetStatistic;
import ru.practicum.explorewithmestatdto.EndpointHitDto;
import ru.practicum.explorewithmestatdto.utils.ParamRest;

import javax.validation.Valid;
import java.util.Map;

@Service
public class StatisticClient extends BaseClient {

    @Autowired
    public StatisticClient(@Value("${statistic-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> addHits(EndpointHitDto endpointHitDto) {
        return post(ParamRest.HITS_CONTROLLER, endpointHitDto);
    }

    public ResponseEntity<Object> getStats(@Valid InfoToGetStatistic infoToGetStatistic) {
        if (infoToGetStatistic.getUnique() != null) {
            Map<String, Object> parameters = Map.of(
                    "start", infoToGetStatistic.getStart(),
                    "end", infoToGetStatistic.getEnd(),
                    "uris", infoToGetStatistic.getUris(),
                    "unique", infoToGetStatistic.getUnique()
            );

            return get(ParamRest.STATS_CONTROLLER + "?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
        } else {
            Map<String, Object> parameters = Map.of(
                    "start", infoToGetStatistic.getStart(),
                    "end", infoToGetStatistic.getEnd(),
                    "uris", infoToGetStatistic.getUris()
            );

            return get(ParamRest.STATS_CONTROLLER + "?start={start}&end={end}&uris={uris}", parameters);
        }
    }
}
