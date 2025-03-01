package com.example.Lms.LMS.service;

import com.example.Lms.LMS.model.Student;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.VCARD;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class RDFService {

    public Model convertToRDF(List<Student> students) {
        Model model = ModelFactory.createDefaultModel();
        String studentURI = "http://example.org/student/";

        for (Student student : students) {
            Resource studentResource = model.createResource(studentURI + student.getId())
                    .addProperty(VCARD.FN, student.getFirstname() + " " + student.getLastname())
                    .addProperty(VCARD.EMAIL, student.getEmail());
        }

        return model;
    }

    public void writeRDFToFile(Model model, String filePath) {
        try (FileOutputStream out = new FileOutputStream("student.rdf")) {
            model.write(out, "RDF/XML");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
