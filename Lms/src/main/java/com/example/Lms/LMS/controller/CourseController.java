
package com.example.Lms.LMS.controller;

import com.example.Lms.LMS.service.CourseService;
import com.example.Lms.LMS.model.Course;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;




import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.ModelFactory;
import jakarta.validation.Valid;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

@Controller
public class CourseController {

    @Autowired
    private CourseService service;
    @GetMapping("/")
    public String home(Model model) {
        return "signup-form";
    }
    @GetMapping("/courses/course-form")
    public String viewAllCourses(Model model) {
        try {
            List<Course> courses = service.getAllCourses();
            model.addAttribute("courseList", courses);
            return "course-form";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/courses/new")
    public String addCourse(Model model) {
        model.addAttribute("course", new Course());
        return "new_course";
    }

    @PostMapping("/courses/save")
    public String saveCourse(@Valid @ModelAttribute("course") Course course, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new_course";
        }
        service.save(course);
        return "redirect:/courses/course-form";
    }

    @GetMapping("/courses/edit/{id}")
    public String showEditCoursePage(@PathVariable(name = "id") Long id, Model model) {
        try {
            Course course = service.getCourseById(id);
            model.addAttribute("course", course);
            return "edit_course";
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/courses/update/{id}")
    public String updateCourse(@PathVariable(name = "id") Long id, @Valid @ModelAttribute("course") Course course, BindingResult result) {
        if (result.hasErrors()) {
            return "edit_course";
        }

        try {
            Course existingCourse = service.getCourseById(id);
            if (existingCourse == null) {
                return "error";
            }

            existingCourse.setName(course.getName());
            existingCourse.setDescription(course.getDescription());
            existingCourse.setCoursecode(course.getCoursecode());

            service.save(existingCourse);

            return "redirect:/courses/course-form";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable(name = "id") Long id) {
        try {
            service.deleteCourseById(id);
            return "redirect:/courses/course-form";
        } catch (Exception e) {
            return "error";
        }
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return "error";
    }



    @GetMapping("/courses")
    public String getCourses(@RequestParam(required = false) String name,
                             @RequestParam(required = false) String code, Model model) {
        // Load RDF data from file
        org.apache.jena.rdf.model.Model rdfModel = ModelFactory.createDefaultModel();
        try {
            // Adjust the file path to your actual location
            FileInputStream rdfFile = new FileInputStream("C:\\Users\\yunus\\Downloads\\Telegram Desktop\\Lms (2)\\Lms (2)\\Lms(semn)\\Lms\\src\\main\\java\\com\\example\\Lms\\LMS\\controller\\course.rdf");
            rdfModel.read(rdfFile, null, "RDF/XML");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            model.addAttribute("error", "File not found!");
            return "error"; // Return an error page
        }

        // Construct the SPARQL query dynamically based on input parameters
        String queryString = "PREFIX ex: <http://example.org/schema#>\n" +
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
                "SELECT ?courseName ?courseCode\n" +
                "WHERE {\n" +
                "    {\n" +
                "        ?course ex:courseName ?courseName ;\n" +
                "               ex:courseCode ?courseCode .\n" +
                "    }\n" +
                "    UNION\n" +
                "    {\n" +
                "        ?course foaf:name ?courseName ;\n" +
                "               foaf:mbox ?courseCode .\n" +
                "    }\n";

        if (name != null && !name.isEmpty()) {
            queryString += "    FILTER regex(?courseName, \"" + name + "\", \"i\") .\n";
        }
        if (code != null && !code.isEmpty()) {
            queryString += "    FILTER regex(?courseCode, \"" + code + "\", \"i\") .\n";
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

        return "course-form";
    }


}
