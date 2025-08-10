package com.example.todolist;

/**
 * Data Transfer Object (数据传输对象) for Task.
 * 它的唯一作用是定义 API 返回给前端的数据结构。
 * 这样可以避免直接暴露数据库实体，增加安全性和灵活性。
 */
public class TaskDTO {

    private Long id;
    private String title;

    // --- 构造函数 ---
    // 提供一个无参构造函数是好习惯，很多框架（如Jackson）会用到它
    public TaskDTO() {
    }

    // 提供一个全参构造函数，方便我们手动创建实例
    public TaskDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    // --- Getters and Setters ---
    // 这是解决你问题的关键！必须为所有私有字段提供公共的 getter 和 setter 方法。
    // Jackson 库在序列化（对象转JSON）时会调用 getter 方法来获取值。
    // 我们在 Controller 里需要 setter 方法来给 DTO 赋值。
    // (在 IntelliJ IDEA 中，可以右键 -> Generate -> Getter and Setter 快速生成)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}