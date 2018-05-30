package com.pinyougou.manager.controller;

import java.util.List;

import com.alibaba.fastjson.JSON;


import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojogroup.Goods;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.sellergoods.service.GoodsService;

import entity.PageResult;
import entity.Result;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Reference
	private GoodsService goodsService;

	//@Reference(timeout = 50000)
	//private ItemSearchService itemSearchService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoods> findAll(){			
		return goodsService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return goodsService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param goods
	 * @return
	 */
//	@RequestMapping("/add")
//	public Result add(@RequestBody TbGoods goods){
//		try {
//			goodsService.add(goods);
//			return new Result(true, "增加成功");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new Result(false, "增加失败");
//		}
//	}
	
	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody Goods goods){
		try {
			goodsService.update(goods);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public Goods findOne(Long id){
		return goodsService.findOne(id);		
	}

	@Autowired
	private Destination queueSolrDeleteDestination;

	@Autowired
	private Destination topicPageDeleteDestination;
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(final Long [] ids){
		try {
			goodsService.delete(ids);

			jmsTemplate.send(queueSolrDeleteDestination, new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {

					return session.createObjectMessage(ids);
				}
			});

			//删除每个服务器上的商品详情页

			jmsTemplate.send(topicPageDeleteDestination, new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					return session.createObjectMessage(ids);
				}
			});

			//itemSearchService.deleteByGoodsIds(Arrays.asList(ids));
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbGoods goods, int page, int rows  ){
		return goodsService.findPage(goods, page, rows);		
	}
	@Autowired
	private JmsTemplate jmsTemplate;
	//索引库 点对点
	@Autowired
	private Destination queueSolrDestination;
	//详情页 发送订阅
	@Autowired
	private Destination topicPageDestination;
	@RequestMapping("/updateStatus")
	public Result updateStatus(Long[] ids,String status){
		try{
			goodsService.updateStatus(ids,status);
			//按照SPU id 查询 sku 列表（状态1）
			if(status.equals("1")){
				List<TbItem> itemList = goodsService.findItemListByGoodsIdandStatus(ids, status);
				//调用搜索接口实现数据批量导入
				if(itemList.size()>0){
					//itemSearchService.importList(itemList);
				}else {
					System.out.println("没有明细数据");
				}
				final String jsonString = JSON.toJSONString(itemList);//转化JSON传输

				jmsTemplate.send(queueSolrDestination, new MessageCreator() {
					@Override
					public Message createMessage(Session session) throws JMSException {
						return session.createTextMessage(jsonString);
					}
				});


				//******生产商品详细页************
				for(final Long goodsId:ids){
					//itemPageService.genItemHtml(goodsId);

					jmsTemplate.send(topicPageDestination, new MessageCreator() {
						@Override
						public Message createMessage(Session session) throws JMSException {
							return session.createTextMessage(goodsId+"");
						}
					});


				}
			}
			return  new Result(true,"修改状态成功");
		}catch (Exception e){
			e.printStackTrace();
			return new Result(false,"修改状态失败");
		}
	}

//	@Reference(timeout = 50000)
//	private ItemPageService itemPageService;

	@RequestMapping("/genHtml")
	public void genHtml(Long goodsId){
		//itemPageService.genItemHtml(goodsId);
	}


}
