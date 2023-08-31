package org.hong.pay.service.config;

import com.alipay.api.AlipayApiException;
import com.ijpay.alipay.AliPayApiConfig;
import com.ijpay.alipay.AliPayApiConfigKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AliPayIJPayConfig {
    private final AlipayProperties alipayProperties;

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public AliPayApiConfig aliPayApiConfig() throws AlipayApiException {
        AliPayApiConfig result = AliPayApiConfig.builder();

        final String https_sandbox_url = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";

        result.setServiceUrl(https_sandbox_url);
        result.setAppId(alipayProperties.getAppId());
        result.setPrivateKey(alipayProperties.getKeyPrivate());
        result.setCharset("UTF-8");
        result.setSignType("RSA2");
        result.setAliPayCertContent(getCert(alipayProperties.getAliPayCert()));
        result.setAppCertContent(getCert(alipayProperties.getMerchantCert()));
        result.setAliPayRootCertContent(getCert((alipayProperties.getAliPayRootCert())));
        result.buildByCertContent();

        AliPayApiConfigKit.putApiConfig(result);
        return result;
    }

    private String getCert(String name) {
        return name;
    }
}
