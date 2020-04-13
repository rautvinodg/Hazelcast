package com.example.demo.config;

import java.io.Serializable;

public class HelloTask implements Runnable, Serializable {

	private static final long serialVersionUID = -2727837260502261351L;
	
	@Override 
    public  void run () {
		
        System.out.println("Hello from Server 1 !!");
    }
}
