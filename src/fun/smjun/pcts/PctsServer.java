package fun.smjun.pcts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Date;

public class PctsServer {
	public static void main(String[] args) {
		PctsServer server = new PctsServer();
		server.exec(args);
	}

	private void exec(String[] args) {
		int portNumber = 8888; // default
		if (args.length < 0)
			portNumber = Integer.parseInt(args[0]);

		try {
			run(portNumber);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void run(int portNumber) throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(portNumber); Socket clientSocket = serverSocket.accept();) {
			try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
				String from = clientSocket.getLocalAddress() + ":" + clientSocket.getLocalPort();
				out.println("hello, " + from + "!");

				String inputLine, outputLine;
				while ((inputLine = in.readLine()) != null) {
					Date date = new Date();
					Timestamp ts = new Timestamp(date.getTime());
					String time = ts.toString();
					String chk = String.format("%s, %s: %s", from, time, inputLine);
					System.out.println(chk);

					outputLine = inputLine;
					out.println(outputLine);
					if (outputLine.equals("exit"))
						break;
				}
			}
		}
	}
}
