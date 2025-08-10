package com.example.todolist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // @Repository 注解：告诉 Spring 这是一个数据仓库 Bean
public interface TaskRepository extends JpaRepository<Task, Long> {
    // 就这样，没了！
}