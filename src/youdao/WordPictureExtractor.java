package youdao;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class WordPictureExtractor {

	public final static String FOLDER = "C:\\Users\\i042416\\Pictures\\WORD\\";

	public static void main(String[] args) throws Exception {
		try {
			readZipFile("C:\\Users\\i042416\\Pictures\\1.docx");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void copyImage(String fileName, InputStream input) {
		try {
			String[] result = fileName.split("/");
			String outputFileName = FOLDER + result[2];
			FileOutputStream fileOutputStream = new FileOutputStream(new File(
					outputFileName));
			ByteArrayOutputStream output = new ByteArrayOutputStream();

			byte[] buffer = new byte[1024];
			int length;

			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}

			fileOutputStream.write(output.toByteArray());
			input.close();
			fileOutputStream.close();
			System.out.println("File: " + outputFileName
					+ " copied successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readZipFile(String file) throws Exception {
		ZipFile zf = new ZipFile(file);
		InputStream in = new BufferedInputStream(new FileInputStream(file));
		ZipInputStream zin = new ZipInputStream(in);
		ZipEntry ze;
		while ((ze = zin.getNextEntry()) != null) {
			if (ze.isDirectory()) {
				continue;
			}
			String fileName = ze.getName();
			if (!fileName.contains("word/media/image"))
				continue;
			System.out.println(fileName + " : " + ze.getSize() + " bytes");
			long size = ze.getSize();
			if (size > 0) {
				InputStream image = zf.getInputStream(ze);
				copyImage(fileName, image);
			}
		}
		zin.closeEntry();
		zin.close();
		zf.close();
	}
}
