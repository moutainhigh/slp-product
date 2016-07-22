package com.ai.slp.product.service.business.impl;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.paas.ipaas.util.JSonUtil;
import com.ai.slp.product.api.productcat.param.ProductCatInfo;
import com.ai.slp.product.cache.prodCat.ProdCatCache;
import com.ai.slp.product.constants.ErrorCodeConstants;
import com.ai.slp.product.dao.mapper.bo.ProductCat;
import com.ai.slp.product.service.business.interfaces.IProductCatBusiSV;
import com.ai.slp.product.service.business.interfaces.IProductCatQueryBusiSV;
import com.ai.slp.product.util.IPaasCatUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackieliu on 16/7/22.
 */
@Service
public class IProductCatQueryBusiSVImpl implements IProductCatQueryBusiSV {
    private static Logger logger = LoggerFactory.getLogger(IProductCatQueryBusiSVImpl.class);
    @Autowired
    IProductCatBusiSV productCatBusiSV;
    @Autowired
    ProdCatCache catCache;
    /**
     * 查询指定标识的类目信息
     *
     * @param tenantId
     * @param catId
     * @return
     */
    @Override
    public ProductCatInfo queryById(String tenantId, String catId) {
        ICacheClient cacheClient = IPaasCatUtils.getCacheClient();
        String catStr = cacheClient.hget(IPaasCatUtils.genMcsCatInfoKey(tenantId),catId);
        ProductCatInfo catInfo = new ProductCatInfo();
        if (StringUtils.isNotBlank(catStr)) {
            ProductCat cat = JSonUtil.fromJSon(catStr,ProductCat.class);
            BeanUtils.copyProperties(catInfo,cat);
        }//若缓存中没有,则查询数据库
        else {
            catInfo = productCatBusiSV.queryByCatId(tenantId,catId);
            catCache.flushCatInfo(tenantId,catId);
        }

        if (catInfo == null){
            logger.error("The cat is null,tenantId={},catId={}",tenantId,catId);
            throw new BusinessException(ErrorCodeConstants.ProductCat.CAT_NO_EXIST,
                    "类目不存在,租户ID:"+tenantId+",类目ID:"+catId);
        }
        return catInfo;
    }

    /**
     * 查询类目的类目链
     *
     * @param tenantId
     * @param productCatId
     * @return
     */
    @Override
    public List<ProductCatInfo> queryLinkOfCatById(String tenantId, String productCatId) {
        List<ProductCatInfo> catInfoList = new ArrayList<>();
        queryCatFoLinkById(catInfoList,tenantId,productCatId);
        return catInfoList;
    }

    private void queryCatFoLinkById(List<ProductCatInfo> catInfoList,String tenantId, String productCatId){
        ProductCatInfo catInfo = queryById(tenantId,productCatId);
        if (catInfo==null)
            return;
        //已经达到根目录
        if (catInfo.getParentProductCatId()==null){
            catInfoList.add(catInfo);
            return;
            //若不是跟类目,则继续查询
        }else
            queryCatFoLinkById(catInfoList,tenantId,catInfo.getParentProductCatId());
        catInfoList.add(catInfo);
    }
}
