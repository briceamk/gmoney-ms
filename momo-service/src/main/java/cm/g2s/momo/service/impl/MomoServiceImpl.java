package cm.g2s.momo.service.impl;

import cm.g2s.momo.configuration.MomoApiConfiguration;
import cm.g2s.momo.constant.MomoConstantType;
import cm.g2s.momo.exception.AppException;
import cm.g2s.momo.exception.BadRequestException;
import cm.g2s.momo.exception.ConflictException;
import cm.g2s.momo.exception.ResourceNotFoundException;
import cm.g2s.momo.service.MomoService;
import cm.g2s.momo.web.payload.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Slf4j
@Service("momoService")
@RequiredArgsConstructor
public class MomoServiceImpl implements MomoService {

    private final RestTemplate restTemplate;
    private final MomoApiConfiguration momoApiConfiguration;

    @Override
    public ApiUserResponse getApiUser() {
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(MomoConstantType.MOMO_X_REFERENCE_ID, momoApiConfiguration.getXReferenceId());
        headers.set(MomoConstantType.MOMO_OCP_APIM_SUBSCRIPTION_KEY, momoApiConfiguration.getOcpApimSubscriptionKey());

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<ApiUserResponse> response =
                restTemplate.exchange(momoApiConfiguration.getApiUserUrl().replace(MomoConstantType.MOMO_X_REFERENCE_ID_VARIABLE,
                        momoApiConfiguration.getXReferenceId()), HttpMethod.GET, entity, ApiUserResponse.class);
        if (response.getStatusCode().equals(HttpStatus.OK))
            return response.getBody();
        else if (response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
            log.error("");
            throw new BadRequestException(response.getBody().toString());
        } else {
            log.error("");
            throw new ResourceNotFoundException(response.getBody().toString());
        }

    }

    @Override
    public ApiKeyResponse generateApiKey() {

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(MomoConstantType.MOMO_X_REFERENCE_ID, momoApiConfiguration.getXReferenceId());
        headers.set(MomoConstantType.MOMO_OCP_APIM_SUBSCRIPTION_KEY, momoApiConfiguration.getOcpApimSubscriptionKey());

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<ApiKeyResponse> response =
                restTemplate.exchange(momoApiConfiguration.getApiKeyUrl().replace(MomoConstantType.MOMO_X_REFERENCE_ID_VARIABLE,
                        momoApiConfiguration.getXReferenceId()),  HttpMethod.POST, entity, ApiKeyResponse.class);

        if (response.getStatusCode().equals(HttpStatus.CREATED))
            return response.getBody();
        else if (response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
            log.error("");
            throw new BadRequestException(response.getBody().toString());
        } else {
            log.error("");
            throw new ResourceNotFoundException(response.getBody().toString());
        }
    }

    @Override
    public AccessTokenResponse getAccessToken() {

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(momoApiConfiguration.getXReferenceId(), momoApiConfiguration.getApiKey());
        headers.set(MomoConstantType.MOMO_OCP_APIM_SUBSCRIPTION_KEY, momoApiConfiguration.getOcpApimSubscriptionKey());

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<AccessTokenResponse> response =
                restTemplate.exchange(momoApiConfiguration.getTokenUrl(),  HttpMethod.POST, entity, AccessTokenResponse.class);

        if (response.getStatusCode().equals(HttpStatus.OK))
            return response.getBody();
        else if (response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
            log.error("");
            throw new BadRequestException(response.getBody().toString());
        } else {
            log.error("");
            throw new ResourceNotFoundException(response.getBody().toString());
        }
    }

