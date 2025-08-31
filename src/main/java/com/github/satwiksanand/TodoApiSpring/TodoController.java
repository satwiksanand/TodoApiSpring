package com.github.satwiksanand.TodoApiSpring;

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

@RestController
public class TodoController {

    private static List<Todo> todos;

    public TodoController(){
        todos = new ArrayList<>();
        todos.add(new Todo(1, false, "Todo 1", 1));
        todos.add(new Todo(2, true, "Todo 2", 2));
    }
}
