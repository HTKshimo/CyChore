package com.example.CyCHORE.Task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    TaskRepository tr;

    @GetMapping("/getTaskList/{id}")
    List<String> getTaskList(@PathVariable Integer id){
        List<Task> allTaskList;
        allTaskList = tr.findAll();
        List<String> taskList = new ArrayList<String>();;
        for (Task temp : allTaskList) {
            if (temp.is_assigned_to() == id){
                taskList.add(temp.toString());
            }
        }
        return taskList;
    }

    @PutMapping("/markAsCompleted/{user_id}/{task_id}")
    String markAsCompleted(@PathVariable Integer user_id, @PathVariable Integer task_id){
        Optional<Task> test = tr.findById(task_id);
        Task t = test.get();
        t.markAsComplete(user_id);
        tr.save(t);
        return t.getTimeCompleted();
    }

}
