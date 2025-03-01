package com.example.Lms.LMS.controller;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SemanticSearchController {

    @PostMapping("/performSemanticSearch")
    public List<Map<String, String>> performSemanticSearch(@RequestBody Map<String, String> requestBody) {
        String queryStr = requestBody.get("query");
        String filePath = "C:\\Users\\yunus\\Downloads\\Telegram Desktop\\Lms (2)\\Lms (2)\\Lms\\src\\main\\java\\com\\example\\Lms\\LMS\\semantic_index.rdf";

        // Load RDF data into Jena Model
        Model model = loadRDF(filePath);

        // Execute SPARQL query
        ResultSet results = executeQuery(model, queryStr);

        // Process results and return as list of maps
        List<Map<String, String>> resultList = processResults(results);

        return resultList;
    }

    // Load RDF data into Jena Model
    // Load RDF data into Jena Model
    // Load RDF data into Jena Model
    private Model loadRDF(String filePath) {
        Model model = ModelFactory.createDefaultModel();
        try {
            FileInputStream in = new FileInputStream(filePath);
            model.read(in, null);
            model.write(System.out, "TURTLE");
            in.close(); // Close the input stream
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }


    // Execute SPARQL query against RDF data
    private ResultSet executeQuery(Model model, String sparqlQuery) {
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                        "PREFIX schema: <http://schema.org/> " +
                        "SELECT ?id ?firstname ?lastname ?email WHERE { " +
                        "?student rdf:type schema:Person ; " +
                        "         schema:identifier ?id ; " +
                        "         schema:givenName ?firstname ; " +
                        "         schema:familyName ?lastname ; " +
                        "         schema:email ?email . " +
                        "FILTER (CONTAINS(LCASE(STR(?firstname)), LCASE('" + sparqlQuery + "')) || " +
                        "        CONTAINS(LCASE(STR(?lastname)), LCASE('" + sparqlQuery + "')) || " +
                        "        CONTAINS(LCASE(STR(?email)), LCASE('" + sparqlQuery + "'))) " +
                        "}";

        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            return qexec.execSelect();
        }
    }

    // Process results and return as list of maps
    private List<Map<String, String>> processResults(ResultSet results) {
        List<Map<String, String>> resultList = new ArrayList<>();
        while (results.hasNext()) {
            QuerySolution soln = results.nextSolution();
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("id", soln.get("id").toString());
            resultMap.put("firstname", soln.get("firstname").toString());
            resultMap.put("lastname", soln.get("lastname").toString());
            resultMap.put("email", soln.get("email").toString());
            resultList.add(resultMap);
        }
        return resultList;
    }
}
