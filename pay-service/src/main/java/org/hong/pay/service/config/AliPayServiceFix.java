package org.hong.pay.service.config;

import com.egzosn.pay.ali.api.AliPayConfigStorage;
import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.ali.bean.AliPayConst;
import com.egzosn.pay.common.http.HttpConfigStorage;

import static com.egzosn.pay.ali.bean.AliPayConst.HTTPS_REQ_URL;

public class AliPayServiceFix extends AliPayService {

    @Override
    public String getReqUrl() {
        final String https_sandbox_url = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
        return payConfigStorage.isTest() ? https_sandbox_url : HTTPS_REQ_URL;
    }

    public AliPayServiceFix(AliPayConfigStorage payConfigStorage, HttpConfigStorage configStorage) {
        super(payConfigStorage, configStorage);
    }
}
