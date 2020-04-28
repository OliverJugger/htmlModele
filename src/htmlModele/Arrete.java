package htmlModele;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Arrete {

	private Arrete() {

	}

	static void demandeArrete(final String cheminXml, final String cheminPdf) throws IOException {

		Charset utf8 = StandardCharsets.UTF_8;

		// PREVISUALISATION = /rest/services/SIRHEN_Gestion_Arretes/Processus/previsualiserArrete:1.0
		// VALIDATION = /rest/services/SIRHEN_Gestion_Arretes/Processus/validerArrete:1.0
		String service = "/rest/services/SIRHEN_Gestion_Arretes/Processus/validerArrete:1.0";

		byte[] message = ("administrator:password").getBytes(utf8);
		String encoded = javax.xml.bind.DatatypeConverter.printBase64Binary(message);

		String xml = new String(Files.readAllBytes(Paths.get(cheminXml)));

		URL url = new URL("http://xl2-sir-edit01.sirhen.prj.in.phm.education.gouv.fr:8080" + service);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Authorization", "Basic " + encoded);

		String input = "racineFragmentsPath=" + URLEncoder.encode("file:///mnt/NFSLOG/xl2-sir", utf8.toString());
		// input += "&pdfXmlArrete=" + URLEncoder.encode(xml, utf8.toString());

		conn.addRequestProperty("pdfXmlArrete", URLEncoder.encode(xml, utf8.toString()));
		conn.addRequestProperty("racineFragmentsPath", URLEncoder.encode("file:///mnt/NFSLOG/xl2-sir", utf8.toString()));

		// conn.setRequestProperty("Content-Length", Integer.toString(input.length()));

		try (DataOutputStream os = new DataOutputStream(conn.getOutputStream())) {
			os.write(input.getBytes());
		}

		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			System.out.println(conn.getResponseMessage());
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}

		InputStream initialStream = conn.getInputStream();
		File targetFile = new File(cheminPdf);

		java.nio.file.Files.copy(initialStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

		conn.disconnect();

	}

}
