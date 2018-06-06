package com.pinyougou.seckill.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.pinyougou.pay.service.WeixinPayService;

import com.pinyougou.pojo.TbSeckillOrder;
import com.pinyougou.seckill.service.SeckillOrderService;
import entity.Result;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
public class PayController {
    @Reference(timeout = 10000)
    private WeixinPayService weixinPayService;
    @Reference(timeout = 10000)
    private SeckillOrderService seckillOrderService;

    @RequestMapping("/createNative")
    public Map createNative(){
        //获取当前用户
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        TbSeckillOrder seckillOrder = seckillOrderService.searchOrderFromRedisByUserId(userId);
        //调用微信支付接口
        if(seckillOrder!=null){
            long fen= (long)(seckillOrder.getMoney().doubleValue()*100);//金额（分）
            return weixinPayService.createNative(seckillOrder.getId()+"",fen+"");
        }else{
            return new HashMap();
        }
//        IdWorker idWorker = new IdWorker();
//        return weixinPayService.createNative(idWorker.nextId()+"","1");

    }
    @RequestMapping("/queryPayStatus")
    public Result queryPayStatus(String out_trade_no){
        //获取当前用户
        String userId=SecurityContextHolder.getContext().getAuthentication().getName();
        Result result = null;
        int x=0;
        while (true){
            Map<String,String> map = weixinPayService.queryPayStatus(out_trade_no);
            if(map==null){
                result = new Result(false,"支付错误");
                break;
            }
            if(map.get("trade_state").equals("SUCCESS")){
                result =  new Result(true,"支付成功");
                //成功之后修改订单状态
                //orderService.updateOrderStatus(out_trade_no,map.get("transaction_id"));
                seckillOrderService.saveOrderFromRedisToDb(userId,
                    Long.valueOf(out_trade_no), map.get("transaction_id"));
                break;
            }
            try {
                Thread.sleep(3000);//间隔三秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            x++;
            if(x>=100){
                result = new Result(false,"二维码超时");

                //关闭支付
                Map<String,String> payResult = weixinPayService.closePay(out_trade_no);
                if(payResult != null && "FAIL".equals(payResult.get("return_code"))){
                    if("ORODERPAID".equals(payResult.get("err_code"))){
                        result =  new Result(true,"支付成功");
                        //成功之后修改订单状态
                        //orderService.updateOrderStatus(out_trade_no,map.get("transaction_id"));
                        seckillOrderService.saveOrderFromRedisToDb(userId,
                            Long.valueOf(out_trade_no), map.get("transaction_id"));
                    }
                }

                //删除订单
                if(result.isSuccess()==false){
                    seckillOrderService.deleteOrderFromRedis(userId,Long.valueOf(out_trade_no));
                }

                break;
            }
        }
       return result;
    }
}
