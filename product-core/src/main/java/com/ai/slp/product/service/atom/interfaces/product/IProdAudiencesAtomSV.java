package com.ai.slp.product.service.atom.interfaces.product;

import com.ai.slp.product.dao.mapper.bo.product.ProdAudiences;

import java.util.List;

/**
 * 商品受众原子操作
 * Created by jackieliu on 16/6/2.
 */
public interface IProdAudiencesAtomSV {

    /**
     * 查询符合用户类型和用户ID的受众新,用户类型和用户ID不能均为空
     *
     * @param tenantId
     * @param userType
     * @param userId
     * @param hasDiscard
     * @return
     */
    public List<ProdAudiences> queryByUserType(String tenantId,String userType,String userId,boolean hasDiscard);
}