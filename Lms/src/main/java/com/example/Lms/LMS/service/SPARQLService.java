package com.example.Lms.LMS.service;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;


@Service
public class SPARQLService {
    public ResultSet executeSPARQLQuery(String filePath, String sparqlQuery) {
        Model model = ModelFactory.createDefaultModel();
        try (FileInputStream in = new FileInputStream(filePath)) {
            model.read(in, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Query query = QueryFactory.create(sparqlQuery);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            return qexec.execSelect();
        }
    }
}


