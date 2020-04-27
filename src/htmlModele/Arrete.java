package htmlModele;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.StandardCopyOption;

public class Arrete {

	private Arrete() {

	}

	static void valideArrete(final String cheminXml, final String cheminPdf) {

		String service = "rest/sync_invoke/ArreteIndividuelSIERH/ArreteIndividuelSIERH:3.0"; // Ajouter le service ici

		try {

			URL url = new URL("http://xl2-sir-edit01.sirhen.prj.in.phm.education.gouv.fr:8080/" + service);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "" + cheminXml;

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			InputStream initialStream = conn.getInputStream();
			File targetFile = new File(cheminPdf);

			java.nio.file.Files.copy(initialStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}
