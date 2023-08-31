package org.hong.pay.web.controller;

import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.ali.bean.AliTransactionType;
import com.egzosn.pay.common.bean.PayOrder;
import com.egzosn.pay.web.support.HttpRequestNoticeParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

//@RestController
@RequestMapping("pay/alipay")
@RequiredArgsConstructor
@Slf4j
public class PayController {
    private final AliPayService service;

    /**
     * 获取二维码地址
     * 二维码支付
     */
    @GetMapping(value = "qr_pay")
    public String getQrPay() {
        //获取对应的支付账户操作工具（可根据账户id）
        PayOrder order = new PayOrder(
                "订单title",
                "摘要",
                new BigDecimal("0.01"),
                System.currentTimeMillis() + "",
                AliTransactionType.SWEEPPAY);
        return service.getQrPay(order);
    }

    /**
     * 支付回调地址
     */
    @PostMapping(value = "pay_back")
    public String payBack(HttpServletRequest request) {
        //业务处理在对应的PayMessageHandler里面处理，在哪里设置PayMessageHandler，详情查看com.egzosn.pay.common.api.PayService.setPayMessageHandler()
        HttpRequestNoticeParams param = new HttpRequestNoticeParams(request);
        log.info("支付回调: {}", param);

        return service.payBack(param).toMessage();
    }
}
