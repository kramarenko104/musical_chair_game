package com.gmail.kramarenko104;

import java.util.concurrent.TimeUnit;

public class Player implements Runnable {

	private String name;
	private Chair chair;
	private ThreadMusic tMusic;

	public Player(String name, Chair chair, ThreadMusic tMusic) {
		this.name = name;
		this.chair = chair;
		this.tMusic = tMusic;
	}

	public void run() {
		while (true) {
			while (!tMusic.isPlaying()) {
				synchronized (chair) {
					chair.occupy();
					System.out.println("	Player " + name + ": is sitting on the chair for 3 sec....!!!!!");
					try {
						TimeUnit.SECONDS.sleep(3);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("	Player " + name + ": leaves chair ");
					chair.free();
					tMusic.setIsPlaying(true);
					tMusic.run();
					System.out.println("	Player " + name + ": exit game");
					return;
				}
			}
		}
	}
}
