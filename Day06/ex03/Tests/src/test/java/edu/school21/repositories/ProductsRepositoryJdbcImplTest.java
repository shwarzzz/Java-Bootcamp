package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ProductsRepositoryJdbcImplTest {
    final List<Product> ALL_EXISTING_PRODUCTS = new ArrayList<>
            (Arrays.asList(new Product(1L, "product 1", 30000.0),
                    new Product(2L, "product 2", 2000.0),
                    new Product(3L, "product 3", 15000.0),
                    new Product(4L, "product 4", 1000000.0),
                    new Product(5L, "product 5", 999.0)));
    final List<Product> EXPECTED_UPDATED_PRODUCTS = new ArrayList<>(
            Arrays.asList(new Product(1L, null, null),
                    new Product(2L, "new product name", 15.0),
                    new Product(3L, null, 0.0),
                    new Product(4L, "changed", null),
                    new Product(5L, "product 5", 999.0)));
    final List<Product> NON_EXISTING_PRODUCTS = new ArrayList<>(
            Arrays.asList(new Product(6L, null, null),
                    new Product(12L, "new product", 15000000.0),
                    new Product(103L, null, 0.0),
                    new Product(8L, "inserted", null),
                    new Product(519L, "inserted prod", 9990.0)));
    Connection connection;

    @BeforeEach
    void init() throws SQLException {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        connection = builder
                .setName("product_db")
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build().getConnection();
    }

    @AfterEach
    void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    void findAllProductsCorrect() throws SQLException {
        ProductsRepository productsRepository =
                new ProductsRepositoryJdbcImpl(connection);
        assertEquals(ALL_EXISTING_PRODUCTS, productsRepository.findAll());
    }

    @Test
    void findAllProductsEmpty() throws SQLException {
        ProductsRepository productsRepository =
                new ProductsRepositoryJdbcImpl(connection);
        connection.createStatement().execute("TRUNCATE TABLE product;");
        assertEquals(Collections.emptyList(), productsRepository.findAll());
    }

    @ParameterizedTest(name = "Test {index} - product with id = {0} was found")
    @CsvSource({"1, 0", "2, 1", "3, 2", "4, 3", "5, 4"})
    void findByIdSendCorrect(Long id, int index) throws SQLException {
        ProductsRepository productsRepository =
                new ProductsRepositoryJdbcImpl(connection);
        Optional<Product> result = productsRepository.findById(id);
        assertTrue(result.isPresent());
        assertEquals(ALL_EXISTING_PRODUCTS.get(index), result.get());
    }

    @ParameterizedTest(name = "Test - id = null")
    @NullSource
    void findByIdSendNull(Long id) {
        ProductsRepository productsRepository =
                new ProductsRepositoryJdbcImpl(connection);
        assertThrows(IllegalArgumentException.class, () -> {
            productsRepository.findById(id);
        });
    }

    @ParameterizedTest(name = "Test {index} - product with id = {0} does not exist")
    @ValueSource(longs = {16, -2, 10, 0, 111, -22})
    void findByIdSendNotExist(Long id) throws SQLException {
        ProductsRepository productsRepository =
                new ProductsRepositoryJdbcImpl(connection);
        assertEquals(Optional.empty(), productsRepository.findById(id));
    }


    @ParameterizedTest(name = "Test {index} - product updated")
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void updateSendCorrect(int index) throws SQLException {
        ProductsRepository productsRepository =
                new ProductsRepositoryJdbcImpl(connection);
        Product product = EXPECTED_UPDATED_PRODUCTS.get(index);
        productsRepository.update(product);
        Optional<Product> insertedProduct = productsRepository.findById(product.getId());
        assertTrue(insertedProduct.isPresent());
        assertEquals(product, insertedProduct.get());
    }

    @ParameterizedTest(name = "Test - product = null")
    @NullSource
    void updateSendEntityEqNull(Product product) {
        ProductsRepository productsRepository =
                new ProductsRepositoryJdbcImpl(connection);
        assertThrows(IllegalArgumentException.class, () ->
        {
            productsRepository.update(product);
        });
    }

    @Test
    void updateSendEntityIdEqNull() {
        ProductsRepository productsRepository =
                new ProductsRepositoryJdbcImpl(connection);
        Product product = new Product(null, null, null);
        assertThrows(IllegalArgumentException.class, () ->
        {
            productsRepository.update(product);
        });
    }

    @ParameterizedTest(name = "Test {index} - product not exist")
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void updateSendEntityNotExist(int index) {
        ProductsRepository productsRepository =
                new ProductsRepositoryJdbcImpl(connection);
        Product product = NON_EXISTING_PRODUCTS.get(index);
        assertThrows(IllegalArgumentException.class, () ->
        {
            productsRepository.update(product);
        });
    }

    @ParameterizedTest(name = "Test {index} - product inserted")
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void saveCorrect(int index) throws SQLException {
        ProductsRepository productsRepository =
                new ProductsRepositoryJdbcImpl(connection);
        Product product = NON_EXISTING_PRODUCTS.get(index);
        productsRepository.save(product);
        Optional<Product> insertedProduct = productsRepository.findById(product.getId());
        assertTrue(insertedProduct.isPresent());
        assertEquals(product, insertedProduct.get());
    }

    @ParameterizedTest(name = "Test - product = null")
    @NullSource
    void saveSendEntityEqNull(Product product) {
        ProductsRepository productsRepository =
                new ProductsRepositoryJdbcImpl(connection);
        assertThrows(IllegalArgumentException.class, () ->
        {
            productsRepository.save(product);
        });
    }

    @Test
    void saveSendEntityIdEqNull() {
        ProductsRepository productsRepository =
                new ProductsRepositoryJdbcImpl(connection);
        Product product = new Product(null, null, null);
        assertThrows(IllegalArgumentException.class, () ->
        {
            productsRepository.save(product);
        });
    }

    @ParameterizedTest(name = "Test {index} - product already exist")
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void saveSendEntityAlreadyExist(int index) {
        ProductsRepository productsRepository =
                new ProductsRepositoryJdbcImpl(connection);
        Product product = ALL_EXISTING_PRODUCTS.get(index);
        assertThrows(IllegalArgumentException.class, () ->
        {
            productsRepository.save(product);
        });
    }

    @ParameterizedTest(name = "Test {index} - product with id = {0} deleted")
    @ValueSource(longs = {1, 2, 3, 4, 5})
    void deleteProductCorrect(Long id) throws SQLException {
        ProductsRepository productsRepository =
                new ProductsRepositoryJdbcImpl(connection);
        productsRepository.delete(id);
        assertFalse(productsRepository.findById(id).isPresent());
    }

    @ParameterizedTest(name = "Test - id = null")
    @NullSource
    void deleteSendIdEqNull(Long id) {
        ProductsRepository productsRepository =
                new ProductsRepositoryJdbcImpl(connection);
        assertThrows(IllegalArgumentException.class, () -> {
            productsRepository.delete(id);
        });
    }

    @ParameterizedTest(name = "Test {index} - product with id = {0} does not exist")
    @ValueSource(longs = {-1, 6, 10, 100, 4412})
    void deleteSendIdNotExist(Long id) {
        ProductsRepository productsRepository =
                new ProductsRepositoryJdbcImpl(connection);
        assertThrows(IllegalArgumentException.class, () -> {
            productsRepository.delete(id);
        });
    }
}
