package com.example.systemarkitekturuppgift2;

import java.util.List;
import java.util.Optional;

public interface WarehouseService {
     boolean addProduct();
     boolean modifyProduct();
     Optional getProduct();
     List getAllProducts();

}
