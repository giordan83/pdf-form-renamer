import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;

import java.io.IOException;
import java.util.*;

public class PdfFormRenamer {
    public static void main(String[] args) throws IOException {
        PdfReader reader = new PdfReader("<path input file>");
        PdfWriter writer = new PdfWriter("<path output file>");
        PdfDocument pdfDocument = new PdfDocument(reader, writer);
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDocument, true);
        Map<String, PdfFormField> fields = form.getFormFields();
        LinkedHashMap<String, PdfFormField> fields2 = new LinkedHashMap<>(fields);
        for (Object field :fields2.keySet()) {
            if (fields2.get(field.toString()).getParent() == null){
                form.renameField(field.toString(), "<prefix string>" + field);
            }
        }
        pdfDocument.close();
    }
}
