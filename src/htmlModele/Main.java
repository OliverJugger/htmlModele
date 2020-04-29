package htmlModele;

import java.util.List;

public class Main {

	public static void main(final String[] args) throws Exception {

		System.out.println("Hey Roro !");

		List < Fragment > articles = Fragment.listFragments(
			"D:\\projet-administration-des-documents\\Livraison_Fragments\\formulairesLiveCycle\\Fragment\\Article");

		List < Fragment > visas = Fragment.listFragments(
			"D:\\projet-administration-des-documents\\Livraison_Fragments\\formulairesLiveCycle\\Fragment\\VisaComplementaire");

		//generation des JDD en XML dans le dossier "JeuxDeDonnees"
		GenerateurXML gen = new GenerateurXML();
		gen.genererLesXML(articles);
		
		int nombreDeJDD = gen.getNombreJDD();
		
		for(int i=1 ; i<nombreDeJDD ; i++) {
		
		String cheminPDF = "D:\\test" + i + ".pdf";

		Arrete.demandeArrete("JeuxDeDonnees\\"+"JDD_" + i +"\\JDD_" + i + ".xml", cheminPDF);

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
		for (int j = 0; j < lines.length; ++j) {
			System.out.println(j + " [ " + lines[j] + " ]");
		}

		// Sauvegarde sur disque
		Arrete.enregistreOctets(texteArrete.getBytes(), "D:\\test" + i +".txt");

		System.out.println("texteVisas:" + texteVisas);
		System.out.println("texteArticles:" + texteArticles);
		
		}

	}

}
