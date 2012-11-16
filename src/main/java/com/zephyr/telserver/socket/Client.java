package com.zephyr.telserver.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	public static void main(String[] argvs){
		try
		{
			Socket socket = new Socket("localhost",4700);
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write("012503000213488669242    1559966408592096932846576590                          012012110902544300000313488669242            ".getBytes());
			outputStream.flush();
			System.out.println(socket);
			InputStream is = socket.getInputStream();
			byte[] bytes = new byte[1024];
			int n = is.read(bytes);
			System.out.println(new String(bytes, 0, n));
			is.close();
			socket.close();
		} catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
