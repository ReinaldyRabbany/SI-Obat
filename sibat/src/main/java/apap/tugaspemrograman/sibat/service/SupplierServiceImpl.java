package apap.tugaspemrograman.sibat.service;

import apap.tugaspemrograman.sibat.repository.SupplierDb;
import apap.tugaspemrograman.sibat.model.SupplierModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    SupplierDb supplierDb;

    @Override
    public Optional<SupplierModel> getSupplerById(Long id) {
        return supplierDb.findById(id);
    }

    @Override
    public List<SupplierModel> findAllSupplier() {
        return supplierDb.findAll();
    }
}
