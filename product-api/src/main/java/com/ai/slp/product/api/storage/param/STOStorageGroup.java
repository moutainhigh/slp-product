package com.ai.slp.product.api.storage.param;

import com.ai.slp.product.api.storage.interfaces.IStorageSV;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 虚拟库存组信息<br>
 *
 * Date: 2016年4月20日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * @author liutong5
 */
public class STOStorageGroup extends StorageGroupStatus {

    /**
     * 标准品标识<br>
     * 设置为null,则不进行更新操作
     */
    @NotNull(message = "标准品标识不能为空",
            groups = { IStorageSV.InstallStorage.class})
    private String prodId;
    /**
     * 库存组名称<br>
     * 设置为null,则不进行更新操作
     */
    @NotNull(message = "库存组名称不能为空",
            groups = { IStorageSV.InstallStorage.class })
    private String groupName;
    /**
     * 序列号
     */
    private Long serialNumber;
    /**
     * 创建者ID<br>
     * 更新时,直接忽略
     */
    @NotNull(message = "创建者ID不能为空",
            groups = { IStorageSV.InstallStorage.class })
    private Long createId;
    /**
     * 创建时间<br>
     * 更新时,直接忽略
     */
    private Date createTime;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}