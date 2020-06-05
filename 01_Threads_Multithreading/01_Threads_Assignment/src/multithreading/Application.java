package multithreading;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Application
{
	public static void main(String[] args)
	{
		//Erstellen einer Array-Liste vom Typ String mit dem Inhalt aller Webadressen
		ArrayList<String> listOfWebsites = new ArrayList<>();
		
		listOfWebsites.add("https://www.spiegel.de/");
		listOfWebsites.add("https://www.zeit.de/");
		listOfWebsites.add("https://www.faz.net/");
		listOfWebsites.add("https://www.tagesschau.de/");
	    listOfWebsites.add("https://www.nzz.ch/");
	    listOfWebsites.add("https://www.krone.at/");
	    listOfWebsites.add("https://www.yomiuri.co.jp");
	    listOfWebsites.add("https://timesofindia.indiatimes.com");
	    listOfWebsites.add("https://www.nytimes.com");
	    listOfWebsites.add("https://www.bild.de/");
	    listOfWebsites.add("http://www.cankaoxiaoxi.com/");
	    listOfWebsites.add("https://www.dailymail.com");
	    listOfWebsites.add("https://www.usatoday.com");
	    listOfWebsites.add("https://news.chosun.com");
	    listOfWebsites.add("https://www.thairath.co.th/");
	    listOfWebsites.add("http://english.ahram.org.eg/");
	    listOfWebsites.add("https://jang.com.pk/");
	    listOfWebsites.add("https://www.ouest-france.fr/");
	    listOfWebsites.add("https://www.kp.ru/");
	    listOfWebsites.add("https://www.telegraaf.nl)");
	   
	    //Ausführung der run-Methode auf die selbe Liste von Webadressen mit unterschiedlicher Anzahl an Threads
		run(listOfWebsites, 1);
		run(listOfWebsites, 2);
		run(listOfWebsites, 4);
		run(listOfWebsites, 6);
		run(listOfWebsites, 8);
		run(listOfWebsites, 10);
		run(listOfWebsites, 20);
	}
	
	public static void run(ArrayList<String> randomList, int numberOfThreads)
	{
	    ArrayList<String> listOfWebsites = randomList;
	    ArrayList<Task> numberOfTasks = new ArrayList<Task>();
	    	 
	    ExecutorService execService = Executors.newFixedThreadPool(numberOfThreads);
	    	
	    //Die Liste von Strings wird in eine Liste von Jobs überführt
	    double startTime = System.currentTimeMillis();
	    for(String website : listOfWebsites)
	    {
	    	numberOfTasks.add(new Task(website));
	    }
	    try
	    {
	    	//Führt alle gegebenen Tasks aus und liefert eine Liste von Future-Objekten mit Ergebnissen und deren Status zurück
	    	execService.invokeAll(numberOfTasks);
		}
	    catch (InterruptedException e)
	    {
			e.printStackTrace();
		}
	    	 
	    double stopTime = System.currentTimeMillis();
	    	
	    //Keine weiteren Tasks werden angenommen durch shutdown
	    execService.shutdown();
	    	
	    double duration = (stopTime - startTime)/1000;
	    	 
	    System.out.println("Ausführung mit " + numberOfThreads + " Thread(s) in " + duration + " Sekunden");
	 }
 		
}
