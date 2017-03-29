package com.ai.slp.product.util;

import java.util.ArrayList;
import java.util.List;

import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.paas.ipaas.search.vo.SearchCriteria;
import com.ai.paas.ipaas.search.vo.SearchOption;
import com.ai.paas.ipaas.util.StringUtil;
import com.ai.slp.product.api.product.param.ProductEditQueryReq;
import com.ai.slp.product.api.product.param.ProductQueryInfo;
import com.ai.slp.product.constants.SearchFieldConfConstants;

public class CriteriaUtils {

	public static List<SearchCriteria> commonConditions(ProductEditQueryReq productEditParam){
		List<SearchCriteria> searchfieldVos = new ArrayList<SearchCriteria>();
		/**
		 * 商品标识
		 */
		if (!StringUtil.isBlank(productEditParam.getProdId())) {
			searchfieldVos.add(new SearchCriteria(com.ai.slp.product.constants.SearchFieldConfConstants.PRODUCT_ID, productEditParam.getProdId(),new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
		}
		/**
		 * 商品名称
		 */
		if (!StringUtil.isBlank(productEditParam.getProdName())) {
			searchfieldVos.add(new SearchCriteria(com.ai.slp.product.constants.SearchFieldConfConstants.PRODUCT_NAME, productEditParam.getProdName(),new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
		}
		/**
		 * 商品类型
		 */
		if (!StringUtil.isBlank(productEditParam.getProductType())) {
			searchfieldVos.add(new SearchCriteria(com.ai.slp.product.constants.SearchFieldConfConstants.PRODUCT_TYPE, productEditParam.getProductType(),new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
		}
		/**
		 * 商品类型
		 */
		if (!StringUtil.isBlank(productEditParam.getSupplierId())) {
			searchfieldVos.add(new SearchCriteria(com.ai.slp.product.constants.SearchFieldConfConstants.SUPPLIER, productEditParam.getSupplierId(),new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
		}
		/**
		 * 商品状态
		 */
		if (!CollectionUtil.isEmpty(productEditParam.getStateList())) {
			SearchCriteria searchCriteria = new SearchCriteria();
			SearchOption option = new SearchOption();
			option.setSearchLogic(SearchOption.SearchLogic.must);
			option.setSearchType(SearchOption.SearchType.term);
			/**
			 * 转换类型
			 */
			List<Object> objects = new ArrayList<>();
			for(String state : productEditParam.getStateList()){
				objects.add((Object)state);
			}
			searchCriteria.setFieldValue( objects );
			searchCriteria.setField(SearchFieldConfConstants.STATE);
			searchCriteria.setOption(option);
			searchfieldVos.add(searchCriteria);
		}
		return searchfieldVos;
	}
	
	public static List<SearchCriteria> commonConditions(ProductQueryInfo productQueryInfo){
		List<SearchCriteria> searchfieldVos = new ArrayList<SearchCriteria>();
		/**
		 * 商品标识
		 */
		if (!StringUtil.isBlank(productQueryInfo.getProdId())) {
			searchfieldVos.add(new SearchCriteria(com.ai.slp.product.constants.SearchFieldConfConstants.PRODUCT_ID, productQueryInfo.getProdId(),new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
		}
		/**
		 * 商品名称
		 */
		if (!StringUtil.isBlank(productQueryInfo.getProdName())) {
			searchfieldVos.add(new SearchCriteria(com.ai.slp.product.constants.SearchFieldConfConstants.PRODUCT_NAME, productQueryInfo.getProdName(),new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
		}
		/**
		 * 商品类型
		 */
		if (!StringUtil.isBlank(productQueryInfo.getProductType())) {
			searchfieldVos.add(new SearchCriteria(com.ai.slp.product.constants.SearchFieldConfConstants.PRODUCT_TYPE, productQueryInfo.getProductType(),new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
		}
		/**
		 * 商品类型
		 */
		if (!StringUtil.isBlank(productQueryInfo.getSupplierId())) {
			searchfieldVos.add(new SearchCriteria(com.ai.slp.product.constants.SearchFieldConfConstants.SUPPLIER, productQueryInfo.getSupplierId(),new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
		}
		/**
		 * 商品状态
		 */
		if (!CollectionUtil.isEmpty(productQueryInfo.getStateList())) {
			SearchCriteria searchCriteria = new SearchCriteria();
			SearchOption option = new SearchOption();
			option.setSearchLogic(SearchOption.SearchLogic.must);
			option.setSearchType(SearchOption.SearchType.term);
			/**
			 * 转换类型
			 */
			List<Object> objects = new ArrayList<>();
			for(String state : productQueryInfo.getStateList()){
				objects.add((Object)state);
			}
			searchCriteria.setFieldValue( objects );
			searchCriteria.setField(SearchFieldConfConstants.STATE);
			searchCriteria.setOption(option);
			searchfieldVos.add(searchCriteria);
		}
		return searchfieldVos;
	}
}