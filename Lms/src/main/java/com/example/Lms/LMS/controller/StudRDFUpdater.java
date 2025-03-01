package com.example.Lms.LMS.controller;

import com.example.Lms.LMS.model.Student;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class StudRDFUpdater {

    public static void updateRDFModel(List<Student> students, String rdfFilePath) throws IOException {
        // Load existing RDF model from file
        Model model = ModelFactory.createDefaultModel();

        try (FileInputStream in = new FileInputStream(rdfFilePath)) {
            model.read(in, null, "RDF/XML");
        } catch (IOException e) {
            System.err.println("Error reading RDF file: " + e.getMessage());
            throw new IOException("RDF file could not be read or is empty.", e);
        }

        // Define namespaces
        String exNS = "http://example.org/schema#";
        String foafNS = "http://xmlns.com/foaf/0.1/";
        model.setNsPrefix("ex", exNS);
        model.setNsPrefix("foaf", foafNS);

        Property nameProp = model.createProperty(foafNS, "name");
        Property emailProp = model.createProperty(foafNS, "mbox");

        // Remove existing student triples (if needed)
        model.removeAll(null, nameProp, null);
        model.removeAll(null, emailProp, null);

        // Add new RDF triples from the student list
        for (Student student : students) {
            Resource studentResource = model.createResource(exNS + "student/" + student.getId())
                    .addProperty(nameProp, student.getFirstname())
                    .addProperty(emailProp, "mailto:" + student.getEmail());
        }

        // Write the updated model back to the file
        try (FileOutputStream out = new FileOutputStream(rdfFilePath)) {
            model.write(out, "RDF/XML-ABBREV");
        } catch (IOException e) {
            System.err.println("Error writing RDF file: " + e.getMessage());
            throw new IOException("RDF file could not be written.", e);
        }
    }

    }

