package com.example.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // @RestController: 表明这个类是用来处理 HTTP 请求的，并且返回 JSON 数据
@RequestMapping("/api/tasks") // 设置所有 API 的基础路径为 /api/tasks
public class TaskController {

    @Autowired // @Autowired: 自动注入 TaskRepository 的实例，Spring 会帮我们创建它
    private TaskRepository taskRepository;

    // 1. 获取所有任务 (GET /api/tasks)
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // 2. 创建一个新任务 (POST /api/tasks)
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        // @RequestBody: 把请求的 JSON body 自动转换成一个 Task 对象
        return taskRepository.save(task);
    }

    // 3. 获取单个任务 (GET /api/tasks/{id})
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        // @PathVariable: 从 URL 路径中获取 id
        Optional<Task> task = taskRepository.findById(id);
        return task.map(ResponseEntity::ok) // 如果找到了，返回 200 OK 和 task 对象
                .orElse(ResponseEntity.notFound().build()); // 如果没找到，返回 404 Not Found
    }

    // 4. 更新一个任务 (PUT /api/tasks/{id})
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(taskDetails.getTitle());
                    task.setCompleted(taskDetails.isCompleted());
                    Task updatedTask = taskRepository.save(task);
                    return ResponseEntity.ok(updatedTask);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. 删除一个任务 (DELETE /api/tasks/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    taskRepository.delete(task);
                    return ResponseEntity.ok().build(); // 返回 200 OK，没有 body
                })
                .orElse(ResponseEntity.notFound().build());
    }
}