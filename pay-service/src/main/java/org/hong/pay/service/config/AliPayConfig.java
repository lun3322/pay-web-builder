package org.hong.pay.service.config;

import com.egzosn.pay.ali.api.AliPayConfigStorage;
import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.common.bean.CertStoreType;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.egzosn.pay.common.util.sign.SignUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AliPayConfig {
    private final AlipayProperties alipayProperties;

    @Bean
    public AliPayService aliPayService() {
        log.info("初始化支付宝支付服务 {}", alipayProperties);

        AliPayConfigStorage aliPayConfigStorage = new AliPayConfigStorage();
        aliPayConfigStorage.setPid(alipayProperties.getPid());
        aliPayConfigStorage.setAppId(alipayProperties.getAppId());

        //设置为证书方式
        aliPayConfigStorage.setCertSign(true);
        //设置证书存储方式，这里为路径
        aliPayConfigStorage.setCertStoreType(CertStoreType.CLASS_PATH);
        aliPayConfigStorage.setMerchantCert(alipayProperties.getMerchantCert());
        aliPayConfigStorage.setAliPayRootCert(alipayProperties.getAliPayRootCert());
        aliPayConfigStorage.setAliPayCert(alipayProperties.getAliPayCert());
        aliPayConfigStorage.setKeyPrivate(alipayProperties.getKeyPrivate());

        aliPayConfigStorage.setNotifyUrl(alipayProperties.getNotifyUrl());

        aliPayConfigStorage.setSignType(SignUtils.RSA2.name());
        aliPayConfigStorage.setSeller(alipayProperties.getSeller());
        aliPayConfigStorage.setInputCharset("utf-8");
        //是否为测试账号，沙箱环境
        aliPayConfigStorage.setTest(true);

        //请求连接池配置
        HttpConfigStorage httpConfigStorage = new HttpConfigStorage();
        //最大连接数
        httpConfigStorage.setMaxTotal(20);
        //默认的每个路由的最大连接数
        httpConfigStorage.setDefaultMaxPerRoute(10);

        return new AliPayServiceFix(aliPayConfigStorage, httpConfigStorage);
    }
}