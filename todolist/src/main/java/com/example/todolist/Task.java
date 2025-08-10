package com.example.todolist;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // @Entity 注解：告诉 JPA，这个类对应数据库里的一张表
public class Task {

    @Id // @Id 注解：表明这是主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // @GeneratedValue：表明主键是自增的
    private Long id;

    private String title;
    private boolean completed = false;

    // --- Getters and Setters ---
    // Java 中需要手动提供 getter/setter 方法来访问私有字段
    // IDE 可以自动生成这些代码 (在 IntelliJ 中，右键 -> Generate -> Getters and Setters)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}