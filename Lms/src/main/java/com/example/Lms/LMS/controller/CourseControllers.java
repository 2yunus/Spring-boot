package com.example.Lms.LMS.controller;

import com.example.Lms.LMS.controller.CourseRDFUpdater;
import com.example.Lms.LMS.model.Course;
import com.example.Lms.LMS.model.Student;
import com.example.Lms.LMS.service.CourseService;
import com.example.Lms.LMS.service.StudentService;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Controller
public class CourseControllers {



    @GetMapping("/coursess")
    public ModelAndView getStudents(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) String mbox) {
        ModelAndView modelAndView = new ModelAndView("redirect:/");

        // Load RDF data from file
        Model model = ModelFactory.createDefaultModel();
        try {
            FileInputStream rdfFile = new FileInputStream("C:\\Users\\yunus\\Downloads\\Telegram Desktop\\Lms (2)\\Lms (2)\\Lms(semn)\\Lms\\src\\main\\java\\com\\example\\Lms\\LMS\\controller\\course.rdf");
            model.read(rdfFile, null, "RDF/XML");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            modelAndView.addObject("error", "File not found!");
            return modelAndView;
        }

        // Construct the SPARQL query dynamically based on input parameters
        String queryString = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
                "SELECT ?name ?email\n" +
                "WHERE {\n" +
                "    ?student foaf:name ?name ;\n" +
                "             foaf:mbox ?email .\n";

        if (name != null && !name.isEmpty()) {
            queryString += "    FILTER regex(?name, \"" + name + "\", \"i\") .\n";
        }
        if (mbox != null && !mbox.isEmpty()) {
            queryString += "    FILTER regex(?email, \"" + mbox + "\", \"i\") .\n";
        }

        queryString += "}";

        // Execute the query and get the results
        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();

            // Log search results to the console
            ResultSetFormatter.outputAsJSON(System.out, results);
        }

        return modelAndView;
    }
    @Autowired
    private CourseService studentService;

    @PostMapping("/course/updates")
    public String updateStudentsRDF(RedirectAttributes redirectAttributes) {
        List<Course> students = studentService.getAllCourses();
        // Print the fetched students

        String rdfFilePath = "C:\\Users\\yunus\\Downloads\\Telegram Desktop\\Lms (2)\\Lms (2)\\Lms(semn)\\Lms\\src\\main\\java\\com\\example\\Lms\\LMS\\controller\\course.rdf";

        try {
            CourseRDFUpdater.updateRDFModel(students, rdfFilePath);
            redirectAttributes.addFlashAttribute("success", "RDF model updated successfully.");
            return "redirect:/courses/course-form";
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "An error occurred while updating the RDF file: " + e.getMessage());
            return "redirect:/error";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "An unexpected error occurred: " + e.getMessage());
            return "redirect:/error";
        }
    }
}
