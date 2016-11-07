package com.ai.slp.product.api.product;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.PageInfoResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.product.api.product.interfaces.IProductManagerSV;
import com.ai.slp.product.api.product.param.*;
import com.ai.slp.product.constants.CommonTestConstants;
import com.google.gson.Gson;

/**
 * Created by jackieliu on 16/6/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:context/core-context.xml")
public class IProductManagerSVTest {
    @Autowired
    IProductManagerSV productManagerSV;

    @Test
    public void queryOtherSetOfProductTest(){
        ProductInfoQuery infoQuery = new ProductInfoQuery();
        infoQuery.setTenantId("SLP");
        infoQuery.setProductId("1000000000000004");
        OtherSetOfProduct otherSet = productManagerSV.queryOtherSetOfProduct(infoQuery);
        System.out.println(otherSet.toString());
    }

    @Test
    public void queryNoKeyAttrOfProd(){
        ProductInfoQuery infoQuery = new ProductInfoQuery();
        infoQuery.setTenantId("SLP");
        infoQuery.setProductId("1000000000000004");
        ProdNoKeyAttr noKeyAttr = productManagerSV.queryNoKeyAttrOfProd(infoQuery);
        System.out.println(noKeyAttr.getAttrInfoForProdList().size());
    }

    /**
     * 上架测试
     */
    @Test
    public void changeToInSaleTest(){
        ProductInfoQuery infoQuery = new ProductInfoQuery();
 //       infoQuery.setTenantId("SLP");
        infoQuery.setTenantId("changhong");
        infoQuery.setSupplierId("-1");
//        infoQuery.setProductId("1000000000000093");
        infoQuery.setProductId("0000000000000286");
        BaseResponse response = productManagerSV.changeToInSale(infoQuery);
        ResponseHeader header = response.getResponseHeader();
        System.out.println(header!=null?header.isSuccess():false);
    }
    
    /**
     * 手动下架测试
     */
    @Test
    public void prodInStoreTest(){
        ProductInfoQuery infoQuery = new ProductInfoQuery();
        infoQuery.setTenantId("changhong");
        infoQuery.setSupplierId("-1");
        infoQuery.setProductId("1000000000000096");
        BaseResponse response = productManagerSV.changeToInStore(infoQuery);
        ResponseHeader header = response.getResponseHeader();
        System.out.println(header!=null?header.isSuccess():false);
    }
    
    /**
     * 查询待编辑
     */
    @Test
    public void queryProductEditTest(){
    	ProductEditQueryReq req = new ProductEditQueryReq();
    	req.setTenantId("changhong");
    	req.setSupplierId("-1");
    	PageInfoResponse<ProductEditUp> queryProductEdit = productManagerSV.queryProductEdit(req);
    	
        Gson gson = new Gson();
		System.out.println(gson.toJson(queryProductEdit));
    	
    }
    
    /**
     * 查询在售商品--按上架时间倒序
     * jiawen
     */
    @Test
    public void searchInSaleTest(){
    	ProductQueryInfo queryInSale = new ProductQueryInfo();
    	queryInSale.setTenantId("changhong");
    	queryInSale.setSupplierId("-1");
    	List<String> stateList = new ArrayList<>();
		stateList.add("5");
		queryInSale.setStateList(stateList);
    	PageInfoResponse<ProductEditUp> inSale = productManagerSV.searchInSale(queryInSale);
    	Gson gson = new Gson();
    	System.out.println(gson.toJson(inSale));
    	
    }
    /**
     * 查询审核商品
     * jiawen
     */
    @Test
    public void searchAuditTest(){
    	ProductQueryInfo queryInSale = new ProductQueryInfo();
    	queryInSale.setTenantId("changhong");
    	queryInSale.setSupplierId("-1");
    	List<String> stateList = new ArrayList<>();
    	stateList.add("4");
    	queryInSale.setStateList(stateList);
    	PageInfoResponse<ProductEditUp> inSale = productManagerSV.searchInSale(queryInSale);
    	Gson gson = new Gson();
    	System.out.println(gson.toJson(inSale));
    	
    }

    /**
     * 对商品进行审核
     */
    @Test
    public void productCheck(){
        ProductCheckParam checkParam = new ProductCheckParam();
        checkParam.setTenantId(CommonTestConstants.COMMON_TENANT_ID);
        checkParam.setState("1");
        checkParam.setOperId(441l);
        List<String> prodIdList = new ArrayList<>();
        prodIdList.add("0000000000000176");
        checkParam.setProdIdList(prodIdList);
        BaseResponse response = productManagerSV.productCheck(checkParam);
        System.out.println(response.getResponseHeader().getIsSuccess());
    }
    
    /**
     * 查询被拒绝商品信息
     * jiawen
     */
    @Test
    public void queryProductRefuseTest(){
    	ProductInfoQuery queryReq = new ProductInfoQuery();
    	queryReq.setTenantId("changhong");
    	queryReq.setSupplierId("-1");
    	queryReq.setProductId("0000000000000134");
    	//获取最新的拒绝愿意  --  按操作时间倒序 获取第一条
		ProdStateLog refuse = productManagerSV.queryRefuseByPordId(queryReq);
    	if (StringUtil.isBlank(refuse.getProdId())) {
			System.out.println("没有记商品审核拒绝描述");
		}else{
			
			System.out.println(refuse.getRefuseDes());
		}
    } 
    
}
