package com.ai.slp.product.search.utils;

import com.ai.slp.product.search.bo.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.ai.slp.product.search.constants.Constants.QUERY_SQL.*;

/**
 * Created by xin on 16-6-1.
 */
public class SKUInfoUtil {

    public static void fetchCategory(SKUInfo skuInfo, String productCategoryId, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(FETCH_CATEGORY_INFO_SQL);
        ps.setString(1, productCategoryId);
        ResultSet resultSet = ps.executeQuery();
        if (!resultSet.next()) {
            return;
        }

        CategoryInfo categoryInfo = new CategoryInfo(resultSet.getString("PRODUCT_CAT_ID"),
                resultSet.getString("PRODUCT_CAT_NAME"));
        skuInfo.addCategoryInfo(categoryInfo);
        String categoryId = resultSet.getString("PARENT_PRODUCT_CAT_ID");

        if (!"0".equals(categoryId)) {
            fetchCategory(skuInfo, categoryId, connection);
        } else {
            skuInfo.setRootcategorid(categoryId);
        }
    }

    public static SKUInfo fillSKUMainInfo(ResultSet resultSet) throws SQLException {
        SKUInfo skuInfo = new SKUInfo(resultSet.getString("tenantid"), resultSet.getString("skuid"),
                resultSet.getString("skuname"));
        skuInfo.setProductid(resultSet.getString("producetid"));
        skuInfo.setProductsellpoint(resultSet.getString("productsellpoint"));
        skuInfo.setProductname(resultSet.getString("producetname"));
        skuInfo.setProductcategoryid(resultSet.getString("productCatId"));
        skuInfo.setBasicorgid(resultSet.getString("basicOrgId"));
        skuInfo.setRechagetype(resultSet.getString("rechageType"));
        skuInfo.setSalenationwide(resultSet.getString("SaleNationwide"));
        skuInfo.setUptime(resultSet.getTimestamp("UP_TIME").getTime());
        return skuInfo;
    }

    public static void fillSKUSaleArea(Connection connection, SKUInfo skuInfo) throws SQLException {
        PreparedStatement ps;
        ResultSet resultSet;
        if ("N".equals(skuInfo.getSalenationwide())) {
            // 价格
            ps = connection.prepareStatement(FETCH_SEAL_AREA);
            ps.setString(1, skuInfo.getProductid());
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                skuInfo.getSaleareainfos().add(new SaleAreaInfo(resultSet.getString("PROV_CODE")));
            }
        }
    }

    public static void fillSKUAudiences(Connection connection, SKUInfo skuInfo) throws SQLException {
        PreparedStatement ps;
        ResultSet resultSet;
        ps = connection.prepareStatement(FETCH_AUDIENCE_SQL);
        ps.setString(1, skuInfo.getProductid());
        resultSet = ps.executeQuery();
        while (resultSet.next()) {
            if ("-1".equals(resultSet.getString("USER_ID"))) {
                skuInfo.addProductAudiences(new ProdAudiences(resultSet.getString("USER_TYPE")));
            } else {
                skuInfo.addProductAudiences(new ProdAudiences(resultSet.getString("USER_TYPE") + resultSet.getString("USER_ID")));
            }

        }
    }

    public static void fillSKUPrice(Connection connection, SKUInfo skuInfo) throws SQLException {
        PreparedStatement ps;
        ResultSet resultSet;
        ps = connection.prepareStatement(FETCH_PRICE_SQL);
        ps.setString(1, skuInfo.getSkuid());
        ps.setString(2, skuInfo.getProductid());
        resultSet = ps.executeQuery();
        resultSet.next();
        skuInfo.setPrice(resultSet.getFloat("SALE_PRICE"));
    }

    public static void fillSKUImageInfo(Connection connection, SKUInfo skuInfo) throws SQLException {
        PreparedStatement ps;
        ResultSet resultSet;
        ps = connection.prepareStatement(FETCH_IMAGE_FROM_SKU_SQL);
        ps.setString(1, skuInfo.getProductcategoryid());
        ps.setString(2, skuInfo.getSkuid());
        resultSet = ps.executeQuery();
        if (resultSet.next()) {
            skuInfo.setImageinfo(new ImageInfo(resultSet.getString("PIC_TYPE"), resultSet.getString("VFS_ID")));
        } else {
            ps = connection.prepareStatement(FETCH_IMAGE_FROM_PROD_SQL);
            ps.setString(1, skuInfo.getProductid());
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                if ("Y".equals(resultSet.getString("IS_MAIN_PIC"))) {
                    skuInfo.setImageinfo(new ImageInfo(resultSet.getString("PIC_TYPE"), resultSet.getString("VFS_ID")));
                }else{
                    skuInfo.addThumbnail(new ImageInfo(resultSet.getString("PIC_TYPE"), resultSet.getString("VFS_ID")));
                }
            }
        }
    }

    public static void fillSKUSaleNum(Connection connection, SKUInfo skuInfo) throws SQLException {
        PreparedStatement ps;
        ResultSet resultSet;
        ps = connection.prepareStatement(FETCH_SALE_NUMBER_SQL);
        ps.setString(1, skuInfo.getProductid());
        resultSet = ps.executeQuery();
        if (resultSet.next()) {
            skuInfo.setSalenum(resultSet.getInt("SALE_NUM"));
        }
    }

    public static void fillSKUAttrInfo(Connection connection, SKUInfo skuInfo) throws SQLException {
        PreparedStatement ps;
        ResultSet resultSet;// 属性
        ps = connection.prepareStatement(FETCH_ATTR_INFO_SQL);
        ps.setString(1, skuInfo.getProductid());
        resultSet = ps.executeQuery();
        List<AttrInfo> attrInfos = new ArrayList<AttrInfo>();
        while (resultSet.next()) {
            AttrInfo attrInfo = new AttrInfo(resultSet.getString("attrID"));
            attrInfo.setAttrvalue(resultSet.getString("attrValue"));
            attrInfos.add(attrInfo);
        }
        skuInfo.setAttrInfos(attrInfos);
    }

    public static void fillSKUInfo(Connection connection, SKUInfo skuInfo) throws SQLException {
        // 属性
        SKUInfoUtil.fillSKUAttrInfo(connection, skuInfo);
        //类目
        SKUInfoUtil.fetchCategory(skuInfo, skuInfo.getProductcategoryid(), connection);
        // 销售量
        SKUInfoUtil.fillSKUSaleNum(connection, skuInfo);
        // 图片
        SKUInfoUtil.fillSKUImageInfo(connection, skuInfo);
        // 价格
        SKUInfoUtil.fillSKUPrice(connection, skuInfo);
        // 受众
        SKUInfoUtil.fillSKUAudiences(connection, skuInfo);
        //销售地域
        SKUInfoUtil.fillSKUSaleArea(connection, skuInfo);
    }


}
