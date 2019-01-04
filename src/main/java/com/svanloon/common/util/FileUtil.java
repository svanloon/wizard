package com.svanloon.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class FileUtil {
	private static Logger _logger = Logger.getLogger(FileUtil.class);
	/**
	 * 
	 * Document the writeFile method 
	 *
	 * @param fileName
	 * @param fileContents
	 * @throws IOException
	 */
	public static void writeFile(String fileName, String fileContents)
			throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(fileName);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
				fileOutputStream);
		PrintWriter printWriter = new PrintWriter(bufferedOutputStream);
		printWriter.write(fileContents);
		printWriter.flush();
		printWriter.close();
	}

	// Block size to read files.
	private static final int READ_LEN = 1024;
	/**
	 * 
	 * Document the copy method 
	 *
	 * @param fromFile
	 * @param toFile
	 * @throws IOException
	 */
	public static void copy(String fromFile, String toFile) throws IOException {
		BufferedInputStream fIn = null;
		BufferedOutputStream fOut = null;

		try {
			fOut = new BufferedOutputStream(new FileOutputStream(toFile));
		} catch (Exception e) {
			_logger.error( "Could not create output file. File="
					+ toFile);
			throw new IOException("Could not create output file: " + toFile);
		}

		try {
			fIn = new BufferedInputStream(new FileInputStream(fromFile));
		} catch (Exception e) {
			_logger.error( "Could not open source file: "
					+ fromFile + ", " + e.getMessage());
			throw new IOException("Could not open input file: " + fromFile);
		}

		try {
			_logger.info("starting copy of " + fromFile + " to "
					+ toFile);
			for (int i;;) {
				i = fIn.read();
				if (i == -1) {
					break;
				}
				fOut.write(i);
			}
			_logger.info("copy done");
		} catch (IOException io) {
			_logger.error( "IO Error copying file " + fromFile
					+ " to " + toFile);
			throw new IOException("IO Error copying file " + fromFile + " to "
					+ toFile);
		}

		try {
			fIn.close();
			fIn = null;
			fOut.close();
			fOut = null;
		} catch (IOException io) {
			String message;
			if (fIn == null) {
				message = "IO Error closing file " + toFile;
			} else {
				message = "IO Error closing file " + fromFile;
			}

			_logger.error( message);
			throw new IOException(message);
		}
	}

	/**
	 * 
	 * Document the moveFile method 
	 *
	 * @param fromFilePath
	 * @param toFilePath
	 * @throws IOException
	 */
	public static void moveFile(String fromFilePath, String toFilePath)
			throws IOException {
		File file = new File(fromFilePath);
		File newFile = new File(toFilePath);
		file.renameTo(newFile);
	}
	/**
	 * 
	 * Document the renameFile method 
	 *
	 * @param directory
	 * @param originalFileName
	 * @param newFileName
	 * @throws IOException
	 */
	public static void renameFile(String directory, String originalFileName,
			String newFileName) throws IOException {
		if (!directory.endsWith("/")) {
			directory += "/";
		}

		File file = new File(directory + originalFileName);
		File newFile = new File(directory + newFileName);
		file.renameTo(newFile);
	}
	/**
	 * 
	 * Document the deleteFile method 
	 *
	 * @param directory
	 * @param fileName
	 * @return boolean
	 */
	public static boolean deleteFile(String directory, String fileName) {
		if (!directory.endsWith("/")) {
			directory += "/";
		}

		File file = new File(directory + fileName);
		return file.delete();
	}

	/**
	 * 
	 * Read a file and return it as a String.
	 * @param absolutePath 
	 * @return String
	 * 
	 */
	public static String readFile(String absolutePath) {
		FileReader fileReader = null;

		try {
			fileReader = new FileReader(absolutePath);
		} catch (FileNotFoundException fileNotFound) {
			_logger.error( "File not found: " + absolutePath);

			return null;
		}

		StringBuffer str = new StringBuffer();

		try {
			char[] buffer = new char[READ_LEN];
			int bytesRead;

			while ((bytesRead = fileReader.read(buffer)) > 0) {
				str.append(buffer, 0, bytesRead);
			}
		} catch (IOException ioException) {
			_logger.error( "IOException "
					+ ioException.getMessage());
		}

		return str.toString();
	}

	/**
	 * Create a SAX InputSource from file.
	 * @param f 
	 * @return InputSource
	 */
	static public InputSource fileInputSource(String f) {
		File file = new File(f);
		String path = file.getAbsolutePath();
		String fSep = System.getProperty("file.separator");

		if (fSep != null && fSep.length() == 1) {
			path = path.replace(fSep.charAt(0), '/');
		}

		if (path.length() > 0 && path.charAt(0) != '/') {
			path = '/' + path;
		}
		try {
			return new InputSource(new URL("file", "", path).toString());
		} catch (java.net.MalformedURLException e) {
			/*
			 * According to the spec this could only happen if the file protocol
			 * were not recognized.
			 */
			throw new Error("unexpected MalformedURLException");
		}
	}
}