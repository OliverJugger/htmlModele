package htmlModele;

import java.util.List;

public class Main {

	public static void main(final String[] args) throws Exception {

		System.out.println("Hey Roro !");

		List < Fragment > articles = Fragment.listFragments(
			"D:\\projet-administration-des-documents\\Livraison_Fragments\\formulairesLiveCycle\\Fragment\\Article");

		List < Fragment > visas = Fragment.listFragments(
			"D:\\projet-administration-des-documents\\Livraison_Fragments\\formulairesLiveCycle\\Fragment\\VisaComplementaire");

		String cheminPDF = "D:\\test.pdf";

		Arrete.demandeArrete("D:\\test.xml", cheminPDF);

		String texteArrete = Arrete.transformeEnTexte(cheminPDF);

		// Récupération des textes de tous les visas + tous les arrêtés
		String debutVisas = "le recteur de l'académie d'Aix-Marseille\r\n";
		String entreVisaEtArticle = "ARRETE\r\n";
		String finArticles = "\r\n20 décembre 1962";

		texteArrete = texteArrete.split(debutVisas, 2)[1];
		String texteVisas = texteArrete.split(entreVisaEtArticle, 2)[0];
		String texteArticles = texteArrete.split(entreVisaEtArticle, 2)[1];
		texteArticles = texteArticles.split(finArticles, 2)[0];

		// pour découper les lignes
		String lines[] = texteArticles.split("\r?\n");
		for (int i = 0; i < lines.length; ++i) {
			System.out.println(i + " [ " + lines[i] + " ]");
		}

		// Sauvegarde sur disque
		Arrete.enregistreOctets(texteArrete.getBytes(), "D:\\test.txt");

		System.out.println("texteVisas:" + texteVisas);
		System.out.println("texteArticles:" + texteArticles);

	}

}
