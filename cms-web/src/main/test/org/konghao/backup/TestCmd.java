package org.konghao.backup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.junit.Test;

public class TestCmd {

	@Test
	public void testSimpleCmd() {
		try {
			String cmd = "cmd /c dir c:\\";
			Process proc = Runtime.getRuntime().exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream(),"utf-8"));
			String str = null;
			while((str=br.readLine())!=null) {
				System.out.println(str);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMySql() {
		try {
			String cmd = "cmd /c mysqldump -ufz -pfz123 fz_cms";
			Process proc = Runtime.getRuntime().exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedWriter bw = new BufferedWriter(new FileWriter("d:/fz.sql"));
			String str = null;
			while((str=br.readLine())!=null) {
				bw.write(str);
				bw.newLine();
			}
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testResume() {
		try {
			String cmd = "cmd /c mysql -ufz -pfz123 fz_cms";
			Process proc = Runtime.getRuntime().exec(cmd);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(proc.getOutputStream()));
			BufferedReader br = new BufferedReader(new FileReader("d:/fz.sql"));
			String str = null;
			while((str=br.readLine())!=null) {
				bw.write(str);
				bw.newLine();
			}
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
