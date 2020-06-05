package multithreading;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Callable;


public class Task implements Callable<String>
{
	String website;
	
	
	public Task(String website)
	{
		this.website = website;
	}
	
	public static String openWebsite(String website)
    {
    	HttpClient client = HttpClient.newHttpClient();
    	HttpRequest request = HttpRequest.newBuilder().uri(URI.create(website)).build();
    	
    	try
    	{
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		}
    	catch (IOException e)
    	{
			e.printStackTrace();
		}
    	catch (InterruptedException e)
    	{
			e.printStackTrace();
		}
		return website;
    }
	
	@Override
	public String call() throws Exception
	{
		return openWebsite(website);
	}
	
}
