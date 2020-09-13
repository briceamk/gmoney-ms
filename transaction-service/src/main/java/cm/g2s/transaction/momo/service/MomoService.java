package cm.g2s.transaction.momo.service;

import cm.g2s.transaction.momo.payload.AccessTokenResponse;
import cm.g2s.transaction.momo.payload.ApiKeyResponse;
import cm.g2s.transaction.momo.payload.ApiUserResponse;
import cm.g2s.transaction.momo.payload.TransferRequest;
import org.codehaus.jettison.json.JSONException;

public interface MomoService {
    ApiUserResponse getApiUser();

    ApiKeyResponse generateApiKey();

    AccessTokenResponse getAccessToken();

    Object makeTransfer(String resourceId, TransferRequest transferRequest, AccessTokenResponse accessTokenResponse) throws JSONException;

    Object findTransferInfo(String resourceId, AccessTokenResponse accessTokenResponse) throws JSONException;
}
