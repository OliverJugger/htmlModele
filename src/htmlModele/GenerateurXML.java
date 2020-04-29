package htmlModele;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class GenerateurXML {

	private final String fichierXMLModele = "modeleDonnees.xml";

	public List < List < Fragment > > listeDocXML;

	public int cpt = 1;

	public void genererLesXML(final List < Fragment > listeArticles, final String type) throws Exception {
		String dossierParent = "JeuxDeDonnees_" + type;

		listeDocXML = genererListeDocumentsXML(listeArticles);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		File file = new File(dossierParent);
		// Creating the directory
		file.mkdir();

		for (List < Fragment > docXml : listeDocXML) {

			String nomJDD = "JDD_" + cpt;
			cpt++;

			Document document = builder.parse(new File(fichierXMLModele));
			// Normalize the XML Structure; It's just too important !!
			document.getDocumentElement().normalize();

			Element root = document.getDocumentElement();

			// lastname element
			File file2 = new File(dossierParent + "\\" + nomJDD);
			// Creating the directory
			file2.mkdir();

			for (int j = 0; j < 400; j++) {
				if (type == "articles") {

					Element articleModif = document.createElement("ArticleModif");
					Element txtArticle = document.createElement("TxtArticle");

					articleModif.appendChild(document.createTextNode(""));
					articleModif.appendChild(txtArticle);
					articleModif.appendChild(document.createTextNode(""));

					root.getElementsByTagName("ArticlesModifs").item(0).appendChild(articleModif);

				}
				if (type == "visas") {

					Element visaModif = document.createElement("VisaComplModif");
					Element txtVisa = document.createElement("TxtVisaCompl");

					visaModif.appendChild(document.createTextNode(""));
					visaModif.appendChild(txtVisa);
					visaModif.appendChild(document.createTextNode(""));

					root.getElementsByTagName("VisasComplModifs").item(0).appendChild(visaModif);
				}
			}

			for (Fragment fragment : docXml) {

				Node texteDuFragment = null;

				if (type == "articles") {

					Element fragmentArticle = document.createElement("FragmentArticle");
					fragmentArticle.setAttribute("modifiable", "false");
					Node txtFragmentArticle = document.createTextNode(fragment.nom);
					fragmentArticle.appendChild(txtFragmentArticle);
					root.getElementsByTagName("FragmentsArticle").item(0).appendChild(fragmentArticle);

					texteDuFragment = root.getElementsByTagName("ArticleModif").item(fragment.index);
				}

				if (type == "visas") {

					Element fragmentVisa = document.createElement("FragmentVisaComplementaire");
					fragmentVisa.setAttribute("modifiable", "false");
					Node txtFragmentVisa = document.createTextNode(fragment.nom);
					fragmentVisa.appendChild(txtFragmentVisa);
					root.getElementsByTagName("FragmentsVisasComplementaires").item(0).appendChild(fragmentVisa);

					texteDuFragment = root.getElementsByTagName("VisaComplModif").item(fragment.index);
				}

				try {

					texteDuFragment.getChildNodes().item(1)
						.appendChild(document.createTextNode("Ceci est le fragment : " + fragment.nom));

				} catch (NullPointerException npe) {
					throw new Exception("Probl�me fragment : " + fragment.nom + " index : " + fragment.index);
				}

			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(dossierParent + "\\" + nomJDD + "\\" + nomJDD + ".xml"));

			transformer.transform(domSource, streamResult);
		}

		System.out.println("Done creating all XML Files");

	}

	public List < List < Fragment > > genererListeDocumentsXML(final List < Fragment > articles) {

		// Pour chaque xml on aura une liste de fragments, et on veut une liste de xml
		List < List < Fragment > > documentsXml = new ArrayList < List < Fragment > >();

		List < Fragment > fragments = new ArrayList < Fragment >();
		for (Fragment article : articles) {
			if (!fragments.contains(article)) {
				fragments.add(article);
			}
		}

		// On initialise les occurences a Zero
		Map < Integer, Integer > compteurOccurencesIndexes = new HashMap < Integer, Integer >();
		Map < Integer, List < Fragment > > mapIndexListeArticles = new HashMap < Integer, List < Fragment > >();

		Map < Fragment, Integer > compteurDOccurencesDIndexes = new HashMap < Fragment, Integer >();
		for (Fragment fragment : fragments) {
			compteurOccurencesIndexes.put(fragment.index, 0);
			compteurDOccurencesDIndexes.put(fragment, 0);
			List < Fragment > fragmentss = new ArrayList < Fragment >();
			mapIndexListeArticles.put(fragment.index, fragmentss);
		}

		// En parcourant les artciles, on recupere la valeur associ�e a la cl� de l'index de cet article
		// et on l'incremente
		for (Fragment article : articles) {
			Integer nbOccurences = compteurOccurencesIndexes.get(article.index);
			nbOccurences = nbOccurences + 1;
			compteurOccurencesIndexes.put(article.index, nbOccurences);

			List < Fragment > articlesAvecTelIndex = mapIndexListeArticles.get(article.index);
			articlesAvecTelIndex.add(article);
			mapIndexListeArticles.put(article.index, articlesAvecTelIndex);

			compteurDOccurencesDIndexes = setIndex(compteurDOccurencesDIndexes, nbOccurences);
		}

		ArrayList < Integer > occurences = new ArrayList < Integer >(compteurOccurencesIndexes.values());
		int MAX = 0;
		for (Integer i2 : occurences) {
			if (i2 > MAX) {
				MAX = i2;
			}
		}

		// Une liste de liste
		// La premi�re liste sera une liste ou les index apparaissent a des occurences de 1
		// La seconde liste sera une liste ou les index apparaissent a des occurences de 2
		// etc

		List < List < Fragment > > listeDocXML = new ArrayList < List < Fragment > >();

		for (int i = 1; i <= MAX; i++) {
			List < Fragment > docXML = new ArrayList < Fragment >();
			docXML = getValues(mapIndexListeArticles, i);
			listeDocXML.add(docXML);
		}

		return listeDocXML;

	}

	public static Map < Fragment, Integer > setIndex(final Map < Fragment, Integer > map, final Integer index) {
		Map < Fragment, Integer > retour = new HashMap < Fragment, Integer >();
		for (Map.Entry < Fragment, Integer > entry : map.entrySet()) {
			if (index.equals(entry.getKey().index)) {
				entry.setValue(index);
				retour.put(entry.getKey(), entry.getValue());
			}
		}
		return retour;
	}

	public static List < Fragment > getValues(final Map < Integer, List < Fragment > > map, final Integer occurence) {
		List < Fragment > fragmentList = new ArrayList < Fragment >();
		for (Map.Entry < Integer, List < Fragment > > entry : map.entrySet()) {
			if (entry.getValue().size() >= occurence) {
				fragmentList.add(entry.getValue().get(occurence - 1));
			}
		}
		return fragmentList;
	}

	public int getNombreJDD() {
		return cpt;
	}

}
