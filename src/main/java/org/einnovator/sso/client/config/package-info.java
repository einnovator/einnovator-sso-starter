/**
 * Configuration related artifacts for SSO Client.
 * 
 * <p>Application configuration is usually done via {@code application.yml} file:
 * 
 * <p>Example configuration in {@code application.yaml}:
 * <p>sso:
 * <p>  server : https://sso.mydomain.com
 * <p>  client-id: myapp
 * <p>  client-secret: myapp$secret
 * <p>  registration:
 * <p>    auto: true
 * <p>    roles:
 * <p>    - name: "CUSTOMER_MANAGER"
 * <p>      display-name: "Customer Manager"
 * <p>      type: GLOBAL
 * <p>      description: "User with this role can manage Customers" 
 * 
 * @see org.einnovator.sso.client.model.SsoRegistration
 * @see org.einnovator.sso.client.SsoClient
 * @author support@einnovator.org
 *
 *
 */
package org.einnovator.sso.client.config;