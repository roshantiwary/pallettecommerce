package com.pallette.controller;

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
import com.pallette.exception.PalletteException;
import com.pallette.service.MerchandiseService;

@RestController
@RequestMapping("/rest/api/v1")
public class FileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	MerchandiseService merchServer;
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/product/upload")
    public ResponseEntity<?> uploadProduct(@RequestParam("file") MultipartFile uploadfile) {
        logger.debug("Product file upload");
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
        	merchServer.processProducts(uploadfile);
        } catch (PalletteException e) {
        	return new ResponseEntity(e.getMessage() + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully uploaded - " + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/media/upload")
    public ResponseEntity<?> uploadMedia(@RequestParam("file") MultipartFile uploadfile) {
        logger.debug("Image file upload");
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
        	merchServer.processMedia(uploadfile);
        } catch (PalletteException e) {
        	return new ResponseEntity(e.getMessage() + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully uploaded - " + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/brand/upload")
    public ResponseEntity<?> uploadBrand(@RequestParam("file") MultipartFile uploadfile) {
        logger.debug("Brand file upload");
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
        	merchServer.processBrand(uploadfile);
        } catch (PalletteException e) {
        	return new ResponseEntity(e.getMessage() + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully uploaded - " + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
 	@PostMapping("/inventory/upload")
     public ResponseEntity<?> uploadInventory(@RequestParam("file") MultipartFile uploadfile) {
        logger.debug("Inventory file upload");
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
        	merchServer.processInventory(uploadfile);
        } catch (PalletteException e) {
        	return new ResponseEntity(e.getMessage() + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully uploaded - " + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
 	@PostMapping("/category/upload")
     public ResponseEntity<?> uploadCategory(@RequestParam("file") MultipartFile uploadfile) {
        logger.debug("Category file upload");
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
        	merchServer.processCategory(uploadfile);
        } catch (PalletteException e) {
        	return new ResponseEntity(e.getMessage() + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully uploaded - " + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
 	@PostMapping("/price/upload")
     public ResponseEntity<?> uploadPrice(@RequestParam("file") MultipartFile uploadfile) {
        logger.debug("Category file upload");
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
        	merchServer.processPrice(uploadfile);
        } catch (PalletteException e) {
        	return new ResponseEntity(e.getMessage() + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully uploaded - " + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
 	@PostMapping("/city/upload")
     public ResponseEntity<?> uploadCity(@RequestParam("file") MultipartFile uploadfile) {
        logger.debug("City file upload");
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
        	merchServer.processCity(uploadfile);
        } catch (PalletteException e) {
        	return new ResponseEntity(e.getMessage() + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully uploaded - " + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
 	@PostMapping("/sku/upload")
     public ResponseEntity<?> uploadSku(@RequestParam("file") MultipartFile uploadfile) {
        logger.debug("Sku file upload");
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
        	merchServer.processSku(uploadfile);
        } catch (PalletteException e) {
        	return new ResponseEntity(e.getMessage() + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully uploaded - " + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/deliveryMethod/upload")
	public ResponseEntity<?> uploadDeliveryMethod(@RequestParam("file") MultipartFile uploadfile) {
		
		logger.debug("Delivery Method file upload");
		if (uploadfile.isEmpty()) {
			return new ResponseEntity("please select a file!", HttpStatus.OK);
		}
		try {
			merchServer.processDeliveryMethod(uploadfile);
		} catch (PalletteException e) {
			return new ResponseEntity(e.getMessage() + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity("Successfully uploaded - " + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
	}

}
