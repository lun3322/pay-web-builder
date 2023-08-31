package org.hong.pay.web.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.ijpay.alipay.AliPayApi;
import com.ijpay.alipay.AliPayApiConfig;
import com.ijpay.alipay.AliPayApiConfigKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("alipay")
@RequiredArgsConstructor
@Slf4j
public class AliPayController {
    @GetMapping("config")
    public AliPayApiConfig getCurrentConfig() {
        return AliPayApiConfigKit.getAliPayApiConfig();
    }

    /**
     * 获取二维码地址
     * 二维码支付
     */
    @GetMapping(value = "qr_pay")
    public String getQrPay() throws AlipayApiException {
        //获取对应的支付账户操作工具（可根据账户id）
//        PayOrder order = new PayOrder(
//                "订单title",
//                "摘要",
//                new BigDecimal("0.01"),
//                System.currentTimeMillis() + "",
//                AliTransactionType.SWEEPPAY);

        AlipayTradePrecreateModel alipayTradePrecreateModel = new AlipayTradePrecreateModel();
        alipayTradePrecreateModel.setOutTradeNo("order" + System.currentTimeMillis());
        alipayTradePrecreateModel.setTotalAmount("10");
        alipayTradePrecreateModel.setSubject("订单title");
        AlipayTradePrecreateResponse response = AliPayApi.tradePrecreatePayToResponse(alipayTradePrecreateModel, "https://ok.pve.lum114.com/alipay/qr_pay");
        return response.getQrCode();
    }
}
