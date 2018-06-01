package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.pojogroup.Cart;

import entity.Result;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.CookieUtil;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Reference(timeout = 60000)
    private CartService cartService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @RequestMapping("/findCartList")
    public List<Cart> findCartList() {

        //得到登陆人账号,判断当前是否有人登陆
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("获取当前登录人" + username);
        //从cookie 提取购物车
        String carListString = CookieUtil.getCookieValue(request, "cartList", "UTF-8");
        if (carListString == null || carListString.equals("")) {
            carListString = "[]";
        }
        List<Cart> cartList_cookie = JSON.parseArray(carListString, Cart.class);

        if (username.equals("anonymousUser")) {//如果没用登录


            return cartList_cookie;
        } else {//登录
            List<Cart> cartList_redis = cartService.findCartListFromRedis(username);
            if (cartList_cookie.size() > 0) {
                List<Cart> cartList = cartService.mergeCartList(cartList_cookie, cartList_redis);
                cartService.saveCartListToRedis(username, cartList);
                CookieUtil.deleteCookie(request, response, "cartList");
                System.out.println("执行了合并购物车");
                return cartList;
            }

            return cartList_redis;
        }


    }


    @RequestMapping("/addGoodsToCartList")
    public Result addGoodsToCartList(Long itemId, Integer num) {
        //得到登陆人账号,判断当前是否有人登陆
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            List<Cart> cartList = findCartList();
            cartList = cartService.addGoodsToCartList(cartList, itemId, num);
            if (username.equals("anonymousUser")) {

                CookieUtil.setCookie(request, response, "cartList", JSON.toJSONString(cartList),
                    3600 * 24, "UTF-8");
                System.out.println("向cookie 存入数据");
            } else {
                cartService.saveCartListToRedis(username, cartList);
            }
            return new Result(true, "添加成功");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败");
        }
    }


}
