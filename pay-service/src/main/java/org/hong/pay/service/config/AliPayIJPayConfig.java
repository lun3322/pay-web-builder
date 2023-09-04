package org.hong.pay.service.config;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.http.HttpUtil;
import com.alipay.api.AlipayApiException;
import com.ijpay.alipay.AliPayApiConfig;
import com.ijpay.alipay.AliPayApiConfigKit;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AliPayIJPayConfig {
    private final AlipayProperties alipayProperties;
    private final ConfigClientProperties configClientProperties;
    private final Environment environment;
    @Value("${spring.application.name}")
    private String appName;

    @PostConstruct
    public void initConfig() throws AlipayApiException {
        log.info(configClientProperties.toString());
        AliPayApiConfig result = AliPayApiConfig.builder();

        final String httpsSandboxUrl = alipayProperties.getIsSandbox() ?
                "https://openapi-sandbox.dl.alipaydev.com/gateway.do" :
                "https://openapi.alipay.com/gateway.do";

        result.setServiceUrl(httpsSandboxUrl);
        result.setAppId(alipayProperties.getAppId());
        result.setPrivateKey(alipayProperties.getKeyPrivate());
        result.setCharset("UTF-8");
        result.setSignType("RSA2");
        result.setAliPayCertContent(getCert(alipayProperties.getAliPayCert()));
        result.setAppCertContent(getCert(alipayProperties.getMerchantCert()));
        result.setAliPayRootCertContent(getCert(alipayProperties.getAliPayRootCert()));
        result.buildByCertContent();

        AliPayApiConfigKit.putApiConfig(result);
    }

    private String getCert(String name) {
        String url = UrlBuilder.of(configClientProperties.getUri()[0])
                .addPath(appName)
                .addPath(environment.getActiveProfiles()[0])
                .addPath(configClientProperties.getLabel())
                .addPath(name)
                .toString();

        log.info(url);
        return HttpUtil.get(url);
    }
}
