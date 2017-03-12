package com.pallette.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import com.mongodb.DBCollection;
import com.pallette.domain.BrandDocument;
import com.pallette.domain.CategoryDocument;
import com.pallette.domain.ImagesDocument;
import com.pallette.domain.InventoryDocument;
import com.pallette.domain.PriceDocument;
import com.pallette.domain.ProductDocument;

@Repository
public class MerchandiseDaoImpl implements MerchandiseDao{

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public void bulkMediaUpload(List<ImagesDocument> imageDocumentList) {
		DBCollection coll = mongoTemplate.getCollection("images");
		mongoTemplate.insertAll(imageDocumentList);
	}

	@Override
	public void bulkBrandUpload(List<BrandDocument> brandDocumentList) {
		DBCollection coll = mongoTemplate.getCollection("images");
		mongoTemplate.insertAll(brandDocumentList);
	}

	@Override
	public void bulkInventoryUpload(List<InventoryDocument> inventoryDocumentList) {
		DBCollection coll = mongoTemplate.getCollection("images");
		mongoTemplate.insertAll(inventoryDocumentList);
	}

	@Override
	public void bulkCategoryUpload(List<CategoryDocument> categoryDocumentList) {
		DBCollection coll = mongoTemplate.getCollection("images");
		mongoTemplate.insertAll(categoryDocumentList);
	}

	@Override
	public void bulkPriceUpload(List<PriceDocument> priceDocumentList) {
		DBCollection coll = mongoTemplate.getCollection("images");
		mongoTemplate.insertAll(priceDocumentList);
	}

	@Override
	public void bulkProductUpload(List<ProductDocument> productDocumentList) {
		DBCollection coll = mongoTemplate.getCollection("images");
		mongoTemplate.insertAll(productDocumentList);
	}

}
