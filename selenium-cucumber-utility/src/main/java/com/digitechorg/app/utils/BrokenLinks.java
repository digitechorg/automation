package com.digitechorg.app.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.digitechorg.app.utils.helper.FileHandler;


public class BrokenLinks {

	private static WebDriver driver = null;
	private static List<String> validLink = new ArrayList<String>();
	private static List<String> brokenLink = new ArrayList<String>();
	private static List<String> skippedLink = new ArrayList<String>();
	
	
	 
	// createDirectory(name);
	
	
	
	public static void main(String[] args) {
		FileHandler.cleanDiretory("testFolder/");
		
		System.setProperty("webdriver.chrome.driver","C:\\Users\\44745\\workspace\\\\digitechorg\\selenium-cucumber-utility\\src\\main\\resources\\driver\\chromedriver.exe");
		String homePage = "https://www.facebook.com/";
		String url = "";
		HttpURLConnection huc = null;
		int respCode = 200;
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(homePage);
		List<WebElement> links = driver.findElements(By.tagName("a"));
		Iterator<WebElement> it = links.iterator();
		
		while (it.hasNext()) {
			url = it.next().getAttribute("href");
		//	System.out.println(url);

			if (url == null || url.isEmpty()) {
				System.out.println("URL is either not configured for anchor tag or it is empty");
				continue;
			}

			if (!url.startsWith(homePage)) {
				System.out.println("URL belongs to another domain, skipping it ======>>> "+url);
				skippedLink.add(url);
				continue;
			}

			try {
				huc = (HttpURLConnection) (new URL(url).openConnection());
				huc.setRequestMethod("HEAD");
				huc.connect();
				respCode = huc.getResponseCode();
				if (respCode >= 400) {
					System.out.println(url + " ======== is a broken link ====== "+ respCode);
					brokenLink.add(url);
				} else {
					System.out.println(url + " ======== is a valid link ======= " +respCode);
					validLink.add(url);
				}

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		FileHandler.writeInDirectory(skippedLink, "testFolder/skippedLinks.txt");
		System.out.println("Total skipped Links =============== :: "+skippedLink.size());
		FileHandler.writeInDirectory(validLink, "testFolder/validLinks.txt");
		System.out.println("Total valid Links ============ :: "+validLink.size());
		FileHandler.writeInDirectory(brokenLink, "testFolder/BrokenLinks.txt");
		System.out.println("Total broken Links ============ :: "+brokenLink.size());
		driver.quit();
 
	}

}
