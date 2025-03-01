package com.example.Lms.LMS.controller;

import com.example.Lms.LMS.model.Course;
import com.example.Lms.LMS.model.Instructor;
import com.example.Lms.LMS.service.CourseService;
import com.example.Lms.LMS.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.ModelFactory;
import jakarta.validation.Valid;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;


import jakarta.validation.Valid;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class InstructorController {

    @Autowired
    private InstructorService instructorService;
    @Autowired
    private CourseService service;



    @GetMapping("/instructors/instructor-form")
    public String getAllInstructors(Model model) {
        List<Instructor> instructorsList = instructorService.getAllInstructors();
        model.addAttribute("instructorsList", instructorsList);
        return "instructor-form";
    }

    @GetMapping("/instructors/new")
    public String addInstructor(Model model) {
        List<Course> courseList = service.getAllCourses();
        model.addAttribute("courseList", courseList);
        model.addAttribute("instructor", new Instructor());
        return "new_instructors";
    }

    @PostMapping("/instructors/save")
    public String saveInstructor(@Valid @ModelAttribute Instructor instructor, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new_instructors";
        }



        // Save Instructor object
        instructorService.save(instructor);

        return "redirect:/instructors/instructor-form";
    }

    @GetMapping("/edit/{id}")
    public String editInstructor(@PathVariable Long id, Model model) {
        Instructor instructor = instructorService.getInstructorById(id);
        if (instructor != null) {
            List<Course> courseList = service.getAllCourses();
            model.addAttribute("courseList", courseList);
            model.addAttribute("instructor", instructor);
            return "edit_instructors";
        } else {
            return "error"; // Handle the case where instructor is not found
        }
    }

    @PostMapping("/instructors/update/{id}")
    public String updateInstructor(@PathVariable Long id, @Valid @ModelAttribute Instructor instructor, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit_instructors";
        }
        instructor.setId(id);
        instructorService.getInstructorById(id);
        return "redirect:/instructors/instructor-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteInstructor(@PathVariable Long id) {
        instructorService.deleteInstructorById(id);
        return "redirect:/instructors/instructor-form";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        // Log the exception or handle it as needed
        return "error"; // Assuming you have an "error.html" template for error handling
    }
    @GetMapping("/instructors")
    public String getInstructors(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String courseName, Model model) {
        // Load RDF data from file
        org.apache.jena.rdf.model.Model rdfModel = ModelFactory.createDefaultModel();
        try {
            // Adjust the file path to your actual location
            FileInputStream rdfFile = new FileInputStream("C:\\Users\\yunus\\Downloads\\Telegram Desktop\\Lms (2)\\Lms (2)\\Lms(semn)\\Lms\\src\\main\\java\\com\\example\\Lms\\LMS\\controller\\instructor.rdf");
            rdfModel.read(rdfFile, null, "RDF/XML");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            model.addAttribute("error", "File not found!");
            return "error"; // Return an error page
        }

        // Construct the SPARQL query dynamically based on input parameters
        String queryString = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
                "PREFIX ex: <http://example.org/schema#>\n" +
                "SELECT ?instructorName ?courseName\n" +
                "WHERE {\n" +
                "    ?instructor foaf:name ?instructorName ;\n" +
                "                ex:coursesTeaching ?course .\n" +
                "    ?course ex:courseName ?courseName .\n";

        if (name != null && !name.isEmpty()) {
            queryString += "    FILTER regex(?instructorName, \"" + name + "\", \"i\") .\n";
        }
        if (courseName != null && !courseName.isEmpty()) {
            queryString += "    FILTER regex(?courseName, \"" + courseName + "\", \"i\") .\n";
        }

        queryString += "}";

        // Execute the query and get the results
        Query query = QueryFactory.create(queryString);
        List<QuerySolution> resultList = new ArrayList<>();
        try (QueryExecution qexec = QueryExecutionFactory.create(query, rdfModel)) {
            ResultSet results = qexec.execSelect();
            resultList = ResultSetFormatter.toList(results);
        }

        // Add search results to the model
        model.addAttribute("results", resultList);

        return "instructor-form";
    }



}
