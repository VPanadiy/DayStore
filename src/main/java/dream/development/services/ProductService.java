package dream.development.services;

import dream.development.model.Product;

public interface ProductService {

    Iterable<Product> listAllProducts();

    Product getProductById(Integer id);

    Product saveProduct(Product product);

    public void deleteProduct(Integer id);
}
