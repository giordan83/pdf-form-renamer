import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class PdfPlaceholderExtractor {
    public static void main(String[] args) throws IOException {

        //path file sorgente
        String pdfPath = "<path input file>";

        PdfDocument pdfDoc = new PdfDocument(new PdfReader(pdfPath));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        Map fields = form.getFormFields();

        Set<String> names = fields.keySet();

        for(String s : names) {
            System.out.println(s);
        }
    }
}
