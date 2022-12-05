package downloadexamp.controll;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import downloadexamp.downloadexcel.UserExcelExplorer;
import downloadexamp.model.Nobi;
import downloadexamp.repo.Nabirepo;

@Controller
@RequestMapping("/nobi")
public class Nobicontroller {

	@Autowired
	Nabirepo nrr;
	
	
	@RequestMapping("/")
	public String  aindex( ){
		
		
		return "INDEX";
	}
	
	
	@PostMapping("/add")
	public ResponseEntity<?> addnobi(@RequestBody Nobi nobi ){
		
		nrr.save(nobi);
		
		return new ResponseEntity<Nobi>(nobi , HttpStatus.OK);
	}
	
	  @GetMapping("/export/excel")
	    public void exportToExcel(HttpServletResponse response) throws IOException {
	        response.setContentType("application/octet-stream");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
	        response.setHeader(headerKey, headerValue);
	         
	        List<Nobi> listUsers = nrr.findAll();
	         
	        UserExcelExplorer excelExporter = new UserExcelExplorer(listUsers);
	         
	        excelExporter.export(response);    
	    }  	
	
	
	
	
}
