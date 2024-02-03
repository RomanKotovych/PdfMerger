package com.mother.pdfmerger;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.io.MemoryUsageSetting;
import java.io.File;
import java.io.IOException;

public class PdfMerger {
    public void merge(String outputDirectory, String name, String... files) {
        try {
            PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();

            for (String file : files) {
                pdfMergerUtility.addSource(new File(file));
            }

            String outputFilePath = outputDirectory + File.separator + name + ".pdf";

            pdfMergerUtility.setDestinationFileName(outputFilePath);

            try {
                pdfMergerUtility.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
            } catch (IOException e) {
                throw new RuntimeException("Error merging PDFs: " + e.getMessage(), e);
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}