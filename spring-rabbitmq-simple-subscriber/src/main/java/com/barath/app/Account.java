package com.barath.app;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Account {
	
	@JsonIgnore
	private Long accountNumber;
	
	

}
