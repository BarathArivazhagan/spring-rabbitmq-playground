package com.barath.app;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

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