    @Override
    public Object makeTransfer(String resourceId, TransferRequest transferRequest, AccessTokenResponse accessTokenResponse) throws JSONException {
            log.info("Sending money ....");
            HttpHeaders headers = new HttpHeaders();

            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(MomoConstantType.MOMO_X_REFERENCE_ID, resourceId);
            headers.set(MomoConstantType.MOMO_OCP_APIM_SUBSCRIPTION_KEY, momoApiConfiguration.getOcpApimSubscriptionKey());
            headers.set(MomoConstantType.MOMO_X_TARGET_ENVIRONMENT, momoApiConfiguration.getXTargetEnvironment());
            headers.set(MomoConstantType.AUTHORIZATION_HEADER, MomoConstantType.BEARER_TOKEN_TYPE + " " + accessTokenResponse.getAccess_token());
            HttpEntity<TransferRequest> entity = new HttpEntity<>(transferRequest, headers);

            ResponseEntity<Object> response = null;

            try {
                response = restTemplate.exchange(momoApiConfiguration.getTransferUrl(), HttpMethod.POST, entity, Object.class);
                if (response.getStatusCode().equals(HttpStatus.ACCEPTED)) {
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }

            }catch (Exception e) {

                if(e.getMessage().contains(String.valueOf(HttpStatus.CONFLICT.value()))) {
                    if(e.getMessage().contains("no body"))
                        return Reason.builder()
                                .message("No body")
                                .code("NO_BODY")
                                .status(409).build();
                    JSONObject jsonObject = new JSONObject(e.getMessage().replace("409 Conflict: [","").replace("]",""));
                    return Reason.builder()
                            .code(jsonObject.get("code").toString() != null? jsonObject.get("code").toString() : "")
                            .message(jsonObject.get("message").toString()!= null? jsonObject.get("message").toString() : "")
                            .status(409)
                            .build();
                }
                else  if(e.getMessage().contains(String.valueOf(HttpStatus.BAD_REQUEST.value()))) {
                    if(e.getMessage().contains("no body"))
                        return Reason.builder()
                                .message("No body")
                                .code("NO_BODY")
                                .status(404).build();
                    JSONObject jsonObject = new JSONObject(e.getMessage().replace("404 Bad Request: [","").replace("]",""));
                    return Reason.builder()
                            .code(jsonObject.get("code").toString() != null? jsonObject.get("code").toString() : "")
                            .message(jsonObject.get("message").toString()!= null? jsonObject.get("message").toString() : "")
                            .status(404)
                            .build();
                }
                else {
                    if(e.getMessage().contains("no body"))
                        return Reason.builder()
                                .message("No body")
                                .code("NO_BODY")
                                .status(500).build();
                    JSONObject jsonObject = new JSONObject(e.getMessage().replace("500 Server Error: [","").replace("]",""));
                    return Reason.builder()
                            .code(jsonObject.get("code").toString() != null? jsonObject.get("code").toString() : "")
                            .message(jsonObject.get("message").toString()!= null? jsonObject.get("message").toString() : "")
                            .status(500)
                            .build();
                }
            }
    }

    @Override
    public Object findTransferInfo(String resourceId, AccessTokenResponse accessTokenResponse) throws JSONException {

        log.info("Getting money transfer info ....");

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(MomoConstantType.MOMO_X_REFERENCE_ID, resourceId);
        headers.set(MomoConstantType.MOMO_OCP_APIM_SUBSCRIPTION_KEY, momoApiConfiguration.getOcpApimSubscriptionKey());
        headers.set(MomoConstantType.MOMO_X_TARGET_ENVIRONMENT, momoApiConfiguration.getXTargetEnvironment());
        headers.set(MomoConstantType.AUTHORIZATION_HEADER, MomoConstantType.BEARER_TOKEN_TYPE + " " + accessTokenResponse.getAccess_token());

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        try {
            ResponseEntity<TransferResponse> response =
                    restTemplate.exchange(momoApiConfiguration.getTransferInfoUrl().replace(MomoConstantType.MOMO_RESOURCE_ID, resourceId),  HttpMethod.GET, entity, TransferResponse.class);
            return response.getBody();
        } catch (Exception e) {
            if(e.getMessage().contains(String.valueOf(HttpStatus.CONFLICT.value()))) {
                if(e.getMessage().contains("no body"))
                    return Reason.builder()
                            .message("No body")
                            .code("NO_BODY")
                            .status(409).build();
                JSONObject jsonObject = new JSONObject(e.getMessage().replace("409 Conflict: [","").replace("]",""));
                return Reason.builder()
                        .code(jsonObject.get("code").toString() != null? jsonObject.get("code").toString() : "")
                        .message(jsonObject.get("message").toString()!= null? jsonObject.get("message").toString() : "")
                        .status(409)
                        .build();
            }
            else  if(e.getMessage().contains(String.valueOf(HttpStatus.BAD_REQUEST.value()))) {
                if(e.getMessage().contains("no body"))
                    return Reason.builder()
                            .message("No body")
                            .code("NO_BODY")
                            .status(404).build();
                JSONObject jsonObject = new JSONObject(e.getMessage().replace("404 Bad Request: [","").replace("]",""));
                return Reason.builder()
                        .code(jsonObject.get("code").toString() != null? jsonObject.get("code").toString() : "")
                        .message(jsonObject.get("message").toString()!= null? jsonObject.get("message").toString() : "")
                        .status(404)
                        .build();
            }
            else {
                if(e.getMessage().contains("no body"))
                    return Reason.builder()
                            .message("No body")
                            .code("NO_BODY")
                            .status(500).build();
                JSONObject jsonObject = new JSONObject(e.getMessage().replace("500 Server Error: [","").replace("]",""));
                return Reason.builder()
                        .code(jsonObject.get("code").toString() != null? jsonObject.get("code").toString() : "")
                        .message(jsonObject.get("message").toString()!= null? jsonObject.get("message").toString() : "")
                        .status(500)
                        .build();
            }
        }


    }
}
