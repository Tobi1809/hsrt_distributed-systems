package tcp;

import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.net.UnknownHostException;

import hs.rt.vs.CalculationTask;


public class Client
{
	public static void main(String[] args) throws ClassNotFoundException, IllegalClassFormatException
	{
		//IP-Adresse und Port des Hochschulservers
		String serverAddress = "134.103.216.90";
		int serverPort = 11223;
		

		//Erstellen der TCP-Verbindung zum Hochschulserver
		System.out.println("Aufbau der Verbindung zum Server...\n");
		for (int i = 0; i < 5; i++)
		{
			int counter = i + 1;
			
			try(TCPSocket tcpSocket = new TCPSocket(serverAddress, serverPort))
			{
			CalculationTask task = tcpSocket.receiveCalculationTask();
			//System.out.println("First value sent from server: " + task.getFirstValue());
			//System.out.println("Second value sent from server: " + task.getSecondValue());
			
			calculateResult(task);
			//System.out.println("Result of squaring-calculation " + counter + ": " + task.getResult());
			
			//Ergebnis der Berechnung wird an den Server verschickt
			tcpSocket.sendResult(task);
			
			String responseFromServer = tcpSocket.readLine();
			System.out.println("Response from server for squaring-calculation " + counter + ": " + responseFromServer + "\n");
			}
			catch (UnknownHostException e)
			{
			e.printStackTrace();
			}
			catch (IOException e)
			{
			e.printStackTrace();
			}
		}
		//tcpSocket.close() nicht nötig, da Klasse TCPSocket das Interface Autocloseable implementiert
		System.out.println("TCP-Verbindung zum Server wurde geschlossen.");
	}

	private static void calculateResult(CalculationTask task)
	{
		double firstValue = task.getFirstValue();
		double secondValue = task.getSecondValue();
		task.setResult(Math.pow(firstValue*secondValue, 2));
	}

}
