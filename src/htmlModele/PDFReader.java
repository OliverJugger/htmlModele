
package htmlModele;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * Classe de lecture d'un PDF, actuellement une surcouche de PDFBox
 *
 * @author acazala
 * @since
 * @version 1.0
 */
public class PDFReader {

	/**
	 * Permet l'extraction du contenu d'un PDF sous forme de String
	 *
	 * @param arretePDF
	 * @return contenu du PDF
	 * @throws IOException
	 */
	public String readStringContentFromFile(final File file) throws IOException {
		// Loading an existing document
		PDDocument document = PDDocument.load(file);

		// Instantiate PDFTextStripper class
		PDFTextStripper pdfStripper = new PDFTextStripper();

		// Retrieving text from PDF document
		String content = pdfStripper.getText(document);

		document.close();

		return content;
	}

}
