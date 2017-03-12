package com.pallette.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.pallette.domain.BrandDocument;
import com.pallette.domain.CategoryDocument;
import com.pallette.domain.ImagesDocument;
import com.pallette.domain.InventoryDocument;
import com.pallette.domain.PriceDocument;
import com.pallette.domain.ProductDocument;
import com.pallette.repository.MerchandiseDao;

@RestController
@RequestMapping("/rest/api/v1")
public class FileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    
	@Autowired
    private MerchandiseDao merchDao;
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/products/upload")
    public ResponseEntity<?> uploadProduct(@RequestParam("file") MultipartFile uploadfile) {
        logger.debug("Single file upload!");
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
        	InputStream inputStream = getInputStream(Arrays.asList(uploadfile));
        	if(null != inputStream) {
        		List<ProductDocument> productList = loadObjectList(ProductDocument.class, inputStream);
        		merchDao.bulkProductUpload(productList);
        		for (ProductDocument productDocument : productList) {
					System.out.println(productDocument.getId() + " " + productDocument.getProductTitle());
				}
        	} else {
        		 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        	}
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully uploaded - " + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/media/upload")
    public ResponseEntity<?> uploadMedia(@RequestParam("file") MultipartFile uploadfile) {
        logger.debug("Single file upload!");
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
        	InputStream inputStream = getInputStream(Arrays.asList(uploadfile));
        	if(null != inputStream) {
        		List<ImagesDocument> imageList = loadObjectList(ImagesDocument.class, inputStream);
        		merchDao.bulkMediaUpload(imageList);
        		for (ImagesDocument imageDocument : imageList) {
					System.out.println(imageDocument.getId());
				}
        	} else {
        		 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        	}
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully uploaded - " + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/brands/upload")
    public ResponseEntity<?> uploadBrand(@RequestParam("file") MultipartFile uploadfile) {
        logger.debug("Single file upload!");
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
        	InputStream inputStream = getInputStream(Arrays.asList(uploadfile));
        	if(null != inputStream) {
        		List<BrandDocument> brandList = loadObjectList(BrandDocument.class, inputStream);
        		merchDao.bulkBrandUpload(brandList);
        		for (BrandDocument brandDocument : brandList) {
					System.out.println(brandDocument.getId() + " " + brandDocument.getStoreName());
				}
        	} else {
        		 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        	}
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully uploaded - " + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
 	@PostMapping("/inventory/upload")
     public ResponseEntity<?> uploadInventory(@RequestParam("file") MultipartFile uploadfile) {
         logger.debug("Single file upload!");
         if (uploadfile.isEmpty()) {
             return new ResponseEntity("please select a file!", HttpStatus.OK);
         }
         try {
         	InputStream inputStream = getInputStream(Arrays.asList(uploadfile));
         	if(null != inputStream) {
         		List<InventoryDocument> inventoryList = loadObjectList(InventoryDocument.class, inputStream);
         		merchDao.bulkInventoryUpload(inventoryList);
         		for (InventoryDocument inventoryDocument : inventoryList) {
 					System.out.println(inventoryDocument.getId() + " " + inventoryDocument.getStockStatus());
 				}
         	} else {
         		 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         	}
         } catch (IOException e) {
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }
         return new ResponseEntity("Successfully uploaded - " + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
     }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
 	@PostMapping("/category/upload")
     public ResponseEntity<?> uploadCategory(@RequestParam("file") MultipartFile uploadfile) {
         logger.debug("Single file upload!");
         if (uploadfile.isEmpty()) {
             return new ResponseEntity("please select a file!", HttpStatus.OK);
         }
         try {
         	InputStream inputStream = getInputStream(Arrays.asList(uploadfile));
         	if(null != inputStream) {
         		List<CategoryDocument> categoryList = loadObjectList(CategoryDocument.class, inputStream);
         		merchDao.bulkCategoryUpload(categoryList);
         		for (CategoryDocument categoryDocument : categoryList) {
 					System.out.println(categoryDocument.getId() + " " + categoryDocument.getCategoryTitle());
 				}
         	} else {
         		 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         	}
         } catch (IOException e) {
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }
         return new ResponseEntity("Successfully uploaded - " + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
     }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
 	@PostMapping("/price/upload")
     public ResponseEntity<?> uploadPrice(@RequestParam("file") MultipartFile uploadfile) {
         logger.debug("Single file upload!");
         if (uploadfile.isEmpty()) {
             return new ResponseEntity("please select a file!", HttpStatus.OK);
         }
         try {
         	InputStream inputStream = getInputStream(Arrays.asList(uploadfile));
         	if(null != inputStream) {
         		List<PriceDocument> priceList = loadObjectList(PriceDocument.class, inputStream);
         		merchDao.bulkPriceUpload(priceList);
         		for (PriceDocument priceDocument : priceList) {
 					System.out.println(priceDocument.getId() + " " + priceDocument.getListPrice());
 				}
         	} else {
         		 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         	}
         } catch (IOException e) {
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }
         return new ResponseEntity("Successfully uploaded - " + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
     }
    
    //save file
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
	
}
