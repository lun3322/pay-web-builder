package org.hong.pay.service.config;

import com.egzosn.pay.ali.api.AliPayConfigStorage;
import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.common.http.HttpConfigStorage;

public class AliPayServiceFix extends AliPayService {

    @Override
    public String getReqUrl() {
        return "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    }

    public AliPayServiceFix(AliPayConfigStorage payConfigStorage, HttpConfigStorage configStorage) {
        super(payConfigStorage, configStorage);
    }
}
