package com.example.CyCHORE.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class TaskController {

    @Autowired
    TaskRepository tr;

    @RequestMapping(value = "/getTaskList/{id}", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    private String getTaskList(@PathVariable Integer id) throws JSONException {
        List<Task> allTaskList;
        allTaskList = tr.findAll();
        List<String> taskList = new ArrayList<String>();;
        JSONObject toSend = new JSONObject();
        int todoCount = 0;
        int finishCount = 0;
        JSONObject toDo = new JSONObject();
        JSONObject finished = new JSONObject();

        for (Task temp : allTaskList) {
            if (temp.is_assigned_to() == id){

                JSONObject curTask = new JSONObject();
                //JSONObject curTask = new JSONArray();
                curTask.put("title",temp.toString());
                curTask.put("tid",temp.getId());
                curTask.put("ddl",temp.getDdl());
                curTask.put("complete",temp.is_completed());
                if(temp.is_completed()){
                    finishCount++;
                    finished.put(temp.toString(), curTask);
                }
                else{
                    todoCount++;
                    toDo.put(temp.toString(), curTask);
                }
            }
        }
        toSend.put("status", "0");
        toSend.put("todo_count",todoCount);
        toSend.put("finish_count",finishCount);
        toSend.put("todo_json",toDo.toString());
        toSend.put("finish_json",finished.toString());
        return toSend.toString();
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
