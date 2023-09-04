package org.hong.pay.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "alipay")
@Data
@EnableConfigurationProperties
public class AlipayProperties {
    /**
     * 商户应用id
     */
    private String appId;
    /**
     * 商户签约拿到的pid,partner_id的简称，合作伙伴身份等同于 partner
     */
    private String pid;
    /**
     * 商户收款账号
     */
    private String seller;
    /**
     * 应用私钥，rsa_private pkcs8格式 生成签名时使用
     */
    private String keyPrivate;
    /**
     * 应用公钥证书
     */
    private String merchantCert;
    /**
     * 支付宝公钥证书
     */
    private String aliPayCert;
    /**
     * 支付宝CA证书，根证书
     */
    private String aliPayRootCert;
    /**
     * 异步回调地址
     */
    private String notifyUrl;
    /**
     * 是否沙箱
     */
    private Boolean isSandbox = false;
}
