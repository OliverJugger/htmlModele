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

	private Arrete() {

	}

	static void demandeArrete(final String cheminXML, final String cheminPDF) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();

		MediaType mediaType = MediaType.parse("text/plain");

		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
			.addFormDataPart("pdfXmlArrete", cheminXML,
				RequestBody.create(MediaType.parse("application/octet-stream"), new File(cheminXML)))
			.addFormDataPart("racineFragmentsPath", "file:///mnt/NFSLOG/xl2-sir").build();

		Request request = new Request.Builder()
			.url(
				"http://xl2-sir-edit01.sirhen.prj.in.phm.education.gouv.fr:8080/rest/services/SIRHEN_Gestion_Arretes/Processus/validerArrete:1.0")
			.method("POST", body).addHeader("Authorization", "Basic YWRtaW5pc3RyYXRvcjpwYXNzd29yZA==").build();
		Response response = client.newCall(request).execute();

		byte[] pdf = response.body().bytes();
		enregistreOctets(pdf, cheminPDF);

	}

	static void enregistreOctets(final byte[] octets, final String chemin) throws IOException {
		File targetFile = new File(chemin);
		OutputStream outStream = new FileOutputStream(targetFile);
		outStream.write(octets);
		outStream.close();
	}

	static String transformeEnTexte(final String cheminPDF) throws IOException {

		File file = new File(cheminPDF);
		PDFReader pdfReader = new PDFReader();

		String pdf = pdfReader.readStringContentFromFile(file);

		return pdf;

	}

}
