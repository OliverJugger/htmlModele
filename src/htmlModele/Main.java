package htmlModele;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

	public static void main(final String[] args) throws Exception {

		System.out.println("Hey Roro !");

		List < Fragment > articles = Fragment.listFragments(
			"D:\\projet-administration-des-documents\\Livraison_Fragments\\formulairesLiveCycle\\Fragment\\Article");

		articles = articles.stream().sorted((objet1, objet2) -> objet1.index - objet2.index).collect(Collectors.toList());

		String frg = "";
		for (Fragment frgm : articles) {
			frg += frgm.nom + "\r\n";
		}

		Arrete.enregistreOctets(frg.getBytes(), "D:\\articles.txt");

		List < Fragment > visas = Fragment.listFragments(
			"D:\\projet-administration-des-documents\\Livraison_Fragments\\formulairesLiveCycle\\Fragment\\VisaComplementaire");

		visas = visas.stream().sorted((objet1, objet2) -> objet1.index - objet2.index).collect(Collectors.toList());

		frg = "";
		for (Fragment frgm : visas) {
			frg += frgm.nom + "\r\n";
		}

		Arrete.enregistreOctets(frg.getBytes(), "D:\\visas.txt");

		// litDonnees("D:\\test.xml", "D:\\test.pdf", "D:\\test.txt");

		System.out.println("Demande génération PDF pour les articles !");

		// generation des JDD en XML dans le dossier "JeuxDeDonnees"
		GenerateurXML gen = new GenerateurXML();
		gen.genererLesXML(articles, "articles");

		int nombreDeJDD = gen.getNombreJDD();

		for (int i = 1; i < nombreDeJDD; i++) {

			System.out.println("Analyse PDF " + i + " commencé !");
			List < Fragment > listeFragments = gen.listeDocXML.get(i - 1);
			String chemin = "D:\\git\\htmlModele\\JeuxDeDonnees_articles\\JDD_" + i + "\\";
			litDonnees(chemin, listeFragments, i);
			System.out.println("Analyse PDF " + i + " finito !");

		}

		System.out.println("Articles finis !");
		System.out.println("Demande génération PDF pour les visas !");

		// generation des JDD en XML dans le dossier "JeuxDeDonnees"
		GenerateurXML genVisas = new GenerateurXML();
		genVisas.genererLesXML(visas, "visas");

		int nombreDeJDDvisas = genVisas.getNombreJDD();

		for (int i = 1; i < nombreDeJDDvisas; i++) {

			System.out.println("Analyse PDF " + i + " commencé !");
			List < Fragment > listeFragments = genVisas.listeDocXML.get(i - 1);
			String chemin = "D:\\git\\htmlModele\\JeuxDeDonnees_visas\\JDD_" + i + "\\";
			litDonnees(chemin, listeFragments, i);
			System.out.println("Analyse PDF " + i + " finito !");

		}

		System.out.println("Visas finis !");
		System.out.println("Finito !");

	}

	public static void litDonnees(final String chemin, final List < Fragment > listeFragments, final int i) throws IOException {

		String cheminPDF = chemin + "test" + i + ".pdf";
		String cheminXML = chemin + "JDD_" + i + ".xml";
		String cheminTXT = chemin + "JDD_" + i + ".txt";
		String cheminArticles = chemin + "JDD_" + i + "Articles.txt";
		String cheminVisas = chemin + "JDD_" + i + "Visas.txt";
		String cheminErreurs = chemin + "JDD_" + i + "ErreursArticles.txt";
		String cheminErreursvisas = chemin + "JDD_" + i + "ErreursVisas.txt";
		String cheminFragments = chemin + "JDD_" + i + "Fragments.txt";

		String frg = "";
		for (Fragment frgm : listeFragments) {
			frg += frgm.nom + "\r\n";
		}

		Arrete.enregistreOctets(frg.getBytes(), cheminFragments);

		Arrete.demandeArrete(cheminXML, cheminPDF);

		String texteArrete = Arrete.transformeEnTexte(cheminPDF);

		// Sauvegarde sur disque
		Arrete.enregistreOctets(texteArrete.getBytes(), cheminTXT);

		// Récupération des textes de tous les visas + tous les arrêtés
		String debutVisas = "LE RECTEUR DE L'ACADEMIE DE TOULOUSE\r\n";
		String entreVisaEtArticle = "ARRETE\r\n";
		// if (chemin.contains("JeuxDeDonnees_articles")) {
		// entreVisaEtArticle = "ARRETE\r\n \r\n";
		// }
		String finArticles = "\r\n18 mars 2020";

		if (!texteArrete.isEmpty() && texteArrete.indexOf(debutVisas) >= 0) {
			texteArrete = texteArrete.split(debutVisas, 2)[1];

			if (texteArrete.indexOf(entreVisaEtArticle) >= 0) {
				String texteVisas = texteArrete.split(entreVisaEtArticle, 2)[0];

				String errorsvisas = "";

				// pour analyser les visas
				if (!texteVisas.isEmpty()) {
					String linesvisas[] = texteVisas.split("\r\n");
					for (int j = 0; j < Math.min(linesvisas.length, listeFragments.size()); ++j) {
						if (!("Ceci est le fragment : " + listeFragments.get(j).nom).equals(linesvisas[j])) {
							errorsvisas += linesvisas[j] + " ≠ " + "Ceci est le fragment : " + listeFragments.get(j).nom + "\r\n";
						}
					}

					Arrete.enregistreOctets(texteVisas.getBytes(), cheminVisas);
					Arrete.enregistreOctets(errorsvisas.getBytes(), cheminErreursvisas);
				}

				String texteArticles = texteArrete.split(entreVisaEtArticle, 2)[1];
				if (texteArticles.indexOf(finArticles) >= 0) {

					texteArticles = texteArticles.split(finArticles, 2)[0];

					String errors = "";

					// pour analyser les articles
					if (!texteArticles.isEmpty()) {
						String lines[] = texteArticles.split("\r\n");
						for (int j = 0; j < Math.min(lines.length, listeFragments.size()); ++j) {
							if (!("Ceci est le fragment : " + listeFragments.get(j).nom).equals(lines[j])) {
								errors += lines[j] + " ≠ " + "Ceci est le fragment : " + listeFragments.get(j).nom + "\r\n";
							}
						}

						Arrete.enregistreOctets(texteArticles.getBytes(), cheminArticles);
						Arrete.enregistreOctets(errors.getBytes(), cheminErreurs);
					}

				}

			}

		}

	}

}
