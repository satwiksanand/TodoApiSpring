package com.github.satwiksanand.TodoApiSpring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

//RestController annotation is a combination of Controller and ResponseBody annotation
//the responsibility of this annotation is to tell the spring boot project that this class
//is a controller, the controller on the other hand is responsible for only collecting the request
//from the client and responding back, it does not contain any business logic.

//so the RestController annotation has two different objectives:
//identify the current class as a controller.
//write the response in the http response body

//on the other hand the GetMapping annotation says that if a get request comes at a
//specific route execute a certain function.

//notice that we are directly returning an object and not json data, we are able to do that
//because behind the scenes, spring boot autoconfigures jackson to convert the java object
//into http-favourable format, thus jackson convert the java object into json and then adds it to the
//response body.

@RestController
public class TodoController {

    private static List<Todo> todoList;

    public TodoController(){
        todoList = new ArrayList<>();
        todoList.add(new Todo(1, false, "Todo 1", 1));
        todoList.add(new Todo(2, true, "Todo 2", 2));
    }

    @GetMapping("/todos")
    public List<Todo> getTodoList(){
        return todoList;
    }

    @PostMapping("/todos")
    public Todo createTodo(@RequestBody Todo newTodo){
        todoList.add(newTodo);
        return newTodo;
    }
}
