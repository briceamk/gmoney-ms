package cm.g2s.momo.service.impl;

import cm.g2s.momo.configuration.MomoApiConfiguration;
import cm.g2s.momo.service.MomoService;
import cm.g2s.momo.web.payload.ApiUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service("momoService")
@RequiredArgsConstructor
public class MomoServiceImpl implements MomoService {

    private final RestTemplate restTemplate;
    private final MomoApiConfiguration momoApiConfiguration;

    @Override
    public ApiUserResponse getApiUser(String xReferenceId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set
        return null;
    }
}
