package com.balacraft.balapiano.soundengine;

import java.util.LinkedList;

public class Fifo {
	LinkedList<Note> todoList = new LinkedList<Note>();
	public void add(Note n) {
		synchronized(this) {
		todoList.add(n);
		}
	}
	public Note getNote() {
		synchronized(this) {
			return todoList.removeFirst();
		}
	}
}
