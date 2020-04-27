package htmlModele;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListeurFragment {

	private ListeurFragment() {

	}

	public static List < Fragment > listFragments(final String chemin) {

		List < Fragment > fragments = new ArrayList <>();

		File repertoire = new File(chemin);
		File[] liste = repertoire.listFiles();

		if (liste != null) {
			for (int i = 0; i < liste.length; i++) {
				File fichier = liste[i];

				if (Fragment.calculeIndex(fichier) > 0) {
					fragments.add(new Fragment(fichier));
				} else {
					System.out.println(fichier.getAbsolutePath() + " PAS PRIS EN COMPTE");
				}

			}
		} else {
			System.err.println("Nom de repertoire invalide");
		}

		return fragments;

	}

}
