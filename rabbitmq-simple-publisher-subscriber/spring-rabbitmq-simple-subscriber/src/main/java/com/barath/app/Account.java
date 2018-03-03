package com.barath.app;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 773784271152664523L;
	
	
	
	private Long accountNumber;
	
	

}
