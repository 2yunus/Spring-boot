package com.example.Lms.LMS.controller;

import com.example.Lms.LMS.service.SPARQLService;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.StringWriter;

@RestController
public class SPARQLController {
    @Autowired
    private SPARQLService sparqlService;

    @GetMapping("/queryRDF")
    public ResponseEntity<String> queryRDF(@RequestParam String query) {
        ResultSet results = sparqlService.executeSPARQLQuery("students.rdf", query);
        StringWriter out = new StringWriter();
        ResultSetFormatter.out(System.out, results);

        return ResponseEntity.ok(out.toString());
    }
}