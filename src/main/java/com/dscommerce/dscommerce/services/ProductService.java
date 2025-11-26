package com.dscommerce.dscommerce.services;
import com.dscommerce.dscommerce.dto.ProductDTO;
import com.dscommerce.dscommerce.entities.Product;
import com.dscommerce.dscommerce.repositories.ProductRepository;
import com.dscommerce.dscommerce.services.exceptions.DatabaseException;
import com.dscommerce.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;


    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        List<Product> result = repository.findAll();
        return result.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
    }



    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        try {
            Product product = repository.findById(id).get();
            return new ProductDTO(product);
        }
        catch (NoSuchElementException e) {
            throw new ResourceNotFoundException("Recurso não encontrado!");
        }
    }


    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription ());
        entity.setPrice(dto.getPrice ());
        entity.setImgUrl(dto.getImgUrl ());

        entity = repository.save(entity);
        return new ProductDTO(entity);
    }

    /*Recuperando id passado como parâmetro e atualizando dados desse id (metodo PUT)*/
    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = repository.getReferenceById(id);
            entity.setName(dto.getName());
            entity.setDescription(dto.getDescription ());
            entity.setPrice(dto.getPrice ());
            entity.setImgUrl(dto.getImgUrl ());

            entity = repository.save(entity);
            return new ProductDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado!");
        }
    }

    /*Recuperando product por id e deletando o mesmo product (metodo delete)*/
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado!");
        }

        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha na Integridade Referencial");
        }
    }

}

