package com.zephyr.telserver.socket;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {
	
	public static void main(String[] argvs) throws IOException{
		Socket socket = null;
		try {
			socket = new Socket("localhost",4700);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			OutputStream writer = new BufferedOutputStream(socket.getOutputStream());
			
			String message = "003610999915010519840701871X        " ;
			writer.write(message.getBytes());
			writer.flush();
			
			char[] c = new char[4];
			char[] rc = null ;
			int i ;
			String rs; 
			boolean flag = false ;
			//模锟斤拷 锟斤拷锟斤拷锟斤拷证锟斤拷锟斤拷锟斤拷状态锟斤拷询锟斤拷锟襟，凤拷锟斤拷状态锟斤拷锟斤拷锟酵匡拷锟斤拷录锟斤拷锟斤拷 锟斤拷锟秸伙拷应锟饺硷拷锟斤拷锟斤拷锟斤拷锟斤拷
			while ((i = reader.read(c) ) > 0 ){
				rc = new char[Integer.parseInt(new String(c)) - 4 ];
				reader.read(rc) ;
				rs = new String(rc);
				System.out.println(rs);
//				if ( rs.startsWith("10") ){
//					System.out.println("锟秸碉拷锟斤拷证锟斤拷应:" + rs);
//					
//				}
//				if ( rs.startsWith("82") ) {
//					System.out.println("锟秸碉拷锟斤拷锟斤拷状态锟斤拷锟斤拷锟斤拷:" + rs);
//					message = "011882999915010519840701871XVER1.00 2004/05/10123                                                   VER1.00 2005/10/10";
//					writer.write(message.getBytes());
//					writer.flush();
//					message = "009301999913488669242    15010519840701871X                  1113              20121013173028";
//					writer.write(message.getBytes());
//					writer.flush();
//					flag = true ;
//				}
//				
//				if ( rs.startsWith("01") ) {
//					System.out.println ( "锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷斜锟?" + rs ) ;
//				}
//				
//				if ( flag ){
				String sb = "012503000013488669242    1559966408592096933015725598                          012012101517331300082413910718125             ";
					writer.write(sb.getBytes());
					writer.flush();
					//flag = false ;
				//}

			}
			
			socket.close();
			
			
		    
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			socket.close();
		}
	}

}
