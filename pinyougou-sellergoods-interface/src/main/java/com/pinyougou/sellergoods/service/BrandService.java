package com.pinyougou.sellergoods.service;

import java.util.List;

import com.pinyougou.pojo.TbBrand;
import entity.PageResult;

/**
 * 品牌接口
 * @author Mao Yiding
 *
 */
public interface BrandService {

	public List<TbBrand> findAll();

	public PageResult findPage(int pageNum,int pageSize);
//
	void add(TbBrand brand);
//
	void update(TbBrand brand);

	TbBrand findOne(Long id);
//
	void delete(Long[] ids);
//
	public PageResult findPage(TbBrand brand,int pageNum,int pageSize);
}
