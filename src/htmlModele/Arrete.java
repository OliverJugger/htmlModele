package htmlModele;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Arrete {

	OkHttpClient client = new OkHttpClient();

	private Arrete() {

	}

	static void test(final String cheminXML, final String cheminPDF) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
			.addFormDataPart("pdfXmlArrete", cheminXML,
				RequestBody.create(MediaType.parse("application/octet-stream"), new File("/D:/test.xml")))
			.addFormDataPart("racineFragmentsPath", "file:///mnt/NFSLOG/xl2-sir").build();
		Request request = new Request.Builder()
			.url(
				"http://xl2-sir-edit01.sirhen.prj.in.phm.education.gouv.fr:8080/rest/services/SIRHEN_Gestion_Arretes/Processus/validerArrete:1.0")
			.method("POST", body).addHeader("Authorization", "Basic YWRtaW5pc3RyYXRvcjpwYXNzd29yZA==").build();
		Response response = client.newCall(request).execute();
		byte[] pdf = response.body().bytes();

		File targetFile = new File(cheminPDF);
		OutputStream outStream = new FileOutputStream(targetFile);
		outStream.write(pdf);
		outStream.close();

	}

	static void demandeArrete(final String cheminXML, final String cheminPDF) throws Exception {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
			.addFormDataPart("pdfXmlArrete", "/D:/test.xml",
				RequestBody.create(MediaType.parse("application/octet-stream"), new File("/D:/test.xml")))
			.addFormDataPart("racineFragmentsPath", "file:///mnt/NFSLOG/xl2-sir").build();
		Request request = new Request.Builder()
			.url(
				"http://xl2-sir-edit01.sirhen.prj.in.phm.education.gouv.fr:8080/rest/services/SIRHEN_Gestion_Arretes/Processus/validerArrete:1.0")
			.method("POST", body).addHeader("Authorization", "Basic YWRtaW5pc3RyYXRvcjpwYXNzd29yZA==").build();

		try (Response response = client.newCall(request).execute()) {
			byte[] buffer = response.body().bytes();

			File targetFile = new File(cheminPDF);
			OutputStream outStream = new FileOutputStream(targetFile);
			outStream.write(buffer);
			outStream.close();
		}

	}

	/**
	 * static void demandeArrete(final String cheminXml, final String cheminPdf) throws IOException {
	 *
	 * Charset utf8 = StandardCharsets.UTF_8;
	 *
	 * // PREVISUALISATION = /rest/services/SIRHEN_Gestion_Arretes/Processus/previsualiserArrete:1.0 // VALIDATION =
	 * /rest/services/SIRHEN_Gestion_Arretes/Processus/validerArrete:1.0 String service =
	 * "/rest/services/SIRHEN_Gestion_Arretes/Processus/validerArrete:1.0";
	 *
	 * byte[] message = ("administrator:password").getBytes(utf8); String auth =
	 * javax.xml.bind.DatatypeConverter.printBase64Binary(message);
	 *
	 * String xml = new String(Files.readAllBytes(Paths.get(cheminXml)));
	 *
	 * String input = "racineFragmentsPath=" + URLEncoder.encode("file:///sirhen/datas", utf8.toString()); String xmlEncoded =
	 * URLEncoder.encode(xml, utf8.toString()); input += "&pdfXmlArrete=" + xmlEncoded;
	 *
	 * // Get the file reference Path path = Paths.get("d:\\output.txt");
	 *
	 * // Use try-with-resource to get auto-closeable writer instance try (BufferedWriter writer = Files.newBufferedWriter(path))
	 * { writer.write(xmlEncoded); }
	 *
	 * byte[] postData = input.getBytes(utf8);
	 *
	 * URL url = new URL("http://xl2-sir-mastt01.sirhen.prj.in.phm.education.gouv.fr:8080" + service); HttpURLConnection conn =
	 * (HttpURLConnection) url.openConnection(); conn.setDoOutput(true); conn.setRequestMethod("POST");
	 * conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); conn.setRequestProperty("charset",
	 * utf8.toString()); conn.setRequestProperty("Authorization", "Basic " + auth); conn.setRequestProperty("Content-Length",
	 * Integer.toString(postData.length));
	 *
	 * conn.setUseCaches(false);
	 *
	 * try (DataOutputStream os = new DataOutputStream(conn.getOutputStream())) { os.write(postData); }
	 *
	 * if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) { System.out.println(conn.getResponseMessage()); throw new
	 * RuntimeException("Failed : HTTP error code : " + conn.getResponseCode()); }
	 *
	 * InputStream initialStream = conn.getInputStream(); File targetFile = new File(cheminPdf);
	 *
	 * java.nio.file.Files.copy(initialStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
	 *
	 * conn.disconnect();
	 *
	 * }
	 **/

}
