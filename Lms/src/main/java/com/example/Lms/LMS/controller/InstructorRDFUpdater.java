package com.example.Lms.LMS.controller;

import com.example.Lms.LMS.model.Instructor;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDF;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class InstructorRDFUpdater {

    public static void updateRDFModel(List<Instructor> instructors, String rdfFilePath) throws IOException {
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
        Property coursesTeachingProp = model.createProperty(exNS, "coursesTeaching");
        Property courseNameProp = model.createProperty(exNS, "courseName");

        // Remove existing instructor triples (if needed)
        model.removeAll(null, RDF.type, model.createResource(exNS + "Instructor"));
        model.removeAll(null, coursesTeachingProp, null);
        model.removeAll(null, nameProp, null);

        // Add new RDF triples from the instructor list
        for (Instructor instructor : instructors) {
            // Create instructor resource
            Resource instructorResource = model.createResource(exNS + "instructor/" + instructor.getName().replace(" ", ""))
                    .addProperty(RDF.type, model.createResource(exNS + "Instructor"))
                    .addProperty(nameProp, instructor.getName());

            // Split the coursesTeaching string by comma to get individual courses
            String[] courses = instructor.getCourseTeaching().split(",");

            // Add courses taught by the instructor
            for (String courseName : courses) {
                String trimmedCourseName = courseName.trim().replace(" ", "");

                Resource courseResource = model.createResource(exNS + "course/" + trimmedCourseName)
                        .addProperty(courseNameProp, courseName.trim());

                // Ensure the course resource is defined in the model
                if (!model.containsResource(courseResource)) {
                    model.add((Statement) courseResource);
                }

                // Link the instructor to the course
                instructorResource.addProperty(coursesTeachingProp, courseResource);
            }
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
