package com.example.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    /**
     * 将数据库实体 Task 对象转换为 TaskDTO 对象。
     * 这是一个辅助方法，用于在多个地方重用转换逻辑。
     * @param task 数据库实体对象
     * @return 用于API响应的DTO对象
     */
    private TaskDTO convertToDto(Task task) {
        return new TaskDTO(task.getId(), task.getTitle());
    }

    // 1. 获取所有任务 (GET /api/tasks)
    // 返回类型从 List<Task> 变为 List<TaskDTO>
    @GetMapping
    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        // 使用 Java Stream API 将 List<Task> 转换为 List<TaskDTO>
        return tasks.stream()
                .map(this::convertToDto) // 对列表中的每个 task 对象调用 convertToDto 方法
                .collect(Collectors.toList());
    }

    // 2. 创建一个新任务 (POST /api/tasks)
    // 返回类型从 Task 变为 TaskDTO
    @PostMapping
    public TaskDTO createTask(@RequestBody Task task) {
        Task savedTask = taskRepository.save(task);
        // 将保存后的实体转换为 DTO 再返回
        return convertToDto(savedTask);
    }

    // 3. 获取单个任务 (GET /api/tasks/{id})
    // 返回类型从 ResponseEntity<Task> 变为 ResponseEntity<TaskDTO>
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);

        return taskOptional
                .map(this::convertToDto) // 如果找到了 Task，就将它转换为 DTO
                .map(ResponseEntity::ok) // 将 DTO 包装在 200 OK 响应中
                .orElse(ResponseEntity.notFound().build()); // 如果没找到，返回 404
    }

    // 4. 更新一个任务 (PUT /api/tasks/{id})
    // 返回类型从 ResponseEntity<Task> 变为 ResponseEntity<TaskDTO>
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(taskDetails.getTitle());
                    task.setCompleted(taskDetails.isCompleted());
                    Task updatedTask = taskRepository.save(task);
                    // 将更新后的实体转换为 DTO 再返回
                    return ResponseEntity.ok(convertToDto(updatedTask));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. 删除一个任务 (DELETE /api/tasks/{id})
    // 这个方法没有返回体，所以不需要改变
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    taskRepository.delete(task);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}