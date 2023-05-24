package backend.shoppingcart.constants;

public class ConstantesOtpLogin {

	private ConstantesOtpLogin() {
	}

	public static final String NCONTENTTYPE = "Content-Type";
	public static final String DCONTENTTYPE = "application/x-www-form-urlencoded";
	public static final String APPLICATIONJSON = "application/json";
	
	public static final String URL_BASE_FAKESTOREAPI = "https://fakestoreapi.com";

	public static final String NACCEPT = "Accept";
	public static final String DACCEPT = "application/json;charset=UTF-8";

	public static final String NAUTHORIZATION = "Authorization";

	public static final String NCREDENTIALS = "credentials";
	public static final String DCREDENTIALS = "true";

	public static final String FIELDLOGIN = "NewLoginIntegracionServiceImpl";

	public static final String URLOTPGENERATE = "/generate";
	public static final String URLOTPGENERATESMS = "/generateSms";
	public static final String URLOTPGENERATEEMAIL = "/generateEmail";
	public static final String ACCEPTLANGUAGE = "Accept-Language";
	public static final String LANGUAGE = "es";

	public static final String SERVICIO_VALIDPRODUCT = "core-query-products/api/v1/product/validProduct";
	public static final String SERVICIO_VALIDICCM = "backend-iccm/api/v1/customer/getCellEmail";

}
