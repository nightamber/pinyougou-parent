package com.pinyougou.solrutil;


import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SolrUtil {
    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private SolrTemplate solrTemplate;

    public void importItemData(){
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("1");//审核通过的
        List<TbItem> itemList = itemMapper.selectByExample(example);

        System.out.println("---商品列表---");
        for(TbItem item:itemList){
            System.out.println(item.getId()+" "+item.getTitle()+" "+item.getPrice());


            Map<String,String> specMap = JSON.parseObject(item.getSpec(), Map.class);//从数据库中提取规格json字符串变成map
            item.setSpecMap(specMap);//动态域注入


        }
        //批量导入索引
        solrTemplate.saveBeans(itemList);
        solrTemplate.commit();


        System.out.println("---结束---");

    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
        SolrUtil solrUtil = (SolrUtil) context.getBean("solrUtil");
        solrUtil.importItemData();
    }
}
