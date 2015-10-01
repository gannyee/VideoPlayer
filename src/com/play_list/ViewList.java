package com.play_list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
/**
 * A class to store history
 * @author ganyee
 *
 */
public class ViewList {
	private HashMap<String, String> map = new HashMap<String, String>();
	private ArrayList<String> list = new ArrayList<String>();

	public HashMap<String, String> getMap() {
		return map;
	}

	public void setMap(String name, String url) {
		map.put(name, url);
	}

	public ArrayList<String> getList() {
		return list;
	}

	public void setList(String name) {
		list.add(name);
	}

	public void setList(ArrayList<String> list) {
		this.list = list;
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

}
