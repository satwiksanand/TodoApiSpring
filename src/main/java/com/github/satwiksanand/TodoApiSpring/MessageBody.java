package com.github.satwiksanand.TodoApiSpring;

public class MessageBody{
	private String message;
	private int statusCode;

    public MessageBody(int statusCode, String message){
        this.message = message;
        this.statusCode = statusCode;
    }

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatusCode(int statusCode){
		this.statusCode = statusCode;
	}

	public int getStatusCode(){
		return statusCode;
	}

	@Override
 	public String toString(){
		return 
			"MessageBody{" + 
			"message = '" + message + '\'' + 
			",statusCode = '" + statusCode + '\'' + 
			"}";
		}
}
