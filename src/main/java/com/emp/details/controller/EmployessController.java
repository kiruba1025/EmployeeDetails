package com.emp.details.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.emp.details.entity.Employees;
import com.emp.details.service.EmployeeService;
import com.emp.details.util.FlyingSaucerPDFUtil;
import com.lowagie.text.DocumentException;


import lombok.NonNull;



@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/employees")
public class EmployessController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private FlyingSaucerPDFUtil pdfUtil;
    @GetMapping("/")
    public String getAllEmployees(Model model) {
        List<Employees> employees = employeeService.getAllEmployees();
        model.addAttribute("listEmployees", employees);
        return "emp"; 
    }
    
    @GetMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model) {
       
    	Employees employee = new Employees();
        model.addAttribute("employee", employee);
        return "new_employee";
    }
  
    @PostMapping("/create")
    public String addEmployee(@ModelAttribute Employees employee) {
    	System.out.println("--------------------"+employee.getDob());
    	employee.setActive(true);
        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdatedAt(LocalDateTime.now());
        employeeService.addEmployee(employee);
        return "redirect:/employees/"; 
    }

    @GetMapping("/update/{id}")
    public String updateEmployeeForm(@PathVariable Long id, Model model) throws NotFoundException {
        Employees employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "update_employee"; 
    }

    @PutMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute Employees employee) {
        try {
            employeeService.updateEmployee(id, employee);
            return "redirect:/employees/";
        } catch (NotFoundException e) {
            return "errorPage";
        }
    }

    @PutMapping("/{id}/status")
    public Employees updateEmployeeStatus(@PathVariable Long id, @RequestParam boolean active) throws NotFoundException {
        return employeeService.updateEmployeeStatus(id, active);
    }
    @GetMapping("/emp/pdf")
    public ResponseEntity<InputStreamResource> downloadPdf() {
        try {
            List<Employees> employees = employeeService.getAllEmployees();

            if (employees == null || employees.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            Map<String, Object> data = new HashMap<>();
            data.put("employees", employees);

            byte[] pdfBytes = pdfUtil.generatePdf("pdf-employee", data);


            InputStream pdfStream = new ByteArrayInputStream(pdfBytes);
            InputStreamResource resource = new InputStreamResource(pdfStream);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees.pdf")
                    .body(resource);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
   
	@GetMapping(value = "/print/xl")
	public ResponseEntity<?> generateFeesCollectionxl() throws Exception {
		
		List<Employees> employees = employeeService.getAllEmployees();
         if (employees == null || employees.isEmpty()) {
             // Handle case when there are no employees found
             return ResponseEntity.noContent().build();
         }
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("employees", employees);

		try {
		    Resource resource = new ClassPathResource("templates/employee.xlsx");
		    InputStream fileStream = resource.getInputStream();
		    XSSFWorkbook workbook = new XSSFWorkbook(fileStream);
		    XSSFSheet worksheet = workbook.getSheetAt(0);
		    XSSFCellStyle style1 = workbook.createCellStyle();
		    XSSFFont font = workbook.createFont();
		    font.setBold(true);

		    for (int i = 0; i < employees.size(); i++) {
		        XSSFRow row = worksheet.createRow(i + 1);
		        row.createCell(0).setCellValue(employees.get(i).getId());
		        row.createCell(1).setCellValue(employees.get(i).getName());
		        row.createCell(2).setCellValue(employees.get(i).getDob());
		        row.createCell(3).setCellValue(employees.get(i).getGender().toString());
		        row.createCell(4).setCellValue(employees.get(i).getAddressLine1());
		        
		        
		    }

		    ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    workbook.write(bos);
		    workbook.close();
		    byte[] barray = bos.toByteArray();
		    InputStream excelInputStream = new ByteArrayInputStream(barray);

		    String fileName = "faculty.xlsx";

		    return ResponseEntity.ok().header("Access-Control-Expose-Headers", "Content-Disposition")
		            .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
		            .body(new InputStreamResource(excelInputStream));
		} catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
	}
}


