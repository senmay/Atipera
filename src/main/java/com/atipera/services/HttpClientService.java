package com.atipera.services;

import com.atipera.exceptions.UserNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.atipera.exceptions.ErrorMessages.USER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class HttpClientService {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;
    public <T> T sendRequest(HttpRequest request, Class<T> responseType) {
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), responseType);
            } else if (response.statusCode() == 404){
                throw new UserNotFoundException(USER_NOT_FOUND);
            } else {
                log.warn("Error: Unable to fetch from API response. Status code: " + response.statusCode());
                throw new IOException("Error fetching from API: Status code " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            log.error("Exception occurred while sending HTTP request: ", e);
            throw new RuntimeException(e);
        }
    }
}
