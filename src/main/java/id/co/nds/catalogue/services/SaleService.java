package id.co.nds.catalogue.services;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import id.co.nds.catalogue.entities.ProductEntity;
import id.co.nds.catalogue.entities.SaleEntity;
import id.co.nds.catalogue.exceptions.ClientExceptions;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.models.SaleModel;
import id.co.nds.catalogue.repos.ProductRepo;
import id.co.nds.catalogue.repos.SaleRepo;
import id.co.nds.catalogue.validators.ProductValidator;

@Service
public class SaleService implements Serializable {
    @Autowired
    public SaleRepo saleRepo;

    @Autowired
    public ProductRepo productRepo;

    public ProductValidator productValidator = new ProductValidator();
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public SaleEntity doSale(SaleModel saleModel) throws Exception {
        productValidator.nullCheckProductId(saleModel.getProductId());

        ProductEntity product = productRepo.findById(saleModel.getProductId()).orElse(null);

        if(saleModel.getPrice() == null || saleModel.getQuantity() == null) {
            throw new ClientExceptions("price or quantity cannot be null");
        }

        
        if(product == null) {
            throw new NotFoundException("Product not found");
        }

        Integer productQuantity = product.getQuantity();

        if(productQuantity < saleModel.getQuantity()) {
            throw new ClientExceptions("Product quantity is not enough, only have " + productQuantity);
        }

        product.setQuantity(productQuantity - saleModel.getQuantity());
        productRepo.save(product);

        SaleEntity sale = new SaleEntity();
        sale.setProductId(saleModel.getProductId());
        sale.setPrice(saleModel.getPrice().doubleValue());
        sale.setQuantity(saleModel.getQuantity());
        sale.setTotalPrice(saleModel.getPrice().doubleValue() * saleModel.getQuantity());
        sale.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        // if(true){
        //     throw new Exception("error");
        // }

        return saleRepo.save(sale);
    }

    
}
