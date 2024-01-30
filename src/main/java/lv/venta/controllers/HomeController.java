package lv.venta.controllers;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

	@GetMapping("/home")
	public String home() {
		return "index";
	}

	@GetMapping("/my-access-denied")
	public String accessDenied() {
		return "my-access-denied";
	}

}
