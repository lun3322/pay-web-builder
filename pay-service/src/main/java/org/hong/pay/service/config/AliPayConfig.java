package org.hong.pay.service.config;

import com.egzosn.pay.ali.api.AliPayConfigStorage;
import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.common.bean.CertStoreType;
import com.egzosn.pay.common.util.sign.SignUtils;
import com.tencent.cloud.polaris.context.config.PolarisContextProperties;
import com.tencent.polaris.configuration.api.core.ConfigFile;
import com.tencent.polaris.configuration.api.core.ConfigFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

//@Configuration
@RequiredArgsConstructor
@Slf4j
public class AliPayConfig {
    private final AlipayProperties alipayProperties;
    private final ConfigFileService configFileService;
    private final PolarisContextProperties polarisContextProperties;
    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public AliPayService aliPayService() throws UnsupportedEncodingException {
        log.info("初始化支付宝支付服务 {}", alipayProperties);

        AliPayConfigStorage aliPayConfigStorage = new AliPayConfigStorage();
        aliPayConfigStorage.setPid(alipayProperties.getPid());
        aliPayConfigStorage.setAppId(alipayProperties.getAppId());

        //设置为证书方式
        aliPayConfigStorage.setCertSign(true);
        //设置证书存储方式，这里为路径
        aliPayConfigStorage.setCertStoreType(CertStoreType.INPUT_STREAM);
        aliPayConfigStorage.setMerchantCert(getCert(alipayProperties.getMerchantCert()));
        aliPayConfigStorage.setAliPayRootCert(getCert(alipayProperties.getAliPayRootCert()));
        aliPayConfigStorage.setAliPayCert(getCert(alipayProperties.getAliPayCert()));
        aliPayConfigStorage.setKeyPrivate(alipayProperties.getKeyPrivate());

        aliPayConfigStorage.setNotifyUrl(alipayProperties.getNotifyUrl());

        aliPayConfigStorage.setSignType(SignUtils.RSA2.name());
        aliPayConfigStorage.setSeller(alipayProperties.getSeller());
        aliPayConfigStorage.setInputCharset("utf-8");
        //是否为测试账号，沙箱环境
        aliPayConfigStorage.setTest(true);

//        //请求连接池配置
//        HttpConfigStorage httpConfigStorage = new HttpConfigStorage();
//        //最大连接数
//        httpConfigStorage.setMaxTotal(20);
//        //默认的每个路由的最大连接数
//        httpConfigStorage.setDefaultMaxPerRoute(10);

        return new AliPayServiceFix(aliPayConfigStorage);
    }

    private InputStream getCert(String name) throws UnsupportedEncodingException {
        ConfigFile configFile = configFileService.getConfigFile(polarisContextProperties.getNamespace(), appName, name);
        String content = configFile.getContent();
        log.info(content);
        return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
    }
}
