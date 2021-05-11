package com.sd.mt;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MTSDServletTest {
	
	private static final int NUM_THREADS = 10;
	private static final String[] messages = {"tony", "mona", "puja", "anil", "will", "john", "dory", "bert", "kong", "dang"};
	Runnable[] runnables;
	Thread[] threads;
	
	class MyRunnable implements Runnable {
		
		//static variables
		//private static final String USER_AGENT = "";
		private static final int THREAD_SLEEP_MAX = 100; //maximum no. of milliseconds a thread sleeps
		private static final String GET_URL = "http://localhost:8080/MultiThreadedServletDemo/msg?message=%s";
		
		//constructor
		public MyRunnable(String message) {
			super();
			this.message = message;
		}

		//thread method
		@Override
		public void run() {
			//random wait
			Random random = new Random();
			int sleepMilliSecs = random.nextInt(THREAD_SLEEP_MAX);
			try {
				Thread.currentThread().sleep(sleepMilliSecs);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendGet();
		}
		
		//helper to send a GET request
		private void sendGet() {
			String sUrl = String.format(GET_URL, message); //add the message as a GET parameter 
			URL url;
			try {
				url = new URL(sUrl); //create a url
				try {
					HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //open a connection
					conn.setRequestMethod("GET");
					int responseCode = conn.getResponseCode();
					//System.out.printf("%s %d", message, responseCode);
					if (responseCode == HttpURLConnection.HTTP_OK) {
						//buffered reader to read output
						BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						String inputLine; //input line to store the buffered line just read
						//string buffer to accumulate the read output
						StringBuffer response = new StringBuffer();
						//start reading
						while ((inputLine = in.readLine()) != null) {
							response.append(inputLine);
						}
						//close the reader
						in.close();
						//print the result
						System.out.printf("%s => %s\n", message, response.toString());
					} else {
						System.out.printf("HTTP request failed for %s.\n", message);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} 
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
		}
		
		//member variable
		String message;
	}

	//helper to start all threads
	private void startAllThreads() {
		for (Thread t: threads) {
			t.start(); //start the thread
		}
	}
	
	//helper to join (wait for) all threads
	private void joinAllThreads() {
		for (Thread t: threads) {
			try {
				t.join(); //make this thread for thread 't' to finish and join
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@BeforeEach
	void setUp() throws Exception {
		//allocate arrays
		runnables = new Runnable[NUM_THREADS];
		threads = new Thread[NUM_THREADS];
		
		//initialize runnables and threads and assign them to array elements 
		for (int i = 0; i < NUM_THREADS; i++) {
			runnables[i] = new MyRunnable(messages[i]); //create runnable
			threads[i] = new Thread(runnables[i]); //create thread from runnable
		}
	}

	/*
	 * HURRAy HURRAY HURRAY!!! Reproduced the multi-threaded servlet behavior
	 * john => Safe Text: 'john'. Unsafe Text: 'will'.
bert => Safe Text: 'bert'. Unsafe Text: 'will'.
will => Safe Text: 'will'. Unsafe Text: 'will'.
puja => Safe Text: 'puja'. Unsafe Text: 'puja'.
anil => Safe Text: 'anil'. Unsafe Text: 'anil'.
mona => Safe Text: 'mona'. Unsafe Text: 'mona'.
kong => Safe Text: 'kong'. Unsafe Text: 'dang'.
dang => Safe Text: 'dang'. Unsafe Text: 'dang'.
tony => Safe Text: 'tony'. Unsafe Text: 'tony'.
dory => Safe Text: 'dory'. Unsafe Text: 'dory'.
	 */
	
	@Test
	void test() {
		startAllThreads();
		joinAllThreads();
	}

}
