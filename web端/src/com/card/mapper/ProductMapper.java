package com.card.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.card.po.Product;

public interface ProductMapper {
	/*添加电动车信息*/
	public void addProduct(Product product) throws Exception;

	/*按照查询条件分页查询电动车记录*/
	public ArrayList<Product> queryProduct(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有电动车记录*/
	public ArrayList<Product> queryProductList(@Param("where") String where) throws Exception;

	/*按照查询条件的电动车记录数*/
	public int queryProductCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条电动车记录*/
	public Product getProduct(String productNo) throws Exception;

	/*更新电动车记录*/
	public void updateProduct(Product product) throws Exception;

	/*删除电动车记录*/
	public void deleteProduct(String productNo) throws Exception;

	/*根据rfid查询某条电动车记录*/
	public Product getProductByRfid(String rfid) throws Exception;

}
