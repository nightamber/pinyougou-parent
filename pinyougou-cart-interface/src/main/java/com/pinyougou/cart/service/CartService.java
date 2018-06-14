package com.pinyougou.cart.service;

import com.pinyougou.pojogroup.Cart;
import java.util.List;

public interface CartService {

    public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num);

    List<Cart> findCartListFromRedis(String username);

    void saveCartListToRedis(String username, List<Cart> cartList);

    List<Cart> mergeCartList(List<Cart> cartList1,List<Cart> cartList2);
}
