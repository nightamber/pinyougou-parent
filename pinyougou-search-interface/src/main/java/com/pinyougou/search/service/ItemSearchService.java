package com.pinyougou.search.service;

import java.util.List;
import java.util.Map;

public interface ItemSearchService {
    Map search(Map searchMap);

    /**
     * 导入数据
     * @param list
     */

    void importList(List list);

    /**
     * 删除商品列表
     * @param goodsIdList
     */
    void deleteByGoodsIds(List goodsIdList);
}
