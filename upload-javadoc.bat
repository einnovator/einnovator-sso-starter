SET NS=einnovator
SET POD=cdn-7c5699d77c-wrgvt

kubectl --namespace=%NS% exec %POD% -- mkdir -p /usr/share/nginx/html/docs/einnovator-sso-starter/latest
kubectl --namespace=%NS% cp  target/apidocs %POD%:/usr/share/nginx/html/docs/einnovator-sso-starter/latest




