package com.pallette.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import com.pallette.domain.BrandDocument;
import com.pallette.domain.CategoryDocument;
import com.pallette.domain.ImagesDocument;
import com.pallette.domain.InventoryDocument;
import com.pallette.domain.PriceDocument;
import com.pallette.domain.ProductDocument;
import org.springframework.data.mongodb.core.query.Criteria;

@Repository
public class MerchandiseDaoImpl implements MerchandiseDao{

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public void bulkMediaUpload(List<ImagesDocument> imageDocumentList) {		
		for (ImagesDocument imagesDocument : imageDocumentList) {
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(imagesDocument.getId()));
			
			Update update = new Update();
			update.set("thumbnailImageUrl", imagesDocument.getThumbnailImageUrl());
			update.set("smallImageUrl", imagesDocument.getSmallImageUrl());
			update.set("largeImageUrl", imagesDocument.getLargeImageUrl());
			update.set("imageAvailablity", imagesDocument.isImageAvailablity());
			mongoTemplate.upsert(query, update, ImagesDocument.class);
		}
	}

	@Override
	public void bulkBrandUpload(List<BrandDocument> brandDocumentList) {
		for (BrandDocument brandDocument : brandDocumentList) {
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(brandDocument.getId()));
			
			Update update = new Update();
			update.set("storeName", brandDocument.getStoreName());
			update.set("address1", brandDocument.getAddress1());
			update.set("address2", brandDocument.getAddress2());
			update.set("city", brandDocument.getCity());
			update.set("state", brandDocument.getState());
			update.set("postalCode", brandDocument.getPostalCode());
			update.set("countryCode", brandDocument.getCountryCode());
			update.set("phone", brandDocument.getPhone());
			mongoTemplate.upsert(query, update, BrandDocument.class);
		}
	}

	@Override
	public void bulkInventoryUpload(List<InventoryDocument> inventoryDocumentList) {
		for (InventoryDocument inventoryDocument : inventoryDocumentList) {
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(inventoryDocument.getId()));
			
			Update update = new Update();
			update.set("availableStockLevel", inventoryDocument.getAvailableStockLevel());
			update.set("reservedStockLevel", inventoryDocument.getReservedStockLevel());
			update.set("stockStatus", inventoryDocument.getStockStatus());
			mongoTemplate.upsert(query, update, InventoryDocument.class);
		}
	}

	@Override
	public void bulkCategoryUpload(List<CategoryDocument> categoryDocumentList) {
		for (CategoryDocument categoryDocument : categoryDocumentList) {
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(categoryDocument.getId()));
			
			Update update = new Update();
			update.set("categoryTitle", categoryDocument.getCategoryTitle());
			update.set("categorySlug", categoryDocument);
			update.set("categoryDescription", categoryDocument);
			update.set("categoryBrand", categoryDocument);
			update.set("categoryStatus", categoryDocument);
			update.set("imagesDocument", categoryDocument);
			update.set("parentCategoryDocument", categoryDocument);
			mongoTemplate.upsert(query, update, CategoryDocument.class);
		}
	}

	@Override
	public void bulkPriceUpload(List<PriceDocument> priceDocumentList) {
		for (PriceDocument priceDocument : priceDocumentList) {
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(priceDocument.getId()));
			
			Update update = new Update();
			update.set("listPrice", priceDocument.getListPrice());
			update.set("salePrice", priceDocument.getSalePrice());
			
			mongoTemplate.upsert(query, update, PriceDocument.class);
		}
	}

	@Override
	public void bulkProductUpload(List<ProductDocument> productDocumentList) {
		for (ProductDocument productDocument : productDocumentList) {
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(productDocument.getId()));
			
			Update update = new Update();
			update.set("productTitle", productDocument.getProductTitle());
			update.set("productSlug", productDocument.getProductSlug());
			update.set("productDescription", productDocument.getProductDescription());
			update.set("productBrand", productDocument.getProductBrand());
			update.set("productStatus", productDocument.getProductStatus());
			update.set("imagesDocument", productDocument.getImagesDocument());
			update.set("priceDocument", productDocument.getPriceDocument());
			update.set("inventoryDocument", productDocument.getInventoryDocument());
			update.set("categoryDocument", productDocument.getCategoryDocument());
			mongoTemplate.upsert(query, update, ProductDocument.class);

		}
	}

}
