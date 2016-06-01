package com.ai.slp.product.service.business.interfaces;

import com.ai.slp.product.api.product.param.SkuInfoMultSave;
import com.ai.slp.product.api.product.param.SkuSetForProduct;
import com.ai.slp.product.api.webfront.param.ProductSKUConfigResponse;
import com.ai.slp.product.api.webfront.param.ProductSKUResponse;
import com.ai.slp.product.dao.mapper.bo.product.ProdSku;

/**
 * 商品SKU业务操作
 * Created by jackieliu on 16/5/12.
 */
public interface IProdSkuBusiSV {

    /**
     * 更新商品SKU信息
     * @param saveInfo
     */
    public void updateSkuOfProduct(SkuInfoMultSave saveInfo);

    /**
     * 查询指定商品下的SKU信息
     *
     * @param tenantId
     * @param prodId
     * @return
     */
    public SkuSetForProduct querySkuByProdId(String tenantId, String prodId);

    /**
     * 添加单个sku
     * @param prodSku
     * @return
     */
    public int addSku(ProdSku prodSku);

    /**
     * 根据SKU标识或SKU属性串查询SKU的信息
     * @param tenantId
     * @param skuId
     * @param skuAttrs
     * @return
     */
    public ProductSKUResponse querySkuDetail(String tenantId,String skuId,String skuAttrs);

    /**
     * 根据SKU标识或SKU属性串查询SKU的所有属性信息
     * @param tenantId
     * @param skuId
     * @param skuAttrs
     * @return
     */
    public ProductSKUConfigResponse querySkuAttr(String tenantId,String skuId,String skuAttrs);
}
