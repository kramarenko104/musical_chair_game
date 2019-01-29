package com.gmail.kramarenko104;

public class Chair {

	private volatile boolean isBusy = false;

	public void occupy() {
		isBusy = true;
	}

	public void free() {
		isBusy = false;
	}

}
