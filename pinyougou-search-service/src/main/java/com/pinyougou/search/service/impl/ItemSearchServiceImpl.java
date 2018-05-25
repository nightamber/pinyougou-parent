package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;

import java.util.*;

@Service(timeout = 5000)
public class ItemSearchServiceImpl implements ItemSearchService {
    @Autowired
    private SolrTemplate solrTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询品牌与规格列表
     * @param category 商品分类名称
     * @return
     */
    private Map searchBrandAndSpecList(String category){
         Map map = new HashMap<>();

        //获得模版ID
        Long typeId = (Long) redisTemplate.boundHashOps("itemCat").get(category);
        if(typeId!=null) {
            //根据模版ID 获取规格列表
            List brandList = (List) redisTemplate.boundHashOps("brandList").get(typeId);
            map.put("brandList", brandList);
            //获取规格列表
            List specList = (List) redisTemplate.boundHashOps("specList").get(typeId);
            map.put("specList",specList);

        }

        return map;

    }


    /**
     * 实现搜索方法
     * @param searchMap
     * @return
     */
    @Override
    public Map search(Map searchMap) {
        Map map = new HashMap<>();
        String keywords= (String) searchMap.get("keywords");
        searchMap.put("keywords",keywords.replace(" ",""));
//        SimpleQuery query = new SimpleQuery("*:*");
//        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
//        query.addCriteria(criteria);
//
//        ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);
//        map.put("rows",page.getContent());
        //高亮显示

        //1.查询列表
        map.putAll( searchList(searchMap));
        //2.分组查询商品分类列表
        List<String> catrgoryList = searchCatrgoryList(searchMap);
        map.put("categoryList",catrgoryList);
        //3.查询品牌与规格
        String category = (String) searchMap.get("category");
        if (!category.equals("")){
            Map map1 = searchBrandAndSpecList(category);
            map.putAll(map1);
        }else {
            if(catrgoryList.size()>0) {
                Map map1 = searchBrandAndSpecList(catrgoryList.get(0));
                map.putAll(map1);
            }
        }


        return map;
    }

    @Override
    public void importList(List list) {
        solrTemplate.saveBeans(list);
        solrTemplate.commit();
    }

    @Override
    public void deleteByGoodsIds(List goodsIdList) {
        System.out.println("删除商品ID"+goodsIdList);
        Query query = new SimpleQuery("*:*");
        Criteria criteria = new Criteria("item_goodsid").in(goodsIdList);
        query.addCriteria(criteria);
        solrTemplate.delete(query);
        solrTemplate.commit();
    }

    //查询列表
    private Map searchList(Map searchMap){
        Map map = new HashMap<>();

        //高亮选项初始化
        HighlightQuery query = new SimpleHighlightQuery();
        //在那里加
        //前缀 后缀
        //构建高亮选项对象
        HighlightOptions highlightQuery = new HighlightOptions().addField("item_title");//设置高亮字段
        highlightQuery.setSimplePrefix("<em style='color:red'>");
        highlightQuery.setSimplePostfix("</em>");
        //1设置高亮选项
        query.setHighlightOptions(highlightQuery);

        //1.1关键字查询

        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);

        //*******过滤*********
        //1.2按商品分类过滤
        if(!"".equals(searchMap.get("category"))) {//用户选择了分类
            FilterQuery filterQuery = new SimpleFilterQuery();
            Criteria filtercriteria = new Criteria("item_category").is(searchMap.get("category"));
            filterQuery.addCriteria(filtercriteria);
            query.addFilterQuery(filterQuery);
        }
        //1.3品牌过滤
        if(!"".equals(searchMap.get("brand"))) {//用户选择了品牌
            FilterQuery filterQuery = new SimpleFilterQuery();
            Criteria filtercriteria = new Criteria("item_brand").is(searchMap.get("brand"));
            filterQuery.addCriteria(filtercriteria);
            query.addFilterQuery(filterQuery);
        }
        //1.4规格过滤
        if(searchMap.get("spec")!=null) {//用户选择了分类
            Map<String,String> specMap = (Map<String, String>) searchMap.get("spec");
            for (String key:specMap.keySet()){
                FilterQuery filterQuery = new SimpleFilterQuery();
                Criteria filtercriteria = new Criteria("item_spec_"+key).is(specMap.get(key));
                filterQuery.addCriteria(filtercriteria);
                query.addFilterQuery(filterQuery);
            }
        }

