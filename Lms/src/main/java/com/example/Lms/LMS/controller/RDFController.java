package com.example.Lms.LMS.controller;

import com.example.Lms.LMS.model.Student;
import com.example.Lms.LMS.service.RDFService;
import com.example.Lms.LMS.service.StudentService;
import org.apache.jena.rdf.model.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
public class RDFController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private RDFService rdfService;

    @GetMapping("/generateRDF")
    public ResponseEntity<String> generateRDF() {
        List<Student> students = studentService.getAllStudents();
        Model rdfModel = rdfService.convertToRDF(students);
        rdfService.writeRDFToFile(rdfModel, "students.rdf");

        return ResponseEntity.ok("RDF file generated successfully.");
    }
}
