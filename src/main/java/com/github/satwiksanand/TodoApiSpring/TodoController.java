package com.github.satwiksanand.TodoApiSpring;

//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/v1/todos")
public class TodoController {

    private static List<Todo> todoList;

    public TodoController(){
        todoList = new ArrayList<>();
        todoList.add(new Todo(1, false, "Todo 1", 1));
        todoList.add(new Todo(2, true, "Todo 2", 2));
    }

    //modifying the getTodoList function to return filtered data as well.
    @TimeMonitor
    @GetMapping
    public List<Todo> getTodoList(@RequestParam(required = false) Boolean isCompleted, @RequestParam(required = false) Integer userId){
        List<Todo> newTodoList = new ArrayList<>();
        for(Todo todo : todoList){
            boolean matches = true;
            if(isCompleted != null){
                matches = (todo.isCompleted() == isCompleted);
            }
            if(userId != null){
                matches = matches && (todo.getUserId() == userId);
            }
            if(matches){
                newTodoList.add(todo);
            }
        }
        return newTodoList;
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo newTodo){
        /*
        * we can use @ResponseStatus(HttpStatus.CREATED) annotation to actually denote that
        * the status code is 201 which is the rest convention for a successful created api call.
        *
        * if you want to manually maintain the response body, you can use something called the
        * ResponseEntity<T> as well, notice the similarity with generics in typescript.
        * */
        todoList.add(newTodo);
        return newTodo;
    }

//    here is an implementation of using that ResponseEntity
//    do not forget to comment out the imports before using this lol.
//        @PostMapping("/todos")
//        public ResponseEntity<Todo> createTodo(@RequestBody Todo newTodo){
//            todoList.add(newTodo);
//            return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
//        }

    @GetMapping("/{todoId}")
    public ResponseEntity<?> getTodoById(@PathVariable Long todoId){
        for(Todo todo : todoList){
            if(todo.getId() == todoId){
                return ResponseEntity.ok(todo);
            }
        }
        MessageBody newMessage = new MessageBody(404, "No Such todo found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(newMessage);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<?> deleteTodoById(@PathVariable Long todoId){
        boolean found = false;
        int ind = -1;
        for(int i = 0; i < todoList.size(); ++i)
        {
            if(todoList.get(i).getId() == todoId){
                ind = i;
                found = true;
            }
        }
        todoList.remove(ind);
        MessageBody newMessage = new MessageBody((found ? 200 : 404), (found ? "Deleted todo" : "Not found"));
        return ResponseEntity.status(found ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(newMessage);
    }

    @PatchMapping
    public ResponseEntity<Todo> patchTodoById(@RequestBody Todo newTodo) {
        int size = todoList.size();
        for(int i = 0; i < size; ++i){
            if(todoList.get(i).getId() == newTodo.getId()){
                todoList.get(i).setTitle(newTodo.getTitle());
                todoList.get(i).setCompleted(newTodo.isCompleted());
                todoList.get(i).setUserId(newTodo.getUserId());
            }
        }
        return ResponseEntity.ok(newTodo);
    }
}
