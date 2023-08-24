package org.hong.pay.web.controller;

import com.tencent.cloud.polaris.context.config.PolarisContextProperties;
import com.tencent.polaris.configuration.api.core.ConfigFile;
import com.tencent.polaris.configuration.api.core.ConfigFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hong
 */
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final ConfigFileService configFileService;
    private final PolarisContextProperties polarisContextProperties;

    @GetMapping
    public String home() {
        return "home";
    }

    @GetMapping("config")
    public String config() {
        ConfigFile alipay = configFileService.getConfigFile(polarisContextProperties.getNamespace(), "koude-pay-alipay", "alipay/alipayPublicCert.crt");
        return alipay.getContent();
    }
}
