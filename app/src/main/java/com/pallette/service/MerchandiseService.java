package com.pallette.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.pallette.browse.documents.BrandDocument;
import com.pallette.browse.documents.CategoryDocument;
import com.pallette.browse.documents.CityDocument;
import com.pallette.browse.documents.ImagesDocument;
import com.pallette.browse.documents.InventoryDocument;
import com.pallette.browse.documents.PriceDocument;
import com.pallette.browse.documents.ProductDocument;
import com.pallette.browse.documents.SkuDocument;
import com.pallette.domain.DeliveryMethod;
import com.pallette.exception.PalletteException;
import com.pallette.repository.MerchandiseDao;

@Service
public class MerchandiseService {
	
	private static final Logger logger = LoggerFactory.getLogger(MerchandiseService.class);
	
	@Autowired
    private MerchandiseDao merchDao;
	
	private InputStream getInputStream(List<MultipartFile> files) throws IOException {
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue; //next pls
            } else {
            	return file.getInputStream();
            }
        }
		return null;
    }
	
	private <T> List<T> loadObjectList(Class<T> type, InputStream inputStream) {
	    try {
	        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
	        CsvMapper mapper = new CsvMapper();
	        MappingIterator<T> readValues = 
	          mapper.reader(type).with(bootstrapSchema).readValues(inputStream);
	        return readValues.readAll();
	    } catch (Exception e) {
	        logger.error("Error occurred while loading object list from file ", e);
	        return Collections.emptyList();
	    }
	}
	
	public void processProducts(MultipartFile uploadfile) throws PalletteException {
		InputStream inputStream;
		try {
			inputStream = getInputStream(Arrays.asList(uploadfile));
			if(null != inputStream) {
				List<ProductDocument> productList = loadObjectList(ProductDocument.class, inputStream);
				merchDao.bulkProductUpload(productList);
			} else {
				 throw new PalletteException("File is empty !!");
			}
		} catch (IOException e) {
			throw new PalletteException("Error while processing the file for products");
		}
	}

	public void processMedia(MultipartFile uploadfile) throws PalletteException {
		InputStream inputStream;
		try {
			inputStream = getInputStream(Arrays.asList(uploadfile));
			if(null != inputStream) {
				List<ImagesDocument> imageList = loadObjectList(ImagesDocument.class, inputStream);
        		merchDao.bulkMediaUpload(imageList);
			} else {
				 throw new PalletteException("File is empty !!");
			}
		} catch (IOException e) {
			throw new PalletteException("Error while processing the file with images");
		}
	}
	
	public void processBrand(MultipartFile uploadfile) throws PalletteException {
		InputStream inputStream;
		try {
			inputStream = getInputStream(Arrays.asList(uploadfile));
			if(null != inputStream) {
				List<BrandDocument> brandList = loadObjectList(BrandDocument.class, inputStream);
        		merchDao.bulkBrandUpload(brandList);
			} else {
				 throw new PalletteException("File is empty !!");
			}
		} catch (IOException e) {
			throw new PalletteException("Error while processing the file with brand");
		}
	}
	
	public void processSku(MultipartFile uploadfile) throws PalletteException {
		InputStream inputStream;
		try {
			inputStream = getInputStream(Arrays.asList(uploadfile));
			if(null != inputStream) {
				List<SkuDocument> skuList = loadObjectList(SkuDocument.class, inputStream);
         		merchDao.bulkSkuUpload(skuList);
			} else {
				 throw new PalletteException("File is empty !!");
			}
		} catch (IOException e) {
			throw new PalletteException("Error while processing the file with Sku");
		}
	}
	
	public void processInventory(MultipartFile uploadfile) throws PalletteException {
		InputStream inputStream;
		try {
			inputStream = getInputStream(Arrays.asList(uploadfile));
			if(null != inputStream) {
				List<InventoryDocument> inventoryList = loadObjectList(InventoryDocument.class, inputStream);
         		merchDao.bulkInventoryUpload(inventoryList);
			} else {
				 throw new PalletteException("File is empty !!");
			}
		} catch (IOException e) {
			throw new PalletteException("Error while processing the file with Inventory");
		}
	}
	
	public void processCategory(MultipartFile uploadfile) throws PalletteException {
		InputStream inputStream;
		try {
			inputStream = getInputStream(Arrays.asList(uploadfile));
			if(null != inputStream) {
				List<CategoryDocument> categoryList = loadObjectList(CategoryDocument.class, inputStream);
         		merchDao.bulkCategoryUpload(categoryList);
			} else {
				 throw new PalletteException("File is empty !!");
			}
		} catch (IOException e) {
			throw new PalletteException("Error while processing the file with Category");
		}
	}
	
	public void processPrice(MultipartFile uploadfile) throws PalletteException {
		InputStream inputStream;
		try {
			inputStream = getInputStream(Arrays.asList(uploadfile));
			if(null != inputStream) {
				List<PriceDocument> priceList = loadObjectList(PriceDocument.class, inputStream);
         		merchDao.bulkPriceUpload(priceList);
			} else {
				 throw new PalletteException("File is empty !!");
			}
		} catch (IOException e) {
			throw new PalletteException("Error while processing the file with Price");
		}
	}

	public void processCity(MultipartFile uploadfile) throws PalletteException {
		InputStream inputStream;
		try {
			inputStream = getInputStream(Arrays.asList(uploadfile));
			if(null != inputStream) {
				List<CityDocument> cityList = loadObjectList(CityDocument.class, inputStream);
         		merchDao.bulkCityUpload(cityList);
			} else {
				 throw new PalletteException("File is empty !!");
			}
		} catch (IOException e) {
			throw new PalletteException("Error while processing the file with City");
		}
	}

	public void processDeliveryMethod(MultipartFile uploadfile) throws PalletteException {
		InputStream inputStream;
		try {
			inputStream = getInputStream(Arrays.asList(uploadfile));
			if (null != inputStream) {
				List<DeliveryMethod> deliveryMethods = loadObjectList(DeliveryMethod.class, inputStream);
				merchDao.bulkDeliveryMethodUpload(deliveryMethods);
			} else {
				throw new PalletteException("File is empty !!");
			}
		} catch (IOException e) {
			throw new PalletteException("Error while processing the file with Delivery Methods");
		}
	}
	
	
}
