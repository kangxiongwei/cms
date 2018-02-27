package org.konghao.backup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbSession;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class TestSmb {
	@Test
	public void testPut() {
		try {
			/**
			 * ip，username，password这些值如果在正式项目中使用，全部应该通过配置文件来设置
			 */
			String ip = "192.168.101.2";
			UniAddress ua = UniAddress.getByName(ip);
			NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(ip, "s1", "123");
			SmbSession.logon(ua, auth);//验证是否能够成功登录
			//创建Smb文件,地址一定要使用smb://
			SmbFile sf = new SmbFile("smb://"+ip+"/samba/goagent-goagent-7805174.zip", auth);
			IOUtils.copyLarge(new FileInputStream("d:/goagent-goagent-7805174.zip"), sf.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SmbException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGet() {
		try {
			/**
			 * ip，username，password这些值如果在正式项目中使用，全部应该通过配置文件来设置
			 */
			String ip = "192.168.101.2";
			UniAddress ua = UniAddress.getByName(ip);
			NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(ip, "s1", "123");
			SmbSession.logon(ua, auth);//验证是否能够成功登录
			//创建Smb文件,地址一定要使用smb://
			SmbFile sf = new SmbFile("smb://"+ip+"/samba/goagent-goagent-7805174.zip", auth);
			IOUtils.copyLarge(sf.getInputStream(), new FileOutputStream("e:/tt.zip"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SmbException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
