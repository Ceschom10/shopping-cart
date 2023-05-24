package backend.shoppingcart.component.util.constant;

/**
 * @author cesar amaya
 *
 */
public class ConstantesGeneral {

	private ConstantesGeneral(){
		throw new IllegalStateException("ConstantesGeneral class component util");
	}
	
	
	public static final String UNI = "UNI";
	public static final String RETIRO_EFECTIVO = "RTC";
	public static final String SUCCESS_RETIRO_EFECTIVO = "P";
	public static final String FAILED_RETIRO_EFECTIVO = "R";
	public static final String MSG_GENERICO_COBIS = "Error en COBIS, favor revisar";
	public static final String INFO_GENERAL= "INF";
	public static final String PRODUCTO_RETIRO_TCR= "TCR";
	public static final String MONEDA_BASE = "USD";
	public static final String TIPO_OPERACION_RETIRO_EFECTIVO = "RE";
	public static final String RESPONSE_OK = "OK";
	public static final String CTA_AHORRO ="Cuenta de ahorro";
	public static final String CTA_CORRIENTE ="Cuenta corriente";
	public static final String ISO_CTA_AHORRO ="AHO";
	public static final String ISO_CTA_CORRIENTE ="CTE";
	public static final String RESPONSE_OK_BROKER = "00";
	public static final String MASCARA_TCR ="XXXX-XXXX-XXXX-";
	public static final String MASCARA_PROD= "XXXX-XXXX-";
	public static final String STATUS_PENDING_BALCON ="Pending to SSU confirm";
	public static final String MASK_NUM="0.00";
	public static final String STATUS_ACTIVE_PILOTO="true";
	public static final String MSG_PILOTO="MIS_NO_VALIDO_PILOTO";
	public static final String SERV_OFFER="validateXMLOnly";
	public static final String TIPO_CONSULTA_PERSONA="P";
	public static final String ERROR_CONTROL_PARSING="TV_ERROR_PARSING_DATA_REQUEST";
	public static final String SERV_OBTENER_INFO="ObtenerCliente";
	public static final String SERV_CONS_PROD="ConsultaProductosCliente";
	public static final String SERV_CONS_DETTCR="ConsultaGeneralCuentas";
	public static final String SERV_BALCON="BalanceTransfer";
	public static final String SERV_RETIRO="RetiroEfectivo";
	public static final String ERROR_CONS="FAVOR VALIDAR EL CLIENTE NO CUENTA CON PRODUCTOS ACTIVOS";
	public static final String ERROR_CONS_TCR="COBIS_FAVOR VALIDAR EL CLIENTE NO CUENTA CON TCR ACTIVOS";
	public static final String TIPO_TCR_MASTERCARD = "Mastercard";
	public static final String TIPO_TCR_VISA = "Visa";
	public static final String CONFIRM_APPLY_PRODUCT="S";
	public static final String NOAPLICA_GENERAL="NA";
	public static final String SERV_OFFER2="validate";
	public static final String SEM_MASK="DigitalSales";
	public static final String TYPE_BALCON="BALCON";
	public static final String TYPE_RETIRO="RETIRO";
	public static final String STATUS_TRAMITE_RETIRO="T";
	public static final String HILO_PRODUCTO="callMainMethodOnBackGround";
	public static final String STATUS_APROBADOR="A";
	public static final String STATUS_PROCESO="En proceso";
	public static final String STATUS_PDCONFIRM_MSG="En proceso de verificación de datos.";
	public static final String STATUS_AP_MSG="En espera que el banco destino acepte el pago.";
	public static final String STATUS_PEND_CONFIM_CLIENT="Pending to client confirm";
	public static final String CONTROLLER_CALCULATE="calculateBalanceTransfer";
	
	/**Constantes de create retiro**/
	public static final String COMMISIONBT = "commissionBT";
	/**** Fin de constantes de retiro**/
	
	public static final String STATUS_OK = "Proceso finalizado exitosamente";
	
	public static final String CONTROLLER_SAVE_EXTRA_FINANCIAMIENTO="saveExtraFinanBalanceTransfer";
	
	public static final String STATUS= "status";
	public static final String SUCCESS= "success";
	public static final String MESSAGE_ERROR= "messageError";

	
	public static final String MESSAGE_LOG= "Consulta exitosa virtualService.desEncripterSql";
	public static final String MESSAGE_OK = "Proceso finalizado exitosamente";
	
	public static final String LOG_INFO= "LOG INFO";
	public static final String LOG_ERROR= "LOG ERROR";
	public static final String ID = ", id: ";
}