        //1.5价格过滤
        if(!"".equals(searchMap.get("price"))){
            String[] price = ((String) searchMap.get("price")).split("-");

            if(!price[0].equals("0")){//如果区间起点不等于0

                FilterQuery filterQuery = new SimpleFilterQuery();
                Criteria filtercriteria = new Criteria("item_price").greaterThanEqual(price[0]);
                filterQuery.addCriteria(filtercriteria);
                query.addFilterQuery(filterQuery);
            }

            if(!price[1].equals("*")){//如果区间终点不等于*
                FilterQuery filterQuery = new SimpleFilterQuery();
                Criteria filtercriteria = new Criteria("item_price").lessThanEqual(price[1]);
                filterQuery.addCriteria(filtercriteria);
                query.addFilterQuery(filterQuery);
            }
        }

        //1.6 分类查询

        Integer pageNo = (Integer) searchMap.get("pageNo");
        if(pageNo == null){
            pageNo=1;
        }

        Integer pageSize = (Integer) searchMap.get("pageSize");
        if(pageSize == null){
            pageSize=20;
        }

        query.setOffset((pageNo-1)*pageSize);//起始索引
        query.setRows(pageSize);//每页记录数


        //1,7排序


        String sortValue = (String) searchMap.get("sort");
        String sortField= (String) searchMap.get("sortField");


        if(sortField !=null && sortValue != null){
            if(sortValue.equals("ASC")){
                Sort sort = new Sort(Sort.Direction.ASC, "item_" + sortField);
                query.addSort(sort);
            }

            if(sortValue.equals("DESC")){
                Sort sort = new Sort(Sort.Direction.DESC, "item_" + sortField);
                query.addSort(sort);
            }
        }


        //************高亮结果集************
        //高亮页对象
        HighlightPage<TbItem> page = solrTemplate.queryForHighlightPage(query, TbItem.class);
        //获得高亮入口集合(每条记录的高亮入口)
        List<HighlightEntry<TbItem>> entryList = page.getHighlighted();

        for (HighlightEntry<TbItem> entry:entryList){
            //获取高亮列表(高亮字段的个数)
            List<HighlightEntry.Highlight> highlightList = entry.getHighlights();
            /*测试
            for(HighlightEntry.Highlight highlight:highlightList){
                List<String> sns = highlight.getSnipplets();
                System.out.println(sns);
            }
            */
            if(highlightList.size()>0 && highlightList.get(0).getSnipplets().size()>0){


                TbItem item = entry.getEntity();
                item.setTitle(highlightList.get(0).getSnipplets().get(0));
            }
        }



        map.put("rows",page.getContent());
        map.put("totalPages",page.getTotalPages());//返回总页数
        map.put("total",page.getTotalElements());//返回总记录数
        return map;

    }


    /**
     * 分组查询（查询商品分类列表）
     * @param searchMap
     * @return
     */
    private List<String> searchCatrgoryList(Map searchMap){
        List<String> list = new ArrayList<String>();

        Query query =  new SimpleQuery();
        //关键字查询
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);

        //groug by 即设置分组选项
        GroupOptions groupOptions = new GroupOptions().addGroupByField("item_category");
        query.setGroupOptions(groupOptions);

        //获得分组页
        GroupPage<TbItem> page = solrTemplate.queryForGroupPage(query, TbItem.class);
        //获得分组结果对象
        GroupResult<TbItem> groupResult = page.getGroupResult("item_category");
        //分组入口
        Page<GroupEntry<TbItem>> groupEntries = groupResult.getGroupEntries();
        List<GroupEntry<TbItem>> content = groupEntries.getContent();
        for(GroupEntry<TbItem> entry:content){
           list.add(entry.getGroupValue());//分组的结果添加到返回
        }
        return list;
    }


}
