package com.pallette.repository;

import java.util.List;

import com.pallette.browse.documents.BrandDocument;
import com.pallette.browse.documents.CategoryDocument;
import com.pallette.browse.documents.CityDocument;
import com.pallette.browse.documents.ImagesDocument;
import com.pallette.browse.documents.InventoryDocument;
import com.pallette.browse.documents.PriceDocument;
import com.pallette.browse.documents.ProductDocument;
import com.pallette.browse.documents.SkuDocument;

public interface MerchandiseDao {

	/**
	 * bulkMediaUpload method is used for initial set up of Image Data for category and products.
	 * 
	 */
	public void bulkMediaUpload(List<ImagesDocument> imageDocumentList);
	
	/**
	 * bulkBrandUpload method is used for initial set up of Brands Data.
	 * 
	 */
	public void bulkBrandUpload(List<BrandDocument> brandDocumentList);

	
	/**
	 * bulkInventoryUpload method is used for initial set up of Inventory Data.
	 * 
	 */
	public void bulkInventoryUpload(List<InventoryDocument> inventoryDocumentList);
	
	
	/**
	 * bulkCategoryUpload method is used for initial set up of Category Data.
	 * 
	 */
	public void bulkCategoryUpload(List<CategoryDocument> categoryDocumentList);
	
	/**
	 * bulkPriceUpload method is used for initial set up of Price Data.
	 * 
	 */
	public void bulkPriceUpload(List<PriceDocument> priceDocumentList);
	
	/**
	 * bulkProductUpload method is used for initial set up of Product Data.
	 * 
	 */
	public void bulkProductUpload(List<ProductDocument> productDocumentList);

	/**
	 * bulkCityUpload method is used for initial set up of City Data.
	 * 
	 */
	public void bulkCityUpload(List<CityDocument> cityDocumentList);
	
	/**
	 * bulkSkuUpload method is used for initial set up of Sku Data.
	 * 
	 */
	public void bulkSkuUpload(List<SkuDocument> skuDocumentList);
}
