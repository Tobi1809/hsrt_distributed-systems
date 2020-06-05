package tcp;

import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.io.IOException;

import hs.rt.vs.CalculationTask;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.instrument.IllegalClassFormatException;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class TCPSocket implements AutoCloseable
{
	private Socket socket;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
    
	
    public TCPSocket(String serverAddress, int serverPort) throws UnknownHostException, IOException
    {
			socket = new Socket (serverAddress, serverPort);
			initializeStreams();
    }
    
    public TCPSocket(Socket socket) throws IOException
    {
    	this.socket = socket;
    	initializeStreams();
    }
    
    /* Erstellt einen Eingabe- und Ausgabestrom zum Lesen oder Schreiben von Daten über die TCP-Verbindung.
     * In diesem Fall: Byteweise Eingabe und Ausgabe!
     */
    private void initializeStreams() throws IOException
    {
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    }
    
    /* Objektserialisierung - liest ein Objekt aus dem ObjectInputStream und gibt dieses nach Prüfung auf der Konsole aus.
     * Erhaltene Objekte müssen das Interface Serializable implementieren!
     */
    public CalculationTask receiveCalculationTask() throws IOException, ClassNotFoundException, IllegalClassFormatException
    {
    	Object receivedObject = objectInputStream.readObject();
    	
    	if (receivedObject instanceof CalculationTask)
    	{
    		return (CalculationTask) receivedObject;
    	}
    	else
    	{
    		throw new IllegalClassFormatException();
    	}
    }
    
    /* Objektserialisierung - schreibt ein Objekt aus dem ObjectOutputStream und sendet dieses dann an den Empfänger.
     * Versendete Objekte müssen auch das Interface Serializable implementieren!
     * Mit flush() wird ein rausschreiben erzwungen.
     */
    public void sendResult(CalculationTask task) throws IOException
    {
    	objectOutputStream.writeObject(task);
    	objectOutputStream.flush();
    }
    
    //String mit Socket empfangen, der mit einem Line-End ('\n') getrennt ist.
    public String readLine() throws IOException
    {
    	BufferedReader br = new BufferedReader(
    							new InputStreamReader(
    									socket.getInputStream()));
    	String message = br.readLine();
    	
		return message;		
    }
    
    // Schließt den Socket, damit Port wieder freigegeben wird.
    public void close() throws IOException
    {
        socket.close();
    }
    
    /* Timeout für den Socket, damit nicht unendlich auf eine Antwort vom Server gewartet werden muss.
     * In der Aufgabe nicht notwendig, da Verbindung automatisch vom Server nach 2 Sekunden geschlossen wird.
     */
    public void setSoTimeout(int timeout) throws SocketException
    {
    	socket.setSoTimeout(timeout);
    }
    
    public Socket getSocket()
    {
        return socket;
    }

}
