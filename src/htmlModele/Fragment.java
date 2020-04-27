package htmlModele;

import java.io.File;

public class Fragment {

	public String chemin;
	public String nom;
	public int index;

	public Fragment(final File fichier) {

		chemin = fichier.getAbsolutePath();
		nom = fichier.getName();
		index = calculeIndex(fichier);

	}

	public static int calculeIndex(final File fichier) {

		int index = -1;

		String extension = getExtension(fichier);

		// On ne prend que les XDP
		if ("xdp".equalsIgnoreCase(Fragment.getExtension(fichier))) {

			String filename = fichier.getName().replace("." + extension, "").replace("_v2", "");
			int i = filename.lastIndexOf('_');

			if (i > 0) {

				// Certains indices contiennent un C
				String indice = filename.substring(i + 1).replace("C", "");

				// Les fragments modèles ont "xxx" en indice
				if (!"xxx".equalsIgnoreCase(indice)) {
					index = Integer.parseInt(indice);
				}

			}
		}

		return index;

	}

	private static String getExtension(final File fichier) {
		String fileName = fichier.getAbsolutePath();
		String extension = "";

		int i = fileName.lastIndexOf('.');
		int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

		if (i > p) {
			extension = fileName.substring(i + 1);
		}

		return extension;
	}

}
