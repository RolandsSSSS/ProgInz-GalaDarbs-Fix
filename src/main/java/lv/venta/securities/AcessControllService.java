package lv.venta.securities;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AcessControllService {

	    public boolean isUserInOrganizationalUnit(Authentication authentication, String organizationalUnit) {
	      String userDn = authentication.getName();
	       return userDn != null && userDn.contains("ou=" + organizationalUnit);
	    }
}



