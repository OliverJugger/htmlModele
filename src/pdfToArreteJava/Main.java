package pdfToArreteJava;

import java.io.IOException;

public class Main {

	public static final String cheminPDFMain = "C:\\Users\\omignot\\git\\htmlModele";
	public static final String cheminTXTMain = "C:\\Users\\omignot\\git\\htmlModele";

	public static void main(String[] args) throws IOException {
		
		String cheminPDF = cheminPDFMain + "\\arretePDF.pdf";
		String cheminTXT = cheminTXTMain + "\\arreteTXT.txt";

		String texteArrete = PDFUtils.transformeEnTexte(cheminPDF);
		
		String ligneTitre = splitTexte(texteArrete, "#{DEBUT_ZONE_TITRE}","#{FIN_ZONE_TITRE}");
		String ligneArticles = splitTexte(texteArrete, "#{DEBUT_ZONE_ARTICLES}","#{FIN_ZONE_ARTICLES}");
		String ligneVisaOfficiels = splitTexte(texteArrete, "#{DEBUT_ZONE_VISAS_OFFICIELS}","#{FIN_ZONE_VISAS_OFFICIELS}");
		String ligneVisaComplementaires = splitTexte(texteArrete, "#{DEBUT_ZONE_VISAS_COMPLEMENTAIRES}","#{FIN_ZONE_VISAS_COMPLEMENTAIRES}");
		
		//TODO ne pas faire comme ca <3 RORO
		String articles[] = ligneArticles.split("\r\n");
		for(int i = 0; i < articles.length ; i++) {
			
		}
			
	}
	
	public static String splitTexte( String chaine, String debut, String fin) {
		String decoupee = chaine.split(debut)[1];
		decoupee = decoupee.split(fin)[0];
		return decoupee;
	}

}
