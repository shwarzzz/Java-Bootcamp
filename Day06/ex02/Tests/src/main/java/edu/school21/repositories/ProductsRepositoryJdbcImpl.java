package edu.school21.repositories;

import edu.school21.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private final Connection connection;

    public ProductsRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement
                    .executeQuery("SELECT * FROM product");
            if (!resultSet.isBeforeFirst()) {
                return Collections.emptyList();
            }
            List<Product> productList = new ArrayList<>();
            while (resultSet.next()) {
                productList.add(new Product(
                        resultSet.getObject("id", Long.class),
                        resultSet.getString("name"),
                        resultSet.getObject("price", Double.class)));
            }
            return productList;
        }
    }

    @Override
    public Optional<Product> findById(Long id) throws SQLException {
        if (id == null) {
            throw new IllegalArgumentException("ID can't be null");
        }
        try (PreparedStatement statement =
                     connection.prepareStatement(
                             "SELECT * FROM product WHERE id = ?;")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Optional<Product> result = Optional.empty();
            if (resultSet.next()) {
                result = Optional.of(new Product(
                        resultSet.getObject("id", Long.class),
                        resultSet.getString("name"),
                        resultSet.getObject("price", Double.class)));
            }
            return result;
        }
    }

    @Override
    public void update(Product product) throws SQLException {
        if (product == null || product.getId() == null) {
            throw new IllegalArgumentException("Product entity/id can't be null");
        }
        if (!findById(product.getId()).isPresent()) {
            throw new IllegalArgumentException("Product does not exist");
        }
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE product SET name = ?, price = ? WHERE id = ?;")) {
            statement.setString(1, product.getName());
            if (product.getPrice() == null) {
                statement.setNull(2, Types.NUMERIC);
            } else {
                statement.setDouble(2, product.getPrice());
            }
            statement.setLong(3, product.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void save(Product product) throws SQLException {
        if (product == null || product.getId() == null) {
            throw new IllegalArgumentException("Product entity/id can't be null");
        }
        if (findById(product.getId()).isPresent()) {
            throw new IllegalArgumentException("Product already exist");
        }
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO product VALUES (?, ?, ?);")) {
            statement.setLong(1, product.getId());
            statement.setString(2, product.getName());
            if (product.getPrice() == null) {
                statement.setNull(3, Types.NUMERIC);
            } else {
                statement.setDouble(3, product.getPrice());
            }
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        if (id == null) {
            throw new IllegalArgumentException("ID can't be null");
        }
        if (!findById(id).isPresent()) {
            throw new IllegalArgumentException("Product does not exist");
        }
        try (PreparedStatement statement =
                     connection.prepareStatement(
                             "DELETE FROM product WHERE id = ?;")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}
