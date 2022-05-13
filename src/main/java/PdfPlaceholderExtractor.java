import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class PdfPlaceholderExtractor {
    public static void main(String[] args) throws IOException {

        //path file temporanei splittati dal file sorgente
        String pdfPath = "<path temp>";

        //nome file sorgente
        String fileName = "<file name>";

        int numberOfFiles = new File(pdfPath).list().length;

        for (int i = 1; i <= numberOfFiles; i++) {
            String fileIter = fileName.replace(".pdf", "-" + i + ".pdf");
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(pdfPath + fileIter));

            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
            Map fields = form.getFormFields();

            Set<String> names = fields.keySet();

            for(String s : names) {
                System.out.println(fileName + ";" + s + ";" + i);
            }
        }

    }
}
