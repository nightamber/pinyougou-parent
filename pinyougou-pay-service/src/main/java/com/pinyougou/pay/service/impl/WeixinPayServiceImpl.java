package com.pinyougou.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.wxpay.sdk.WXPayUtil;
import com.pinyougou.pay.service.WeixinPayService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import util.HttpClient;

@Service
public class WeixinPayServiceImpl implements WeixinPayService {

    @Value("${appid}")
    private String appid;
    @Value("${partner}")
    private String partner;
    @Value("${partnerkey}")
    private String partnerkey;
    @Value("${notifyurl}")
    private String notify_url;


    @Override
    public Map createNative(String out_trade_no, String total_fee) {
        //1.参数封装
        //根据官方文档 选择必须的
        Map param = new HashMap<>();
        param.put("appid", appid);//公众号ID
        param.put("mch_id", partner);//商铺号
        param.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
        param.put("body", "小熊先生");
        param.put("out_trade_no", out_trade_no);
        param.put("total_fee", total_fee);
        param.put("notify_url", notify_url);
        param.put("trade_type", "NATIVE");
        param.put("spbill_create_ip","127.0.0.1");
        try {
            String xmlParam =WXPayUtil.generateSignedXml(param, partnerkey);
            System.out.println(xmlParam);
            //2.发送请求
            HttpClient httpClient =new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);
            httpClient.post();
            //3.获取结果
            String xmlResult = httpClient.getContent();
            Map<String, String> mapResult = WXPayUtil.xmlToMap(xmlResult);
            System.out.println("mapResult="+mapResult.toString());
            System.out.println("================================================");
            Map map = new HashMap();
            map.put("code_url",mapResult.get("code_url"));//生产支付二维码的链接地址
            map.put("total_fee",total_fee);//金额
            map.put("out_trade_no",out_trade_no);//订单号
            System.out.println(map.toString());
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap();
        }

    }

    @Override
    public Map queryPayStatus(String out_trade_no) {
        Map param =new HashMap();
        param.put("appid", appid);//公众账号 ID
        param.put("mch_id", partner);//商户号
        param.put("out_trade_no", out_trade_no);//订单号
        param.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
        String url="https://api.mch.weixin.qq.com/pay/orderquery";
        try {
            String xmlParam = WXPayUtil.generateSignedXml(param, partnerkey);
            HttpClient httpClient = new HttpClient(url);
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);
            httpClient.post();
            String xmlResult = httpClient.getContent();
            Map<String, String> map = WXPayUtil.xmlToMap(xmlResult);
            System.out.println("queryAPI"+xmlResult);
            return map;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
