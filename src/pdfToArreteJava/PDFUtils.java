package pdfToArreteJava;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import htmlModele.PDFReader;

public class PDFUtils {

	public static void enregistreOctets(final byte[] octets, final String chemin) throws IOException {
		File targetFile = new File(chemin);
		OutputStream outStream = new FileOutputStream(targetFile);
		outStream.write(octets);
		outStream.close();
	}

	public static String transformeEnTexte(final String cheminPDF) throws IOException {

		File file = new File(cheminPDF);
		PDFReader pdfReader = new PDFReader();

		String pdf = pdfReader.readStringContentFromFile(file);

		pdf = pdf.replace("\r\n \r\n", "\r\n");
		pdf = pdf.replace("1 / 3\r\n", "");
		pdf = pdf.replace("2 / 3\r\n", "");
		pdf = pdf.replace("3 / 3\r\n", "");
		pdf = pdf.replace("1 / 1\r\n", "");

		return pdf;

	}

}
