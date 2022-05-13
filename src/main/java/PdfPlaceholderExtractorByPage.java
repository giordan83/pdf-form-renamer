import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.utils.PdfMerger;

import java.io.IOException;
import java.util.List;

public class PdfPlaceholderExtractorByPage {

    public static void main(String[] args) throws IOException {

        //path file sorgente
        String pdfPath = "<path input file>";

        //nome file sorgente
        String fileName = "<file name>";

        //path file temporaneo per i file splittati (pagine)
        String tempPath = "<path temp file>";

        //split file sorgente in file multipli, per pagina
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(pdfPath + fileName));
        int n = pdfDoc.getNumberOfPages();
        splitPdfPages(pdfPath, tempPath, fileName, n);


        //per ogni pagina stampo in maniera ordinata i placeholder
        //output finale in formato: <pdf_sorgente>;<placeholder>;<numero_pagina>
        for (int i = 1; i <= n; i++) {

            PdfDocument pdfDocIter = new PdfDocument(new PdfReader(tempPath + "page_" + i + ".pdf"));

            //per ogni pagina recupero le annotation (numero pagina sempre 1)
            List<PdfAnnotation> lista = pdfDocIter.getPage(1).getAnnotations();

            for(PdfAnnotation a : lista) {
                System.out.println(fileName + ";" + a.getTitle() + ";" + i);
            }

        }

    }

    private static void splitPdfPages(String pdfPath, String tempPath, String fileName, int count) throws IOException {

        for (int i = 1; i <= count; i++) {

            PdfDocument split = new PdfDocument(new PdfWriter(tempPath + "page_" + i + ".pdf"));
            PdfMerger merger = new PdfMerger(split);
            PdfDocument sourcePdf = new PdfDocument(new PdfReader(pdfPath + fileName));
            // merge
            merger.merge(sourcePdf, i,i).setCloseSourceDocuments(true);
            // close
            sourcePdf.close();
            merger.close();
            split.close();
        }

    }
}
