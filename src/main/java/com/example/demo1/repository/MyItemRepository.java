package com.example.demo1.repository;

import com.example.demo1.entity.MyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyItemRepository extends JpaRepository<MyItem, Long> {
}
