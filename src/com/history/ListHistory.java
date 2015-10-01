package com.history;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ListHistory {
	
	
	
	public static void writeHistory(ArrayList<String> list) throws IOException{
		FileOutputStream fos = new FileOutputStream("D:\\output.txt");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(list);
		oos.close();
	}
	
	public static ArrayList<String> readHistory() throws ClassNotFoundException, IOException{
		FileInputStream fis = new FileInputStream("D:\\output.txt");
		ObjectInputStream ois = new ObjectInputStream(fis);
		ArrayList<String> historyList = (ArrayList<String>) ois.readObject();
		ois.close();
		return historyList;
	}
	
	public static void writeHistory(HashMap<String, String> map) throws IOException{
		FileOutputStream fos = new FileOutputStream("D:\\output1.txt");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(map);
		oos.close();
	}
	
	public static HashMap<String, String> readHistoryMap() throws ClassNotFoundException, IOException{
		FileInputStream fis = new FileInputStream("D:\\output1.txt");
		ObjectInputStream ois = new ObjectInputStream(fis);
		HashMap<String, String> historyMap = (HashMap<String, String>) ois.readObject();
		ois.close();
		return historyMap;
	}
	/*public static void main(String[] args) throws IOException, ClassNotFoundException {
		ArrayList<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("6");
		list.add("7");
		list.add("8");
		list.add("9");
		writeHistory(list);
		ArrayList<String> arrayList = readHistory();
		System.out.println("read: " + arrayList);
	}*/
}
