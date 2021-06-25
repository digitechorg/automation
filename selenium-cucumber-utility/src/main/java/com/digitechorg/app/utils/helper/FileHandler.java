package com.digitechorg.app.utils.helper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileHandler {
	
	public static void createDirectory(String fileName) {
		try {
			FileUtils.touch(new File (fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void cleanDiretory(String FileName) {
		try {
			FileUtils.cleanDirectory(new File( FileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void writeInDirectory(List<String> list, String path) {
		
		try {
			FileUtils.writeLines(new File(path), "UTF-8", list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	

}
