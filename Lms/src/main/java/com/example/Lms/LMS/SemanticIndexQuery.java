package com.example.Lms.LMS;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.FileInputStream;
import java.io.IOException;

public class SemanticIndexQuery {

    public ResultSet querySemanticIndex(String sparqlQuery, String filePath) {
        Model model = ModelFactory.createDefaultModel(); // Initialize the model
        try (FileInputStream in = new FileInputStream(filePath)) {
            model.read(in, null);
        } catch (IOException e) {
            System.err.println("Error reading RDF file: " + e.getMessage());
            e.printStackTrace();
            return null; // Return null to indicate failure
        }

        Query query = QueryFactory.create(sparqlQuery);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            return qexec.execSelect();
        } catch (Exception e) {
            System.err.println("Error executing SPARQL query: " + e.getMessage());
            e.printStackTrace();
            return null; // Return null to indicate failure
        }
    }

    public static void main(String[] args) {
        SemanticIndexQuery query = new SemanticIndexQuery();
        String sparqlQuery = "SELECT ?id ?name ?description WHERE {?s rdf:type schema:Course ; " +
                "schema:name ?name ; " +
                "schema:description ?description ; " +
                "schema:provider ?provider ; " +
                "schema:inLanguage 'English' .}";

        ResultSet results = query.querySemanticIndex(sparqlQuery, "C:\\Users\\yunus\\Downloads\\Telegram Desktop\\Lms (2)\\Lms (2)\\Lms\\src\\main\\java\\com\\example\\Lms\\LMS\\semantic_index.rdf");
        if (results != null) {
            ResultSetFormatter.out(System.out, results);
        } else {
            System.out.println("Failed to execute the SPARQL query.");
        }
    }
}
