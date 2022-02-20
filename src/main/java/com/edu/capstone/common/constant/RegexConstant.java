package com.edu.capstone.common.constant;

/**
 * @author NhatHH 
 * Date: Jan 22, 2022
 */
public class RegexConstant {

	/**
	 * Password cần ít nhất 6 ký tự
	 * 
	 * @version 1.0 - Initiation (Jan 22, 2022 by <b>NhatHH</b>)
	 */
	public static final int PASSWORD_LENGTH = 6;
	
	/**
	 * Phone cần có 10-11 ký tự, bắt đầu bằng số 0
	 * 
	 * @version 1.0 - Initiation (Jan 22, 2022 by <b>NhatHH</b>)
	 */
	public static final String PHONE_REGEXP = "^0[0-9]{9,10}$";

	/**
	 * Name chỉ được chứa chữ cái, khoảng cách và dấu gạch ngang
	 * 
	 * @version 1.0 - Initiation (Jan 22, 2022 by <b>NhatHH</b>)
	 */
	public static final String NAME_REGEXP = "[ \\p{L}-]+";

	/**
	 * Address chỉ được chứa: <br>
	 * <ul>
	 * 		<li>chữ cái, số</li>
	 * 		<li>khoảng cách (phím space)</li>
	 * 		<li>dấu phẩy, chấm, gạch ngang</li>
	 * </ul>
	 * 
	 * @version 1.0 - Initiation (Jan 22, 2022 by <b>NhatHH</b>)
	 */
	public static final String ADDRESS_REGEXP = "[ ,0-9\\p{L}-]+";
	
	/**
	 * Role name chỉ được chứa chữ cái và dấu gạch dưới
	 * 
	 * @version 1.0 - Initiation (Feb 6, 2022 by <b>NhatHH</b>)
	 */
	public static final String ROLE_NAME_REGEXP = "[\\p{L}_]+";

}
