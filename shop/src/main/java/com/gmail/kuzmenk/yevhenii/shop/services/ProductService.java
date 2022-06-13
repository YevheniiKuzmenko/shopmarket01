package com.gmail.kuzmenk.yevhenii.shop.services;

import com.gmail.kuzmenk.yevhenii.shop.models.Product;
import com.gmail.kuzmenk.yevhenii.shop.models.productsInOrder.ProductsInOrder;
import com.gmail.kuzmenk.yevhenii.shop.models.productsInOrder.ProductsInOrderKey;
import com.gmail.kuzmenk.yevhenii.shop.repositories.ProductRepository;
import com.gmail.kuzmenk.yevhenii.shop.repositories.ProductsInOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductsInOrderRepository productsInOrderRepository;

    @Transactional
    public List<Product> findAll(){
        return productRepository.findAll();
    }

    @Transactional
    public void addProduct(MultipartFile photo, String name, String specification, double weight, double price){

        if (!photo.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String photoName = uuidFile + "." + photo.getOriginalFilename();
            try {
                photo.transferTo(new File(uploadPath+"/"+photoName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Product product = new Product(name, weight,price, photoName, specification);
            productRepository.save(product);
        }

    }

    @Transactional
    public void productDelete(Long id){
        List<ProductsInOrder> productsInOrders=productsInOrderRepository.findAllByProduct(productRepository.getById(id));
        productsInOrderRepository.deleteAll(productsInOrders);
        productRepository.deleteById(id);
    }

    @Transactional
    public Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void productEdit(Long id,MultipartFile newPhoto,String specification,String name, double weight, double price){
        Product product=productRepository.getById(id);
        if (!newPhoto.isEmpty()){
            File photo = new File(uploadPath + "/" + product.getPhotoName());
            photo.delete();
            String uuidFile = UUID.randomUUID().toString();
            String photoName = uuidFile + "." + newPhoto.getOriginalFilename();
            try {
                newPhoto.transferTo(new File(uploadPath+"/"+photoName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            product.setPhotoName(photoName);
        }
        if (!product.getSpecification().equals(specification) && !specification.isEmpty()){
            product.setSpecification(specification);
        }
        if (!product.getName().equals(name) && !name.isEmpty()){
            product.setName(name);
        }
        if (product.getWeight()!=weight){
            product.setWeight(weight);
        }
        if (product.getPrice()!=price){
            product.setPrice(price);
        }
        productRepository.save(product);
    }

    @Transactional
    public Product findProductsByPattern(String pattern) {
        return productRepository.findProductByPattern(pattern);
    }


}
