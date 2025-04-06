package com.example.wms.basicfunction;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository {

    private final JdbcClient jdbcClient; // JdbcClientのインスタンス

    @Autowired
    public ItemRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient; // コンストラクタでJdbcClientを受け取る
    }

    public List<Item> findAll() {
        String sql = "SELECT * FROM items";
        return jdbcClient.sql(sql)
                .query(new DataClassRowMapper<>(Item.class)) // DataClassRowMapperを使用してItemにマッピング
                .list(); // list()メソッドでList<Item>に変換
    }

    public Optional<Item> findById(Long id) {
        return jdbcClient.sql("SELECT * FROM items WHERE id = ?")
                .param(id)
                .query(new DataClassRowMapper<>(Item.class))
                .optional(); // アイテムが存在しない場合はOptional.empty()を返す
    }

    public int save(Item item) {
        String sql = "INSERT INTO items(name, description, quantity) VALUES(?, ?, ?)";
        return jdbcClient.sql(sql)
                .param(item.getName())
                .param(item.getDescription())
                .param(item.getQuantity())
                .update(); // Insert文を実行
    }

    public void delete(Long id) {
        jdbcClient.sql("DELETE FROM items WHERE id = ?")
                .param(id)
                .update(); // Delete文を実行
    }

    public int update(Item item) {
        String sql = "UPDATE items SET name = ?, description = ?, quantity = ? WHERE id = ?";
        return jdbcClient.sql(sql)
                .param(item.getName())
                .param(item.getDescription())
                .param(item.getQuantity())
                .param(item.getId())
                .update(); // Update文を実行
    }
}
