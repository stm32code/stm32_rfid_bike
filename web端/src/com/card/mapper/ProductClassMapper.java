package com.card.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.card.po.ProductClass;

public interface ProductClassMapper {
	/*添加电动车类别信息*/
	public void addProductClass(ProductClass productClass) throws Exception;

	/*按照查询条件分页查询电动车类别记录*/
	public ArrayList<ProductClass> queryProductClass(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有电动车类别记录*/
	public ArrayList<ProductClass> queryProductClassList(@Param("where") String where) throws Exception;

	/*按照查询条件的电动车类别记录数*/
	public int queryProductClassCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条电动车类别记录*/
	public ProductClass getProductClass(int classId) throws Exception;

	/*更新电动车类别记录*/
	public void updateProductClass(ProductClass productClass) throws Exception;

	/*删除电动车类别记录*/
	public void deleteProductClass(int classId) throws Exception;

}
