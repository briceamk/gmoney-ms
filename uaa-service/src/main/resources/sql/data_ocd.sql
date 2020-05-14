INSERT INTO oauth_client_details (client_id, client_secret, web_server_redirect_uri, scope, access_token_validity, refresh_token_validity, resource_ids, authorized_grant_types, additional_information)
SELECT
'mobile', '{bcrypt}$2y$12$/hFpaWtGjPIbE2v4tMBipeZ8Oos2/77EAUnZjaucflR9VRjYAliEW', 'http://localhost:8080/code', 'READ,WRITE', '3600', '10000', 'account,company,partner,rule,loan,uaa', 'authorization_code,password,refresh_token,implicit', '{}'
WHERE NOT EXISTS (
    SELECT * FROM oauth_client_details WHERE client_id = 'mobile'
);

