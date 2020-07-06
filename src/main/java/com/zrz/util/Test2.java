package com.zrz.util;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test2 {
	public static void main(String[] args) throws InterruptedException {

		int threadNum = 100;
		ExecutorService exec = Executors.newFixedThreadPool(threadNum);
		
		List<Callable<String>> tasks = new LinkedList<Callable<String>>();
		Callable<String> task = new Callable<String>() {
            @Override
            public String call() throws Exception {
                //插入数据
            	String id =  Thread.currentThread().getId()+"";
            	System.out.println(id);
            	Thread.sleep(500);
                return id;
            }
        };
        for(int i=0;i<500;i++){
        	tasks.add(task);
        }
		
		
		//List<Future<String>> results = exec.invokeAll(tasks);
		
		for(int i=0;i<500;i++){
			exec.submit(task);
        }
	}
}