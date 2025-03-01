package com.example.Lms.LMS.controller;

import com.example.Lms.LMS.model.Student;
import com.example.Lms.LMS.service.StudentService;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students/student-form")
    public String viewAllStudents(Model model) {
        try {
            List<Student> students = studentService.getAllStudents();
            model.addAttribute("studentList", students);
            return "student-form";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/students/new")
    public String addStudent(Model model) {
        model.addAttribute("student", new Student());
        return "new-student";
    }

    @PostMapping("/students/save")
    public String saveStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new-student";
        }
        studentService.save(student);
        return "redirect:/students/student-form";
    }

    @GetMapping("/students/edit/{id}")
    public String showEditStudentPage(@PathVariable(name = "id") Long id, Model model) {
        try {
            Student student = studentService.getStudentById(id);
            model.addAttribute("student", student);
            return "edit-student";
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/students/update/{id}")
    public String updateStudent(@PathVariable(name = "id") Long id, @Valid @ModelAttribute("student") Student student, BindingResult result) {
        if (result.hasErrors()) {
            return "edit-student";
        }

        try {
            Student existingStudent = studentService.getStudentById(id);
            if (existingStudent == null) {
                return "error";
            }

            existingStudent.setFirstname(student.getFirstname());
            existingStudent.setLastname(student.getLastname());
            existingStudent.setEmail(student.getEmail());
            existingStudent.setSex(student.getSex());
            existingStudent.setPassword(student.getPassword());

            studentService.save(existingStudent);

            return "redirect:/students/student-form";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable(name = "id") Long id) {
        try {
            studentService.deleteStudentById(id);
            return "redirect:/students/student-form";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/students")
    public String getStudents(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String email, Model model) {
        org.apache.jena.rdf.model.Model rdfModel = ModelFactory.createDefaultModel();
        try {
            FileInputStream rdfFile = new FileInputStream("C:\\Users\\yunus\\Downloads\\Telegram Desktop\\Lms (2)\\Lms (2)\\Lms(semn)\\Lms\\src\\main\\java\\com\\example\\Lms\\LMS\\controller\\student.rdf");
            rdfModel.read(rdfFile, null, "RDF/XML");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "File not found!");
            return "student-form";
        }

        String queryString = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
                "SELECT ?name ?email\n" +
                "WHERE {\n" +
                "    ?student foaf:name ?name ;\n" +
                "            foaf:mbox ?email .\n";

        if (name != null && !name.isEmpty()) {
            queryString += "    FILTER regex(?name, \"" + name + "\", \"i\") .\n";
        }
        if (email != null && !email.isEmpty()) {
            queryString += "    FILTER regex(?email, \"" + email + "\", \"i\") .\n";
        }

        queryString += "}";

        Query query = QueryFactory.create(queryString);
        List<QuerySolution> resultList = new ArrayList<>();
        try (QueryExecution qexec = QueryExecutionFactory.create(query, rdfModel)) {
            ResultSet results = qexec.execSelect();
            resultList = ResultSetFormatter.toList(results);
        }

        model.addAttribute("results", resultList);

        return "student-form";
    }




    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return "error";
    }
}
