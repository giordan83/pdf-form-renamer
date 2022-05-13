import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.PdfPageFormCopier;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;

import java.io.*;
import java.util.*;

public class PdfFormRenamer {
    public static void main(String[] args) throws IOException {
        Scanner scReader = new Scanner(System.in);
        System.out.println("Insert input file:");
        String inputFile = scReader.next();
        System.out.println("Insert output file:");
        String outputFile = scReader.next();
        System.out.println("Insert prefix to use:");
        String prefix = scReader.next();
        System.out.println("Page to limit the changes :");
        Integer pageNumber = scReader.nextInt();

        System.out.println("input file: " + inputFile);
        System.out.println("output file: " + outputFile);
        System.out.println("prefix: " + prefix);

        PdfDocument outputDoc = new PdfDocument(new PdfWriter(outputFile));

        PdfDocument inputDoc = new PdfDocument(new PdfReader(inputFile));


        PdfAcroForm form = PdfAcroForm.getAcroForm(inputDoc, true);
        Map<String, PdfFormField> fields = form.getFormFields();
        LinkedHashMap<String, PdfFormField> fields2 = new LinkedHashMap<>(fields);
        for (Object field : fields2.keySet()) {
            if (fields2.get(field.toString()).getParent() == null) {
                form.renameField(field.toString(), prefix + field);
            }
        }
        File f = File.createTempFile("tmp","file");
        PdfDocument tmpDoc = new PdfDocument(new PdfWriter(f));
        inputDoc.copyPagesTo(pageNumber, pageNumber, tmpDoc,new PdfPageFormCopier());

        PdfAcroForm tmpForm = PdfAcroForm.getAcroForm(tmpDoc, true);
        Map<String, PdfFormField> tmpFields = tmpForm.getFormFields();
        LinkedHashMap<String, PdfFormField> tmpFields2 = new LinkedHashMap<>(tmpFields);
        for (Object tmpField : tmpFields2.keySet()) {
            if (tmpFields2.get(tmpField.toString()).getParent() == null) {
                tmpForm.renameField(tmpField.toString(), "x-" + tmpField);
            }
        }
        tmpDoc.close();
        inputDoc.copyPagesTo(1, (pageNumber-1),outputDoc);
        new PdfDocument(new PdfReader(f)).copyPagesTo(1,1, outputDoc);

        inputDoc.copyPagesTo((pageNumber+1), inputDoc.getNumberOfPages(), outputDoc);

        inputDoc.close();

        outputDoc.close();

        if(!f.delete()) {
            System.out.println("cancella il file");
        }
    }
}
