package org.konghao.backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class TestTarAndGzip {

	@Test
	public void testSimpleTar() {
		try {
			String path = "D:\\作业\\tools\\jktkdzjst";
			String f = "d:/2013-8-25/test.tar";
			TarArchiveOutputStream taos = new TarArchiveOutputStream(new FileOutputStream(f));
			File of = new File(path);
			File[] ofs = of.listFiles();
			for(File off:ofs) {
				FileInputStream fis = new FileInputStream(off);
				//此时创建Entry时是通过off来传的，会自动找到相应的绝对路径完成打包，此时如果希望通过相对路径来打包，需要手动设置路径地址
//				TarArchiveEntry tae = new TarArchiveEntry(off);
				String p = off.getParentFile().getParent();
				String pn = off.getParentFile().getAbsolutePath().substring(p.length()+1);
				System.out.println(pn);
				TarArchiveEntry tae = new TarArchiveEntry(pn+File.separator+off.getName());
				tae.setSize(off.length());
				taos.putArchiveEntry(tae);
				IOUtils.copy(fis, taos);
				fis.close();
				taos.closeArchiveEntry();
			}
			taos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSimpleUnTar() {
		try {
			TarArchiveInputStream tais = new TarArchiveInputStream(new FileInputStream("d:/2013-8-25/test.tar"));
			TarArchiveEntry tae = null;
			String path = "e:/";
			while((tae=tais.getNextTarEntry())!=null) {
				String p = path+File.separator+tae.getName();
				File f = new File(p);
				if(!f.getParentFile().exists()) {
					f.getParentFile().mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(path+File.separator+tae.getName());
				IOUtils.copy(tais, fos);
				fos.close();
			}
			tais.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGzip() {
		try {
			GZIPOutputStream gzos = new GZIPOutputStream(new FileOutputStream("e:/test.tar.gz"));
			FileInputStream fis = new FileInputStream("d:/2013-8-25/test.tar");
			IOUtils.copy(fis, gzos);
			gzos.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUnGzip() {
		try {
			GZIPInputStream gzis = new GZIPInputStream(new FileInputStream("e:/test.tar.gz"));
			FileOutputStream fos = new FileOutputStream("e:/test.tar");
			IOUtils.copy(gzis,fos);
			gzis.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testTarFile() {
		String path = "d:/kh/stu";
		String tp = "d:/stu.tar";
		tarFile(path,tp);
	}
	
	@Test
	public void testUnTarFile() {
		String path = "d:/test";
		File f = new File("d:/stu.tar.gz");
		unGzipFile(f);
		
		unTarFile(new File("d:/stu.tar"), path);
	}
	
	private void tarFile(String path,String tarFile) {
		TarArchiveOutputStream taos = null;
		try {
			File f = new File(path);
			int len = f.getParent().length();
			taos = new TarArchiveOutputStream(new FileOutputStream(tarFile));
			taos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
			tarFile(new File(path),taos,len);
			gzipFile(new File(tarFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if(taos!=null) taos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void unTarFile(File file,String path) {
		TarArchiveInputStream tais = null;
		try {
			tais = new TarArchiveInputStream(new FileInputStream(file));
			TarArchiveEntry tae = null;
			while((tae=tais.getNextTarEntry())!=null) {
				String name = path+File.separator+tae.getName();//d:/test/stu/.classpath
				FileOutputStream fos = null;
				File ff = new File(name);
				if(!ff.getParentFile().exists()) ff.getParentFile().mkdirs();
				try {
					fos = new FileOutputStream(ff);
					IOUtils.copy(tais, fos);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if(fos!=null) fos.close();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(tais!=null) tais.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void unGzipFile(File file) {
		GZIPInputStream gis = null;
		FileOutputStream fos = null;
		try {
			gis = new GZIPInputStream(new FileInputStream(file));
			String path = file.getAbsolutePath();
			path = path.substring(0,path.lastIndexOf("."));
			System.out.println(path);
			fos = new FileOutputStream(path);
			IOUtils.copy(gis, fos);
			file.deleteOnExit();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(gis!=null) gis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(fos!=null) fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private void gzipFile(File file) {
		GZIPOutputStream gos = null;
		FileInputStream fis = null;
		try {
			gos = new GZIPOutputStream(new FileOutputStream(file.getAbsolutePath()+".gz"));
			fis = new FileInputStream(file);
			IOUtils.copy(fis, gos);
			file.deleteOnExit();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(gos!=null) gos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(fis!=null) fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void tarFile(File file, TarArchiveOutputStream taos,int len) {
		if(file.isDirectory()) {
			File[] fs = file.listFiles();
			for(File f:fs) {
				tarFile(f,taos,len);
			}
		} else {
			FileInputStream fis = null;
			try {
//				System.out.println(file.getAbsolutePath().substring(len)+File.separator+file.getName());
				TarArchiveEntry tae = new TarArchiveEntry(file.getParent().substring(len)+File.separator+file.getName());
				tae.setSize(file.length());
				fis = new FileInputStream(file);
				taos.putArchiveEntry(tae);
				IOUtils.copy(fis, taos);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(fis!=null) fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					taos.closeArchiveEntry();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
