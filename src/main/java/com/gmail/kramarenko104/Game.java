package com.gmail.kramarenko104;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Game {

	private int countPlayers;
	private LinkedList<Thread> playerThreads = new LinkedList<>();
	private ThreadMusic tMusic;

	public Game(int countPlayers) {
		this.countPlayers = countPlayers;
	}

	public void startGame() throws InterruptedException {

		Chair chair = new Chair();
		
		// create threads
		tMusic = new ThreadMusic();

		for (int i = 0; i < countPlayers; i++) {
			Thread t = new Thread(new Player(Integer.toString(i), chair, tMusic), "Player " + Integer.toString(i));
			playerThreads.add(t);
		}
		
		// start all threads
		tMusic.start();
		for (Thread player : playerThreads) {
			player.start();
		}

		// play game while there are any live players
		while (thereIsLivePlayer()) {
			if (tMusic.isPlaying()) {
				int randTimeToStopMusic = (int) (Math.random() * 1000);
				System.out.println("Music will be stopped in " + randTimeToStopMusic + " milliseconds...");
				TimeUnit.MILLISECONDS.sleep(randTimeToStopMusic);
				tMusic.stopPlaying();
			}
		}

		// be sure that all threads are finished
		for (Thread player : playerThreads) {
			if (player.isAlive()) {
				player.join();
			}
		}
		System.out.println("=====================================================================");
		System.out.println("Game is OVER!");
	}

	//////////////////////////////////////////////////////////////
	private boolean thereIsLivePlayer() {
		for (Thread player : playerThreads) {
			if (player.isAlive()) {
				return true;
			}
		}
		return false;
	}
}
