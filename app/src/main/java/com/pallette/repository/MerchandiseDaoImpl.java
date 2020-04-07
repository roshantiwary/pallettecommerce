package com.pallette.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.pallette.browse.documents.BrandDocument;
import com.pallette.browse.documents.CategoryDocument;
import com.pallette.browse.documents.CityDocument;
import com.pallette.browse.documents.ImagesDocument;
import com.pallette.browse.documents.InventoryDocument;
import com.pallette.browse.documents.PriceDocument;
import com.pallette.browse.documents.ProductDocument;
import com.pallette.browse.documents.SkuDocument;
import com.pallette.domain.DeliveryMethod;

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
			update.set("categorySlug", categoryDocument.getCategorySlug());
			update.set("categoryDescription", categoryDocument.getCategoryDescription());
			update.set("categoryBrand", categoryDocument.getCategoryBrand());
			update.set("categoryStatus", categoryDocument.getCategoryStatus());
			update.set("imagesDocument", categoryDocument.getImagesDocument());
//			update.set("parentCategoryDocument", categoryDocument.getParentCategoryDocument());
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
			update.set("categoryDocument", productDocument.getCategoryDocument());
			update.set("imagesDocument", productDocument.getImagesDocument());
			update.set("skuDocument", productDocument.getSkuDocument());
			mongoTemplate.upsert(query, update, ProductDocument.class);

		}
	}

	@Override
	public void bulkCityUpload(List<CityDocument> cityDocumentList) {
		for (CityDocument cityDocument : cityDocumentList) {
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(cityDocument.getId()));
			
			Update update = new Update();
			update.set("name", cityDocument.getName());
			update.set("imagesDocument", cityDocument.getImagesDocument());
			update.set("brandDocument", cityDocument.getBrandDocuments());
			mongoTemplate.upsert(query, update, CityDocument.class);

		}
	}
	
	@Override
	public void bulkSkuUpload(List<SkuDocument> skuDocumentList) {
		for (SkuDocument skuDocument : skuDocumentList) {
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(skuDocument.getId()));
			
			Update update = new Update();
			update.set("name", skuDocument.getName());
			update.set("defaultDisplayName", skuDocument.getDefaultDisplayName());
			update.set("active", skuDocument.isActive());
			update.set("unitOfMeasure", skuDocument.getUnitOfMeasure());
			update.set("returnable", skuDocument.isReturnable());
			update.set("priceDocument", skuDocument.getPriceDocument());
			update.set("inventoryDocument", skuDocument.getInventoryDocument());
			mongoTemplate.upsert(query, update, SkuDocument.class);

		}
	}

	@Override
	public void bulkDeliveryMethodUpload(List<DeliveryMethod> deliveryMethods) {
		for (DeliveryMethod deliveryMethod : deliveryMethods) {
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(deliveryMethod.getId()));

			Update update = new Update();
			update.set("deliveryMethodName", deliveryMethod.getDeliveryMethodName());
			update.set("deliveryMethodDescription", deliveryMethod.getDeliveryMethodDescription());
			update.set("active", deliveryMethod.isActive());
			update.set("deliveryMethodType", deliveryMethod.getDeliveryMethodType());
			update.set("convenienceFee", deliveryMethod.getConvenienceFee());
			update.set("minDaysToShip", deliveryMethod.getMinDaysToShip());
			update.set("maxDaysToShip", deliveryMethod.getMaxDaysToShip());
			mongoTemplate.upsert(query, update, DeliveryMethod.class);

		}
	}
}
